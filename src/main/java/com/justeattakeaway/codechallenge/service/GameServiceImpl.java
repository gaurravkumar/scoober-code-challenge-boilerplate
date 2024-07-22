package com.justeattakeaway.codechallenge.service;

import com.justeattakeaway.codechallenge.configuration.RabbitMQConfig;
import com.justeattakeaway.codechallenge.model.game.Game;
import com.justeattakeaway.codechallenge.model.game.dto.GameCreationRequest;
import com.justeattakeaway.codechallenge.model.game.dto.GameCreationResponse;
import com.justeattakeaway.codechallenge.model.game.dto.JoinGameRequest;
import com.justeattakeaway.codechallenge.model.game.dto.MoveResponse;
import com.justeattakeaway.codechallenge.repository.GamePersistence;
import com.justeattakeaway.codechallenge.repository.GameProgressPersistence;
import com.justeattakeaway.codechallenge.service.common.ListenerService;
import com.justeattakeaway.codechallenge.service.common.MessageSenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class GameServiceImpl implements GameService {

    private static final Logger logger = LoggerFactory.getLogger(GameServiceImpl.class);

    private GamePersistence gamePersistence;

    private PlayerService playerService;

    private RabbitMQConfig rabbitMQConfig;

    private ListenerService listenerService;

    private MessageSenderService messageSenderService;

    private GameProgressPersistence gameProgressPersistence;

    private ProcessorImpl processor;

    GameServiceImpl(GamePersistence gamePersistence,
                    PlayerService playerService,
                    RabbitMQConfig rabbitMQConfig,
                    ListenerService listenerService,
                    MessageSenderService messageSenderService,
                    GameProgressPersistence gameProgressPersistence,
                    ProcessorImpl processor) {
        this.gamePersistence = gamePersistence;
        this.playerService = playerService;
        this.rabbitMQConfig = rabbitMQConfig;
        this.listenerService = listenerService;
        this.messageSenderService = messageSenderService;
        this.gameProgressPersistence = gameProgressPersistence;
        this.processor = processor;
    }

    public Integer startGame(String gameId, String loginName, Integer message) {
        Integer number = message;
        if (number == null) {
            Random random = new Random();
            number = random.nextInt(Integer.MAX_VALUE - 1) + 1;
        }
        var game = gamePersistence.getGameById(gameId);

        if (game == null) {
            throw new RuntimeException("Game not found");
        }
        if (!(game.getGameOwner().equals(loginName))) {
            throw new RuntimeException("Game Owner should start the game");
        }
        if (game.isFinished()) {
            throw new RuntimeException("Game Finished. Cannot start the game again.");
        }
        var owner = playerService.getPlayerByUniqueLoginName(loginName);
        var otherPlayer = playerService.getPlayerByUniqueLoginName(game.getPlayers().stream().filter(p -> !p.equals(loginName)).findFirst().orElse(null));
        messageSenderService.sendMessage(owner.getQueueName(), number);
        listenerService.listenToQueue(owner.getQueueName(), gameId, owner.getUniqueLoginName());
        return number;
    }

    public GameCreationResponse createGame(GameCreationRequest gameCreationRequest) {
        var player = playerService.getPlayerByUniqueLoginName(gameCreationRequest.getLoginName());
        if(player == null) {
            throw new RuntimeException("Player not found");
        }
        rabbitMQConfig.createQueue(player.getQueueName());

        Game game = Game.builder()
                .players(List.of(gameCreationRequest.getLoginName()))
                .inputType(gameCreationRequest.getInputType())
                .gameOwner(gameCreationRequest.getLoginName())
                .build();
        var persistedGame = gamePersistence.saveGame(game);

        return GameCreationResponse.builder()
                .id(persistedGame.getId())
                .players(persistedGame.getPlayers())
                .numbers(persistedGame.getNumbers())
                .gameOwner(persistedGame.getGameOwner())
                .inputType(persistedGame.getInputType())
                .build();
    }

    public List<Game> getAllActiveGames() {
        return gamePersistence.findAllActiveGames();
    }

    public Game joinGame(JoinGameRequest joinGameRequest, String gameId) {
        // Get the game from MongoDB using gameId. Add the player from JoinGameRequest to the game. Save the game in MongoDB. Return the game object
        var game = gamePersistence.findAllActiveGames().stream().filter(g -> g.getId().equals(gameId)).findFirst().orElseThrow();
        if(game.getPlayers() != null && (game.getPlayers().size() == 2 || game.getPlayers().contains(joinGameRequest.getPlayer()))) {
            return game;
        }
        game.getPlayers().add(joinGameRequest.getPlayer());
        return gamePersistence.saveGame(game);
    }

    public MoveResponse makeMove(String gameId, String loginName, Integer addNumber) {
        MoveResponse moveResponse = new MoveResponse();
        var gameProgress = gameProgressPersistence.getgameById(gameId);
        if (gameProgress == null) {
            throw new RuntimeException("Game Over. Cannot make a move.");
        }
        var playerAndNumber = gameProgress.getPlayerAndNumber();
        var colonBeforeLastNumber = playerAndNumber.lastIndexOf(":");
        var lastNumber = Integer.parseInt(playerAndNumber.substring(colonBeforeLastNumber + 1));
        if (lastNumber == 1) {
            var checkPlayer = playerAndNumber.lastIndexOf(",");
            var lastPlayer = playerAndNumber.substring(checkPlayer + 1, colonBeforeLastNumber);
            Game game = gamePersistence.getGameById(gameId);
            game.setFinished(true);
            this.gamePersistence.saveGame(game);
            moveResponse.setWinner(lastPlayer);
            moveResponse.setFinished(true);
            return moveResponse;
        }
        var move = processor.processMessage(lastNumber, addNumber);
        var player = playerService.getPlayerByUniqueLoginName(loginName);
        messageSenderService.sendMessage(player.getQueueName(), move.getResult() / 3);
        listenerService.listenToQueue(player.getQueueName(), gameId, player.getUniqueLoginName());
        moveResponse.setResult(move.getResult());
        moveResponse.setAdded(move.getAdded());
        moveResponse.setFinished(false);
        logger.info("Move made by player: " + loginName + " resulting in number: " + move.getResult() + " by adding: " + move.getAdded());
        return moveResponse;
    }

}
