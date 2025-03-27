/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalTime;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import model.Book;
import utils.DBContext;

/**
 *
 * @author anhkc
 */
public class HomeBookDAO {

    private DBContext dbContext;

    public HomeBookDAO() {
        dbContext = new DBContext();
    }

    public List<Book> selectAllBook() throws SQLException {
        String sql = "select * from Book";
        List<Book> bookList = new ArrayList<>();

        ResultSet resultSet = dbContext.exeQuery(sql);

        while (resultSet.next()) {
            bookList.add(new Book(resultSet.getInt("bookID"), resultSet.getString("bookTitle"), resultSet.getString("bookCover"),
                    resultSet.getString("bookVersion"), resultSet.getString("publisherID"), resultSet.getDate("bookPublishDate"),
                    resultSet.getDate("bookImportDate"), resultSet.getString("bookIntro"), resultSet.getInt("bookQuantity"),
                    resultSet.getInt("bookPrice"), resultSet.getInt("bookDiscount"), resultSet.getInt("bookFlashSale")));
        }
        return bookList;
    }

    public Book getbookbyid(int bookID) {

        String query = "select * from Book\n"
                + "where bookID =?";
        try {
            Connection conn = new DBContext().getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, bookID);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                Book book = new Book();
                book.setBookID(bookID);
                book.setBookCover(resultSet.getString("bookCover"));
                book.setBookDiscount(resultSet.getInt("bookDiscount"));
                book.setBookFlashSale(resultSet.getInt("bookFlashSale"));
                book.setBookImportDate(resultSet.getDate("bookImportDate"));
                book.setBookIntro(resultSet.getString("bookIntro"));
                book.setBookPrice(resultSet.getInt("bookPrice"));
                book.setBookPublishDate(resultSet.getDate("bookPublishDate"));
                book.setBookQuantity(resultSet.getInt("bookQuantity"));
                book.setBookTitle(resultSet.getString("bookTitle"));
                book.setBookVersion(resultSet.getString("bookVersion"));
                book.setPublisherID(resultSet.getString("publisherID"));
                return book;
//                return new Book(resultSet.getInt("bookID"), resultSet.getString("bookTitle"), resultSet.getString("bookCover"),
//                        resultSet.getString("bookVersion"), resultSet.getString("publisherID"), resultSet.getDate("bookPublishDate"),
//                        resultSet.getDate("bookImportDate"), resultSet.getString("bookIntro"), resultSet.getInt("bookQuantity"),
//                        resultSet.getDouble("bookPrice"), resultSet.getInt("bookDiscount"), resultSet.getInt("bookFlashSale"));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Book> selectTop10OrderBy(String condition, String order) throws SQLException {
        String sql = "select top 10 * from Book order by" + " " + condition + " " + order;
        List<Book> bookList = new ArrayList<>();
        ResultSet resultSet = dbContext.exeQuery(sql);

        while (resultSet.next()) {
            bookList.add(new Book(resultSet.getInt("bookID"), resultSet.getString("bookTitle"), resultSet.getString("bookCover"),
                    resultSet.getString("bookVersion"), resultSet.getString("publisherID"), resultSet.getDate("bookPublishDate"),
                    resultSet.getDate("bookImportDate"), resultSet.getString("bookIntro"), resultSet.getInt("bookQuantity"),
                    resultSet.getInt("bookPrice"), resultSet.getInt("bookDiscount"), resultSet.getInt("bookFlashSale")));
        }
        return bookList;

    }

    public List<Book> selectTop10BestSeller() throws SQLException {
        String sql = "SELECT TOP 10 s.bookID, bookTitle, bookCover, bookQuantity, bookPrice, bookDiscount, SUM(soldQuantity) AS totalSales\n"
                + "FROM SaleHistory s\n"
                + "join Book b\n"
                + "on b.bookID = s.bookID\n"
                + "WHERE saleDate between getdate() - 30 and getdate()\n"
                + "GROUP BY s.bookID, bookTitle, bookCover, bookQuantity, bookPrice, bookDiscount\n"
                + "ORDER BY totalSales DESC";
        System.out.println(sql);
        List<Book> bookList = new ArrayList<>();
        ResultSet resultSet = dbContext.exeQuery(sql);

        while (resultSet.next()) {
            bookList.add(new Book(resultSet.getInt("bookID"), resultSet.getString("bookTitle"), resultSet.getString("bookCover"),
                    resultSet.getInt("bookQuantity"), resultSet.getInt("bookPrice"), resultSet.getInt("bookDiscount"), resultSet.getInt("totalSales")));
        }
        return bookList;

    }

    public int randomDiscount(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }

    public List<Book> select10RandomBooks() throws SQLException {
        String sql = "select top 10 * from Book order by NEWID()";
        List<Book> bookList = new ArrayList<>();
        ResultSet resultSet = dbContext.exeQuery(sql);

        while (resultSet.next()) {
            bookList.add(new Book(resultSet.getInt("bookID"), resultSet.getString("bookTitle"), resultSet.getString("bookCover"),
                    resultSet.getString("bookVersion"), resultSet.getString("publisherID"), resultSet.getDate("bookPublishDate"),
                    resultSet.getDate("bookImportDate"), resultSet.getString("bookIntro"), resultSet.getInt("bookQuantity"),
                    resultSet.getInt("bookPrice"), resultSet.getInt("bookDiscount"), resultSet.getInt("bookFlashSale")));
        }
        return bookList;

    }

    public void updateFlashSale(int flashDiscount, Book flashBook) throws SQLException {
        String sql = "update Book set bookFlashSale = ? where bookID = ?";
        Object[] params = {flashDiscount, flashBook.getBookID()};
        int rowsAffected = dbContext.exeNonQuery(sql, params);
        System.out.println(rowsAffected + " book has been updated - " + flashDiscount);
    }

    public void callUpdateFlashSale() throws SQLException {
        List<Book> flashBooks = selectFlashSaleBooks();
        if (!flashBooks.isEmpty()) {
            return;
        }
        flashBooks = select10RandomBooks();
        for (int i = 0; i < flashBooks.size(); i++) {
            int flashDiscount = randomDiscount(30, 50);
            updateFlashSale(flashDiscount, flashBooks.get(i));
        }
    }

    public void updateDiscount(int discount, Book book) throws SQLException {
        String sql = "update Book set bookDiscount = ? where bookID = ?";
        Object[] params = {discount, book.getBookID()};
        int rowsAffected = dbContext.exeNonQuery(sql, params);
        System.out.println(rowsAffected + " book has been updated - " + discount);
    }

    public void callUpdateDiscount_AllBook() throws SQLException {
        List<Book> books = selectAllBook();
        for (int i = 0; i < books.size(); i++) {
            int discount = randomDiscount(10, 30);
            updateDiscount(discount, books.get(i));
        }
    }

    public List<Book> selectFlashSaleBooks() throws SQLException {
        String sql = "select * from Book where bookFlashSale > 0";
        List<Book> bookList = new ArrayList<>();
        ResultSet resultSet = dbContext.exeQuery(sql);

        while (resultSet.next()) {
            bookList.add(new Book(resultSet.getInt("bookID"), resultSet.getString("bookTitle"), resultSet.getString("bookCover"),
                    resultSet.getString("bookVersion"), resultSet.getString("publisherID"), resultSet.getDate("bookPublishDate"),
                    resultSet.getDate("bookImportDate"), resultSet.getString("bookIntro"), resultSet.getInt("bookQuantity"),
                    resultSet.getInt("bookPrice"), resultSet.getInt("bookDiscount"), resultSet.getInt("bookFlashSale")));
        }
        return bookList;
    }

    public void resetFlashSale(Book flashBook) throws SQLException {
        String sql = "update Book set bookFlashSale = 0 where bookID = ?";
        Object[] params = {flashBook.getBookID()};
        int rowsAffected = dbContext.exeNonQuery(sql, params);
        System.out.println(rowsAffected + " books have been updated.");

    }

    public void callResetFlashSale() throws SQLException {
        List<Book> bookList = selectFlashSaleBooks();
        if (bookList.isEmpty()) {
            return;
        }

        for (Book book : bookList) {
            resetFlashSale(book);
        }
    }

    public static void main(String[] args) {

        LocalTime currentTime = LocalTime.now();
        int currentHour = currentTime.getHour();
        System.out.println("Current hour: " + currentHour + "h");

        try {
            System.out.println(Runtime.getRuntime().availableProcessors() + " threads are currently available.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
