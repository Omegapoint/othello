package player.agents;

import game.Color;
import game.GameBoard;
import game.Position;
import player.Agent;

import java.util.LinkedList;

/**
 * Created by SarahHale on 2016-11-09.
 */
public class Hillary extends Agent {

    int[][] scores = new int[][]
            {       {100, 90, 80, 60, 60, 80, 90, 100},
                    {90,  50, 20, 20, 20, 20, 50,  90},
                    {80, 20, 45, 40, 40, 45, 20, 80},
                    {60, 20, 40, 40, 40, 40, 20, 60},
                    {60, 20, 40, 40, 40, 40, 20, 60},
                    {80, 20, 45, 40, 40, 45, 20, 60},
                    {90,  50, 20, 20, 20, 20, 50,  90},
                    {100, 90, 80, 60, 60, 80, 90, 100}
            };

    public Hillary(Color color) {
        super(color);


    }


    public void newGame() {

    }


    public Position nextMove(GameBoard board, LinkedList<Position>
            currentLegalPositions) {
        Position chosenPosition = null;

        int max = -1;


        for (Position m : this.currentLegalPositions) {
            if (max < scores[m.row][m.column]) {
                max = scores[m.row][m.column];
                chosenPosition = m;
            }

        }

        return new Position(chosenPosition);

    }


}
