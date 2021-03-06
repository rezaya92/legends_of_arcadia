package Controller;

import Model.Card.Card;
import Model.Card.MonsterCard;
import Model.Card.SpellCard;
import Model.Player;
import View.GameView.ConsoleView;
import View.GameView.GameView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Formatter;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static Controller.Main.human;

public class CellTower extends Thread {
    private Socket socket;
    private ServerSocket serverSocket;
    private Scanner scanner;
    private PrintWriter printWriter;
    private final Object lock = new Object();

    CellTower(Socket socket) throws IOException {
        this.socket = socket;
        scanner = new Scanner(socket.getInputStream());
        printWriter = new PrintWriter(socket.getOutputStream(),true);
    }

    CellTower(Socket socket, ServerSocket serverSocket) throws IOException {
        this(socket);
        this.serverSocket = serverSocket;
    }

    void transmitText(String text){
        synchronized (lock) {
            printWriter.println(text);
        }
    }

    String receiveText(){
        if (human.getIsPlaying()) {
            String receivedText = scanner.nextLine();
            System.out.println(receivedText);
            return receivedText;
        }
        else
            return null;
    }

    void transmitInitials(int coin){
        synchronized (lock) {
            transmitInitials();
            transmitText("coin:" + String.valueOf(coin));
        }
    }

    void transmitInitials(){
        synchronized (lock) {
            transmitText("name:" + human.getName());
            if (human.getEquippedAmulet() == null)
                transmitText("equipped amulet:NULL");
            else
                transmitText("equipped amulet:" + human.getEquippedAmulet().getName());
        }
    }


    public void transmitPlayerData(Player player){
        synchronized (lock) {
            if (player == human)
                transmitText("Opponent Data:");
            else
                transmitText("My Data:");
            transmitText("Player Hp:" + player.getPlayerHero().getHp());
            transmitText("Player Mp:" + player.getMana());
            transmitText("player Max Mp:" + player.getMaxMana());
            transmitText("MonsterField:");
            for (int i = 0; i < 5; i++) {
                transmitMonsterFieldCard((MonsterCard) player.getMonsterFieldCards().get(i));
            }
            transmitText("SpellField:");
            for (int i = 0; i < 3; i++) {
                transmitCard(player.getSpellFieldCards().get(i));
            }
            transmitText("GraveYard:");
            for (Card card : player.getGraveyardCards()) {
                transmitCard(card);
            }
            transmitText("Hand:");
            for (Card card : player.getHandCards()) {
                transmitCard(card);
            }
            transmitText("Deck:");
            for (Card card : player.getDeckCards()) {
                transmitCard(card);
            }
            transmitText("end of transmission");
        }
    }

    private void transmitMonsterFieldCard(MonsterCard monsterCard){
        synchronized (lock) {
            if (monsterCard == null)
                transmitText("monster card name:NULL");
            else {
                transmitText("monster card name:" + monsterCard.getName());
                transmitText("monster card hp:" + monsterCard.getHp());
                transmitText("monster card ap:" + monsterCard.getAp());
                transmitText("monster card hasUsedSpell:" + monsterCard.hasUsedSpell());
            }
        }
    }

    private void transmitCard(Card card){
        synchronized (lock) {
            if (card == null)
                transmitText("card name:NULL");
            else {
                transmitText("card name:" + card.getName());
            }
        }
    }

    public void transmitConsoleView(String text){
        synchronized (lock) {
            transmitText("Console Text:");
            transmitText(text);
        }
    }

    void transmitWinner(Player winner){
        synchronized (lock) {
            transmitPlayerData(new Player("lost",-1000));
            //transmitText("Winner is:");
            //transmitText(winner.getName());
        }
    }

    @Override
    public void run() {
        String command = receiveText();
        while (human.getIsPlaying()){
            switch (command){
                case "Opponent Data:":
                    try {
                        Battle.processReceivedPlayerData(human.getOpponent());
                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                    }
                    break;
                case "My Data:":
                    try {
                        Battle.processReceivedPlayerData(human);
                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                    }
                    break;
                case "End Turn":
                    Battle.humanPlayTurn();
                    break;
                case "Console Text:":
                    ConsoleView.viewText(receiveText());
            }
            try{
                command = receiveText();
            }catch (NoSuchElementException ignored){}
        }
        System.out.println("Battle ended");
    }

    void closeSockets() {
        synchronized (lock) {
            try {
                socket.close();
                if (serverSocket != null)
                    serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
