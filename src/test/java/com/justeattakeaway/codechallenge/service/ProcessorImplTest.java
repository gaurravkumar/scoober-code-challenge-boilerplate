import com.justeattakeaway.codechallenge.model.game.Move;
import com.justeattakeaway.codechallenge.service.ProcessorImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProcessorImplTest {

    private ProcessorImpl processor;

    @BeforeEach
    void setUp() {
        processor = new ProcessorImpl();
    }

    @Test
    void processMessage_addNumberIsNull_numberIsMultipleOfThree() {
        Move move = processor.processMessage(9, null);
        assertEquals(0, move.getAdded());
        assertEquals(9, move.getResult());
    }

    @Test
    void processMessage_addNumberIsNull_numberModuloThreeIsOne() {
        Move move = processor.processMessage(10, null);
        assertEquals(-1, move.getAdded());
        assertEquals(9, move.getResult());
    }

    @Test
    void processMessage_addNumberIsNull_numberModuloThreeIsTwo() {
        Move move = processor.processMessage(11, null);
        assertEquals(1, move.getAdded());
        assertEquals(12, move.getResult());
    }

    @Test
    void processMessage_addNumberIsNotNull_validAddNumber() {
        Move move = processor.processMessage(10, -1);
        assertEquals(-1, move.getAdded());
        assertEquals(9, move.getResult());
    }

    @Test
    void processMessage_addNumberIsNotNull_invalidAddNumber() {
        Exception exception = assertThrows(RuntimeException.class, () -> processor.processMessage(10, 2));
        assertEquals("Move not Allowed. You can add 1, 0, -1 only", exception.getMessage());
    }
}