package com.example.expense.repository;

import com.example.expense.Expense;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class JSONRepository implements IRepository {

    private String filename = "expenses.json";
    private Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, _, _) ->
                    LocalDateTime.parse(json.getAsJsonPrimitive().getAsString()))
            .registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>)
                    (src, _, _) -> new JsonPrimitive(src.toString()))
            .setPrettyPrinting()
            .create();

    @Override
    public List<Expense> loadExpenses() {
        try (FileReader in = new FileReader(filename)) {
            return gson.fromJson(in, new TypeToken<>() {});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveExpenses(List<Expense> expenses) {
        try (FileWriter out = new FileWriter(filename)) {
            gson.toJson(expenses, out);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
