//TODO: simulate when army/armies are empty leads to error behind the scenes.
//TODO: Choosing the type of unit made?
//TODO: export to file.
//TODO: Handle exeption in unitfactory when importing file

package org.johakv.stud.ntnu.no.ui.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.util.Pair;
import no.ntnu.idatx2001.newsstand.model.*;
import no.ntnu.idatx2001.newsstand.ui.views.UnitDetailsDialog;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>The main controller of the application. This controller will be the
 * "glue" between the GUI and the underlying business logic. Hence
 * it is the responsibility of this class to create the instance of the
 * LiteratureRegister-class, and initialize the instance.</p>
 * <p>The class implements the {@code Initializable} interface, which defines
 * the {@code initialize()}-method, replacing the constructor.
 * The {@code initialize()}-method is being called by the JavaFX runtime
 * upon initialization of the GUI, after the GUI-components in the
 * Scenegraph has been created.</p>
 */
public class MainWindowAppController implements Initializable {

  private Army armyOne;
  private Army armyTwo;
  private Battle battle;
  private ObservableList<Unit> observableArmyOne;
  private ObservableList<Unit> observableArmyTwo;
  private Logger logger = Logger.getLogger(getClass().toString());

  @FXML
  private TableView<Unit> armyOneTableView;
  @FXML
  private TableView<Unit> armyTwoTableView;
  @FXML
  private TableColumn<Unit, String> nameColumnArmyOne;
  @FXML
  private TableColumn<Unit, String> nameColumnArmyTwo;
  @FXML
  private TableColumn<Unit, String> healthColumnArmyOne;
  @FXML
  private TableColumn<Unit, String> healthColumnArmyTwo;
  @FXML
  private ChoiceBox<String> terrainChoice;
  @FXML
  private TextField winner;
  @FXML
  private Button open;
  @FXML
  private Button restart;
  @FXML
  private Button export;


  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    // Create the business logic by creating an instance of
    // LiteratureRegister and filling it with dummy data.
    this.armyOne = new Army("ArmyOne");
    this.armyTwo = new Army("ArmyTwo");

    String forest = "Forest";
    String plains = "Plains";
    String hill = "Hills";
    terrainChoice.getItems().addAll(forest, plains, hill);
    SaveToFileRefactored saveToFileRefactored = new SaveToFileRefactored();
    try {
      this.armyOne = saveToFileRefactored.retrieveArmy("ArmyOne.csv");
    } catch (IOException e) {
      e.printStackTrace();
    }

    try {
      this.armyTwo = saveToFileRefactored.retrieveArmy("ArmyTwo.csv");
    } catch (IOException e) {
      e.printStackTrace();
    }
    this.battle = new Battle(armyOne, armyTwo);

    // Finalize the setup of the TableView
    nameColumnArmyOne.setMinWidth(150);
    nameColumnArmyOne.setCellValueFactory(new PropertyValueFactory<>("name"));
    healthColumnArmyOne.setMinWidth(150);
    healthColumnArmyOne.setCellValueFactory(new PropertyValueFactory<>("health"));

    nameColumnArmyTwo.setMinWidth(150);
    nameColumnArmyTwo.setCellValueFactory(new PropertyValueFactory<>("name"));
    healthColumnArmyTwo.setMinWidth(150);
    healthColumnArmyTwo.setCellValueFactory(new PropertyValueFactory<>("health"));

    // Attach action listener
    // Add listener for double click on row
    this.armyOneTableView.setOnMousePressed(mouseEvent -> {
      if (mouseEvent.isPrimaryButtonDown() && (mouseEvent.getClickCount() == 2)) {
        this.showUnitDetails(null);
      }
    });

    this.armyTwoTableView.setOnMousePressed(mouseEvent -> {
      if (mouseEvent.isPrimaryButtonDown() && (mouseEvent.getClickCount() == 2)) {
        this.showUnitDetails(null);
      }
    });

    // Populate the TableView by data from the literature register
    this.observableArmyOne =
        FXCollections.observableArrayList(this.battle.getArmyOne().getAllUnits());
    this.armyOneTableView.setItems(this.observableArmyOne);

    this.observableArmyTwo =
            FXCollections.observableArrayList(this.battle.getArmyTwo().getAllUnits());
    this.armyTwoTableView.setItems(this.observableArmyTwo);
  }

  /**
   * Display the input dialog to get input to create a new Newspaper.
   * If the user confirms creating a new newspaper, a new instance
   * of Newspaper is created and added to the literature register provided.
   *
   * @param actionEvent the event triggering the action
   */
  @FXML
  public void addArmyUnitArmyOne(ActionEvent actionEvent) {

    UnitDetailsDialog npDialog = new UnitDetailsDialog();

    Optional<List<Unit>> result = npDialog.showAndWait();

    if (result.isPresent()) {
      List<Unit> newArmyUnit = result.get();
      for (Unit unit: newArmyUnit) {
        this.battle.getArmyOne().add(unit);
      }
        this.updateObservableArmyOne();
    }
  }

  @FXML
  public void addArmyUnitArmyTwo(ActionEvent actionEvent) {

    UnitDetailsDialog npDialog = new UnitDetailsDialog();
    Optional<List<Unit>> result = npDialog.showAndWait();

    if (result.isPresent()) {
      List<Unit> newArmyUnit = result.get();
      for (Unit unit: newArmyUnit) {
        this.battle.getArmyTwo().add(unit);
      }
      this.updateObservableArmyTwo();
    }
  }

  /**
   * Deletes the literature selected in the table. If no literature is
   * selected, nothing is deleted, and the user is informed that he/she must
   * select which literature to delete.
   *
   * @param actionEvent the event triggering the action
   */
  @FXML
  public void deleteArmyUnit(ActionEvent actionEvent) {
    Unit selectedArmyOne =
            this.armyOneTableView.getSelectionModel().getSelectedItem();
    Unit selectedArmyTwo =
            this.armyTwoTableView.getSelectionModel().getSelectedItem();

    if (selectedArmyOne == null && selectedArmyTwo == null) {
      showPleaseSelectItemDialog();
    } else {
      if (showDeleteConfirmationDialog()) {
        if(selectedArmyOne != null){
        this.battle.getArmyOne().remove(selectedArmyOne);
        this.updateObservableArmyOne();}
      }
      if(selectedArmyTwo != null){
        this.battle.getArmyTwo().remove(selectedArmyTwo);
        this.updateObservableArmyTwo();}
      }

    }

  @FXML
  public void simulate(ActionEvent actionEvent) throws IOException {
    winner.setText(this.battle.simulate().getName());
    String terrainType = terrainChoice.getSelectionModel().getSelectedItem();
    switch (terrainType) {
      case "Hill" -> {
        for(Unit rangeUnit: battle.getArmyOne().getRangedUnits()){
          rangeUnit.setAttackBonus(rangeUnit.getAttackBonus() + 5);

        }for(Unit rangeUnit: battle.getArmyTwo().getRangedUnits()){
          rangeUnit.setAttackBonus(rangeUnit.getAttackBonus() + 5);
        }
      }

      case "Plains" -> {
        for(Unit cavalryUnit: battle.getArmyOne().getCavalryUnits()){
          cavalryUnit.setAttackBonus(cavalryUnit.getAttackBonus() + 5);
        }
        for(Unit cavalryUnit: battle.getArmyTwo().getCavalryUnits()){
          cavalryUnit.setAttackBonus(cavalryUnit.getAttackBonus() + 5);
        }
      }

      case "Forest" -> {
        for(Unit cavalryUnit: battle.getArmyOne().getCavalryUnits()){
          cavalryUnit.setAttackBonus(0);
        }
        for(Unit cavalryUnit: battle.getArmyTwo().getCavalryUnits()){
          cavalryUnit.setAttackBonus(0);
        }
        for(Unit rangeUnit: battle.getArmyOne().getRangedUnits()){
          rangeUnit.setAttackBonus(rangeUnit.getAttackBonus() + 2);
        }
        for(Unit rangeUnit: battle.getArmyTwo().getRangedUnits()){
          rangeUnit.setAttackBonus(rangeUnit.getAttackBonus() + 2);
        }
        for(Unit infantryUnit: battle.getArmyOne().getInfantryUnits()){
          infantryUnit.setAttackBonus(infantryUnit.getAttackBonus() + 5);
          infantryUnit.setArmor(infantryUnit.getArmor() + 5);
        }
        for(Unit infantryUnit: battle.getArmyTwo().getInfantryUnits()){
          infantryUnit.setAttackBonus(infantryUnit.getAttackBonus() + 5);
          infantryUnit.setArmor(infantryUnit.getArmor() + 5);
        }
      }
      default -> throw new IOException();
    }
    this.updateObservableArmyOne();
    this.updateObservableArmyTwo();

  }
  /**
   * Edit the selected item.
   *
   * @param actionEvent the event triggering the action
   */
  @FXML
  public void editUnit(ActionEvent actionEvent) {
    Unit selectedUnitArmyOne =
            this.armyOneTableView.getSelectionModel().getSelectedItem();

    Unit selectedUnitArmyTwo =
            this.armyTwoTableView.getSelectionModel().getSelectedItem();
    
    if (selectedUnitArmyOne == null && selectedUnitArmyTwo == null) {
      showPleaseSelectItemDialog();
    } else {
      if (selectedUnitArmyOne instanceof Unit) {
        Unit selectedUnit = selectedUnitArmyOne;
        UnitDetailsDialog npDialog = new UnitDetailsDialog(selectedUnit, true);
        npDialog.showAndWait();

        this.updateObservableArmyOne();
      }
      if (selectedUnitArmyTwo instanceof Unit) {
        Unit selectedUnit = selectedUnitArmyTwo;
        UnitDetailsDialog npDialog = new UnitDetailsDialog(selectedUnit, true);
        npDialog.showAndWait();

        this.updateObservableArmyTwo();
      }
    }
  }

  /**
   * Show details of the selected item.
   *
   * @param actionEvent the event triggering the action
   */
  @FXML
  public void showUnitDetails(ActionEvent actionEvent) {
    Unit selectedArmyOne =
            this.armyOneTableView.getSelectionModel().getSelectedItem();

    Unit selectedUnitArmyTwo =
            this.armyTwoTableView.getSelectionModel().getSelectedItem();

    if (selectedArmyOne == null && selectedUnitArmyTwo == null) {
      showPleaseSelectItemDialog();
    } else {

      if (selectedArmyOne instanceof Unit) {
        Unit selectedUnit = selectedArmyOne;
        UnitDetailsDialog npDialog = new UnitDetailsDialog(selectedUnit, false);
        npDialog.showAndWait();
      }

      if (selectedUnitArmyTwo instanceof Unit) {
        Unit selectedUnit = selectedUnitArmyTwo;
        UnitDetailsDialog npDialog = new UnitDetailsDialog(selectedUnit, false);
        npDialog.showAndWait();
      }
    }
  }

  //TODO: make this the terrainChooser.
  /**
   * Displays a confirmation dialog with custom actions.
   */
  @FXML
  public void chooseUnitType(ActionEvent actionEvent) {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Choose unit type");
    alert.setHeaderText("Choose the unit type");
    alert.setContentText("unit type:");

    ButtonType buttonTypeOne = new ButtonType("Cavalry Unit");
    ButtonType buttonTypeTwo = new ButtonType("Commander Unit");
    ButtonType buttonTypeThree = new ButtonType("Infantry Unit");
    ButtonType buttonTypeFour = new ButtonType("Ranged Unit");
    ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

    alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeThree, buttonTypeCancel);

    Optional<ButtonType> result = alert.showAndWait();

    if (result.isPresent()) {
      if (result.get() == buttonTypeOne) {
        // ... user chose "One"
        logger.log(Level.INFO, "User chose ONE..");
      } else if (result.get() == buttonTypeTwo) {
        // ... user chose "Two"
        logger.log(Level.INFO, "User chose TWO..");
      } else if (result.get() == buttonTypeThree) {
        // ... user chose "Three"
        logger.log(Level.INFO, "User chose THREE..");
      } else if (result.get() == buttonTypeFour) {
        // ... user chose "Three"
        logger.log(Level.INFO, "User chose Four..");
      } else {
        // ... user chose CANCEL or closed the dialog
        logger.log(Level.INFO, "User chose CANCEL or closed the dialog..");
      }
    }
  }

  /**
   * Exit the application. Displays a confirmation dialog.
   */
  @FXML
  public void exitApplicationdoExitApplication(ActionEvent actionEvent) {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Confirmation Dialog");
    alert.setHeaderText("Exit Application ?");
    alert.setContentText("Are you sure you want to exit this application?");

    Optional<ButtonType> result = alert.showAndWait();

    if (result.isPresent()) {
      if (result.get() == ButtonType.OK) {
        SaveToFileRefactored saveToFileRefactored = new SaveToFileRefactored();
        saveToFileRefactored.saveArmy(battle.getArmyOne(),0,"ArmyOne.csv");
        saveToFileRefactored.saveArmy(battle.getArmyTwo(),1,"ArmyTwo.csv");
        Platform.exit();
      } else {
        // ... user chose CANCEL or closed the dialog
        // then do nothing.
      }
    }
  }

  /**
   * Displays an example of an alert (info) dialog. In this case an "about"
   * type of dialog.
   */
  @FXML
  public void showAboutDialog(ActionEvent actionEvent) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Information Dialog - About");
    alert.setHeaderText("Literature Register");
    alert.setContentText("A brilliant application created by\n"
        + "(C)Johannes Valøy\n"
        + "v0.1 2016-04-21");

    alert.showAndWait();
  }

  /**
   * Displays a login dialog using a custom dialog.
   */
  @FXML
  public void showLogInDialog(ActionEvent actionEvent) {
    // Create the custom dialog.
    Dialog<Pair<String, String>> dialog = new Dialog<>();
    dialog.setTitle("Login Dialog");
    dialog.setHeaderText("Look, a Custom Login Dialog");

    // Set the button types.
    ButtonType loginButtonType = new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);
    dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

    // Create the username and password labels and fields.
    GridPane grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(20, 150, 10, 10));

    TextField username = new TextField();
    username.setPromptText("Username");

    PasswordField password = new PasswordField();
    password.setPromptText("Password");

    grid.add(new Label("Username:"), 0, 0);
    grid.add(username, 1, 0);
    grid.add(new Label("Password:"), 0, 1);
    grid.add(password, 1, 1);

    // Enable/Disable login button depending on whether a username was entered.
    Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
    loginButton.setDisable(true);

    // Do some validation .
    username.textProperty().addListener((observable, oldValue, newValue) ->
        loginButton.setDisable(newValue.trim().isEmpty()));

    dialog.getDialogPane().setContent(grid);

    // Request focus on the username field by default.
    Platform.runLater(username::requestFocus);

    // Convert the result to a username-password-pair when the login button is clicked.
    dialog.setResultConverter(
        dialogButton -> {
          if (dialogButton == loginButtonType) {
            return new Pair<>(username.getText(), password.getText());
          }
          return null;
        });

    Optional<Pair<String, String>> result = dialog.showAndWait();

    result.ifPresent(
        usernamePassword -> logger.info("Username=" + usernamePassword.getKey()
            + " Password=" + usernamePassword.getValue()));
  }

  @FXML
  public void export(ActionEvent actionEvent){
    DirectoryChooser directoryChooser = new DirectoryChooser();
    directoryChooser.setTitle("Open Resource File Army One");
    File selectedFolder = directoryChooser.showDialog(null);
    System.out.println(selectedFolder.getAbsolutePath());
    SaveToFileRefactored saveToFileRefactored = new SaveToFileRefactored();
    saveToFileRefactored.saveArmy(battle.getArmyOne(), 0, selectedFolder.getAbsolutePath() + "\\ArmyOne.csv");
    saveToFileRefactored.saveArmy(battle.getArmyTwo(), 0, selectedFolder.getAbsolutePath() + "\\ArmyTwo.csv");

  }
  /**
   * Displays the file chooser dialog. Nothing is done with the file chosen
   * by the user. For demo-purposes only.
   *
   */
  @FXML
  public void open(ActionEvent actionEvent) throws IOException {
    FileChooser fileChooserArmyOne = new FileChooser();
    fileChooserArmyOne.setTitle("Open Resource File Army One");
    fileChooserArmyOne.getExtensionFilters().addAll(
        new FileChooser.ExtensionFilter("Text Files", "*.csv"));
    File selectedFileArmyOne = fileChooserArmyOne.showOpenDialog(null);

    FileChooser fileChooserArmyTwo = new FileChooser();
    fileChooserArmyTwo.setTitle("Open Resource File Army Two");
    fileChooserArmyTwo.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Text Files", "*.csv"));
    File selectedFileArmyTwo = fileChooserArmyTwo.showOpenDialog(null);

    SaveToFileRefactored saveToFileRefactored = new SaveToFileRefactored();
    this.battle = new Battle(
            saveToFileRefactored.retrieveArmy(selectedFileArmyOne.getAbsolutePath()),
            saveToFileRefactored.retrieveArmy(selectedFileArmyTwo.getAbsolutePath()));

    if (selectedFileArmyOne != null) {
      logger.log(Level.INFO, selectedFileArmyOne.getAbsolutePath());
    }
    if (selectedFileArmyTwo != null) {
      logger.log(Level.INFO, selectedFileArmyTwo.getAbsolutePath());
    }
    this.updateObservableArmyOne();
    this.updateObservableArmyTwo();

  }
  /**
   * Updates the observable literature register by replacing the entire content
   * by the current content in the literature register.
   * Method to be called whenever changes are made to the literature register.
   */
  private void updateObservableArmyOne() {
    this.observableArmyOne.setAll(this.battle.getArmyOne().getAllUnits());
  }

  private void updateObservableArmyTwo() {
    this.observableArmyTwo.setAll(this.battle.getArmyTwo().getAllUnits());
  }

  /**
   * TODO: remove this dummy funtcion.
   * Fills the register with dummy data.
   */
  private void fillRegisterWithDummyData() {
    this.battle.getArmyOne().add(new CavalryUnit("Cavalry Unit One", 1, 123, 305));
    this.battle.getArmyOne().add(new CavalryUnit("Cavalry Unit Two", 1, 204, 305));
    this.battle.getArmyOne().add(new CavalryUnit("Cavalry Unit Three", 1, 195, 305));
    this.battle.getArmyOne().add(new CommanderUnit("Cavalry Unit Four", 1, 12, 305));
    this.battle.getArmyTwo().add(new CavalryUnit("Cavalry Unit Five", 1, 123, 305));
    this.battle.getArmyTwo().add(new CavalryUnit("Cavalry Unit Six", 1, 204, 305));
    this.battle.getArmyTwo().add(new CommanderUnit("Cavalry Unit Seven", 1, 195, 305));
    this.battle.getArmyTwo().add(new CavalryUnit("Cavalry Unit Eight", 1, 12, 305));
  }

  // -----------------------------------------------------------
  //    DIALOGS
  // -----------------------------------------------------------

  /**
   * Displays a delete confirmation dialog. If the user confirms the delete,
   * <code>true</code> is returned.
   *
   * @return <code>true</code> if the user confirms the delete
   */
  private boolean showDeleteConfirmationDialog() {
    boolean deleteConfirmed = false;

    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Delete confirmation");
    alert.setHeaderText("Delete confirmation");
    alert.setContentText("Are you sure you want to delete this item?");

    Optional<ButtonType> result = alert.showAndWait();

    if (result.isPresent()) {
      deleteConfirmed = (result.get() == ButtonType.OK);
    }
    return deleteConfirmed;
  }


  /**
   * Displays a warning informing the user that an item must be selected from
   * the table.
   */
  private void showPleaseSelectItemDialog() {
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setTitle("Information");
    alert.setHeaderText("No items selected");
    alert.setContentText("No item is selected from the table.\n"
        + "Please select an item from the table.");

    alert.showAndWait();
  }

  public void selectedArmyTwo(MouseEvent mouseEvent) {
    this.armyOneTableView.getSelectionModel().clearSelection();
  }
  public void selectedArmyOne(MouseEvent mouseEvent) {
    this.armyTwoTableView.getSelectionModel().clearSelection();
  }

  @FXML
  public void restart(){
      this.battle = new Battle(new Army("ArmyOne"), new Army("ArmyTwo"));
    System.out.println(battle.getArmyTwo().hasUnits());
      updateObservableArmyOne();
      updateObservableArmyTwo();
  }
}

