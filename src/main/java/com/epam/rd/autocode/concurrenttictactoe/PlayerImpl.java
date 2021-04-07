package com.epam.rd.autocode.concurrenttictactoe;

public class PlayerImpl implements Player {
    public static final char FIRST_TURN_MARK = 'X';
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
                waitIfNotFirstTurnMark();
                if (ticTacToe.isFinished()) {
                    return;
                }
                if (ticTacToe.lastMark() == mark) {
                    try {
                        ticTacToe.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    Move move = playerStrategy.computeMove(mark, ticTacToe);
                    ticTacToe.setMark(move.row, move.column, mark);
                    if (isWin()) {
                        ticTacToe.finish();
                        ticTacToe.notifyAll();
                        return;
                    }
                    ticTacToe.notifyAll();
                }
            }
        }
    }

    private void waitIfNotFirstTurnMark() {
        if (isNotFirstTurnMark()) {
            try {
                ticTacToe.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isNotFirstTurnMark() {
        return mark != FIRST_TURN_MARK && ticTacToe.lastMark() == EMPTY;
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