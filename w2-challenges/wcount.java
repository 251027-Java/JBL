/*
Question:
Ava is developing a text analytics tool that needs to analyze a sentence and count how many words in it begin with a vowel (a, e, i, o, u). She wants the program to ignore case sensitivity and consider words as sequences of letters separated by spaces.

Write a function to help Ava count the number of words that start with a vowel in the given sentence.

Function Description:
The function Main should take a string sentence as input and return an integer representing the count of words that begin with a vowel.

Constraints:
(1 ≤ len(sentence) ≤ 10^5)
The sentence contains only alphabetic characters and spaces.

Input Format:
    The input consists of a single line:
        A string sentence representing the sentence to analyze.

Output Format:
    Print a single integer:
        the number of words in the sentence that start with a vowel.

Sample Input:
An umbrella is outside on the avenue

Sample Output:
6
*/

import java.util.*;

public class wcount {
    public static void main(String[] args) {
        new wcount().run();
    }

    void run() {
        Scanner sc = new Scanner(System.in);

        int count = 0;
        Set<Character> vowels = Set.of('a', 'e', 'i', 'o', 'u');

        while (sc.hasNext()) {
            String s = sc.next();
            char c = Character.toLowerCase(s.charAt(0));

            if (vowels.contains(c)) {
                count++;
            }
        }

        System.out.println(count);

        sc.close();
    }
}
