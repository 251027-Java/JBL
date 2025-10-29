public class HelloJava {
    // fields,properties
    // methods,behaviors

    // applications starts at entrypoint - default main method

    public static void main(String[] args) {
        System.out.println("Hello World");
        IO.println("this exists");

        // several types in java
        int a = 34;
        double b = 345.673;

        // math operators
        a = 34 - 35;

        // logical operators
        boolean bal = a > b;

        // control flow
        // if, while, etc

        String str = "sadiosdoifsdoigsjiodg";

        for (int i = 0; i < str.length(); i++) {
            System.out.println(str.charAt(i));
        }
    }
}
