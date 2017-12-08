package alpha_beta_minimax;


import java.util.List;

/**
 * Created by nafee on 12/8/17.
 */
public abstract class ABMiniMax
{
    abstract State getNextState(State oldState, Action action);

    Action alphaBetaSearch(State state)
    {
        MinimaxVal minimaxVal = getMaxVal(state, Integer.MIN_VALUE, Integer.MAX_VALUE);
        return minimaxVal.getAction();
    }

    MinimaxVal getMaxVal(State state, int alpha, int beta)
    {
        MinimaxVal ret = new MinimaxVal();
        if ( state.isTerminal() )
        {
            int score = state.getEvaluationVal();
            ret.setScore(score);

            ret.setAction(null);
            return ret;
        }

        ret.setScore(Integer.MIN_VALUE);

        List<Action> allPossibleActions = state.getAllPossibleActions();
        for  (Action action : allPossibleActions)
        {
            State nextState = getNextState(state, action);
            MinimaxVal minimaxVal = getMinVal(nextState, alpha, beta);

            if ( minimaxVal.getScore() > ret.getScore())
            {
                ret.setScore( minimaxVal.getScore() );
                ret.setAction( action );
            }

            if ( ret.getScore() >= beta )
            {
                return ret;
            }

            alpha = Integer.max(alpha, ret.getScore());
        }

        return ret;
    }

    MinimaxVal getMinVal( State state, int alpha, int beta )
    {
        MinimaxVal ret = new MinimaxVal();
        if ( state.isTerminal() )
        {
            int score = state.getEvaluationVal();
            ret.setScore(score);

            ret.setAction(null);
            return ret;
        }

        ret.setScore(Integer.MAX_VALUE);

        List<Action> allPossibleActions = state.getAllPossibleActions();
        for (Action action : allPossibleActions)
        {
            State nextState = getNextState(state, action);
            MinimaxVal minimaxVal = getMaxVal(nextState, alpha, beta);

            if ( minimaxVal.getScore() < ret.getScore() )
            {
                ret.setScore( minimaxVal.getScore() );
                ret.setAction( action );
            }

            if ( ret.getScore() <= alpha )
            {
                return ret;
            }

            beta = Integer.min(beta, ret.getScore());
        }

        return ret;
    }
}
