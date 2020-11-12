package Games;

public abstract class PlayingPiece {
    private String name;
    private String location;
    // State is 1 if the playing piece is still in the game else 0
    private boolean state;

    public abstract void move();
    public abstract void swap();
}
