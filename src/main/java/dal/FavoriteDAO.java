/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

/**
 *
 * @author Tran Viet Minh - CE182526
 */
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import utils.DBContext;

public class FavoriteDAO {

    DBContext dBContext = new DBContext();

    // Thêm sách yêu thích
    public boolean addFavorite(String accountID, int bookID) throws SQLException {
        String sql = "INSERT INTO Favorite (accountID, bookID) VALUES (?, ?)";
        Object[] params = {
            accountID, bookID
        };
        return dBContext.exeNonQuery(sql, params) > 0;
    }

    // Xóa sách yêu thích
    public boolean removeFavorite(String accountID, int bookID) throws SQLException {
        String sql = "DELETE FROM Favorite WHERE accountID = ? AND bookID = ?";
        Object[] params = {
            accountID, bookID
        };
        return dBContext.exeNonQuery(sql, params) > 0;
    }

    // Lấy danh sách sách yêu thích của một tài khoản
    public List<Integer> getFavoriteBooks(String accountID) {
        List<Integer> bookList = new ArrayList<>();
        String sql = "SELECT bookID FROM Favorite WHERE accountID = ?";
        Object[] params = {
            accountID
        };
        try {
            ResultSet rs = dBContext.exeQueryAlt(sql, params);
            while (rs.next()) {
                bookList.add(rs.getInt("bookID"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookList;
    }

    // Kiểm tra xem sách đã có trong danh sách yêu thích chưa
    public boolean isFavorite(String accountID, int bookID) {
        String sql = "SELECT * FROM Favorite WHERE accountID = ? AND bookID = ?";
        Object[] params = {
            accountID, bookID
        };
        try {

            ResultSet rs = dBContext.exeQueryAlt(sql, params);
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
