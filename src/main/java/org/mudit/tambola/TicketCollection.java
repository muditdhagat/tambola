package org.mudit.tambola;

import java.util.ArrayList;
import java.util.List;

import org.mudit.tambola.model.TicketSet;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class TicketCollection {

  @Id private String id;

  private int ticketCount;

  private int ticketsPerPlayer;

  private String ticketCollectionName;

  private List<TicketSet> ticketsSets = new ArrayList<>();

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public List<TicketSet> getTicketsSets() {
    return ticketsSets;
  }

  public void setTicketsSets(List<TicketSet> ticketsSets) {
    this.ticketsSets = ticketsSets;
  }

  public int getTicketCount() {
    return ticketCount;
  }

  public void setTicketCount(int ticketCount) {
    this.ticketCount = ticketCount;
  }

  public int getTicketsPerPlayer() {
    return ticketsPerPlayer;
  }

  public void setTicketsPerPlayer(int ticketsPerPlayer) {
    this.ticketsPerPlayer = ticketsPerPlayer;
  }

  public String getTicketCollectionName() {
    return ticketCollectionName;
  }

  public void setTicketCollectionName(String ticketCollectionName) {
    this.ticketCollectionName = ticketCollectionName;
  }
}
