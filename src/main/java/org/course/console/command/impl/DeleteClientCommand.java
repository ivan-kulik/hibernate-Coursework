package org.course.console.command.impl;

import org.course.console.command.ConsoleCommand;
import org.course.console.command.ConsoleCommandType;
import org.course.console.util.ConsoleInputReader;
import org.course.service.ClientService;
import org.springframework.stereotype.Component;

import java.util.Scanner;
import java.util.function.Function;

@Component
public class DeleteClientCommand implements ConsoleCommand {

    private final ClientService clientService;

    public DeleteClientCommand(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public void execute(Scanner scanner) {
        String name = readName(scanner);
        if (!this.clientService.deleteClientByName(name)) {
            System.out.printf("Client with name=%s does not exist. \n", name);
            return;
        }
        System.out.printf("Client with name=%s was successfully deleted. \n", name);
    }

    private String readName(Scanner scanner) {
        return ConsoleInputReader.readInputLineWithValidation(
                scanner,
                "Enter a client name to delete: ",
                Function.identity(),
                null, null, null, null
        );
    }

    @Override
    public String getDescription() {
        return "– " + getType().toString();
    }

    @Override
    public ConsoleCommandType getType() {
        return ConsoleCommandType.DELETE_CLIENT;
    }
}
