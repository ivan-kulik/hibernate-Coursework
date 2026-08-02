package org.course.console.util;

import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public final class ConsoleInputReader {

    private ConsoleInputReader () {}

    public static <T> T readInputLineWithValidation(
            Scanner scanner,
            String consoleMessage,
            Function<String, T> parser,
            String parserErrorMessage,
            Predicate<T> validator,
            String validatorErrorMessage,
            Consumer<T> businessValidator
    ) {
        while (true) {
            System.out.println(consoleMessage);
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                continue;
            }

            try {
                T value = parser.apply(input);

                if (validator != null && !validator.test(value)) {
                    System.out.println(validatorErrorMessage);
                    continue;
                }

                if (businessValidator != null) {
                    businessValidator.accept(value);
                }

                return value;
            } catch (NumberFormatException exception) {
                System.out.println(parserErrorMessage);
            } catch (IllegalArgumentException exception) {
                System.out.println(exception.getMessage());
            }
        }
    }
}
