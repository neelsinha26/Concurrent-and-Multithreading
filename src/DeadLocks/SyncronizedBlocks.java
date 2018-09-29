package DeadLocks;

public class SyncronizedBlocks {

    public static int count1 = 0;
    public static int count2 = 0;

    private static Object lock1 = new Object();
    private static Object lock2 = new Object();

    // we donot use app.class here in place lock1 or lock2 because if we used app.class then in that case once a syncronized
    // function is running then in that case other syncronized wont run if they have the same class. so different objects
    // lock1 and lock2
    private static void add(){
        synchronized (lock1){
            count1++;
        }
    }

    private static void addAgain(){
        synchronized (lock2){
            count2++;
        }
    }


    public static void compute(){
        for (int i = 0; i < 100 ; i++) {
            add();
            addAgain();
        }
    }

    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                compute();
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                compute();
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

        System.out.println("Count1 - "+ count1);
        System.out.println("Count2 - "+ count2);
    }
}
