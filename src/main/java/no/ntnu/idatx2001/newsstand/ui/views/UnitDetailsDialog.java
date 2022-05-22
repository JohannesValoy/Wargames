package no.ntnu.idatx2001.newsstand.ui.views;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import no.ntnu.idatx2001.newsstand.model.Unit;
import no.ntnu.idatx2001.newsstand.factory.UnitFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A dialog used to get the necessary information about a Unit from the
 * user, in order to be able to create a Unit instance to be added to the
 * register. The dialog can be opened in 3 different modes:
 * <ul>
 * <li> EDIT - Used for editing an existing Unit.</li>
 * <li> NEW - Used for entering information to create a new Unit.</li>
 * <li> INFO - Used to show non-editable info of a Unit</li>
 * </ul>
 *
 * @author asty
 */
public class UnitDetailsDialog extends Dialog<List<Unit>> {

    /**
     * The mode of the dialog. If the dialog is opened to edit an existing
     * Unit, the mode is set to <code>Mode.EDIT</code>. If the dialog is
     * opened to create a new Unit, the <code>Mode.NEW</code> is used.
     */
    public enum Mode {
        NEW, EDIT, INFO
    }


    /**
     * The mode of the dialog. NEW if new Unit, EDIT if edit existing
     * Unit.
     */
    private final Mode mode;

    /**
     * Holds the Unit instance to edit, if any.
     */
    private Unit existingArmyUnit = null;

    /**
     * The GUI-components holding the Unit information.
     */
    private TextField title;
    private TextField healthNumber;
    private ChoiceBox<String> unitType;
    private TextField numberOfUnits;
    private Label attackBonus;
    private Label resistBonus;

    /**
     * Creates an instance of the UnitDetails dialog to get information to
     * create a new instance of Unit.
     */
    public UnitDetailsDialog() {
        super();
        this.mode = Mode.NEW;
        createContent();
        defineReturnInstance();
    }

    /**
     * Creates an instance of the NewspaperDetails dialog.
     *
     * @param armyUnit the newspaper instance to edit
     * @param editable  if set to <code>true</code>, the dialog will enable
     *                  editing of the fields in the dialog. if <code>false</code> the
     *                  information will be displayed in non-editable fields.
     */
    public UnitDetailsDialog(Unit armyUnit, boolean editable) {
        super();
        if (editable) {
            this.mode = Mode.EDIT;
        } else {
            this.mode = Mode.INFO;
        }
        this.existingArmyUnit = armyUnit;
        createContent();
        defineReturnInstance();
    }

    /**
     * Creates the content of the dialog.
     */
    private void createContent() {
        setTitle("Unit Details");

        // Set the button types.
        if (this.mode == Mode.INFO) {
            getDialogPane().getButtonTypes().addAll(ButtonType.OK);

        } else {
            getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        }

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        this.title = new TextField();
        this.title.setPromptText("Name");
        this.healthNumber = new TextField();
        this.healthNumber.setPromptText("Health");
        this.unitType = new ChoiceBox<>();
        this.unitType.setValue("Unit Type");
        this.numberOfUnits = new TextField();
        this.numberOfUnits.setPromptText("Number of Units");
        this.attackBonus = new Label();
        this.resistBonus = new Label();

        // Prevent characters (non-integers) to be added. By adding an event listener
        // to the TextField for the issue number, this event is fired for each keypress
        // while in the text field. The listener added is a ChangeListener<T>, which has
        // one abstract method:
        //      changed(ObservableValue<? extends T> observable, T oldValue, T newValue)
        // It is this method the Lambda-expression must match, hence the parameters
        // observable (of type ObservableValue<? extends String>), and oldValue and
        // newValue, both of the type of the field, i.e. String (T = String)
        // By trying to pass the current text as an Integer, an exception will be
        // thrown if the user has entered something other than a number. We can than catch
        // this exception and use that to handle what to do if the user has entered a
        // character instead of a number. The parameter "oldValue" holds the value/content
        // of the TextField prior to the user typing in a new character, while the parameter
        // newValue holds the result that will be displayed in the TextField if nothing is
        // wrong.
        this.healthNumber.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                if (newValue.length() > 0) {
                    if(newValue.length() < 4){
                        Integer.parseInt(newValue);
                    }else {
                        this.healthNumber.setText(oldValue);
                    }
                }
            } catch (NumberFormatException e) {
                // The user have entered a non-integer character, hence just keep the
                // oldValue and ignore the newValue.
                this.healthNumber.setText(oldValue);
            }
        });
        this.numberOfUnits.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                if (newValue.length() > 0) {
                    if(newValue.length() < 4){
                        Integer.parseInt(newValue);
                    }else {
                        this.numberOfUnits.setText(oldValue);
                    }
                }
            } catch (NumberFormatException e) {
                // The user have entered a non-integer character, hence just keep the
                // oldValue and ignore the newValue.
                this.numberOfUnits.setText(oldValue);
            }
        });


        // Fill inn data from the provided Unit, if not null.
        if ((mode == Mode.EDIT) || (mode == Mode.INFO)) {
            title.setText(existingArmyUnit.getName());
            healthNumber.setText(Integer.toString(existingArmyUnit.getHealth()));
            unitType.setValue(existingArmyUnit.getClass().getSimpleName());
            unitType.setDisable(true);
            numberOfUnits.setDisable(true);
            this.attackBonus.setDisable(true);
            this.resistBonus.setDisable(true);

            this.attackBonus.setText(existingArmyUnit.getAttackBonus(false) + "");
            this.resistBonus.setText(existingArmyUnit.getResistBonus() + "");
            // Set to non-editable if Mode.INFO
            if (mode == Mode.INFO) {
                title.setEditable(false);
                healthNumber.setEditable(false);
                grid.add(new Label("Attack-bonus:"), 3, 0);
                grid.add(attackBonus, 4, 0);
                grid.add(new Label("Resist-bonus:"), 3, 1);
                grid.add(resistBonus, 4, 1);
            }
        }

        // Use the GridPane to create the section of the dialog displaying the details
        // about the Newspaper
        grid.add(new Label("Name:"), 0, 0);
        grid.add(title, 1, 0);
        grid.add(new Label("Health:"), 0, 1);
        grid.add(healthNumber, 1, 1);
        grid.add(new Label("Unit Type:"), 0, 2);
        this.unitType.getItems().addAll("CavalryUnit", "CommanderUnit", "InfantryUnit", "RangedUnit");
        grid.add(unitType, 1, 2);
        grid.add(new Label("Number of Units:"), 0, 3);
        grid.add(numberOfUnits, 1, 3);

        getDialogPane().setContent(grid);
    }

    /**
     * Defines what to be returned from the dialog when the dialog is closed.
     * What will be returned will depend upon the <b>mode</b> that the dialog was
     * opened in (EDIT, NEW, INFO).
     */
    private void defineReturnInstance() {
        //TODO: Check for exepetions when fields are empty and unittype not chosen.

        // Convert the result to Newspaper-instance when the OK button is clicked.
        // In this example I use lambda. Could also have been solved using anonymous inner class
        // Check out: https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/Dialog.html#setResultConverter-javafx.util.Callback-
        // and: https://docs.oracle.com/javase/8/javafx/api/javafx/util/Callback.html
        this.setResultConverter(
                (ButtonType button) -> {
                    List<Unit> result = null;
                    if (button == ButtonType.OK) {
                        int issueNo = Integer.parseInt(this.healthNumber.getText());


                        // Note how the mode of the dialog effects how to deal with the result.
                        // If we opened the dialog in EDIT-mode, the changes been made by the user in the
                        // fields of the dialog needs to be changed in the Newspaper-instance given to the
                        // dialog when opened, and the return will be the updated Newspaper instance.
                        // If the mode is NEW, a completely new instance of class Newspaper must be created,
                        // based on the values in the fields of the dialog, and then returned
                        // upon closing the dialog.
                        // If the mode was INFO, nothing is returned (null), since no changes have been made.
                        if (mode == Mode.NEW) {
                            int numberOfUnits = Integer.parseInt(this.numberOfUnits.getText());
                            UnitFactory unitFactory = new UnitFactory();
                            Unit unit = null;
                            try {
                                unitFactory.addUnit(issueNo, this.title.getText(), this.unitType.getSelectionModel().getSelectedItem(),numberOfUnits);
                                result = unitFactory.retrieveAllunits();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        } else if (mode == Mode.EDIT) {
                            existingArmyUnit.setName(this.title.getText());
                            existingArmyUnit.setHealth(issueNo);
                            ArrayList<Unit> unitlist =new ArrayList<>();
                            unitlist.add(existingArmyUnit);
                            result = unitlist;
                        }
                    }
                    return result;
                }
        );
    }
}
