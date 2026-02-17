package FOR_EVal2;

import java.util.*;

/**
 * STRATEGY : Divide & Conquer (Simplified Version)
 * Full board scan without quadrant merging.
 */
public class StrategyDnC {

    // ── core fields ──────────────────────────────────────────
    private final GameState state;
    private final int SIZE;

    // ── reward constants ─────────────────────────────────────
    private static final double BASE_REWARD         = 1.0;
    private static final double ROW_COMPLETE_REWARD = 12.0;
    private static final double COL_COMPLETE_REWARD = 12.0;
    private static final double VIS_VALID_BONUS     = 18.0;
    private static final double DOUBLE_BONUS        = 28.0;
    private static final double QUAD_CONTROL_BONUS  = 5.0;
    private static final double LOW_OPT_PENALTY     = -5.0;

    // ── constructor ──────────────────────────────────────────
    public StrategyDnC(GameState state) {
        this.state = state;
        this.SIZE  = state.getSize();
    }

    // ════════════════════════════════════════════════════════
    //  PUBLIC API
    // ════════════════════════════════════════════════════════

    /**
     * Find best move by scanning entire board.
     */
public int[] findBestMove() {
        // Divide board into quadrants; each returns its local champion.
        // Conquer: pick the global best among all champions.
        int half = SIZE / 2;

        // Four quadrant regions  [rowStart, rowEnd, colStart, colEnd]
        int[][] quads = {
                { 0,    half, 0,    half },   // top-left
                { 0,    half, half, SIZE },   // top-right
                { half, SIZE, 0,    half },   // bottom-left
                { half, SIZE, half, SIZE }    // bottom-right
        };

        List<MoveEval> allCandidates   = new ArrayList<>();
        List<MoveEval> quadChampions   = new ArrayList<>();
        List<String>   quadSummaries   = new ArrayList<>();
        int[]          quadEmptyCounts = new int[4];

        for (int q = 0; q < 4; q++) {
            int rS = quads[q][0], rE = quads[q][1];
            int cS = quads[q][2], cE = quads[q][3];

            List<MoveEval> quadMoves = solveQuadrant(rS, rE, cS, cE);
            quadEmptyCounts[q] = emptyInQuadrant(rS, rE, cS, cE);
            allCandidates.addAll(quadMoves);

            if (!quadMoves.isEmpty()) {
                quadMoves.sort(Comparator.comparingDouble(MoveEval::score).reversed());
                MoveEval champ = quadMoves.get(0);
                // bonus for controlling the richest quadrant
                if (quadEmptyCounts[q] == maxOf(quadEmptyCounts)) {
                    champ = new MoveEval(champ.row, champ.col, champ.value,
                            champ.score + QUAD_CONTROL_BONUS,
                            champ.legalOpts, champ.rowDone, champ.colDone);
                }
                quadChampions.add(champ);
                quadSummaries.add(String.format(
                        "  Q%d [r%d-%d c%d-%d]: best=%d@(%d,%d) score=%.1f empty=%d",
                        q+1, rS+1, rE, cS+1, cE,
                        champ.value, champ.row+1, champ.col+1,
                        champ.score, quadEmptyCounts[q]
                ));
            } else {
                quadSummaries.add(String.format(
                        "  Q%d [r%d–%d c%d–%d]: NO MOVES (empty=%d)",
                        q+1, rS+1, rE, cS+1, cE, quadEmptyCounts[q]
                ));
            }
        }

        if (quadChampions.isEmpty()) return null;

        // Merge step: pick the global champion
        quadChampions.sort(Comparator
                .comparingDouble(MoveEval::score).reversed()
                .thenComparingInt(m -> -m.legalOpts));   // more options = more control

        MoveEval best = quadChampions.get(0);
        state.setCpuReasoningExplanation(
                buildExplanation(best, quadSummaries, allCandidates.size()));
        return new int[]{ best.row, best.col, best.value };
    }

    /**
     * Heat-map score for a single cell.
     */
    public double evaluateCell(int row, int col) {

        if (state.getGrid()[row][col] != 0) return 0.0;

        double max = 0;

        for (int v = 1; v <= SIZE; v++) {

            if (state.getGraph().hasConflict(state.getGrid(), row, col, v))
                continue;

            int[][] after = deepCopy(state.getGrid());
            after[row][col] = v;

            double score = localScore(after, row, col, v);
            max = Math.max(max, score);
        }

        return max;
    }

    // ════════════════════════════════════════════════════════
    //  LOCAL SCORE
    // ════════════════════════════════════════════════════════

    private double localScore(int[][] grid, int row, int col, int value) {

        double score = BASE_REWARD;

        boolean rowDone = isRowComplete(grid, row);
        boolean colDone = isColComplete(grid, col);

        if (rowDone) score += ROW_COMPLETE_REWARD;
        if (colDone) score += COL_COMPLETE_REWARD;
        if (rowDone && colDone) score += DOUBLE_BONUS;

        int opts = legalCount(row, col);

        if (opts <= 1) score += LOW_OPT_PENALTY * 2;
        else if (opts <= 2) score += LOW_OPT_PENALTY;

        return score;
    }

    // ════════════════════════════════════════════════════════
    //  UTILITIES
    // ════════════════════════════════════════════════════════

    private boolean isRowComplete(int[][] g, int r) {
        for (int c = 0; c < SIZE; c++)
            if (g[r][c] == 0) return false;
        return true;
    }

    private boolean isColComplete(int[][] g, int c) {
        for (int r = 0; r < SIZE; r++)
            if (g[r][c] == 0) return false;
        return true;
    }

    private int legalCount(int row, int col) {
        int count = 0;
        for (int v = 1; v <= SIZE; v++)
            if (!state.getGraph().hasConflict(state.getGrid(), row, col, v))
                count++;
        return count;
    }

    private int[][] deepCopy(int[][] src) {
        int[][] copy = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++)
            copy[i] = src[i].clone();
        return copy;
    }
}

