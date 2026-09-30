
package com.mycompany.projectfinal;

/**
 *
 * @author iffath
 */


import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.table.JTableHeader;

public class LibraryManagementGUI extends JFrame {
    // Purple theme colors
    private final Color PRIMARY_PURPLE = new Color(147, 112, 219);
    private final Color DARK_PURPLE = new Color(75, 0, 130);
    private final Color LIGHT_PURPLE = new Color(216, 191, 216);
    private final Color ACCENT_PURPLE = new Color(138, 43, 226);
    private final Color WHITE_BG = new Color(255, 255, 255);
    private final Color BLACK_TEXT = new Color(0, 0, 0);
    
    // Data lists
    private ArrayList<Book> books;
    private ArrayList<Member> members;
    private ArrayList<Borrowing> borrowings;
    
    // Tables
    private JTable booksTable;
    private JTable membersTable;
    private JTable borrowingsTable;
    
    // Tabbed pane
    private JTabbedPane tabbedPane;
    
    public LibraryManagementGUI() {
        setTitle("Library Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);
        
        // Load data
        loadAllData();
        
        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(PRIMARY_PURPLE);
        
        // Tabbed pane
        tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(DARK_PURPLE);
        tabbedPane.setForeground(Color.WHITE);
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 14));
        
        // Create tabs
        tabbedPane.addTab("Books", createBooksPanel());
        tabbedPane.addTab("Members", createMembersPanel());
        tabbedPane.addTab("Borrowings", createBorrowingsPanel());
        
        // Menu bar
        JMenuBar menuBar = createMenuBar();
        setJMenuBar(menuBar);
        
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        add(mainPanel);
    }
    
    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(DARK_PURPLE);
        
        JMenu fileMenu = new JMenu("File");
        fileMenu.setForeground(Color.WHITE);
        fileMenu.setFont(new Font("Arial", Font.BOLD, 12));
        
        JMenuItem saveItem = new JMenuItem("Save Data");
        JMenuItem loadItem = new JMenuItem("Load Data");
        JMenuItem exitItem = new JMenuItem("Exit");
        
        // Style menu items
        styleMenuItem(saveItem);
        styleMenuItem(loadItem);
        styleMenuItem(exitItem);
        
        saveItem.addActionListener(e -> saveAllData());
        loadItem.addActionListener(e -> {
            loadAllData();
            refreshAllTables();
            JOptionPane.showMessageDialog(this, "Data loaded successfully!");
        });
        exitItem.addActionListener(e -> System.exit(0));
        
        fileMenu.add(saveItem);
        fileMenu.add(loadItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);
        
        menuBar.add(fileMenu);
        return menuBar;
    }
    
    private void styleMenuItem(JMenuItem menuItem) {
        menuItem.setBackground(LIGHT_PURPLE);
        menuItem.setForeground(BLACK_TEXT);
        menuItem.setFont(new Font("Arial", Font.PLAIN, 12));
    }
    
    private void loadAllData() {
        books = (ArrayList<Book>) FileManager.loadBooks();
        members = (ArrayList<Member>) FileManager.loadMembers();
        borrowings = (ArrayList<Borrowing>) FileManager.loadBorrowings();
        
        if (books == null) books = new ArrayList<>();
        if (members == null) members = new ArrayList<>();
        if (borrowings == null) borrowings = new ArrayList<>();
        
        System.out.println("Loaded " + books.size() + " books, " + 
                          members.size() + " members, " + 
                          borrowings.size() + " borrowings");
    }
    
    private void saveAllData() {
        FileManager.saveBooks(books);
        FileManager.saveMembers(members);
        FileManager.saveBorrowings(borrowings);
        JOptionPane.showMessageDialog(this, "Data saved successfully!");
    }
    
    private JPanel createBooksPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(LIGHT_PURPLE);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        buttonPanel.setBackground(LIGHT_PURPLE);
        
        JButton addButton = createStyledButton("Add Book", ACCENT_PURPLE);
        JButton editButton = createStyledButton("Edit Book", ACCENT_PURPLE);
        JButton deleteButton = createStyledButton("Delete Book", ACCENT_PURPLE);
        JButton searchButton = createStyledButton("Search", ACCENT_PURPLE);
        JButton refreshButton = createStyledButton("Refresh", ACCENT_PURPLE);
        
        addButton.addActionListener(e -> showBookForm(null));
        editButton.addActionListener(e -> editSelectedBook());
        deleteButton.addActionListener(e -> deleteSelectedBook());
        searchButton.addActionListener(e -> searchBooks());
        refreshButton.addActionListener(e -> refreshBooksTable());
        
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(refreshButton);
        
        // Table
        booksTable = new JTable();
        configureTable(booksTable);
        refreshBooksTable();
        
        JScrollPane scrollPane = new JScrollPane(booksTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(DARK_PURPLE, 1));
        scrollPane.getViewport().setBackground(WHITE_BG);
        
        panel.add(buttonPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createMembersPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(LIGHT_PURPLE);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        buttonPanel.setBackground(LIGHT_PURPLE);
        
        JButton addButton = createStyledButton("Add Member", ACCENT_PURPLE);
        JButton editButton = createStyledButton("Edit Member", ACCENT_PURPLE);
        JButton deleteButton = createStyledButton("Delete Member", ACCENT_PURPLE);
        JButton refreshButton = createStyledButton("Refresh", ACCENT_PURPLE);
        
        addButton.addActionListener(e -> showMemberForm(null));
        editButton.addActionListener(e -> editSelectedMember());
        deleteButton.addActionListener(e -> deleteSelectedMember());
        refreshButton.addActionListener(e -> refreshMembersTable());
        
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);
        
        // Table
        membersTable = new JTable();
        configureTable(membersTable);
        refreshMembersTable();
        
        JScrollPane scrollPane = new JScrollPane(membersTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(DARK_PURPLE, 1));
        scrollPane.getViewport().setBackground(WHITE_BG);
        
        panel.add(buttonPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createBorrowingsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(LIGHT_PURPLE);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        buttonPanel.setBackground(LIGHT_PURPLE);
        
        JButton addButton = createStyledButton("Add Borrowing", ACCENT_PURPLE);
        JButton returnButton = createStyledButton("Return Book", ACCENT_PURPLE);
        JButton deleteButton = createStyledButton("Delete", ACCENT_PURPLE);
        JButton refreshButton = createStyledButton("Refresh", ACCENT_PURPLE);
        
        addButton.addActionListener(e -> showBorrowingForm());
        returnButton.addActionListener(e -> returnSelectedBook());
        deleteButton.addActionListener(e -> deleteSelectedBorrowing());
        refreshButton.addActionListener(e -> refreshBorrowingsTable());
        
        buttonPanel.add(addButton);
        buttonPanel.add(returnButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);
        
        // Table
        borrowingsTable = new JTable();
        configureTable(borrowingsTable);
        refreshBorrowingsTable();
        
        JScrollPane scrollPane = new JScrollPane(borrowingsTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(DARK_PURPLE, 1));
        scrollPane.getViewport().setBackground(WHITE_BG);
        
        panel.add(buttonPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void configureTable(JTable table) {
        table.setBackground(WHITE_BG);
        table.setForeground(BLACK_TEXT);
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.setGridColor(new Color(200, 200, 200));
        table.setRowHeight(25);
        table.setShowGrid(true);
        
        // Custom cell renderer to ensure black text
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, 
                        isSelected, hasFocus, row, column);
                c.setForeground(BLACK_TEXT);
                c.setBackground(isSelected ? new Color(230, 230, 255) : WHITE_BG);
                setHorizontalAlignment(SwingConstants.LEFT);
                setFont(new Font("Arial", Font.PLAIN, 12));
                return c;
            }
        });
        
        // Set header
        JTableHeader header = table.getTableHeader();
        header.setBackground(DARK_PURPLE);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 12));
        header.setReorderingAllowed(false);
    }
    
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setPreferredSize(new Dimension(120, 35));
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(DARK_PURPLE, 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        
        // Add hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(bgColor.darker());
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });
        
        return button;
    }
    
    // Helper method to create date spinner
    private JSpinner createDateSpinner() {
        SpinnerDateModel model = new SpinnerDateModel();
        JSpinner spinner = new JSpinner(model);
        
        // Set the date format
        JSpinner.DateEditor editor = new JSpinner.DateEditor(spinner, "yyyy-MM-dd");
        spinner.setEditor(editor);
        
        // Set styling
        spinner.setBackground(WHITE_BG);
        spinner.setForeground(BLACK_TEXT);
        spinner.setFont(new Font("Arial", Font.PLAIN, 12));
        spinner.setBorder(BorderFactory.createLineBorder(DARK_PURPLE, 1));
        
        // Style the editor text field
        JFormattedTextField tf = ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField();
        tf.setBackground(WHITE_BG);
        tf.setForeground(BLACK_TEXT);
        tf.setFont(new Font("Arial", Font.PLAIN, 12));
        
        return spinner;
    }
    
    // Helper method to create styled text field
    private JTextField createStyledTextField(int columns) {
        JTextField textField = new JTextField(columns);
        textField.setBackground(WHITE_BG);
        textField.setForeground(BLACK_TEXT);
        textField.setFont(new Font("Arial", Font.PLAIN, 12));
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(DARK_PURPLE, 1),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        return textField;
    }
    
    // Helper method to create styled combo box
    private <T> JComboBox<T> createStyledComboBox(T[] items) {
        JComboBox<T> comboBox = new JComboBox<>(items);
        comboBox.setBackground(WHITE_BG);
        comboBox.setForeground(BLACK_TEXT);
        comboBox.setFont(new Font("Arial", Font.PLAIN, 12));
        comboBox.setBorder(BorderFactory.createLineBorder(DARK_PURPLE, 1));
        
        // Style the editor component
        Component editor = comboBox.getEditor().getEditorComponent();
        if (editor instanceof JTextField) {
            ((JTextField) editor).setBackground(WHITE_BG);
            ((JTextField) editor).setForeground(BLACK_TEXT);
        }
        
        return comboBox;
    }
    
    // Helper method to create styled label
    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(BLACK_TEXT);
        label.setFont(new Font("Arial", Font.BOLD, 12));
        return label;
    }
    
    // Refresh table methods
    private void refreshBooksTable() {
        String[] columns = {"ISBN", "Title", "Author", "Genre", "Publication Date", "Total Copies", "Available"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table non-editable
            }
        };
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        for (Book book : books) {
            Object[] row = {
                book.getIsbn(),
                book.getTitle(),
                book.getAuthor(),
                book.getGenre(),
                sdf.format(book.getPublicationDate()),
                book.getTotalCopies(),
                book.getAvailableCopies()
            };
            model.addRow(row);
        }
        
        booksTable.setModel(model);
        configureTable(booksTable);
    }
    
    private void refreshMembersTable() {
        String[] columns = {"Member ID", "Name", "Email", "Phone", "Type", "Registration Date", "Books Borrowed"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        for (Member member : members) {
            Object[] row = {
                member.getMemberId(),
                member.getName(),
                member.getEmail(),
                member.getPhone(),
                member.getMemberType(),
                sdf.format(member.getRegistrationDate()),
                member.getBorrowedBooksCount()
            };
            model.addRow(row);
        }
        
        membersTable.setModel(model);
        configureTable(membersTable);
    }
    
    private void refreshBorrowingsTable() {
        String[] columns = {"Borrow ID", "Book ISBN", "Member ID", "Borrow Date", "Due Date", "Return Date", "Status"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        for (Borrowing borrowing : borrowings) {
            Object[] row = {
                borrowing.getBorrowingId(),
                borrowing.getBookIsbn(),
                borrowing.getMemberId(),
                sdf.format(borrowing.getBorrowDate()),
                sdf.format(borrowing.getDueDate()),
                borrowing.getReturnDate() != null ? sdf.format(borrowing.getReturnDate()) : "Not Returned",
                borrowing.getStatus()
            };
            model.addRow(row);
        }
        
        borrowingsTable.setModel(model);
        configureTable(borrowingsTable);
    }
    
    private void refreshAllTables() {
        refreshBooksTable();
        refreshMembersTable();
        refreshBorrowingsTable();
    }
    
    // Book CRUD operations
    private void showBookForm(Book book) {
        JDialog dialog = new JDialog(this, book == null ? "Add Book" : "Edit Book", true);
        dialog.setSize(500, 550);
        dialog.setLocationRelativeTo(this);
        dialog.getContentPane().setBackground(LIGHT_PURPLE);
        
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(LIGHT_PURPLE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // ISBN
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(createStyledLabel("ISBN:"), gbc);
        
        gbc.gridx = 1; gbc.gridy = 0;
        JTextField isbnField = createStyledTextField(20);
        if (book != null) isbnField.setText(book.getIsbn());
        panel.add(isbnField, gbc);
        
        // Title
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(createStyledLabel("Title:"), gbc);
        
        gbc.gridx = 1; gbc.gridy = 1;
        JTextField titleField = createStyledTextField(20);
        if (book != null) titleField.setText(book.getTitle());
        panel.add(titleField, gbc);
        
        // Author
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(createStyledLabel("Author:"), gbc);
        
        gbc.gridx = 1; gbc.gridy = 2;
        JTextField authorField = createStyledTextField(20);
        if (book != null) authorField.setText(book.getAuthor());
        panel.add(authorField, gbc);
        
        // Genre
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(createStyledLabel("Genre:"), gbc);
        
        gbc.gridx = 1; gbc.gridy = 3;
        JComboBox<Book.BookGenre> genreCombo = createStyledComboBox(Book.BookGenre.values());
        if (book != null) genreCombo.setSelectedItem(book.getGenre());
        panel.add(genreCombo, gbc);
        
        // Publication Date
        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(createStyledLabel("Publication Date:"), gbc);
        
        gbc.gridx = 1; gbc.gridy = 4;
        JSpinner pubDateSpinner = createDateSpinner();
        if (book != null && book.getPublicationDate() != null) {
            pubDateSpinner.setValue(book.getPublicationDate());
        } else {
            pubDateSpinner.setValue(new Date());
        }
        panel.add(pubDateSpinner, gbc);
        
        // Total Copies
        gbc.gridx = 0; gbc.gridy = 5;
        panel.add(createStyledLabel("Total Copies:"), gbc);
        
        gbc.gridx = 1; gbc.gridy = 5;
        JTextField copiesField = createStyledTextField(20);
        if (book != null) copiesField.setText(String.valueOf(book.getTotalCopies()));
        else copiesField.setText("1");
        panel.add(copiesField, gbc);
        
        // Buttons
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(LIGHT_PURPLE);
        
        JButton saveButton = createStyledButton("Save", ACCENT_PURPLE);
        saveButton.setPreferredSize(new Dimension(100, 35));
        
        JButton cancelButton = createStyledButton("Cancel", Color.GRAY);
        cancelButton.setPreferredSize(new Dimension(100, 35));
        
        saveButton.addActionListener(e -> {
            try {
                String isbn = isbnField.getText().trim();
                String title = titleField.getText().trim();
                String author = authorField.getText().trim();
                Book.BookGenre genre = (Book.BookGenre) genreCombo.getSelectedItem();
                Date pubDate = (Date) pubDateSpinner.getValue();
                int totalCopies = Integer.parseInt(copiesField.getText().trim());
                
                if (isbn.isEmpty() || title.isEmpty() || author.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Please fill all required fields!");
                    return;
                }
                
                if (totalCopies <= 0) {
                    JOptionPane.showMessageDialog(dialog, "Total copies must be greater than 0!");
                    return;
                }
                
                if (book == null) {
                    // Check for duplicate ISBN
                    for (Book b : books) {
                        if (b.getIsbn().equals(isbn)) {
                            JOptionPane.showMessageDialog(dialog, "A book with this ISBN already exists!");
                            return;
                        }
                    }
                    
                    // Add new book
                    Book newBook = new Book(isbn, title, author, genre, pubDate, totalCopies);
                    books.add(newBook);
                    JOptionPane.showMessageDialog(this, "Book added successfully!");
                } else {
                    // Update existing book
                    book.setIsbn(isbn);
                    book.setTitle(title);
                    book.setAuthor(author);
                    book.setGenre(genre);
                    book.setPublicationDate(pubDate);
                    book.setTotalCopies(totalCopies);
                    JOptionPane.showMessageDialog(this, "Book updated successfully!");
                }
                
                refreshBooksTable();
                dialog.dispose();
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Please enter a valid number for copies!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Error: " + ex.getMessage());
            }
        });
        
        cancelButton.addActionListener(e -> dialog.dispose());
        
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        panel.add(buttonPanel, gbc);
        
        dialog.add(panel);
        dialog.setVisible(true);
    }
    
    private void editSelectedBook() {
        int selectedRow = booksTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a book to edit!");
            return;
        }
        
        String isbn = (String) booksTable.getValueAt(selectedRow, 0);
        Book bookToEdit = null;
        
        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) {
                bookToEdit = book;
                break;
            }
        }
        
        if (bookToEdit != null) {
            showBookForm(bookToEdit);
        }
    }
    
    private void deleteSelectedBook() {
        int selectedRow = booksTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a book to delete!");
            return;
        }
        
        String isbn = (String) booksTable.getValueAt(selectedRow, 0);
        
        int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to delete this book?",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            Book bookToDelete = null;
            for (Book book : books) {
                if (book.getIsbn().equals(isbn)) {
                    bookToDelete = book;
                    break;
                }
            }
            
            if (bookToDelete != null) {
                books.remove(bookToDelete);
                refreshBooksTable();
                JOptionPane.showMessageDialog(this, "Book deleted successfully!");
            }
        }
    }
    
    private void searchBooks() {
        String searchTerm = JOptionPane.showInputDialog(this, "Enter search term (ISBN, Title, or Author):");
        if (searchTerm == null || searchTerm.trim().isEmpty()) return;
        
        searchTerm = searchTerm.toLowerCase();
        DefaultTableModel model = (DefaultTableModel) booksTable.getModel();
        model.setRowCount(0);
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        for (Book book : books) {
            if (book.getIsbn().toLowerCase().contains(searchTerm) ||
                book.getTitle().toLowerCase().contains(searchTerm) ||
                book.getAuthor().toLowerCase().contains(searchTerm)) {
                
                Object[] row = {
                    book.getIsbn(),
                    book.getTitle(),
                    book.getAuthor(),
                    book.getGenre(),
                    sdf.format(book.getPublicationDate()),
                    book.getTotalCopies(),
                    book.getAvailableCopies()
                };
                model.addRow(row);
            }
        }
    }
    
    // Member CRUD operations
    private void showMemberForm(Member member) {
        JDialog dialog = new JDialog(this, member == null ? "Add Member" : "Edit Member", true);
        dialog.setSize(500, 500);
        dialog.setLocationRelativeTo(this);
        dialog.getContentPane().setBackground(LIGHT_PURPLE);
        
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(LIGHT_PURPLE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Member ID
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(createStyledLabel("Member ID:"), gbc);
        
        gbc.gridx = 1; gbc.gridy = 0;
        JTextField memberIdField = createStyledTextField(20);
        if (member != null) memberIdField.setText(member.getMemberId());
        else memberIdField.setText("MEM" + (members.size() + 1001));
        panel.add(memberIdField, gbc);
        
        // Name
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(createStyledLabel("Name:"), gbc);
        
        gbc.gridx = 1; gbc.gridy = 1;
        JTextField nameField = createStyledTextField(20);
        if (member != null) nameField.setText(member.getName());
        panel.add(nameField, gbc);
        
        // Email
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(createStyledLabel("Email:"), gbc);
        
        gbc.gridx = 1; gbc.gridy = 2;
        JTextField emailField = createStyledTextField(20);
        if (member != null) emailField.setText(member.getEmail());
        panel.add(emailField, gbc);
        
        // Phone
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(createStyledLabel("Phone:"), gbc);
        
        gbc.gridx = 1; gbc.gridy = 3;
        JTextField phoneField = createStyledTextField(20);
        if (member != null) phoneField.setText(member.getPhone());
        panel.add(phoneField, gbc);
        
        // Member Type
        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(createStyledLabel("Member Type:"), gbc);
        
        gbc.gridx = 1; gbc.gridy = 4;
        JComboBox<Member.MemberType> typeCombo = createStyledComboBox(Member.MemberType.values());
        if (member != null) typeCombo.setSelectedItem(member.getMemberType());
        panel.add(typeCombo, gbc);
        
        // Registration Date
        gbc.gridx = 0; gbc.gridy = 5;
        panel.add(createStyledLabel("Registration Date:"), gbc);
        
        gbc.gridx = 1; gbc.gridy = 5;
        JSpinner regDateSpinner = createDateSpinner();
        if (member != null && member.getRegistrationDate() != null) {
            regDateSpinner.setValue(member.getRegistrationDate());
        } else {
            regDateSpinner.setValue(new Date());
        }
        panel.add(regDateSpinner, gbc);
        
        // Buttons
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(LIGHT_PURPLE);
        
        JButton saveButton = createStyledButton("Save", ACCENT_PURPLE);
        saveButton.setPreferredSize(new Dimension(100, 35));
        
        JButton cancelButton = createStyledButton("Cancel", Color.GRAY);
        cancelButton.setPreferredSize(new Dimension(100, 35));
        
        saveButton.addActionListener(e -> {
            try {
                String memberId = memberIdField.getText().trim();
                String name = nameField.getText().trim();
                String email = emailField.getText().trim();
                String phone = phoneField.getText().trim();
                Member.MemberType type = (Member.MemberType) typeCombo.getSelectedItem();
                Date regDate = (Date) regDateSpinner.getValue();
                
                if (memberId.isEmpty() || name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Please fill all required fields!");
                    return;
                }
                
                if (member == null) {
                    // Check for duplicate member ID
                    for (Member m : members) {
                        if (m.getMemberId().equals(memberId)) {
                            JOptionPane.showMessageDialog(dialog, "A member with this ID already exists!");
                            return;
                        }
                    }
                    
                    // Add new member
                    Member newMember = new Member(memberId, name, email, phone, type, regDate);
                    members.add(newMember);
                    JOptionPane.showMessageDialog(this, "Member added successfully!");
                } else {
                    // Update existing member
                    member.setMemberId(memberId);
                    member.setName(name);
                    member.setEmail(email);
                    member.setPhone(phone);
                    member.setMemberType(type);
                    member.setRegistrationDate(regDate);
                    JOptionPane.showMessageDialog(this, "Member updated successfully!");
                }
                
                refreshMembersTable();
                dialog.dispose();
                
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Error saving member: " + ex.getMessage());
            }
        });
        
        cancelButton.addActionListener(e -> dialog.dispose());
        
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        panel.add(buttonPanel, gbc);
        
        dialog.add(panel);
        dialog.setVisible(true);
    }
    
    private void editSelectedMember() {
        int selectedRow = membersTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a member to edit!");
            return;
        }
        
        String memberId = (String) membersTable.getValueAt(selectedRow, 0);
        Member memberToEdit = null;
        
        for (Member member : members) {
            if (member.getMemberId().equals(memberId)) {
                memberToEdit = member;
                break;
            }
        }
        
        if (memberToEdit != null) {
            showMemberForm(memberToEdit);
        }
    }
    
    private void deleteSelectedMember() {
        int selectedRow = membersTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a member to delete!");
            return;
        }
        
        String memberId = (String) membersTable.getValueAt(selectedRow, 0);
        
        // Check if member has active borrowings
        boolean hasActiveBorrowings = false;
        for (Borrowing borrowing : borrowings) {
            if (borrowing.getMemberId().equals(memberId) && 
                borrowing.getStatus() == Borrowing.BorrowingStatus.ACTIVE) {
                hasActiveBorrowings = true;
                break;
            }
        }
        
        if (hasActiveBorrowings) {
            JOptionPane.showMessageDialog(this, 
                "Cannot delete member with active book borrowings!");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to delete this member?",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            Member memberToDelete = null;
            for (Member member : members) {
                if (member.getMemberId().equals(memberId)) {
                    memberToDelete = member;
                    break;
                }
            }
            
            if (memberToDelete != null) {
                members.remove(memberToDelete);
                refreshMembersTable();
                JOptionPane.showMessageDialog(this, "Member deleted successfully!");
            }
        }
    }
    
    // Borrowing operations
    private void showBorrowingForm() {
        JDialog dialog = new JDialog(this, "Borrow Book", true);
        dialog.setSize(500, 450);
        dialog.setLocationRelativeTo(this);
        dialog.getContentPane().setBackground(LIGHT_PURPLE);
        
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(LIGHT_PURPLE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Book selection
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(createStyledLabel("Select Book:"), gbc);
        
        gbc.gridx = 1; gbc.gridy = 0;
        DefaultComboBoxModel<String> bookModel = new DefaultComboBoxModel<>();
        for (Book book : books) {
            if (book.getAvailableCopies() > 0) {
                bookModel.addElement(book.getIsbn() + " - " + book.getTitle() + 
                                   " (Available: " + book.getAvailableCopies() + ")");
            }
        }
        JComboBox<String> bookCombo = createStyledComboBox(new String[0]);
        bookCombo.setModel(bookModel);
        panel.add(bookCombo, gbc);
        
        // Member selection
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(createStyledLabel("Select Member:"), gbc);
        
        gbc.gridx = 1; gbc.gridy = 1;
        DefaultComboBoxModel<String> memberModel = new DefaultComboBoxModel<>();
        for (Member member : members) {
            memberModel.addElement(member.getMemberId() + " - " + member.getName());
        }
        JComboBox<String> memberCombo = createStyledComboBox(new String[0]);
        memberCombo.setModel(memberModel);
        panel.add(memberCombo, gbc);
        
        // Borrow Date
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(createStyledLabel("Borrow Date:"), gbc);
        
        gbc.gridx = 1; gbc.gridy = 2;
        JSpinner borrowDateSpinner = createDateSpinner();
        borrowDateSpinner.setValue(new Date());
        panel.add(borrowDateSpinner, gbc);
        
        // Due Date
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(createStyledLabel("Due Date:"), gbc);
        
        gbc.gridx = 1; gbc.gridy = 3;
        JSpinner dueDateSpinner = createDateSpinner();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 14); // 14 days from today
        dueDateSpinner.setValue(cal.getTime());
        panel.add(dueDateSpinner, gbc);
        
        // Buttons
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(LIGHT_PURPLE);
        
        JButton saveButton = createStyledButton("Borrow", ACCENT_PURPLE);
        saveButton.setPreferredSize(new Dimension(100, 35));
        
        JButton cancelButton = createStyledButton("Cancel", Color.GRAY);
        cancelButton.setPreferredSize(new Dimension(100, 35));
        
        saveButton.addActionListener(e -> {
            try {
                if (bookCombo.getSelectedItem() == null || memberCombo.getSelectedItem() == null) {
                    JOptionPane.showMessageDialog(dialog, "Please select both book and member!");
                    return;
                }
                
                String bookSelection = (String) bookCombo.getSelectedItem();
                String memberSelection = (String) memberCombo.getSelectedItem();
                
                // Extract ISBN from selection string
                String bookIsbn = bookSelection.split(" - ")[0];
                String memberId = memberSelection.split(" - ")[0];
                Date borrowDate = (Date) borrowDateSpinner.getValue();
                Date dueDate = (Date) dueDateSpinner.getValue();
                
                // Validate dates
                if (dueDate.before(borrowDate)) {
                    JOptionPane.showMessageDialog(dialog, "Due date must be after borrow date!");
                    return;
                }
                
                // Find book and member
                Book selectedBook = null;
                Member selectedMember = null;
                
                for (Book book : books) {
                    if (book.getIsbn().equals(bookIsbn)) {
                        selectedBook = book;
                        break;
                    }
                }
                
                for (Member member : members) {
                    if (member.getMemberId().equals(memberId)) {
                        selectedMember = member;
                        break;
                    }
                }
                
                if (selectedBook == null || selectedMember == null) {
                    JOptionPane.showMessageDialog(dialog, "Book or member not found!");
                    return;
                }
                
                // Check if book is available
                if (selectedBook.getAvailableCopies() <= 0) {
                    JOptionPane.showMessageDialog(dialog, "No copies available for this book!");
                    return;
                }
                
                // Create borrowing record
                String borrowingId = "BOR" + (borrowings.size() + 1001);
                Borrowing newBorrowing = new Borrowing(borrowingId, bookIsbn, memberId, borrowDate, dueDate);
                
                // Update book and member
                selectedBook.borrowCopy();
                selectedMember.incrementBorrowedCount();
                
                borrowings.add(newBorrowing);
                
                refreshBooksTable();
                refreshMembersTable();
                refreshBorrowingsTable();
                
                dialog.dispose();
                JOptionPane.showMessageDialog(this, "Book borrowed successfully!");
                
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Error: " + ex.getMessage());
                ex.printStackTrace();
            }
        });
        
        cancelButton.addActionListener(e -> dialog.dispose());
        
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        panel.add(buttonPanel, gbc);
        
        dialog.add(panel);
        dialog.setVisible(true);
    }
    
    private void returnSelectedBook() {
        int selectedRow = borrowingsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a borrowing record to return!");
            return;
        }
        
        String borrowingId = (String) borrowingsTable.getValueAt(selectedRow, 0);
        Borrowing borrowingToReturn = null;
        
        for (Borrowing borrowing : borrowings) {
            if (borrowing.getBorrowingId().equals(borrowingId) && 
                borrowing.getStatus() == Borrowing.BorrowingStatus.ACTIVE) {
                borrowingToReturn = borrowing;
                break;
            }
        }
        
        if (borrowingToReturn == null) {
            JOptionPane.showMessageDialog(this, "Selected book is already returned or not active!");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to return this book?",
            "Confirm Return",
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            // Find and update the book
            for (Book book : books) {
                if (book.getIsbn().equals(borrowingToReturn.getBookIsbn())) {
                    book.returnCopy();
                    break;
                }
            }
            
            // Find and update the member
            for (Member member : members) {
                if (member.getMemberId().equals(borrowingToReturn.getMemberId())) {
                    member.decrementBorrowedCount();
                    break;
                }
            }
            
            // Update borrowing record
            borrowingToReturn.returnBook();
            
            refreshBooksTable();
            refreshMembersTable();
            refreshBorrowingsTable();
            
            JOptionPane.showMessageDialog(this, "Book returned successfully!");
        }
    }
    
    private void deleteSelectedBorrowing() {
        int selectedRow = borrowingsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a borrowing record to delete!");
            return;
        }
        
        String borrowingId = (String) borrowingsTable.getValueAt(selectedRow, 0);
        
        int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to delete this borrowing record?",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            Borrowing borrowingToDelete = null;
            for (Borrowing borrowing : borrowings) {
                if (borrowing.getBorrowingId().equals(borrowingId)) {
                    borrowingToDelete = borrowing;
                    break;
                }
            }
            
            if (borrowingToDelete != null) {
                // If book is not returned, update book and member counts
                if (borrowingToDelete.getStatus() == Borrowing.BorrowingStatus.ACTIVE) {
                    for (Book book : books) {
                        if (book.getIsbn().equals(borrowingToDelete.getBookIsbn())) {
                            book.returnCopy();
                            break;
                        }
                    }
                    
                    for (Member member : members) {
                        if (member.getMemberId().equals(borrowingToDelete.getMemberId())) {
                            member.decrementBorrowedCount();
                            break;
                        }
                    }
                }
                
                borrowings.remove(borrowingToDelete);
                refreshAllTables();
                JOptionPane.showMessageDialog(this, "Borrowing record deleted successfully!");
            }
        }
    }
}