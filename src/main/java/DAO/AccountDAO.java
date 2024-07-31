package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Model.Account;
import Util.ConnectionUtil;

public class AccountDAO {
    public List<Account> getAllAccounts() {
        Connection connection = ConnectionUtil.getConnection();
        List<Account> allAcc = new ArrayList<>();

        try {
            String sql = "SELECT * FROM Account";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Account acc = new Account(
                    rs.getInt("account_id"),
                    rs.getString("username"),
                    rs.getString("password")
                );
                allAcc.add(acc);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return allAcc;
    }

    public Account registerAccount(Account acc) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO account (username, password) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, acc.getUsername());
            preparedStatement.setString(2, acc.getPassword());

            preparedStatement.executeUpdate();
            
            ResultSet primaryKeyResultSet = preparedStatement.getGeneratedKeys();
            if (primaryKeyResultSet.next()) {
                int generated_acc_id = (int) primaryKeyResultSet.getLong(1);
                return new Account(generated_acc_id, acc.getUsername(), acc.getPassword());
            }
    
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public Account updateAccount(int Account_id) {
        return null;
    }

    public Account getAccountByID(int Account_id) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM account WHERE account_id = (?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, Account_id);
            
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Account acc = new Account(
                    rs.getInt("account_id"),
                    rs.getString("username"),
                    rs.getString("password")
                );
                return acc;
            }
    
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public Account getAccountByUsernameAndPassword(String Username, String Password) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM account WHERE (username = ?) AND (password = ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, Username);
            preparedStatement.setString(2, Password);
            
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Account acc = new Account(
                    rs.getInt("account_id"),
                    rs.getString("username"),
                    rs.getString("password")
                );
                return acc;
            }
    
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

}
