package Controller;

import Model.Card.Card;
import Model.Card.MonsterCard;
import Model.Card.SpellCard;
import Model.Player;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Formatter;
import java.util.Scanner;

import static Controller.Main.human;

public class CellTower implements Runnable {
    private Socket socket;
    private Scanner scanner;
    private Formatter formatter;

    CellTower(Socket socket) throws IOException {
        this.socket = socket;
        scanner = new Scanner(socket.getInputStream());
        formatter = new Formatter(socket.getOutputStream());
    }

    private void transmitText(String text){
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
        for (int i = 0; i < 5; i++) {
            transmitText("MonsterField:");
            transmitMonsterFieldCard((MonsterCard)player.getMonsterFieldCards().get(i));
        }
        for (int i = 0; i < 3; i++) {
            transmitText("SpellField:");
            transmitCard(player.getSpellFieldCards().get(i));
        }
        for (Card card: player.getGraveyardCards()){
            transmitText("GraveYard:");
            transmitCard(card);
        }
        for (Card card: player.getHandCards()){
            transmitText("Hand:");
            transmitCard(card);
        }
        for (Card card: player.getDeckCards()){
            transmitText("Deck");
            transmitCard(card);
        }
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
            transmitText("card:NULL");
        else {
            transmitText("monsterC");
        }
    }

    @Override
    public void run() {

    }


}
