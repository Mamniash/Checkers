import PackageOfCheckers.Game;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;

public class CheckersApp extends Application {
    public static Stage stage;
    public static Scene sceneMain;
    public static Scene sceneCheckers;
    public static boolean isGame = false;

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        stage.setTitle("Checkers");
        stage.setResizable(false);
        stage.setScene(getAndSetUpSceneMain());

        stage.show();
    }

    private static Pane loadFXML(String url) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(CheckersApp.class.getResource(url));
            return loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Pane();
    }

    private static Scene getAndSetUpSceneMain() {
        sceneMain = new Scene(loadFXML("/FXML/Main2.fxml"));
        return sceneMain;
    }

    public static Scene getAndSetUpSceneCheckers() {
        Game.start();
        isGame = true;
        BorderPane root = new BorderPane();

        Pane bar = loadFXML("/FXML/Checkers2.fxml");
        Pane paneOfCheckers = new Pane();
        paneOfCheckers.setPrefSize(Game.WIDTH * Game.TILE_SIZE, Game.HEIGHT * Game.TILE_SIZE);

        paneOfCheckers.getChildren().addAll(Game.getTileGroup(), Game.getPieceGroup());

        bar.setPrefWidth(paneOfCheckers.getPrefWidth());

        root.setTop(bar);
        root.setCenter(paneOfCheckers);

        sceneCheckers = new Scene(root);

        return sceneCheckers;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
