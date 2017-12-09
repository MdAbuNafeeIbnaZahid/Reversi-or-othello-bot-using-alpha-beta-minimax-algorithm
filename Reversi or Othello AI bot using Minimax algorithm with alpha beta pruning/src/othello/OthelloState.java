package othello;

/**
 * Created by nafee on 12/9/17.
 */
public class OthelloState
{
    private DiskColor currentMoveColor;
    private Board board;

    public DiskColor getCurrentMoveColor() {
        return currentMoveColor;
    }

    public void setCurrentMoveColor(DiskColor currentMoveColor) {
        this.currentMoveColor = currentMoveColor;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}
