/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package flc.model;

/**
 *
 * @author basitmalik
 */
public class Booking {

    private int bookingID;
    private Member member;
    private Lesson lesson;
    private String status; // "Booked", "Attended", "Changed", "Cancelled"

    public Booking(int id, Member m, Lesson l) {
        this.bookingID = id;
        this.member = m;
        this.lesson = l;
        this.status = "Booked";
    }

    public int getBookingID() {
        return bookingID;
    }

    public Member getMember() {
        return member;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }
}