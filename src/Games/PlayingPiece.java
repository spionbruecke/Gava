package src.games;

public abstract class PlayingPiece {
    private String name;
    // State is 1 if the playing piece is still in the game else 0
    private boolean state;

    public abstract void move();
    public abstract void swap();
}
