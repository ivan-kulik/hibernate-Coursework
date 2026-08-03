package org.course.console.command.impl;

import org.course.console.command.ConsoleCommand;
import org.course.console.command.ConsoleCommandType;
import org.course.console.util.ConsoleInputReader;
import org.course.entity.Client;
import org.course.service.ClientService;
import org.springframework.stereotype.Component;

import java.util.Scanner;
import java.util.function.Function;

@Component
public class EditProfileCommand implements ConsoleCommand {

    private final ClientService clientService;

    public EditProfileCommand(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public void execute(Scanner scanner) {
        String name = readClientName(scanner);
        Client client = this.clientService.findClientByName(name);
        if (client == null) {
            System.out.printf("Client with name=%s does not exist. \n", name);
            return;
        }

        System.out.println("Leave the field empty (press Enter) to keep the current value.");

        String currentAddress = client.getProfile().getAddress();
        String newAddress = readNewAddress(scanner, currentAddress);

        String currentPhone = client.getProfile().getPhone();
        String newPhone = readNewUniquePhone(scanner, currentPhone);

        this.clientService.updateProfile(client.getId(), newAddress, newPhone);
        System.out.println("Client's profile was successfully edited.");
    }

    private String readClientName(Scanner scanner) {
        return ConsoleInputReader.readInputLineWithValidation(
                scanner,
                "Enter a client name to edit profile: ",
                false,
                Function.identity(),
                null, null, null, null
        );
    }

    private String readNewAddress(Scanner scanner, String currentAddress) {
        String prompt = String.format("Enter new address (current: %s): ", currentAddress);

        String input = ConsoleInputReader.readInputLineWithValidation(
                scanner,
                prompt,
                true,
                Function.identity(),
                null, null, null, null
        );
        return input.isBlank() ? currentAddress : input;
    }

    private String readNewUniquePhone(Scanner scanner, String currentPhone) {
        String prompt = String.format("Enter new phone (current: %s): ", currentPhone);

        String input = ConsoleInputReader.readInputLineWithValidation(
                scanner,
                prompt,
                true,
                Function.identity(),
                null,
                val -> val.chars().allMatch(Character::isDigit),
                "Phone number must contain only digits.",
                val -> {
                    if (!val.isBlank() && !val.equals(currentPhone)) {
                        if (this.clientService.existsByPhone(val)) {
                            throw new IllegalArgumentException("Client with this phone already exists.");
                        }
                    }
                }
        );
        return input.isBlank() ? currentPhone : input;
    }

    @Override
    public String getDescription() {
        return "– " + getType().toString();
    }

    @Override
    public ConsoleCommandType getType() {
        return ConsoleCommandType.EDIT_PROFILE;
    }
}
