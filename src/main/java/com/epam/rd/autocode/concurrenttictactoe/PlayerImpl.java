package com.epam.rd.autocode.concurrenttictactoe;

public class PlayerImpl implements Player {
    private final TicTacToe ticTacToe;
    private final PlayerStrategy playerStrategy;
    private final char mark;
    private static final int GAME_SIZE = 9;
    private static final char EMPTY = ' ';

    public PlayerImpl(TicTacToe ticTacToe, char mark, PlayerStrategy playerStrategy) {
        this.ticTacToe = ticTacToe;
        this.mark = mark;
        this.playerStrategy = playerStrategy;
    }

    @Override
    public void run() {
        synchronized (ticTacToe) {
            for (int i = 0; i < GAME_SIZE; i++) {
                if (ticTacToe.lastMark() != EMPTY)
                    ticTacToe.notify();
                waitThread();
                addMove();
                if (isWin()) {
                    break;
                }
            }
        }
    }

    private void waitThread() {
        if (ticTacToe.lastMark() == mark && !isWin()) {
            try {
                ticTacToe.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void addMove() {
        if (!isWin()) {
            Move move = playerStrategy.computeMove(mark, ticTacToe);
            ticTacToe.setMark(move.row, move.column, mark);
        }
    }

    private boolean isWin() {
        for (int i = 0; i < ticTacToe.table().length; i++) {
            if (ticTacToe.table()[i][0] == mark && ticTacToe.table()[i][1] == mark && ticTacToe.table()[i][2] == mark)
                return true;
            if (ticTacToe.table()[0][i] == mark && ticTacToe.table()[1][i] == mark && ticTacToe.table()[2][i] == mark)
                return true;
        }
        if (ticTacToe.table()[0][0] == mark && ticTacToe.table()[1][1] == mark && ticTacToe.table()[2][2] == mark) {
            return true;
        } else {
            return ticTacToe.table()[0][2] == mark && ticTacToe.table()[1][1] == mark && ticTacToe.table()[2][0] == mark;
        }
    }
}