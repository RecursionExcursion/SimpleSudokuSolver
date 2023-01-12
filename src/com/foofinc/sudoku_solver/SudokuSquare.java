package com.foofinc.sudoku_solver;

import java.util.ArrayList;
import java.util.List;

public class SudokuSquare {

    private final ArrayList<Integer> possibleNumbers;

    public SudokuSquare() {
        possibleNumbers = new ArrayList<>(9);
        for (int i = 1; i <= 9; i++) {
            possibleNumbers.add(i);
        }
    }

    public SudokuSquare(int i) {
        possibleNumbers = new ArrayList<>();
        possibleNumbers.add(i);
    }

    public void removePossibility(List<Integer> notPossibleNumbers) {
        for (Integer i : notPossibleNumbers) {
            possibleNumbers.remove(i);
        }
        if (possibleNumbers.size() == 0) throw new RuntimeException("List Size Cannot be zero");
    }

    public void removePossibility(Integer i) {
        possibleNumbers.remove(i);
        if (possibleNumbers.size() == 0) throw new RuntimeException("List Size Cannot be zero");
    }

    public int getAnswer() {
        if (possibleNumbers.size() == 1) {
            return possibleNumbers.get(0);
        } else {
            return -1;
        }
    }

    public boolean isSolved() {
        return possibleNumbers.size() == 1;
    }

    public int numberOfPossibilities() {
        return possibleNumbers.size();
    }

    public List<Integer> viewPossibilities() {
        return new ArrayList<>(possibleNumbers);
    }
}
