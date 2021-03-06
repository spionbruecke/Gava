package src.games;

/**
 * Field has the attributes row and column. It is used for saving the
 * coordinates of a certain field from a two dimensional array.
 *
 * @author Begüm Tosun
 */

public class Field {

    private int row;
    private int column;

    /**
     * Default Constructor
     */
    public Field(){
        this.row = -1;
        this.column = -1;
    }

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

    public boolean equals(Field f){
        return (this.getRow() == f.getRow()) && (this.getColumn() == f.getColumn());
    }

    public String toString(){
        return "row = "+ row + " | column = " + column;
    }
}
