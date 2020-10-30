import java.util.ArrayList;

/**
 * Main driving class for solving the Sudoku puzzles.
 * 
 * @author Sam Sternman
 */
public class SudokuSolver {

	// Class for the square object
	private class Square {
		private int value; // Value of the Sudoku Tile
		private ArrayList<Integer> noteVals; // Note Values

		// Constructor
		private Square(int value) {
			this.value = value;
			this.noteVals = new ArrayList<>();
		}

		// Setter for the value
		private void setValue(int value) {
			this.value = value;
		}

		// Getter for the value
		private int getValue() {
			return this.value;
		}

		// Adds a value to the noteVals list
		private void addNoteVal(int value) {
			if (this.noteVals.contains(value) || value < 1 || value > 9) {
				return;
			}
			this.noteVals.add(value);
		}

		// Removes a value from the noteVals list
		private void removeNoteVal(int value) {
			if (this.noteVals.contains(value)) {
				this.noteVals.remove(this.getNoteVals().indexOf(value));
			}
		}

		// Returns a copy of the list of noteVals
		@SuppressWarnings("unchecked")
		private ArrayList<Integer> getNoteVals() {
			return (ArrayList<Integer>) this.noteVals.clone();
		}

		// Sets up the list of possible values (noteVals)
		private void setupNoteVals(Square[][] board, int row, int col) {
			if (this.getValue() != 0) {
				this.noteVals = new ArrayList<Integer>();
				return;
			} else {
				// Add values 1-9 (if not there yet)
				for (int i = 1; i < 10; i++) {
					if (!this.getNoteVals().contains(i)) {
						this.addNoteVal(i);
					}
				}

				// Remove any numbers in its col
				for (int i = 0; i < 9; i++) {
					int note = board[i][col].getValue();
					if (note != 0 && this.getNoteVals().contains(note)) {
						this.removeNoteVal(note);
					}
				}

				// Remove any numbers in its row
				for (int i = 0; i < 9; i++) {
					int note = board[row][i].getValue();
					if (note != 0 && this.getNoteVals().contains(note)) {
						this.removeNoteVal(note);
					}
				}

				// Remove any numbers in its 3x3
				int matrixRow = row / 3;
				int matrixCol = col / 3;

				for (int i = (matrixRow * 3); i < (matrixRow * 3) + 3; i++) {
					for (int j = (matrixCol * 3); j < (matrixCol * 3) + 3; j++) {
						int note = board[i][j].getValue();
						if (note != 0 && this.getNoteVals().contains(note)) {
							this.removeNoteVal(note);
						}
					}
				}

			}
		}

		// Checks notVal lists to make sure that any square which is the only
		// possibility for a value has the other values in its list removed
		private void updateNoteVals(Square[][] board, int row, int col) {

			int matrixRow = row / 3;
			int matrixCol = col / 3;

			// If any square is the only spot for a value, remove all other notes
			for (int onlyVal : this.getNoteVals()) {
				boolean foundVal = false;
				for (int i = (matrixRow * 3); i < (matrixRow * 3) + 3; i++) {
					for (int j = (matrixCol * 3); j < (matrixCol * 3) + 3; j++) {
						ArrayList<Integer> otherNoteVals = board[i][j].getNoteVals();
						if (otherNoteVals.contains(onlyVal) && (i != row || j != col)) {
							foundVal = true;
							break;
						}
					}
				}
				if (!foundVal) {
					// System.out.println("Didn't find " + onlyVal);
					// System.out.println("At location [" + row + "][" + col + "]");
					// printBoard(board);

					ArrayList<Integer> newList = new ArrayList<>();
					newList.add(onlyVal);
					this.noteVals = newList;
					break;
				}
			}
		}

		// Returns a copy of this Square object
		private Square getCopy() {
			Square copy = new Square(this.value);
			copy.noteVals = this.getNoteVals();
			return copy;
		}

	}

	// Prints the board given to it
	private static void printBoard(Square[][] board, int numIndents) {
		System.out.println("\n");

		for (int i = 0; i < 9; i++) {

			for (int k = 0; k < numIndents; k++) {
				System.out.print("	");
			}

			for (int j = 0; j < 9; j++) {
				System.out.print(" " + board[i][j].getValue() + " ");
				if (j == 2 || j == 5) {
					System.out.print("|");
				}
				if (j == 8) {
					System.out.println();
				}
				if ((i == 2 || i == 5) && j == 8) {
					for (int k = 0; k < numIndents; k++) {
						System.out.print("	");
					}
					System.out.println("-----------------------------");
				}
			}
		}
	}

	// Prints the notes of the board given to it (used for dsebugging)
	private static void printBoardNotes(Square[][] board) {
		System.out.println("\n");

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				System.out.print(" " + board[i][j].getNoteVals() + " ");
				if (j == 2 || j == 5) {
					System.out.print("|");
				}
				if (j == 8) {
					System.out.println();
				}
				if ((i == 2 || i == 5) && j == 8) {
					System.out.println("-----------------------------");
				}
			}
		}
	}

	// Checks if the board is in a valid state and returns true if it is
	private static boolean validityChecker(Square[][] board, int numIndents) {
		// Check each row for the numbers 1-9
		for (int i = 0; i < 9; i++) {
			ArrayList<Integer> numsInRow = new ArrayList<>();
			for (int j = 0; j < 9; j++) {
				int currNum = board[i][j].getValue();
				if (currNum == 0) {
					continue;
				} else if (numsInRow.contains(currNum)) {
					for (int k = 0; k < numIndents; k++) {
						System.out.print("	");
					}
					System.out.println("Error with [" + i + "][" + j + "] row");
					return false;
				} else {
					numsInRow.add(currNum);
				}
			}
		}

		// Check each col for the numbers 1-9
		for (int i = 0; i < 9; i++) {
			ArrayList<Integer> numsInCol = new ArrayList<>();
			for (int j = 0; j < 9; j++) {
				int currNum = board[j][i].getValue();
				if (currNum == 0) {
					continue;
				} else if (numsInCol.contains(currNum)) {
					for (int k = 0; k < numIndents; k++) {
						System.out.print("	");
					}
					System.out.println("Error with [" + j + "][" + i + "] col");
					return false;
				} else {
					numsInCol.add(currNum);
				}
			}
		}

		// Check each 3x3
		for (int k = 0; k < 9; k++) {
			int rowMod = 0;
			int colMod = 0;
			switch (k) {
			case 0:
				rowMod = 0;
				colMod = 0;
				break;
			case 1:
				rowMod = 0;
				colMod = 3;
				break;
			case 2:
				rowMod = 0;
				colMod = 6;
				break;
			case 3:
				rowMod = 3;
				colMod = 0;
				break;
			case 4:
				rowMod = 3;
				colMod = 3;
				break;
			case 5:
				rowMod = 3;
				colMod = 6;
				break;
			case 6:
				rowMod = 6;
				colMod = 0;
				break;
			case 7:
				rowMod = 6;
				colMod = 3;
				break;
			case 8:
				rowMod = 6;
				colMod = 6;
				break;
			}

			ArrayList<Integer> numsIn3x3 = new ArrayList<>();
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					int currNum = board[i + rowMod][j + colMod].getValue();
					if (currNum == 0) {
						continue;
					} else if (numsIn3x3.contains(currNum)) {
						for (int l = 0; l < numIndents; l++) {
							System.out.print("	");
						}
						System.out.println("Error with [" + i + "][" + j + "] 3x3");
						return false;
					} else {
						numsIn3x3.add(currNum);
					}
				}
			}
		}

		// No error so return true
		return true;

	}

	// Tries solving the current board state
	private static void boardSolver(Square[][] board, int numIndent) {
		// Setup the noteVals of each board position
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				board[i][j].setupNoteVals(board, i, j);
			}
		}

		// Update the noteVals of each board position (in case of onlyVal spots)
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				board[i][j].updateNoteVals(board, i, j);
			}
		}

		// Get the number of solved spaces
		int numSolved = 0;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (board[i][j].getValue() != 0) {
					numSolved++;
				}
			}
		}

		int oldNumSolved = numSolved;
		boolean split = false;

		// Place values till it's solved
		while (numSolved < 81) {
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					if (board[i][j].getNoteVals().size() == 0 && board[i][j].getValue() == 0) {
						for (int k = 0; k < numIndent; k++) {
							System.out.print("	");
						}
						System.out.println("Error with board state...leaving");
						return;
					} else if (board[i][j].getNoteVals().size() == 1) {
						board[i][j].setValue(board[i][j].getNoteVals().get(0));
						board[i][j].removeNoteVal(board[i][j].getNoteVals().get(0));
						numSolved++;
					}
				}
			}

			// Update the noteVals of each board position
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					board[i][j].setupNoteVals(board, i, j);
					board[i][j].updateNoteVals(board, i, j);
				}
			}

			if (!validityChecker(board, numIndent)) {
				for (int k = 0; k < numIndent; k++) {
					System.out.print("	");
				}
				System.out.println("Error with board state validity...leaving");
				return;
			}

			if (oldNumSolved == numSolved) {
				System.out.println();
				for (int k = 0; k < numIndent; k++) {
					System.out.print("	");
				}
				System.out.println("Needs to split");
				split = true;

				// Find a spot to split on
				int splittingRow = 0;
				int splittingCol = 0;
				int splitNum1 = 0;
				int splitNum2 = 0;
				boolean leave = false;

				for (int i = 0; i < 9; i++) {
					for (int j = 0; j < 9; j++) {
						ArrayList<Integer> list = board[i][j].getNoteVals();
						if (list.size() == 2) {
							splitNum1 = list.get(0);
							splitNum2 = list.get(1);
							splittingRow = i;
							splittingCol = j;
							leave = true;
							break;
						}
					}
					if (leave) {
						break;
					}
				}

				// Copy the board over to 2 new boards
				Square[][] boardA = new Square[9][9];
				Square[][] boardB = new Square[9][9];

				for (int i = 0; i < 9; i++) {
					for (int j = 0; j < 9; j++) {
						boardA[i][j] = board[i][j].getCopy();
						boardB[i][j] = board[i][j].getCopy();
					}
				}

				boardA[splittingRow][splittingCol].setValue(splitNum1);
				boardB[splittingRow][splittingCol].setValue(splitNum2);

				for (int k = 0; k < numIndent; k++) {
					System.out.print("	");
				}
				System.out.println("Split A for [" + splittingRow + "][" + splittingCol + "]:");
				printBoard(boardA, numIndent + 1);
				boardSolver(boardA, numIndent + 1);

				System.out.println("");
				for (int k = 0; k < numIndent; k++) {
					System.out.print("	");
				}
				System.out.println("Split B for [" + splittingRow + "][" + splittingCol + "]:");
				printBoard(boardB, numIndent + 1);
				boardSolver(boardB, numIndent + 1);

				break;
			} else {
				oldNumSolved = numSolved;
			}
		}

		// Print the solution board
		if (!split) {
			for (int k = 0; k < numIndent; k++) {
				System.out.print("	");
			}
			System.out.println("=============================");
			printBoard(board, numIndent);
			if (validityChecker(board, numIndent)) {
				System.out.println("");
				for (int k = 0; k < numIndent; k++) {
					System.out.print("	");
				}
				System.out.println("	VALID SOLUTION");
				System.exit(0);
			}
		}
	}

	// Main driver for the program
	public static void main(String[] args) {

		// Make the empty board
		SudokuSolver s = new SudokuSolver();
		Square[][] board = new Square[9][9];
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				board[i][j] = s.new Square(0);
			}
		}
		// printBoard(board);

		// Populate the board with starter values

		// Difficult Board 1 -- Simple Split (SUCCESS)
		/*
		 * board[0][1].setValue(6); board[0][3].setValue(4); board[1][0].setValue(3);
		 * board[1][5].setValue(1); board[2][0].setValue(1); board[2][2].setValue(7);
		 * board[2][4].setValue(3); board[2][8].setValue(5); board[3][0].setValue(9);
		 * board[3][1].setValue(2); board[3][7].setValue(3); board[4][5].setValue(4);
		 * board[4][6].setValue(6); board[5][5].setValue(5); board[5][8].setValue(9);
		 * board[6][2].setValue(5); board[6][4].setValue(2); board[7][0].setValue(7);
		 * board[7][2].setValue(4); board[7][7].setValue(1); board[8][5].setValue(6);
		 * board[8][6].setValue(8);
		 */
		// Difficult Board 2 -- Multi-Split (SUCCESS)

		board[0][4].setValue(1);
		board[0][6].setValue(6);
		board[1][0].setValue(9);
		board[1][5].setValue(7);
		board[1][7].setValue(3);
		board[2][1].setValue(8);
		board[2][3].setValue(2);
		board[2][7].setValue(1);
		board[3][2].setValue(1);
		board[3][4].setValue(4);
		board[3][7].setValue(9);
		board[4][7].setValue(8);
		board[5][3].setValue(8);
		board[5][4].setValue(9);
		board[5][6].setValue(7);
		board[5][8].setValue(3);
		board[6][1].setValue(3);
		board[6][8].setValue(5);
		board[7][1].setValue(9);
		board[7][4].setValue(6);
		board[8][0].setValue(7);
		board[8][3].setValue(5);
		board[8][5].setValue(8);

		// Easy board (SUCCESS)
		/*
		 * board[0][2].setValue(1); board[0][3].setValue(9); board[0][4].setValue(8);
		 * board[0][5].setValue(4); board[0][6].setValue(7); board[0][7].setValue(6);
		 * board[1][0].setValue(6); board[1][4].setValue(5); board[1][5].setValue(7);
		 * board[2][0].setValue(8); board[2][2].setValue(7); board[2][4].setValue(1);
		 * board[3][0].setValue(9); board[3][1].setValue(6); board[3][3].setValue(3);
		 * board[3][5].setValue(8); board[3][6].setValue(1); board[3][8].setValue(5);
		 * board[4][0].setValue(1); board[4][1].setValue(8); board[4][2].setValue(5);
		 * board[4][4].setValue(2); board[4][7].setValue(7); board[4][8].setValue(3);
		 * board[5][0].setValue(3); board[5][6].setValue(2); board[5][8].setValue(8);
		 * board[6][0].setValue(2); board[6][1].setValue(1); board[6][7].setValue(3);
		 * board[6][8].setValue(6); board[7][3].setValue(1); board[7][8].setValue(4);
		 * board[8][1].setValue(9); board[8][2].setValue(6); board[8][5].setValue(2);
		 * board[8][6].setValue(5); board[8][7].setValue(1);
		 */
		printBoard(board, 0);
		boardSolver(board, 0);

	}

}
