import java.util.Scanner;

public class Cinema {

    static int capacity;
    static int income = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the number of rows: ");
        int rows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row: ");
        int seats = scanner.nextInt();

        int[][] bookedSeats = new int[rows][seats];
        capacity = rows * seats;
        int option;

        // repeat option selection until user quits
        do {
            System.out.println("1. Show the seats \n2. Buy a ticket \n3. Statistics \n0. Exit");
            option = scanner.nextInt();
            switch (option) {
            case 1: {
                printCinema(bookedSeats);
                break;
            }
            case 2: {
                buyTicket(bookedSeats, scanner);
                break;
            }
            case 3: {
                showStatistics(bookedSeats);
                break;
            }
            }
        } while ((option) != 0);
    }

    // print seating plan
    private static void printCinema(int[][] bookedSeats) {
        System.out.println("\nCinema:");
        System.out.print(" ");
        for (int i = 1; i <= bookedSeats[0].length; i++) {
            System.out.print(" " + i);
        }
        System.out.println();
        for (int row = 1; row <= bookedSeats.length; row++) {
            System.out.print(row);
            for (int col = 1; col <= bookedSeats[0].length; col++) {
                System.out.print(bookedSeats[row - 1][col - 1] < 1 ? " S" : " B"); // an unreserved seat is marked 'S'
                                                                                   // while 'B' is for reserved
            }
            System.out.println();
        }
        System.out.println();
    }

    // calculate income if the cinema capacity is sold out... first half price is
    // $10, while back half is $8 per seat
    private static int calculateTotalIncome(int rows, int seats) {
        int totalIncome;
        if (capacity <= 60) {
            totalIncome = capacity * 10;
        } else {
            int frontHalf = rows / 2;
            int backHalf = rows - frontHalf;
            totalIncome = (frontHalf * 10 + backHalf * 8) * seats;
        }
        return totalIncome;
    }

    // determine price of ticket based on its position
    private static int calculatePrice(int row, int seat, int rows, int seats) {
        int capacity = rows * seats;
        int price;
        if (capacity <= 60) {
            price = 10;
        } else {
            int frontHalf = rows / 2;
            price = (row <= frontHalf) ? 10 : 8;
        }
        return price;
    }

    private static void buyTicket(int[][] cinemaSeats, Scanner scanner) {
        boolean isInvalid;
        do {
            System.out.println("Enter a row number: ");
            int selectedRow = scanner.nextInt();
            System.out.println("Enter a seat number in that row: ");
            int selectedSeat = scanner.nextInt();

            if (selectedRow > cinemaSeats.length || selectedSeat > cinemaSeats[0].length || selectedRow < 0
                    || selectedSeat < 0) {
                System.out.println("Wrong input!");
                isInvalid = true;
            } else if (cinemaSeats[selectedRow - 1][selectedSeat - 1] == 1) {
                System.out.println("That ticket has already been purchased!");
                isInvalid = true;
            } else {
                cinemaSeats[selectedRow - 1][selectedSeat - 1] = 1;
                int price = calculatePrice(selectedRow, selectedSeat, cinemaSeats.length, cinemaSeats[0].length);
                income += price;

                System.out.println();
                System.out.println("Ticket price: $" + price + "\n");
                isInvalid = false;
            }
        } while (isInvalid);
    }

    private static void showStatistics(int[][] cinemaSeats) {
        int numberOfTicketsSold = getTicketsSold(cinemaSeats);
        double percentageSold = (double) numberOfTicketsSold / capacity * 100;
        System.out.println(String.format(
                "Number of purchased tickets: %d\nPercentage: %.2f%%\nCurrent totalIncome:$%d\nTotal totalIncome:$%d",
                numberOfTicketsSold, percentageSold, income,
                calculateTotalIncome(cinemaSeats.length, cinemaSeats[0].length)));
    }

    private static int getTicketsSold(int[][] bookedSeats) {
        int count = 0;
        for (int row = 1; row <= bookedSeats.length; row++) {
            for (int col = 1; col <= bookedSeats[0].length; col++) {
                if (bookedSeats[row - 1][col - 1] > 0)
                    count++;
            }
        }
        return count;
    }
}