import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TicTacToeGUI extends JFrame {
    private char[][] board;
    private char currentPlayer;
    private char opponentPlayer;
    private JButton[][] buttons;

    /**
     * Constructor for the TicTacToeGUI class.
     * Initializes the game board and sets up the graphical user interface (GUI).
     */
    public TicTacToeGUI() {
        board = new char[3][3];
        currentPlayer = 'X';
        opponentPlayer = 'O';
        initializeBoard();
        initializeUI();
    }

    /**
     * Initializes the game board with empty cells represented by '-'.
     */
    public void initializeBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = '-';
            }
        }
    }

    /**
     * Initializes the graphical user interface (GUI) for the Tic Tac Toe game.
     * Creates buttons for the Tic Tac Toe grid and adds action listeners to handle user clicks.
     */
    public void initializeUI() {
        setTitle("Tic Tac Toe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 3));
        buttons = new JButton[3][3];

        // Create buttons and set up their properties
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                JButton button = new JButton();
                buttons[i][j] = button;
                button.setFont(new Font("Arial", Font.PLAIN, 60));
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        onCellClicked(button);
                    }
                });
                add(button);
            }
        }

        setSize(300, 300); // Set the window size to medium
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Handles the event when a cell (button) on the game grid is clicked.
     * Determines the row and column of the clicked cell and updates the game state accordingly.
     */
    public void onCellClicked(JButton button) {
        int row = -1, col = -1;

        // Find the clicked button in the array and get its row and column
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j] == button) {
                    row = i;
                    col = j;
                    break;
                }
            }
        }

        // Execute the game logic when a player makes a move on the grid
        if (makeMove(row, col, currentPlayer)) {
            button.setText(String.valueOf(currentPlayer));
            if (checkWin(currentPlayer)) {
                JOptionPane.showMessageDialog(this, "Player " + currentPlayer + " wins!");
                initializeBoard();
                resetButtons();
            } else if (isBoardFull()) {
                JOptionPane.showMessageDialog(this, "It's a draw!");
                initializeBoard();
                resetButtons();
            } else {
                currentPlayer = opponentPlayer;
                playComputerMove();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Invalid move. Please try again.");
        }
    }

    /**
     * Attempts to make a move for the given player at the specified row and column.
     * Returns true if the move is valid and successful; otherwise, returns false.
     */
    public boolean makeMove(int row, int col, char player) {
        if (row >= 0 && row < 3 && col >= 0 && col < 3 && board[row][col] == '-') {
            board[row][col] = player;
            return true;
        }
        return false;
    }

    /**
     * Checks if the given player has won the game.
     * Returns true if the player has won; otherwise, returns false.
     */
    public boolean checkWin(char player) {
        // Check rows
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player) {
                return true;
            }
        }

        // Check columns
        for (int i = 0; i < 3; i++) {
            if (board[0][i] == player && board[1][i] == player && board[2][i] == player) {
                return true;
            }
        }

        // Check diagonals
        if (board[0][0] == player && board[1][1] == player && board[2][2] == player) {
            return true;
        }
        if (board[0][2] == player && board[1][1] == player && board[2][0] == player) {
            return true;
        }

        return false;
    }

    /**
     * Checks if the game board is full, i.e., no empty cells remain.
     * Returns true if the board is full; otherwise, returns false.
     */
    public boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '-') {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Implements a simple AI strategy for the computer's move.
     * Tries to win if possible, otherwise, blocks the player's winning move.
     * If neither is possible, makes a random move.
     */
    public void playComputerMove() {
        // First, check if the computer can win
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '-') {
                    board[i][j] = opponentPlayer;
                    if (checkWin(opponentPlayer)) {
                        buttons[i][j].setText(String.valueOf(opponentPlayer));
                        JOptionPane.showMessageDialog(this, "Computer wins!");
                        initializeBoard();
                        resetButtons();
                        currentPlayer = 'X';
                        return;
                    }
                    board[i][j] = '-';
                }
            }
        }

        // Next, check if the player can win and block the player
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '-') {
                    board[i][j] = currentPlayer;
                    if (checkWin(currentPlayer)) {
                        buttons[i][j].setText(String.valueOf(opponentPlayer));
                        board[i][j] = opponentPlayer;
                        currentPlayer = 'X';
                        return;
                    }
                    board[i][j] = '-';
                }
            }
        }

        // If no winning move or block is possible, make a random move
        int row, col;
        do {
            row = (int) (Math.random() * 3);
            col = (int) (Math.random() * 3);
        } while (!makeMove(row, col, opponentPlayer));

        buttons[row][col].setText(String.valueOf(opponentPlayer));

        if (checkWin(opponentPlayer)) {
            JOptionPane.showMessageDialog(this, "Computer wins!");
            initializeBoard();
            resetButtons();
        } else if (isBoardFull()) {
            JOptionPane.showMessageDialog(this, "It's a draw!");
            initializeBoard();
            resetButtons();
        } else {
            currentPlayer = 'X';
        }
    }

    /**
     * Resets the text of all buttons on the game grid.
     * Clears the board for a new game.
     */
    public void resetButtons() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }
    }

    /**
     * Main method to start the Tic Tac Toe game.
     * Creates an instance of TicTacToeGUI and displays the game window.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new TicTacToeGUI();
            }
        });
    }
}