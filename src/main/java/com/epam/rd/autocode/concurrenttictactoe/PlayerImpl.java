package com.epam.rd.autocode.concurrenttictactoe;

public class PlayerImpl implements Player {
    private final TicTacToe ticTacToe;
    private final PlayerStrategy playerStrategy;
    private final char mark;

    public PlayerImpl(TicTacToe ticTacToe, char mark, PlayerStrategy playerStrategy) {
        this.ticTacToe = ticTacToe;
        this.mark = mark;
        this.playerStrategy = playerStrategy;
    }

    @Override
    public void run() {
        synchronized (ticTacToe) {
            for (int i = 0; i < ticTacToe.table().length; i++) {
                Move move = playerStrategy.computeMove(mark, ticTacToe);
                ticTacToe.setMark(move.row, move.column, mark);
            }
        }
    }
}