
package com.NBAPlayers.demo.repository;
import com.NBAPlayers.demo.model.NBAPlayer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NBAPlayerRepository extends MongoRepository<NBAPlayer, Integer> {
    NBAPlayer findFirstById(String id);
}


