package player.agents;

import game.Color;
import game.GameBoard;
import game.Position;
import player.Agent;

import java.util.LinkedList;
import java.util.Optional;

/**
 * @author Tobias, Rasmus och Markus
 */
public class Taurus extends Agent {

    int[] scores = {
            100, 0, 80, 60, 60, 80, 0, 100,
            0, 0, 20, 20, 20, 20, 0, 0,
            80, 20, 40, 40, 40, 40, 20, 80,
            60, 20, 40, 40, 40, 40, 20, 60,
            60, 20, 40, 40, 40, 40, 20, 60,
            80, 20, 40, 40, 40, 40, 20, 60,
            0, 0, 20, 20, 20, 20, 0, 0,
            100, 0, 80, 60, 60, 80, 0, 100
    };

    public Taurus(Color color) {
        super(color);
    }

    @Override
    public void newGame() {

    }

    @Override
    public Position nextMove(GameBoard board, LinkedList<Position> currentLegalPositions) throws InterruptedException {
        Optional<Position> max = currentLegalPositions.stream()
                .max(this::compare);
        return max.orElse(null);
    }

    private int compare(Position a, Position b) {
        return Integer.compare(score(a), score(b));
    }

    private int score(Position p) {
        return scores[p.row * 8 + p.column];
    }
}
