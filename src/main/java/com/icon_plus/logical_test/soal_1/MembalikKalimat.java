package com.icon_plus.logical_test.soal_1;

public class MembalikKalimat {
        public static String reverseWords(String sentence) {
            String[] words = sentence.split(" ");
            StringBuilder reversed = new StringBuilder();

            for (String word : words) {
                reversed.append(new StringBuilder(word).reverse()).append(" ");
            }

            return reversed.toString().trim();
        }

        public static void main(String[] args) {
            String[] sentences = {
                    "italem irad irigayaj",
                    "iadab itsap ulalreb",
                    "nalub kusutret gnalali"
            };

            for (String sentence : sentences) {
                System.out.println("Original: " + sentence);
                System.out.println("Reversed: " + reverseWords(sentence));
                System.out.println();
            }
        }



}
