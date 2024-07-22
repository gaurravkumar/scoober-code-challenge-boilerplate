package com.justeattakeaway.codechallenge.repository;

import com.justeattakeaway.codechallenge.model.game.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Component
public class GamePersistence {
    private GameRepository repository;

    @Autowired
    public GamePersistence(GameRepository repository) {
        this.repository = repository;
    }

    public Game saveGame(Game game) {
        return repository.save(game);
    }

    public List<Game> findAllActiveGames() {
        return repository.findByFinishedFalse();
    }

    public Game getGameById(String id) {
        return repository.findById(id).orElse(null);
    }

}

@Repository
interface GameRepository extends MongoRepository<Game, String> {
    List<Game> findByFinishedFalse();

    Optional<Game> findById(String id);
}