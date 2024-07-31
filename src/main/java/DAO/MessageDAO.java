package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Model.Message;
import Util.ConnectionUtil;

public class MessageDAO {
    public Message createMessage(Message msg) {
        Connection connection = ConnectionUtil.getConnection();
        
        try {
            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) values (?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, msg.getPosted_by());
            preparedStatement.setString(2, msg.getMessage_text());
            preparedStatement.setLong(3, msg.getTime_posted_epoch());
            //System.currentTimeMillis()

            preparedStatement.executeUpdate();
            //test this
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                int generated_msg_id = (int) rs.getLong(1);
                return new Message(generated_msg_id, msg.getPosted_by(), msg.getMessage_text(), msg.getTime_posted_epoch());
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Message> getAllMessages() {
        Connection connection = ConnectionUtil.getConnection();
        List<Message> allMsgs = new ArrayList<>(); 

        try {
            String sql = "SELECT * FROM message";
            
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Message msg = new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch")
                );
                allMsgs.add(msg);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return allMsgs;
    }

    public Message getMessageByID(int message_id) {
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "SELECT * FROM message WHERE message_id = (?)";
            
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, message_id);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Message msg = new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch")
                );
                return msg;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public void deleteMessageByID(int message_id) {
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "DELETE FROM message WHERE message_id = (?)";
            
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, message_id);

            preparedStatement.executeQuery();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public Message updateMessageByID(int message_id, String msg) {
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "UPDATE message SET message_text = (?) WHERE message_id = (?)";
            
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, msg);
            preparedStatement.setInt(2, message_id);

            preparedStatement.executeUpdate();
            

            sql = "SELECT * FROM message WHERE message_id = (?)";
            
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, message_id);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Message newMsg = new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch")
                );
                return newMsg;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public List<Message> getAllMessagesForAccID(int account_id) {
        Connection connection = ConnectionUtil.getConnection();
        List<Message> allMsgs = new ArrayList<>(); 

        try {
            String sql = "SELECT * FROM message WHERE posted_by = (?)";
            
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, account_id);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Message msg = new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch")
                );
                allMsgs.add(msg);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return allMsgs;
    }
    
}
