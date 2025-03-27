/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Report;
import utils.DBContext;

/**
 *
 * @author conkg
 */
public class ReportDAO {

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    // Hàm lấy tất cả báo cáo
    public boolean addReport(String imagePath, String description) {
        String query = "INSERT INTO [dbo].[Report]\n"
                + "           ([accountID]\n"
                + "           ,[image_path]\n"
                + "           ,[description])\n"
                + "     VALUES (?, ?, ?)";

        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, "ACC001");  // Gán mặc định accountID = ACC001
            ps.setString(2, imagePath);
            ps.setString(3, description);

            return ps.executeUpdate() > 0; // Trả về true nếu thêm thành công
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Report> getAllReports() {
        List<Report> list = new ArrayList<>();
        String query = "SELECT * FROM Report";

        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Report(
                        rs.getInt("reportID"),
                        rs.getString("accountID"),
                        rs.getString("image_path"),
                        rs.getString("description")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
