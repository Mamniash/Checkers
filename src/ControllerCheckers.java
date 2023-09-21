import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ControllerCheckers {

    @FXML
    private Button esc;

    @FXML
    private Button restart;

    @FXML
    void initialize() {
        esc.setOnMouseClicked(event -> CheckersApp.stage.setScene(CheckersApp.sceneMain));

        restart.setOnMouseClicked(event -> CheckersApp.stage.setScene(CheckersApp.getAndSetUpSceneCheckers()));
    }
}

