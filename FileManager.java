
package com.mycompany.projectfinal;

/**
 *
 * @author iffath
 */
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    private static final String BOOKS_FILE = "books.dat";
    private static final String MEMBERS_FILE = "members.dat";
    private static final String BORROWINGS_FILE = "borrowings.dat";
    
    public static void saveBooks(List<Book> books) {
        saveToFile(books, BOOKS_FILE);
    }
    
    public static void saveMembers(List<Member> members) {
        saveToFile(members, MEMBERS_FILE);
    }
    
    public static void saveBorrowings(List<Borrowing> borrowings) {
        saveToFile(borrowings, BORROWINGS_FILE);
    }
    
    public static List<Book> loadBooks() {
        return loadFromFile(BOOKS_FILE);
    }
    
    public static List<Member> loadMembers() {
        return loadFromFile(MEMBERS_FILE);
    }
    
    public static List<Borrowing> loadBorrowings() {
        return loadFromFile(BORROWINGS_FILE);
    }
    
    private static <T> void saveToFile(List<T> data, String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(filename))) {
            oos.writeObject(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @SuppressWarnings("unchecked")
    private static <T> List<T> loadFromFile(String filename) {
        List<T> data = new ArrayList<>();
        File file = new File(filename);
        
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(
                    new FileInputStream(filename))) {
                data = (List<T>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return data;
    }
}