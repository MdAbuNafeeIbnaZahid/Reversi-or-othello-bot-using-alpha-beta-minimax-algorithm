package othello;

import alpha_beta_minimax.Action;

/**
 * Created by nafee on 12/9/17.
 */
public class OthelloAction extends Action {

    private int row;
    private int column;
    private DiskColor diskColor;

    public OthelloAction(int row, int col, DiskColor diskColor) {
        this.row = row;
        this.column = col;
        this.diskColor = diskColor;
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

    public void setColumn(int col) {
        this.column = col;
    }

    public DiskColor getDiskColor() {
        return diskColor;
    }

    public void setDiskColor(DiskColor diskColor) {
        this.diskColor = diskColor;
    }
}
