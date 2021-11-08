package com.company;

import java.util.Objects;
import java.util.Stack;

/**
 * This class is designed to calculate simple mathematical expressions.
 * Among the operations that this class supports are:
 * '+' - addition;
 * '-' - subtraction;
 * '*' - multiplication;
 * '/' - division;
 * '(' - opening parenthesis;
 * ')' - closing parenthesis;
 * ' ' - spaces are simply ignored;
 *
 * @author Dasha Shevchenko
 */
public class Calculator {
    /** Stack for storing numbers from an expression. */
    private Stack<Double> Number;

    /** A stack for storing mathematical operations from an expression. */
    private Stack<Character> Operations;

    /** Input string. */
    public String exp;

    /** The current element of the string. */
    private int index;

    /** Flag for unary minus. */
    private boolean flagOfUnMinus;

    /** Flag to check whether the current element is a number or not. */
    private boolean isNumber;

    /** The result of the current mathematical expression.*/
    public double result;

    /** Constructor of the class. The input is a mathematical expression that needs to be calculated. */
    public Calculator(String exp) {
        this.exp = exp;
        Number = new Stack<>();
        Operations = new Stack<>();
        flagOfUnMinus = true;
        isNumber = false;
        index = 0;
    }

    /**
     * The main method designed for analyzing a string (mathematical expression).
     * It takes each character of a string and compares it either with numbers or
     * with certain operations.
     *
     * @throws Exception Triggers a warning if needed.
     * "Incorrect location of operations!" - If there are several operations in a row (for example, "++", "+-", "+/", "(/" and so on)
     * "The opening parenthesis is missing!" - If there is no opening parenthesis in the expression for some closing parenthesis.
     * "Invalid character(s)" - If there are signs in the expression, they are not defined in the method.
     */
    public void analyzer() throws Exception {
        while (index != exp.length()) {
            char curItem = exp.charAt(index);

            if (curItem >= '0' && curItem <= '9' || curItem == '-' && flagOfUnMinus == true) {
                int value = 1;
                if (curItem == '-') {
                    index++;
                    value = -1;
                }
                Number.push(numberToken() * value);
                flagOfUnMinus = false;
                isNumber = true;
            }

            else if (curItem == '+' || curItem == '-' && flagOfUnMinus == false) {
                if (isNumber == false) {
                    throw new Exception("Incorrect location of operations!");
                }
                sumOrMinus();
                isNumber = false;
            }

            else if (curItem == '*' || curItem == '/') {
                if (isNumber == false) {
                    throw new Exception("Incorrect location of operations!");
                }
                multOrDiv();
                isNumber = false;
            }

            else if (curItem == '(') {
                if (isNumber == true && index != 0) {
                    throw new Exception("Incorrect location of operations!");
                }
                Operations.push(curItem);
                flagOfUnMinus = true;
                index++;
                isNumber = true;
            }

            else if (curItem == ')') {
                if (!isNumber) {
                    throw new Exception("Incorrect location of operations!");
                }
                if (!Operations.contains('(')) {
                    throw new Exception("The opening parenthesis is missing!");
                }
                while(Operations.peek() != '(') {
                    mathOp();
                }
                Operations.pop();
                if (!Operations.isEmpty()) {
                    if (Operations.peek() == '-') {
                        Operations.pop();
                        Operations.push('+');
                        Number.push(Number.pop()*(-1));
                    }
                }
                index++;
                flagOfUnMinus = true;
                isNumber = true;
            }

            else if (curItem == ' ') {
                index++;
            }

            else {
                throw new Exception("Invalid character(s)");
            }

        }
        while(!Operations.isEmpty()) {
            mathOp();
        }
        result = Number.pop();
    }

    /**
     * A method that is called when an addition or subtraction sign is found in a string.
     * Calls the method of counting the expression on the left if the current operation is
     * lower in priority and adds + or - sign to the stack with operations.
     *
     * @throws Exception
     */
    private void sumOrMinus() throws Exception {
        boolean isRight = true;
        while (!Operations.isEmpty() && isRight == true) {
            if (Operations.peek() != '(' && Operations.peek() != ')') {
                mathOp();
            }
            else {
                isRight = false;
            }
        }
        Operations.push(exp.charAt(index));
        index++;
    }

    /**
     * A method that is called when an multiplication or division sign is found in a string.
     * Calls the method of counting the expression on the left if the current operation is
     * lower in priority and adds * or / sign to the stack with operations.
     * @throws Exception
     */
    private void multOrDiv() throws Exception {
        boolean isRight = true;
        while (!Operations.isEmpty() && isRight == true) {
            if (Operations.peek() == '*' || Operations.peek() == '/') {
                mathOp();
            }
            else {
                isRight = false;
            }
        }
        Operations.push(exp.charAt(index));
        index++;
    }

    /**
     * Called to extract a number from a mathematical expression.
     * @return The isolated number
     * @throws Exception
     */
    private double numberToken() throws Exception {
        String number = "";
        while (index != exp.length() && exp.charAt(index) >= '0' && exp.charAt(index) <= '9') {
            number += exp.charAt(index++);
        }
        return Integer.parseInt(number);
    }

    /**
     * A method that performs basic operations (addition, subtraction, addition and multiplication).
     * @throws Exception Triggers a warning if needed.
     * "Division by zero" - If the divisor is 0;
     * "There are not enough brackets!" - If there are parenthesis operations left in the stack,
     * which means that a string was submitted at the input, in which there are not enough
     * parentheses.
     */
    private void mathOp() throws Exception {
        char op = Operations.pop();
        if (op == '+') {
            Number.push(Number.pop()+Number.pop());
        }
        if (op == '-') {
            Number.push(-Number.pop()+Number.pop());
        }
        if (op == '*') {
            Number.push(Number.pop()*Number.pop());
        }
        if (op == '/') {
            if (Number.peek() == 0) {
                throw new Exception("Division by zero");
            }
            Number.push(Math.pow(Number.pop(), -1) * Number.pop());
        }
        if (op == '(' || op == ')') {
            throw new Exception("There are not enough brackets!");
        }
    }

    /**
     * Overriding the method of returning the result as a string.
     * @return The result of the calculator is in the form of a string.
     */
    @Override
    public String toString() {
        return String.valueOf(result);
    }

    /**
     * Overriding the method for comparing objects.
     * @param o The object that the current object is being compared to.
     * @return TRUE if the objects are equal and FALSE if the objects are not equal.
     */
    @Override
    public boolean equals(Object o) {
        Calculator that = (Calculator) o;
        return Double.compare(that.result, result) == 0 && exp.equals(that.exp);
    }

    /**
     * An overridden method for calculating the hashcode of each object.
     * @return The hashcode value for the current object.
     */
    @Override
    public int hashCode() {
        return (int) (exp.length()+Math.round(result));
    }
}
