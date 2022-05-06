package org.johakv.stud.ntnu.no;

import org.johakv.stud.ntnu.no.ui.views.MainWindowApp;

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
