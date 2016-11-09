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

    private boolean timeIsUp = false;
    private final int INFINITY = 100000;
    private final int NEGATIVE_INFINITY = -10000;
    private final float TIME_LIMIT = 2000;

    private Color myColor;
    private Color opponentColor;
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
            int bestScore = NEGATIVE_INFINITY;
            Position currentBestPosition = null;

            for (Position position : currentLegalPositions) {
                int currentScore;
                GameBoard childBoard = new GameBoard(board.copyMatrix());
                childBoard.placeDisk(myColor, position);

                currentScore = minimax(childBoard, depth, NEGATIVE_INFINITY, INFINITY, false);

                if (timeIsUp) {
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
        return bestPosition;
    }


    public int minimax(GameBoard board, int depth, int alpha, int beta, boolean maxPlayer) {
        LinkedList<Position> legalPosition = board.getAllLegalPositions(myColor);
        int score = 0;

        if(depth == 0 || legalPosition.isEmpty()) {
            return costFunction(board);
        }

        if ((timeIsUp = isTimeUp())) {
            return score;
        }

        if (maxPlayer || !timeIsUp) {
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
        } else if(!timeIsUp) {
            score = INFINITY;
            for (Position position : legalPosition) {
                GameBoard childBoard = new GameBoard(board.copyMatrix());
                childBoard.placeDisk(Color.opposite(myColor), position);

                score = max(score, minimax(childBoard, depth - 1, alpha, beta, !maxPlayer));
                beta = min(beta, score);

                if (beta <= alpha) {
                    break;
                }
            }
        }

        return score;
    }

    private int max(int first, int second) {
        return first > second ? first : second;
    }

    private int min(int first, int second) {
        return first < second ? first : second;
    }

    private void startTimer() {
        startTime = System.currentTimeMillis();
    }

    private boolean isTimeUp() {
        if ((System.currentTimeMillis() - startTime) > (TIME_LIMIT - 500)) {
            return true;
        }

        return false;
    }

    private int costFunction(GameBoard board) {
        int nMyDisks = board.getNumberOfDisksInColor(myColor);
        int nMoves = board.getAllLegalPositions(myColor).size();
        int nCorners = countCorners(board);

        return 5*nMyDisks + 10*nMoves + 500*nCorners;
    }

    private int countCorners(GameBoard board) {
        int nCorners = 0;
        Color[][] boardMatrix = board.getBoardMatrix();

        for (int x = 0; x < 8; x+=7) {
            for (int y = 0; y < 8; y+=7) {
                if (boardMatrix[x][y] == myColor) {
                    nCorners++;
                }
            }
        }

        return nCorners;
    }
}
