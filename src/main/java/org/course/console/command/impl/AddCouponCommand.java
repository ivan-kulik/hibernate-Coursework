package org.course.console.command.impl;

import org.course.console.command.ConsoleCommand;
import org.course.console.command.ConsoleCommandType;
import org.course.console.util.ConsoleInputReader;
import org.course.service.CouponService;
import org.springframework.stereotype.Component;

import java.util.Scanner;
import java.util.function.Function;

@Component
public class AddCouponCommand implements ConsoleCommand {

    private final CouponService couponService;

    public AddCouponCommand(CouponService couponService) {
        this.couponService = couponService;
    }

    @Override
    public void execute(Scanner scanner) {
        String couponCode = readUniqueCouponCode(scanner);
        Float couponDiscount = readCouponDiscount(scanner);

        Long couponId = this.couponService.createCoupon(couponCode, couponDiscount);
        System.out.printf("New coupon with id=%d was successfully created. \n", couponId);
    }

    private String readUniqueCouponCode(Scanner scanner) {
        return ConsoleInputReader.readInputLineWithValidation(
                scanner,
                "Enter code for new coupon: ",
                false,
                Function.identity(),
                null, null, null,
                value -> {
                    if (this.couponService.existByCode(value)) {
                        throw new IllegalArgumentException("Coupon with this code already exists.");
                    }
                }
        );
    }

    private Float readCouponDiscount(Scanner scanner) {
        return ConsoleInputReader.readInputLineWithValidation(
                scanner,
                "Enter discount for new coupon: ",
                false,
                Float::parseFloat,
                "Invalid format of discount.",
                value -> value > 0 && value < 1,
                "Discount must be greater than zero and less than one.",
                null
        );
    }

    @Override
    public String getDescription() {
        return "– " + getType().toString();
    }

    @Override
    public ConsoleCommandType getType() {
        return ConsoleCommandType.ADD_COUPON;
    }
}
