package locks;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class MCSLock implements Lock {
    AtomicReference<QNode> queue;
    ThreadLocal<QNode> myNode;

    public MCSLock() {
        queue = new AtomicReference<>(null);
        // initialize thread-local variable
        myNode = ThreadLocal.withInitial(() -> new QNode());
    }

    @Override
    public void lock() {
        QNode qnode = myNode.get();
        QNode pred = queue.getAndSet(qnode);
        if (pred != null) {
            qnode.locked = true;
            pred.next = qnode;
            while (qnode.locked) {
            } // spin
        }
    }

    @Override
    public void unlock() {
        QNode qnode = myNode.get();
        if (qnode.next == null) {
            if (queue.compareAndSet(qnode, null))
                return;
            while (qnode.next == null) {
            } // spin
        }
        qnode.next.locked = false;
        qnode.next = null;
    }

    static class QNode { // Queue node inner class
        volatile boolean locked = false;
        volatile QNode next = null;
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