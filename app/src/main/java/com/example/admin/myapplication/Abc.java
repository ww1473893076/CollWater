package com.example.admin.myapplication;

public class Abc {
    public static int count = 0;
    public static void main(String args[]){
//        System.out.println(count);
//
//
//        Thread thread = new Thread(){
//            @Override
//            public void run() {
//               System.out.println("hello world two!");
//            }
//        };
//         new Thread(){
//            @Override
//            public void run() {
//                for ( int i = 0; i < 5; i++) {
//                    count = count + 1;
//                    System.out.print("+1");
//                }
//            }
//        };

//
//        thread.start();
        (new Thread(){
            @Override
            public void run() {
               System.out.println("1");
               while(true){
                   try {
                       Thread.sleep(1000);
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
                  countCar();
               }
            }
        }).start();

        (new Thread(){
            @Override
            public void run() {
                while(true){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                   // if(count!=0) {
                       printCar();
                   // }
                }

            }
        }).start();
    }
     static void printCar(){
        System.out.println("过去的车辆数=>" + count);
        count = 0;
    }
    synchronized static void countCar(){
        count++;
        System.out.println("又过了一辆车");

    }
}
