import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeMap;

public class KataCalc {

    private static HashMap<Character, Integer> romanNumerals = new HashMap<>();
    private static TreeMap<Integer, String> NumeralsRoman = new TreeMap<>();

    static {
        romanNumerals.put('I', 1);
        romanNumerals.put('V', 5);
        romanNumerals.put('X', 10);
        romanNumerals.put('L', 50);
        romanNumerals.put('C', 100);
        romanNumerals.put('D', 500);
        romanNumerals.put('M', 1000);

        NumeralsRoman.put(1, "I");
        NumeralsRoman.put(4, "IV");
        NumeralsRoman.put(5, "V");
        NumeralsRoman.put(9, "IX");
        NumeralsRoman.put(10, "X");
        NumeralsRoman.put(40, "XL");
        NumeralsRoman.put(50, "L");
        NumeralsRoman.put(100, "C");
        NumeralsRoman.put(500, "D");
        NumeralsRoman.put(1000, "M");

    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Введите выражение в римской или арабской системе счисления(от 1 до 10) без пробелов + Enter ");
        String expr = scan.nextLine();
        calculate(expr);
    }

    public static int calculate(String expression) throws IllegalArgumentException {
        //Делим строку по найденному знаку
        String[] operands = expression.split("[+\\-*/]");
        //Определяем введено ли мат. выражение
        if (operands.length < 2) {
            throw new IllegalArgumentException("строка не является математической операцией");
        }
        //Определяем введено ли мат. выражение из 2 операторов
        if (operands.length != 2) {
            throw new IllegalArgumentException("Формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
        }

        String operand1 = operands[0].trim();
        String operand2 = operands[1].trim();
        char operator = expression.charAt(operand1.length());
        // Определяем находятся ли числа в одной системе счисления
        if (isRomanNumeral(operand1) && isRomanNumeral(operand2)) {
            //конвертируем  римские в арабские числа и вычислем рез-т
            int num1 = convertRomanToDecimal(operand1);
            int num2 = convertRomanToDecimal(operand2);
            int result = calculate(num1, num2, operator);
            System.out.println(convertDecimalToRoman(result));
            return result;
        } else if (isNumeric(operand1) && isNumeric(operand2)) {
            int num1 = Integer.parseInt(operand1);
            int num2 = Integer.parseInt(operand2);
            int result = calculate(num1, num2, operator);
            System.out.println(result);
            return result;
        } else {
            throw new IllegalArgumentException("Используются одновременно разные системы счисления");
        }
    }

    private static int calculate(int num1, int num2, char operator) throws IllegalArgumentException {
        switch (operator) {
            case '+':
                return num1 + num2;
            case '-':
                return num1 - num2;
            case '*':
                return num1 * num2;
            case '/':
                return num1 / num2;
            default:
                throw new IllegalArgumentException("Введен неверный оператор, разрешено [+,-,/,*]");
        }
    }

    private static boolean isRomanNumeral(String str) {
        return str.matches("[IVXLCDM]+");
    }

    private static boolean isNumeric(String str) {
        return str.matches("\\d+");
    }

    private static int convertRomanToDecimal(String romanNumeral) {
        int result = 0;

        for (int i = 0; i < romanNumeral.length(); i++) {
            char currentChar = romanNumeral.charAt(i);

            if (!romanNumerals.containsKey(currentChar)) {
                throw new IllegalArgumentException("Invalid Roman numeral: " + romanNumeral);
            }

            int currentValue = romanNumerals.get(currentChar);
            int nextValue = (i + 1 < romanNumeral.length()) ? romanNumerals.get(romanNumeral.charAt(i + 1)) : 0;

            if (currentValue < nextValue) {
                result -= currentValue;
            } else {
                result += currentValue;
            }
        }

        return result;
    }

    private static String convertDecimalToRoman(int decimalNumber) {
        StringBuilder romanNumeral = new StringBuilder();

        if (decimalNumber < 0) {
            System.out.println("В римской системе нет отрицательных чисел");
            return "";
        }

        int arabianKey;

//12
        while (decimalNumber != 0) {
            arabianKey = NumeralsRoman.floorKey(decimalNumber);
            romanNumeral.append(NumeralsRoman.get(arabianKey));
            decimalNumber -= arabianKey;
        }
        return romanNumeral.toString();
    }
}




