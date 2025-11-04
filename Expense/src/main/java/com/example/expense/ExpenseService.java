package com.example.expense;

import com.example.expense.repository.IRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ExpenseService {
    private IRepository txtRepository;
    private IRepository csvRepository;
    private IRepository jsonRepository;

    ExpenseService(IRepository txtRepository, IRepository csvRepository, IRepository jsonRepository) {
        this.txtRepository = txtRepository;
        this.csvRepository = csvRepository;
        this.jsonRepository = jsonRepository;
    }

    private int generateUniqueID(List<Expense> expenseList) {
        Set<Integer> set = expenseList.stream().map(Expense::getId).collect(Collectors.toSet());

        for (int i = 1; i < Integer.MAX_VALUE; i++) {
            if (!set.contains(i)) {
                return i;
            }
        }

        return -1;
    }

    Expense addExpense(String fileFormat, double value, String merchant) {
        IRepository repo = resolveRepository(fileFormat);
        Expense expense = new Expense(generateUniqueID(repo.loadExpenses()), LocalDateTime.now(), value, merchant);
        repo.createExpense(expense);
        return expense;
    }

    void deleteExpense(String fileFormat, int id) {
        IRepository repo = resolveRepository(fileFormat);
        repo.deleteExpense(id);
    }

    void updateExpense(String fileFormat, Expense expense) {
        IRepository repo = resolveRepository(fileFormat);
        repo.updateExpense(expense);
    }

    Expense getExpense(String fileFormat, int id) {
        IRepository repo = resolveRepository(fileFormat);
        return repo.readExpense(id);
    }

    private IRepository resolveRepository(String fileFormat) {
        if (fileFormat.equalsIgnoreCase("txt")) return txtRepository;
        if (fileFormat.equalsIgnoreCase("csv")) return csvRepository;
        if (fileFormat.equalsIgnoreCase("json")) return jsonRepository;

        return null;
    }

    void start() {
        Scanner in = new Scanner(System.in);

        boolean done = false;
        while (!done) {
            displayActions();
            String[] command = getUserCommand(in);
            done = !processCommand(command);
        }

        in.close();
    }

    private void displayActions() {
        String res = """
            <txt|csv|json> <action> to perform an action on a specific file format
            <quit|exit> to exit the program

            <txt|csv|json> list
            <txt|csv|json> view <id>
            <txt|csv|json> add <value> <merchant>
            <txt|csv|json> update <id> <date|value|merchant> <new_value>
            <txt|csv|json> delete <id>
            """;
        System.out.println(res);
    }

    private String[] getUserCommand(Scanner in) {
        return parseArgs(in.nextLine().trim());
    }

    private boolean processCommand(String[] command) {
        if (command.length == 1 && (command[0].equalsIgnoreCase("quit") || command[0].equalsIgnoreCase("exit"))) {
            return false;
        }

        if (command.length == 0 || command.length == 1) {
            return true;
        }

        switch (command[1]) {
            case "list" -> {
                listExpenses(command[0]);
            }
            case "view" -> {
                viewExpense(command[0], command[2]);
            }
            default -> {}
        }

        return true;
    }

    private void unsupportedFileFormat(String fileFormat) {
        System.out.printf("Unsupported file format: %s\n", fileFormat);
    }

    private void invalidValue(String name, String value) {
        System.out.printf("Invalid value provided for '%s': %s\n", name, value);
    }

    private void listExpenses(String fileFormat) {
        IRepository repo = resolveRepository(fileFormat);

        if (repo == null) {
            unsupportedFileFormat(fileFormat);
            return;
        }

        for (var e : repo.loadExpenses()) {
            System.out.println(e);
        }

        System.out.println();
    }

    private void viewExpense(String fileFormat, String id) {
        IRepository repo = resolveRepository(fileFormat);

        if (repo == null) {
            unsupportedFileFormat(fileFormat);
            return;
        }

        try {
            int valId = Integer.parseInt(id);
            System.out.println(getExpense(fileFormat, valId));
        } catch (Exception e) {
            invalidValue("id", id);
        }
    }

    private static String[] parseArgs(String commandLine) {
        if (commandLine == null || commandLine.trim().isEmpty()) {
            return new String[0];
        }

        List<String> args = new ArrayList<>();
        Pattern pattern = Pattern.compile("\"([^\"]*)\"|'([^']*)'|(\\S+)");
        Matcher matcher = pattern.matcher(commandLine);

        while (matcher.find()) {
            if (matcher.group(1) != null) {
                args.add(matcher.group(1));
            } else if (matcher.group(2) != null) {
                args.add(matcher.group(2));
            } else {
                args.add(matcher.group(3));
            }
        }

        return args.toArray(new String[0]);
    }
}
