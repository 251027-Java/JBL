package com.example.expense.service;

import com.example.expense.Expense;
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
    private IRepository repository;

    public ExpenseService(IRepository repository) {
        this.repository = repository;
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

    private int generateUniqueID(List<Expense> expenseList) {
        Set<Integer> set = expenseList.stream().map(Expense::getId).collect(Collectors.toSet());

        for (int i = 1; i < Integer.MAX_VALUE; i++) {
            if (!set.contains(i)) {
                return i;
            }
        }

        return -1;
    }

    public Expense addExpense(double value, String merchant) {
        Expense expense =
                new Expense(generateUniqueID(repository.loadExpenses()), LocalDateTime.now(), value, merchant);
        repository.createExpense(expense);
        return expense;
    }

    public Expense deleteExpense(int id) {
        Expense expense = getExpense(id);
        repository.deleteExpense(id);
        return expense;
    }

    public void updateExpense(Expense expense) {
        repository.updateExpense(expense);
    }

    public Expense getExpense(int id) {
        return repository.readExpense(id);
    }

    public double sumExpenses() {
        return repository.loadExpenses().stream().mapToDouble(Expense::getValue).sum();
    }

    public void start() {
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
            quit|exit to exit the program
            list
            view <id>
            add <value> <merchant>
            update <id> <date|value|merchant> <new_value>
            delete <id>
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

        if (command.length == 0) {
            return true;
        }

        switch (command[0]) {
            case "list" -> {
                listExpenses();
            }
            case "view" -> {
                viewExpense(command[1]);
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

    private void listExpenses() {
        for (var e : repository.loadExpenses()) {
            System.out.println(e);
        }

        System.out.println();
    }

    private void viewExpense(String id) {
        try {
            int valId = Integer.parseInt(id);
            System.out.println(getExpense(valId));
        } catch (Exception e) {
            invalidValue("id", id);
        }
    }
}
