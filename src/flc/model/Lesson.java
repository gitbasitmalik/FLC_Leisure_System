/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package flc.model;

/**
 *
 * @author basitmalik
 */
import java.util.ArrayList;
import java.util.List;

public class Lesson {

    private String lessonID;
    private String day;
    private String timeSlot;
    private ExerciseType exercise;
    private int capacity = 4;
    private List<Member> participants = new ArrayList<>();
    private List<Review> reviews = new ArrayList<>();

    public Lesson(String id, String day, String slot, ExerciseType ex) {
        this.lessonID = id;
        this.day = day;
        this.timeSlot = slot;
        this.exercise = ex;
    }

    public boolean isFull() {
        return participants.size() >= capacity;
    }

    public void addParticipant(Member m) {
        if (!isFull()) {
            participants.add(m);
        }
    }

    public void removeParticipant(Member m) {
        participants.remove(m);
    }

    public double getAverageRating() {
        if (reviews.isEmpty()) {
            return 0.0;
        }
        double sum = 0;
        for (Review r : reviews) {
            sum += r.getRating();
        }
        return sum / reviews.size();
    }

    // Getters
    public String getLessonID() {
        return lessonID;
    }

    public String getDay() {
        return day;
    }

    public ExerciseType getExercise() {
        return exercise;
    }

    public List<Member> getParticipants() {
        return participants;
    }

    public void addReview(Review r) {
        reviews.add(r);
    }
}
