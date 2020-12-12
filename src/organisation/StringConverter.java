package src.organisation;

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


    private StringConverter(){}

    public static InformationsTypes getInformationType(String input)throws WrongInformationFormatException{

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
            case "Sucess" :
                return InformationsTypes.SUCESS;
            case "Color" :
                return InformationsTypes.COLOR;
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

    public static String getInformation(String input){
        StringBuilder information = new StringBuilder();
        int charNumber = 0;
        int counter = 0;

        for(int j = 0 ; j < input.length(); j ++){
            if(input.charAt(j) == '='){
                charNumber = j + 1;
                break;
            }
        }

        for(int i = charNumber; i < input.length(); i ++){
            if (input.charAt(i) == '<')
                counter ++;
            if(input.charAt(i) == '>'){
                if (counter != 0) 
                    counter --;
                else
                    break;
            }
            information.append(input.charAt(i));
        }
        return information.toString();
    }

    public static String getNewMove(){
        return null;
    }
}
