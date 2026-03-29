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

    // Requirement: Search by Exercise [cite: 83, 130]
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

        if (member == null || lesson == null) {
            return "Invalid ID.";
        }
        if (lesson.isFull()) {
            return "Lesson is full.";
        }

        // Check for duplicate booking [cite: 132, 192]
        for (Booking b : bookings) {
            if (b.getMember().getMemberID().equals(memberID)
                    && b.getLesson().getLessonID().equals(lessonID)
                    && !b.getStatus().equals("Cancelled")) {
                return "Duplicate booking not allowed.";
            }
        }

        lesson.addParticipant(member);
        bookings.add(new Booking(nextBookingID++, member, lesson));
        return "Booking successful!";
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

    // You will add the report methods here later...
}
