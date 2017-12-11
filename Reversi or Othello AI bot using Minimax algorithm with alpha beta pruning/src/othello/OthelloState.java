package othello;

import alpha_beta_minimax.Action;
import alpha_beta_minimax.State;

import java.util.List;

/**
 * Created by nafee on 12/9/17.
 */
public class OthelloState implements State
{
    private DiskColor currentMoveColor;
    private OthelloBoard othelloBoard;

    public DiskColor getCurrentMoveColor() {
        return currentMoveColor;
    }

    public void setCurrentMoveColor(DiskColor currentMoveColor) {
        this.currentMoveColor = currentMoveColor;
    }

    public OthelloBoard getOthelloBoard() {
        return othelloBoard;
    }

    public void setOthelloBoard(OthelloBoard othelloBoard) {
        this.othelloBoard = othelloBoard;
    }



    // This evaluation val is White cnt - black cnt
    @Override
    public int getEvaluationVal() {

        if ( othelloBoard == null )
        {
            throw new RuntimeException(" othelloBoard can't be null ");
        }

        int ret = othelloBoard.getWhiteCntMinusBlackCnt();
        return ret;
    }



    @Override
    public boolean isValidAction(Action action) {

        if ( currentMoveColor == null )
        {
            throw new RuntimeException(" currentMoveColor can't be null ");
        }

        if ( othelloBoard == null )
        {
            throw  new RuntimeException(" othelloBoard can't be null ");
        }

        assert  ( action instanceof OthelloAction ) : " action must be an OthelloAction";
        OthelloAction othelloAction = (OthelloAction) action;

        boolean ret = othelloBoard.isValidAction( othelloAction );

        return ret;
    }
}
