package org.mudit.tambola.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Game {

  @Id private String id;

  private String name;

  private int playerCount;

  private int ticketsPerPlayer;

  private LocalDateTime createdAt;

  private LocalDateTime scehduledPlaytime;

  private String ticketCollectionId;

  private List<Integer> calledOutNumbers = new ArrayList<>();

  public Game() {}

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getPlayerCount() {
    return playerCount;
  }

  public void setPlayerCount(int playerCount) {
    this.playerCount = playerCount;
  }

  public int getTicketsPerPlayer() {
    return ticketsPerPlayer;
  }

  public void setTicketsPerPlayer(int ticketsPerPlayer) {
    this.ticketsPerPlayer = ticketsPerPlayer;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public LocalDateTime getScehduledPlaytime() {
    return scehduledPlaytime;
  }

  public void setScehduledPlaytime(LocalDateTime scehduledPlaytime) {
    this.scehduledPlaytime = scehduledPlaytime;
  }

  public List<Integer> getCalledOutNumbers() {
    return calledOutNumbers;
  }

  public void setCalledOutNumbers(List<Integer> calledOutNumbers) {
    this.calledOutNumbers = calledOutNumbers;
  }

  public String getTicketCollectionId() {
    return ticketCollectionId;
  }

  public void setTicketCollectionId(String ticketCollectionId) {
    this.ticketCollectionId = ticketCollectionId;
  }
}
