package othello;

/**
 * Created by nafee on 12/9/17.
 */
public class Direction
{
    private int dRow;
    private int dColumn;

    public Direction(int dr, int dc) {
        this.dRow = dr;
        this.dColumn = dc;
    }

    public int getdRow() {
        return dRow;
    }

    public void setdRow(int dr) {
        this.dRow = dr;
    }

    public int getdColumn() {
        return dColumn;
    }

    public void setdColumn(int dColumn) {
        this.dColumn = dColumn;
    }
}
