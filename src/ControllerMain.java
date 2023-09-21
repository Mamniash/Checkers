import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ControllerMain {

    @FXML
    private Button start;

    @FXML
    private Button begin;

    @FXML
    void initialize() {
        begin.setOnMouseClicked(event -> {
            if (CheckersApp.isGame) CheckersApp.stage.setScene(CheckersApp.sceneCheckers);
        });

        start.setOnMouseClicked(event -> CheckersApp.stage.setScene(CheckersApp.getAndSetUpSceneCheckers()));

    }
}

