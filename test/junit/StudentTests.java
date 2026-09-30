// Add focused @org.junit.jupiter.api.Test methods here.
// Create fresh objects for each test and assert results AND unchanged state on rejection.
// The supplied wrapper is not a substitute for your own test design.

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

import org.junit.jupiter.api.Test;

public class StudentTests {
    
    @Test
    public void validHours(){
        for (int i = 1; i <= 24; i++){
            try {
                DomainRules.requireDuration(i);
            } 
            catch (Exception e) {
                fail("failed for hours: " + i);
            }
        }
    }

    @Test
    public void invalidHours(){
        assertThrows(IllegalArgumentException.class, () -> DomainRules.requireDuration(0));
        assertThrows(IllegalArgumentException.class, () -> DomainRules.requireDuration(25));
    }

    @Test 
    public void nullOrInvalidIdentifiers(){
        assertThrows(IllegalArgumentException.class, () -> DomainRules.requireIdentifier(null));
        assertThrows(IllegalArgumentException.class, () -> DomainRules.requireIdentifier("invalid"));
        assertThrows(IllegalArgumentException.class, () -> DomainRules.requireIdentifier("0INVALID"));
        assertThrows(IllegalArgumentException.class, () -> DomainRules.requireIdentifier("INVALID0123456789"));
    }

    @Test 
    public void validIdentifier(){
        try {
            DomainRules.requireIdentifier("VALID67890123456");
        } 
        catch (Exception e) {
            fail();
        }
    }
    
    @Test 
    public void eligibleB12(){
        StudentApplication a = new StudentApplication();
        ProposalView p = a.propose("B12");
        
        assertNotNull(p);
        assertEquals("B1", p.bookingId());
        assertEquals("B12", p.targetId());
        assertEquals("PENDING", p.status());
        assertEquals(0, p.bookingVersion());
    }
    
    @Test 
    public void occupiedD09(){
        StudentApplication a = new StudentApplication();
        assertThrows(IllegalArgumentException.class, () -> a.propose("D09"));
    }
    
    @Test 
    public void unknownTarget(){
        StudentApplication a = new StudentApplication();
        assertThrows(IllegalArgumentException.class, () -> a.propose("unknownId"));
    }
    
    @Test 
    public void unchangedBookingStateProposing(){
        StudentApplication a = new StudentApplication();

        Map<String, Object> before = a.bookingSnapshot();
        a.propose("B12");
        Map<String, Object> after = a.bookingSnapshot();

        assertEquals(before, after);

    }

    @Test
    public void unchangedBookingStateRejecting(){
        StudentApplication a = new StudentApplication();
        Map<String, Object> before = a.bookingSnapshot();

        assertThrows(IllegalArgumentException.class, () -> a.propose("D09"));
        assertThrows(IllegalArgumentException.class, () -> a.propose("unknownId"));

        Map<String, Object> after = a.bookingSnapshot();

        assertEquals(before, after);
    }

}
