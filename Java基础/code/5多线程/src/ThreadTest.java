/**
 * 演示线程的死锁问题
 * <p>
 * 1.死锁的理解：不同的线程分别占用对方需要的同步资源不放弃，
 * 都在等待对方放弃自己需要的同步资源，就形成了线程的死锁
 * <p>
 * 2.说明：
 * 1）出现死锁后，不会出现异常，不会出现提示，只是所有的线程都处于阻塞状态，无法继续
 * 2）我们使用同步时，要避免出现死锁。
 *
 * @author shkstart
 * @create 2019-02-15 下午 3:20
 */
public class ThreadTest {

    public static void main(String[] args) {

        Object s1 = new Object();
        Object s2 = new Object();

        new Thread() {
            @Override
            public void run() {
                synchronized (s1) {
                    System.out.println("线程1 -- 步骤一：sleep睡前");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("线程1 -- 步骤一：sleep睡后");
                    synchronized (s2) {
                        System.out.println("线程1 -- 步骤二");
                    }
                }
            }
        }.start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (s2) {
                    System.out.println("线程2 -- 步骤一：sleep睡前");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("线程2 -- 步骤一：sleep睡后");
                    synchronized (s1) {
                        System.out.println("线程2 -- 步骤二");
                    }
                }
            }
        }).start();
    }
}
