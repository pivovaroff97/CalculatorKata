package ru.pivovarov;

import java.util.Scanner;

public class Main {

    private final static String[] ROMAN_NUMERALS = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
    private final static int[] DECIMAL_VALUES = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println(calc(scanner.nextLine()));
    }

    public static String calc(String input) throws Exception {
        String[] expression = input.split(" ");
        if (expression.length != 3) {
            throw new Exception("Must have 2 operands and 1 sign");
        }
        int a, b;
        boolean isRoman = false;
        try {
            if (expression[0].matches("^M{0,4}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$")
                    && expression[2].matches("^M{0,4}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$")) {
                a = fromRoman(expression[0]);
                b = fromRoman(expression[2]);
                isRoman = true;
            } else  {
                a = Integer.parseInt(expression[0]);
                b = Integer.parseInt(expression[2]);
            }
            if (!isCorrectNumeral(a) || !isCorrectNumeral(b)) {
                throw new Exception("Numerals must be in interval from 1 to 10");
            }
        } catch (NumberFormatException e) {
            throw new Exception("Incorrect numerals");
        }
        String sign = expression[1];
        int result;
        switch (sign) {
            case "*" -> result = a * b;
            case "/" -> result = a / b;
            case "+" -> result = a + b;
            case "-" -> result = a - b;
            default -> throw new Exception("No such arithmetic operation");
        }
        return isRoman ? toRoman(result) : String.valueOf(result);
    }

    private static String toRoman(int number) throws Exception {
        if (number < 1) {
            throw new Exception("Roman numerals must be more than 0");
        }
        StringBuilder sb = new StringBuilder();
        int remain = number;
        for (int i = 0; i < ROMAN_NUMERALS.length; i++) {
            int decimalValue = DECIMAL_VALUES[i];
            String romanNumeral = ROMAN_NUMERALS[i];
            while (remain >= decimalValue) {
                sb.append(romanNumeral);
                remain -= decimalValue;
            }
        }
        return sb.toString();
    }

    private static int fromRoman(String romanNumeral) {
        int result = 0;
        String input = romanNumeral;
        for (int i = 0; i < ROMAN_NUMERALS.length; i++) {
            String romanNumeralToCheck = ROMAN_NUMERALS[i];
            int decimalValue = DECIMAL_VALUES[i];
            while (input.startsWith(romanNumeralToCheck)) {
                result += decimalValue;
                input = input.substring(romanNumeralToCheck.length());
            }
        }
        return result;
    }

    private static boolean isCorrectNumeral(int a) {
        return a >= 1 && a <= 10;
    }
}