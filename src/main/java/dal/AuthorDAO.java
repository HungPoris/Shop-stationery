/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import utils.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Author;
import model.Book;

/**
 *
 * @author conkg
 */
public class AuthorDAO {

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public List<Author> getbookbyAuthor(String bookID) {
        List<Author> list = new ArrayList<>();
        String query = "SELECT f.authorID, f.authorName FROM Author f "
                + "JOIN Book_Author h ON f.authorID = h.authorID "
                + "WHERE h.bookID = ?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, bookID);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Author(
                        rs.getString("authorID"),
                        rs.getString("authorName")
                ));
            }
        } catch (Exception e) {
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
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}
