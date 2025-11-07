/*
You are building an auto-suggestion system for a digital library. The system maintains a list of book ISBNs sorted in ascending order and each ISBN is a unique integer.

When a user enters an ISBN to search for a book:
    If the ISBN exists in the system, return its index.
    If the ISBN does not exist, return the index where the new ISBN should be inserted to maintain the order.

Constraints:
    The isbn_list will always be sorted and contain distinct values.
    You can expect up to 10^4 ISBN entries — efficiency is important!
    ISBNs can be both negative and positive integers (for legacy or special code formats), ranging from -10^4 to 10^4.
    The target_isbn will also fall in the same numeric range.

Input format:
    The first line contains a single integer n — the number of ISBNs in the list.
    The second line contains n space-separated integers — the sorted list of distinct ISBNs.
    The third line contains a single integer target_isbn — the ISBN the user is searching for.

Output format:
    Print a single integer — the index at which the target_isbn is found or should be inserted to maintain the sorted order.

Sample Input 1:
4
101 203 305 409
305

Sample Output 1:
2

Sample Input 2:
4
101 203 305 409
204

Sample Output 2:
2
*/

import java.util.*;

public class isbns {
    static void main() {
        new isbns().run();
    }

    void run() {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int[] isbns = new int[n];

        for (int i = 0; i < isbns.length; i++) {
            isbns[i] = sc.nextInt();
        }

        int target = sc.nextInt();

        int res = solve2(isbns, target);
        System.out.println(res);

        sc.close();
    }

    int solve1(int[] isbns, int target) {
        for (int i = 0; i < isbns.length; i++) {
            if (isbns[0] >= target) {
                return i;
            }
        }
        return isbns.length;
    }

    int solve2(int[] isbns, int target) {
        int lo = 0;
        int hi = isbns.length - 1;

        while (lo < hi) {
            int mid = (lo + hi) / 2;
            int val = isbns[mid];

            if (val < target) {
                lo = mid + 1;
            } else if (val > target) {
                hi = mid - 1;
            } else {
                return mid;
            }
        }

        return lo;
    }
}