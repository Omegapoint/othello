package player.agents;

import game.Color;
import game.GameBoard;
import game.Position;
import player.Agent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class Donald extends Agent {

    private static final int ITERATIONS = 0;
    private Color color;
    private HashMap<Position, Integer> val;

    public Donald(Color color) {
        super(color);
        this.color = color;
    }

    @Override
    public void newGame() {
        val = new HashMap<>();
    }

    @Override
    public Position nextMove(GameBoard board, LinkedList<Position> currentLegalPositions) throws InterruptedException {
        for(Position position : currentLegalPositions) {
            GameBoard copy = board.copyBoard();
            copy.placeDisk(this.color, position);
            int ret = recursive(copy, 0);
            val.put(position, ret);
        }

        Position position = currentLegalPositions.get(0);
        int max = 0;
        for(Position pos : val.keySet()) {
            if(val.get(pos) > max) {
                max = val.get(pos);
                position = pos;
            }
        }

        return position;
    }

    private int recursive(GameBoard board, int iteration) {
        ArrayList<Integer> returnValues = new ArrayList<>();
        Color turn;
        if(iteration % 2 == 0) {
            turn = this.color;
        } else {
            turn = this.color == Color.WHITE ? Color.BLACK : Color.WHITE;
        }

        if(iteration == ITERATIONS) {
            return evalGameBoard(board);
        } else {
            for (Position position : board.getAllLegalPositions(turn)) {
                GameBoard copy = board.copyBoard();
                copy.placeDisk(turn, position);
                int dist = recursive(copy, iteration++);
                returnValues.add(dist);
            }
        }
        int max = 0;
        for(Integer val : returnValues) {
            if(val > max) {
                max = val;
            }
        }
        return max;
    }

    private int evalGameBoard(GameBoard board) {
        int val = 0;
        Color[][] matrix = board.getBoardMatrix();
        for(int x = 0; x < matrix.length; x++) {
            for(int y = 0; y < matrix.length; y++) {
                if(matrix[x][y] == this.color) {
                    val++;
                }
            }
        }
        return val;
    }

}
