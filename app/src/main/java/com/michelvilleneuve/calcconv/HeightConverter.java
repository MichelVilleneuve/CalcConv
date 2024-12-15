package com.michelvilleneuve.calcconv;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HeightConverter {

        public static void main(String[] args) {
            String input = "2'5 1/2\"";
            double inches = convertToInches(input);
            System.out.println("Height in inches: " + inches);
        }

        protected static double convertToInches(String heightString) {
            // Define a regular expression to match feet, inches, and fractions
            String regex = "(\\d+)'(\\d+)\\s?(\\d+)?/?(\\d+)?\"";

            // Create a Pattern object
            Pattern pattern = Pattern.compile(regex);

            // Create a Matcher object
            Matcher matcher = pattern.matcher(heightString);

            // Variables to store feet, inches, and fractions
            int feet = 0;
            int inches = 0;
            int numerator = 0;
            int denominator = 1;

            // Check if the pattern matches
            if (matcher.matches()) {
                // Extract feet, inches, and fractions
                feet = Integer.parseInt(Objects.requireNonNull(matcher.group(1)));
                inches = Integer.parseInt(Objects.requireNonNull(matcher.group(2)));

                // Check if fractions are present
                if (matcher.group(3) != null && matcher.group(4) != null) {
                    numerator = Integer.parseInt(Objects.requireNonNull(matcher.group(3)));
                    denominator = Integer.parseInt(Objects.requireNonNull(matcher.group(4)));
                }

                // Calculate total inches
                inches += (double) numerator / denominator;
            }

            // Convert feet to inches and add to the total
            return feet * 12 + inches;
        }
    }