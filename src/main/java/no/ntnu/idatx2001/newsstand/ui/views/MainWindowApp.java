package no.ntnu.idatx2001.newsstand.ui.views;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import no.ntnu.idatx2001.newsstand.ui.controllers.MainWindowAppController;

/**
 * The Main window of the application. in this version we use FXML to define the
 * content of the window.
 */
public class MainWindowApp extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws IOException {
    // Load the scene graph from the FXML-file. The file should be placed in the same
    // folder/package as the MainWindowApp-class
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainWindowApp.fxml"));
    Parent root = fxmlLoader.load();

    Scene scene = new Scene(root);

    primaryStage.setScene(scene);
    primaryStage.show();
    primaryStage.setOnCloseRequest(event -> {
      MainWindowAppController mainWindowAppController = fxmlLoader.getController();
      try {
        mainWindowAppController.exit();
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
  }
}
