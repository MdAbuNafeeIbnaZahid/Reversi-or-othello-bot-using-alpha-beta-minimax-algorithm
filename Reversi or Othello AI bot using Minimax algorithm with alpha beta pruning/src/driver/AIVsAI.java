package driver;

import alpha_beta_minimax.ABMiniMax;
import alpha_beta_minimax.Action;
import alpha_beta_minimax.Heuristics;
import othello.AbsoluteCountHeuristic;
import othello.DiskColor;
import othello.OthelloState;
import othello.PositionalHeuristic;
import othelloStateFactory.OthelloStateFactory;

/**
 * Created by nafee on 12/13/17.
 */
public class AIVsAI {

    int depth;
    Heuristics h1;
    Heuristics h2;
    int iteration;

    ABMiniMax abMiniMax1;
    ABMiniMax abMiniMax2;


    public AIVsAI(int depth, Heuristics h1, Heuristics h2, int iteration) {

        if ( depth <= 0 )
        {
            throw new IllegalArgumentException("depth = " + depth);
        }

        if ( h1 == null )
        {
            throw new IllegalArgumentException(" h1 = " + h1);
        }

        if ( h2 == null )
        {
            throw new IllegalArgumentException(" h2 = " + h2);
        }

        if ( iteration < 1 )
        {
            throw new IllegalArgumentException(" iteration = " + iteration);
        }

        this.depth = depth;
        this.h1 = h1;
        this.h2 = h2;
        this.iteration = iteration;

        this.abMiniMax1 = new ABMiniMax(h1);
        this.abMiniMax2 = new ABMiniMax(h2);

    }


    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public Heuristics getH1() {
        return h1;
    }

    public void setH1(Heuristics h1) {
        this.h1 = h1;
    }

    public Heuristics getH2() {
        return h2;
    }

    public void setH2(Heuristics h2) {
        this.h2 = h2;
    }

    public int getIteration() {
        return iteration;
    }

    public void setIteration(int iteration) {
        this.iteration = iteration;
    }



    OthelloState initialState = OthelloStateFactory.getInitialOthelloState(DiskColor.WHITE);
    OthelloState currentState = initialState;



    // player 1 gives move first
    // player 1 one is white
    // evaluation and heuristic value is computed for player 1
    void playSingleGame()
    {
        while ( ! currentState.isTerminal() )
        {
            if ( currentState.isTerminal() )
            {
                break;
            }

            System.out.println("\n\n\n");
            System.out.println(h1);
            System.out.println(currentState);
            Action action1 = abMiniMax1.alphaBetaMaxSearch(currentState, depth);
            System.out.println( action1 );

            currentState = (OthelloState) currentState.getNextState(action1);
            System.out.println(currentState);

            if ( currentState.isTerminal() )
            {
                break;
            }

            System.out.println("\n\n\n");
            System.out.println( h2 );
            System.out.println(currentState);
            Action action2 = abMiniMax2.alphaBetaMinSearch(currentState, depth);
            System.out.println(action2);

            currentState = (OthelloState) currentState.getNextState(action2);
        }

        System.out.println(h1);
        System.out.println(h2);
        System.out.println("Game ends");
        System.out.println( currentState );
        System.out.println( "evaluation val = " +currentState.getEvaluationVal());
    }
}
