import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class JavaFXTemplate extends Application {

	public static void main(String[] args) {
		launch(args); // Launch the JavaFX application
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			// Load the FXML file for the start scene (start.fxml)
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/start.fxml"));
			Parent root = loader.load();

			// Set up the primary stage and scene
			primaryStage.setTitle("Welcome to Three Card Poker Server");
			Scene scene = new Scene(root, 800, 535); // Scene size matches the FXML layout dimensions

			// Add CSS stylesheet
			scene.getStylesheets().add(getClass().getResource("/styles/style1.css").toExternalForm());

			// Set the scene on the primary stage
			primaryStage.setScene(scene);

			// Add window close handler
			primaryStage.setOnCloseRequest(event -> {
				if (ServerController.server != null && ServerController.server.isRunning()) {
					ServerController.server.stopServer();
				}
				Platform.exit();
				System.exit(0);
			});

			// Display the stage
			primaryStage.show();



		} catch (Exception e) {
			// Print the stack trace for debugging
			e.printStackTrace();

			// Exit the application with error code 1
			System.exit(1);
		}
	}
}
