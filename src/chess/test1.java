package src.chess;

import src.games.Field;

public class test1 {

    public static void main(String[] args){
        Field f1 = new Field(2,1);
        Field f2 = new Field(2,1);

        if(f1.equals(f2))
            System.out.println("burdaaaa burda");
    }
}
