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

public class Main {

    private static LeisureCentre centre = new LeisureCentre();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initializeData();
        runMenu();
    }

    private static void initializeData() {
        // 1. Create Exercise Types
        ExerciseType yoga = new ExerciseType("Yoga", 15.0);
        ExerciseType zumba = new ExerciseType("Zumba", 10.0);
        ExerciseType aquacise = new ExerciseType("Aquacise", 12.0);
        ExerciseType boxfit = new ExerciseType("Box Fit", 14.0);

        // 2. Pre-register 10 Members
        for (int i = 1; i <= 10; i++) {
            centre.addMember(new Member("M" + i, "Member " + i));
        }

        // 3. Initialize 8 Weekends (48 Lessons) 
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
                case 6:
                    System.out.println("Exiting...");
                    return; 
                default:
                    System.out.println("Coming soon...");
            }
        }
    }

    private static void handleBooking() {
        System.out.println("Search by: 1. Day  2. Exercise Type");
        int subChoice = scanner.nextInt();
        scanner.nextLine();

        if (subChoice == 1) {
            System.out.print("Enter Day (Saturday/Sunday): ");
            String day = scanner.nextLine();
            centre.searchByDay(day).forEach(l -> System.out.println(l.getLessonID() + ": " + l.getExercise().getName()));
        }

        System.out.print("Enter Member ID: ");
        String mID = scanner.nextLine();
        System.out.print("Enter Lesson ID: ");
        String lID = scanner.nextLine();

        System.out.println(centre.bookLesson(mID, lID));
    }
}
