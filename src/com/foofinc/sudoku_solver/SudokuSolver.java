package com.foofinc.sudoku_solver;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class SudokuSolver {

    private char[][] board;
    List<SudokuSquare> squares;
    ArrayDeque<List<SudokuSquare>> savedStack;


    public SudokuSolver(char[][] board) {
        this.board = board;
        squares = createSquares();
        savedStack = new ArrayDeque<>();
        solve();
    }

    private void solve() {

        int lastNumOfSolved = getNumOfSolvedSquares();

        while (!this.isSolved()) {

            for (int i = 0; i < squares.size(); i++) {

                int row = getRow(i);
                int col = getCol(i);

                SudokuSquare ss = squares.get(i);
                if (!ss.isSolved()) checkSquareAgainstRow(ss, row);
                if (!ss.isSolved()) checkSquareAgainstCol(ss, col);
                if (!ss.isSolved()) checkSquareAgainstSquare(ss, row, col);
                if (ss.isSolved()) board[row][col] = (char) ((char) ss.getAnswer() + 48);
            }
            if (lastNumOfSolved == getNumOfSolvedSquares()) {
                saveBoard(new ArrayList<>(squares));
                guess2(squares);
            }
        }
        System.out.println("Done!");

    }

    private List<SudokuSquare> createSquares() {
        List<SudokuSquare> sudokuSquares = new ArrayList<>();

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                char c = board[i][j];
                if (c == '.') {
                    sudokuSquares.add(new SudokuSquare());
                } else {
                    int x = Character.getNumericValue(c);
                    sudokuSquares.add(new SudokuSquare(x));
                }
            }
        }
        return sudokuSquares;
    }

    private void checkSquareAgainstRow(SudokuSquare ss, int row) {
        List<Integer> takenNumbers = new ArrayList<>();

        int start = row * 9;
        int end = start + 9;

        for (int i = start; i < end; i++) {
            SudokuSquare compareSquare = squares.get(i);
            if (compareSquare.isSolved()) {
                takenNumbers.add(compareSquare.getAnswer());
            }
        }
        ss.removePossibility(takenNumbers);
    }

    private void checkSquareAgainstCol(SudokuSquare ss, int col) {

        List<Integer> takenNumbers = new ArrayList<>();

        for (int i = col; i < 80; i += 9) {
            SudokuSquare compareSquare = squares.get(i);
            if (compareSquare.isSolved()) {
                takenNumbers.add(compareSquare.getAnswer());
            }
        }
        ss.removePossibility(takenNumbers);
    }

    private void checkSquareAgainstSquare(SudokuSquare ss, int row, int col) {
        List<Integer> takenNumbers = new ArrayList<>();

        int r = getSquareCorner(row);
        int c = getSquareCorner(col);
        int i = getIndex(r, c);

        for (int x = i; x < i + 27; x += 9) {
            for (int y = x; y < x + 3; y++) {
                SudokuSquare compareSquare = squares.get(y);
                if (compareSquare.isSolved()) {
                    takenNumbers.add(compareSquare.getAnswer());
                }
            }
        }
        ss.removePossibility(takenNumbers);
    }

    private int getSquareCorner(int i) {
        if (i < 3) {
            return 0;
        } else if (i < 6) {
            return 3;
        } else {
            return 6;
        }
    }

    private int getRow(int i) {
        return i / 9;
    }

    private int getCol(int i) {
        int r = i / 9;
        return i - (9 * r);
    }

    private int getIndex(int row, int col) {
        return (row * 9) + col;
    }

    private boolean isSolved() {
        for (SudokuSquare ss : squares) {
            if (!ss.isSolved()) return false;
        }
        return true;
    }

    private int getNumOfSolvedSquares() {
        int x = 0;
        for (SudokuSquare ss : squares) {
            if (ss.isSolved()) {
                x++;
            }
        }
        return x;
    }

    private void saveBoard(List<SudokuSquare> board) {
        savedStack.push(board);
    }

    private void guess(List<SudokuSquare> squares) {
        for (SudokuSquare ss : squares) {
            if (ss.numberOfPossibilities() == 2) {
                ss.removePossibility(ss.viewPossibilities().get(1));
                break;
            }
        }
        solve();
    }

    private void guess2(List<SudokuSquare> squares) {
        for (SudokuSquare ss : squares) {
            if (ss.numberOfPossibilities() == 2) {
                ss.removePossibility(ss.viewPossibilities().get(0));
                break;
            }
        }
        solve();
    }

    private void loadList() {
        squares = savedStack.pop();
    }
}
