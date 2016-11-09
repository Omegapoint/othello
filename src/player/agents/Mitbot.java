package player.agents;

import game.Color;
import game.GameBoard;
import game.Position;
import javafx.geometry.Pos;
import player.Agent;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;

public class Mitbot extends Agent{
    private int size;
    private Random r;
    private HashSet<WrapPosition> stables;

    public Mitbot(Color color) {
        super(color);
    }

    class WrapPosition {
        public int row, column;

        public WrapPosition(int row, int column){
            this.row = row;
            this.column = column;
        }

        public boolean equalsPos(Position pos) {
            return (row == pos.row && column == pos.column);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o != null && o.getClass() == Position.class) return equalsPos((Position) o);
            if (o == null || getClass() != o.getClass()) return false;

            WrapPosition that = (WrapPosition) o;

            if (row != that.row) return false;
            return column == that.column;

        }

        @Override
        public int hashCode() {
            int result = row;
            result = 31 * result + column;
            return result;
        }

        @Override
        public String toString() {
            return "WrapPosition{" +
                    "row=" + row +
                    ", column=" + column +
                    '}';
        }
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
            stables.add(new WrapPosition(-1, i));
            stables.add(new WrapPosition(size, i));
            stables.add(new WrapPosition(i, -1));
            stables.add(new WrapPosition(i, size));
        }
        for(WrapPosition p : stables) {
          //  System.out.println(p.toString());
        }
        //System.out.println("stable size after creation: " + stables.size());
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

        updateStables(board);
        for (Position p: currentLegalPositions) {
            if(stable(new WrapPosition(p.row,p.column))){
                //System.out.println("Found stable position when doing next move\nrow: " + p.row + " col: " + p.column);
                stables.add(new WrapPosition(p.row,p.column));
                return p;
            }
        }
        int i = r.nextInt(currentLegalPositions.size());
        Position p = currentLegalPositions.get(i);
        return currentLegalPositions.get(i);
    }

    private void updateStables(GameBoard board) {
        WrapPosition p;
        boolean added = true;
        Color c[][] = board.getBoardMatrix();
        while (added) {
            added = false;
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (!stables.contains(p = new WrapPosition(i, j)) && (c[i][j] == color) && stable(p)) {
                        stables.add(p);
                        //System.out.println("Found stable position while checking board\nrow: " + p.row + " col: " + p.column);
                        added = true;
                        break;
                    }
                    if(added) {
                        break;
                    }
                }
            }
        }
    }

    private boolean stable(WrapPosition p) {

        if(isCorner(p)){
            //System.out.println("found corner!");
            return true;
        }
        if(stables.contains(new WrapPosition(p.row -1 ,p.column - 1))) {
            if(stables.contains(new WrapPosition(p.row,p.column-1)) &&
                    stables.contains(new WrapPosition(p.row-1, p.column))){
                if(stables.contains(new WrapPosition(p.row -1, p.column +1)) ||
                        stables.contains(new WrapPosition(p.row +1, p.column -1))){
                    return true;
                }
            }
        }

        if(stables.contains(new WrapPosition(p.row -1 ,p.column + 1))) {
            if(stables.contains(new WrapPosition(p.row,p.column+1)) &&
                    stables.contains(new WrapPosition(p.row-1, p.column))){
                if(stables.contains(new WrapPosition(p.row -1, p.column -1)) ||
                        stables.contains(new WrapPosition(p.row +1, p.column +1))){
                    return true;
                }
            }
        }

        if(stables.contains(new WrapPosition(p.row + 1 ,p.column - 1))) {
            if(stables.contains(new WrapPosition(p.row,p.column-1)) &&
                    stables.contains(new WrapPosition(p.row+1, p.column))){
                if(stables.contains(new WrapPosition(p.row -1, p.column -1)) ||
                        stables.contains(new WrapPosition(p.row +1, p.column +1))){
                    return true;
                }
            }
        }

        if(stables.contains(new WrapPosition(p.row +1 ,p.column + 1))) {
            if(stables.contains(new WrapPosition(p.row,p.column+1)) &&
                    stables.contains(new WrapPosition(p.row+1, p.column))){
                if(stables.contains(new WrapPosition(p.row -1, p.column +1)) ||
                        stables.contains(new WrapPosition(p.row +1, p.column -1))){
                    return true;
                }
            }
        }

        return false;
    }

    private boolean isCorner (WrapPosition p) {
        return ((p.row == 0 && p.column == 0)
                || (p.row == 0 && p.column == size -1)
                || (p.row == size - 1 && p.column == 0)
                || (p.row == size -1 && p.column == size -1));
    }
}
