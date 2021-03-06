package player.agents;

import game.Color;
import game.GameBoard;
import game.Position;
import player.Agent;

import javax.naming.TimeLimitExceededException;
import java.util.LinkedList;

/**
 * Created by david on 11/9/16.
 */
public class Dolores extends Agent {

    private final float INFINITY = 100000;
    private final float NEGATIVE_INFINITY = -10000;
    private final float TIME_LIMIT = 2000;

    private Color myColor;
    private long startTime;

    public Dolores(Color color) {
        super(color);

        myColor = color;
    }

    @Override
    public void newGame() {

    }

    @Override
    public Position nextMove(GameBoard board, LinkedList<Position> currentLegalPositions) throws InterruptedException {
        int depth = 1;
        boolean proceed = true;
        Position bestPosition = null;

        startTimer();

        while (proceed) {
            float bestScore = NEGATIVE_INFINITY;
            Position currentBestPosition = null;

            for (Position position : currentLegalPositions) {
                float currentScore;
                GameBoard childBoard = new GameBoard(board.copyMatrix());
                childBoard.placeDisk(myColor, position);

                currentScore = minimax(childBoard, depth, NEGATIVE_INFINITY, INFINITY, false);

                if (isTimeUp()) {
                    proceed = false;
                    break;
                }

                if (currentScore > bestScore) {
                    bestScore = currentScore;
                    currentBestPosition = position;
                }

            }
            depth++;

            if (proceed) {
                bestPosition = currentBestPosition;
            }

        }
        return new Position(bestPosition);
    }


    public float minimax(GameBoard board, int depth, float alpha, float beta, boolean maxPlayer) {
        LinkedList<Position> legalPosition = board.getAllLegalPositions(myColor);
        float score = 0;

        if(depth == 0 || legalPosition.isEmpty()) {
            return costFunction(board);
        }

        if (isTimeUp()) {
            return score;
        }

        if (maxPlayer && !isTimeUp()) {
            score = NEGATIVE_INFINITY;
            for (Position position : legalPosition) {
                GameBoard childBoard = new GameBoard(board.copyMatrix());
                childBoard.placeDisk(myColor, position);

                score = max(score, minimax(childBoard, depth - 1, alpha, beta, !maxPlayer));
                alpha = max(alpha, score);

                if (beta <= alpha) {
                    break;
                }
            }
            return score;
        } else if(!isTimeUp()) {
            score = INFINITY;
            for (Position position : legalPosition) {
                GameBoard childBoard = new GameBoard(board.copyMatrix());
                childBoard.placeDisk(Color.opposite(myColor), position);

                score = min(score, minimax(childBoard, depth - 1, alpha, beta, !maxPlayer));
                beta = min(beta, score);

                if (beta <= alpha) {
                    break;
                }
            }
            return score;
        }

        return score;
    }

    private float max(float first, float second) {
        return first > second ? first : second;
    }

    private float min(float first, float second) {
        return first < second ? first : second;
    }

    private void startTimer() {
        startTime = System.currentTimeMillis();
    }

    private boolean isTimeUp() {
        if ((System.currentTimeMillis() - startTime) > (TIME_LIMIT - 10)) {
            return true;
        }

        return false;
    }

    private float costFunction(GameBoard board) {
        int nWMyDisks = board.getNumberOfDisksInColor(myColor);
        int nWMoves = board.getAllLegalPositions(myColor).size();
        int nWCorners = countCorners(board, myColor);
        int nWEdgeDisks = countEdges(board, myColor);

        int nBMyDisks = board.getNumberOfDisksInColor(Color.opposite(myColor));
        int nBMoves = board.getAllLegalPositions(Color.opposite(myColor)).size();
        int nBCorners = countCorners(board, Color.opposite(myColor));
        int nBEdgeDisks = countEdges(board, Color.opposite(myColor));

        float score = 5*scoreParity(nWMyDisks, nBMyDisks) + 10*scoreParity(nWMoves, nBMoves) +
                50*scoreParity(nWCorners, nBCorners) + 15*scoreParity(nWEdgeDisks, nBEdgeDisks);

        System.out.println(score);

        return score;
    }

    private int countCorners(GameBoard board, Color color) {
        int nCorners = 0;
        Color[][] boardMatrix = board.getBoardMatrix();

        for (int x = 0; x < 8; x+=7) {
            for (int y = 0; y < 8; y+=7) {
                if (boardMatrix[x][y] == color) {
                    nCorners++;
                }
            }
        }

        return nCorners;
    }

    private int countEdges(GameBoard board, Color color) {
        int y;
        int x;
        int edgeDisks = 0;
        Color[][] boardMatrix = board.getBoardMatrix();

        y = 0;
        for(x=0; x<8; x++){
            if (boardMatrix[x][y] == color) {
                edgeDisks++;
            }
        }

        y = 7;
        for(x=0; x<8; x++){
            if (boardMatrix[x][y] == color) {
                edgeDisks++;
            }
        }

        x=0;
        for(y=1; y<7; y++){
            if (boardMatrix[x][y] == color) {
                edgeDisks++;
            }
        }

        x=7;
        for(y=1; y<7; y++){
            if (boardMatrix[x][y] == color) {
                edgeDisks++;
            }
        }

        return edgeDisks;
    }

    public float scoreParity(int maxScore, int minScore) {
        if((maxScore + minScore) > 0)
        {
            return 10*((float)(maxScore - minScore)/(float)(maxScore + minScore));
        } else {
            return 0;
        }
    }
}
