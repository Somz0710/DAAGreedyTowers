////package finalversion;
////
////import javax.swing.*;
////import java.awt.*;
////import java.awt.event.*;
////// ============================================================================
////// TowersMove.java - Represents a single move in the Towers game
////// ============================================================================
////class TowersMove {
////    int row;
////    int col;
////    int value;
////
////    public TowersMove(int row, int col, int value) {
////        this.row = row;
////        this.col = col;
////        this.value = value;
////    }
////
////    @Override
////    public String toString() {
////        return String.format("Place %d at (%d,%d)", value, row, col);
////    }
////}
////
////// ============================================================================
////// TowersGameState.java - Manages game state, time, and points
////// ============================================================================
////class TowersGameState {
////    private final int gridSize;
////    private final int[][] board;
////    private final int[] topClues;
////    private final int[] bottomClues;
////    private final int[] leftClues;
////    private final int[] rightClues;
////
////    // Time and points tracking
////    private int humanTime;
////    private int humanPoints;
////    private int cpuTime;
////    private int cpuPoints;
////
////    // Game constants
////    private static final int CORRECT_MOVE_TIME_BONUS = 5;
////    private static final int CORRECT_MOVE_POINTS = 10;
////    private static final int WRONG_MOVE_TIME_PENALTY = 10;
////    private static final int WRONG_MOVE_POINTS_PENALTY = 5;
////    private static final int INITIAL_TIME = 100;
////
////    private boolean isHumanTurn;
////    private int turnCount;
////
////    public TowersGameState(int size) {
////        this.gridSize = size;
////        this.board = new int[size][size];
////        this.topClues = new int[size];
////        this.bottomClues = new int[size];
////        this.leftClues = new int[size];
////        this.rightClues = new int[size];
////
////        this.humanTime = INITIAL_TIME;
////        this.humanPoints = 0;
////        this.cpuTime = INITIAL_TIME;
////        this.cpuPoints = 0;
////        this.isHumanTurn = true;
////        this.turnCount = 0;
////
////        generateClues();
////    }
////
////    // Generate visibility clues for the puzzle
////    private void generateClues() {
////        // For simplicity, generate random but valid clues
////        // In a real implementation, you'd generate from a valid solution
////        for (int i = 0; i < gridSize; i++) {
////            topClues[i] = (int)(Math.random() * gridSize) + 1;
////            bottomClues[i] = (int)(Math.random() * gridSize) + 1;
////            leftClues[i] = (int)(Math.random() * gridSize) + 1;
////            rightClues[i] = (int)(Math.random() * gridSize) + 1;
////        }
////    }
////
////    public boolean makeMove(TowersMove move, boolean isCPU) {
////        if (!isValidPlacement(move.row, move.col, move.value)) {
////            // Wrong move - penalize current player
////            if (isCPU) {
////                cpuTime = Math.max(0, cpuTime - WRONG_MOVE_TIME_PENALTY);
////                cpuPoints = Math.max(0, cpuPoints - WRONG_MOVE_POINTS_PENALTY);
////            } else {
////                humanTime = Math.max(0, humanTime - WRONG_MOVE_TIME_PENALTY);
////                humanPoints = Math.max(0, humanPoints - WRONG_MOVE_POINTS_PENALTY);
////            }
////            return false;
////        }
////
////        // Correct move - reward current player
////        board[move.row][move.col] = move.value;
////        if (isCPU) {
////            cpuTime += CORRECT_MOVE_TIME_BONUS;
////            cpuPoints += CORRECT_MOVE_POINTS;
////        } else {
////            humanTime += CORRECT_MOVE_TIME_BONUS;
////            humanPoints += CORRECT_MOVE_POINTS;
////        }
////
////        turnCount++;
////        isHumanTurn = !isHumanTurn;
////        return true;
////    }
////
////    // Check if a value can be placed at (row, col) without violating Towers rules
////    private boolean isValidPlacement(int row, int col, int value) {
////        if (board[row][col] != 0) return false; // Cell occupied
////        if (value < 1 || value > gridSize) return false; // Invalid value
////
////        // Check row uniqueness
////        for (int c = 0; c < gridSize; c++) {
////            if (board[row][c] == value) return false;
////        }
////
////        // Check column uniqueness
////        for (int r = 0; r < gridSize; r++) {
////            if (board[r][col] == value) return false;
////        }
////
////        return true;
////    }
////
////    // Calculate how many points/time would be gained/lost by this move
////    public int calculateDeltaPoints(TowersMove move) {
////        if (!isValidPlacement(move.row, move.col, move.value)) {
////            return -WRONG_MOVE_POINTS_PENALTY;
////        }
////        return CORRECT_MOVE_POINTS;
////    }
////
////    public int calculateDeltaTime(TowersMove move) {
////        if (!isValidPlacement(move.row, move.col, move.value)) {
////            return -WRONG_MOVE_TIME_PENALTY;
////        }
////        return CORRECT_MOVE_TIME_BONUS;
////    }
////
////    public boolean isGameOver() {
////        return humanTime <= 0 || cpuTime <= 0 || isBoardFull() || turnCount > gridSize * gridSize + 10;
////    }
////
////    private boolean isBoardFull() {
////        for (int r = 0; r < gridSize; r++) {
////            for (int c = 0; c < gridSize; c++) {
////                if (board[r][c] == 0) return false;
////            }
////        }
////        return true;
////    }
////
////    public String getWinner() {
////        if (humanTime <= 0) return "CPU";
////        if (cpuTime <= 0) return "Human";
////        if (humanPoints > cpuPoints) return "Human";
////        if (cpuPoints > humanPoints) return "CPU";
////        if (humanTime > cpuTime) return "Human";
////        if (cpuTime > humanTime) return "CPU";
////        return "Draw";
////    }
////
////    // Getters
////    public int getGridSize() { return gridSize; }
////    public int getCellValue(int r, int c) { return board[r][c]; }
////    public int getHumanTime() { return humanTime; }
////    public int getHumanPoints() { return humanPoints; }
////    public int getCpuTime() { return cpuTime; }
////    public int getCpuPoints() { return cpuPoints; }
////    public boolean isHumanTurn() { return isHumanTurn; }
////    public int[] getTopClues() { return topClues; }
////    public int[] getBottomClues() { return bottomClues; }
////    public int[] getLeftClues() { return leftClues; }
////    public int[] getRightClues() { return rightClues; }
////}
////
////// ============================================================================
////// GreedyTowersAI.java - CPU AI using pure Greedy algorithm
////// ============================================================================
////class GreedyTowersAI {
////    // Greedy strategy modes
////    public enum GreedyMode {
////        SCORE_GREEDY,   // Maximize points
////        TIME_GREEDY,    // Maximize time
////        BALANCED        // Balanced approach
////    }
////
////    private GreedyMode mode;
////
////    public GreedyTowersAI(GreedyMode mode) {
////        this.mode = mode;
////    }
////
////    public void setMode(GreedyMode mode) {
////        this.mode = mode;
////    }
////
////    /**
////     * PURE GREEDY ALGORITHM
////     *
////     * For each possible legal move m:
////     *   Calculate utility(m) = w1 * deltaPoints(m) + w2 * deltaTime(m)
////     *
////     * Return the move with MAXIMUM utility
////     *
////     * NO RECURSION, NO BACKTRACKING, NO MINIMAX, NO DP
////     * Only considers immediate gain/loss of current move
////     */
////    public TowersMove selectMove(TowersGameState state) {
////        int gridSize = state.getGridSize();
////        TowersMove bestMove = null;
////        double bestUtility = Double.NEGATIVE_INFINITY;
////
////        // Set weights based on mode
////        double w1 = 1.0; // Weight for points
////        double w2 = 1.0; // Weight for time
////
////        switch (mode) {
////            case SCORE_GREEDY:
////                w1 = 2.0;
////                w2 = 0.5;
////                break;
////            case TIME_GREEDY:
////                w1 = 0.5;
////                w2 = 2.0;
////                break;
////            case BALANCED:
////                w1 = 1.0;
////                w2 = 1.0;
////                break;
////        }
////
////        // Try all possible moves (empty cells × possible values)
////        for (int row = 0; row < gridSize; row++) {
////            for (int col = 0; col < gridSize; col++) {
////                if (state.getCellValue(row, col) == 0) { // Empty cell
////                    for (int value = 1; value <= gridSize; value++) {
////                        TowersMove move = new TowersMove(row, col, value);
////
////                        // Calculate immediate gains/losses (GREEDY)
////                        int deltaPoints = state.calculateDeltaPoints(move);
////                        int deltaTime = state.calculateDeltaTime(move);
////
////                        // Greedy utility function
////                        double utility = w1 * deltaPoints + w2 * deltaTime;
////
////                        // Select move with maximum utility (GREEDY CHOICE)
////                        if (utility > bestUtility) {
////                            bestUtility = utility;
////                            bestMove = move;
////                        }
////                    }
////                }
////            }
////        }
////
////        return bestMove;
////    }
////}
////
////// ============================================================================
////// TowersGUI.java - Swing/SwingX graphical user interface
////// ============================================================================
////
////
////public class TowersGameGUI extends JFrame {
////    private TowersGameState gameState;
////    private GreedyTowersAI ai;
////    private JButton[][] cellButtons;
////    private JLabel humanTimeLabel, humanPointsLabel;
////    private JLabel cpuTimeLabel, cpuPointsLabel;
////    private JLabel turnLabel;
////    private JTextArea logArea;
////    private JComboBox<String> strategySelector;
////
////    private static final int GRID_SIZE = 4;
////
////    public TowersGameGUI() {
////        setTitle("Towers Puzzle Game - Greedy AI");
////        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
////        setLayout(new BorderLayout(10, 10));
////
////        gameState = new TowersGameState(GRID_SIZE);
////        ai = new GreedyTowersAI(GreedyTowersAI.GreedyMode.BALANCED);
////
////        initComponents();
////
////        pack();
////        setLocationRelativeTo(null);
////        setVisible(true);
////    }
////
////    private void initComponents() {
////        // Top panel - Status
////        JPanel topPanel = new JPanel(new GridLayout(2, 2, 10, 5));
////        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
////
////        humanTimeLabel = new JLabel("Human Time: " + gameState.getHumanTime());
////        humanPointsLabel = new JLabel("Human Points: " + gameState.getHumanPoints());
////        cpuTimeLabel = new JLabel("CPU Time: " + gameState.getCpuTime());
////        cpuPointsLabel = new JLabel("CPU Points: " + gameState.getCpuPoints());
////
////        topPanel.add(humanTimeLabel);
////        topPanel.add(cpuTimeLabel);
////        topPanel.add(humanPointsLabel);
////        topPanel.add(cpuPointsLabel);
////
////        add(topPanel, BorderLayout.NORTH);
////
////        // Center panel - Game board with clues
////        JPanel centerPanel = new JPanel(new BorderLayout(5, 5));
////        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
////
////        // Top clues
////        JPanel topCluesPanel = new JPanel(new GridLayout(1, GRID_SIZE));
////        int[] topClues = gameState.getTopClues();
////        for (int i = 0; i < GRID_SIZE; i++) {
////            JLabel clue = new JLabel(String.valueOf(topClues[i]), SwingConstants.CENTER);
////            clue.setFont(new Font("Arial", Font.BOLD, 14));
////            topCluesPanel.add(clue);
////        }
////        centerPanel.add(topCluesPanel, BorderLayout.NORTH);
////
////        // Left clues
////        JPanel leftCluesPanel = new JPanel(new GridLayout(GRID_SIZE, 1));
////        int[] leftClues = gameState.getLeftClues();
////        for (int i = 0; i < GRID_SIZE; i++) {
////            JLabel clue = new JLabel(String.valueOf(leftClues[i]), SwingConstants.CENTER);
////            clue.setFont(new Font("Arial", Font.BOLD, 14));
////            leftCluesPanel.add(clue);
////        }
////        centerPanel.add(leftCluesPanel, BorderLayout.WEST);
////
////        // Right clues
////        JPanel rightCluesPanel = new JPanel(new GridLayout(GRID_SIZE, 1));
////        int[] rightClues = gameState.getRightClues();
////        for (int i = 0; i < GRID_SIZE; i++) {
////            JLabel clue = new JLabel(String.valueOf(rightClues[i]), SwingConstants.CENTER);
////            clue.setFont(new Font("Arial", Font.BOLD, 14));
////            rightCluesPanel.add(clue);
////        }
////        centerPanel.add(rightCluesPanel, BorderLayout.EAST);
////
////        // Bottom clues
////        JPanel bottomCluesPanel = new JPanel(new GridLayout(1, GRID_SIZE));
////        int[] bottomClues = gameState.getBottomClues();
////        for (int i = 0; i < GRID_SIZE; i++) {
////            JLabel clue = new JLabel(String.valueOf(bottomClues[i]), SwingConstants.CENTER);
////            clue.setFont(new Font("Arial", Font.BOLD, 14));
////            bottomCluesPanel.add(clue);
////        }
////        centerPanel.add(bottomCluesPanel, BorderLayout.SOUTH);
////
////        // Game board
////        JPanel boardPanel = new JPanel(new GridLayout(GRID_SIZE, GRID_SIZE, 2, 2));
////        boardPanel.setBackground(Color.BLACK);
////        cellButtons = new JButton[GRID_SIZE][GRID_SIZE];
////
////        for (int r = 0; r < GRID_SIZE; r++) {
////            for (int c = 0; c < GRID_SIZE; c++) {
////                final int row = r;
////                final int col = c;
////                JButton btn = new JButton("");
////                btn.setFont(new Font("Arial", Font.BOLD, 24));
////                btn.setPreferredSize(new Dimension(60, 60));
////                btn.addActionListener(e -> handleCellClick(row, col));
////                cellButtons[r][c] = btn;
////                boardPanel.add(btn);
////            }
////        }
////        centerPanel.add(boardPanel, BorderLayout.CENTER);
////
////        add(centerPanel, BorderLayout.CENTER);
////
////        // Right panel - Controls and log
////        JPanel rightPanel = new JPanel(new BorderLayout(5, 5));
////        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
////        rightPanel.setPreferredSize(new Dimension(250, 0));
////
////        JPanel controlPanel = new JPanel(new GridLayout(5, 1, 5, 5));
////
////        turnLabel = new JLabel("Turn: Human", SwingConstants.CENTER);
////        turnLabel.setFont(new Font("Arial", Font.BOLD, 14));
////        controlPanel.add(turnLabel);
////
////        JLabel stratLabel = new JLabel("CPU Strategy:");
////        controlPanel.add(stratLabel);
////
////        strategySelector = new JComboBox<>(new String[]{
////                "Balanced", "Score-Greedy", "Time-Greedy"
////        });
////        strategySelector.addActionListener(e -> updateAIStrategy());
////        controlPanel.add(strategySelector);
////
////        JButton newGameBtn = new JButton("New Game");
////        newGameBtn.addActionListener(e -> resetGame());
////        controlPanel.add(newGameBtn);
////
////        rightPanel.add(controlPanel, BorderLayout.NORTH);
////
////        logArea = new JTextArea(15, 20);
////        logArea.setEditable(false);
////        JScrollPane scrollPane = new JScrollPane(logArea);
////        scrollPane.setBorder(BorderFactory.createTitledBorder("Game Log"));
////        rightPanel.add(scrollPane, BorderLayout.CENTER);
////
////        add(rightPanel, BorderLayout.EAST);
////
////        log("Game started! Human plays first.");
////    }
////
////    private void handleCellClick(int row, int col) {
////        if (!gameState.isHumanTurn() || gameState.isGameOver()) {
////            return;
////        }
////
////        if (gameState.getCellValue(row, col) != 0) {
////            log("Cell already occupied!");
////            return;
////        }
////
////        // Prompt for value
////        String input = JOptionPane.showInputDialog(this,
////                "Enter tower height (1-" + GRID_SIZE + "):",
////                "Place Tower",
////                JOptionPane.QUESTION_MESSAGE);
////
////        if (input == null) return; // Cancelled
////
////        try {
////            int value = Integer.parseInt(input.trim());
////            TowersMove move = new TowersMove(row, col, value);
////
////            boolean success = gameState.makeMove(move, false);
////            if (success) {
////                log("Human placed " + value + " at (" + row + "," + col + ") - Correct!");
////                updateDisplay();
////
////                if (!gameState.isGameOver()) {
////                    SwingUtilities.invokeLater(() -> executeCPUTurn());
////                } else {
////                    endGame();
////                }
////            } else {
////                log("Human tried " + value + " at (" + row + "," + col + ") - WRONG! Penalty applied.");
////                updateDisplay();
////                if (gameState.isGameOver()) {
////                    endGame();
////                }
////            }
////        } catch (NumberFormatException e) {
////            log("Invalid input!");
////        }
////    }
////
////    private void executeCPUTurn() {
////        if (gameState.isGameOver()) {
////            endGame();
////            return;
////        }
////
////        log("CPU is thinking...");
////
////        // Simulate thinking time
////        Timer timer = new Timer(500, e -> {
////            TowersMove cpuMove = ai.selectMove(gameState);
////
////            if (cpuMove == null) {
////                log("CPU has no valid moves!");
////                endGame();
////                return;
////            }
////
////            boolean success = gameState.makeMove(cpuMove, true);
////            if (success) {
////                log("CPU placed " + cpuMove.value + " at (" + cpuMove.row + "," + cpuMove.col + ") - Correct!");
////            } else {
////                log("CPU tried " + cpuMove.value + " at (" + cpuMove.row + "," + cpuMove.col + ") - WRONG!");
////            }
////
////            updateDisplay();
////
////            if (gameState.isGameOver()) {
////                endGame();
////            }
////        });
////        timer.setRepeats(false);
////        timer.start();
////    }
////
////    private void updateDisplay() {
////        // Update board
////        for (int r = 0; r < GRID_SIZE; r++) {
////            for (int c = 0; c < GRID_SIZE; c++) {
////                int value = gameState.getCellValue(r, c);
////                cellButtons[r][c].setText(value == 0 ? "" : String.valueOf(value));
////                cellButtons[r][c].setEnabled(value == 0 && gameState.isHumanTurn());
////            }
////        }
////
////        // Update status
////        humanTimeLabel.setText("Human Time: " + gameState.getHumanTime());
////        humanPointsLabel.setText("Human Points: " + gameState.getHumanPoints());
////        cpuTimeLabel.setText("CPU Time: " + gameState.getCpuTime());
////        cpuPointsLabel.setText("CPU Points: " + gameState.getCpuPoints());
////        turnLabel.setText("Turn: " + (gameState.isHumanTurn() ? "Human" : "CPU"));
////    }
////
////    private void endGame() {
////        String winner = gameState.getWinner();
////        log("=== GAME OVER ===");
////        log("Winner: " + winner);
////        log("Final Scores:");
////        log("  Human: " + gameState.getHumanPoints() + " points, " + gameState.getHumanTime() + " time");
////        log("  CPU: " + gameState.getCpuPoints() + " points, " + gameState.getCpuTime() + " time");
////
////        JOptionPane.showMessageDialog(this,
////                "Game Over!\nWinner: " + winner +
////                        "\n\nHuman: " + gameState.getHumanPoints() + " points, " + gameState.getHumanTime() + " time" +
////                        "\nCPU: " + gameState.getCpuPoints() + " points, " + gameState.getCpuTime() + " time",
////                "Game Over",
////                JOptionPane.INFORMATION_MESSAGE);
////    }
////
////    private void resetGame() {
////        gameState = new TowersGameState(GRID_SIZE);
////        logArea.setText("");
////        updateDisplay();
////        log("Game reset! Human plays first.");
////    }
////
////    private void updateAIStrategy() {
////        String selected = (String) strategySelector.getSelectedItem();
////        switch (selected) {
////            case "Score-Greedy":
////                ai.setMode(GreedyTowersAI.GreedyMode.SCORE_GREEDY);
////                log("CPU strategy: Score-Greedy (maximize points)");
////                break;
////            case "Time-Greedy":
////                ai.setMode(GreedyTowersAI.GreedyMode.TIME_GREEDY);
////                log("CPU strategy: Time-Greedy (maximize time)");
////                break;
////            case "Balanced":
////                ai.setMode(GreedyTowersAI.GreedyMode.BALANCED);
////                log("CPU strategy: Balanced (both points and time)");
////                break;
////        }
////    }
////
////    private void log(String message) {
////        logArea.append(message + "\n");
////        logArea.setCaretPosition(logArea.getDocument().getLength());
////    }
////
////    public static void main(String[] args) {
////        SwingUtilities.invokeLater(() -> new TowersGameGUI());
////    }
////}
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//import java.util.*;
//
//class Solution {
//    public int getMinimumServers(int n, List<int[]> serverConnections) {
//        // Build adjacency list for the undirected graph
//        List<List<Integer>> graph = new ArrayList<>();
//        for (int i = 0; i <= n; i++) {
//            graph.add(new ArrayList<>());
//        }
//        
//        for (int[] connection : serverConnections) {
//            int a = connection[0];
//            int b = connection[1];
//            graph.get(a).add(b);
//            graph.get(b).add(a);
//        }
//        
//        // BFS to find shortest path from server 1 to server n
//        Queue<Integer> queue = new LinkedList<>();
//        int[] distance = new int[n + 1];
//        Arrays.fill(distance, -1);
//        
//        queue.offer(1);
//        distance[1] = 1; // Start node counts as 1 server
//        
//        while (!queue.isEmpty()) {
//            int current = queue.poll();
//            
//            // If we reached server n, return the distance
//            if (current == n) {
//                return distance[n];
//            }
//            
//            // Explore neighbors
//            for (int neighbor : graph.get(current)) {
//                if (distance[neighbor] == -1) { // Not visited
//                    distance[neighbor] = distance[current] + 1;
//                    queue.offer(neighbor);
//                }
//            }
//        }
//        
//        // If server n is not reachable, return -1
//        return -1;
//    }
//}
