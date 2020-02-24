package com.orange.jvm.demo;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author orangeC
 * @description
 * @date 2019/12/29 19:32
 */
public class BlockingQueueDemo {

    public static void main(String[] args) throws InterruptedException {
       BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);

         /*System.out.println(blockingQueue.add("a"));
        System.out.println( blockingQueue.add("b"));
        System.out.println(blockingQueue.add("c"));

        System.out.println(blockingQueue.element());

        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());*/

       /*
        System.out.println(blockingQueue.offer("a"));
        System.out.println(blockingQueue.offer("a"));
        System.out.println(blockingQueue.offer("a"));

        System.out.println(blockingQueue.peek());

        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());*/


       /* try {
            blockingQueue.put("a");
            blockingQueue.put("a");
            blockingQueue.put("a");
            System.out.println("===================");

            blockingQueue.take();
            blockingQueue.take();
            blockingQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        System.out.println(blockingQueue.offer("a", 2L, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("a", 2L, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("a", 2L, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("a", 2L, TimeUnit.SECONDS));
    }
}
