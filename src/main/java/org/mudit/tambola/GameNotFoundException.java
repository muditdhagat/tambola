package org.mudit.tambola;

@SuppressWarnings("serial")
public class GameNotFoundException extends RuntimeException {
  public GameNotFoundException(String id) {
    super("Could not find game " + id);
  }
}
