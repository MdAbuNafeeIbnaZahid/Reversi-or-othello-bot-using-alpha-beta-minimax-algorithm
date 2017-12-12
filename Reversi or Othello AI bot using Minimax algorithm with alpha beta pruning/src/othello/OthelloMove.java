package othello;

import alpha_beta_minimax.Action;

/**
 * Created by nafee on 12/9/17.
 */
public class OthelloMove extends Action {

    private Position position;
    private DiskColor diskColor;

    public OthelloMove(Position position, DiskColor diskColor) {



        if ( diskColor == null )
        {
            throw new IllegalArgumentException(" diskColor can't be null ");
        }

        this.position = position;
        this.diskColor = diskColor;
    }



    public Position getPosition() {
        return position;
    }


    public DiskColor getDiskColor() {
        return diskColor;
    }


    @Override
    public String toString() {
        return "OthelloMove{" +
                "position=" + position +
                ", diskColor=" + diskColor +
                '}';
    }
}
