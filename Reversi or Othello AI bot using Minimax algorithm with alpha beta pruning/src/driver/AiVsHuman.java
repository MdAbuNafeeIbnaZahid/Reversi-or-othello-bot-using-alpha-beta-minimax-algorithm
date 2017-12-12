package driver;

import alpha_beta_minimax.ABMiniMax;
import alpha_beta_minimax.Heuristics;
import othello.DiskColor;
import othello.OthelloState;
import othelloStateFactory.OthelloStateFactory;

import java.util.Scanner;

/**
 * Created by nafee on 12/13/17.
 */
public class AiVsHuman
{
    Heuristics heuristics;
    int depth;

    public AiVsHuman(Heuristics heuristics) {
        this.heuristics = heuristics;
    }

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
    }

    ABMiniMax abMiniMax = new ABMiniMax(heuristics);


    OthelloState initialState = OthelloStateFactory.getInitialOthelloState(DiskColor.WHITE);
    OthelloState currentState = initialState;

    Scanner scanner = new Scanner(System.in);

    void startGame()
    {
        while ( true )
        {
            // first player is human

        }
    }
}
