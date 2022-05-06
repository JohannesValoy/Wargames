package no.ntnu.idatx2001.newsstand.ui.views;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import no.ntnu.idatx2001.newsstand.model.CavalryUnit;
import no.ntnu.idatx2001.newsstand.model.Unit;
import no.ntnu.idatx2001.newsstand.model.UnitFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A dialog used to get the necessary information about a newspaper from the
 * user, in order to be able to create a Newspaper instance to be added to the
 * register. The dialog can be opened in 3 different modes:
 * <ul>
 * <li> EDIT - Used for editing an existing Newspaper.</li>
 * <li> NEW - Used for entering information to create a new Newspaper.</li>
 * <li> INFO - Used to show non-editable info of a Newspaper</li>
 * </ul>
 *
 * @author asty
 */
public class UnitDetailsDialog extends Dialog<List<Unit>> {

    /**
     * The mode of the dialog. If the dialog is opened to edit an existing
     * Newspaper, the mode is set to <code>Mode.EDIT</code>. If the dialog is
     * opened to create a new Newspaper, the <code>Mode.NEW</code> is used.
     */
    public enum Mode {
        NEW, EDIT, INFO
    }


    /**
     * The mode of the dialog. NEW if new Newspaper, EDIT if edit existing
     * Newspaper.
     */
    private final Mode mode;

    /**
     * Holds the Newspaper instance to edit, if any.
     */
    private Unit existingArmyUnit = null;

    /**
     * The GUI-components holding the Newspaper information.
     */
    private TextField title;
    private TextField issueNoTxt;
    private ChoiceBox<String> unitType;
    private TextField numberOfUnits;

    /**
     * Creates an instance of the NewspaperDetails dialog to get information to
     * create a new instance of Newspaper.
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
        this.issueNoTxt = new TextField();
        this.issueNoTxt.setPromptText("Health");
        this.unitType = new ChoiceBox<>();
        this.unitType.setValue("Unit Type");
        this.numberOfUnits = new TextField();
        this.numberOfUnits.setPromptText("Number of Units");

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
        this.issueNoTxt.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                if (newValue.length() > 0) {
                    Integer.parseInt(newValue);
                }
            } catch (NumberFormatException e) {
                // The user have entered a non-integer character, hence just keep the
                // oldValue and ignore the newValue.
                this.issueNoTxt.setText(oldValue);
            }
        });
        this.numberOfUnits.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                if (newValue.length() > 0) {
                    Integer.parseInt(newValue);
                }
            } catch (NumberFormatException e) {
                // The user have entered a non-integer character, hence just keep the
                // oldValue and ignore the newValue.
                this.numberOfUnits.setText(oldValue);
            }
        });


        // Fill inn data from the provided Newspaper, if not null.
        if ((mode == Mode.EDIT) || (mode == Mode.INFO)) {
            title.setText(existingArmyUnit.getName());
            issueNoTxt.setText(Integer.toString(existingArmyUnit.getHealth()));
            unitType.setValue(existingArmyUnit.getClass().getSimpleName());
            unitType.setDisable(true);
            numberOfUnits.setDisable(true);
            // Set to non-editable if Mode.INFO
            if (mode == Mode.INFO) {
                title.setEditable(false);
                issueNoTxt.setEditable(false);
            }
        }

        // Use the GridPane to create the section of the dialog displaying the details
        // about the Newspaper
        grid.add(new Label("Name:"), 0, 0);
        grid.add(title, 1, 0);
        grid.add(new Label("Health:"), 0, 2);
        grid.add(issueNoTxt, 1, 2);
        String cavalryUnit = "CavalryUnit";
        String commanderUnit = "CommanderUnit";
        String infantryUnit = "InfantryUnit";
        String rangedUnit = "RangedUnit";
        grid.add(new Label("Unit Type:"), 0, 3);
        this.unitType.getItems().addAll(cavalryUnit, commanderUnit, infantryUnit, rangedUnit);
        grid.add(unitType, 1, 3);
        grid.add(new Label("Number of Units:"), 0, 4);
        grid.add(numberOfUnits, 1, 4);

        getDialogPane().setContent(grid);
    }

    /**
     * Defines what to be returned from the dialog when the dialog is closed.
     * What will be returned will depend upon the <b>mode</b> that the dialog was
     * opened in (EDIT, NEW, INFO).
     */
    private void defineReturnInstance() {
        // Convert the result to Newspaper-instance when the OK button is clicked.
        // In this example I use lambda. Could also have been solved using anonymous inner class
        // Check out: https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/Dialog.html#setResultConverter-javafx.util.Callback-
        // and: https://docs.oracle.com/javase/8/javafx/api/javafx/util/Callback.html
        this.setResultConverter(
                (ButtonType button) -> {
                    List<Unit> result = null;
                    if (button == ButtonType.OK) {
                        int issueNo = Integer.parseInt(this.issueNoTxt.getText());
                        int numberOfUnits = Integer.parseInt(this.numberOfUnits.getText());

                        // Note how the mode of the dialog effects how to deal with the result.
                        // If we opened the dialog in EDIT-mode, the changes been made by the user in the
                        // fields of the dialog needs to be changed in the Newspaper-instance given to the
                        // dialog when opened, and the return will be the updated Newspaper instance.
                        // If the mode is NEW, a completely new instance of class Newspaper must be created,
                        // based on the values in the fields of the dialog, and then returned
                        // upon closing the dialog.
                        // If the mode was INFO, nothing is returned (null), since no changes have been made.
                        if (mode == Mode.NEW) {
                            UnitFactory unitFactory = new UnitFactory();
                            Unit unit = null;
                            try {
                                System.out.println(numberOfUnits);
                                unitFactory.addUnit(issueNo, this.title.getText(), this.unitType.getSelectionModel().getSelectedItem(),numberOfUnits);
                                result = unitFactory.retrieveUnits();
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
