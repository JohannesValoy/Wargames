//TODO: simulate when army/armies are empty leads to error behind the scenes.
//TODO: Choosing the type of unit made?
//TODO: export to file.
//TODO: Handle exeption in unitfactory when importing file

package no.ntnu.idatx2001.newsstand.ui.controllers;

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
import java.util.ArrayList;
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
    winner.setEditable(false);
    Army armyOne = new Army("ArmyOne");
    Army armyTwo = new Army("ArmyTwo");
    String forest = "Forest";
    String plains = "Plains";
    String hill = "Hills";
    terrainChoice.getItems().addAll(forest, plains, hill);
    terrainChoice.setValue("Terrain");
    try {
      FileController fileController = new FileController();
      this.battle = fileController.retriveBattle();
    } catch (IOException e) {
      this.battle = new Battle(armyOne, armyTwo);
    }


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

  //todo: how to make this work
  public void makeTable() {
    boolean isArmyOne = true;

    TableColumn<Unit, String> nameColumn = this.nameColumnArmyOne;
    TableColumn<Unit, String> healthColumn = healthColumnArmyOne;
    TableView<Unit> c = this.armyOneTableView;
    ObservableList<Unit> d = this.observableArmyOne;
    Army e = battle.getArmyOne();
    if (!isArmyOne) {
      nameColumnArmyOne = nameColumnArmyTwo;
      healthColumn = healthColumnArmyTwo;
      c = this.armyTwoTableView;
      d = this.observableArmyTwo;
      e = battle.getArmyTwo();
    }

    nameColumn.setMinWidth(150);
    nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    healthColumn.setMinWidth(150);
    healthColumn.setCellValueFactory(new PropertyValueFactory<>("health"));

    c.setOnMousePressed(mouseEvent -> {
      if (mouseEvent.isPrimaryButtonDown() && (mouseEvent.getClickCount() == 2)) {
        this.showUnitDetails(null);
      }
    });

    d = FXCollections.observableArrayList(e.getAllUnits());
    c.setItems(d);
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
      case "Hills" -> {
        for(Unit rangedUnit: battle.getArmyOne().getRangedUnits()){
          rangedUnit.setAttackBonus(rangedUnit.getAttackBonus() + 2);

        }for(Unit rangeUnit: battle.getArmyTwo().getRangedUnits()){
          rangeUnit.setAttackBonus(rangeUnit.getAttackBonus() + 2);
        }
      }

      case "Plains" -> {
        for(Unit cavalryUnit: battle.getArmyOne().getCavalryUnits()){
          cavalryUnit.setAttackBonus(cavalryUnit.getAttackBonus() + 3);
        }
        for(Unit cavalryUnit: battle.getArmyTwo().getCavalryUnits()){
          cavalryUnit.setAttackBonus(cavalryUnit.getAttackBonus() + 3);
        }
      }

      case "Forest" -> {
        for(Unit cavalryUnit: battle.getArmyOne().getCavalryUnits()){
          cavalryUnit.setAttackBonus(0);
        }
        for(Unit cavalryUnit: battle.getArmyTwo().getCavalryUnits()){
          cavalryUnit.setAttackBonus(0);
        }
        for(Unit rangedUnit: battle.getArmyOne().getRangedUnits()){
          rangedUnit.setAttackBonus(rangedUnit.getAttackBonus() + 2);
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
    unitDialog(true);
  }

  /**
   * Show details of the selected item.
   *
   * @param actionEvent the event triggering the action
   */
  @FXML
  public void showUnitDetails(ActionEvent actionEvent) {
    unitDialog(false);
  }

  public void unitDialog(boolean editable){
    Unit selectedArmyOne =
            this.armyOneTableView.getSelectionModel().getSelectedItem();

    Unit selectedUnitArmyTwo =
            this.armyTwoTableView.getSelectionModel().getSelectedItem();

    if (selectedArmyOne == null && selectedUnitArmyTwo == null) {
      showPleaseSelectItemDialog();
    } else {
      Unit selectedUnit = null;
      if (selectedArmyOne != null) {
        selectedUnit = selectedArmyOne;
      }
      if (selectedUnitArmyTwo != null) {
        selectedUnit = selectedUnitArmyTwo;
      }
      UnitDetailsDialog npDialog = new UnitDetailsDialog(selectedUnit, editable);
      npDialog.showAndWait();
    }
    updateObservableArmyOne();
    updateObservableArmyTwo();
  }

  //TODO: make this the terrainChooser.
  /**
   * Displays a confirmation dialog with custom actions.
   */
  @FXML
  public void chooseUnitType(ActionEvent actionEvent) throws IOException {
    if(terrainChoice.getSelectionModel().isEmpty()) {
      Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
      alert.setTitle("Choose terrain type");
      alert.setHeaderText("Choose the terrain type, this can be changed in the <Terrain> dropdown menu");
      alert.setContentText("terrain type:");

      ButtonType buttonTypeOne = new ButtonType("Forest");
      ButtonType buttonTypeTwo = new ButtonType("Plains");
      ButtonType buttonTypeThree = new ButtonType("Hills");
      ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

      alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeThree, buttonTypeCancel);

      Optional<ButtonType> result = alert.showAndWait();

      if (result.isPresent()) {
        if (result.get() == buttonTypeOne) {
          terrainChoice.getSelectionModel().select(0);
        } else if (result.get() == buttonTypeTwo) {
          terrainChoice.getSelectionModel().select(1);
        } else if (result.get() == buttonTypeThree) {
          terrainChoice.getSelectionModel().select(2);
          logger.log(Level.INFO, "User chose THREE..");
        } else {
          // ... user chose CANCEL or closed the dialog
          logger.log(Level.INFO, "User chose CANCEL or closed the dialog..");
        }
      }
    }
    simulate(actionEvent);
  }

  /**
   * Exit the application. Displays a confirmation dialog.
   */
  @FXML
  public void exitApplicationdoExitApplication(ActionEvent actionEvent) throws IOException {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Confirmation Dialog");
    alert.setHeaderText("Exit Application ?");
    alert.setContentText("Are you sure you want to exit this application?");

    Optional<ButtonType> result = alert.showAndWait();

    if (result.isPresent()) {
      if (result.get() == ButtonType.OK) {
        FileController fileController = new FileController();
        fileController.saveBattle(battle);
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
    alert.setHeaderText("Wargames");
    alert.setContentText("An application created by\n" + "(C)Johannes Valøy\n" + "v1.0 2022-05-07");

    alert.showAndWait();
  }


  @FXML
  public void export(ActionEvent actionEvent){
    DirectoryChooser directoryChooser = new DirectoryChooser();
    directoryChooser.setTitle("Open Resource File Army One");
    File selectedFolder = directoryChooser.showDialog(null);
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
    File fileArmyOne = openDialog();
    File fileArmyTwo = openDialog();
    SaveToFileRefactored saveToFileRefactored = new SaveToFileRefactored();
    this.battle = new Battle(
            saveToFileRefactored.retrieveArmy(fileArmyOne.getAbsolutePath()),
            saveToFileRefactored.retrieveArmy(fileArmyTwo.getAbsolutePath()));
    this.updateObservableArmyOne();
    this.updateObservableArmyTwo();
  }

  public File openDialog(){
    FileChooser fileChooserArmyTwo = new FileChooser();
    fileChooserArmyTwo.setTitle("Open Resource File Army Two");
    fileChooserArmyTwo.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Text Files", "*.csv"));
    return fileChooserArmyTwo.showOpenDialog(null);
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

  public void selectedArmyTwo() {
    this.armyOneTableView.getSelectionModel().clearSelection();
  }
  public void selectedArmyOne() {
    this.armyTwoTableView.getSelectionModel().clearSelection();
  }

  @FXML
  public void restart(){
      this.battle = new Battle(new Army("ArmyOne"), new Army("ArmyTwo"));
      updateObservableArmyOne();
      updateObservableArmyTwo();
  }
}

