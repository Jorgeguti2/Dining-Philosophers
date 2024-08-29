/**
 * DiningPhilosophers.java
 *
 * This program starts the dining philosophers problem.
 *
 */

// Importing necessary libraries to create forks
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DiningPhilosophers {
    public static void main(String[] args) throws Exception {
        // variable to mark the number of philosophers
        int numPhilosphers = 5;

        // array of the forks in between the philosophers
        Lock []forks = new Lock[numPhilosphers];

        //initialize forks
        for(int i= 0; i< numPhilosphers;i++){
            forks[i] = new ReentrantLock();
        }

        //pass the forks into DiningServerImpl to be able to use
        // the forks array in DiningServerImpl
        DiningServerImpl diningServer = new DiningServerImpl(forks);

        //creates 5 philosophers, assigning them their number and the forks
        Philosopher philosophers1 = new Philosopher(0, diningServer);
        Philosopher philosophers2 = new Philosopher(1, diningServer);
        Philosopher philosophers3 = new Philosopher(2, diningServer);
        Philosopher philosophers4 = new Philosopher(3, diningServer);
        Philosopher philosophers5 = new Philosopher(4, diningServer);

        //create five threads, one for each philosopher
        Thread t1 = new Thread(philosophers1, "Philosopher #1");
        Thread t2 = new Thread(philosophers2, "Philosopher #2");
        Thread t3 = new Thread(philosophers3, "Philosopher #3");
        Thread t4 = new Thread(philosophers4, "Philosopher #4");
        Thread t5 = new Thread(philosophers5, "Philosopher #5");

        //start the threads
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();

        //join the threads to ensure that they run simultaneously
        t1.join();
        t2.join();
        t3.join();
        t4.join();
        t5.join();
    }
}