import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class ClientController implements Initializable {

    @FXML
    ImageView card1, card2, card3, card4, cardP1a, cardP1b, cardP1c, cardDa, cardDb, cardDc, h1, h2, h3, h4, l1, l2, l3, l4;

    @FXML
    Label totalWonW, totalWonL, P1PotLbl, Welcome, to, ThreeCardPoker, P1, D, ErrorLbl, P1AnteLbl, P1PairPlusLbl, P1PlayLbl, P1TotalWinningsLbl;

    @FXML
    Button moveToNextGame, quitBtn, nextRoundBtn2, foldedBtn, loseBtn, NextBtn, ShowDealersCards, ShowCards, P1NoPairPlusBtn, connectBtn, startBtn, exitBtn, P1SetAnteBtn, P1SetPairPlusBtn, P1FoldBtn, P1ReadyBtn, P1setPlayBtn;

    @FXML
    TextField portTextField, IPAddressTextField, P1AnteTextField, P1PairPlusTextField;

    @FXML
    ListView Listview1;

    @FXML
    MenuBar MyMenu;

    private static Stage pStage;

    Stage stage;
    Client c1;

    ArrayList<String> listItems = new ArrayList<>();
    ArrayList<String> listItems2 = new ArrayList<>();
    ArrayList<String> listItems3 = new ArrayList<>();

    boolean dealerQualified = true;

    int port;
    String IP;


    int styleScreen = 1;

    public void setPrimaryStage(Stage stage) {
        this.pStage = stage;
    }

    ObservableList<String> items = FXCollections.observableArrayList();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
    }

    public void startSpinAnimation() {
        //sets up animation for homepage
        // Create the rotate transition
        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(2), card1);
        rotateTransition.setByAngle(175);
        rotateTransition.setCycleCount(RotateTransition.INDEFINITE);
        rotateTransition.setAutoReverse(true); // Spin back and forth

        RotateTransition rotateTransition2 = new RotateTransition(Duration.seconds(2), card2);
        rotateTransition2.setByAngle(175);
        rotateTransition2.setCycleCount(RotateTransition.INDEFINITE);
        rotateTransition2.setAutoReverse(true); // Spin back and forth

        RotateTransition rotateTransition3 = new RotateTransition(Duration.seconds(2), card3);
        rotateTransition3.setByAngle(175);
        rotateTransition3.setCycleCount(RotateTransition.INDEFINITE);
        rotateTransition3.setAutoReverse(true); // Spin back and forth

        RotateTransition rotateTransition4 = new RotateTransition(Duration.seconds(2), card4);
        rotateTransition4.setByAngle(175);
        rotateTransition4.setCycleCount(RotateTransition.INDEFINITE);
        rotateTransition4.setAutoReverse(true); // Spin back and forth

        // Start the animation
        rotateTransition.play();
        rotateTransition2.play();
        rotateTransition3.play();
        rotateTransition4.play();
    }

    public void connectToServer(ActionEvent e) throws IOException {
        try {

            String ip = IPAddressTextField.getText();
            int p = Integer.parseInt(portTextField.getText());
            if(ip.equals("")){
                showError("Invalid IP Address");
                return;
            }

            IP = ip;
            port = p;

            c1 = new Client(p, ip, data -> {
                Platform.runLater(() -> {
                    if (data.toString().contains("Failed") || data.toString().contains("Connection lost")) {
                        resetConnectionUI(data.toString());
                    } else {
                        listItems.add(data.toString());
                    }
                });

            }, data2 -> {
                Platform.runLater(() -> {
                    try {
                        System.out.println("2 From Client: " + data2.toString());
                        listItems2.add(data2.toString());
                    } catch (Exception b) {
                        System.out.println("2 Received null data");
                    }
                });
            }, data3 -> {
                Platform.runLater(() -> {
                    try {
                        System.out.println("3 From Client: " + data3.toString());
                        listItems3.add(data3.toString());
                    } catch(Exception a) {
                        System.out.println("3 Received null data");
                    }
                });

            });

            ErrorLbl.setVisible(false);

            startBtn.setVisible(true);
            exitBtn.setVisible(true);

            connectBtn.setVisible(false);
            IPAddressTextField.setVisible(false);
            portTextField.setVisible(false);

        } catch (NumberFormatException ex) {
            showError("Invalid port number or IP Address");
        }
    }

    public void startMethod(ActionEvent e) throws IOException {
        //setting up the game screen shifting from the homepage
        Listview1.setItems(items);

        card1.setVisible(false);
        card2.setVisible(false);
        card3.setVisible(false);
        card4.setVisible(false);
        Welcome.setVisible(false);
        to.setVisible(false);
        ThreeCardPoker.setVisible(false);
        startBtn.setVisible(false);
        exitBtn.setVisible(false);

        P1.setVisible(true);
        D.setVisible(true);

        P1SetAnteBtn.setVisible(true);
        P1AnteTextField.setVisible(true);

        P1AnteLbl.setVisible(true);
        P1PairPlusLbl.setVisible(true);
        P1PlayLbl.setVisible(true);
        P1TotalWinningsLbl.setVisible(true);

        Listview1.setVisible(true);
        MyMenu.setVisible(true);

        items.add("Player must set Ante bet");
        items.add("Pair plus bet is optional");

    }

    public void setAntePlayerOne (ActionEvent e) throws IOException{
        if(Integer.parseInt(P1AnteTextField.getText()) > 4 && Integer.parseInt(P1AnteTextField.getText()) <= 25){
            P1SetAnteBtn.setVisible(false);
            P1AnteTextField.setVisible(false);

            P1PairPlusTextField.setVisible(true);
            P1SetPairPlusBtn.setVisible(true);
            P1NoPairPlusBtn.setVisible(true);

            P1AnteLbl.setText("Ante: $" + Integer.parseInt(P1AnteTextField.getText()));
            items.add("Player bet Ante: $" + Integer.parseInt(P1AnteTextField.getText()));

            //Step 2a sending the Ante Bet
            c1.c.sendAnteBet(P1AnteTextField.getText());  //sends the ante bet back to the server
        } else {
            showError("Enter valid Ante Bet");
        }
    }

    public void setPairPlusPlayerOne(ActionEvent e) throws IOException{
        if(!P1PairPlusTextField.getText().equals("")){
            if(Integer.parseInt(P1PairPlusTextField.getText()) > 4 && Integer.parseInt(P1PairPlusTextField.getText()) <= 25){
                ErrorLbl.setVisible(false);
                P1PairPlusTextField.setVisible(false);
                P1SetPairPlusBtn.setVisible(false);
                P1NoPairPlusBtn.setVisible(false);

                ShowCards.setVisible(true);

                P1PairPlusLbl.setText("Pair Plus: $" + Integer.parseInt(P1PairPlusTextField.getText()));
                items.add("Player bet Pair Plus: $" + Integer.parseInt(P1PairPlusTextField.getText()));

                //Step 2b setting the pair plus bet
                c1.c.sendPairPlus(P1PairPlusTextField.getText());

                cardP1a.setVisible(true);
                cardP1b.setVisible(true);
                cardP1c.setVisible(true);
                Image p1 = new Image("/images/cardback.jpg");
                cardP1a.setImage(p1);
                Image p2 = new Image("/images/cardback.jpg");
                cardP1b.setImage(p2);
                Image p3 = new Image("/images/cardback.jpg");
                cardP1c.setImage(p3);

                items.add("Player's cards are shown");
            } else {
                showError("Enter valid Pair Plus Bet");
            }
        } else {
            showError("Enter valid Pair Plus Bet");
        }

    }

    public void NoPairPlusPlayerOne(ActionEvent e) throws IOException{
        cardP1a.setVisible(true);
        cardP1b.setVisible(true);
        cardP1c.setVisible(true);
        Image p1 = new Image("/images/cardback.jpg");
        cardP1a.setImage(p1);
        Image p2 = new Image("/images/cardback.jpg");
        cardP1b.setImage(p2);
        Image p3 = new Image("/images/cardback.jpg");
        cardP1c.setImage(p3);

        ErrorLbl.setVisible(false);
        P1PairPlusTextField.setVisible(false);
        P1SetPairPlusBtn.setVisible(false);
        P1NoPairPlusBtn.setVisible(false);

        ShowCards.setVisible(true);
//        P1FoldBtn.setVisible(true);
//        P1setPlayBtn.setVisible(true);

        P1PairPlusLbl.setText("Pair Plus: $0");
        items.add("Player did not bet Pair Plus");

        //Step 2b setting the pair plus bet
        c1.c.sendPairPlus("0");
    }

    public void ShowCardsP1(){
        cardP1a.setVisible(true);
        cardP1b.setVisible(true);
        cardP1c.setVisible(true);

        ShowCards.setVisible(false);
        String c10 = listItems2.get(0);
        String result = c10.substring(c10.indexOf("/"));
        System.out.println(listItems2.get(0));
        Image p1 = new Image(result);
        cardP1a.setImage(p1);

        String c2 = listItems2.get(1);
        result = c2.substring(c2.indexOf("/"));
        Image p2 = new Image(result);
        cardP1b.setImage(p2);

        String c3 = listItems2.get(2);
        result = c3.substring(c3.indexOf("/"));
        Image p3 = new Image(result);
        cardP1c.setImage(p3);

        P1FoldBtn.setVisible(true);
        P1setPlayBtn.setVisible(true);
    }

    public void setPlayPlayerOne(ActionEvent e) throws IOException{
        ErrorLbl.setVisible(false);

        P1FoldBtn.setVisible(false);
        P1setPlayBtn.setVisible(false);

        ShowDealersCards.setVisible(true);

        items.add("Player bet Play");

        cardDa.setVisible(true);
        cardDb.setVisible(true);
        cardDc.setVisible(true);
        Image p1 = new Image("/images/cardback.jpg");
        cardDa.setImage(p1);
        Image p2 = new Image("/images/cardback.jpg");
        cardDb.setImage(p2);
        Image p3 = new Image("/images/cardback.jpg");
        cardDc.setImage(p3);

        //Step 4a player play bet
        c1.c.sendPlay("Play");
    }

    public void foldPlayerOne(ActionEvent e) throws IOException{
        ErrorLbl.setVisible(false);

        P1FoldBtn.setVisible(false);
        P1setPlayBtn.setVisible(false);

        P1PlayLbl.setText("Play: $0");
        items.add("Player folds");

        //Step 4b player folded
        c1.c.sendPlay("Fold");

//        playerFolded();
        foldedBtn.setVisible(true);
    }

    public void playerFolded(){
        foldedBtn.setVisible(false);
        quitBtn.setVisible(true);
        nextRoundBtn2.setVisible(true);
        P1TotalWinningsLbl.setText("Total: $" + listItems3.get(1));
        items.add("Player had folded this round.");
    }

    public void NextRoundFromFold(ActionEvent e) throws IOException{
        quitBtn.setVisible(false);
        nextRoundBtn2.setVisible(false);
        nextRound();
    }

    public void quitFromFold(ActionEvent e) throws IOException{
        quitBtn.setVisible(false);
        nextRoundBtn2.setVisible(false);
        c1.c.sendNextRound(false);
        System.exit(0);
    }

    public void setShowDealersCards(){
        ShowDealersCards.setVisible(false);
        P1PlayLbl.setText("Play: " + listItems3.get(0));
        if(listItems3.get(3).equals("false")){
            //the dealer did not qualify
            items.add("The dealer does not have a ");
            items.add("Queen high or better.");
            items.add("Ante bet will be pushed");
            items.add("to the next round.");
            items.add("Play bet will be returned");
            items.add(" ");
            showWinnings(false);
            dealerQualified = false;
        } else {
            P1TotalWinningsLbl.setText("Total: $" + listItems3.get(1));

            String c10 = listItems2.get(3);
            String result = c10.substring(c10.indexOf("/"));
            Image p1 = new Image(result);
            cardDa.setImage(p1);

            String c2 = listItems2.get(4);
            result = c2.substring(c2.indexOf("/"));
            Image p2 = new Image(result);
            cardDb.setImage(p2);

            String c3 = listItems2.get(5);
            result = c3.substring(c3.indexOf("/"));
            Image p3 = new Image(result);
            cardDc.setImage(p3);

            showWinnings(true);
        }
    }

    public void showWinnings(boolean dealerQualified){
        if(dealerQualified) {
            P1TotalWinningsLbl.setText("Total: $" + listItems3.get(1));
            if (listItems3.get(2).equals("true")) {
                items.add("Player Won this round!");
            } else {
                items.add("Dealer Won this round!");
            }
            NextBtn.setVisible(true);
        } else {
            //dealer didn't qualify
            moveToNextGame.setVisible(true);
        }
    }

    public void next(ActionEvent e) throws IOException{
        NextBtn.setVisible(false);
        if(listItems3.get(2).equals("true")){
            playerWon();
            //totalWonW.setText("Total: " + listItems3.get(1));
        } else {
            try {
                loseBtn.setVisible(true);
                //playerLost();
            } catch (Exception a){}

            System.out.println("PROBLEM: " +  listItems3.get(1));
            //totalWonL.setText("Total: " + listItems3.get(1));
        }
    }

    public void exitMethod(ActionEvent e) throws IOException{
        //the player clicks the exit button on the homepage
        //ends the program
        System.exit(0);
    }

    private void resetConnectionUI(String errorMessage) {
        showError(errorMessage);

        // Reset connection-related UI elements
        connectBtn.setVisible(true);
        IPAddressTextField.setVisible(true);
        portTextField.setVisible(true);
        startBtn.setVisible(false);
        exitBtn.setVisible(false);

        IPAddressTextField.clear();
        portTextField.clear();
    }

    private void showError(String message) {
        ErrorLbl.setText(message);
        ErrorLbl.setVisible(true);
    }

    public void menuNewLookMethod(ActionEvent e) throws IOException{
        //changes the look of the page
        //flips between the two stylesheets
        if(styleScreen == 1){
            pStage.getScene().getStylesheets().clear();
            pStage.getScene().getStylesheets().add(getClass().getResource("/styles/style2.css").toExternalForm());
            styleScreen = 2;
            items.add(0, "A Halloween theme!!!!!");
        } else {
            pStage.getScene().getStylesheets().clear();
            pStage.getScene().getStylesheets().add(getClass().getResource("/styles/style1.css").toExternalForm());
            styleScreen = 1;
            items.add(0, "Back to the normal theme . . .");
        }
        pStage.setScene(pStage.getScene());
        pStage.show();
    }

    public void menuExitMethod(ActionEvent e) throws IOException{
        //the player clicks the exit button on the menu
        //creates the pop-up to confirm if they want to end the program
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/game.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, 335, 250);
        if(styleScreen == 1){
            scene.getStylesheets().add("/styles/style1.css");
        } else {
            scene.getStylesheets().add("/styles/style2.css");
        }

        stage = new Stage();
        stage.setTitle("Exit Window");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();

    }

    public void endGame(ActionEvent e)throws IOException{
        //the player clicks the exit button on the popup
        //ends the program
        System.exit(0);
    }

    public void continueGame(ActionEvent e)throws IOException{
        //the player clicks the continue button on the homepage
        //closes the pop-up and goes back to the game screen
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.close();
    }

    public void menuFreshStartMethod(ActionEvent e) throws IOException{
        //resetting the UI
        pStage.getScene().getStylesheets().clear();
        pStage.getScene().getStylesheets().add(getClass().getResource("/styles/style1.css").toExternalForm());
        styleScreen = 1;

        items.clear();
        items.add(0, "New Game started");
        items.add("Player must set Ante bet");
        items.add("Pair plus bet is optional");

        P1SetAnteBtn.setVisible(true);
        P1AnteTextField.setVisible(true);
        P1AnteTextField.clear();
        P1SetPairPlusBtn.setVisible(false);
        P1NoPairPlusBtn.setVisible(false);
        P1PairPlusTextField.setVisible(false);
        P1PairPlusTextField.clear();
        P1AnteLbl.setVisible(true);
        P1AnteLbl.setText("Ante: ");
        P1PairPlusLbl.setVisible(true);
        P1PairPlusLbl.setText("Pair Plus: ");
        P1PlayLbl.setVisible(true);
        P1PlayLbl.setText("Play: ");
        P1TotalWinningsLbl.setVisible(true);
        P1TotalWinningsLbl.setText("Total: ");

        cardP1a.setVisible(false);
        cardP1b.setVisible(false);
        cardP1c.setVisible(false);
        P1FoldBtn.setVisible(false);
        P1setPlayBtn.setVisible(false);
        cardDa.setVisible(false);
        cardDb.setVisible(false);
        cardDc.setVisible(false);
        ErrorLbl.setVisible(false);
        moveToNextGame.setVisible(false);
        quitBtn.setVisible(false);
        nextRoundBtn2.setVisible(false);
        foldedBtn.setVisible(false);
        loseBtn.setVisible(false);
        NextBtn.setVisible(false);
        ShowDealersCards.setVisible(false);
        ShowCards.setVisible(false);


        listItems.clear();
        listItems2.clear();
        listItems3.clear();
        //c1.c.closeResources();


        try {

            c1 = new Client(port, IP, data -> {
                Platform.runLater(() -> {
                    if (data.toString().contains("Failed") || data.toString().contains("Connection lost")) {
                        resetConnectionUI(data.toString());
                    } else {
                        listItems.add(data.toString());
                    }
                });

            }, data2 -> {
                Platform.runLater(() -> {
                    try {
                        System.out.println("2 From Client: " + data2.toString());
                        listItems2.add(data2.toString());
                    } catch (Exception b) {
                        System.out.println("2 Received null data");
                    }
                });
            }, data3 -> {
                Platform.runLater(() -> {
                    try {
                        System.out.println("3 From Client: " + data3.toString());
                        listItems3.add(data3.toString());
                    } catch(Exception a) {
                        System.out.println("3 Received null data");
                    }
                });

            });

            ErrorLbl.setVisible(false);

            startBtn.setVisible(false);
            exitBtn.setVisible(false);

            connectBtn.setVisible(false);
            IPAddressTextField.setVisible(false);
            portTextField.setVisible(false);

        } catch (NumberFormatException ex) {
            showError("Invalid port number or IP Address");
        }


    }

    public void playerWon(){
        //the player clicks the exit button on the menu
        //creates the pop-up to confirm if they want to end the program
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/win.fxml"));
            Parent root = loader.load();

            // Get the controller for win.fxml and update the label
            WinController winController = loader.getController();
            winController.setTotalWon("Total: $" + listItems3.get(1));

            Scene scene = new Scene(root, 400, 400);
            if (styleScreen == 1) {
                scene.getStylesheets().add("/styles/style1.css");
            } else {
                scene.getStylesheets().add("/styles/style2.css");
            }

            stage = new Stage();
            stage.setTitle("You Won");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e){

        }

        moveToNextGame.setVisible(true);
    }

    public void playerLost(ActionEvent e) throws IOException {
        loseBtn.setVisible(false);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/lose.fxml"));
        Parent root = loader.load();

        // Get the controller for lose.fxml and update the label
        LoseController loseController = loader.getController();
        loseController.setTotalWon("Total: $" + listItems3.get(1));

        Scene scene = new Scene(root, 400, 400);
        if (styleScreen == 1) {
            scene.getStylesheets().add("/styles/style1.css");
        } else {
            scene.getStylesheets().add("/styles/style2.css");
        }

        stage = new Stage();
        stage.setTitle("Exit Window");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();

        moveToNextGame.setVisible(true);
    }

    public void setMoveToNextGame(ActionEvent e) throws IOException{
        moveToNextGame.setVisible(false);
        nextRound();
    }

    public void nextRoundBtn(ActionEvent e) throws IOException {
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.close();
    }

    public void nextRound(){
        System.out.println("IN next rnd function --> should remove cards ");
        items.add("Moving to the next round.");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/client.fxml"));
        loader.setController(this); // Make sure the controller is set

        if(dealerQualified){
            //if the dealer was qualified then new bets
            P1AnteTextField.setVisible(true);
            P1AnteTextField.clear();
            P1SetAnteBtn.setVisible(true);

            P1PairPlusTextField.clear();

            P1AnteLbl.setText("Ante: ");
            P1PairPlusLbl.setText("Pair Plus: ");
            P1PlayLbl.setText("Play: ");

            listItems.clear();
            listItems2.clear();
            listItems3.clear();
        }

        cardDa.setVisible(false);
        cardDb.setVisible(false);
        cardDc.setVisible(false);
        cardP1a.setVisible(false);
        cardP1b.setVisible(false);
        cardP1c.setVisible(false);

        try{
            //Step 6 --> send next round info
            c1.c.sendNextRound(true);
        } catch (Exception a){}

        //new function?
        if(!dealerQualified){
            System.out.println("DEALER NOT QUALIFIED!");
//            double anteBet = Double.parseDouble(listItems3.get(0));
//            c1.c.sendAnteBet(String.valueOf((int) anteBet));
//
//            double pairPlusBet = Double.parseDouble(listItems3.get(4));
//            c1.c.sendPairPlus(String.valueOf((int) pairPlusBet));
            c1.c.setDealerQualified(false);
//            c1.c.sendAnteBet(P1AnteTextField.getText());
//            System.out.println("Anye Bet: " + P1AnteTextField.getText());
//            if(P1PairPlusTextField.getText().equals("")){
//                c1.c.sendPairPlus("0");
//            } else {
//                c1.c.sendPairPlus(P1PairPlusTextField.getText());
//            }
//            System.out.println("PairPlus  Bet: " + P1PairPlusTextField.getText());
            listItems.clear();
            listItems2.clear();
            listItems3.clear();
            ShowCards.setVisible(true);
        }
        dealerQualified = true;
    }

}