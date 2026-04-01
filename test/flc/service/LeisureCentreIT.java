/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package flc.service;

import flc.model.*;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author basitmalik
 */
public class LeisureCentreIT {
  
    @Test
  public void testSearchByDay() {
        LeisureCentre instance = new LeisureCentre();
        ExerciseType yoga = new ExerciseType("Yoga", 15.0);
        instance.addLesson(new Lesson("L1", "Saturday", "Morning", yoga));
        
        List<Lesson> result = instance.searchByDay("Saturday");
        assertEquals(1, result.size());
        assertEquals("Saturday", result.get(0).getDay());
    }

    /**
     * Test 2: Booking Success
     * Ensures a member can successfully book an available lesson
     */
    @Test
    public void testBookLesson() {
        LeisureCentre instance = new LeisureCentre();
        instance.addMember(new Member("M1", "Basit"));
        instance.addLesson(new Lesson("L1", "Saturday", "Morning", new ExerciseType("Yoga", 15.0)));
        
        String result = instance.bookLesson("M1", "L1");
        assertTrue(result.contains("successful"));
    }

    /**
     * Test 3: Duplicate Booking Prevention
     * Ensures a member cannot book the same lesson twice.
     */
    @Test
    public void testDuplicateBooking() {
        LeisureCentre instance = new LeisureCentre();
        instance.addMember(new Member("M1", "Basit"));
        instance.addLesson(new Lesson("L1", "Saturday", "Morning", new ExerciseType("Yoga", 15.0)));
        
        instance.bookLesson("M1", "L1"); // First booking
        String result = instance.bookLesson("M1", "L1"); // Duplicate attempt
        
        assertEquals("Duplicate booking not allowed.", result);
    }

    /**
     * Test 4: Capacity Constraint
     * Ensures a 5th member cannot book a lesson with a limit of 4
     */
    @Test
    public void testCapacityConstraint() {
        LeisureCentre instance = new LeisureCentre();
        Lesson lesson = new Lesson("L1", "Saturday", "Morning", new ExerciseType("Yoga", 15.0));
        instance.addLesson(lesson);
        
        // Add 4 members to fill it
        for (int i = 1; i <= 4; i++) {
            Member m = new Member("M" + i, "Name");
            instance.addMember(m);
            instance.bookLesson("M" + i, "L1");
        }
        
        // Attempt 5th booking
        Member extra = new Member("M5", "Extra");
        instance.addMember(extra);
        String result = instance.bookLesson("M5", "L1");
        
        assertEquals("Lesson is full.", result);
    }

    /**
     * Test 5: Attendance Recording
     * Ensures status changes to 'Attended' after recording attendance
     */
    @Test
    public void testAttendLesson() {
        LeisureCentre instance = new LeisureCentre();
        instance.addMember(new Member("M1", "Basit"));
        instance.addLesson(new Lesson("W1-SAT-MOR", "Saturday", "Morning", new ExerciseType("Yoga", 15.0)));
        
        instance.bookLesson("M1", "W1-SAT-MOR"); // This will be Booking ID 1
        String result = instance.attendLesson(1, 5, "Excellent!");
        
        assertEquals("Attendance recorded.", result);
    }
    
}
