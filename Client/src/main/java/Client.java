import javafx.application.Platform;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Consumer;

public class Client {

    Socket socketClient;

    ObjectOutputStream out;
    ObjectInputStream in;

    private Consumer<Serializable> callback;
    private Consumer<Serializable> callback2;
    private Consumer<Serializable> callback3;
    private int portNumber;
    private String IPAddress;
    ClientThread c;

    public Client(int p, String i, Consumer<Serializable> call, Consumer<Serializable> call2,  Consumer<Serializable> call3) {
        callback = call;
        callback2 = call2;
        callback3 = call3;
        this.portNumber = p;
        this.IPAddress = i;
        c = new ClientThread();
        new Thread(c).start();
    }

    public class ClientThread implements Runnable {
        PokerInfo info;
        int i = 1;
        double aBet;
        double pBet;
        boolean d = true;
        public void run() {
            connectToServer(IPAddress, portNumber);


            while(true) {
                try {
                    //Waiting for the player bet
                    //Step 1
                     System.out.println("STEP 1 Initial get INFO");
                     info = (PokerInfo) in.readObject();
                     System.out.println("Call 1: " + info.getAnteBet());
                     callback.accept(info.getAnteBet());

                    if(!d){
                        //send old values
                        info.setAnteBet(aBet);
                        info.setPairPlusBet(pBet);
                        System.out.println("Step 2 Sending Ante and Pair Plus: " + info.getAnteBet() + " PP: " + info.getPairPlusBet());
                        out.writeObject(info);
                        d = true;
                    } //else just wait for the

                    //Step 3 --> updated PokerInfo with the player and dealer cards as well as the placed bets
//                    getInfo2(info);
                    info = (PokerInfo) in.readObject();
                    System.out.println("STEP 3 get player and dealer hands");
                    String[] pHandString = info.getPlayerHand();
                    callback2.accept(pHandString[0]);
                    callback2.accept(pHandString[1]);
                    callback2.accept(pHandString[2]);
                    String[] dHandString = info.getDealerHand();
                    callback2.accept(dHandString[0]);
                    callback2.accept(dHandString[1]);
                    callback2.accept(dHandString[2]);
                    System.out.println("Player Hand: " + Arrays.toString(pHandString));
                    System.out.println("Dealer Hand: " + Arrays.toString(dHandString));

                    //Step 5 get winnings info
                    info = (PokerInfo) in.readObject();
                    System.out.println("STEP 5 get winnings INFO");
                    System.out.println("Call3: " + info.getAnteBet());
                    callback3.accept(info.getAnteBet());
                    callback3.accept(info.getTotalWinnings());
                    callback3.accept(info.isPlayerWins());
                    callback3.accept(info.isDealerQualified());
                    callback3.accept(info.getPairPlusBet());

                    //step 7 buffer?
                    info = (PokerInfo) in.readObject();

                }
                catch(Exception e) {
                    System.out.println("Thread Terminated");
                    closeResources();
                    System.exit(0);
                }
            }

        }

        public void sendAnteBet(String data) {
            double d = Integer.parseInt(data);
            aBet = d;
            info.setAnteBet(d);
            System.out.println("AnteBet Client: "+  info.getAnteBet());
        }

        public void sendPairPlus(String data) {
            try {
                double d = Integer.parseInt(data);
                pBet = d;
                info.setPairPlusBet(d);
                System.out.println("Step 2 Sending Ante and Pair Plus: " + info.getAnteBet() + " PP: " + info.getPairPlusBet());
                out.writeObject(info);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        public void setDealerQualified(boolean dea){
            System.out.println("setDealerQualified");
            d = dea;
        }

        public void sendPlay(String data) {
            try {
                info.setDecision(data);
                System.out.println(" Step 4 Sending Play or Fold");
                out.writeObject(info);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void sendNextRound(boolean data) {
            try {
                info.setPlayAnotherGame(data);
                System.out.println("Step 6 Sending next round info");
                out.writeObject(info);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void connectToServer(String host, int port){
            try {
                socketClient = new Socket(host, port); // port --> 5555
                out = new ObjectOutputStream(socketClient.getOutputStream());
                in = new ObjectInputStream(socketClient.getInputStream());
                socketClient.setTcpNoDelay(true);
            } catch(Exception e) {
                notifyConnectionFailure("Failed to connect: " + e.getMessage());
                System.out.println("Failed with ip address: " + host);
            }
        }

        private void notifyConnectionFailure(String errorMessage) {
            Platform.runLater(() -> callback.accept(errorMessage)); // Notify controller of error
            closeResources();
        }

        private void closeResources() {
            try {
                if (socketClient != null) socketClient.close();
                if (in != null) in.close();
                if (out != null) out.close();
            } catch (IOException ignored) {}
        }
    }

}
