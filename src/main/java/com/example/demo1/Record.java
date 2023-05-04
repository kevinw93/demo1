package com.example.demo1;


import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

public class Record {

    private Connection connection;
    private ArrayList<Table> results;
    private ResultSet rs;

    //Getters and Setters
    public ArrayList<Table> getResults() {
        return results;
    }
    public void setResults(ArrayList<Table> results) {
        this.results = results;
    }
    public ResultSet getRs() {
        return rs;
    }
    public void setRs(ResultSet rs) {
        this.rs = rs;
    }

    public Record() {
        super();
        this.setResults(new ArrayList<Table>());

    }



    //Finding JDBC connection to schema
    public boolean findConnection() {
        try {

            //Getting the connection
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/quiz", "root", "R3by$ound");

            //Try-catch to catch for errors
            try {

                //SQL statement to create table if one does not exist
                connection.prepareStatement("CREATE TABLE IF NOT EXISTS `questionTable`(\n" +
                        "    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,\n" +
                        "    `question` TEXT NOT NULL,\n" +
                        "    `answer1` VARCHAR(255) NOT NULL,\n" +
                        "    `answer2` VARCHAR(255),\n" +
                        "    `answer3` VARCHAR(255),\n" +
                        "    `category` VARCHAR(255) NOT NULL,\n" +
                        "    `difficulty` INT NOT NULL,\n" +
                        "    `power` INT\n" +
                        ");").executeUpdate();

            } catch (Exception e) {

                //Printing error if there is one when creating table
                e.printStackTrace();
            }


        } catch (Exception e) {

            //Printing error if there is an error connecting
            System.out.println(e);
            return false;
        }
        return true;
    }

    //Disconnecting from the database
    public boolean disconnect() {

        //Try-catch to catch for errors
        try {
            connection.close();

        }catch(Exception e){

            //Printing error back to user if there is one
            System.out.println(e);
            return false;
        }
        return true;
    }


    //Adding a record to questionTable
    public String addRecord(Table q) {

        String query = "INSERT INTO questionTable(question,answer1,answer2,answer3,category,difficulty,power) " + "VALUES(?,?,?,?,?,?,?)";

		try {

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, q.getQuestion());
            preparedStatement.setString(2, q.getAnswer1());
            preparedStatement.setString(3, q.getAnswer2());
            preparedStatement.setString(4, q.getAnswer3());
            preparedStatement.setString(5, q.getCategory());
            preparedStatement.setInt(6, q.getDifficulty());
            preparedStatement.setInt(7, q.getPower());
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            return e.toString();
        }
        loadResults();
		return "Record Added.";
    }

    public String loadResults() {

        //Here is another way to execute a 'statement on a database'
        try {
            getResults().clear();
            Statement stmt = connection.createStatement();
            setRs(stmt.executeQuery("SELECT * from questionTable ORDER BY id asc;"));
            while(getRs().next()) {
                getResults().add(new Table(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getInt(7),
                        rs.getInt(8)));
            }
            return "Records Loaded.";

        } catch (SQLException e) {
            e.printStackTrace();
            return e.toString();
        }
    }

    public String editRecord(Table q) {

        String query = "UPDATE questionTable SET "
                + "question=?, "
                + "answer1=?, "
                + "answer2=?, "
                + "answer3=?, "
                + "category=?, "
                + "difficulty=?, "
                + "power=? "
                + "WHERE id=?;";

        try {

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, q.getQuestion());
            preparedStatement.setString(2, q.getAnswer1());
            preparedStatement.setString(3, q.getAnswer2());
            preparedStatement.setString(4, q.getAnswer3());
            preparedStatement.setString(5, q.getCategory());
            preparedStatement.setInt(6, q.getDifficulty());
            preparedStatement.setInt(7, q.getPower());
            preparedStatement.setInt(8, q.getId());
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            return e.toString();
        }
        loadResults();
        return "Record Updated";
    }

    public String deleteRecord(Table q) {
        try {

            String query = "DELETE FROM questionTable WHERE id=?;";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,  q.getId());
            preparedStatement.executeUpdate();

        }catch(Exception e) {
            e.printStackTrace();
            return e.toString();
        }
        loadResults();
        return "Record Deleted.";
    }

    public String selectRecord(Table q) {
        try {

            String query = "SELECT FROM questionTable WHERE id=?;";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,  q.getId());
            preparedStatement.executeUpdate();

        }catch(Exception e) {
            e.printStackTrace();
            return e.toString();
        }
        loadResults();
        return "Record Selected.";
    }


}
