package src.games;

/**
 * @author Begüm Tosun
 */
public abstract class Game {

    private StringBuilder gameName;

    public Game(String gameName){
        this.gameName = new StringBuilder();
        this.gameName.append(gameName);
    }

    // Creates new Gameboard
    public abstract void initialize();

    public String getName(){
        return gameName.toString();
    }

}
