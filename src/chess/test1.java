package src.chess;

import src.games.Field;
import src.games.GameBoard;
import src.games.Messages;
import src.games.PlayingPiece;
import src.chess.ChessRules;
import src.mill.MillRules;

public class test1 {

    public static void main(String[] args) {

        String move = "7A 6A";

        System.out.println(MillRules.isTargetValid(move));
    }
}
