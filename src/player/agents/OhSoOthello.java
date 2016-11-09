package player.agents;

import game.Color;
import game.GameBoard;
import game.Position;
import player.Agent;

import java.util.LinkedList;

public class Bjaerta extends Agent {

    private static final int[][] EVALUATION_MATRIX = {
        {0, 0, 3, 1, 1, 3, 0, 0},
        {0, 0, 0, 2, 2, 0, 0, 0},
        {3, 0, 1, 1, 1, 1, 0, 3},
        {1, 2, 1, 0, 0, 1, 2, 1},
        {1, 2, 1, 0, 0, 1, 2, 1},
        {3, 0, 1, 1, 1, 1, 0, 3},
        {0, 0, 0, 2, 2, 0, 0, 0},
        {0, 0, 3, 1, 1, 3, 0, 0}
    };

    public Bjaerta(Color color) {
        super(color);
    }

    @Override
    public void newGame() {

    }

    @Override
    public Position nextMove(GameBoard board, LinkedList<Position> currentLegalPositions) throws InterruptedException {
        return null;
    }
}
