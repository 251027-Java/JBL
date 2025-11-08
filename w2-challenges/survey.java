/*
Scenario:
    Noah is analyzing survey data where each response is rated from 1 to 5. He wants to know how many times each rating appears in the survey.
    Write a function to count the frequency of each rating from 1 to 5 in a given list of responses. Return an array of size 5 where the value at index i represents how many times rating i+1 appeared.

Function Description:
    The function countRatings should take an integer array responses as input and return an integer array of size 5 with the frequency of each rating.

Constraints:
    1 ≤ len(responses) ≤ 10^5
    Each element of responses is an integer between 1 and 5 inclusive

Input Format:
    A single line with space-separated integers representing the responses.

Output Format:
    A single line with 5 space-separated integers representing the frequency of ratings 1 through 5.

Sample Input:
1 2 2 3 4 2 1 5 3 3

Sample Output:
2 3 3 1 1
*/

import java.util.*;

public class survey {
    static void main() {
        new survey().run();
    }

    void run() {
        Scanner sc = new Scanner(System.in);

        int[] freq = new int[5];

        while (sc.hasNextInt()) {
            int e = sc.nextInt();
            freq[e - 1]++;
        }

        for (int e : freq) {
            System.out.print(e + " ");
        }

        sc.close();
    }
}
