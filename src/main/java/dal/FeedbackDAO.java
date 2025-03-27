/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import model.Feedback;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import utils.DBContext;

public class FeedbackDAO extends DBContext {

    DBContext dbcontext = new DBContext();

    // Constructor không tham số, tự động mở kết nối
    public FeedbackDAO() {
    }

    // Các phương thức khác...
    public List<Feedback> getAllFeedbacks(int bookID) {
        List<Feedback> feedbackList = new ArrayList<>();
        CustomerDAO customerDAO = new CustomerDAO();
        String sql = "SELECT	*\n"
                + "FROM	[dbo].[Feedback]\n"
                + "WHERE	[dbo].[Feedback].[bookID] = ?\n"
                + "ORDER BY created_at DESC";
        Object[] params = {bookID};
        try ( ResultSet rs = dbcontext.exeQueryAlt(sql, params)) {
            while (rs.next()) {
                Feedback feedback = new Feedback(
                        customerDAO.getCustomerInfoByID(rs.getString("accountID")),
                        rs.getInt("bookID"),
                        rs.getString("content"),
                        rs.getDate("created_at")
                );
                feedbackList.add(feedback);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return feedbackList;
    }

    public void addFeedback(String accountID, String bookID, String content) throws SQLException {
        System.out.println("add Feed Back:" + accountID + " " + " " + bookID + " " + content);
        String sql = "INSERT INTO Feedback (accountID, bookID, content) VALUES (?, ?, ?)";
        Object[] params = {
            accountID, bookID, content
        };
        dbcontext.exeNonQuery(sql, params);
    }
}
