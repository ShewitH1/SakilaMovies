package com.pluralsight;

import com.pluralsight.models.Category;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        //  to make sure the user provided at least 3 arguments before the program tries to access!
        if (args.length < 3) {
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



    }

    private static List<Category> getAllCategories(BasicDataSource dataSource){

        //  make an Arraylist - List
        List<Category> categories = new ArrayList<>();

        String query = """
                select category_id, name
                from category
                """;

        //  connect to the database - use try with resources instead
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery()) {


            // 1. connection
            // 2. prepared statement
            // 3. result
            // 4. iterate
            while (resultSet.next()) {
                int id = resultSet.getInt("category_id");
                String name = resultSet.getString("name");
                Category c = new Category(id, name);
                categories.add(c);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categories;
    }
}