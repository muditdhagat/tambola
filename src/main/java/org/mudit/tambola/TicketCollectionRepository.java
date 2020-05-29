package org.mudit.tambola;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface TicketCollectionRepository extends MongoRepository<TicketCollection, String> {}
