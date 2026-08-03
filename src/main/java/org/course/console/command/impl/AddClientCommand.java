package org.course.console.command.impl;

import org.course.console.command.ConsoleCommand;
import org.course.console.command.ConsoleCommandType;
import org.course.console.util.ConsoleInputReader;
import org.course.service.ClientService;
import org.springframework.stereotype.Component;

import java.util.Scanner;
import java.util.function.Function;

@Component
public class AddClientCommand implements ConsoleCommand {

    private final ClientService clientService;

    public AddClientCommand(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public void execute(Scanner scanner) {
        String name = readUniqueName(scanner);
        String email = readUniqueEmail(scanner);
        String address = readAddress(scanner);
        String phone = readUniquePhone(scanner);

        Long newClientId = this.clientService.createClient(name, email, address, phone);

        System.out.printf("New client with id=%d was created. \n", newClientId);
    }

    private String readUniqueName(Scanner scanner) {
        return ConsoleInputReader.readInputLineWithValidation(
                scanner,
                "Enter the name: ",
                Function.identity(),
                null, null, null,
                input -> {
                    if (this.clientService.isClientExistByName(input)) {
                        throw new IllegalArgumentException("Client with this name already exists.");
                    }
                }
        );
    }

    private String readUniqueEmail(Scanner scanner) {
        return ConsoleInputReader.readInputLineWithValidation(
                scanner,
                "Enter the email: ",
                Function.identity(),
                null,
                input -> input.endsWith("@gmail.com"),
                "Invalid format of email. Must end with '@gmail.com'.",
                input -> {
                    if (this.clientService.isClientExistByEmail(input)) {
                        throw new IllegalArgumentException("Client with this email already exists.");
                    }
                }
        );
    }

    private String readAddress(Scanner scanner) {
        return ConsoleInputReader.readInputLineWithValidation(
                scanner,
                "Enter the address: ",
                Function.identity(),
                null, null, null, null
        );
    }

    private String readUniquePhone(Scanner scanner) {
        return ConsoleInputReader.readInputLineWithValidation(
                scanner,
                "Enter the phone: ",
                Function.identity(),
                null,
                input -> input.chars().allMatch(Character::isDigit),
                "Phone number must contain only digits.",
                input -> {
                    if (this.clientService.isProfileExistByPhone(input)) {
                        throw new IllegalArgumentException("Client with this phone already exists.");
                    }
                }
        );
    }

    @Override
    public String getDescription() {
        return "– " + getType().toString();
    }

    @Override
    public ConsoleCommandType getType() {
        return ConsoleCommandType.ADD_CLIENT;
    }
}
