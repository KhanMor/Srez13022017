package com.kharkhanov;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Main {
    public volatile static boolean flag = false;
    public static Set<Integer> uniqueNumbers = new HashSet<>();

    public static void main(String[] args) {
        System.out.println("Program started.");

	    Thread threadRandomer = new Thread(new Runnable() {
	        private Random random = new Random();
            @Override
            public void run() {
                while (!flag) {
                    try {
                        Thread.sleep(1000);
                        Integer newNumber = random.nextInt(99);
                        uniqueNumbers.add(newNumber);
                        if(uniqueNumbers.size() == 100) {
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
