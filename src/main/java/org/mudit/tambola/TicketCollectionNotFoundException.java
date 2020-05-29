package org.mudit.tambola;

@SuppressWarnings("serial")
public class TicketCollectionNotFoundException extends RuntimeException {
    public TicketCollectionNotFoundException(String id) {
	    super("Could not find ticket collection " + id);
	  }
}
