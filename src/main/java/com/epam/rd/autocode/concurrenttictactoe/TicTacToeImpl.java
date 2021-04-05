package com.epam.rd.autocode.concurrenttictactoe;

import java.util.Arrays;

public class TicTacToeImpl implements TicTacToe {
    private final char[][] table = new char[3][3];
    private static final char START_CHAR = ' ';
    private char lastMark;

    {
        fillTable();
    }

    @Override
    public synchronized void setMark(int x, int y, char mark) {
        if (table[x][y] == ' ') {
            table[x][y] = mark;
        } else throw new IllegalArgumentException("Wrong input!!!");
        lastMark = mark;
    }

    private void fillTable() {
        Arrays.stream(table)
                .forEach(chars -> Arrays.fill(chars, START_CHAR));
    }

    @Override
    public final char[][] table() {
        return table;
    }

    @Override
    public synchronized char lastMark() {
        return lastMark;
    }
}