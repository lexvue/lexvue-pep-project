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

        String sql = "SELECT * FROM account";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet rs = preparedStatement.executeQuery();

        return allAcc;

    }
}
