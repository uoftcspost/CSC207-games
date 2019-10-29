package uoft.csc207.gameapplication.TetrisGame;

abstract class Piece {

    private int x;
    private int y;
    private int rotation;
    String[][] states;

    Piece() {
        x = 3;   // center piece on x-axis of grid
        y = -1;   // fix piece on top of grid (actual coordinates are out of bounds)
        rotation = 0;
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    int getRotation() {
        return rotation;
    }

    private boolean canMoveTo(BoardV2 board, int adjX, int adjY) {
        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 5; x++) {
                if (this.states[this.rotation][y].charAt(x) != '.') {
                    try {
                        if (board.board[this.y + y + adjY][this.x + x + adjX] != '.') {
                            return false;   // move results in collision
                        }
                    } catch (IndexOutOfBoundsException e) {
                        return false;   // move is out of bounds
                    }
                }
            }
        }
        return true;
    }

    private boolean tryMove(BoardV2 board, int adjX, int adjY) {
        if (this.canMoveTo(board, adjX, adjY)) {
            this.x += adjX;
            this.y += adjY;
            return true;
        }
        else {
            return false;
        }
    }

    // CALL THESE METHODS IN THE DRIVER
    // returns true if the move was successful
    boolean moveLeft(BoardV2 board) {
        return this.tryMove(board, -1, 0);
    }

    boolean moveRight(BoardV2 board) {
        return this.tryMove(board, 1, 0);
    }

    boolean moveDown(BoardV2 board) {
        return this.tryMove(board, 0, 1);
    }

    // void drop(Board board) {}   // phase 2

    // abstract void rotateClockwise(Board board);   // phase 2

    // abstract void rotateCounterClockwise(Board board);   // phase 2

    // boolean canRotate(Board board)   // phase 2
}
