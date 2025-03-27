/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import utils.DBContext;
import model.Book;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Category;

/**
 *
 * @author conkg
 */
public class CategroryDAO {

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public List<Book> getBookByCategrogy(String cateroryid) throws ClassNotFoundException, SQLException {
        List<Book> list = new ArrayList<>();
        String query = "SELECT Book.*\n"
                + "FROM     Book INNER JOIN\n"
                + "                  Book_Category ON Book.bookID = Book_Category.bookID\n"
                + "WHERE  Book_Category.categoryID = ?\n"
                + "order by Book.bookID\n";

        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);

            ps.setString(1, cateroryid);

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

    public int getTotalProductsCategrogy(String categoryid) throws ClassNotFoundException {
        int count = 0;
        String query = "SELECT count(Book.bookID)\n"
                + "FROM     Book INNER JOIN\n"
                + "                  Book_Category ON Book.bookID = Book_Category.bookID\n"
                + "where Book_Category.categoryID = ?";

        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, categoryid);
            rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public List<Category> getCategoriesByBookID(String bookID) {
        List<Category> list = new ArrayList<>();
        String query = "SELECT f.categoryID, f.categoryName "
                + "FROM Category f "
                + "JOIN Book_Category h ON f.categoryID = h.categoryID "
                + "WHERE h.bookID = ?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, bookID);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Category(
                        rs.getString("categoryID"),
                        rs.getString("categoryName")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return list;
    }

    public List<Category> getAllCategories() {
        List<Category> list = new ArrayList<>();
        String query = "SELECT categoryID, categoryName FROM Category";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Category(
                        rs.getString("categoryID"),
                        rs.getString("categoryName")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return list;
    }

    private void closeResources() {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
