package no.ntnu.idata.wargames;

import no.ntnu.idata.wargames.ui.views.MainWindowApp;

/**
 * Represents the non-JavaFX main-class who's sole responsibility is to provide
 * a non-JavaFX entry point to the application. Needed in order to be able to create an
 * executable JAR.
 *
 * @author johan
 * @version $Id: $Id
 */
public class Main {
  /**
   * <p>main.</p>
   *
   * @param args an array of {@link java.lang.String} objects
   */
  public static void main(String[] args) {
    MainWindowApp.main(args);
  }
}
