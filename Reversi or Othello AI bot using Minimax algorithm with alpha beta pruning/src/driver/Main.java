package driver;

import othello.DiskColor;
import othello.OthelloBoard;
import othello.OthelloState;
import othelloStateFactory.OthelloStateFactory;

/**
 * Created by nafee on 12/9/17.
 */
public class Main {

    public static void main(String[] args) {


        OthelloState initialState = OthelloStateFactory.getInitialOthelloState( DiskColor.WHITE );
        System.out.println(initialState);

    }
}
