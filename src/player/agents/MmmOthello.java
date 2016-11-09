package player.agents;

import java.util.LinkedList;
import java.util.Random;

import game.Color;
import game.GameBoard;
import game.Position;
import player.Agent;


public class MmmOthello extends Agent {


    private Random random;
    private int boardSize;

    public MmmOthello(Color color) {
        super(color);
    }


    @Override
    public void newGame() {
        this.random = new Random();
        System.out.println(NAME + " is alive! :)");
        this.boardSize = 8;
    }

    @Override
    public Position nextMove(GameBoard currentBoard, LinkedList<Position> currentLegalPositions) throws InterruptedException {

        int randomIndex = random.nextInt(currentLegalPositions.size());
        Position choosenPosition = this.currentLegalPositions.get(randomIndex);
        Color oppositeColor = getOppositeColor();

        int maxNumberOfMoves = Integer.MAX_VALUE;
        for(Position p : this.currentLegalPositions) {

            if(coordinateIsACorner(p)) {
                choosenPosition = p;
                break;
            }

            GameBoard tempBoard = this.currentBoard.copyBoard();
            tempBoard.placeDisk(this.color, p);
            int numberOfMovesForOpponent = tempBoard.getAllLegalPositions(oppositeColor).size();

            if(numberOfMovesForOpponent < maxNumberOfMoves) {
                choosenPosition = new Position(p);
            }
        }


        return choosenPosition;
    }

    private Color getOppositeColor() {
        if(this.color == Color.WHITE) return Color.BLACK;
        return color.WHITE;
    }

    private boolean coordinateIsACorner(Position position) {
        int row = position.row % (this.boardSize - 1);
        int col = position.column % (this.boardSize - 1);

        if(row == 0 && col == 0) return true;

        return false;
    }

}
