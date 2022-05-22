module no.ntnu.idata2001.wargames {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;

    // Since the JavaFX-class PropertyValueFactory uses reflection to access
    // the values/getters of the Literature-class, we must "open up"
    // the package holding the Literature class to the javafx.base, where
    // the PropertyValueFactory lives
    opens no.ntnu.idata2001.wargames;

    opens no.ntnu.idata2001.wargames.model;
    opens no.ntnu.idata2001.wargames.ui.views to javafx.graphics, javafx.fxml;
    exports no.ntnu.idata2001.wargames.model;
    exports no.ntnu.idata2001.wargames;
    exports no.ntnu.idata2001.wargames.ui.views;
    exports no.ntnu.idata2001.wargames.ui.controllers;
    opens no.ntnu.idata2001.wargames.ui.controllers;
    exports no.ntnu.idata2001.wargames.factory;
    opens no.ntnu.idata2001.wargames.factory;
    exports no.ntnu.idata2001.wargames.controllers;
    opens no.ntnu.idata2001.wargames.controllers;
    exports no.ntnu.idata2001.wargames.units;
    opens no.ntnu.idata2001.wargames.units;
}
