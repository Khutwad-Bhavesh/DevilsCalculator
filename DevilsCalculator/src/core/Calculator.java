package core;

public class Calculator {

    private double firstOperand = 0;
    private double secondOperand = 0;
    private String operator = "";
    private boolean freshResult = false;

    public double evaluate(double a, double b, String op) {
        switch (op) {
            case "+": return a + b;
            case "-": return a - b;
            case "*": return a * b;
            case "/":
                if (b == 0) throw new ArithmeticException("Division by zero, idiot.");
                return a / b;
            default: return b;
        }
    }

    public void setFirstOperand(double val) { this.firstOperand = val; }
    public void setOperator(String op)      { this.operator = op; }
    public double getFirstOperand()         { return firstOperand; }
    public String getOperator()             { return operator; }
    public boolean isFreshResult()          { return freshResult; }
    public void setFreshResult(boolean v)   { this.freshResult = v; }
    public void reset() {
        firstOperand = 0;
        secondOperand = 0;
        operator = "";
        freshResult = false;
    }
}
