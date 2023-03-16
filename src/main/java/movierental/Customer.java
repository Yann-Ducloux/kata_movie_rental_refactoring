package movierental;

import java.util.ArrayList;
import java.util.List;

public class Customer {

    private final String name;
    private final List<Rental> rentals = new ArrayList<Rental>();

    public Customer(String name) {
        this.name = name;
    }

    public void addRental(Rental arg) {
        rentals.add(arg);
    }

    public String getName() {
        return name;
    }

    public String statement() {
        double totalAmount = 0;
        int frequentRenterPoints = 0;
        StringBuilder result = new StringBuilder(firstLine());

        for (Rental rental : rentals) {
            double amount = amountForRentalLine(rental);
            frequentRenterPoints = countFrequentRenterPoints(frequentRenterPoints, rental);
            result.append(showFiguresRental(rental, amount));
            totalAmount += amount;
        }

        result.append(footerLines(totalAmount, frequentRenterPoints));

        return result.toString();
    }

    private static int countFrequentRenterPoints(int frequentRenterPoints, Rental rental) {
        frequentRenterPoints++;
        if (isDayOfNewRelease(rental)) {
            frequentRenterPoints++;
        }
        return frequentRenterPoints;
    }

    private static boolean isDayOfNewRelease(Rental rental) {
        return (rental.getMovie().getPriceCode() == PriceCode.NEW_RELEASE) && rental.getDaysRented() > 1;
    }

    private static double amountForRentalLine(Rental rental) {
        double amount = 0;
        switch (rental.getMovie().getPriceCode()) {
            case REGULAR -> amount = amountRegular(rental);
            case NEW_RELEASE -> amount = amountNewRelease(rental);
            case CHILDRENS -> amount = amountChildren(rental);
        }
        return amount;
    }

    private static double amountChildren(Rental rental) {
        double amount = 1.5;
        if (rental.getDaysRented() > 3) {
            amount += (rental.getDaysRented() - 3) * 1.5;
        }
        return amount;
    }

    private static int amountNewRelease(Rental rental) {
        return rental.getDaysRented() * 3;
    }

    private static double amountRegular(Rental rental) {
        double amount = 2;
        if (rental.getDaysRented() > 2) {
            amount += (rental.getDaysRented() - 2) * 1.5;
        }
        return amount;
    }

    private String firstLine() {
        return "Rental Record for " + getName() + "\n";
    }

    private static String showFiguresRental(Rental rental, double amount) {
        return "\t" + rental.getMovie().getTitle() + "\t" + amount + "\n";
    }
    private static String footerLines(Double totalAmount, int frequentRenterPoints) {
        return "Amount owed is " + totalAmount + "\n" +
        "You earned " + frequentRenterPoints + " frequent renter points";
    }
}
