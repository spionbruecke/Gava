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

    /*
    public abstract void start();

    public abstract void end();

    public abstract void play();
    */

    public String getName(){
        return gameName.toString();
    }
    /*
    public abstract void timer();
    Do we need this method?
     */

     public String toString(){
         return gameName.toString();
     }

}
