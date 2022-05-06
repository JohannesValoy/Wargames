
package Old;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class AddArmiesController {
    @FXML
    private TextField armyOneTextField;
    @FXML
    private TextField armyTwoTextField;
    @FXML
    private Button addArmy;

    public void openAddArmyWindow() throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlAddUnitLoader = new FXMLLoader(getClass().getResource("AddArmies.fxml"));
        Parent root = fxmlAddUnitLoader.load();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Add Unit - Battle");
        Scene scene = new Scene(root, 600, 500);
        stage.setScene(scene);
        stage.show();
    }

    public String armyOneName() {
        return armyOneTextField.getText();
    }

    public String armyTwoName() {
        return armyTwoTextField.getText();
    }

    public void addArmy(){

    }
}
