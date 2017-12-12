package othello;

import alpha_beta_minimax.Heuristics;
import alpha_beta_minimax.State;

import java.util.Arrays;

/**
 * Created by nafee on 12/13/17.
 */
public class PositionalHeuristic implements Heuristics {

    @Override
    public String toString() {
        return "PositionalHeuristic{" +
                '}';
    }

    int[][] positionalGrid = new int[][]{

            { 4, -3, 2, 2, 2, 2, -3, 4},
            { -3, -4, -1, -1, -1, -1, -4, -3},
            { 2, -1, 1, 0, 0, 1, -1, 2},
            { 2, -1, 0, 1, 1, 0, -1, 2},
            { 2, -1, 0, 1, 1, 0, -1, 2},
            { 2, -1, 1, 0, 0, 1, -1, 2},
            { -3, -4, -1, -1, -1, -1, -4, -3},
            { 4, -3, 2, 2, 2, 2, -3, 4} };


    // This gives hVal with respect to white
    @Override
    public double getHVal(State state) {
        if ( ! ( state instanceof OthelloState ) )
        {
            throw new IllegalArgumentException(" state must be an othelloState ");
        }

        OthelloState othelloState = (OthelloState) state;

        OthelloBoard othelloBoard = othelloState.getOthelloBoard();

        double ret = 0;
        for (int a = 0; a < OthelloBoard.ROW; a++)
        {
            for (int b = 0; b < OthelloBoard.COLUMN; b++)
            {
                double curVal = positionalGrid[a][b];

                Position position = new Position(a, b);
                DiskColor diskColor = othelloBoard.getDiskColor(position);

                double curW = 0;
                if ( diskColor == null )
                {
                    curW = 0;
                }
                else
                {
                    switch (diskColor)
                    {
                        case WHITE: curW = 1;
                                    break;

                        case BLACK: curW = -1;
                                    break;

                        default:    assert false : " Unknown diskColor ";

                    }
                }


                double curPosHVal = (curW * curVal);
                ret +=  curPosHVal;
            }
        }

        return ret;
    }
}
