package org.mudit.tambola.core;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mudit.tambola.core.model.Ticket;
import org.mudit.tambola.generator.TicketExporter;
import org.mudit.tambola.model.Game;
import org.mudit.tambola.model.TicketSet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class GameTest {

  @Test
  public void GametoStringTest() throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper()
	    .registerModule(new Jdk8Module())
	    .registerModule(new JavaTimeModule());
    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    Game game = new Game();
    game.setName("Family Tambola");
    game.setPlayerCount(45);
    game.setTicketsPerPlayer(2);
    game.setCreatedAt(LocalDateTime.now());
    game.setScehduledPlaytime(LocalDateTime.of(2020, 4, 23, 16, 0));
    
    String output = mapper.writeValueAsString(game);
    System.out.println(output);
  }
}
