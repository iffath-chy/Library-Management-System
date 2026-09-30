
package com.mycompany.projectfinal;

/**
 *
 * @author iffath
 */
import java.io.Serializable;
import java.util.Date;

public class Borrowing implements Serializable 
{
    private String borrowingId;
    private String bookIsbn;
    private String memberId;
    private Date borrowDate;
    private Date dueDate;
    private Date returnDate;
    private BorrowingStatus status;
    
    public enum BorrowingStatus 
    {
        ACTIVE, RETURNED, OVERDUE
    }
    
    public Borrowing() {}
    
    public Borrowing(String borrowingId, String bookIsbn, String memberId, 
                     Date borrowDate, Date dueDate) 
    {
        this.borrowingId = borrowingId;
        this.bookIsbn = bookIsbn;
        this.memberId = memberId;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.status = BorrowingStatus.ACTIVE;
    }
    
    public String getBorrowingId() { return borrowingId; }
    public void setBorrowingId(String borrowingId) { this.borrowingId = borrowingId; }
    
    public String getBookIsbn() { return bookIsbn; }
    public void setBookIsbn(String bookIsbn) { this.bookIsbn = bookIsbn; }
    
    public String getMemberId() { return memberId; }
    public void setMemberId(String memberId) { this.memberId = memberId; }
    
    public Date getBorrowDate() { return borrowDate; }
    public void setBorrowDate(Date borrowDate) { this.borrowDate = borrowDate; }
    
    public Date getDueDate() { return dueDate; }
    public void setDueDate(Date dueDate) { this.dueDate = dueDate; }
    
    public Date getReturnDate() { return returnDate; }
    public void setReturnDate(Date returnDate) { this.returnDate = returnDate; }
    
    public BorrowingStatus getStatus() { return status; }
    public void setStatus(BorrowingStatus status) { this.status = status; }
    
    public void returnBook() {
        this.returnDate = new Date();
        this.status = BorrowingStatus.RETURNED;
    }
    
    public boolean isOverdue() {
        return new Date().after(dueDate) && status == BorrowingStatus.ACTIVE;
    }
    
    @Override
    public String toString() {
        return "Borrowing ID: " + borrowingId + " - Book: " + bookIsbn + " - Member: " + memberId;
    }
}
