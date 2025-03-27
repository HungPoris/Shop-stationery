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
import model.Publisher;

/**
 *
 * @author conkg
 */
public class PublisherDAO {

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public Publisher getPublisherByBookID(int bookID) {
        Publisher publisher = null;
        String query = "SELECT p.publisherID, p.publisherName "
                + "FROM Publisher p "
                + "JOIN Book b ON p.publisherID = b.publisherID "
                + "WHERE b.bookID = ?";
        try ( Connection conn = new DBContext().getConnection();  PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, bookID);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    publisher = new Publisher(
                            rs.getString("publisherID"),
                            rs.getString("publisherName")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return publisher;
    }

    public List<Publisher> getAllPublishers() {
        List<Publisher> publishers = new ArrayList<>();
        String query = "SELECT publisherID, publisherName FROM Publisher";
        try ( Connection conn = new DBContext().getConnection();  PreparedStatement ps = conn.prepareStatement(query);  ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                publishers.add(new Publisher(
                        rs.getString("publisherID"),
                        rs.getString("publisherName")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return publishers;
    }

}
