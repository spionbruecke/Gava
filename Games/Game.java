package Games;

public abstract class Game {

    private StringBuilder gameName;

    Game(String gameName){
        this.gameName = new StringBuilder();
        this.gameName.append(gameName);
        /*
        ChessGame and MillsGame will be implemented later therefore, this piece of code
        does not work yet.

        if(gameName.getName().equals("Schach")){
            ChessGame game = new ChessGame();
            initialize(game);
            start();
            play();
        }else if(gameName.getName().equals("Mühle")){
            MillsGame game = new MillsGame();
            initialize(game);
            start();
            play();
        }else{
            // invalid Game option: error handling
        }
         */

    }

    // Creates new Gameboard
    public abstract void initialize(String gameName);

    public abstract void start();

    public abstract void end();

    public abstract void play();

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
