import gui.MainPaneController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Created by Gilles on 30/05/2016.
 */
public final class StartUp extends Application{

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new MainPaneController());
        stage.setScene(scene);

        stage.setOnShown((WindowEvent t) -> {
            stage.setMinWidth(stage.getWidth());
            stage.setMinHeight(stage.getHeight());
        });
        stage.setTitle("DeviousPs editor by Glis - V1.0");
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(StartUp.class, args);
    }
}
