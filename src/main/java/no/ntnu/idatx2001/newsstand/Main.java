package no.ntnu.idatx2001.newsstand;

import no.ntnu.idatx2001.newsstand.ui.views.MainWindowApp;

/**
 * Represents the non-JavaFX main-class who's sole responsibility is to provide
 * a non-JavaFX entry point to the application. Needed in order to be able to create an
 * executable JAR.
 */
public class Main {
  public static void main(String[] args) {
    MainWindowApp.main(args);
  }
}
