package othello;

/**
 * Created by nafee on 12/9/17.
 */
public class OthelloState
{
    private DiskColor currentMoveColor;
    private OthelloBoard othelloBoard;

    public DiskColor getCurrentMoveColor() {
        return currentMoveColor;
    }

    public void setCurrentMoveColor(DiskColor currentMoveColor) {
        this.currentMoveColor = currentMoveColor;
    }

    public OthelloBoard getOthelloBoard() {
        return othelloBoard;
    }

    public void setOthelloBoard(OthelloBoard othelloBoard) {
        this.othelloBoard = othelloBoard;
    }
}
