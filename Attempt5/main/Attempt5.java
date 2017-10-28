import java.util.Random;

class Time {
    public static void delay(int msec) {
        // Pause thread for specified number of milliseconds
        try {
            Thread.sleep(msec);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

public class Attempt5 {

    public static volatile int c1 = 1;
    public static volatile int c2 = 1;

    public static void main(String[] args) {

        P1 thread1 = new P1();
        P2 thread2 = new P2();

        thread1.start();
        thread2.start();
    }
}

class P1 extends Thread {
    private Random rnd = new Random();
    private ThreadStatus status;

    public void run() {
        while (true) {
            nonCriticalSection();
            preProtocol();
            criticalSection();
            postProtocol();
        }
    }

    public void nonCriticalSection() {
        updateStatus(ThreadStatus.NON_CRITICAL_SECTION);
    }

    private void updateStatus(ThreadStatus status) {
        this.status = status;
        System.out.println("Thread 1: " + status);
    }

    public void preProtocol() {
        updateStatus(ThreadStatus.PRE_PROTOCOL);
//        yield();
        Time.delay(rnd.nextInt(120));
        Attempt5.c1 = 0;
//        yield();
        Time.delay(rnd.nextInt(120));
        while (Attempt5.c2 != 1) {
//            yield();
            Time.delay(rnd.nextInt(120));
            Attempt5.c1 = 1;
            yield();
//            Time.delay(rnd.nextInt(120));
            Attempt5.c1 = 0;
//            yield();
            Time.delay(rnd.nextInt(120));
        }
        updateStatus(ThreadStatus.LEAVING_PRE_PROTOCOL);
    }

    public void criticalSection() {
        updateStatus(ThreadStatus.ENTERING_CRITICAL_SECTION);
        updateStatus(ThreadStatus.CRITICAL_SECTION);
        Time.delay(rnd.nextInt(120));
        updateStatus(ThreadStatus.LEAVING_CRITICAL_SECTION);
    }

    public void postProtocol() {
        updateStatus(ThreadStatus.POST_PROTOCOL);
        Attempt5.c1 = 1;
        Time.delay(rnd.nextInt(120));
    }
}

class P2 extends Thread {
    private Random rnd = new Random();
    private ThreadStatus status;

    public void run() {
        while (true) {
            nonCriticalSection();
            preProtocol();
            criticalSection();
            postProtocol();
        }
    }

    public void nonCriticalSection() {
        updateStatus(ThreadStatus.NON_CRITICAL_SECTION);
    }

    private void updateStatus(ThreadStatus status) {
        this.status = status;
        System.out.println("Thread 2: " + status);
    }

    public void preProtocol() {
        updateStatus(ThreadStatus.PRE_PROTOCOL);
//        yield();
        Time.delay(rnd.nextInt(120));
        Attempt5.c2 = 0;
//        yield();
        Time.delay(rnd.nextInt(120));
        while (Attempt5.c1 != 1) {
//            yield();
            Time.delay(rnd.nextInt(120));
            Attempt5.c2 = 1;
            yield();
//            Time.delay(rnd.nextInt(120));
            Attempt5.c2 = 0;
//            yield();
            Time.delay(rnd.nextInt(120));
        }
        updateStatus(ThreadStatus.PRE_PROTOCOL);
    }

    public void criticalSection() {
        updateStatus(ThreadStatus.ENTERING_CRITICAL_SECTION);
        updateStatus(ThreadStatus.CRITICAL_SECTION);
        Time.delay(rnd.nextInt(120));
        updateStatus(ThreadStatus.LEAVING_CRITICAL_SECTION);
    }

    public void postProtocol() {
        updateStatus(ThreadStatus.POST_PROTOCOL);
        Attempt5.c2 = 1;
        Time.delay(rnd.nextInt(120));
    }
}

