//TODO: simulate when army/armies are empty leads to error behind the scenes.
//TODO: Choosing the type of unit made?
//TODO: export to file.
//TODO: Handle exeption in unitfactory when importing file

package no.ntnu.idata2001.wargames.ui.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import no.ntnu.idata2001.wargames.controllers.FileController;
import no.ntnu.idata2001.wargames.model.*;
import no.ntnu.idata2001.wargames.ui.views.UnitDetailsDialog;
import no.ntnu.idata2001.wargames.controllers.CSVController;
import no.ntnu.idata2001.wargames.units.CavalryUnit;
import no.ntnu.idata2001.wargames.units.InfantryUnit;
import no.ntnu.idata2001.wargames.units.RangedUnit;
import no.ntnu.idata2001.wargames.units.Unit;

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
 *
 * @author johan
 * @version $Id: $Id
 */
public class MainWindowAppController implements Initializable {


  private Battle battle;
  private ObservableList<Unit> observableArmyOne;
  private ObservableList<Unit> observableArmyTwo;
  private String terrain;

  @FXML
  private Label numberOfUnitsArmyTwoLabel;
  @FXML
  private Label fileArmyTwoLabel;
  @FXML
  private Label armyTwoNameLabel;
  @FXML
  private Label numberOfUnitsArmyOneLabel;
  @FXML
  private Label fileArmyOneLabel;
  @FXML
  private Label armyOneNameLabel;
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
   * {@inheritDoc}
   *
   * Initializes the table-view UI-element, and retrieves the battle from saved file if available.
   * Initializes observableList observableArmyOne and observableList observableArmyTwo for use in the table-view.
   */
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    winner.setEditable(false);
    this.terrain = null;

    //Retrieve battle from memory-file.
    try {
      FileController fileController = new FileController();
      this.battle = fileController.retrieveBattle();
    } catch (IOException e) {
      showErrorDialog("Memory File", "Memory-" + e.getMessage(), "Both armies will be reset");
      this.battle = new Battle(new Army("ArmyOne"), new Army("ArmyTwo"));
    }


    //TableView
    // Finalize the setup of the TableView for armyOne and armyTwo.
    nameColumnArmyOne.setCellValueFactory(new PropertyValueFactory<>("name"));
    healthColumnArmyOne.setCellValueFactory(new PropertyValueFactory<>("health"));
    nameColumnArmyTwo.setCellValueFactory(new PropertyValueFactory<>("name"));
    healthColumnArmyTwo.setCellValueFactory(new PropertyValueFactory<>("health"));

    // Attach action listener.
    // Add listener for double-click on row.
    this.armyOneTableView.setOnMousePressed(mouseEvent -> {
      if (mouseEvent.isPrimaryButtonDown() && (mouseEvent.getClickCount() == 2)) {
        this.showUnitDetails();
      }
    });

    // Attach action listener.
    // Add listener for double-click on row.
    this.armyTwoTableView.setOnMousePressed(mouseEvent -> {
      if (mouseEvent.isPrimaryButtonDown() && (mouseEvent.getClickCount() == 2)) {
        this.showUnitDetails();
      }
    });

    // Populate the armyOne-TableView by data from the battle-Class.
    this.observableArmyOne =
        FXCollections.observableArrayList(this.battle.getArmyOne().getAllUnits());
    this.armyOneTableView.setItems(this.observableArmyOne);
    //Updates parameters:
    updateObservableArmyOne();

    // Populate the armyTwo-TableView by data from the battle-Class.
    this.observableArmyTwo =
            FXCollections.observableArrayList(this.battle.getArmyTwo().getAllUnits());
    this.armyTwoTableView.setItems(this.observableArmyTwo);
    //Updates parameters:
    updateObservableArmyTwo();
  }


  /**
   * Display the input dialog to get input to create a new Unit in armyOne.
   * If the user confirms creating a new unit, a new instance
   * of Unit is created and added to armyOne battle-class.
   * Checks if there is to many units added and displays a dialog if the amounts allowed are surpassed.
   */

  @FXML
  public void addArmyUnitArmyOne() {
    //If-sentence, tells the user, it´s not possible to add more units, if units are not less than 999,
    // if not, the showTooManyUnitsDialog is called
    if(battle.getArmyOne().getAllUnits().size() < 999){

      //Initializes the dialog to add Units.
    UnitDetailsDialog npDialog = new UnitDetailsDialog();
    Optional<List<Unit>> result = npDialog.showAndWait();
    if (result.isPresent()) {
      List<Unit> newArmyUnit = result.get();
      //Checks if the sum of units in army and units added is less than 1000,
      // if not the showTooManyUnitsDialog is called
      if(newArmyUnit.size() + this.battle.getArmyOne().getAllUnits().size() < 1000){
        //Adds Units to army.
      for (Unit unit: newArmyUnit) {
        this.battle.getArmyOne().add(unit);
      }
      } else {showTooManyUnitsDialog();}
        this.updateObservableArmyOne();
    }
    terrainController();
    } else {showTooManyUnitsDialog();}
  }

  //The methods for armyOne and armyTwo is split in two,
  // as they update two different table-views.
  /**
   * Display the input dialog to get input to create a new Unit in armyTwo.
   * If the user confirms creating a new unit, a new instance
   * of Unit is created and added to armyTwo battle-class.
   * Checks if there is to many units added and displays a dialog if the amounts allowed are surpassed.
   */

  @FXML
  public void addArmyUnitArmyTwo() {
    //If-sentence, tells the user, it´s not possible to add more units, if units are not less than 999,
    // if not, the showTooManyUnitsDialog is called
    if(battle.getArmyTwo().getAllUnits().size() < 999){

      //Initializes the dialog to add Units.
      UnitDetailsDialog npDialog = new UnitDetailsDialog();
      Optional<List<Unit>> result = npDialog.showAndWait();
      if (result.isPresent()) {
        List<Unit> newArmyUnit = result.get();
        //Checks if the sum of units in army and units added is less than 1000,
        // if not the showTooManyUnitsDialog is called
        if(newArmyUnit.size() + this.battle.getArmyTwo().getAllUnits().size() < 1000){
          //Adds Units to army.
          for (Unit unit: newArmyUnit) {
            this.battle.getArmyTwo().add(unit);
          }
        } else {showTooManyUnitsDialog();}
        this.updateObservableArmyTwo();
      }
      terrainController();
    } else {showTooManyUnitsDialog();}
  }

  /**
   * Deletes the Unit selected in the table. If no Unit is
   * selected, nothing is deleted, and the user is informed that he/she must
   * select which Unit to delete.
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
      showErrorDialog("Unit Selection Error", "No Unit Selected", "Please Select a Unit from one of the Army-Tables");
    } else {
      boolean delete = showDeleteConfirmationDialog();
      // armyOne
      if (delete && selectedArmyOne != null) {
        this.battle.getArmyOne().remove(selectedArmyOne);
        this.updateObservableArmyOne();
      }
      // armyTwo
      if(delete && selectedArmyTwo != null){
        this.battle.getArmyTwo().remove(selectedArmyTwo);
        this.updateObservableArmyTwo();}
      }
    }


  /**
   * If the terrain is chosen, it starts the simulation.
   * If the terrain is null, it will not do anything.
   */
  @FXML
  public void simulate(){
    if(battle.getArmyOne().hasUnits() && battle.getArmyTwo().hasUnits()){
      try{
        save();
      } catch (IOException e){
        showErrorDialog("Error", "Error - " + e.getMessage(), "Error saving file, please export it before closing the program");
      }
    if(terrain != null) {
      winner.setText(this.battle.simulate().getName());
      updateObservableArmyOne();
      updateObservableArmyTwo();
    } else {chooseTerrainType();}
    } else {
      showErrorDialog("Army out of units", "Both armies needs to have units for the simulation to start",
              "Please add units to both armies or import them from .csv-file");
    }
  }

  /**
   * To be called when the terrain 'Hills' is chosen.
   */
  public void onHills() {
    this.terrain = "Hills";
    terrainController();
  }

  /**
   * To be called when the terrain 'Plains' is chosen.
   */
  public void onPlains() {
    this.terrain = "Plains";
    terrainController();
  }

  /**
   * To be called when the terrain 'Forest' is chosen.
   */
  public void onForest() {
    this.terrain = "Forest";
    terrainController();

  }


  /**
   * Displays a confirmation dialog for choosing the terrain if the user have not already chosen terrain .
   */
  @FXML
  public void chooseTerrainType(){
    if(terrain == null) {
      Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
      alert.setTitle("Choose terrain type");
      alert.setHeaderText("No terrain chosen");
      alert.setContentText("Choose the terrain type, this can be changed in the <Terrain> dropdown menu");

      ButtonType buttonTypeOne = new ButtonType("Forest");
      ButtonType buttonTypeTwo = new ButtonType("Plains");
      ButtonType buttonTypeThree = new ButtonType("Hills");
      ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

      alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeThree, buttonTypeCancel);

      Optional<ButtonType> result = alert.showAndWait();

      if (result.isPresent()) {
        if (result.get() == buttonTypeOne) {
          terrain = "Forest";
          terrainController();
        } else if (result.get() == buttonTypeTwo) {
          terrain = "Plains";
          terrainController();
        } else if (result.get() == buttonTypeThree) {
          terrain = "Hills";
        }
      }
    }
    simulate();
  }


  /**
   * Adds resistBonus and attackBonus the Cavalry, Commander, Infantry and Ranged-Units in the battle-class.
   * If the set terrain is not 'Hills', 'Plains', 'Forest' or null, there will ve thrown an IllegalArgumentException to the terminal.
   */
  public void terrainController() {
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
        //Error handling
        default -> showErrorDialog("Error","Terrain must either be 'Hills', 'Plains', 'Forest' or null", "Internal Error, " + terrain + " is not available");
      }
      }
      i++;

      //Updates tables
      this.updateObservableArmyOne();
      this.updateObservableArmyTwo();
      army = battle.getArmyTwo();
    }
  }

  /**
   * Edit the selected unit.
   */
  @FXML
  public void editUnit() {
    unitDialog(true);
  }

  /**
   * Show details of the selected unit.
   */
  @FXML
  public void showUnitDetails() {
    unitDialog(false);
  }

  /**
   * unit dialog with boolean editable shows details about the units, edits units or adds unit.
   * If unit not given it will show new Unit dialog, if unit given, its editable if boolean set to editable.
   *
   * @param editable true if unit should be editable false is not.
   */
  public void unitDialog(boolean editable){
    Unit selectedArmyOne =
            this.armyOneTableView.getSelectionModel().getSelectedItem();

    Unit selectedUnitArmyTwo =
            this.armyTwoTableView.getSelectionModel().getSelectedItem();

    if (selectedArmyOne == null && selectedUnitArmyTwo == null) {
      showErrorDialog("Unit Selection Error", "No Unit Selected", "Please Select a Unit from one of the two Army-Tables");
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

  /**
   * Saves battle to Battle.bin-file using fileController. Throws IOException if writing failed.
   *
   * @throws java.io.IOException if writing to Battla.csv-file fails.
   */
  @FXML
  public void save() throws IOException {
    FileController fileController = new FileController();
    fileController.saveBattle(battle);
  }
  /**
   * TODO: make filecontroller in the initialize or the contstructor. this.filecontroller.
   * Exit the application. Displays a confirmation dialog.
   *
   * @throws java.io.IOException if any.
   */
  @FXML
  public void exit(){
    try {
      FileController fileController = new FileController();
      fileController.saveBattle(battle);
    } catch (IOException e){
      showErrorDialog("Error", "Error - " + e.getMessage(), "Please save the game in a safe folder");
      export();
    }
    Platform.exit();
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
    alert.setContentText("A simulation of a war between two armies with four different types of units and three different terrains\n\n" +
            "As created by " + "(C)Johannes Valøy\n" + "v1.0 2022-05-07");

    alert.showAndWait();
  }

  /**
   * Exports file as .csv-file. Shows choose- folder-dialog.
   */
  @FXML
  public void export(){
    DirectoryChooser directoryChooser = new DirectoryChooser();
    directoryChooser.setTitle("Open Resource File Army One");
    File selectedFolder = directoryChooser.showDialog(null);
    if (selectedFolder == null) {
      showErrorDialog("No Folder Chosen", "There is no folder chosen to export to.", "Please choose a folder, hit 'open', and click 'OK'.");
    } else {
      CSVController CSVController = new CSVController();
      try {
        CSVController.saveArmy(battle.getArmyOne(), 0, selectedFolder.getAbsolutePath() + "\\ArmyOne.csv");
        CSVController.saveArmy(battle.getArmyTwo(), 0, selectedFolder.getAbsolutePath() + "\\ArmyTwo.csv");
      } catch (IOException e){
        showErrorDialog("File export Error", e.getMessage(), "Please select a different folder without other files with the same name as the armies and try again.");
      }
    }
  }

  /**
   * Displays the file chooser dialog. Nothing is done with the file chosen
   * by the user. For demo-purposes only.
   */
  @FXML
  public void open() throws IOException {
    File fileArmyOne = openDialog();
    File fileArmyTwo = openDialog();
    if(fileArmyOne == null || fileArmyTwo == null){
      showErrorDialog("No files chosen", "There is no file chosen", "Please select files that are not empty");
    } else {


      CSVController csvController = new CSVController();
      Army armyOne = null;
      Army armTwo = null;
      try{
        armyOne = csvController.retrieveArmy(fileArmyOne.getAbsolutePath());}
      catch (IOException e){
        showErrorDialog("ArmyOne file corrupted", "Army One-" + e.getMessage(), "Please Select a different file");
      }
      try{
        armTwo = csvController.retrieveArmy(fileArmyTwo.getAbsolutePath());}
      catch (IOException e){
        showErrorDialog("ArmyTwo file corrupted", "Army Two-" + e.getMessage(), "Please Select a different file");
      }
      if(armyOne != null && armTwo != null){
      csvController.retrieveArmy(fileArmyOne.getAbsolutePath()).getName();
      this.battle = new Battle(armyOne, armTwo);
      fileArmyOneLabel.setText(fileArmyOne.getName());
      fileArmyTwoLabel.setText(fileArmyTwo.getName());
      this.updateObservableArmyOne();
      this.updateObservableArmyTwo();}
    }
  }

  /**
   * Resets the armies from memory. Calls for new Random() to make the battle different each time.
   *
   * @throws java.io.IOException from FileController if memory file is corrupted.
   */
  @FXML
  public void resetSession() throws IOException {
    this.battle = new FileController().retrieveBattle();
    battle.getArmyOne().newRandom();
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
      numberOfUnitsArmyOneLabel.setText(battle.getArmyOne().getAllUnits().size() + "");
      armyOneNameLabel.setText(battle.getArmyOne().getName());
  }

  /**
   * updates the observable for army two, and parameters for the UI.
   */
  private void updateObservableArmyTwo() {
    this.observableArmyTwo.setAll(this.battle.getArmyTwo().getAllUnits());
      numberOfUnitsArmyTwoLabel.setText(battle.getArmyTwo().getAllUnits().size() + "");
      armyTwoNameLabel.setText(battle.getArmyTwo().getName());
  }

  /**
   * Unselects armyTwo selection when armyOne unit is selected.
   */
  public void selectedArmyTwo() {
    this.armyOneTableView.getSelectionModel().clearSelection();
  }
  /**
   * Unselects armyOne selection when armyTwo unit is selected.
   */
  public void selectedArmyOne() {
    this.armyTwoTableView.getSelectionModel().clearSelection();
  }

  /**
   * Removes all the units in both armies.
   */
  @FXML
  public void empty(){
    this.battle = new Battle(new Army("ArmyOne"), new Army("ArmyTwo"));
    updateObservableArmyOne();
    updateObservableArmyTwo();
  }


  // -----------------------------------------------------------
  //    DIALOGS
  // -----------------------------------------------------------

  /**
   * Opens file-chooser dialog where user selects file to be opened as .csv file.
   *
   * @return open .csv-file dialog.
   */
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
   * Displays a warning informing the user that an item must be not add more than 999 units to army.
   */
    private void showTooManyUnitsDialog() {
      showErrorDialog("Information", "Cannot add more than 999 units in total", "Please do not add more than 999 units");
    }

  /**
   * Shows error dialog with the necessary messages.
   * @param type, message as string shows as the title on the dialog.
   * @param text, message as string shows as the main message on the dialog.
   * @param content, message as string shows as the detailed message on the dialog.
   */
  private void showErrorDialog(String type, String text, String content) {
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setTitle(type);
    alert.setHeaderText(text);
    alert.setContentText(content);

    alert.showAndWait();
  }
}

