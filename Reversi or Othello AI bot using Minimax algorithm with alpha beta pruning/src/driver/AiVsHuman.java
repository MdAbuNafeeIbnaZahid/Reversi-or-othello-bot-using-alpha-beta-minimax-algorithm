package driver;

import alpha_beta_minimax.ABMiniMax;
import alpha_beta_minimax.Action;
import alpha_beta_minimax.Heuristics;
import othello.*;
import othelloStateFactory.OthelloStateFactory;

import java.util.List;
import java.util.Scanner;

/**
 * Created by nafee on 12/13/17.
 */
public class AiVsHuman
{
    Heuristics heuristics;
    int depth;
    ABMiniMax abMiniMax;

    public Heuristics getHeuristics() {
        return heuristics;
    }

    public void setHeuristics(Heuristics heuristics) {
        this.heuristics = heuristics;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public AiVsHuman(Heuristics heuristics, int depth) {
        this.heuristics = heuristics;
        this.depth = depth;

        this.abMiniMax = new ABMiniMax(heuristics);
    }




    OthelloState initialState = OthelloStateFactory.getInitialOthelloState(DiskColor.WHITE);
    OthelloState currentState = initialState;

    Action takeActionInput(OthelloState state)
    {

        List<Position> allPossibleMovePositions = state.getAllPossibleMovePositions();
        if ( allPossibleMovePositions.isEmpty() )
        {
            System.out.println("You don't have any move");
            return new OthelloNoMove();
        }

        Action ret = null;
        while (true)
        {
            String str = scanner.next();
            assert str.length() == 2 ;
            int row = str.charAt(0)-'0';
            int column = str.charAt(1)-'A';

            Position position = new Position(row, column);
            if ( allPossibleMovePositions.contains(position) )
            {
                 ret = new OthelloMove(position, state.getCurrentMoveColor() );
                    return ret;
            }


        }
    }

    Scanner scanner = new Scanner(System.in);

    // First player is human
    // human in white
    void playSingleGame()
    {
        while ( ! currentState.isTerminal() )
        {
            if ( currentState.isTerminal() )
            {
                break;
            }

            System.out.println("\n\n\n");
            System.out.println("Human");
            System.out.println(currentState);


            Action action1 = takeActionInput(currentState);
            System.out.println( action1 );

            currentState = (OthelloState) currentState.getNextState(action1);
            System.out.println(currentState);

            if ( currentState.isTerminal() )
            {
                break;
            }

            System.out.println("\n\n\n");
            System.out.println( heuristics );
            System.out.println(currentState);
            Action action2 = abMiniMax.alphaBetaMinSearch(currentState, depth);
            System.out.println(action2);

            currentState = (OthelloState) currentState.getNextState(action2);
        }

        System.out.println("Human");
        System.out.println(heuristics);
        System.out.println("Game ends");
        System.out.println( currentState );
        System.out.println( "evaluation val = " +currentState.getEvaluationVal());
    }
}
