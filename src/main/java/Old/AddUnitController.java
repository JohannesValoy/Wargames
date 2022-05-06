package Old;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import Old.model.Unit;
import Old.model.UnitFactory;

import java.io.IOException;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class AddUnitController extends Dialog<UnitFactory>{
    @FXML
    private BorderPane addUnit;

    private String unitType;
    @FXML
    private TextField nameField;
    @FXML
    private TextField healthField;
    @FXML
    private Button addUnitButton;
    @FXML
    private Button removeUnitButton;
    @FXML
    private Button cavalryUnitButton;
    @FXML
    private Button commanderUnitButton;
    @FXML
    private Button infantryUnitButton;
    @FXML
    private Button rangeUnitButton;

    UnitFactory unitFactory = new UnitFactory();

    public AddUnitController(){

    }
    public void openAddUnitWindow() throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlAddUnitLoader = new FXMLLoader(getClass().getResource("AddUnit.fxml"));
        Parent root = fxmlAddUnitLoader.load();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Add Unit - Battle");
        Scene scene = new Scene(root, 800, 500);
        stage.setScene(scene);
        stage.show();
    }

    public void removeUnitButton(){

    }

    public void cavalryUnitButton(){unitType = "CavalryUnit";}
    public void commanderUnitButton(){unitType = "CommanderUnit";}
    public void rangeUnitButton(){
        unitType = "RangeUnit";
    }
    public void infantryUnitButton(){
        unitType = "InfantryUnit";
    }

    public ArrayList<Unit> addUnitButton() throws IOException {
        UnitFactory unitFactory = new UnitFactory();
        unitFactory.addUnit(parseInt(healthField.getText()), nameField.getText(), unitType, 1);
        return unitFactory.retrieveUnits();
    }

    public void addUnitReturn() throws IOException {
        openAddUnitWindow();
    }
}
