
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.locks.*;

import locks.ALock;
import locks.BackoffLock;
import locks.CLHLock;
import locks.CompositeFastPathLock;
import locks.CompositeLock;
import locks.MCSLock;
import locks.SemLock;
import locks.TASLock;
import locks.TTASLock;
import util.calcMedia;

class Main {

    public static void main(String[] args) {

        if (args.length != 5) {
            System.out.println("Usage: java numThreads numIterations numRepetitions sizeBubbleSort fileName");
        } else {
            System.out.println("Executando experimentos");
            runExperiment(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]),
                    Integer.parseInt(args[3]), args[4]);
        }
    }

    static void runExperiment(int numThreads, int numIterations, int numRepetitions, int sizeBubbleSort,
            String fileName) {
        Lock lock;

        PrintWriter writer;
        try {
            writer = new PrintWriter("src\\graphs\\" + fileName + ".csv", "UTF-8");
            for (int numThreadsExec = 2; numThreadsExec <= numThreads; numThreadsExec = numThreadsExec + 2) {
                writer.print(numThreadsExec + ",");
                // System.out.print(">> TASLock >>");
                lock = new TASLock();
                writer.print(calcMedia.returnMedia(numThreadsExec, numIterations, numRepetitions, sizeBubbleSort, lock)
                        + ",");
                // System.out.print(">> TTASLock >>");
                lock = new TTASLock();
                writer.print(calcMedia.returnMedia(numThreadsExec, numIterations, numRepetitions, sizeBubbleSort, lock)
                        + ",");
                // System.out.print(">> BackoffLock >>");
                lock = new BackoffLock();
                writer.print(calcMedia.returnMedia(numThreadsExec, numIterations, numRepetitions, sizeBubbleSort, lock)
                        + ",");
                // System.out.print(">> SemLock >>");
                lock = new SemLock();
                writer.print(calcMedia.returnMedia(numThreadsExec, numIterations, numRepetitions, sizeBubbleSort, lock)
                        + ",");
                // System.out.print(">> ALock >>");
                // Verificar motivo de travar ao executar com maior número de threads
                lock = new ALock(numThreadsExec);
                writer.print(calcMedia.returnMedia(numThreadsExec, numIterations, numRepetitions, sizeBubbleSort, lock)
                        + ",");
                // System.out.print(">> MCSLock >>");
                lock = new MCSLock();
                writer.print(calcMedia.returnMedia(numThreadsExec, numIterations, numRepetitions, sizeBubbleSort, lock)
                        + ",");
                // System.out.print(">> CLHLock >>");
                lock = new CLHLock();
                writer.print(calcMedia.returnMedia(numThreadsExec, numIterations, numRepetitions, sizeBubbleSort, lock)
                        + ",");
                lock = new CompositeFastPathLock();
                writer.print(calcMedia.returnMedia(numThreadsExec, numIterations, numRepetitions, sizeBubbleSort, lock)
                        + ",");
                lock = new CompositeLock();
                writer.println(
                        calcMedia.returnMedia(numThreadsExec, numIterations, numRepetitions, sizeBubbleSort, lock));
            }

            writer.close();

        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

}