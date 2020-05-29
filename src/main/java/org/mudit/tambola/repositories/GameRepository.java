package org.mudit.tambola.repositories;

import org.mudit.tambola.model.Game;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GameRepository extends MongoRepository<Game, String> {}
