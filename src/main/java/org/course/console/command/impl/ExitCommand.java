package org.course.console.command.impl;

import org.course.console.ApplicationLifecycle;
import org.course.console.command.ConsoleCommand;
import org.course.console.command.ConsoleCommandType;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ExitCommand implements ConsoleCommand {

    private final ApplicationLifecycle applicationLifecycle;

    public ExitCommand(ApplicationLifecycle applicationLifecycle) {
        this.applicationLifecycle = applicationLifecycle;
    }

    @Override
    public void execute(Scanner scanner) {
        this.applicationLifecycle.shutdown();
    }

    @Override
    public String getDescription() {
        return "– " + getType().toString();
    }

    @Override
    public ConsoleCommandType getType() {
        return ConsoleCommandType.EXIT;
    }
}
