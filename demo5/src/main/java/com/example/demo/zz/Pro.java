package com.example.demo.zz;

public class Pro {

    static class Lock {
        public int lock = 1;
         void setValue(int value){
            this.lock = value;
        }

        public  int getLock() {
            return lock;
        }
    }

    static class Item implements Runnable{
        volatile Lock lock;
        int value;
        int i;

        public Item(Lock lock, int value, int i) {
            this.lock = lock;
            this.value = value;
            this.i = i;
        }

        @Override
        public void run() {
                while (i<100) {
                    while (lock.getLock() == value){
                        System.out.println(Thread.currentThread().getName()+":"+i);
                        i+=2;
                        lock.setValue((-1)*lock.getLock());
                    }
            }
        }
    }



    public static void main(String[] args) {
        Lock lock = new Lock();
        Lock lock1 = new Lock();
        Item item = new Item(lock, 1, 1);
        Item item1 = new Item(lock, -1, 2);
        Thread thread = new Thread(item,"线程1");
        Thread thread1 = new Thread(item1,"线程2");
        thread.start();
        thread1.start();
    }
}
