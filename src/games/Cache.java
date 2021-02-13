package src.games;

/**
 * This class is used for undoing the last move.
 * @author Begüm Tosun
 */
public class Cache {
    private PlayingPiece[][] state;

    /**
     * Constructs a Cache-object.
     * @param state PlayingPiece
     */
    public Cache(PlayingPiece[][] state){
        this.state = state;
    }

    /**
     * Returns a multidimensional PlayingPiece array.
     * @return state
     */
    public PlayingPiece[][] getState(){
        return state;
    }

}
