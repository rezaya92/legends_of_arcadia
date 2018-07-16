package Controller;

import Model.Card.Card;
import Model.Card.MonsterCard;
import Model.Card.SpellCard;
import Model.Player;
import View.GameView.GameView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Formatter;
import java.util.Scanner;

import static Controller.Main.human;

public class CellTower implements Runnable {
    private Scanner scanner;
    private Formatter formatter;

    CellTower(Socket socket) throws IOException {
        scanner = new Scanner(socket.getInputStream());
        formatter = new Formatter(socket.getOutputStream());
    }

    void transmitText(String text){
        formatter.format(text + "\n");
    }

    String receiveText(){
        String receivedText = scanner.nextLine();
        System.out.println(receivedText);
        return receivedText;
    }

    void transmitInitials(int coin){
        transmitInitials();
        transmitText("coin:" + String.valueOf(coin));
    }

    void transmitInitials(){
        transmitText("name:" + human.getName());
        transmitText("equipped amulet:" + human.getEquippedAmulet().getName());
    }

    public void transmitPlayerData(Player player){
        if (player == human)
            transmitText("Opponent Data:");
        else
            transmitText("My Data:");
        transmitText("Player Hp:" + player.getPlayerHero().getHp());
        transmitText("Player Mp:" + player.getMana());
        transmitText("player Max Mp:" + player.getMaxMana());
        transmitText("MonsterField:");
        for (int i = 0; i < 5; i++) {
            transmitMonsterFieldCard((MonsterCard)player.getMonsterFieldCards().get(i));
        }
        transmitText("SpellField:");
        for (int i = 0; i < 3; i++) {
            transmitCard(player.getSpellFieldCards().get(i));
        }
        transmitText("GraveYard:");
        for (Card card: player.getGraveyardCards()){
            transmitCard(card);
        }
        transmitText("Hand:");
        for (Card card: player.getHandCards()){
            transmitCard(card);
        }
        transmitText("Deck:");
        for (Card card: player.getDeckCards()){
            transmitCard(card);
        }
        transmitText("end of transmission");
    }

    private void transmitMonsterFieldCard(MonsterCard monsterCard){
        if (monsterCard == null)
            transmitText("monster card name:NULL");
        else {
            transmitText("monster card name:" + monsterCard.getName());
            transmitText("monster card hp:" + monsterCard.getHp());
            transmitText("monster card ap:" + monsterCard.getAp());
        }
    }

    private void transmitCard(Card card){
        if (card == null)
            transmitText("card name:NULL");
        else {
            transmitText("card name:" + card.getName());
        }
    }

    @Override
    public void run() {
        String command = receiveText();
        while (!command.equals("Winner is:")){
            switch (command){
                case "Opponent Data:":
                    Battle.processReceivedPlayerData(human.getOpponent());
                    break;
                case "My Data:":
                    Battle.processReceivedPlayerData(human);
                    break;
                case "End Turn":
                    GameView.showIdleScene();
            }
            command = receiveText();
        }
    }


}
