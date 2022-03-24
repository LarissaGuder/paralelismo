
import java.util.concurrent.Semaphore;

// https://mkyong.com/java/java-thread-mutex-and-semaphore-example/
// Chapter 3.4 Multiplex -> TheLittleBookOfSemaphores
public class mutex {
    static Semaphore mutex = new Semaphore(1);
    volatile static int count = 1;

    static class MyLockerThread extends Thread {

        String name = "";

        MyLockerThread(String name) {
            this.name = name;
        }

        public void run() {
            try {
                while (true) {
                    System.out.println(name + " : solicitando bloqueio");
                    mutex.acquire();
                    System.out.println(name + " : com permissão");
                    try {
                        count = count + 1;
                        Thread.sleep(1000);
                    } finally {
                        System.out.println(name + " : liberando");
                        System.out.println("Count " + count);
                        mutex.release();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("Total de semaforos disponíveis : "
                + mutex.availablePermits());

        MyLockerThread t1 = new MyLockerThread("A");
        t1.start();

        MyLockerThread t2 = new MyLockerThread("B");
        t2.start();
    }
}