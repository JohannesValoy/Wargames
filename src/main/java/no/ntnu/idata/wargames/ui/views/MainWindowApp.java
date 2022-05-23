package no.ntnu.idata.wargames.ui.views;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import no.ntnu.idata.wargames.logic.controllers.MainWindowAppController;

/**
 * The Main window of the application. in this version we use FXML to define the
 * content of the window.
 *
 * @author johan
 * @version $Id: $Id
 */
public class MainWindowApp extends Application {

  /**
   * <p>main.</p>
   *
   * @param args an array of {@link java.lang.String} objects
   */
  public static void main(String[] args) {
    launch(args);
  }

  /** {@inheritDoc} */
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
      mainWindowAppController.exit();
    });
  }
}
