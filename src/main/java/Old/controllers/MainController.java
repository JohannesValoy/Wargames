package Old.controllers;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import Old.AddArmiesController;
import Old.AddUnitController;
import Old.model.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    public Label ArmyOneLabel;
    @FXML
    private TableColumn<Unit, String> unitsColumn;
    @FXML
    public Label ArmyTwoLabel;
    @FXML
    public TableView ArmyOneTable;
    @FXML
    private Button save;

    private Army armyOne;
    private Army armyTwo;
    private Battle battle;

    public MainController() {
        Army armyOne = new Army("ArmyOne");
        Army armyTwo = new Army("ArmyTwo");
        this.battle = new Battle(armyOne, armyTwo);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        ArrayList<Unit> unitsOne = new ArrayList<>();
        armyOne = new Army("ArmyOne", unitsOne);
        CavalryUnit cavalryUnit = new CavalryUnit("NameUnit1", 1);
        armyOne.add(cavalryUnit);
        ArrayList<Unit> unitsTwo = new ArrayList<>();
        armyTwo = new Army("ArmyTwo", unitsTwo);
        setArmyOneLabel(armyOne.getName());
        setArmyTwoLabel(armyTwo.getName());
        setTable();
        try {
            addArmy();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void simulate() {
        battle.simulate();
    }

    @FXML
    public void onSave() {
        SaveToFileRefactored saver = new SaveToFileRefactored();
        saver.saveArmy(armyOne, 0);
        saver.saveArmy(armyTwo, 1);
    }

    public void addArmy() throws IOException {
        AddArmiesController addArmiesController = new AddArmiesController();
        addArmiesController.openAddArmyWindow();
    }

    public void addUnit() throws Exception {
        AddUnitController addUnitController = new AddUnitController();
        addUnitController.showAndWait();
        setTable();
    }

    public void setArmyOneName(String text) {
        this.armyOne.setName(text);
    }

    public void setArmyTwoName(String text) {
        this.armyTwo.setName(text);
    }

    public void setArmyOneLabel(String text) {
        ArmyOneLabel.setText(text);
    }

    public void setArmyTwoLabel(String text) {
        ArmyTwoLabel.setText(text);
    }

    public void setTable() {
        unitsColumn.setCellValueFactory(new PropertyValueFactory<Unit, String>("name"));
        ArmyOneTable.setItems(getUnits());
    }

    public ObservableList<Unit> getUnits() {
        ObservableList<Unit> units = FXCollections.observableArrayList();
        units.addAll(armyOne.getAllUnits());
        return units;
    }

    public void altTable() {
        for (Unit unit : armyOne.getAllUnits()) {
            unitsColumn.setCellValueFactory(new PropertyValueFactory<Unit, String>(unit.getName()));
            //unitsTable.setCellValueFactory(new PropertyValueFactory<Unit, Integer>(unit.getHealth()));
            System.out.println(unit.getName());
        }
        ArmyOneTable.setItems(getUnits());
    }
}