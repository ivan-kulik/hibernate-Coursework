package org.course.console.command;

import java.util.Scanner;

public interface ConsoleCommand {

    void execute(Scanner scanner);

    String getDescription();

    ConsoleCommandType getType();
}
