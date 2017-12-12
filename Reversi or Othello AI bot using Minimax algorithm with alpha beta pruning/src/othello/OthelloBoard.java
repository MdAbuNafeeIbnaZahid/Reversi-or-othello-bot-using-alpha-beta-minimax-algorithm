package othello;

import alpha_beta_minimax.Action;

import java.io.Serializable;
import java.util.*;

/**
 * Created by nafee on 12/9/17.
 */
public class OthelloBoard implements Serializable
{
    // row and columns are indexed from 0 to 7
    public final static int ROW = 8;
    public final static int COLUMN = 8;


    public static List<OthelloMove> getAllMovesOfAColor( DiskColor diskColor )
    {
        if ( diskColor == null )
        {
            throw new IllegalArgumentException("diskColor can't be null ");
        }

        List<OthelloMove> allMovesOfAColor = new ArrayList<OthelloMove>();
        List<Position> allPositions = getAllPositions();

        for (Position position : allPositions)
        {
            OthelloMove action = new OthelloMove(position, diskColor);
            allMovesOfAColor.add(action);
        }

        return allMovesOfAColor;
    }

    public static List<OthelloMove> getAllMovesOfAllColor( DiskColor diskColor )
    {
        List<OthelloMove> allActionsOfAllColors = new ArrayList<OthelloMove>();

        List<OthelloMove> allActionsOfWhite = getAllMovesOfAColor(DiskColor.WHITE);
        assert ( allActionsOfWhite != null ) : " allActionsOfWhite is null ";
        allActionsOfAllColors.addAll(allActionsOfWhite);

        List<OthelloMove> allActionsOfBlack = getAllMovesOfAColor(DiskColor.BLACK);
        assert (allActionsOfBlack != null) : " allActionsOfBlack can't be null ";
        allActionsOfAllColors.addAll(allActionsOfBlack);

        return allActionsOfAllColors;
    }


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

        int row = position.getRow();
        int column = position.getColumn();

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

    void setDiskColorOfAPosition(Position position, DiskColor diskColor)
    {
        assert position != null : " position must not be null ";
        assert isInBoard(position) : "position is outside of board";

        int row = position.getRow();
        int column = position.getColumn();

        grid[ row ][ column ] = diskColor;
    }

    DiskColor getDiskColorOfAPosition( Position position )
    {
        assert position != null : " position must not be null ";
        assert isInBoard(position) : "position is outside of board";

        int row = position.getRow();
        int column = position.getColumn();

        return grid[row][column];
    }

    void toggleADisk(Position position)
    {
        assert position != null : " position can't be null ";

        assert isInBoard(position) : " position is outside of board ";

        assert getDiskColor(position) != null : " before toggling diskColor can't be null ";

        DiskColor currentDiskColor = getDiskColor(position);
        DiskColor oppositeDiskColor = DiskColor.getOpponentDiskColor(currentDiskColor);

        setDiskColorOfAPosition(position, oppositeDiskColor);
    }


    public OthelloBoard()
    {
        grid = new DiskColor[ROW][COLUMN];
        grid[ 3 ][ 3 ] = DiskColor.WHITE;
        grid[ 3 ][ 4 ] = DiskColor.BLACK;

        grid[ 4 ][ 3 ] = DiskColor.BLACK;
        grid[ 4 ][ 4 ] = DiskColor.WHITE;
    }

    @Override
    public String toString() {


        String ret = "\n\n";

        ret += "     ";
        for (int a = 0; a < COLUMN; a++)
        {
            ret += "  " + (char)(a+'A') + "  ";
        }
        ret += "\n";

        for ( int a = 0; a < ROW; a++ )
        {
            ret += "  " + a + "  ";
            for (int b = 0; b < COLUMN; b++)
            {
                DiskColor diskColor = grid[a][b];
                String col = "";

                if ( diskColor == null )
                {
                    col = "_";
                }
                else
                {
                    switch ( diskColor )
                    {
                        case WHITE: col = "W";
                                    break;

                        case BLACK: col = "B";
                                    break;

                        default:    throw new RuntimeException(" Unknown DiskColor type ");

                    }
                }



                ret += "  " + col + "  ";

            }

            ret += "\n\n";
        }

        return ret;

    }





    public String toString( List<Position> allPossibleMovePositions) {


        String ret = "\n\n";

        ret += "     ";
        for (int a = 0; a < COLUMN; a++)
        {
            ret += "  " + (char)(a+'A') + "  ";
        }
        ret += "\n";

        for ( int a = 0; a < ROW; a++ )
        {
            ret += "  " + a + "  ";
            for (int b = 0; b < COLUMN; b++)
            {
                Position currentPosition = new Position(a, b);
                DiskColor diskColor = grid[a][b];
                String col = "";

                if ( diskColor == null )
                {
                    col = "_";
                    if ( allPossibleMovePositions.contains(currentPosition) )
                    {
                        col = "?";
                    }
                }
                else
                {
                    assert ! allPossibleMovePositions.contains( currentPosition ) : " we can't" +
                            "place a disk on top of another disk ";

                    switch ( diskColor )
                    {
                        case WHITE: col = "W";
                            break;

                        case BLACK: col = "B";
                            break;

                        default:    throw new RuntimeException(" Unknown DiskColor type ");

                    }
                }



                ret += "  " + col + "  ";

            }

            ret += "\n\n";
        }

        return ret;

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

    int getNumberOfSwapsInOneDirection( OthelloMove action, Direction direction )
    {
        assert (action != null) : "action can't be null";
        assert (direction != null) : "direction can't be null";

        Set<Position> swapPositionsInOneDirection = getSwapPositionsInOneDirection(action, direction);
        int swapCntInOneDirection = swapPositionsInOneDirection.size();

        return swapCntInOneDirection;
    }

    int getTotalSwapCntInAllDirections( OthelloMove action )
    {
        assert (action != null) : "action can't be null";

        Set<Position> swapPositionsInAllDirection = getSwapPositionsInAllDirections(action);
        int swapCntInAllDirections = swapPositionsInAllDirection.size();

        return swapCntInAllDirections;
    }




    Set<Position> getSwapPositionsInOneDirection(OthelloMove action, Direction direction )
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


    void makeMove( OthelloMove othelloMove )
    {
        assert othelloMove != null : " othelloMoveCan't be null ";

        assert isValidMove(othelloMove) : " otelloMove is not valid ";


        Set<Position> swapPositionsInAllDirections = getSwapPositionsInAllDirections(othelloMove);
        assert ! swapPositionsInAllDirections.isEmpty() : " a valid move " +
                "will have some disks to toggle ";
        for (Position position : swapPositionsInAllDirections)
        {
            toggleADisk( position );
        }

        // finally setting the color of newDisk
        setDiskColorOfAPosition(othelloMove.getPosition(), othelloMove.getDiskColor());
    }



    Set<Position> getSwapPositionsInAllDirections( OthelloMove othelloMove )
    {
        assert othelloMove != null : "othelloMove can't be null";
        Set<Position> swapPositionsInAllDirections = new HashSet<Position>();
        for (Direction direction : allDirections)
        {
            Set<Position> swapPositionsInOneDirection = getSwapPositionsInOneDirection(othelloMove, direction);
            swapPositionsInAllDirections.addAll( swapPositionsInOneDirection );
        }

        return swapPositionsInAllDirections;
    }

    boolean isValidMove(OthelloMove action)
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


    int getDiskColorCnt(DiskColor diskColor)
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

                if ( color == diskColor )
                {
                    ret++;
                }
            }
        }

        return ret;
    }



    int getWhiteCntMinusBlackCnt()
    {
        int whiteCnt = getDiskColorCnt(DiskColor.WHITE);
        int blackCnt = getDiskColorCnt(DiskColor.BLACK);
        int ret = whiteCnt - blackCnt;

        return ret;
    }
}
