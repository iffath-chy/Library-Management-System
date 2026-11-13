package com.mycompany.projectfinal;

import org.junit.jupiter.api.*;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Borrowing Model Full Test Suite")
class BorrowingTest 
{

    private Borrowing borrowing;
    private Date borrowDate;
    private Date futureDueDate;
    private Date pastDueDate;

    @BeforeEach
    void setUp() 
    {
        borrowDate= new Date();

        
        Calendar futureCal= Calendar.getInstance();
        futureCal.add(Calendar.DAY_OF_MONTH, 14);
        futureDueDate= futureCal.getTime();

        
        Calendar pastCal= Calendar.getInstance();
        pastCal.add(Calendar.DAY_OF_MONTH, -1);
        pastDueDate= pastCal.getTime();

        borrowing = new Borrowing("BOR1001", "978-1234567890", "MEM1001",
                                  borrowDate, futureDueDate);
    }

   

    @Test
    @DisplayName("Constructor: sets borrowingId correctly")
    void testConstructorBorrowingId() {
        assertEquals("BOR1001", borrowing.getBorrowingId());
    }

    @Test
    @DisplayName("Constructor: sets bookIsbn correctly")
    void testConstructorBookIsbn() {
        assertEquals("978-1234567890", borrowing.getBookIsbn());
    }

    @Test
    @DisplayName("Constructor: sets memberId correctly")
    void testConstructorMemberId() {
        assertEquals("MEM1001", borrowing.getMemberId());
    }

    @Test
    @DisplayName("Constructor: sets borrowDate correctly")
    void testConstructorBorrowDate() {
        assertEquals(borrowDate, borrowing.getBorrowDate());
    }

    @Test
    @DisplayName("Constructor: sets dueDate correctly")
    void testConstructorDueDate() {
        assertEquals(futureDueDate, borrowing.getDueDate());
    }

    @Test
    @DisplayName("Constructor: returnDate is null")
    void testConstructorReturnDateNull() {
        assertNull(borrowing.getReturnDate());
    }

    @Test
    @DisplayName("Constructor: status defaults to ACTIVE")
    void testConstructorStatusIsActive() {
        assertEquals(Borrowing.BorrowingStatus.ACTIVE, borrowing.getStatus());
    }

    @Test
    @DisplayName("Constructor: status is not RETURNED or OVERDUE")
    void testConstructorStatusNotOtherValues() {
        assertNotEquals(Borrowing.BorrowingStatus.RETURNED, borrowing.getStatus());
        assertNotEquals(Borrowing.BorrowingStatus.OVERDUE, borrowing.getStatus());
    }

    

    @Test
    @DisplayName("No-arg constructor: all fields null/default")
    void testDefaultConstructor() {
        Borrowing empty= new Borrowing();
        assertNull(empty.getBorrowingId());
        assertNull(empty.getBookIsbn());
        assertNull(empty.getMemberId());
        assertNull(empty.getBorrowDate());
        assertNull(empty.getDueDate());
        assertNull(empty.getReturnDate());
        assertNull(empty.getStatus());
    }

   

    @Test
    @DisplayName("setBorrowingId updates value")
    void testSetBorrowingId() {
        borrowing.setBorrowingId("BOR9999");
        assertEquals("BOR9999", borrowing.getBorrowingId());
    }

    @Test
    @DisplayName("setBookIsbn updates value")
    void testSetBookIsbn() {
        borrowing.setBookIsbn("111-222");
        assertEquals("111-222", borrowing.getBookIsbn());
    }

    @Test
    @DisplayName("setMemberId updates value")
    void testSetMemberId() {
        borrowing.setMemberId("MEM8888");
        assertEquals("MEM8888", borrowing.getMemberId());
    }

    @Test
    @DisplayName("setBorrowDate updates value")
    void testSetBorrowDate() {
        Date newDate= new Date(0);
        borrowing.setBorrowDate(newDate);
        assertEquals(newDate, borrowing.getBorrowDate());
    }

    @Test
    @DisplayName("setDueDate updates value")
    void testSetDueDate() {
        Date newDate= new Date(0);
        borrowing.setDueDate(newDate);
        assertEquals(newDate, borrowing.getDueDate());
    }

    @Test
    @DisplayName("setReturnDate updates value")
    void testSetReturnDate() {
        Date returnDate= new Date();
        borrowing.setReturnDate(returnDate);
        assertEquals(returnDate, borrowing.getReturnDate());
    }

    @Test
    @DisplayName("setStatus updates value")
    void testSetStatus() {
        borrowing.setStatus(Borrowing.BorrowingStatus.OVERDUE);
        assertEquals(Borrowing.BorrowingStatus.OVERDUE, borrowing.getStatus());
    }

   

    @Test
    @DisplayName("returnBook: sets returnDate to a non-null value")
    void testReturnBookSetsReturnDate() {
        borrowing.returnBook();
        assertNotNull(borrowing.getReturnDate());
    }

    @Test
    @DisplayName("returnBook: sets status to RETURNED")
    void testReturnBookSetsStatusReturned() {
        borrowing.returnBook();
        assertEquals(Borrowing.BorrowingStatus.RETURNED, borrowing.getStatus());
    }

    @Test
    @DisplayName("returnBook: status is no longer ACTIVE")
    void testReturnBookNoLongerActive() {
        borrowing.returnBook();
        assertNotEquals(Borrowing.BorrowingStatus.ACTIVE, borrowing.getStatus());
    }

    @Test
    @DisplayName("returnBook: returnDate is not before borrowDate")
    void testReturnBookReturnDateAfterBorrowDate() {
        borrowing.returnBook();
        assertFalse(borrowing.getReturnDate().before(borrowing.getBorrowDate()),
                "returnDate should not be before borrowDate");
    }

    @Test
    @DisplayName("returnBook: returnDate is not in the far past")
    void testReturnBookReturnDateReasonable() {
        Date before= new Date(System.currentTimeMillis() - 5000);
        borrowing.returnBook();
        Date after= new Date(System.currentTimeMillis() + 5000);

        assertFalse(borrowing.getReturnDate().before(before));
        assertFalse(borrowing.getReturnDate().after(after));
    }

    @Test
    @DisplayName("returnBook: calling twice still leaves status RETURNED")
    void testReturnBookTwice() {
        borrowing.returnBook();
        Date firstReturn = borrowing.getReturnDate();

        
        try { Thread.sleep(5); } catch (InterruptedException ignored) {}
        borrowing.returnBook();
        Date secondReturn = borrowing.getReturnDate();

        assertEquals(Borrowing.BorrowingStatus.RETURNED, borrowing.getStatus());
        assertNotNull(secondReturn);
        
        assertFalse(secondReturn.before(firstReturn));
    }

  

    @Test
    @DisplayName("isOverdue: false when dueDate is in future and status ACTIVE")
    void testIsOverdueFalseWhenDueInFuture() {
        assertFalse(borrowing.isOverdue());
    }

    @Test
    @DisplayName("isOverdue: true when dueDate has passed and status ACTIVE")
    void testIsOverdueTrueWhenDueInPast() {
        borrowing.setDueDate(pastDueDate);
        borrowing.setStatus(Borrowing.BorrowingStatus.ACTIVE);
        assertTrue(borrowing.isOverdue());
    }

    @Test
    @DisplayName("isOverdue: false when RETURNED (even if dueDate passed)")
    void testIsOverdueFalseWhenReturned() {
        borrowing.setDueDate(pastDueDate);
        borrowing.returnBook();
        assertEquals(Borrowing.BorrowingStatus.RETURNED, borrowing.getStatus());
        assertFalse(borrowing.isOverdue());
    }

    @Test
    @DisplayName("isOverdue: false when status OVERDUE but not overdue by date")
    void testIsOverdueFalseWhenStatusOverdueButDateFuture() {
        borrowing.setStatus(Borrowing.BorrowingStatus.OVERDUE);
        
        assertFalse(borrowing.isOverdue());
    }

    @Test
    @DisplayName("isOverdue: true when status OVERDUE and dueDate past")
    void testIsOverdueTrueWhenStatusOverdueAndDatePast() {
        borrowing.setStatus(Borrowing.BorrowingStatus.OVERDUE);
        borrowing.setDueDate(pastDueDate);
       
        assertFalse(borrowing.isOverdue());
    }

    @Test
    @DisplayName("isOverdue: false on exact same instant as dueDate (boundary)")
    void testIsOverdueAtExactDueDate() {
        borrowing.setDueDate(new Date());
        
        assertFalse(borrowing.isOverdue());
    }

    @Test
    @DisplayName("isOverdue: true one millisecond after dueDate (boundary)")
    void testIsOverdueOneMillisecondAfter() throws InterruptedException {
        Date due= new Date(System.currentTimeMillis() + 50);
        borrowing.setDueDate(due);
        Thread.sleep(100);
        assertTrue(borrowing.isOverdue());
    }

    

    @Test
    @DisplayName("toString: contains borrowingId")
    void testToStringContainsBorrowingId() {
        assertTrue(borrowing.toString().contains("BOR1001"));
    }

    @Test
    @DisplayName("toString: contains bookIsbn")
    void testToStringContainsBookIsbn() {
        assertTrue(borrowing.toString().contains("978-1234567890"));
    }

    @Test
    @DisplayName("toString: contains memberId")
    void testToStringContainsMemberId() {
        assertTrue(borrowing.toString().contains("MEM1001"));
    }

    @Test
    @DisplayName("toString: is not null and not empty")
    void testToStringNotNull() {
        assertNotNull(borrowing.toString());
        assertFalse(borrowing.toString().isEmpty());
    }

   

    @Test
    @DisplayName("BorrowingStatus has exactly 3 values")
    void testBorrowingStatusValues() {
        assertEquals(3, Borrowing.BorrowingStatus.values().length);
    }

    @Test
    @DisplayName("BorrowingStatus contains ACTIVE, RETURNED, OVERDUE")
    void testBorrowingStatusContainsAll() {
        Borrowing.BorrowingStatus[] values= Borrowing.BorrowingStatus.values();
        assertTrue(java.util.Arrays.asList(values).contains(Borrowing.BorrowingStatus.ACTIVE));
        assertTrue(java.util.Arrays.asList(values).contains(Borrowing.BorrowingStatus.RETURNED));
        assertTrue(java.util.Arrays.asList(values).contains(Borrowing.BorrowingStatus.OVERDUE));
    }

    @Test
    @DisplayName("BorrowingStatus.valueOf works")
    void testBorrowingStatusValueOf() {
        assertEquals(Borrowing.BorrowingStatus.ACTIVE,
                Borrowing.BorrowingStatus.valueOf("ACTIVE"));
        assertEquals(Borrowing.BorrowingStatus.RETURNED,
                Borrowing.BorrowingStatus.valueOf("RETURNED"));
        assertEquals(Borrowing.BorrowingStatus.OVERDUE,
                Borrowing.BorrowingStatus.valueOf("OVERDUE"));
    }

    @Test
    @DisplayName("BorrowingStatus.valueOf throws on unknown value")
    void testBorrowingStatusValueOfInvalid() {
        assertThrows(IllegalArgumentException.class,
                () -> Borrowing.BorrowingStatus.valueOf("NOT_A_STATUS"));
    }

    @Test
    @DisplayName("Borrowing is Serializable")
    void testIsSerializable() {
        assertTrue(borrowing instanceof java.io.Serializable);
    }
}