package locks;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class SemLock implements Lock {
	Semaphore state = new Semaphore(1);
	
	public void lock() {
		try {
            state.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
	}
	
	public void unlock() {
		state.release();
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