package DeadLocks;



public class Syncronized1 {

    private static int counter = 0;

    public static synchronized void increase(){
        counter++;
    }

    public static void process() {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1000 ; i++) {
                    increase();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1000 ; i++) {
                    increase();
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
    public static void main(String[] args) {
        process();
        System.out.println(counter);

    }
}