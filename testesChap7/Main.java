package testesChap7;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.*;

class Main {
    static Lock lock;
    static int L = 1000, N = 16, D = 0, METHODS = 1, REPEAT = 10;
    static long start, end;

    // N: 2, 4, 6, 8, 10, 12, 14, 16. (depende de onde roda)
    // L: nro de iterações - 1000.000. (chute)
    // D: por enquanto 0. ( nao ha tempo associado ao uso do recurso - unlock é
    // imediato)
    // TODO: Implementar algoritmos do cap 7.5
    static Thread thread(int n) {
        Thread t = new Thread(() -> {
            for (int i = 0; i < L; i++) {
                lock.lock();
                /// Adicionar algum processamento (cpu bound) >> NÃO SLEEP
                bubbleSort();
                lock.unlock();
            }
        });
        t.start();
        return t;
    }

    // Consolidado
    static long testThreads(int TH) {
        start = System.nanoTime();
        Thread[] threads = new Thread[TH];
        for (int i = 0; i < TH; i++)
            threads[i] = thread(i);
        try {
            for (int i = 0; i < TH; i++) {
                threads[i].join();
            }
        } catch (InterruptedException e) {
            return 0;
        } finally {
            end = System.nanoTime();
        }
        return (end - start);
    }

    // Consolidado (CPU BOUND)
    static void bubbleSort() {
        int temp, arraysize = 10;
        int[] array = new int[arraysize];

        for (int i = 0; i < arraysize; i++) {
            array[i] = i;
        }

        for (int i = 0; i < (arraysize - 1); i++) {
            for (int j = 0; j < arraysize - i - 1; j++) {
                if (array[j] < array[j + 1]) {
                    temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
    }

    static long returnMedia(int thread) {
        long[] result = new long[REPEAT];
        long media = 0;
        for (int i = 0; i < REPEAT; i++) {
            result[i] = testThreads(thread);
        }
        Arrays.sort(result);
        for (int i = 1; i < REPEAT - 1; i++) {
            media += result[i];
        }
        media = media / (REPEAT - 2);
        // media = TimeUnit.SECONDS.convert(media, TimeUnit.NANOSECONDS);

        return media;
    }

    public static void main(String[] args) {
        /// Repetir o exp e fazer média
        /// Repetir exp por n vezes, remover maior e menor resultado, e usar média
        PrintWriter writer;
        try {
            writer = new PrintWriter("testesChap7\\graphs\\results.csv", "UTF-8");
            for (int i = 2; i < N; i = i + 2) {
                writer.print(i + ",");
                lock = new TASLock();
                writer.print(returnMedia(i) + ",");
                lock = new TTASLock();
                writer.print(returnMedia(i) + ",");
                lock = new BackoffLock();
                writer.print(returnMedia(i) + ",");
                lock = new SemLock();
                writer.print(returnMedia(i) + ",");
                // Saporra não está funcionando
                // lock = new ALock(120);
                // writer.print(testTASLock(i) + ",");
                lock = new MCSLock();
                writer.print(returnMedia(i) + ",");
                lock = new CLHLock();
                writer.println(returnMedia(i));
            }

            writer.close();

        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

}