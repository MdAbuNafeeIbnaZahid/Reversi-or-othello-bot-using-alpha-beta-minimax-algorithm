package othello;

import alpha_beta_minimax.Action;
import alpha_beta_minimax.State;
import my_util.UnoptimizedDeepCopy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by nafee on 12/9/17.
 */
public class OthelloState implements State, Serializable
{
    private DiskColor currentMoveColor;
    private OthelloBoard othelloBoard;

    OthelloState getOpponentOthelloState()
    {
        OthelloState copyOfThisState = (OthelloState) UnoptimizedDeepCopy.copy(  this );
        DiskColor opponentDiskColor = DiskColor.getOpponentDiskColor(this.currentMoveColor);

        // We are chaning toggling the currentMoveColor of the copy
        copyOfThisState.setCurrentMoveColor(opponentDiskColor);
        OthelloState opponentOthelloState = copyOfThisState;

        return opponentOthelloState;
    }

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


    @Override
    public boolean isTerminal() {
        if ( currentMoveColor == null )
        {
            throw new RuntimeException(" currentMoveColor can't be null while checking for " +
                    " terminality ");
        }

        List<OthelloMove> allPossibleMoves = getAllPossibleMoves();
        if ( ! allPossibleMoves.isEmpty() )
        {
            // Current player has valid moves
            return false;
        }


        OthelloState opponentOthelloState = getOpponentOthelloState();
        List<OthelloMove> opponentAllPossibleMoves = opponentOthelloState.getAllPossibleMoves();
        if ( ! opponentAllPossibleMoves.isEmpty() )
        {
            // opponent has valid moves
            return false;
        }

        return true;
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

    // It does not return the NULL move. That is OthelloNoMove
    public List<OthelloMove> getAllPossibleMoves()
    {
        List<OthelloMove> allPossibleMoves = new ArrayList<OthelloMove>();

        assert currentMoveColor != null : " currentMoveColor can't be null ";
        List<OthelloMove> allMovesOfCurrentColor = OthelloBoard.getAllMovesOfAColor(currentMoveColor);

        for (OthelloMove othelloMove : allMovesOfCurrentColor)
        {
            if ( isValidMove(othelloMove) )
            {
                allPossibleMoves.add( othelloMove );
            }
        }

        return allPossibleMoves;
    }

    // it may return an OthelloNoMove
    @Override
    public List<Action> getAllPossibleActions()
    {
        List<Action> allPossibleActions = new ArrayList<Action>();

        List<OthelloMove> allPossibleMoves = getAllPossibleMoves();
        assert ( allPossibleMoves != null ) : " All possible moves can't be null ";


        allPossibleActions.addAll( allPossibleMoves );
        if ( allPossibleActions.isEmpty() )
        {
            Action nullAction = new OthelloNoMove();
            allPossibleActions.add(nullAction);
        }

        assert ( ! allPossibleActions.isEmpty() ) : "There is always a possible action." +
                "Though may be a nullAction";

        return allPossibleActions;
    }

    @Override
    public State getNextState(Action action) {

        if ( action == null )
        {
            throw new IllegalArgumentException(" action can't be null ");
        }


        if ( action instanceof OthelloNoMove )
        {
//            System.out.println("action instanceof OthelloNoMove");
            OthelloState nextState = ( OthelloState ) UnoptimizedDeepCopy.copy( this );

            assert nextState.currentMoveColor != null : " nextState is a clone. It can't have" +
                    " currentMoveColor as null ";

            DiskColor opponentMoveColor = DiskColor.getOpponentDiskColor(this.currentMoveColor);



            nextState.setCurrentMoveColor( opponentMoveColor );

            assert nextState.currentMoveColor != null : " nextState is a clone. It can't have" +
                    " currentMoveColor as null ";


            return nextState;
        }
        else if ( action instanceof OthelloMove )
        {
//            System.out.println(" action instanceof OthelloMove ");
            OthelloMove othelloMove = (OthelloMove)action;
            OthelloState nextState = ( OthelloState ) UnoptimizedDeepCopy.copy( this );

            assert nextState.getCurrentMoveColor() != null : " nextState is a clone. It can't have" +
                    " currentMoveColor as null ";


            nextState.othelloBoard.makeMove( othelloMove);
            assert nextState.getCurrentMoveColor() != null : " nextState is a clone. It can't have" +
                    " currentMoveColor as null ";


            nextState.currentMoveColor = DiskColor.getOpponentDiskColor( nextState.currentMoveColor );
            assert nextState.getCurrentMoveColor() != null : " nextState is a clone. It can't have" +
                    " currentMoveColor as null ";




            return nextState;
        }

        assert false : " Action must be either an OthelloNoMove or OthelloMove ";
        return null;
    }


    boolean OthelloMoveContainsSameDiskColor(OthelloMove othelloMove)
    {
        assert (othelloMove != null) : "othelloMove can't be null";

        DiskColor actionDiskColor = othelloMove.getDiskColor();
        assert actionDiskColor != null : " actionDiskColor can't be null ";

        assert (this.currentMoveColor != null) : " currentMoveColor can't be null ";

        return actionDiskColor.equals( this.currentMoveColor );
    }


    public boolean isValidMove(OthelloMove othelloMove) {

        if ( currentMoveColor == null )
        {
            throw new RuntimeException(" currentMoveColor can't be null ");
        }

        if ( othelloBoard == null )
        {
            throw  new RuntimeException(" othelloBoard can't be null ");
        }




        if ( ! OthelloMoveContainsSameDiskColor(othelloMove) )
        {
            // State is due to place a particular disk color
            // But the action intends to move the opposite color
            return false;
        }

        boolean ret = othelloBoard.isValidMove( othelloMove );

        return ret;
    }



    public List<Position> getAllPossibleMovePositions()
    {
        List<Position> allPossibleMovePositions = new ArrayList<Position>();

        List<OthelloMove> allPossibleMoves = getAllPossibleMoves();
        for ( OthelloMove othelloMove : allPossibleMoves )
        {
            Position movePosition = othelloMove.getPosition();
            assert movePosition != null : " movePosition can't be null ";
            allPossibleMovePositions.add( movePosition );
        }

        return allPossibleMovePositions;
    }


    @Override
    public String toString() {

        String ret = "OthelloState " + "\n";
        ret += "currentMoveColor = " + currentMoveColor + "\n";


        List<Position> allPossibleMovePositions = getAllPossibleMovePositions();

        ret += othelloBoard.toString( allPossibleMovePositions );
        ret += "\n";
        return ret;



//        return "OthelloState{" +
//                "currentMoveColor=" + currentMoveColor +
//                ", othelloBoard=" + othelloBoard +
//                '}';
    }
}
