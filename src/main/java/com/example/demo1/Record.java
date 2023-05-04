/*
Kevin Wang
3 May 2023
AP Computer Science A
2nd Period
Master Project
Database Class
 */
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

    //Fields of the class
    private Connection connection;
    private ArrayList<Table> results;
    private ResultSet rs;

    //Getters and Setters

    //Getter for results array list
    public ArrayList<Table> getResults() {
        return results;
    }

    //Setter for results array list
    public void setResults(ArrayList<Table> results) {
        this.results = results;
    }

    //Getter for result set
    public ResultSet getRs() {
        return rs;
    }

    //Setter for result set
    public void setRs(ResultSet rs) {
        this.rs = rs;
    }

    //Constructors
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

        //Query to insert into table
        String query = "INSERT INTO questionTable(question,answer1,answer2,answer3,category,difficulty,power) " + "VALUES(?,?,?,?,?,?,?)";

		try {

            //Getting values to insert
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, q.getQuestion());
            preparedStatement.setString(2, q.getAnswer1());
            preparedStatement.setString(3, q.getAnswer2());
            preparedStatement.setString(4, q.getAnswer3());
            preparedStatement.setString(5, q.getCategory());
            preparedStatement.setInt(6, q.getDifficulty());
            preparedStatement.setInt(7, q.getPower());
            preparedStatement.executeUpdate();

        //Returning error if there is one
        } catch (Exception e) {
            e.printStackTrace();
            return e.toString();
        }

        loadResults();
		return "Record Added.";
    }

    public String loadResults() {

        //Try-catch to catch for errors
        try {
            getResults().clear();
            Statement stmt = connection.createStatement();

            //Query to select all from table
            setRs(stmt.executeQuery("SELECT * from questionTable ORDER BY id asc;"));
            while(getRs().next()) {

                //Getting records from result set and adding into array list
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

        //Printing errors back to user if there are any
        } catch (SQLException e) {
            e.printStackTrace();
            return e.toString();
        }
    }

    //Editing/updating a record in questionTable
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

    //Deleting a record from question table
    public String deleteRecord(Table q) {
        try {

            //Query to delete record
            String query = "DELETE FROM questionTable WHERE id=?;";

            //Getting id of record to delete
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,  q.getId());
            preparedStatement.executeUpdate();

        //Returning error if there is one
        }catch(Exception e) {
            e.printStackTrace();
            return e.toString();
        }
        loadResults();
        return "Record Deleted.";
    }

    //Selecting records from question table with a certain category
    public String categoryRecord(Table q) {
        try {

            //Query to select a specific record
            String query = "SELECT FROM questionTable WHERE category=?;";

            //Getting category of records to be selected
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,  q.getCategory());
            preparedStatement.executeUpdate();

        //Returning error if there is one
        }catch(Exception e) {
            e.printStackTrace();
            return e.toString();
        }
        loadResults();
        return "Record Selected.";
    }


}
