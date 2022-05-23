module no.ntnu.idata2001.wargames {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;

    // Since the JavaFX-class PropertyValueFactory uses reflection to access
    // the values/getters of the Literature-class, we must "open up"
    // the package holding the Literature class to the javafx.base, where
    // the PropertyValueFactory lives
    opens no.ntnu.idata.wargames;

    opens no.ntnu.idata.wargames.logic.warsimulation;
    opens no.ntnu.idata.wargames.ui.views to javafx.graphics, javafx.fxml;
    exports no.ntnu.idata.wargames.logic.warsimulation;
    exports no.ntnu.idata.wargames;
    exports no.ntnu.idata.wargames.ui.views;
    exports no.ntnu.idata.wargames.ui.controllers;
    opens no.ntnu.idata.wargames.ui.controllers;
    exports no.ntnu.idata.wargames.logic.factory;
    opens no.ntnu.idata.wargames.logic.factory;
    exports no.ntnu.idata.wargames.logic.controllers;
    opens no.ntnu.idata.wargames.logic.controllers;
    exports no.ntnu.idata.wargames.model;
    opens no.ntnu.idata.wargames.model;
}
