package src.organisation;

import java.util.function.IntPredicate;

/**
 * 
 * @author Alexander Posch
 * @version 1.0
 * 
 * This Class should get every String from the Server and recognize which Information this is.
 * It also should to the Work the other Way
 * 
 */
public class StringConverter{

    public static void main(String[] args) {
        String test = "< Gamemode = Dingsbumms >";

        try{
            System.out.println(getKeyword(test));
        } catch (WrongInformationFormatException e){
            System.out.println("Fuck");
        }
    }

    public static InformationsTypes stringToInformation(String input)throws WrongInformationFormatException{

        switch(getKeyword(input)){
            case "Gamemode":
                return InformationsTypes.GAMEMODE;
            case "Boardgame":
                return InformationsTypes.GAMEBOARD;
            case "Login":
                return InformationsTypes.LOGIN;
            case "Connectionstatus" :
                return InformationsTypes.CONNECTIONSTATUS;
            case "Message" :
                return InformationsTypes.MESSAGE;
            default:
                throw new WrongInformationFormatException();
        }
    } 
    
    public static String getKeyword(String input)throws WrongInformationFormatException{
        char currentChar;
        int i;
        StringBuilder keywort = new StringBuilder();

        i = 1;
        currentChar = input.charAt(0);

        if(currentChar == '<'){
            currentChar = input.charAt(1);

            while(currentChar != '=' && i < input.length()){
                if (currentChar != ' ')   
                    keywort.append(currentChar);
                i ++;
                currentChar = input.charAt(i);
            }
            return keywort.toString();
        } else {
            throw new WrongInformationFormatException();
        }
    }
}
