package king;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import king.ui.DialogBox;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private King king;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/User.png"));
    private Image kingImage = new Image(this.getClass().getResourceAsStream("/images/King.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Injects the King instance
     */
    public void setKing(King k) {
        king = k;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing King's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = king.getResponse(input);
        if (response.startsWith("BYE")) {
            Platform.exit();
        } else {
            dialogContainer.getChildren().addAll(
                    DialogBox.getUserDialog(input, userImage),
                    DialogBox.getKingDialog(response, kingImage)
            );
        }
        userInput.clear();
    }
}
