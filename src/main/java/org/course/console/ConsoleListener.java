package org.course.console;

import org.course.command.CommandRegistry;
import org.course.command.ConsoleCommand;
import org.course.command.ConsoleCommandType;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleListener {

    private final CommandRegistry commandRegistry;
    private final ApplicationLifecycle applicationLifecycle;

    public ConsoleListener(
            CommandRegistry commandRegistry,
            ApplicationLifecycle applicationLifecycle
    ) {
        this.commandRegistry = commandRegistry;
        this.applicationLifecycle = applicationLifecycle;
    }

    public void run() {
        try (Scanner scanner = new Scanner(System.in)) {
            while (this.applicationLifecycle.isRunning() &&
                    !Thread.currentThread().isInterrupted()
            ) {
                printAllCommands();

                String input = scanner.nextLine().trim().toUpperCase();
                if (input.isEmpty()) {
                    continue;
                }

                ConsoleCommandType type;
                try {
                    type =  ConsoleCommandType.valueOf(input);
                } catch (IllegalArgumentException exception) {
                    System.out.println("Unknown command. Please try again.");
                    continue;
                }

                ConsoleCommand consoleCommand = this.commandRegistry.getCommand(type);
                consoleCommand.execute(scanner);
            }
        }
    }

    public void printAllCommands() {
        System.out.println("Select an command:");
        this.commandRegistry.getAllCommands().stream()
                .map(ConsoleCommand::getDescription)
                .forEach(System.out::println);
        System.out.println("Enter the command number:");
        System.out.print("> ");
    }
}
