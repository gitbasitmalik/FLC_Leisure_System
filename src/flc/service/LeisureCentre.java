/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package flc.service;

/**
 *
 * @author basitmalik
 */
import flc.model.*;
import java.util.*;

public class LeisureCentre {

    private List<Lesson> lessons = new ArrayList<>();
    private List<Member> members = new ArrayList<>();
    private List<Booking> bookings = new ArrayList<>();
    private int nextBookingID = 1;

    public void addLesson(Lesson l) {
        lessons.add(l);
    }

    public void addMember(Member m) {
        members.add(m);
    }

    // Requirement: Search by Day [cite: 83, 129]
    public List<Lesson> searchByDay(String day) {
        List<Lesson> result = new ArrayList<>();
        for (Lesson l : lessons) {
            if (l.getDay().equalsIgnoreCase(day)) {
                result.add(l);
            }
        }
        return result;
    }

    // Requirement: Search by Exercise
    public List<Lesson> searchByType(String type) {
        List<Lesson> result = new ArrayList<>();
        for (Lesson l : lessons) {
            if (l.getExercise().getName().equalsIgnoreCase(type)) {
                result.add(l);
            }
        }
        return result;
    }

    // Requirement: Book a Lesson
    public String bookLesson(String memberID, String lessonID) {
    Member member = findMember(memberID);
    Lesson lesson = findLesson(lessonID);

    if (member == null || lesson == null) return "Invalid ID.";
    if (lesson.isFull()) return "Lesson is full.";
    
    // ... (keep your duplicate check logic here)

    Booking newBooking = new Booking(nextBookingID++, member, lesson);
    bookings.add(newBooking);
    lesson.addParticipant(member);
    
    // Return the ID so the user sees it!
    return "Booking successful! Your Booking ID is: " + newBooking.getBookingID();
}

    private Member findMember(String id) {
        for (Member m : members) {
            if (m.getMemberID().equals(id)) {
                return m;
            }
        }
        return null;
    }

    private Lesson findLesson(String id) {
        for (Lesson l : lessons) {
            if (l.getLessonID().equals(id)) {
                return l;
            }
        }
        return null;
    }

    //  Requirement: Change or Cancel a Booking
    public String changeOrCancelBooking(int bID, String newLessonID, boolean isCancellation) {
        Booking booking = null;
        for (Booking b : bookings) {
            if (b.getBookingID() == bID) {
                booking = b;
                break;
            }
        }

        if (booking == null) {
            return "Booking ID not found.";
        }

        // Release slot from the old lesson
        booking.getLesson().removeParticipant(booking.getMember());

        if (isCancellation) {
            booking.setStatus("Cancelled");
            return "Booking cancelled successfully.";
        }

        // Change Logic
        Lesson newLesson = findLesson(newLessonID);
        if (newLesson == null || newLesson.isFull()) {
            booking.getLesson().addParticipant(booking.getMember()); // Revert
            return "Change failed: New lesson is full or invalid.";
        }

        newLesson.addParticipant(booking.getMember());
        booking.setLesson(newLesson);
        booking.setStatus("Changed");
        return "Booking changed successfully.";
    }

    // Requirement: Attend and Rate
    public String attendLesson(int bID, int rating, String reviewText) {
        for (Booking b : bookings) {
            if (b.getBookingID() == bID && b.getStatus().equals("Booked") || b.getStatus().equals("Changed")) {
                b.setStatus("Attended");
                Review r = new Review(rating, reviewText);
                b.getLesson().addReview(r);
                return "Attendance recorded.";
            }
        }
        return "Booking not eligible for attendance.";
    }

// Requirement: Monthly Lesson Report
    public void generateMonthlyReport(int monthNum) {
        System.out.println("--- Monthly Lesson Report (Month " + monthNum + ") ---");
        // Month 1 = Weekends 1-4; Month 2 = Weekends 5-8
        int startW = (monthNum == 1) ? 1 : 5;
        int endW = (monthNum == 1) ? 4 : 8;

        for (Lesson l : lessons) {
            int weekend = Integer.parseInt(l.getLessonID().substring(1, 2));
            if (weekend >= startW && weekend <= endW) {
                long attendedCount = bookings.stream()
                        .filter(b -> b.getLesson().equals(l) && b.getStatus().equals("Attended"))
                        .count();
                System.out.println(l.getLessonID() + " | Attended: " + attendedCount + " | Avg Rating: " + String.format("%.1f", l.getAverageRating()));
            }
        }
    }
    
    // Requirement: Champion Exercise Report

    public void generateChampionReport(int monthNum) {
        Map<String, Double> incomeMap = new HashMap<>();
        int startW = (monthNum == 1) ? 1 : 5;
        int endW = (monthNum == 1) ? 4 : 8;

        for (Booking b : bookings) {
            int weekend = Integer.parseInt(b.getLesson().getLessonID().substring(1, 2));
            if (b.getStatus().equals("Attended") && weekend >= startW && weekend <= endW) {
                String type = b.getLesson().getExercise().getName();
                double price = b.getLesson().getExercise().getPrice();
                incomeMap.put(type, incomeMap.getOrDefault(type, 0.0) + price);
            }
        }

        System.out.println("--- Champion Exercise Report ---");
        incomeMap.forEach((name, income) -> System.out.println(name + ": £" + income));

        String champion = incomeMap.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey).orElse("None");
        System.out.println("Champion: " + champion);
    }
}
