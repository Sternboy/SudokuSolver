# SudokuSolver
A recursive program for solving Sudoku puzzles!

Works by doing several things: 
First, it checks if any rows can obviously be filled in (like if there is a box that can only be a 3). 
For simple games, this process continues until the puzzle is solved. 
For harder puzzles, normally the player would have to make a guess at some point.
Here, the program uses recursion to make a guess and then retrace its steps if the guess was wrong.
The program remembers the board state and temporarily fills in the spot that has the fewest possible options.
It then continues on as if the guess was true and the process continues until it is solved, or it is determined that the guess was wrong.
In the case that it is wrong, it will escape that branch of the guesing tree.
It is essentially a DFS Sudoku solver where the nodes being traversed are possible board states from the given board state, until it finds the correct/winning board state.
It works on problems from novice to quite difficult ones requiring multi-level recursive guessing.
This program solves the puzzles in very little time and displays each step for the user to see.

(WARNING: This algorithm will ruin Sudoku for you :)  ---at least it did for me)
