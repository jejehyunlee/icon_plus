package com.icon_plus.logical_test.soal_1;

public class AngkaDalamArray {

    public static int countNumbers(String[] arr) {
        int count = 0;

        for (String element : arr) {
            // Cek jika element adalah angka
            if (element.matches("\\d+")) {
                count++;
            }
        }

        return count;
    }

    public static void main(String[] args) {
        // Test cases
        String[][] testCases = {
                {"2", "h", "6", "u", "y", "t", "7", "j", "y", "h", "8"}, // Output: 4
                {"b", "7", "h", "6", "h", "k", "i", "5", "g", "7", "8"},  // Soal 1
                {"7", "b", "8", "5", "6", "9", "n", "f", "y", "6", "9"},  // Soal 2
                {"u", "h", "b", "n", "7", "6", "5", "1", "g", "7", "9"}   // Soal 3
        };

        for (int i = 0; i < testCases.length; i++) {
            System.out.println("Input " + (i + 1) + ": " + java.util.Arrays.toString(testCases[i]));
            System.out.println("Output: " + countNumbers(testCases[i]));
            System.out.println();
        }
    }
}
