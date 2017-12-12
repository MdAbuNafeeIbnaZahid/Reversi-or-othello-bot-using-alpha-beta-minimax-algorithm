package alpha_beta_minimax;


import java.util.List;

/**
 * Created by nafee on 12/8/17.
 */
public class ABMiniMax
{

    private Heuristics heuristics;

    public ABMiniMax(Heuristics heuristics) {

        if ( heuristics == null )
        {
            throw new IllegalArgumentException("heuristics can't be null");
        }
        this.heuristics = heuristics;
    }

    public Heuristics getHeuristics() {
        return heuristics;
    }

    public void setHeuristics(Heuristics heuristics) {
        this.heuristics = heuristics;
    }

    public Action alphaBetaMaxSearch(State state, int depth)
    {
        assert depth > 0 : " depth = " + depth ;
        MinimaxVal minimaxVal = getMaxVal(state, Integer.MIN_VALUE, Integer.MAX_VALUE, depth);
        return minimaxVal.getAction();
    }

    public Action alphaBetaMinSearch(State state, int depth)
    {
        assert depth > 0 : " depth = " + depth ;
        MinimaxVal minimaxVal = getMinVal(state, Integer.MIN_VALUE, Integer.MAX_VALUE, depth);
        return minimaxVal.getAction();
    }



    MinimaxVal getMaxVal(State state, double alpha, double beta, int depth)
    {
        MinimaxVal ret = new MinimaxVal();
        if ( state.isTerminal() )
        {
            int score = state.getEvaluationVal();
            ret.setScore(score);

            ret.setAction(null);
            return ret;
        }

        if ( depth <= 0 )
        {
            double hVal = heuristics.getHVal( state );
            ret.setScore( hVal );

            ret.setAction(null);
            return ret;
        }

        ret.setScore(Integer.MIN_VALUE);

        List<Action> allPossibleActions = state.getAllPossibleActions();
        for  (Action action : allPossibleActions)
        {
            State nextState = state.getNextState(action);
            MinimaxVal minimaxVal = getMinVal(nextState, alpha, beta, depth-1);

            if ( minimaxVal.getScore() > ret.getScore())
            {
                ret.setScore( minimaxVal.getScore() );
                ret.setAction( action );
            }

            if ( ret.getScore() >= beta )
            {
                return ret;
            }

            alpha = Double.max(alpha, ret.getScore());
        }

        return ret;
    }

    MinimaxVal getMinVal( State state, double alpha, double beta, int depth )
    {
        MinimaxVal ret = new MinimaxVal();
        if ( state.isTerminal() )
        {
            int score = state.getEvaluationVal();
            ret.setScore(score);

            ret.setAction(null);
            return ret;
        }

        if ( depth <= 0 )
        {
            assert heuristics != null : "heuristics can't be null";
            assert state != null : " state can't be null ";
            double hVal = heuristics.getHVal( state );
            ret.setScore( hVal );

            ret.setAction(null);
            return ret;
        }

        ret.setScore(Integer.MAX_VALUE);

        List<Action> allPossibleActions = state.getAllPossibleActions();
        for (Action action : allPossibleActions)
        {
            State nextState = state.getNextState( action);
            MinimaxVal minimaxVal = getMaxVal(nextState, alpha, beta, depth-1);

            if ( minimaxVal.getScore() < ret.getScore() )
            {
                ret.setScore( minimaxVal.getScore() );
                ret.setAction( action );
            }

            if ( ret.getScore() <= alpha )
            {
                return ret;
            }

            beta = Double.min(beta, ret.getScore());
        }

        return ret;
    }
}
