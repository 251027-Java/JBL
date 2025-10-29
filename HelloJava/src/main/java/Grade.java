import java.util.Scanner;

public class Grade {
    static void main() {
        Scanner sc = new Scanner(System.in);
        double grade = -1;

        while (grade < 0 || grade > 100) {
            try {
                System.out.print("Enter a score between 0 and 100: ");
                grade = sc.nextDouble();
            } catch (Exception e) {
                System.out.println("Not a number");
                sc.nextLine();
            }
        }


        sc.close();

        char res = calcLetterGrade(grade);
        IO.println(res);
    }

    static char calcLetterGrade(double grade) {
        if (grade >= 90) return 'A';
        if (grade >= 80) return 'B';
        if (grade >= 70) return 'C';
        if (grade >= 60) return 'D';
        return 'F';
    }
}
