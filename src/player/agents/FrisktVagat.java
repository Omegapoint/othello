package player.agents;

import game.Color;
import game.GameBoard;
import game.Position;
import player.Agent;

import java.util.LinkedList;

/**
 * Created by KaninJaevel on 09/11/2016.
 */
public class FrisktVagat extends Agent {

    Position bestPosition;
    int bestTurns;
    LinkedList<Position> cP;
    int rowTablet[]= {-1,-1,0,1,1,1,0,-1};
    int colTablet[]= {0,-1,-1,-1,0,1,1,1};

    public FrisktVagat(Color color) {
        super(color);
    }

    @Override
    public void newGame() {

    }

    @Override
    public Position nextMove(GameBoard board, LinkedList<Position> currentLegalPositions) throws InterruptedException {
        currentBoard=board;
        cP = currentLegalPositions;
        bestTurns=0;
        bestPosition=null;
        for(int i=0; i<cP.size();i++){
            checkPosition(cP.get(i));
        }

        return bestPosition;
    }

    private void checkPosition(Position p){
        int noOfTurns=0;
        int temp=0;
        Position tempPositon;

        if(isCornerpiece(p)) {
            bestPosition = p;
            bestTurns= 9999999;
        }
        else {
            for (int v = 0; v < 8; v++) {
                tempPositon = new Position(p.row + rowTablet[v], p.column + colTablet[v]);
                if (tempPositon.row + rowTablet[v] > 7 || tempPositon.row + rowTablet[v] < 0 ||
                        tempPositon.column + colTablet[v] > 7 || tempPositon.column + colTablet[v] < 0) {
                } else if (currentBoard.getPositionColor(tempPositon) == Color.opposite(color)) {
                    temp++;
                    boolean isSame = false;
                    int i = 2;
                    while (!isSame) {
                        tempPositon = new Position(p.row + (rowTablet[v] * i), (p.column + colTablet[v] * i));
                        if (tempPositon.row == 0 || tempPositon.row == 7
                                || tempPositon.column == 0 || tempPositon.column == 7) {
                            isSame = true;
                        } else if (currentBoard.getPositionColor(tempPositon).equals(Color.opposite(color))) {
                            temp++;
                        } else if (!currentBoard.getPositionColor(tempPositon).equals(color)) {
                            noOfTurns += temp;
                            isSame = true;
                        }
                        i++;
                    }
                }
            }
            if(noOfTurns>bestTurns)
                bestPosition=p;
            else if(noOfTurns==bestTurns&&bestPosition!=null)
                if(p.row%7<bestPosition.row%7||p.column%7<bestPosition.column%7)
                    bestPosition=p;
        }
    }

    public boolean isCornerpiece(Position p){
        boolean isCornerpiece=false;
        if(p.row==0&&p.column==0)
            isCornerpiece=true;
        else if(p.row==7&&p.column==0)
            isCornerpiece=true;
        if(p.row==7&&p.column==7)
            isCornerpiece=true;
        else if(p.row==0&&p.column==7)
            isCornerpiece=true;
        return isCornerpiece;
    }
}
