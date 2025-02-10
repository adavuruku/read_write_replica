package com.example.read_write_db.service;

/**
 * Created by Sherif.Abdulraheem 08/02/2025 - 00:32
 **/
public class util {
    public static int reverse(int num) {
        int reversed = 0;
        while (num != 0) {
            int digit = num % 10; // Get the last digit
            reversed = reversed * 10 + digit; // Append it to reversed number
            num /= 10; // Remove the last digit
        }
        return reversed;
    }

    public static int reverse3(int num) {
        String s = Integer.toString(num);
        StringBuilder reversed = new StringBuilder(s).reverse();
        return Integer.parseInt(reversed.toString());
    }
    public static int reverse4(int num) {

        return - num;
    }

    public static String reverse2(int num) {
        StringBuilder reversed = new StringBuilder(String.valueOf(num)).reverse();
        return reversed.toString();
    }

    public static void main(String[] args) {
        int num = 10;
        System.out.println("Reversed Number: " + reverse(num));
        System.out.println("Reversed Number: " + reverse2(num));
        System.out.println("Reversed Number: " + reverse3(num));
        System.out.println("Opposite Number: " + reverse4(0));
        System.out.println(System.currentTimeMillis());// Output: 54321
    }
}
