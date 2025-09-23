package com.icon_plus.logical_test.soal_1;

public class Saham {

    public static int maxProfit(int[] prices) {
        if (prices == null || prices.length < 2) {
            return 0;
        }

        int minPrice = prices[0];
        int maxProfit = 0;

        for (int i = 1; i < prices.length; i++) {
            if (prices[i] < minPrice) {
                minPrice = prices[i];
            } else {
                int profit = prices[i] - minPrice;
                if (profit > maxProfit) {
                    maxProfit = profit;
                }
            }
        }

        return maxProfit;
    }

    public static void main(String[] args) {
        // Test cases
        int[][] testCases = {
                {10, 9, 6, 5, 15}, // Output: 10 (15-5)
                {7, 8, 3, 10, 8},   // Output: 7 (10-3)
                {5, 12, 11, 12, 10}, // Output: 7 (12-5)
                {7, 18, 27, 10, 29}, // Output: 22 (29-7)
                {20, 17, 15, 14, 10} // Output: 0 (tidak ada keuntungan)
        };

        for (int i = 0; i < testCases.length; i++) {
            System.out.println("Input " + (i + 1) + ": " + java.util.Arrays.toString(testCases[i]));
            System.out.println("Output: " + maxProfit(testCases[i]));
            System.out.println();
        }
    }
}

