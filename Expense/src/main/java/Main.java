import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Main {

    static void main() {
        List<Expense> list = new ArrayList<>();

        list.add(new Expense(1, LocalDateTime.now(), 99.32, "Walmart"));
        list.add(new Expense(2, LocalDateTime.now(), 35.2, "Costco"));
        list.add(new Expense(3, LocalDateTime.now(), 10000, "Private Jet"));

        System.out.println(list);

        try (PrintWriter out = new PrintWriter(new FileWriter("expenses.csv"))) {
            out.println("id,date,value,merchant");
            for (var e : list) {
                out.println(e.toCSV());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, _, _) ->
                        LocalDateTime.parse(json.getAsJsonPrimitive().getAsString()))
                .registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>)
                        (src, _, _) -> new JsonPrimitive(src.toString()))
                .setPrettyPrinting()
                .create();

        try (FileWriter out = new FileWriter("expenses.json")) {
            gson.toJson(list, out);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String json = gson.toJson(list);
        List<Expense> list2 = gson.fromJson(json, new TypeToken<ArrayList<Expense>>() {});

        System.out.println(list2);
    }
}
