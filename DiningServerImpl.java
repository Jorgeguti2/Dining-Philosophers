/**
 * DiningServer.java
 *
 * This class contains the methods called by the  philosophers.
 *
 */

// import libraries to be able to use locks for the forks when philosophers are eating or not
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
public class DiningServerImpl implements DiningServer {
    //initialize locks to use for forks when eating or not
    final Lock lock = new ReentrantLock();
    final Condition notFull = lock.newCondition();
    final Condition notEmpty = lock.newCondition();
    //initialize array of locks for the forks
    private Lock[] forks;

    //passed in forks to this file
    public DiningServerImpl(Lock [] forks){
        this.forks = forks;
    }

    //Checks to see if the left and right fork are available.
    //If not, then it has the philosopher wait until they are.
    //If so, it has the philosopher pick the forks up to eat.
    @Override
    public String takeForks(int philNumber) throws InterruptedException {
        //block all other threads from accessing this method
        lock.lock();
        try{
            //If statement to test if both forks on left and right side are occupied
            if(((ReentrantLock) forks[philNumber]).isLocked() && ((ReentrantLock) forks[(philNumber + 1) % 5]).isLocked()){
                //have philosopher wait if true
                notEmpty.await();
            }
            //If statement to test if the right fork is locked
            if ( ((ReentrantLock) forks[philNumber]).isLocked()){
                //have philosopher wait if true
                notEmpty.await();
            }
            //If statement to test if both forks are available
            if( !((ReentrantLock) forks[philNumber]).isLocked() && !((ReentrantLock) forks[(philNumber+1)%5]).isLocked()){
                //if so, lock both forks and signal the waiting philosopher to eat
                forks[philNumber].lock();
                forks[(philNumber+1) % 5].lock();
                notFull.signalAll();
                //print eating when philosopher is eating
                return ("Eating");
            }
        }finally{
            //unlock the method
            lock.unlock();
        }
        //print hungry when philosopher is waiting to eat
        return ("Hungry");
    }

    //When philosopher is done eating, returnForks is called to unlock both forks,
    // making them available
    @Override
    public String returnForks(int philNumber) throws InterruptedException {
        //lock other threads from using this method
        lock.lock();
        try{
            //unlock the left and right forks and signal that the forks are open
            forks[philNumber].unlock();
            forks[(philNumber + 1) % 5].unlock();
            notFull.signalAll();
            // print thinking to symbolize the philosopher is full and done eating
            return("Thinking");
        }finally{
            //unlock this method
            lock.unlock();
        }
    }
}
