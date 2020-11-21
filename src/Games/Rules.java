package src.games;

public interface Rules {

    public  boolean isFieldOccupied(int field);
    public  boolean isMoveAllowed(PlayingPiece[][] oldState, PlayingPiece[][] newState);
}
