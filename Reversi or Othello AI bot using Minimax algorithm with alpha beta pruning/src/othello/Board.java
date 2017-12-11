package othello;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by nafee on 12/9/17.
 */
public class Board
{
    // row and columns are indexed from 0 to 7
    public final static int ROW = 8;
    public final static int COL = 8;

    private static final List<Direction> directions = new ArrayList<Direction>();
    static
    {
        directions.add( new Direction(-1, 0) );
        directions.add( new Direction(-1, 1) );
        directions.add( new Direction(0, 1) );
        directions.add( new Direction(1, 1) );
        directions.add( new Direction(1, 0) );
        directions.add( new Direction(1, -1) );
        directions.add( new Direction(0, -1) );
        directions.add( new Direction(-1, -1) );
    }


    DiskColor[][] grid;


    public Board()
    {
        grid = new DiskColor[ROW][COL];
    }

    @Override
    public String toString() {
        return "Board{" +
                "grid=" + Arrays.deepToString(grid) +
                '}';
    }

    boolean isValidAction()
    {
        
    }
}
