module no.ntnu.idatx2001.newsstand {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;

    // Since the JavaFX-class PropertyValueFactory uses reflection to access
    // the values/getters of the Literature-class, we must "open up"
    // the package holding the Literature class to the javafx.base, where
    // the PropertyValueFactory lives
    opens no.ntnu.idatx2001.newsstand;

    opens no.ntnu.idatx2001.newsstand.model;
    opens no.ntnu.idatx2001.newsstand.ui.views to javafx.graphics, javafx.fxml;
    opens no.ntnu.idatx2001.newsstand.ui.controllers to javafx.fxml;
    exports no.ntnu.idatx2001.newsstand.ui.controllers to javafx.fxml;
    exports no.ntnu.idatx2001.newsstand.model;
    exports no.ntnu.idatx2001.newsstand;
    exports no.ntnu.idatx2001.newsstand.ui.views;
}
