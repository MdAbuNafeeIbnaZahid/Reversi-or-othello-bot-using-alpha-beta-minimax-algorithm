package alpha_beta_minimax;

import java.util.List;

/**
 * Created by nafee on 12/8/17.
 */
public interface State {
    boolean isTerminal();

    int getEvaluationVal(); // It should work only for terminal states

    List<Action> getAllPossibleActions();

//    MinimaxVal calcMaxVal(int alpha, int beta, int moreDepthToDive);
//
//    MinimaxVal calcMinVal(int alpha, int beta, int moreDepthToDive);

    boolean isValidAction( Action action );

}
