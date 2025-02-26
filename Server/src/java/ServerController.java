import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.Serializable;
import java.util.function.Consumer;

public class ServerController {

    @FXML
    public Button startBtn, exitBtn;

    @FXML
    public Button port55, port56, port57;

    @FXML
    public Button cancelBtn;

    @FXML
    public Label Welcome;

    @FXML
    public Label ThreeCardPoker;

    @FXML
    public AnchorPane start;
    public AnchorPane serverGUI;
    public VBox serverScreen;

    @FXML public Label clientsCountLabel;
    @FXML public ListView gameResultsList;
    @FXML public ListView clientStatusLog;
    @FXML public Button stopServerBtn, startServerBtn;;
    @FXML public ImageView card1, card2, card3, card4;


    private static int selectedPort = -1;
    static Server server; // Reference to the Server class

    private Consumer<Serializable> onClientConnected;
    private Consumer<Serializable> onMessageReceived;
    private Consumer<Serializable> onServerError;
    /**
     * Initializes the controller class. You can add additional setup logic here.
     */
    @FXML
    public void initialize() {
//        selectedPort = -1; // No port selected by default
//        server = new Server(); // Initialize the server instance
    }

    @FXML
    void startServer(ActionEvent event) {
        try {
            // Make sure these UI elements exist and are properly initialized
            if (clientsCountLabel == null || gameResultsList == null || clientStatusLog == null) {
                System.err.println("UI components not initialized!");
                return;
            }

            if (server == null || !server.isRunning()) {
                System.out.println("Port:" + selectedPort);
                server = new Server(selectedPort,
                        data1 -> {
                            Platform.runLater(() -> {
                                clientsCountLabel.setText("Connected Clients: " + data1.toString());
                            });
                        },
                        data2 -> {
                            Platform.runLater(() -> {
                                gameResultsList.getItems().add(data2.toString());
                            });
                        },
                        data3 -> {
                            Platform.runLater(() -> {
                                clientStatusLog.getItems().add(data3.toString());
                            });
                        });

                server.startServer(selectedPort);
                startServerBtn.setDisable(true);
                stopServerBtn.setDisable(false);
                clientStatusLog.getItems().add("Server started on port " + selectedPort);
            }
        } catch (Exception e) {
            showAlert("Error", "Failed to start server: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    void stopServer(ActionEvent event) {
        try {
            if (server != null) {
                server.stopServer();
            }

            // Return to start screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/start.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 800, 600);
            scene.getStylesheets().add(getClass().getResource("/styles/style1.css").toExternalForm());

            // Get current stage and set new scene
            Stage currentStage = (Stage) stopServerBtn.getScene().getWindow();

            currentStage.setScene(scene);
            currentStage.show();

        } catch (Exception e) {
            showAlert("Error", "Failed to stop server: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    void port55Method(ActionEvent event) {
        selectedPort = 5555;
        showAlert("Port Selected", "You have selected port 5555.", Alert.AlertType.INFORMATION);
        System.out.println("Port 5555 selected.");
    }

    @FXML
    void port56Method(ActionEvent event) {
        selectedPort = 5556;
        showAlert("Port Selected", "You have selected port 5556.", Alert.AlertType.INFORMATION);
        System.out.println("Port 5556 selected.");
    }

    @FXML
    void port57Method(ActionEvent event) {
        selectedPort = 5557;
        showAlert("Port Selected", "You have selected port 5557.", Alert.AlertType.INFORMATION);
        System.out.println("Port 5557 selected.");
    }

    @FXML
    void cancelBtnMethod(ActionEvent event) {
        selectedPort = -1;
        showAlert("Action Cancelled", "Port selection has been reset.", Alert.AlertType.INFORMATION);
        System.out.println("Selection canceled.");
    }

    /**
     * Utility method to show an alert message.
     *
     * @param title   The title of the alert.
     * @param content The content of the alert.
     * @param type    The type of alert (ERROR, INFORMATION, etc.).
     */
    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Switch to the next scene after the server starts.
     */
    @FXML
    private void switchToNextScene() {
        try {
            // Load the new FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/serverGUI.fxml")); // Adjust the FXML path
            Parent nextSceneRoot = loader.load();

            // Get the current stage
            Stage currentStage = (Stage) start.getScene().getWindow();

            // Create and configure the new scene
            Scene nextScene = new Scene(nextSceneRoot, 800, 800); // Adjust size if needed
            // Try one of these paths:
            String css = getClass().getResource("/styles/style1.css").toExternalForm();

            nextScene.getStylesheets().add(css);

            // Set the new scene on the current stage
            currentStage.setScene(nextScene);

            // Show the updated stage
            currentStage.show();

            // Show reminder popup
            Alert reminder = new Alert(Alert.AlertType.INFORMATION);
            reminder.setTitle("Server Instructions");
            reminder.setHeaderText("Welcome to Server Control Panel");
            reminder.setContentText("Please click 'Start Server' to begin accepting client connections.");

            // Style the alert (optional)
            DialogPane dialogPane = reminder.getDialogPane();
            dialogPane.getStylesheets().add(css);

            reminder.showAndWait();

        } catch (Exception e) {
            showAlert("Error", "Failed to switch scenes: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }
}
