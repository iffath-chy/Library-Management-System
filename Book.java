
package com.mycompany.projectfinal;

/**
 *
 * @author iffath
 */
import java.io.Serializable;
import java.util.Date;

public class Book implements Serializable 
{
    private String isbn;
    private String title;
    private String author;
    private BookGenre genre;
    private Date publicationDate;
    private int totalCopies;
    private int availableCopies;
    
    public enum BookGenre 
    {
        FICTION, NON_FICTION, SCIENCE, HISTORY, BIOGRAPHY, FANTASY, MYSTERY, ROMANCE
    }
    
    public Book() {}
    
    public Book(String isbn, String title, String author, BookGenre genre, 
                Date publicationDate, int totalCopies) 
    {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.publicationDate = publicationDate;
        this.totalCopies = totalCopies;
        this.availableCopies = totalCopies;
    }
    
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    
    public BookGenre getGenre() { return genre; }
    public void setGenre(BookGenre genre) { this.genre = genre; }
    
    public Date getPublicationDate() { return publicationDate; }
    public void setPublicationDate(Date publicationDate) { this.publicationDate = publicationDate; }
    
    public int getTotalCopies() { return totalCopies; }
    public void setTotalCopies(int totalCopies) { 
        this.totalCopies = totalCopies; 
        this.availableCopies = totalCopies;
    }
    
    public int getAvailableCopies() { return availableCopies; }
    public void setAvailableCopies(int availableCopies) { this.availableCopies = availableCopies; }
    
    public void borrowCopy() {
        if(availableCopies > 0) {
            availableCopies--;
        }
    }
    
    public void returnCopy() {
        if(availableCopies < totalCopies) {
            availableCopies++;
        }
    }
    
    @Override
    public String toString() {
        return title + " by " + author + " (ISBN: " + isbn + ")";
    }
}