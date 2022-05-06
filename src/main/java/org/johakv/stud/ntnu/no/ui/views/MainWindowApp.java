package org.johakv.stud.ntnu.no.ui.views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

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
  }
}
