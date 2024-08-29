/**
 * Philosopher.java
 *
 * This class represents each philosopher thread.
 * Philosophers alternate between eating and thinking.
 *
 */

public class Philosopher implements Runnable
{
    //Construct the philosopher object, giving them their number and access to the forks
    public int philNumber;
    public DiningServerImpl diningServerImpl = new DiningServerImpl(null);
    public Philosopher(int i,DiningServerImpl diningServerImpl) {
        this.diningServerImpl = diningServerImpl;
        this.philNumber= i;
    }

    //Method used to symbolize and execute the eating action
    public void eat(){
        String eatting;
        try {
            //calls takeForks for the philosopher to pick up both forks and begin eating
            eatting = diningServerImpl.takeForks(philNumber);
            System.out.println(Thread.currentThread().getName()+ " " + eatting);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    //Method used to symbolize and execute the thinking action
    public void thinking(){
        try {
            //calls returnForks for the philosopher to put down their forks and make them available, then proceed to think
            String thinking;
            thinking = diningServerImpl.returnForks(philNumber);
            System.out.println(Thread.currentThread().getName() + " "+ thinking);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    @Override
    public void run(){
        try {
            //philosophers eat then think, sleeping the thread when they are thinking
            while(true){
                eat();
                thinking();
                Thread.currentThread().sleep(2000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

