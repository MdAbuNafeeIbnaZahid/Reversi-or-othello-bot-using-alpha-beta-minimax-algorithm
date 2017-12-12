package othello;

/**
 * Created by nafee on 12/8/17.
 */
public enum DiskColor {
    WHITE,
    BLACK;

    public static DiskColor getOpponentDiskColor(DiskColor diskColor)
    {
        if ( diskColor == null )
        {
            throw new IllegalArgumentException(" diskColor can't be null ");
        }

        DiskColor opponentDiskColor = null;

        switch (diskColor)
        {
            case WHITE:
                opponentDiskColor = BLACK;
                break;
            case BLACK:
                opponentDiskColor = WHITE;
                break;
            default:
                throw new IllegalArgumentException(" Unknown disk color ");
        }

        return opponentDiskColor;
    }


    @Override
    public String toString() {
        return this.name();
    }
}


