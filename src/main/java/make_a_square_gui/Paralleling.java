package make_a_square_gui;

import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import java.util.*;

interface constants {
    public final int gridRows = 4;
    public final int gridCols = 4;

    public final int numberOfPieces = 5;
    public final int logNumberOfPieces = 3; // log2(numberOfPieces-1)+1

    public final int numberOfMoves = 4;
    public final int logNumberOfMoves = 2; // log2(numberOfMoves-1)+1

    public final int bitsPerPiece = (logNumberOfPieces + logNumberOfMoves);
    public final int bitsPerBoard = numberOfPieces * bitsPerPiece;
    public final int numberOfBoards = (1 << bitsPerBoard);

    public final int numberOfThreads = 12;
    public final int sectionSize = numberOfBoards / numberOfThreads;
}

public class Paralleling implements Runnable {

    static boolean foundBoard;
    private ReentrantLock lock;
    public static int[][] finallyBoard;
    static public Map<Integer, int[][]> allPieces;
    public static Set<int[][]> allBoards = new HashSet<>(); // to avoid duplicates

    public Paralleling() {
        lock = new ReentrantLock();
    }

    @Override
    public void run() {
        int[][] finalBoard;
        int threadID = Integer.parseInt(Thread.currentThread().getName());

        int from = threadID * constants.sectionSize;
        int to = Math.min(from + constants.sectionSize - 1, constants.numberOfBoards - 1);
        if (threadID == constants.numberOfThreads - 1)
            to = constants.numberOfBoards - 1;

        // last thread must complete to the end of the states.
        for (int mask = from; mask <= to; mask++) {
            Board slaveBoard = new Board(allPieces);
            // slaveBoard.decompose(mask);
            lock.lock();
            finalBoard = slaveBoard.decompose(mask);
            lock.unlock();
            if (foundBoard)
                break;

            if (finalBoard != null) {
                lock.lock();
                foundBoard = true;
                finallyBoard = finalBoard;
                lock.unlock();
            }
        }
    }
}
