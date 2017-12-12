package driver;

import alpha_beta_minimax.Heuristics;
import othello.*;
import othelloStateFactory.OthelloStateFactory;

/**
 * Created by nafee on 12/9/17.
 */
public class Main {

    public static void main(String[] args) {


//        OthelloState initialState = OthelloStateFactory.getInitialOthelloState( DiskColor.WHITE );
//        System.out.println(initialState);

        Heuristics positionalHeuristics = new PositionalHeuristic();
        Heuristics absoluteCntHeuristics = new AbsoluteCountHeuristic();

        AIVsAI aiVsAI = new AIVsAI(5, positionalHeuristics, absoluteCntHeuristics,
               20 );

        aiVsAI.playSingleGame();

    }
}
