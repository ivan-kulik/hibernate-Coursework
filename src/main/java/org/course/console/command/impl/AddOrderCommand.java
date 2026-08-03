package org.course.console.command.impl;

import org.course.console.command.ConsoleCommand;
import org.course.console.command.ConsoleCommandType;
import org.course.console.util.ConsoleInputReader;
import org.course.entity.Client;
import org.course.service.ClientService;
import org.course.service.OrderService;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Scanner;
import java.util.function.Function;

@Component
public class AddOrderCommand implements ConsoleCommand {

    private final ClientService clientService;
    private final OrderService orderService;

    public AddOrderCommand(
            OrderService orderService,
            ClientService clientService
    ) {
        this.orderService = orderService;
        this.clientService = clientService;
    }

    @Override
    public void execute(Scanner scanner) {
        String name = readName(scanner);
        Client client = this.clientService.findClientByName(name);
        if (client == null) {
            System.out.printf("Client with name=%s does not exist. \n", name);
            return;
        }

        LocalDate orderDate = askForOrderDate(scanner);
        Integer totalAmount = askForTotalAmount(scanner);
        Long orderId = this.orderService.saveOrder(client.getId(), orderDate, totalAmount);

        System.out.printf("Order with id=%d was successfully saved. \n", orderId);
    }

    private String readName(Scanner scanner) {
        return ConsoleInputReader.readInputLineWithValidation(
                scanner,
                "Enter a client name to add order: ",
                false,
                Function.identity(),
                null, null, null, null
        );
    }

    private LocalDate askForOrderDate(Scanner scanner) {
        return ConsoleInputReader.readInputLineWithValidation(
                scanner,
                "Enter a order date in format 'yyyy-MM-dd': ",
                false,
                LocalDate::parse,
                "Invalid format of order date.",
                null, null, null
        );
    }

    private Integer askForTotalAmount(Scanner scanner) {
        return ConsoleInputReader.readInputLineWithValidation(
                scanner,
                "Enter a total amount: ",
                false,
                Integer::parseInt,
                "Invalid format of total amount.",
                value -> value > 0,
                "Total amount must be greater than zero.",
                null
        );
    }

    @Override
    public String getDescription() {
        return "– " + getType().toString();
    }

    @Override
    public ConsoleCommandType getType() {
        return ConsoleCommandType.ADD_ORDER;
    }
}
