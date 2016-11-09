package player.agents;

import game.Color;
import game.GameBoard;
import game.Position;
import player.Agent;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;

public class Mitbot extends Agent{
    private int size;
    private Random r;
    private HashSet<Position> stables;

    public Mitbot(Color color) {
        super(color);
    }

    /**
     * This Method initializes the player when a new game starts. Time slot ~ 4 seconds.
     */
    @Override
    public void newGame() {
        r = new Random();
        size = 8;
        stables = new HashSet<>();
        for (int i = -1; i < size+1; i++) {
            stables.add(new Position(-1, i));
            stables.add(new Position(size+1, i));
            stables.add(new Position(i, -1));
            stables.add(new Position(i, size +1));
        }
    }

    /**
     * This method is called when it is the players turn to make a move. Time slot ~ 2 seconds.
     * If the player is not able to return a move within the given time slot, a random
     * move is choosen from the list of available moves. Any time not used will be added
     * to the next time this method is called.
     * <p/>
     * Before this method is called, all global variables are updated.
     *
     * @param board                 A copy of the current Board. The same variable as the global board in this class.
     * @param currentLegalPositions A copy of the legal positions. Same as the gloal list.
     * @return A choosen position for the next turn.
     * @throws InterruptedException
     */
    @Override
    public Position nextMove(GameBoard board, LinkedList<Position> currentLegalPositions) throws InterruptedException {

        for (Position p: currentLegalPositions) {
            if(stable(p)){
                stables.add(p);
                return p;
            }
        }
        int i = r.nextInt(currentLegalPositions.size());
        Position p = currentLegalPositions.get(i);
        return currentLegalPositions.get(i);
    }

    private boolean stable(Position p) {
        if(isCorner(p)){
            return true;
        }
        if(stables.contains(new Position(p.row -1 ,p.column - 1))) {
            if(stables.contains(new Position(p.row,p.column-1)) &&
                    stables.contains(new Position(p.row-1, p.column))){
                if(stables.contains(new Position(p.row -1, p.column +1)) ||
                        stables.contains(new Position(p.row +1, p.column -1))){
                    return true;
                }
            }
        }

        if(stables.contains(new Position(p.row -1 ,p.column + 1))) {
            if(stables.contains(new Position(p.row,p.column+1)) &&
                    stables.contains(new Position(p.row-1, p.column))){
                if(stables.contains(new Position(p.row -1, p.column -1)) ||
                        stables.contains(new Position(p.row +1, p.column +1))){
                    return true;
                }
            }
        }

        if(stables.contains(new Position(p.row + 1 ,p.column - 1))) {
            if(stables.contains(new Position(p.row,p.column-1)) &&
                    stables.contains(new Position(p.row+1, p.column))){
                if(stables.contains(new Position(p.row -1, p.column -1)) ||
                        stables.contains(new Position(p.row +1, p.column +1))){
                    return true;
                }
            }
        }

        if(stables.contains(new Position(p.row +1 ,p.column + 1))) {
            if(stables.contains(new Position(p.row,p.column+1)) &&
                    stables.contains(new Position(p.row+1, p.column))){
                if(stables.contains(new Position(p.row -1, p.column +1)) ||
                        stables.contains(new Position(p.row +1, p.column -1))){
                    return true;
                }
            }
        }

        return false;
    }

    private boolean isCorner (Position p) {
        return ((p.row == 0 && p.column == 0)
                || (p.row == 0 && p.column == size -1)
                || (p.row == size - 1 && p.column == 0)
                || (p.row == size -1 && p.column == size -1));
    }
}
