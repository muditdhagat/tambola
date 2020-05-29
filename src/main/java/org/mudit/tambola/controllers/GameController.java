package org.mudit.tambola.controllers;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;

import org.mudit.tambola.GameNotFoundException;
import org.mudit.tambola.TicketCollection;
import org.mudit.tambola.TicketCollectionNotFoundException;
import org.mudit.tambola.TicketCollectionRepository;
import org.mudit.tambola.core.TicketGenerator;
import org.mudit.tambola.generator.TicketExporter;
import org.mudit.tambola.model.Game;
import org.mudit.tambola.model.TicketSet;
import org.mudit.tambola.repositories.GameRepository;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {

  private final GameRepository gameRepository;
  private final TicketCollectionRepository ticketCollectionRepository;

  public GameController(
      GameRepository gameRepository, TicketCollectionRepository ticketCollectionRepository) {
    this.gameRepository = gameRepository;
    this.ticketCollectionRepository = ticketCollectionRepository;
  }

  @GetMapping("/game/{gameId}")
  public Game fetchGame(@PathVariable String gameId) {
    return gameRepository.findById(gameId).orElseThrow(() -> new GameNotFoundException(gameId));
  }
  
  @GetMapping("/game/list")
  public List<Game> listGames() {
    return gameRepository.findAll();
  }

  @PostMapping("/game/create")
  public Game createGame(@RequestBody Game game) {
    return gameRepository.insert(game);
  }
  
  @PostMapping("/game/save")
  public Game saveGame(@RequestBody Game game) {
    return gameRepository.save(game);
  }

  @PostMapping("/tickets/create/{gameId}")
  public TicketCollection createTickets(@PathVariable String gameId) {

    Game game =
        gameRepository.findById(gameId).orElseThrow(() -> new GameNotFoundException(gameId));

    TicketCollection ticketCollection = new TicketCollection();
    ticketCollection.setTicketCount(game.getPlayerCount());
    ticketCollection.setTicketsPerPlayer(game.getTicketsPerPlayer());
    ticketCollection.setTicketCollectionName(game.getName());

    TicketGenerator generator = new TicketGenerator();
    List<TicketSet> ticketSets =
        generator.generateTicketSets(
            ticketCollection.getTicketsPerPlayer(), ticketCollection.getTicketCount());
    ticketCollection.setTicketsSets(ticketSets);
    TicketCollection saveTicketCollection = ticketCollectionRepository.save(ticketCollection);
    // Save the Game with the ticketCollectionId
    game.setTicketCollectionId(saveTicketCollection.getId());
    gameRepository.save(game);

    return ticketCollection;
  }

  @GetMapping(value = "/export/tickets/{ticketCollectionId}", produces = "application/zip")
  public void exportTicketCollection (
      @PathVariable String ticketCollectionId, HttpServletResponse response) throws IOException {

    // Get the Tickets
    TicketCollection ticketCollection =
        ticketCollectionRepository
            .findById(ticketCollectionId)
            .orElseThrow(() -> new TicketCollectionNotFoundException(ticketCollectionId));
    
    TicketExporter ticketExporter = new TicketExporter();
    String directory = ticketExporter.exportTickets(ticketCollection);

    ZipOutputStream zipOut = new ZipOutputStream(response.getOutputStream());
    File file = new File(directory);
    for (File f : file.listFiles()) {
      FileSystemResource resource = new FileSystemResource(f);
      ZipEntry zipEntry = new ZipEntry(resource.getFilename());
      zipEntry.setSize(resource.contentLength());
      zipOut.putNextEntry(zipEntry);
      StreamUtils.copy(resource.getInputStream(), zipOut);
      zipOut.closeEntry();
    }

    zipOut.finish();
    zipOut.close();
    response.setStatus(HttpServletResponse.SC_OK);
    response.addHeader(
        HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + "tickets" + ".zip");
  }
}
