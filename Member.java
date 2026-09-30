
package com.mycompany.projectfinal;

/**
 *
 * @author iffath
 */
import java.io.Serializable;
import java.util.Date;

public class Member implements Serializable 
{
    private String memberId;
    private String name;
    private String email;
    private String phone;
    private Date registrationDate;
    private MemberType memberType;
    private int borrowedBooksCount;
    
    public enum MemberType 
    {
        STUDENT, FACULTY, REGULAR, PREMIUM
    }
    
    public Member() {}
    
    public Member(String memberId, String name, String email, String phone, 
                  MemberType memberType, Date registrationDate) 
    {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.memberType = memberType;
        this.registrationDate = registrationDate;
        this.borrowedBooksCount = 0;
    }
    
    public String getMemberId() { return memberId; }
    public void setMemberId(String memberId) { this.memberId = memberId; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public MemberType getMemberType() { return memberType; }
    public void setMemberType(MemberType memberType) { this.memberType = memberType; }
    
    public Date getRegistrationDate() { return registrationDate; }
    public void setRegistrationDate(Date registrationDate) { this.registrationDate = registrationDate; }
    
    public int getBorrowedBooksCount() { return borrowedBooksCount; }
    public void setBorrowedBooksCount(int borrowedBooksCount) { 
        this.borrowedBooksCount = borrowedBooksCount; 
    }
    
    public void incrementBorrowedCount() { borrowedBooksCount++; }
    public void decrementBorrowedCount() { 
        if(borrowedBooksCount > 0) borrowedBooksCount--; 
    }
    
    @Override
    public String toString() {
        return name + " (" + memberId + ")";
    }
}