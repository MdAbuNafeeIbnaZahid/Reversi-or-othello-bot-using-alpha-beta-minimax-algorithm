package othello;

import alpha_beta_minimax.Heuristics;
import alpha_beta_minimax.State;

/**
 * Created by nafee on 12/12/17.
 */
public class AbsoluteCountHeuristic implements Heuristics {
    @Override
    public String toString() {
        return "AbsoluteCountHeuristic{}";
    }

    @Override
    public double getHVal(State state) {
        if ( ! ( state instanceof OthelloState ) )
        {
            throw new IllegalArgumentException(" state must be an othelloState ");
        }

        OthelloState othelloState = (OthelloState) state;

        return othelloState.getEvaluationVal();


    }
}
