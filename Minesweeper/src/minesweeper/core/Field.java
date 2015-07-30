package minesweeper.core;

import java.util.Random;

/**
 * Field represents playing field and game logic.
 */
public class Field {
    /**
     * Playing field tiles.
     */
    private final Tile[][] tiles;

    /**
     * Field row count. Rows are indexed from 0 to (rowCount - 1).
     */
    private final int rowCount;

    /**
     * Column count. Columns are indexed from 0 to (columnCount - 1).
     */
    private final int columnCount;

    /**
     * Mine count.
     */
    private final int mineCount;

    /**
     * Game state.
     */
    private GameState state = GameState.PLAYING;

    /**
     * Constructor.
     *
     * @param rowCount    row count
     * @param columnCount column count
     * @param mineCount   mine count
     */
    public Field(int rowCount, int columnCount, int mineCount) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.mineCount = mineCount;
        tiles = new Tile[rowCount][columnCount];

        //generate the field content
        generate();        
    }

    /**
     * Opens tile at specified indeces.
     *
     * @param row    row number
     * @param column column number
     */
    public void openTile(int row, int column) {
        Tile tile = tiles[row][column];
        if (tile.getState() == Tile.State.CLOSED) {
            tile.setState(Tile.State.OPEN);
            if (tile instanceof Mine) {
                state = GameState.FAILED;
                return;
            }

//            if (isSolved()) {
//                state = GameState.SOLVED;
//                return;
//            }
        }
    }

    /**
     * Marks tile at specified indeces.
     *
     * @param row    row number
     * @param column column number
     */
	public void markTile(int row, int column) {
		Tile tile = tiles[row][column];
		if (tile.getState() == Tile.State.CLOSED) {
			tile.setState(Tile.State.MARKED);
		} else {
			tile.setState(Tile.State.CLOSED);
		}

	}
    
    /**
     * Generates playing field.
     */
	private void generate() { 
		Random random = new Random(); //create object random
		int row;
		int column;
		for (int i = 0; i < this.mineCount; i++) { //cycle for placing mines
			row = random.nextInt(this.rowCount); //random row
			column = random.nextInt(this.columnCount); //random column
			if (tiles[row][column] == null) { //if tile is null, place mine
				tiles[row][column] = new Mine();
			} else { //if on tile is mine, decrement i and place mine on another tile 
				i--;
			}
		}
		for (int j = 0; j < this.rowCount; j++){ //cycle for counting row
			for (int k=0; k < this.columnCount; k++){ // cycle for counting columns
				if (tiles[j][k] == null){ //if on tile isnt mine, place clue
				tiles[j][k] = new Clue(countAdjacentMines(j, k));
				}
			}
		}
	}

    /**
     * Returns true if game is solved, false otherwise.
     *
     * @return true if game is solved, false otherwise
     */
    private boolean isSolved() {
        throw new UnsupportedOperationException("Method isSolved not yet implemented");
    }

    /**
     * Returns number of adjacent mines for a tile at specified position in the field.
     *
     * @param row    row number.
     * @param column column number.
     * @return number of adjacent mines.
     */
    private int countAdjacentMines(int row, int column) {
        int count = 0;
        for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
            int actRow = row + rowOffset;
            if (actRow >= 0 && actRow < rowCount) {
                for (int columnOffset = -1; columnOffset <= 1; columnOffset++) {
                    int actColumn = column + columnOffset;
                    if (actColumn >= 0 && actColumn < columnCount) {
                        if (tiles[actRow][actColumn] instanceof Mine) {
                            count++;
                        }
                    }
                }
            }
        }

        return count;
    }
    /**
     * returns count of rows
     * @return
     */
	public int getRowCount() {
		return rowCount;
	}
	/**
	 * returns count of columns
	 * @return
	 */
	public int getColumnCount() {
		return columnCount;
	}
	/**
	 * returns count of mines
	 * @return
	 */
	public int getMineCount() {
		return mineCount;
	}
	/**
	 * returns Game State
	 * @return
	 */
	public GameState getState() {
		return state;
	}
	/**
	 * Returns tile at row and column
	 * @param row
	 * @param column
	 * @return
	 */
	public Tile getTile(int row, int column) {
		return tiles[row][column];
	}
	/**
	 * Returns all tiles in string
	 */
	@Override
	public String toString(){ 
		String s="";
		for(int i=0;i<rowCount;i++){
			for(int j=0;j<columnCount;j++){
				s+=tiles[i][j].toString()+" "; //print all tiles
			}
			s+="\n";
		}
		return s;
	}
    
    
}
