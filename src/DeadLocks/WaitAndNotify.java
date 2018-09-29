package DeadLocks;

public class WaitAndNotify{
    public static void main(String[] args) {

        Processor processor = new Processor();
        Thread t1 =  new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    processor.produce();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 =  new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    processor.consume();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}

class Processor{

    // both procucer and consumer are locking on the same object

    public void produce() throws InterruptedException{
        synchronized (this){
            System.out.println("Inside the Producer ..... ");
            wait(); // It can be called only from within the syncronized keyword. It hands over the control of the
                    // syncronised  block
            System.out.println("Again inside the Producer .......");
        }
    }

    public void consume() throws InterruptedException{

        Thread.sleep(1000);
        synchronized (this){
            System.out.println("Inside the Consumer ..... ");
            notify(); // It used to notify the waiting thread that it can wake up , its ok I am done you can acquire the
            // intrinsic lock of the thread again. It is not necessary it will go after this step. it will go after completing the
            // execution of this step
        }
    }
}
