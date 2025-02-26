import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;


public class JavaFXTemplate extends Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/client.fxml"));
		Parent root = loader.load();
		// Get the controller instance from the loader to ensure @FXML fields are injected
		ClientController controller = loader.getController();
		primaryStage.setTitle("Welcome to Three Card Poker ClientThread");
		Scene scene = new Scene(root, 800,535);
		scene.getStylesheets().add("/styles/style1.css");
		primaryStage.setScene(scene);
		primaryStage.show();
		controller.startSpinAnimation();
		controller.setPrimaryStage(primaryStage);
	}

}
