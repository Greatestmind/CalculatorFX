package sample.service;


public class Calculator {

    public double calculation(double a, double b, String opetor) {

        switch (opetor) {
            case "+":
                return a + b;
            case "-":
                return a - b;
            case "*":
                return a * b;
            case "/":
                if( a == 0||b == 0) {
                    return 0;
                }
                return a / b;
        }
        throw new IllegalArgumentException("Неизвестная операция:" + opetor);
    }
}
