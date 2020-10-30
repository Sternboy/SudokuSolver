# SudokuSolver
A recursive program for solving Sudoku puzzles!

Works by doing several things. 
First, it checks if any rows can be obviously filled in (like if there is a box that can only be a 3). 
This continues until the puzzle is solved. 
But for harder puzzles, the player must normally make a guess.
This is where the recursion comes in.
The program remembers the board state and "temporarily" fills in the spot with the fewest options with each guess.
It then follows each guess as though it were certain and the process continues until it is solved.
It works on problems from novice to quite difficult ones requiring multi-level recursive guessing.
This program solves the puzzles in very little time and displays each step for the user to see.

(WARNING: This algorithm will ruin Sudoku for you :)  ---at least it did for me)
