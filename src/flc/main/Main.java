/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package flc.main;

/**
 *
 * @author basitmalik
 */
import flc.model.*;
import flc.service.LeisureCentre;
import java.util.Scanner;
import java.util.List;

public class Main {

    private static LeisureCentre centre = new LeisureCentre();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initializeData();
        runMenu();
    }

    private static void initializeData() {
        ExerciseType yoga = new ExerciseType("Yoga", 15.0);
        ExerciseType zumba = new ExerciseType("Zumba", 10.0);
        ExerciseType aquacise = new ExerciseType("Aquacise", 12.0);
        ExerciseType boxfit = new ExerciseType("Box Fit", 14.0);

        for (int i = 1; i <= 10; i++) {
            centre.addMember(new Member("M" + i, "Member " + i));
        }

        String[] days = {"Saturday", "Sunday"};
        String[] slots = {"Morning", "Afternoon", "Evening"};
        ExerciseType[] types = {yoga, zumba, aquacise, boxfit};

        int typeIndex = 0;
        for (int w = 1; w <= 8; w++) {
            for (String day : days) {
                for (String slot : slots) {
                    String id = "W" + w + "-" + day.substring(0, 3).toUpperCase() + "-" + slot.substring(0, 3).toUpperCase();
                    centre.addLesson(new Lesson(id, day, slot, types[typeIndex % 4]));
                    typeIndex++;
                }
            }
        }
    }

    private static void runMenu() {
        while (true) {
            System.out.println("\n--- Furzefield Leisure Centre (FLC) ---");
            System.out.println("1. Book a group exercise lesson");
            System.out.println("2. Change/Cancel a booking");
            System.out.println("3. Attend a lesson (Review/Rate)");
            System.out.println("4. Monthly lesson report");
            System.out.println("5. Monthly champion exercise type report");
            System.out.println("6. Exit");
            System.out.print("Select an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    handleBooking();
                    break;
                case 2:
                    handleChangeOrCancel();
                    break;
                case 3:
                    handleAttendance();
                    break;
                case 4:
                    handleMonthlyReport();
                    break;
                case 5:
                    handleChampionReport();
                    break;
                case 6:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void handleBooking() {
        System.out.println("\n--- Book a Lesson ---");
        System.out.println("Search by: 1. Day  2. Exercise Type");
        int subChoice = scanner.nextInt();
        scanner.nextLine();

        List<Lesson> lessons;
        if (subChoice == 1) {
            System.out.print("Enter Day (Saturday/Sunday): ");
            lessons = centre.searchByDay(scanner.nextLine());
        } else {
            System.out.print("Enter Exercise Name: ");
            lessons = centre.searchByType(scanner.nextLine());
        }

        lessons.forEach(l -> System.out.println(l.getLessonID() + ": " + l.getExercise().getName() + " (" + l.getDay() + " " + l.getTimeSlot() + ")"));

        System.out.print("Enter Member ID (e.g. M1): ");
        String mID = scanner.nextLine();
        System.out.print("Enter Lesson ID (e.g. W1-SAT-MOR): ");
        String lID = scanner.nextLine();

        System.out.println(centre.bookLesson(mID, lID));
    }

    private static void handleChangeOrCancel() {
        System.out.print("Enter Booking ID: ");
        int bID = scanner.nextInt();
        scanner.nextLine();
        System.out.println("1. Change Lesson  2. Cancel Booking");
        int action = scanner.nextInt();
        scanner.nextLine();

        if (action == 2) {
            System.out.println(centre.changeOrCancelBooking(bID, "", true));
        } else {
            System.out.print("Enter New Lesson ID: ");
            String newLID = scanner.nextLine();
            System.out.println(centre.changeOrCancelBooking(bID, newLID, false));
        }
    }

    private static void handleAttendance() {
        System.out.print("Enter Booking ID: ");
        int bID = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Rating (1-5): ");
        int rating = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Review: ");
        String review = scanner.nextLine();

        System.out.println(centre.attendLesson(bID, rating, review));
    }

    private static void handleMonthlyReport() {
        System.out.print("Enter Month Number (1 or 2): ");
        int month = scanner.nextInt();
        centre.generateMonthlyReport(month);
    }

    private static void handleChampionReport() {
        System.out.print("Enter Month Number (1 or 2): ");
        int month = scanner.nextInt();
        centre.generateChampionReport(month);
    }
}
