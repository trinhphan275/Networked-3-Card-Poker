import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.fxml.FXML;

import java.io.IOException;

public class WinController {
    @FXML
    private Label totalWonW;

    public void setTotalWon(String text) {
        totalWonW.setText(text);
    }


    public void nextRoundBtn(ActionEvent e) throws IOException {
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.close();
    }

    public void endGame(ActionEvent e)throws IOException{
        //the player clicks the exit button on the popup
        //ends the program
        System.exit(0);
    }

}
