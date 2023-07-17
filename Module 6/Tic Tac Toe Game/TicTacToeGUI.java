import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TicTacToeGUI extends JFrame {
    private char[][] board;
    private char currentPlayer;
    private char opponentPlayer;
    private JButton[][] buttons;

    public TicTacToeGUI() {
        board = new char[3][3];
        currentPlayer = 'X';
        opponentPlayer = 'O';
        initializeBoard();
        initializeUI();
    }

    public void initializeBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = '-';
            }
        }
    }

    public void initializeUI() {
        setTitle("Tic Tac Toe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 3));
        buttons = new JButton[3][3];

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

    public boolean makeMove(int row, int col, char player) {
        if (row >= 0 && row < 3 && col >= 0 && col < 3 && board[row][col] == '-') {
            board[row][col] = player;
            return true;
        }
        return false;
    }

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

    public void playComputerMove() {
        // Simple AI strategy: Try to win or block the player, otherwise, make a random move

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




    
    public void resetButtons() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new TicTacToeGUI();
            }
        });
    }
}