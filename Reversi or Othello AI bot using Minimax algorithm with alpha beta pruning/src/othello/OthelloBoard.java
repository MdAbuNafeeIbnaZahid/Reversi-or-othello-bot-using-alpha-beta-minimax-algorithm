package othello;

import alpha_beta_minimax.Action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by nafee on 12/9/17.
 */
public class OthelloBoard
{
    // row and columns are indexed from 0 to 7
    public final static int ROW = 8;
    public final static int COLUMN = 8;

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
                return 0;
            }

            assert ( isInBoard(currentPosition) );

            // Now 3 possibilites

            // currentPosition can contain NO disk at all (Swap can't be completed)
            //  currentPosition can contain same color as Action (End of swap)
            // currentPosition can conatin opponent color of Action (Increment swap)


            // currentPosition can contain NO disk at all (Swap can't be completed)
            if ( isBoardPositionEmpty(currentPosition) )
            {
                return 0;
            }

            //  currentPosition can contain same color as Action (End of swap)
            if ( isBoardPositionContainsColor(currentPosition, actionDiskColor) )
            {
                return ret;
            }

            // currentPosition can conatin opponent color of Action (Increment swap)
            if ( isBoardPositionContainsOpponentColor(currentPosition, actionDiskColor) )
            {
                ret++; // We are incrementing number of swaps

                currentPosition = currentPosition.getOneHopDisPos(direction);
            }
        }
    }

    int getTotalSwapCntInAllDirections( OthelloAction action )
    {
        assert action != null : "action can't be null";
        int ret = 0;
        for (Direction direction : allDirections)
        {
            int swapInThisDirection = getNumberOfSwapsInOneDirection(action, direction);
            ret += swapInThisDirection;
        }

        return ret;
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
}
