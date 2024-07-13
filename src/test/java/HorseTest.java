import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HorseTest {
    @Test
    public void nullNameException() {
        assertThrows(IllegalArgumentException.class,() -> new Horse(null, 1,1));
    }
    @Test
    public void nullNameMessage() {
        try{
            new Horse(null, 1, 1);
        }catch (IllegalArgumentException e) {
            assertEquals("Name cannot be null.", e.getMessage());
        }
    }
    @ParameterizedTest
    @MethodSource("blanksString")
    public void blankNameException(String name) {
        assertThrows(IllegalArgumentException.class,() -> new Horse(name, 1,1));
    }

    @ParameterizedTest
    @MethodSource("blanksString")
    public void blankNameMessage(String name) {
        try{
            new Horse(name, 1, 1);
        }catch (IllegalArgumentException e) {
            assertEquals("Name cannot be blank.", e.getMessage());
        }
    }

    @Test
    public void negativeSpeedException() {
        assertThrows(IllegalArgumentException.class,() -> new Horse("Name", -1,1));
    }

    @Test
    public void negativeSpeedMessage() {
        try{
            new Horse("Name", -1, 1);
        }catch (IllegalArgumentException e) {
            assertEquals("Speed cannot be negative.", e.getMessage());
        }
    }

    @Test
    public void negativeDistanceException() {
        assertThrows(IllegalArgumentException.class,() -> new Horse("Name", 1,-1));
    }

    @Test

    public void negativeDistanceMessage() {
        try{
            new Horse("Name", 1, -1);
        }catch (IllegalArgumentException e) {
            assertEquals("Distance cannot be negative.", e.getMessage());
        }
    }

    @Test
    public void getName() {
        assertEquals("TestPassed", new Horse("TestPassed", 1, 1).getName());
    }

    @Test
    public void getSpeed() {
        assertEquals(20, new Horse("TestPassed", 20, 1).getSpeed());
    }

    @Test
    public void getDistanceWithConstructor() {
        assertEquals(117, new Horse("TestPassed", 1, 117).getDistance());
    }

    @Test
    public void getDistanceWithoutConstructor() {
        assertEquals(0, new Horse("TestPassed", 1).getDistance());
    }

    @Test
    public void moveCallingGetRandomDouble() {
        try(MockedStatic<Horse> horseMockedStatic = Mockito.mockStatic(Horse.class)) {
            new Horse("TestPassed", 1, 1).move();

            horseMockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.1, 0.2, 20, 3})
    public void move(double random) {
        try(MockedStatic<Horse> horseMockedStatic = Mockito.mockStatic(Horse.class)) {
            horseMockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(random);

            Horse horse = new Horse("Test", 10, 20);
            horse.move();
            double horseDistance = horse.getDistance();
            double distance = 20 + 10 * random;

            assertEquals(distance, horseDistance);
        }
    }

    static Stream<String> blanksString() {
        return Stream.of(" ", "", "  ", "    ", "  ");
    }
}
