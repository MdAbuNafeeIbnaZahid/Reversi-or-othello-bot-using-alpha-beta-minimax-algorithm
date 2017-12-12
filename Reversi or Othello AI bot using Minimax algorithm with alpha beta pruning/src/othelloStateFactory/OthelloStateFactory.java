package othelloStateFactory;

import othello.DiskColor;
import othello.OthelloBoard;
import othello.OthelloState;

/**
 * Created by nafee on 12/13/17.
 */
public class OthelloStateFactory {
    public static OthelloState getInitialOthelloState(DiskColor firstMoveColor )
    {
        OthelloState othelloState = new OthelloState();

        OthelloBoard othelloBoard = new OthelloBoard();
        othelloState.setOthelloBoard(othelloBoard);

        othelloState.setCurrentMoveColor(firstMoveColor);

        return othelloState;
    }
}
