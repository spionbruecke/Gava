package src.Games;

/**
 * @author Begüm Tosun
 *
 * Field has the attributes row and column. It is used for saving the coordinates of a certain field from a two
 * dimensional array.
 */

public class Field {

    private int row;
    private int column;

    /**
     * Default Constructor
     */
    public Field(){}

    /**
     * Constructs a Field with the given values.
     * @param row int
     * @param column int
     */
    public Field(int row, int column){
        this.row = row;
        this.column = column;
    }

    /**
     * Returns row
     * @return int
     */
    public int getRow() {
        return row;
    }

    /**
     * Returns column
     * @return int
     */
    public int getColumn() {
        return column;
    }
}
