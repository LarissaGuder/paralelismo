package testesChap7;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.locks.*;

class Main {
    static Lock lock;
    static double[] sharedData;
    static int L = 1000000, N = 14, D = 0;
    static long TASTime1, TASTime2, result;

    // N: 2, 4, 6, 8, 10, 12, 14, 16.  (depende de onde roda)
    // L: nro de iterações - 1000.000.  (chute)
    // D:  por enquanto 0. ( nao ha tempo associado ao uso do recurso - unlock é imediato)

    static Thread thread(int n) {
        Thread t = new Thread(() -> {
            for (int i = 0; i < L; i++) {
                lock.lock();
                lock.unlock();
            }
        });
        t.start();
        return t;
    }

    static void testThreads(int TH) {
        Thread[] threads = new Thread[TH];
        for (int i = 0; i < TH; i++)
            threads[i] = thread(i);
        try {
            for (int i = 0; i < TH; i++)
                threads[i].join();
        } catch (InterruptedException e) {
        }
    }

    public static void main(String[] args) {
        try {
            PrintWriter writer = new PrintWriter("testesChap7\\graphs\\results.csv", "UTF-8");
            for (int i = 2; i < N; i = i + 2) {
                TASTime1 = System.nanoTime();
                lock = new TASLock();
                testThreads(i);
                TASTime2 = System.nanoTime();
                result = TASTime2 - TASTime1;

                TASTime1 = System.nanoTime();
                lock = new TTASLock();
                testThreads(i);
                TASTime2 = System.nanoTime();
                long resultTTAS = TASTime2 - TASTime1;

                TASTime1 = System.nanoTime();
                lock = new BackoffLock();
                testThreads(i);
                TASTime2 = System.nanoTime();
                long resultBackOff = TASTime2 - TASTime1;

                writer.println(i + "," + result + "," + resultTTAS + "," + resultBackOff);
            }
            writer.close();

        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

}