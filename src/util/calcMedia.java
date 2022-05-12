package util;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

public class calcMedia {

    static Thread thread(int numIterations, int sizeBubbleSort, Lock lock) {
        Thread t = new Thread(() -> {
            for (int i = 0; i < numIterations; i++) {
                lock.lock();
                if (sizeBubbleSort > 0)
                    bubbleSort(sizeBubbleSort);
                lock.unlock();
            }
        });
        t.start();
        return t;
    }

    // Consolidado
    static long testThreads(int numThreadsExec, int sizeBubbleSort, Lock lock) {
        long start, end;
        start = System.nanoTime();
        Thread[] threads = new Thread[numThreadsExec];
        for (int i = 0; i < numThreadsExec; i++)
            threads[i] = thread(i, sizeBubbleSort, lock);
        try {
            for (int i = 0; i < numThreadsExec; i++) {
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
    static void bubbleSort(int sizeBubbleSort) {
        int temp, arraysize = sizeBubbleSort;
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

    public static long returnMedia(int numThreadsExec, int numIterations, int numRepetitions, int sizeBubbleSort,
            Lock lock) {
        if (numRepetitions == 1) {
            return TimeUnit.MILLISECONDS.convert(testThreads(numThreadsExec, sizeBubbleSort, lock),
                    TimeUnit.NANOSECONDS);
        } else if (numRepetitions == 2) {
            long a = testThreads(numThreadsExec, sizeBubbleSort, lock);
            long b = testThreads(numThreadsExec, sizeBubbleSort, lock);
            return TimeUnit.MILLISECONDS.convert((a + b) / 2, TimeUnit.NANOSECONDS);
        }
        long[] result = new long[numRepetitions];
        long media = 0;
        for (int i = 0; i < numRepetitions; i++) {
            result[i] = testThreads(numThreadsExec, sizeBubbleSort, lock);
        }
        Arrays.sort(result);
        for (int i = 1; i < numRepetitions - 1; i++) {
            media += result[i];
        }
        media = media / (numRepetitions - 2);
        System.out.println(" time >> " + media + " >> n threads  " + numThreadsExec);
        media = TimeUnit.MILLISECONDS.convert(media, TimeUnit.NANOSECONDS);
        return media;
    }
}
