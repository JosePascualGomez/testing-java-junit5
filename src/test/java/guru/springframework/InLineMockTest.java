package guru.springframework;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class InLineMockTest {
    @Test
    void testInLineMock(){
        Map mpaMock = mock(Map.class);
        assertEquals(mpaMock.size(), 0);
    }
}
