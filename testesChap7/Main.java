package testesChap7;

import java.util.concurrent.locks.*;

class Main {
    static Lock lock;
    static double[] sharedData;
    static int CS = 1000000; 
    // TH = 14;
    // CS: critical section executed per thread
    // TH: number of threads

    static Thread thread(int n) {
        Thread t = new Thread(() -> {
            for (int i = 0; i < CS; i++) {
                lock.lock();
                lock.unlock();
            }
        });
        t.start();
        return t;
    }

    // Tests to see if threads execute critical
    // section atomically.
    static void testThreads(int TH) {
        log("Starting " + TH + " threads ...");
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
        long time1 = System.nanoTime();
        lock = new TASLock();
        testThreads(14);
        long time12 = System.nanoTime();

        System.out.println("time " + (time12 - time1));
    }

    static void log(String x) {
        System.out.println(x);
    }
}