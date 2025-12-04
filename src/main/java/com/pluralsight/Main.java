package com.pluralsight;

import com.pluralsight.models.Category;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        //  to make sure the user provided at least 3 arguments before the program tries to access!
        if(args.length < 3){
            System.out.println("You need to provide a username, password, and URL when running this command.");
            System.out.println("For example:");
            System.out.println("Main.exe myUsername myPassword myURL");
            return;
        }

        //  Get username, Password, URL
        String username = args[0];
        String password = args[1];
        String URL = args[2];

        //make datasource instead of driver manager
        BasicDataSource dataSource = new BasicDataSource();

        //configure the datasource
        dataSource.setUrl(URL);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        // make a query
        String query = "select productid, productname, unitprice, unitsinstock from products";


        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()){
            //connect to the database

            // 1. connection
            // 2. prepared statement
            // 3. result
            // 4. iterate
            while(resultSet.next()){
                String productid = resultSet.getString("productid");
                String productname = resultSet.getString("productname");
                int unitprice = resultSet.getInt("unitprice");
                int unitsinstock = resultSet.getInt("unitsinstock");

                System.out.printf("%-10s %30s %30s %20d\n", productid, productname, unitprice, unitsinstock);

            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}