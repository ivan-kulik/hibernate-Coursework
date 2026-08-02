package org.course.command;

import java.util.Scanner;

public interface ConsoleCommand {

    void execute(Scanner scanner);

    ConsoleCommandType getType();
}
