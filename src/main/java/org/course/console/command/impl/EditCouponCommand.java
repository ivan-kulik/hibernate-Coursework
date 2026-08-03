package org.course.console.command.impl;

import org.course.console.command.ConsoleCommand;
import org.course.console.command.ConsoleCommandType;
import org.course.console.util.ConsoleInputReader;
import org.course.entity.Coupon;
import org.course.service.CouponService;
import org.springframework.stereotype.Component;

import java.util.Scanner;
import java.util.function.Function;

@Component
public class EditCouponCommand implements ConsoleCommand {

    private final CouponService couponService;

    public EditCouponCommand(CouponService couponService) {
        this.couponService = couponService;
    }

    @Override
    public void execute(Scanner scanner) {
        Long couponId = readCouponId(scanner);
        Coupon coupon = this.couponService.findById(couponId);
        if (coupon == null) {
            System.out.printf("Coupon with id=%d does not exist. \n", couponId);
            return;
        }

        System.out.println("Leave the field empty (press Enter) to keep the current value.");

        String newCouponCode = readNewUniqueCouponCode(scanner, coupon.getCode());
        Float newCouponDiscount = readNewCouponDiscount(scanner, coupon.getDiscount());
        this.couponService.updateCoupon(couponId, newCouponCode, newCouponDiscount);

        System.out.printf("Coupon with id=%d was successfully updated. \n", couponId);
    }

    private Long readCouponId(Scanner scanner) {
        return ConsoleInputReader.readInputLineWithValidation(
                scanner,
                "Enter coupon id to edit: ",
                false,
                Long::parseLong,
                "Invalid format of coupon id.",
                value -> value > 0,
                "Coupon id must be greater than zero.",
                null
        );
    }

    private String readNewUniqueCouponCode(Scanner scanner, String currCouponCode) {
        String consoleMessage = String.format(
                "Enter new coupon code (current: %s): ",
                currCouponCode
        );
        String input = ConsoleInputReader.readInputLineWithValidation(
                scanner,
                consoleMessage,
                true,
                Function.identity(),
                null, null, null,
                value -> {
                    if (this.couponService.existByCode(value)) {
                        throw new IllegalArgumentException("Coupon with this code already exists.");
                    }
                }
        );
        return input.isBlank() ? currCouponCode : input;
    }

    private Float readNewCouponDiscount(Scanner scanner, Float currCouponDiscount) {
        String consoleMessage = String.format(
                "Enter new coupon discount (current: %f): ",
                currCouponDiscount
        );
        Float input =  ConsoleInputReader.readInputLineWithValidation(
                scanner,
                consoleMessage,
                true,
                Float::parseFloat,
                "Invalid format of discount.",
                value -> value > 0 && value < 1,
                "Discount must be greater than zero and less than one.",
                null
        );
        return input.isNaN() ? currCouponDiscount : input;
    }

    @Override
    public String getDescription() {
        return "– " + getType().toString();
    }

    @Override
    public ConsoleCommandType getType() {
        return ConsoleCommandType.EDIT_COUPON;
    }
}
