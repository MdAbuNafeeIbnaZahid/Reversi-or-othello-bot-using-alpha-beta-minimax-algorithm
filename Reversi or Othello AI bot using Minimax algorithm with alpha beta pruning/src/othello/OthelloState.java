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


    boolean othelloActionContainsSameDiskColor(OthelloAction action)
    {
        assert (action != null) : "action can't be null";

        DiskColor actionDiskColor = action.getDiskColor();
        assert actionDiskColor != null : " actionDiskColor can't be null ";

        assert (this.currentMoveColor != null) : " currentMoveColor can't be null ";

        return actionDiskColor.equals( this.currentMoveColor );
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

        if ( !  ( action instanceof OthelloAction ) )
        {
            throw new IllegalArgumentException("  action must be an OthelloAction ");
        }
        OthelloAction othelloAction = (OthelloAction) action;



        if ( ! othelloActionContainsSameDiskColor(othelloAction) )
        {
            // State is due to place a particular disk color
            // But the action intends to move the opposite color
            return false;
        }

        boolean ret = othelloBoard.isValidAction( othelloAction );

        return ret;
    }
}
