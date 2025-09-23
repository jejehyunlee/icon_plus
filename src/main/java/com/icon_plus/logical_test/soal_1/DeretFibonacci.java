package com.icon_plus.logical_test.soal_1;

public class DeretFibonacci {

    public static void generateFibonacci(int n) {
        int a = 0, b = 1;

        System.out.print(a);
        if (n >= 1) System.out.print("," + b);

        for (int i = 2; i < n; i++) {
            int next = a + b;
            System.out.print("," + next);
            a = b;
            b = next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        generateFibonacci(9); // 0,1,1,2,3,5,8,13,21
    }
}


