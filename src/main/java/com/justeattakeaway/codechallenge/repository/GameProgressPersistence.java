package com.justeattakeaway.codechallenge.repository;

import com.justeattakeaway.codechallenge.model.game.GameProgress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
public class GameProgressPersistence {
    private GameProgressRepository repository;

    @Autowired
    public GameProgressPersistence(GameProgressRepository repository) {
        this.repository = repository;
    }

    public GameProgress saveGame(GameProgress gameProgress) {
        return repository.save(gameProgress);
    }

    public GameProgress getgameById(String id) {
        return repository.findById(id).orElse(null);
    }

}

@Repository
interface GameProgressRepository extends MongoRepository<GameProgress, String> {
}