package testesChap7;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.locks.*;

class Main {
    static Lock lock;
    static int L = 100000, N = 16, D = 0;
    static long start, end;

    // N: 2, 4, 6, 8, 10, 12, 14, 16.  (depende de onde roda)
    // L: nro de iterações - 1000.000.  (chute)
    // D:  por enquanto 0. ( nao ha tempo associado ao uso do recurso - unlock é imediato)

    static Thread thread(int n) {
        Thread t = new Thread(() -> {
            for (int i = 0; i < L; i++) {
                lock.lock();
                /// Adicionar algum processamento  (cpu bound) >> NÃO SLEEP
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
            /// Repetir o exp e fazer média
            /// Repetir exp por n vezes, remover maior e menor resultado, e usar média
            for (int i = 2; i < N; i = i + 2) {
                writer.print(i + ",");
                start = System.nanoTime();
                lock = new TASLock();
                testThreads(i);
                end = System.nanoTime();
                writer.print(end - start + ",");
                start = System.nanoTime();
                lock = new TTASLock();
                testThreads(i);
                end = System.nanoTime();
                writer.print(end - start + ",");
                start = System.nanoTime();
                lock = new BackoffLock();
                testThreads(i);
                end = System.nanoTime();
                writer.print(end - start + ",");
                start = System.nanoTime();
                lock = new SemLock();
                testThreads(i);
                end = System.nanoTime();
                writer.println(end - start);
            }
            writer.close();
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

}