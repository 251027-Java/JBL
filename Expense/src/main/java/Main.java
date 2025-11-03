import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main {
    static void main() {
        List<Expense> list = new ArrayList<>();

        list.add(new Expense(1, new Date(), 99.32, "Walmart"));
        list.add(new Expense(2, new Date(), 35.2, "Costco"));
        list.add(new Expense(3, new Date(), 10000, "Private Jet"));

        System.out.println(list);

        try (PrintWriter out = new PrintWriter(new FileWriter("expenses.txt"))) {
            out.println("id,date,value,merchant");
            for (var e : list) {
                out.println(e.toCSV());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
