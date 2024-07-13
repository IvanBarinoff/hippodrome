import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HippodromeTest {
    @Test
    public void nullHorsesException() {
        assertThrows(IllegalArgumentException.class,() -> new Hippodrome(null));
    }

    @Test
    public void  nullHorsesMessage() {
        try {
            new Hippodrome(null);
        } catch (IllegalArgumentException e) {
            assertEquals("Horses cannot be null.", e.getMessage());
        }
    }

    @Test
    public void emptyHorsesException() {
        assertThrows(IllegalArgumentException.class,() -> new Hippodrome(new ArrayList<>()));
    }

    @Test
    public void  emptyHorsesMessage() {
        try {
            new Hippodrome(new ArrayList<>());
        } catch (IllegalArgumentException e) {
            assertEquals("Horses cannot be empty.", e.getMessage());
        }
    }

    @Test
    public void  getHorses() {
        List<Horse> horses = randomHorses(30);
        Hippodrome hippodrome = new Hippodrome(horses);

        assertEquals(horses, hippodrome.getHorses());
    }

    @Test
    public void move() {
        Hippodrome hippodrome = new Hippodrome(randomMockHorses(50));
        hippodrome.move();
        for(Horse horse: hippodrome.getHorses())
            Mockito.verify(horse).move();
    }

    @Test
    public void getWinner() {
        Hippodrome hippodrome = new Hippodrome(randomHorses(30));
        hippodrome.move();
        Horse testWinner = hippodrome.getWinner();

        Horse winner = hippodrome.getHorses().stream()
                .max(Comparator.comparingDouble(Horse::getDistance))
                .orElse(null);

        assertEquals(winner, testWinner);
    }

    public static List<Horse> randomHorses(int limit) {
        return Stream.generate(() -> String.valueOf(Math.random()))
                .limit(limit)
                .map(x -> new Horse(x, 1, 1)).collect(Collectors.toList());
    }

    public static List<Horse> randomMockHorses(int limit) {
        return Stream.generate(() -> String.valueOf(Math.random()))
                .limit(limit)
                .map(x -> Mockito.spy(new Horse(x, 1, 1))).collect(Collectors.toList());
    }
}
