package com.foofinc.sudoku_solver;

public class Main {
    public static void main(String[] args) {

        ValidSudoku validSudoku = new ValidSudoku();

//        SudokuSolver sudokuSolver = new SudokuSolver(validSudoku.getBoard());
        SudokuSolver sudokuSolver2 = new SudokuSolver(validSudoku.getHardBoard());

    }
}
