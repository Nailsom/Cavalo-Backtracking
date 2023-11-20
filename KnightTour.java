import java.util.ArrayList;
import java.util.Arrays;

class Node {
    private final int[][] movements = {
            {2, 1},
            {1, 2},
            {-1, 2},
            {-2, 1},
            {-2, -1},
            {-1, -2},
            {1, -2},
            {2, -1}
    };
    private int[][] board;
    private int[] pos;
    private ArrayList<Node> children;

    public Node(int[][] board, int[] pos) {
        this.board = board;
        this.pos = pos;
        this.children = new ArrayList<>();
    }

    private boolean isValid(int[] mov) {
        int[] newPos = {pos[0] + mov[0], pos[1] + mov[1]};
        int n = board.length - 1;
        return !(newPos[0] < 0 || newPos[0] > n || newPos[1] < 0 || newPos[1] > n);
    }

    public void generateChildren() {
        if (Arrays.stream(board).flatMapToInt(Arrays::stream).sum() == board.length * board.length) {
            printBoard();
            return;  // Problema solucionado
        }

        printBoard();
        for (int[] mov : movements) {
            if (isValid(mov)) {
                int[] newPos = {pos[0] + mov[0], pos[1] + mov[1]};
                if (board[newPos[0]][newPos[1]] == 0) {
                    int[][] childBoard = new int[board.length][];
                    for (int i = 0; i < board.length; i++) {
                        childBoard[i] = Arrays.copyOf(board[i], board[i].length);
                    }
                    childBoard[newPos[0]][newPos[1]] = 1;
                    children.add(new Node(childBoard, newPos));
                }
            }
        }

        for (Node child : children) {
            child.generateChildren();
        }
    }

    private void printBoard() {
        for (int[] row : board) {
            for (int cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
        System.out.println("- ".repeat(board.length));
    }
}

public class KnightTour {
    public static void main(String[] args) {
        solveKnight();
    }

    private static void solveKnight() {
        int size = 4;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int[][] initialBoard = new int[size][size];
                Node root = new Node(initialBoard, new int[]{i, j});
                root.generateChildren();
            }
        }
    }
}
