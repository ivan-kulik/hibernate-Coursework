package org.course.console.command.impl;

import org.course.console.command.ConsoleCommand;
import org.course.console.command.ConsoleCommandType;
import org.course.console.util.ConsoleInputReader;
import org.course.entity.Order;
import org.course.entity.OrderStatus;
import org.course.service.OrderService;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Scanner;

@Component
public class FindOrdersCommand implements ConsoleCommand {

    private final OrderService orderService;

    public FindOrdersCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public void execute(Scanner scanner) {
        OrderStatus orderStatus = readOrderStatus(scanner);
        Collection<Order> orders = this.orderService.findOrdersByStatus(orderStatus);
        printOrdersToConsole(orders);
    }

    private OrderStatus readOrderStatus(Scanner scanner) {
        return ConsoleInputReader.readInputLineWithValidation(
                scanner,
                "Enter a status to find orders: ",
                false,
                input -> {
                    try {
                        return OrderStatus.valueOf(input.toUpperCase());
                    } catch (IllegalArgumentException exception) {
                        throw new IllegalArgumentException("Invalid type of order status.");
                    }
                },
                "Invalid type of order status.",
                null, null, null
        );
    }

    private void printOrdersToConsole(Collection<Order> orders) {
        orders.stream()
                .map(this::formatOrderForConsoleOutput)
                .forEach(System.out::println);
    }

    private String formatOrderForConsoleOutput(Order order) {
        return String.format(
                "Order #%d, total amount: %d. Client: %s (email: %s).",
                order.getId(),
                order.getTotalAmount(),
                order.getClient().getName(),
                order.getClient().getEmail()
        );
    }

    @Override
    public String getDescription() {
        return "– " + getType().toString();
    }

    @Override
    public ConsoleCommandType getType() {
        return ConsoleCommandType.FIND_ORDERS;
    }
}
