package locks;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class BackoffLock implements Lock {
    private AtomicBoolean state = new AtomicBoolean(false);
    private static final int MIN_DELAY = 2;
    private static final int MAX_DELAY = 6;

    public void lock() {
        Backoff backoff = new Backoff(MIN_DELAY, MAX_DELAY);
        while (true) {
            while (state.get()) {
            }
            ;
            if (!state.getAndSet(true)) {

                return;

            } else {
                try {
                    backoff.backoff();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }

    }

    public void unlock() {
        state.set(false);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
