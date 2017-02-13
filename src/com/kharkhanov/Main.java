package com.kharkhanov;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Main {
    private volatile static boolean flag = false;
    private static final int BOUND = 100;
    private static Set<Integer> uniqueNumbers = new HashSet<>(100);

    public static void main(String[] args) {
        System.out.println("Program started.");

	    Thread threadRandomer = new Thread(new Runnable() {
	        private Random random = new Random();
            @Override
            public void run() {
                while (!flag) {
                    try {
                        Thread.sleep(1000);
                        Integer newNumber = random.nextInt(BOUND);
                        uniqueNumbers.add(newNumber);
                        if(uniqueNumbers.size() == BOUND) {
                            flag = true;
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

	    Thread threadOuter = new Thread(new Runnable() {
            @Override
            public void run() {

                while (!flag) {
                    try {
                        Thread.sleep(5000);
                        System.out.println(uniqueNumbers);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

	    threadRandomer.start();
	    threadOuter.start();

        try {
            threadOuter.join();
            threadRandomer.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Program finished.");
    }
}
