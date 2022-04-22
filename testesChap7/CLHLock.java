package testesChap7;

import java.util.concurrent.atomic.AtomicReference;

public class CLHLock implements Lock {
    AtomicReference<QNode> tail;
    ThreadLocal<QNode> myPred;
    ThreadLocal<QNode> myNode;

    public CLHLock() {
        tail = new AtomicReference<QNode>(new QNode());
        myNode = new ThreadLocal<QNode>() {
            protected QNode initialValue() {
                return new QNode();
            }
        };
        myPred = new ThreadLocal<QNode>() {
            protected QNode initialValue() {
                return null;
            }
        };
    }

    @Override
    public boolean trylock() {
        return false;
    }

    @Override
    public void lock() {

        QNode qnode = myNode.get();
        qnode.locked = true;
        QNode pred = tail.getAndSet(qnode);
        myPred.set(pred);
        while (pred.locked) {
        }

    }

    @Override
    public void unlock() {

        QNode qnode = myNode.get();
        qnode.locked = false;
        myNode.set(myPred.get());

    }

    static class QNode { // Queue node inner class
        public volatile boolean locked = false;
    }
}