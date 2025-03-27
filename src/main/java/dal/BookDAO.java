/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import utils.DBContext;
import model.Book;
import model.Category;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author conkg
 */
public class BookDAO {

    private DBContext dbContext;
    Connection conn;
    PreparedStatement ps;
    ResultSet rs;

    public BookDAO() throws SQLException, ClassNotFoundException {
        conn = new DBContext().getConnection();
        dbContext = new DBContext();
    }

    public List<Book> getRandomBooksByCategory(String categoryId) throws ClassNotFoundException, SQLException {
        List<Book> list = new ArrayList<>();
        String query = "SELECT TOP 5 Book.* "
                + "FROM Book "
                + "INNER JOIN Book_Category ON Book.bookID = Book_Category.bookID "
                + "WHERE Book_Category.categoryID = ? "
                + "ORDER BY NEWID()";

        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, categoryId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Book(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getDate(6),
                        rs.getDate(7),
                        rs.getString(8),
                        rs.getInt(9),
                        rs.getInt(10),
                        rs.getInt(11),
                        rs.getInt(12)
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Book> getALLBook() {
        List<Book> list = new ArrayList<>();
        String query = "select * from Book";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Book(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getDate(6),
                        rs.getDate(7),
                        rs.getString(8),
                        rs.getInt(9),
                        rs.getInt(10),
                        rs.getInt(11),
                        rs.getInt(12)
                ));

            }

        } catch (Exception e) {
        }

        return list;
    }

    public List<Category> getAllcategory() {
        List<Category> list = new ArrayList<>();
        String query = "select * from Category";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Category(
                        rs.getString(1),
                        rs.getString(2)
                ));

            }
        } catch (Exception e) {
        }

        return list;
    }

    public Book getbookbyid(int bookID) {

        String query = "select * from Book\n"
                + "where bookID =?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, bookID);
            rs = ps.executeQuery();

            while (rs.next()) {
                return new Book(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getDate(6),
                        rs.getDate(7),
                        rs.getString(8),
                        rs.getInt(9),
                        rs.getInt(10),
                        rs.getInt(11),
                        rs.getInt(12));

            }
        } catch (Exception e) {
        }
        return null;
    }

    public String getBookCoverById(int bookID) {
    String bookCover = null;
    String query = "SELECT bookCover FROM [dbo].[Book] WHERE bookID = ?";
    
    try (Connection conn = new DBContext().getConnection();
         PreparedStatement ps = conn.prepareStatement(query)) {
        
        ps.setInt(1, bookID);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                bookCover = rs.getString("bookCover");
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return bookCover; // Trả về null nếu không tìm thấy sách hoặc có lỗi
}
    
    
    public List<Book> getbooknew(String query) {
        List<Book> list = new ArrayList<>();

        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Book(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getDate(6),
                        rs.getDate(7),
                        rs.getString(8),
                        rs.getInt(9),
                        rs.getInt(10),
                        rs.getInt(11),
                        rs.getInt(12)
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list; // Return the populated list
    }

    public Book getHoaDonByName(int bookID) throws ClassNotFoundException {
        String query = "select * from book where bookID=?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, bookID);
            rs = ps.executeQuery();
            while (rs.next()) {
                Book hd = new Book(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getDate(6),
                        rs.getDate(7),
                        rs.getString(8),
                        rs.getInt(9),
                        rs.getInt(10),
                        rs.getInt(11),
                        rs.getInt(12)
                );
                return hd;
            }
        } catch (SQLException e) {
        }
        return null;

    }

    public List<String> getCategoriesByBookID(int bookID) {
        List<String> categories = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = new DBContext().getConnection();
            String query = "SELECT c.categoryID, c.categoryName FROM [dbo].[Book_Category] bc "
                    + "JOIN [dbo].[Category] c ON bc.categoryID = c.categoryID "
                    + "WHERE bc.bookID = ?";
            ps = conn.prepareStatement(query);
            ps.setInt(1, bookID);
            rs = ps.executeQuery();

            while (rs.next()) {
                String categoryID = rs.getString("categoryID");
                categories.add(categoryID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return categories;
    }

    public void edit(Book book) throws ClassNotFoundException {
        String sql = "UPDATE [dbo].[Book] SET [bookTitle] = ?, [bookCover] = ?, [bookVersion] = ?, "
                + "[publisherID] = ?, [bookPublishDate] = ?, [bookImportDate] = ?, "
                + "[bookIntro] = ?, [bookQuantity] = ?, [bookPrice] = ?, [bookDiscount] = ? ,bookFlashSale = ? "
                + "WHERE [bookID] = ?";

        PreparedStatement st = null;

        try {

            st = conn.prepareStatement(sql);
            st.setString(1, book.getBookTitle());
            st.setString(2, book.getBookCover());
            st.setString(3, book.getBookVersion());
            st.setString(4, book.getPublisherID());
            st.setDate(5, book.getBookPublishDate());
            st.setDate(6, book.getBookImportDate());
            st.setString(7, book.getBookIntro());
            st.setInt(8, book.getBookQuantity());
            st.setDouble(9, book.getBookPrice());
            st.setDouble(10, book.getBookDiscount());
            st.setInt(11, book.getBookFlashSale());
            st.setInt(12, book.getBookID());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (st != null) {
                    st.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void createBookWithCategories(Book book, String[] categoryIDs) {
        Connection conn = null;
        PreparedStatement psBook = null;
        PreparedStatement psCategory = null;
        try {
            conn = new DBContext().getConnection();
            conn.setAutoCommit(false); // Bắt đầu transaction

            // Thêm sách vào bảng Book
            String bookQuery = "INSERT INTO [dbo].[Book] (bookID, bookTitle, bookCover, bookVersion, publisherID, bookPublishDate, bookImportDate, bookIntro, bookQuantity, bookPrice, bookDiscount, bookFlashSale) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            psBook = conn.prepareStatement(bookQuery);
            psBook.setInt(1, book.getBookID());
            psBook.setString(2, book.getBookTitle());
            psBook.setString(3, book.getBookCover());
            psBook.setString(4, book.getBookVersion());
            psBook.setString(5, book.getPublisherID());
            psBook.setDate(6, book.getBookPublishDate());
            psBook.setDate(7, book.getBookImportDate());
            psBook.setString(8, book.getBookIntro());
            psBook.setInt(9, book.getBookQuantity());
            psBook.setDouble(10, book.getBookPrice());
            psBook.setDouble(11, book.getBookDiscount());
            psBook.setInt(12, book.getBookFlashSale());

            psBook.executeUpdate();

            // Thêm dữ liệu vào bảng Book_Category
            String categoryQuery = "INSERT INTO [dbo].[Book_Category] (bookID, categoryID) VALUES (?, ?)";
            psCategory = conn.prepareStatement(categoryQuery);
            for (String categoryID : categoryIDs) {
                psCategory.setInt(1, book.getBookID());
                psCategory.setString(2, categoryID);
                psCategory.executeUpdate();
            }

            conn.commit(); // Nếu tất cả đều thành công, commit transaction
        } catch (SQLException e) {
            try {
                if (conn != null) {
                    conn.rollback(); // Nếu lỗi, rollback transaction
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                if (psBook != null) {
                    psBook.close();
                }
                if (psCategory != null) {
                    psCategory.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateBookWithCategories(Book book, String[] categoryIDs) {
        Connection conn = null;
        PreparedStatement psBook = null;
        PreparedStatement psDeleteCategory = null;
        PreparedStatement psInsertCategory = null;
        try {
            conn = new DBContext().getConnection();
            conn.setAutoCommit(false); // Bắt đầu transaction

            // Cập nhật thông tin sách
            String bookQuery = "UPDATE [dbo].[Book] SET bookTitle = ?, bookCover = ?, bookVersion = ?, publisherID = ?, bookPublishDate = ?, bookImportDate = ?, bookIntro = ?, bookQuantity = ?, bookPrice = ?, bookDiscount = ?, bookFlashSale = ? WHERE bookID = ?";
            psBook = conn.prepareStatement(bookQuery);
            psBook.setString(1, book.getBookTitle());
            psBook.setString(2, book.getBookCover());
            psBook.setString(3, book.getBookVersion());
            psBook.setString(4, book.getPublisherID());
            psBook.setDate(5, book.getBookPublishDate());
            psBook.setDate(6, book.getBookImportDate());
            psBook.setString(7, book.getBookIntro());
            psBook.setInt(8, book.getBookQuantity());
            psBook.setDouble(9, book.getBookPrice());
            psBook.setDouble(10, book.getBookDiscount());
            psBook.setInt(11, book.getBookFlashSale());
            psBook.setInt(12, book.getBookID());

            psBook.executeUpdate();

            // Xóa category cũ của sách
            String deleteCategoryQuery = "DELETE FROM [dbo].[Book_Category] WHERE bookID = ?";
            psDeleteCategory = conn.prepareStatement(deleteCategoryQuery);
            psDeleteCategory.setInt(1, book.getBookID());
            psDeleteCategory.executeUpdate();

            // Thêm category mới vào sách
            String insertCategoryQuery = "INSERT INTO [dbo].[Book_Category] (bookID, categoryID) VALUES (?, ?)";
            psInsertCategory = conn.prepareStatement(insertCategoryQuery);
            for (String categoryID : categoryIDs) {
                psInsertCategory.setInt(1, book.getBookID());
                psInsertCategory.setString(2, categoryID);
                psInsertCategory.executeUpdate();
            }

            conn.commit(); // Nếu tất cả đều thành công, commit transaction
        } catch (SQLException e) {
            try {
                if (conn != null) {
                    conn.rollback(); // Nếu lỗi, rollback transaction
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                if (psBook != null) {
                    psBook.close();
                }
                if (psDeleteCategory != null) {
                    psDeleteCategory.close();
                }
                if (psInsertCategory != null) {
                    psInsertCategory.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void delete(int bookID) {
        String deleteOrderItems = "DELETE FROM OrderItems WHERE bookID = ?";
        String deleteCartItems = "DELETE FROM CartItems WHERE bookID = ?";
        String deleteBookAuthor = "DELETE FROM Book_Author WHERE bookID = ?";
        String deleteBookCategory = "DELETE FROM Book_Category WHERE bookID = ?";
        String deleteBook = "DELETE FROM Book WHERE bookID = ?";

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = new DBContext().getConnection();
            conn.setAutoCommit(false); // Begin transaction

            // Delete from OrderItems
            ps = conn.prepareStatement(deleteOrderItems);
            ps.setInt(1, bookID);
            ps.executeUpdate();
            ps.close();

            // Delete from CartItems
            ps = conn.prepareStatement(deleteCartItems);
            ps.setInt(1, bookID);
            ps.executeUpdate();
            ps.close();

            // Delete from Book_Author
            ps = conn.prepareStatement(deleteBookAuthor);
            ps.setInt(1, bookID);
            ps.executeUpdate();
            ps.close();

            // Delete from Book_Category
            ps = conn.prepareStatement(deleteBookCategory);
            ps.setInt(1, bookID);
            ps.executeUpdate();
            ps.close();

            // Delete from Book
            ps = conn.prepareStatement(deleteBook);
            ps.setInt(1, bookID);
            ps.executeUpdate();
            ps.close();

            conn.commit(); // Commit transaction
        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback(); // Rollback transaction on error
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public List<Book> getPaginatedProducts(int pageNumber, int pageSize) throws ClassNotFoundException {
        List<Book> list = new ArrayList<>();
        String query = "SELECT *\n"
                + "FROM Book ORDER BY bookID\n"
                + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);

            ps.setInt(1, (pageNumber - 1) * pageSize);
            ps.setInt(2, pageSize);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Book(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getDate(6),
                        rs.getDate(7),
                        rs.getString(8),
                        rs.getInt(9),
                        rs.getInt(10),
                        rs.getInt(11),
                        rs.getInt(12)
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int getTotalProducts() throws ClassNotFoundException {
        int count = 0;
        String query = "SELECT COUNT(*) FROM Book";

        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public List<Book> getSeach(String tuKhoaTimKiem) {
        List<Book> list = new ArrayList<>();
        String query = "SELECT distinct Book.*\n"
                + "FROM     Author INNER JOIN\n"
                + "                  Book_Author ON Author.authorID = Book_Author.authorID INNER JOIN\n"
                + "                  Book ON Book_Author.bookID = Book.bookID INNER JOIN\n"
                + "                  Book_Category ON Book.bookID = Book_Category.bookID INNER JOIN\n"
                + "                  Category ON Book_Category.categoryID = Category.categoryID\n"
                + "WHERE  (Book.bookTitle LIKE ?) OR\n"
                + "                  (Author.authorName LIKE ?) OR\n"
                + "                  (Category.categoryName LIKE ?)";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, tuKhoaTimKiem);
            ps.setString(2, tuKhoaTimKiem);
            ps.setString(3, tuKhoaTimKiem);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Book(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getDate(6),
                        rs.getDate(7),
                        rs.getString(8),
                        rs.getInt(9),
                        rs.getInt(10),
                        rs.getInt(11),
                        rs.getInt(12)
                ));

            }
        } catch (Exception e) {
        }

        return list;
    }

    public List<Book> selectOrderBy(String condition, String order) throws SQLException {
        String sql = "select * from Book order by" + " " + condition + " " + order;
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

    public List<Book> selectBestSeller() throws SQLException {
        String sql = "SELECT s.bookID, bookTitle, bookCover, bookQuantity, bookPrice, bookDiscount, SUM(soldQuantity) AS totalSales\n"
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
    public int getTotalProductsWithfilter(String[] filters) throws ClassNotFoundException {
        StringBuilder s = new StringBuilder();
        for (String filter : filters) {
            s.append("'").append(filter).append("', ");
        }
        s.setLength(s.length() - 2);

        String query = "SELECT COUNT(*) AS TotalProducts\n"
                + "FROM [Book]\n"
                + "JOIN (\n"
                + "    SELECT bc.bookID\n"
                + "    FROM [dbo].[Book_Category] bc\n"
                + "    WHERE bc.categoryID IN (" + s + ")\n"
                + "    GROUP BY bc.bookID\n"
                + "    HAVING COUNT(DISTINCT bc.categoryID) = ?\n"
                + ") AS filtered_books\n"
                + "ON [dbo].[Book].[bookID] = filtered_books.[bookID]";

        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, filters.length);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("TotalProducts");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
  public List<Book> getPaginatedProductsWithfilter(int pageNumber, int pageSize, String[] filters) throws ClassNotFoundException {
        List<Book> list = new ArrayList<>();
        String s = "";
        for (String filter : filters) {
            s += "'" + filter + "', ";
        }
        s = s.substring(0, s.length() - 2);
        String query = "SELECT *\n"
                + "FROM [Book]\n"
                + "JOIN	(\n"
                + "			SELECT bc.bookID\n"
                + "			FROM [dbo].[Book_Category] bc\n"
                + "			WHERE bc.categoryID IN (" + s + ")\n"
                + "			GROUP BY bc.bookID\n"
                + "			HAVING COUNT(DISTINCT bc.categoryID) = ?\n"
                + "		) AS filtered_books\n"
                + "ON		[dbo].[Book].[bookID] = filtered_books.[bookID]\n"
                + "ORDER BY [Book].[bookID]\n"
                + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);

            ps.setInt(1, filters.length);
            ps.setInt(2, (pageNumber - 1) * pageSize);
            ps.setInt(3, pageSize);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Book(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getDate(6),
                        rs.getDate(7),
                        rs.getString(8),
                        rs.getInt(9),
                        rs.getInt(10),
                        rs.getInt(11),
                        rs.getInt(12)
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

}