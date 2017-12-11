package othello;

/**
 * Created by nafee on 12/11/17.
 */
public class Position
{
    int row;
    int column;

    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    @Override
    public String toString() {
        return "Position{" +
                "row=" + row +
                ", column=" + column +
                '}';
    }

    Position getOneHopDisPos( Direction direction )
    {
        assert direction != null : "Direction can't be null";

        int newRow = this.row + direction.getdRow();
        int newColumn = this.column + direction.getdColumn();

        Position ret = new Position( newRow, newColumn );

        return ret;
    }
}