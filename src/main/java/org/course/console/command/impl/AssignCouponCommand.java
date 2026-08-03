package org.course.console.command.impl;

import org.course.console.command.ConsoleCommand;
import org.course.console.command.ConsoleCommandType;
import org.course.console.util.ConsoleInputReader;
import org.course.entity.Client;
import org.course.service.ClientService;
import org.course.service.CouponService;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class AssignCouponCommand implements ConsoleCommand {

    private final CouponService couponService;
    private final ClientService clientService;

    public AssignCouponCommand(
            CouponService couponService,
            ClientService clientService
    ) {
        this.couponService = couponService;
        this.clientService = clientService;
    }

    @Override
    public void execute(Scanner scanner) {
        Long clientId = readClientId(scanner);
        if (!this.clientService.existById(clientId)) {
            System.out.printf("Client with id=%d does not exist. \n", clientId);
            return;
        }

        Long couponId = readCouponId(scanner);
        if (!this.couponService.existById(couponId)) {
            System.out.printf("Coupon with id=%d does not exist. \n", clientId);
            return;
        }

        this.couponService.assignCouponToClient(clientId, couponId);
        System.out.printf("Coupon with id=%d was successfully assigned to client with id=%d. \n",
                couponId, clientId);
    }

    private Long readClientId(Scanner scanner) {
        return ConsoleInputReader.readInputLineWithValidation(
                scanner,
                "Enter client id: ",
                false,
                Long::parseLong,
                "Invalid format of client id.",
                value -> value > 0,
                "Client id must be greater than zero.",
                null
        );
    }

    private Long readCouponId(Scanner scanner) {
        return ConsoleInputReader.readInputLineWithValidation(
                scanner,
                "Enter coupon id: ",
                false,
                Long::parseLong,
                "Invalid format of coupon id.",
                value -> value > 0,
                "Coupon id must be greater than zero.",
                null
        );
    }

    @Override
    public String getDescription() {
        return "– " + getType().toString();
    }

    @Override
    public ConsoleCommandType getType() {
        return ConsoleCommandType.ASSIGN_COUPON;
    }
}
