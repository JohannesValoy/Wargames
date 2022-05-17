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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import no.ntnu.idatx2001.newsstand.model.*;
import no.ntnu.idatx2001.newsstand.ui.views.UnitDetailsDialog;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>The main controller of the application. This controller will be the
 * "glue" between the GUI and the underlying business logic. Hence
 * it is the responsibility of this class to create the instance of the
 * Battle-class, and initialize the instance.</p>
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
  private String terrain;
  private final Logger logger = Logger.getLogger(getClass().toString());

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
  private TextField winner;


  /**
   * Initializes the table-view UI-element, and retrieves the battle from saved file if available.
   * Initializes observableList observableArmyOne and observableList observableArmyTwo for use in the table-view.
   * @param url TODO research and add Comment Here.
   * @param resourceBundle TODO research and add Comment Here.
   */
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    winner.setEditable(false);
    try {
      FileController fileController = new FileController();
      this.battle = fileController.retrieveBattle();
    } catch (IOException e) {
      this.battle = new Battle(new Army("ArmyOne"), new Army("ArmyTwo"));
    }
    this.terrain = null;
    // Finalize the setup of the TableView for armyOne and armyTwo.
    nameColumnArmyOne.setCellValueFactory(new PropertyValueFactory<>("name"));
    healthColumnArmyOne.setCellValueFactory(new PropertyValueFactory<>("health"));

    nameColumnArmyTwo.setCellValueFactory(new PropertyValueFactory<>("name"));
    healthColumnArmyTwo.setCellValueFactory(new PropertyValueFactory<>("health"));

    // Attach action listener
    // Add listener for double-click on row
    this.armyOneTableView.setOnMousePressed(mouseEvent -> {
      if (mouseEvent.isPrimaryButtonDown() && (mouseEvent.getClickCount() == 2)) {
        this.showUnitDetails();
      }
    });

    this.armyTwoTableView.setOnMousePressed(mouseEvent -> {
      if (mouseEvent.isPrimaryButtonDown() && (mouseEvent.getClickCount() == 2)) {
        this.showUnitDetails();
      }
    });

    // Populate the TableView by data from the battle-Class
    this.observableArmyOne =
        FXCollections.observableArrayList(this.battle.getArmyOne().getAllUnits());
    this.armyOneTableView.setItems(this.observableArmyOne);

    this.observableArmyTwo =
            FXCollections.observableArrayList(this.battle.getArmyTwo().getAllUnits());
    this.armyTwoTableView.setItems(this.observableArmyTwo);
  }

  /**
   * Display the input dialog to get input to create a new Unit in armyOne.
   * If the user confirms creating a new unit, a new instance
   * of Unit is created and added to armyOne battle-class.
   */

  //The methods for armyOne and armyTwo is split in two,
  // as they update two different table-views.
  @FXML
  public void addArmyUnitArmyOne() {

    UnitDetailsDialog npDialog = new UnitDetailsDialog();

    Optional<List<Unit>> result = npDialog.showAndWait();

    if (result.isPresent()) {
      List<Unit> newArmyUnit = result.get();
      for (Unit unit: newArmyUnit) {
        this.battle.getArmyOne().add(unit);
      }
        this.updateObservableArmyOne();
    }
    terrainChoiceCase();
  }

  /**
   * Display the input dialog to get input to create a new Unit in armyTwo.
   * If the user confirms creating a new unit, a new instance
   * of Unit is created and added to armyTwo battle-class.
   */

  @FXML
  public void addArmyUnitArmyTwo() {

    UnitDetailsDialog npDialog = new UnitDetailsDialog();
    Optional<List<Unit>> result = npDialog.showAndWait();

    if (result.isPresent()) {
      List<Unit> newArmyUnit = result.get();
      for (Unit unit: newArmyUnit) {
        this.battle.getArmyTwo().add(unit);
      }
      this.updateObservableArmyTwo();
    }
    terrainChoiceCase();
  }

  /**
   * Deletes the Unit selected in the table. If no Unit is
   * selected, nothing is deleted, and the user is informed that he/she must
   * select which Unit to delete.
   *
   */
  @FXML
  public void deleteArmyUnit() {
    // selected in armyOne table-view.
    Unit selectedArmyOne =
            this.armyOneTableView.getSelectionModel().getSelectedItem();

    // selected in armyTwo table-view.
    Unit selectedArmyTwo =
            this.armyTwoTableView.getSelectionModel().getSelectedItem();

    if (selectedArmyOne == null && selectedArmyTwo == null) {
      showPleaseSelectItemDialog();
    } else {
      // armyOne
      if (showDeleteConfirmationDialog() && selectedArmyOne != null) {
        this.battle.getArmyOne().remove(selectedArmyOne);
        this.updateObservableArmyOne();
      }
      // armyTwo
      if(showDeleteConfirmationDialog() && selectedArmyTwo != null){
        this.battle.getArmyTwo().remove(selectedArmyTwo);
        this.updateObservableArmyTwo();}
      }
    }


  /**
   * If the terrain is chosen, it starts the simulation.
   * If the terrain is null, it will not do anything.
   */
  @FXML
  public void simulate() {
    if(terrain != null) {
      winner.setText(this.battle.simulate().getName());
      updateObservableArmyOne();
      updateObservableArmyTwo();
    }
  }

  /**
   * To be called when the terrain 'Hills' is chosen.
   */
  public void onHills() {
    this.terrain = "Hills";
    terrainChoiceCase();
  }

  /**
   * To be called when the terrain 'Plains' is chosen.
   */
  public void onPlains() {
    this.terrain = "Plains";
    terrainChoiceCase();
  }

  /**
   * To be called when the terrain 'Forest' is chosen.
   */
  public void onForest() {
    this.terrain = "Forest";
    terrainChoiceCase();

  }

  /**
   * Adds resistBonus and attackBonus the Cavalry, Commander, Infantry and Ranged-Units in the battle-class.
   * If the set terrain is not 'Hills', 'Plains', 'Forest' or null, there will ve thrown an IllegalArgumentException to the terminal.
   * TODO make Exception to logger.log.
   */
  public void terrainChoiceCase() {
    Army army = battle.getArmyOne();
    int i = 0;

    while(i < 2) {
      for (Unit unit : army.getAllUnits()) {
        unit.addAttackBonus(0);
        unit.addResistBonus(0);
      }
      if(terrain != null){
      switch (terrain) {
        case "Hills" -> army.getAllUnits().stream().filter(RangedUnit.class::isInstance).forEach(unit -> unit.addAttackBonus(2));

        case "Plains" -> army.getAllUnits().stream().filter(CavalryUnit.class::isInstance).forEach(unit -> unit.addAttackBonus(3));

        case "Forest" -> {
          army.getAllUnits().stream().filter(RangedUnit.class::isInstance).forEach(unit -> unit.addAttackBonus(2));
          army.getAllUnits().stream().filter(InfantryUnit.class::isInstance).forEach(unit -> unit.addAttackBonus(5));
          army.getAllUnits().stream().filter(RangedUnit.class::isInstance).forEach(unit -> unit.addResistBonus(5));
        }
        default -> throw new IllegalArgumentException("Terrain must either be 'Hills', 'Plains', 'Forest' or null");
      }
      }
      i++;

      this.updateObservableArmyOne();
      this.updateObservableArmyTwo();
      army.getAllUnits().stream().forEach(unit -> System.out.println(unit.getResistBonus()));
      army.getAllUnits().stream().forEach(unit -> System.out.println(unit.getAttackBonus()));
      army = battle.getArmyTwo();
    }
  }

  /**
   * Edit the selected item.
   *
   */
  @FXML
  public void editUnit() {
    unitDialog(true);
  }

  /**
   * Show details of the selected item.
   *
   */
  @FXML
  public void showUnitDetails() {
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
  public void chooseTerrainType() {
    if(terrain == null) {
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
          terrain = "Forest";
          terrainChoiceCase();
        } else if (result.get() == buttonTypeTwo) {
          terrain = "Plains";
          terrainChoiceCase();
        } else if (result.get() == buttonTypeThree) {
          terrain = "Hills";
          terrainChoiceCase();
          logger.log(Level.INFO, "User chose THREE..");
        } else {
          // ... user chose CANCEL or closed the dialog
          logger.log(Level.INFO, "User chose CANCEL or closed the dialog..");
        }
      }
    }
    simulate();
  }


  @FXML
  public void save() throws IOException {
    FileController fileController = new FileController();
    fileController.saveBattle(battle);
  }
  /**
   * TODO: make filecontroller in the initialize or the contstructor. this.filecontroller.
   * Exit the application. Displays a confirmation dialog.
   */
  @FXML
  public void exit() throws IOException {
    FileController fileController = new FileController();
    fileController.saveBattle(battle);
    Platform.exit();
    /**
     * Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
     *     alert.setTitle("Confirmation Dialog");
     *     alert.setHeaderText("Exit Application ?");
     *     alert.setContentText("Are you sure you want to exit this application?");
     *
     *     Optional<ButtonType> result = alert.showAndWait();
     *     if (result.isPresent()) {
     *       if (result.get() == ButtonType.OK) {
     *         ----------------------- code moved to the topp.
     *       } else {
     *         // ... user chose CANCEL or closed the dialog
     *         // then do nothing.
     *       }
     *     }
     */



  }

  /**
   * Displays an example of an alert (info) dialog. In this case an "about"
   * type of dialog.
   */
  @FXML
  public void showAboutDialog() {
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
  public void open() {
    File fileArmyOne = openDialog();
    File fileArmyTwo = openDialog();
    SaveToFileRefactored saveToFileRefactored = new SaveToFileRefactored();
    this.battle = new Battle(
            saveToFileRefactored.retrieveArmy(fileArmyOne.getAbsolutePath()),
            saveToFileRefactored.retrieveArmy(fileArmyTwo.getAbsolutePath()));
    this.updateObservableArmyOne();
    this.updateObservableArmyTwo();
  }

  @FXML
  public void resetSession() throws IOException {
    this.battle = new FileController().retrieveBattle();
    updateObservableArmyOne();
    updateObservableArmyTwo();
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


  // -----------------------------------------------------------
  //    DIALOGS
  // -----------------------------------------------------------

  public File openDialog(){
    FileChooser fileChooserArmyTwo = new FileChooser();
    fileChooserArmyTwo.setTitle("Open Resource File Army Two");
    fileChooserArmyTwo.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Text Files", "*.csv"));
    return fileChooserArmyTwo.showOpenDialog(null);
  }

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


}

