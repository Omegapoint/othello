package player.agents;

import game.Color;
import game.GameBoard;
import game.Position;
import player.Agent;

import java.util.LinkedList;

public class OhSoOthello extends Agent {

    private static final int[][] EVALUATION_MATRIX = {
        {5, 0, 3, 1, 1, 3, 0, 5},
        {0, 0, 0, 2, 2, 0, 0, 0},
        {3, 0, 1, 1, 1, 1, 0, 3},
        {1, 2, 1, 0, 0, 1, 2, 1},
        {1, 2, 1, 0, 0, 1, 2, 1},
        {3, 0, 1, 1, 1, 1, 0, 3},
        {0, 0, 0, 2, 2, 0, 0, 0},
        {5, 0, 3, 1, 1, 3, 0, 5}
    };

    public OhSoOthello(Color color) {
        super(color);
    }

    @Override
    public void newGame() {
        // do nothing
    }

    @Override
    public Position nextMove(GameBoard board, LinkedList<Position> currentLegalPositions) throws InterruptedException {
        return currentLegalPositions.stream().reduce(null, (accumulated, position1) -> evaluate(accumulated) < evaluate(position1) ? position1 : accumulated);
    }

    private int evaluate(Position position) {
        if (position == null) {
            return 0;
        }
        return EVALUATION_MATRIX[position.row][position.column];
    }
}
