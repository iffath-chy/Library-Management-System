package com.mycompany.projectfinal;

import org.junit.jupiter.api.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("FileManager Full Test Suite")
class FileManagerTest 
{

    
    private static final String BOOKS_FILE = "books.dat";
    private static final String MEMBERS_FILE = "members.dat";
    private static final String BORROWINGS_FILE = "borrowings.dat";

    @BeforeEach
    void cleanBefore() 
    {
        
        new File(BOOKS_FILE).delete();
        new File(MEMBERS_FILE).delete();
        new File(BORROWINGS_FILE).delete();
    }

    @AfterEach
    void cleanAfter() 
    {
        
        new File(BOOKS_FILE).delete();
        new File(MEMBERS_FILE).delete();
        new File(BORROWINGS_FILE).delete();
    }

    private Book sampleBook(String isbn, String title, String author,
                            Book.BookGenre genre, int copies) {
        return new Book(isbn, title, author, genre, new Date(), copies);
    }

    private Member sampleMember(String id, String name) {
        return new Member(id, name, name.toLowerCase() + "@test.com",
                "123456789", Member.MemberType.STUDENT, new Date());
    }

    private Borrowing sampleBorrowing(String id, String isbn, String memberId) {
        return new Borrowing(id, isbn, memberId, new Date(), new Date());
    }


    @Test
    @DisplayName("saveBooks + loadBooks: single book round-trips")
    void testSaveAndLoadSingleBook() {
        List<Book> books= new ArrayList<>();
        books.add(sampleBook("978-1", "Clean Code", "Robert Martin",
                             Book.BookGenre.SCIENCE, 5));

        FileManager.saveBooks(books);
        List<Book> loaded= FileManager.loadBooks();

        assertNotNull(loaded);
        assertEquals(1, loaded.size());
        assertEquals("978-1", loaded.get(0).getIsbn());
        assertEquals("Clean Code", loaded.get(0).getTitle());
        assertEquals("Robert Martin", loaded.get(0).getAuthor());
        assertEquals(Book.BookGenre.SCIENCE, loaded.get(0).getGenre());
        assertEquals(5, loaded.get(0).getTotalCopies());
        assertEquals(5, loaded.get(0).getAvailableCopies());
    }

    @Test
    @DisplayName("saveBooks + loadBooks: multiple books preserve order")
    void testSaveAndLoadMultipleBooks() {
        List<Book> books= new ArrayList<>();
        books.add(sampleBook("1", "Book A", "Author A", Book.BookGenre.FICTION, 2));
        books.add(sampleBook("2", "Book B", "Author B", Book.BookGenre.HISTORY, 3));
        books.add(sampleBook("3", "Book C", "Author C", Book.BookGenre.ROMANCE, 4));

        FileManager.saveBooks(books);
        List<Book> loaded= FileManager.loadBooks();

        assertEquals(3, loaded.size());
        assertEquals("Book A", loaded.get(0).getTitle());
        assertEquals("Book B", loaded.get(1).getTitle());
        assertEquals("Book C", loaded.get(2).getTitle());
    }

    @Test
    @DisplayName("saveBooks + loadBooks: empty list round-trips")
    void testSaveAndLoadEmptyBookList() {
        FileManager.saveBooks(new ArrayList<>());
        List<Book> loaded= FileManager.loadBooks();

        assertNotNull(loaded);
        assertTrue(loaded.isEmpty());
        assertEquals(0, loaded.size());
    }

    @Test
    @DisplayName("loadBooks: returns empty list when file does not exist")
    void testLoadBooksNoFile() {
        
        List<Book> loaded= FileManager.loadBooks();
        assertNotNull(loaded);
        assertTrue(loaded.isEmpty());
    }

    @Test
    @DisplayName("saveBooks: overwrites previous save")
    void testSaveBooksOverwrites() {
        List<Book> first= new ArrayList<>();
        first.add(sampleBook("1", "Old", "X", Book.BookGenre.FICTION, 1));
        FileManager.saveBooks(first);

        List<Book> second= new ArrayList<>();
        second.add(sampleBook("2", "New", "Y", Book.BookGenre.SCIENCE, 2));
        FileManager.saveBooks(second);

        List<Book> loaded= FileManager.loadBooks();
        assertEquals(1, loaded.size());
        assertEquals("New", loaded.get(0).getTitle());
    }

    @Test
    @DisplayName("saveBooks: creates books.dat file")
    void testSaveBooksCreatesFile() {
        FileManager.saveBooks(new ArrayList<>());
        File f= new File(BOOKS_FILE);
        assertTrue(f.exists(), "books.dat should exist after save");
        assertTrue(f.length()>0, "books.dat should not be empty");
    }

    @Test
    @DisplayName("saveBooks: availableCopies preserved")
    void testBorrowStatePreserved() {
        Book b= sampleBook("1", "T", "A", Book.BookGenre.FICTION, 5);
        b.borrowCopy();
        b.borrowCopy();

        List<Book> books= new ArrayList<>();
        books.add(b);
        FileManager.saveBooks(books);

        List<Book> loaded= FileManager.loadBooks();
        assertEquals(5, loaded.get(0).getTotalCopies());
        assertEquals(3, loaded.get(0).getAvailableCopies());
    }

   

    @Test
    @DisplayName("saveMembers + loadMembers: single member round-trips")
    void testSaveAndLoadSingleMember() {
        List<Member> members= new ArrayList<>();
        members.add(sampleMember("MEM1001", "Alice"));

        FileManager.saveMembers(members);
        List<Member> loaded= FileManager.loadMembers();

        assertNotNull(loaded);
        assertEquals(1, loaded.size());
        assertEquals("MEM1001", loaded.get(0).getMemberId());
        assertEquals("Alice", loaded.get(0).getName());
        assertEquals("alice@test.com", loaded.get(0).getEmail());
        assertEquals("123456789", loaded.get(0).getPhone());
        assertEquals(Member.MemberType.STUDENT, loaded.get(0).getMemberType());
        assertEquals(0, loaded.get(0).getBorrowedBooksCount());
    }

    @Test
    @DisplayName("saveMembers+loadMembers: multiple members")
    void testSaveAndLoadMultipleMembers() {
        List<Member> members= new ArrayList<>();
        members.add(sampleMember("M1", "Alice"));
        members.add(sampleMember("M2", "Bob"));
        members.add(sampleMember("M3", "Charlie"));

        FileManager.saveMembers(members);
        List<Member> loaded= FileManager.loadMembers();

        assertEquals(3, loaded.size());
        assertEquals("Alice", loaded.get(0).getName());
        assertEquals("Bob", loaded.get(1).getName());
        assertEquals("Charlie", loaded.get(2).getName());
    }

    @Test
    @DisplayName("saveMembers+loadMembers: empty list")
    void testSaveAndLoadEmptyMembers() {
        FileManager.saveMembers(new ArrayList<>());
        List<Member> loaded= FileManager.loadMembers();
        assertNotNull(loaded);
        assertTrue(loaded.isEmpty());
    }

    @Test
    @DisplayName("loadMembers: empty list when no file")
    void testLoadMembersNoFile() {
        List<Member> loaded= FileManager.loadMembers();
        assertNotNull(loaded);
        assertTrue(loaded.isEmpty());
    }

    @Test
    @DisplayName("saveMembers: borrow count preserved")
    void testMemberBorrowCountPreserved() {
        Member m= sampleMember("M1", "Alice");
        m.incrementBorrowedCount();
        m.incrementBorrowedCount();
        m.incrementBorrowedCount();

        List<Member> members= new ArrayList<>();
        members.add(m);
        FileManager.saveMembers(members);

        List<Member> loaded= FileManager.loadMembers();
        assertEquals(3, loaded.get(0).getBorrowedBooksCount());
    }

    @Test
    @DisplayName("saveMembers: creates members.dat file")
    void testSaveMembersCreatesFile() {
        FileManager.saveMembers(new ArrayList<>());
        File f= new File(MEMBERS_FILE);
        assertTrue(f.exists(), "members.dat should exist after save");
        assertTrue(f.length() > 0);
    }

   
    @Test
    @DisplayName("saveBorrowings + loadBorrowings: single round-trip")
    void testSaveAndLoadSingleBorrowing() {
        Borrowing b= sampleBorrowing("B1", "978-1", "MEM1");
        List<Borrowing> borrowings = new ArrayList<>();
        borrowings.add(b);

        FileManager.saveBorrowings(borrowings);
        List<Borrowing> loaded= FileManager.loadBorrowings();

        assertNotNull(loaded);
        assertEquals(1, loaded.size());
        assertEquals("B1", loaded.get(0).getBorrowingId());
        assertEquals("978-1", loaded.get(0).getBookIsbn());
        assertEquals("MEM1", loaded.get(0).getMemberId());
        assertEquals(Borrowing.BorrowingStatus.ACTIVE, loaded.get(0).getStatus());
        assertNull(loaded.get(0).getReturnDate());
    }

    @Test
    @DisplayName("saveBorrowings + loadBorrowings: multiple round-trip")
    void testSaveAndLoadMultipleBorrowings() {
        List<Borrowing> borrowings= new ArrayList<>();
        borrowings.add(sampleBorrowing("B1", "ISBN1", "M1"));
        borrowings.add(sampleBorrowing("B2", "ISBN2", "M2"));

        FileManager.saveBorrowings(borrowings);
        List<Borrowing> loaded= FileManager.loadBorrowings();

        assertEquals(2, loaded.size());
        assertEquals("B1", loaded.get(0).getBorrowingId());
        assertEquals("B2", loaded.get(1).getBorrowingId());
    }

    @Test
    @DisplayName("saveBorrowings + loadBorrowings: empty list")
    void testSaveAndLoadEmptyBorrowings() {
        FileManager.saveBorrowings(new ArrayList<>());
        List<Borrowing> loaded= FileManager.loadBorrowings();
        assertNotNull(loaded);
        assertTrue(loaded.isEmpty());
    }

    @Test
    @DisplayName("loadBorrowings: empty list when no file")
    void testLoadBorrowingsNoFile() {
        List<Borrowing> loaded= FileManager.loadBorrowings();
        assertNotNull(loaded);
        assertTrue(loaded.isEmpty());
    }

    @Test
    @DisplayName("saveBorrowings: RETURNED status preserved")
    void testReturnedStatusPreserved() {
        Borrowing b= sampleBorrowing("B1", "ISBN1", "M1");
        b.returnBook();

        List<Borrowing> list= new ArrayList<>();
        list.add(b);
        FileManager.saveBorrowings(list);

        List<Borrowing> loaded= FileManager.loadBorrowings();
        assertEquals(Borrowing.BorrowingStatus.RETURNED, loaded.get(0).getStatus());
        assertNotNull(loaded.get(0).getReturnDate());
    }

    @Test
    @DisplayName("saveBorrowings: creates borrowings.dat file")
    void testSaveBorrowingsCreatesFile() {
        FileManager.saveBorrowings(new ArrayList<>());
        File f= new File(BORROWINGS_FILE);
        assertTrue(f.exists(), "borrowings.dat should exist after save");
        assertTrue(f.length() > 0);
    }


    @Test
    @DisplayName("Saving books does not create members file")
    void testBooksAndMembersIsolated() {
        List<Book> books= new ArrayList<>();
        books.add(sampleBook("1", "T", "A", Book.BookGenre.FICTION, 1));
        FileManager.saveBooks(books);

        
        assertFalse(new File(MEMBERS_FILE).exists(),
                "members.dat should not exist when only books were saved");
    }

    @Test
    @DisplayName("All three data files coexist independently")
    void testThreeFilesCoexist() {
        List<Book> books= new ArrayList<>();
        books.add(sampleBook("1", "T", "A", Book.BookGenre.FICTION, 1));
        FileManager.saveBooks(books);

        List<Member> members= new ArrayList<>();
        members.add(sampleMember("M1", "Alice"));
        FileManager.saveMembers(members);

        List<Borrowing> borrowings= new ArrayList<>();
        borrowings.add(sampleBorrowing("B1", "1", "M1"));
        FileManager.saveBorrowings(borrowings);

        assertEquals(1, FileManager.loadBooks().size());
        assertEquals(1, FileManager.loadMembers().size());
        assertEquals(1, FileManager.loadBorrowings().size());

        assertTrue(new File(BOOKS_FILE).exists());
        assertTrue(new File(MEMBERS_FILE).exists());
        assertTrue(new File(BORROWINGS_FILE).exists());
    }

   

    @Test
    @DisplayName("Save book with special characters")
    void testSaveBookWithSpecialCharacters() {
        List<Book> books= new ArrayList<>();
        books.add(sampleBook("ISBN-Ω-ß", "Título éçà", "作者", Book.BookGenre.FICTION, 1));

        FileManager.saveBooks(books);
        List<Book> loaded= FileManager.loadBooks();

        assertEquals(1, loaded.size());
        assertEquals("Título éçà", loaded.get(0).getTitle());
        assertEquals("作者", loaded.get(0).getAuthor());
    }

    @Test
    @DisplayName("Save book with null publicationDate does not crash")
    void testSaveBookWithNullDate() {
        List<Book> books= new ArrayList<>();
        books.add(new Book("1", "T", "A", Book.BookGenre.FICTION, null, 1));

        assertDoesNotThrow(() -> FileManager.saveBooks(books));
        List<Book> loaded= FileManager.loadBooks();
        assertEquals(1, loaded.size());
        assertNull(loaded.get(0).getPublicationDate());
    }

    @Test
    @DisplayName("Save many books round-trips exactly")
    void testSaveManyBooks() {
        List<Book> books= new ArrayList<>();
        for (int i=0; i<50; i++) 
        {
            books.add(sampleBook("ISBN" + i, "Title" + i, "Author" + i,
                                 Book.BookGenre.FICTION, i + 1));
        }
        FileManager.saveBooks(books);
        List<Book> loaded= FileManager.loadBooks();

        assertEquals(50, loaded.size());
        assertEquals("ISBN0", loaded.get(0).getIsbn());
        assertEquals("ISBN49", loaded.get(49).getIsbn());
        assertEquals(50, loaded.get(49).getTotalCopies());
    }

    

    @Test
    @DisplayName("Book is Serializable")
    void testBookIsSerializable() {
        assertTrue(new Book() instanceof java.io.Serializable);
    }

    @Test
    @DisplayName("Member is Serializable")
    void testMemberIsSerializable() {
        assertTrue(new Member() instanceof java.io.Serializable);
    }

    @Test
    @DisplayName("Borrowing is Serializable")
    void testBorrowingIsSerializable() {
        assertTrue(new Borrowing() instanceof java.io.Serializable);
    }
}