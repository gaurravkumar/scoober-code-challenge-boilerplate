import com.justeattakeaway.codechallenge.configuration.RabbitMQConfig;
import com.justeattakeaway.codechallenge.model.player.Player;
import com.justeattakeaway.codechallenge.model.player.dto.PlayerCreationRequest;
import com.justeattakeaway.codechallenge.model.player.dto.PlayerCreationResponse;
import com.justeattakeaway.codechallenge.repository.PlayerPersistence;
import com.justeattakeaway.codechallenge.service.PlayerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlayerServiceImplTest {

    @Mock
    private PlayerPersistence playerPersistence;

    @Mock
    private RabbitMQConfig rabbitMQConfig;

    @InjectMocks
    private PlayerServiceImpl playerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createPlayer_uniqueLoginNameExists() {
        PlayerCreationRequest request = new PlayerCreationRequest();
        request.setName("John");
        request.setUniqueLoginName("john123");

        Player existingPlayer = new Player();
        existingPlayer.setUniqueLoginName("john123");

        when(playerPersistence.getPlayerByLoginName("john123")).thenReturn(existingPlayer);

        PlayerCreationResponse response = playerService.createPlayer(request);

        assertNotNull(response);
        assertEquals("Player with this unique login name already exists", response.getErrrorMessage());
    }

    @Test
    void createPlayer_uniqueLoginNameDoesNotExist() {
        PlayerCreationRequest request = new PlayerCreationRequest();
        request.setName("John");
        request.setUniqueLoginName("john123");

        when(playerPersistence.getPlayerByLoginName("john123")).thenReturn(null);

        Player newPlayer = new Player();
        newPlayer.setName("John");
        newPlayer.setUniqueLoginName("john123");
        newPlayer.setQueueName("John-" + UUID.randomUUID().toString());

        when(playerPersistence.savePlayer(any(Player.class))).thenReturn(newPlayer);

        PlayerCreationResponse response = playerService.createPlayer(request);

        assertNotNull(response);
        assertEquals("john123", response.getUniqueLoginName());
        assertEquals("John", response.getName());
    }

    @Test
    void getPlayerByUniqueLoginName_uniqueLoginNameExists() {
        Player existingPlayer = new Player();
        existingPlayer.setUniqueLoginName("john123");

        when(playerPersistence.getPlayerByLoginName("john123")).thenReturn(existingPlayer);

        Player player = playerService.getPlayerByUniqueLoginName("john123");

        assertNotNull(player);
        assertEquals("john123", player.getUniqueLoginName());
    }

    @Test
    void getPlayerByUniqueLoginName_uniqueLoginNameDoesNotExist() {
        when(playerPersistence.getPlayerByLoginName("john123")).thenReturn(null);

        Player player = playerService.getPlayerByUniqueLoginName("john123");

        assertNull(player);
    }
}