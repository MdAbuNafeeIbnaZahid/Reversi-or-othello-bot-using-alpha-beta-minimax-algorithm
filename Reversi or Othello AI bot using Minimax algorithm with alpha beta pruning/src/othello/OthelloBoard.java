package othello;

import alpha_beta_minimax.Action;

import java.util.*;

/**
 * Created by nafee on 12/9/17.
 */
public class OthelloBoard
{
    // row and columns are indexed from 0 to 7
    public final static int ROW = 8;
    public final static int COLUMN = 8;

    public static List<Position> getAllPositions()
    {
        List<Position> allPositions = new ArrayList<Position>();
        for (int a = 0; a < ROW; a++)
        {
            for (int b = 0; b < COLUMN; b++)
            {
                Position position = new Position(a, b);
                allPositions.add( position );
            }
        }

        return allPositions;
    }

    private static final List<Direction> allDirections = new ArrayList<Direction>();
    static
    {
        allDirections.add( new Direction(-1, 0) );
        allDirections.add( new Direction(-1, 1) );
        allDirections.add( new Direction(0, 1) );
        allDirections.add( new Direction(1, 1) );
        allDirections.add( new Direction(1, 0) );
        allDirections.add( new Direction(1, -1) );
        allDirections.add( new Direction(0, -1) );
        allDirections.add( new Direction(-1, -1) );
    }

    // row and column is numbered from 0
    boolean isInBoard(Position position)
    {
        assert position != null : "position can't be null";

        int row = position.row;
        int column = position.column;

        if ( row < 0 || row >= ROW )
        {
            return false;
        }

        if ( column < 0 || column >= COLUMN )
        {
            return false;
        }

        return true;
    }



    DiskColor[][] grid;


    public OthelloBoard()
    {
        grid = new DiskColor[ROW][COLUMN];
    }

    @Override
    public String toString() {
        return "OthelloBoard{" +
                "grid=" + Arrays.deepToString(grid) +
                '}';
    }


    DiskColor getDiskColor( Position position )
    {
        assert position != null : "Position can't be null";
        assert isInBoard(position) : " Position is outside of board ";

        int row = position.getRow();
        int column = position.getColumn();

        return grid[ row ][column];
    }

    boolean isBoardPositionEmpty( Position position )
    {
        assert isInBoard(position) : " position is outside of board. position = " + position;

        // When no disk is placed in a position the DiskColor of that position is null
        return getDiskColor(position) == null;
    }

    boolean isBoardPositionContainsColor( Position position, DiskColor expectedDiskColor )
    {
        assert position != null : " position can't be null ";


        // Note that expectedDiskColor can be null
        DiskColor diskColorInBoarPosition = getDiskColor(position);
        return expectedDiskColor.equals( diskColorInBoarPosition );
    }

    boolean isBoardPositionContainsOpponentColor( Position position, DiskColor diskColor )
    {
        assert position != null : " position can't be null " ;
        assert diskColor != null : "diskColor can't be null";

        DiskColor opponentColor = null;

        switch ( diskColor )
        {
            case BLACK:
                opponentColor = DiskColor.WHITE;
                break;
            case WHITE:
                opponentColor = DiskColor.BLACK;
                break;
            default:
                throw new RuntimeException(" Unknown Disk color ");
        }

        boolean ret = isBoardPositionContainsColor(position, opponentColor);
        return ret;
    }

    int getNumberOfSwapsInOneDirection( OthelloAction action, Direction direction )
    {
        Set<Position> swapPositionsInOneDirection = getSwapPositionsInOneDirection(action, direction);
        int swapCntInOneDirection = swapPositionsInOneDirection.size();

        return swapCntInOneDirection;
    }

    int getTotalSwapCntInAllDirections( OthelloAction action )
    {
        Set<Position> swapPositionsInAllDirection = getSwapPositionsInAllDirections(action);
        int swapCntInAllDirections = swapPositionsInAllDirection.size();

        return swapCntInAllDirections;
    }




    Set<Position> getSwapPositionsInOneDirection(OthelloAction action, Direction direction )
    {
        Set<Position> swapPositionsInOneDirection = new HashSet<Position>();

        assert action != null : "Action can't be null";
        assert direction != null : " direction can't be null ";

        Position actionPosition = action.getPosition();
        assert actionPosition != null : actionPosition;
        assert isInBoard(actionPosition) : "position of action is out of board ";

        assert isBoardPositionEmpty(actionPosition) : " position of action is already occupied" ;

        DiskColor actionDiskColor = action.getDiskColor();
        assert actionDiskColor != null : actionDiskColor;



        int ret = 0;

        Position startOfSwapPosition = actionPosition.getOneHopDisPos(direction);
        Position currentPosition = startOfSwapPosition;
        while (true)
        {
            if ( ! isInBoard(currentPosition) )
            {
                // We are out of board !!!!
                swapPositionsInOneDirection = new HashSet<Position>();
                return swapPositionsInOneDirection;
            }

            assert ( isInBoard(currentPosition) );

            // Now 3 possibilites

            // currentPosition can contain NO disk at all (Swap can't be completed)
            //  currentPosition can contain same color as Action (End of swap)
            // currentPosition can conatin opponent color of Action (Increment swap)


            // currentPosition can contain NO disk at all (Swap can't be completed)
            if ( isBoardPositionEmpty(currentPosition) )
            {
                swapPositionsInOneDirection = new HashSet<Position>();
                return swapPositionsInOneDirection;
            }

            //  currentPosition can contain same color as Action (End of swap)
            if ( isBoardPositionContainsColor(currentPosition, actionDiskColor) )
            {
                return swapPositionsInOneDirection;
            }

            // currentPosition can conatin opponent color of Action (Increment swap)
            if ( isBoardPositionContainsOpponentColor(currentPosition, actionDiskColor) )
            {
                swapPositionsInOneDirection.add(currentPosition);
                // We are adding current position with the the swapPositions

                currentPosition = currentPosition.getOneHopDisPos(direction);
            }
        }
    }

    Set<Position> getSwapPositionsInAllDirections( OthelloAction action )
    {
        assert action != null : "action can't be null";
        Set<Position> swapPositionsInAllDirections = new HashSet<Position>();
        for (Direction direction : allDirections)
        {
            Set<Position> swapPositionsInOneDirection = getSwapPositionsInOneDirection(action, direction);
            swapPositionsInAllDirections.addAll( swapPositionsInOneDirection );
        }

        return swapPositionsInAllDirections;
    }

    boolean isValidAction(OthelloAction action)
    {
        assert (action != null) : " action can't be null ";

        Position actionPosition = action.getPosition();

        if ( ! isInBoard(actionPosition) )
        {
            return false;
        }

        if ( ! isBoardPositionEmpty(actionPosition) )
        {
            return false;
        }

        assert isBoardPositionEmpty(actionPosition) : " action position must be empty ";

        int totalSwapCntInAllDirections = getTotalSwapCntInAllDirections(action);

        // for a valid action it needs to color at least one disk to its own color
        return totalSwapCntInAllDirections > 0;
    }


    int getWhiteCnt()
    {
        assert grid.length == ROW : " grid is deformed, grid.length = " + grid.length + ", " +
                " ROW = " + ROW;

        int ret = 0;

        for (DiskColor[] row : grid)
        {
            assert row.length == COLUMN : "grid is deformed, row.length = " + row.length + ", " +
                    " COLUMN =  " + COLUMN;

            for (DiskColor color : row)
            {
                if ( color.equals(DiskColor.WHITE) )
                {
                    ret++;
                }
            }
        }

        return ret;
    }

    int getBlackCnt()
    {
        assert grid.length == ROW : " grid is deformed, grid.length = " + grid.length + ", " +
                " ROW = " + ROW;

        int ret = 0;

        for (DiskColor[] row : grid)
        {
            assert row.length == COLUMN : "grid is deformed, row.length = " + row.length + ", " +
                    " COLUMN =  " + COLUMN;

            for (DiskColor color : row)
            {
                if ( color.equals(DiskColor.BLACK) )
                {
                    ret++;
                }
            }
        }

        return ret;
    }

    int getWhiteCntMinusBlackCnt()
    {
        int whiteCnt = getWhiteCnt();
        int blackCnt = getBlackCnt();
        int ret = whiteCnt - blackCnt;

        return ret;
    }
}
