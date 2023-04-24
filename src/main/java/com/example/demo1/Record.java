package master;

import java.io.File;
import java.io.FileInputStream;

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

    //Connection

    public boolean SQLConnect() {
        try {
            String loadPath = "quiz.db";
            FileInputStream inFile = new FileInputStream(loadPath);
            Properties props = new Properties();
            props.load(inFile);

            connection = DriverManager.getConnection(props.getProperty("dbUrl"), props.getProperty("dbUser"), props.getProperty("dbPassword"));

            connection.prepareStatement("CREATE TABLE IF NOT EXISTS quiz(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT" +
                    "question VARCHAR(255) NOT NULL," +
                    "answer1 VARCHAR(255) NOT NULL," +
                    "answer2 VARCHAR(255)," +
                    "answer3 VARCHAR(255)," +
                    "category VARCHAR(255)," +
                    "difficulty INT," +
                    "power INT," +
                    "speed INT NOT NULL,").executeUpdate();

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }

    public boolean SQLDisconnect() {
        try {
            connection.close();
        }catch(Exception e){
            System.out.println(e);
            return false;
        }
        return true;
    }

    public String addRecord(Table q) {

        String query = "INSERT INTO quiz(question,answer1,answer2,answer3,category,difficulty,power,speed) " + "VALUES(?,?,?,?,?,?,?,?)";

		try {

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, q.getQuestion());
            preparedStatement.setString(2, q.getAnswer1());
            preparedStatement.setString(3, q.getAnswer2());
            preparedStatement.setString(4, q.getAnswer3());
            preparedStatement.setString(5, q.getCategory());
            preparedStatement.setInt(6, q.getDifficulty());
            preparedStatement.setInt(7, q.getPower());
            preparedStatement.setInt(8, q.getSpeed());
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
            setRs(stmt.executeQuery("SELECT * from quiz ORDER BY id asc;"));
            while(getRs().next()) {
                getResults().add(new Table(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getInt(7),
                        rs.getInt(8),
                        rs.getInt(9)));
            }
            return "Records Loaded.";

        } catch (SQLException e) {
            e.printStackTrace();
            return e.toString();
        }
    }

    public String editRecord(Table q) {

        String query = "UPDATE quiz SET "
                + "question=?, "
                + "answer1=?, "
                + "answer2=?, "
                + "answer3=? "
                + "category=? "
                + "difficulty=? "
                + "power=? "
                + "speed=? "
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
            preparedStatement.setInt(8, q.getSpeed());
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

            String query = "DELETE FROM quiz WHERE id=?;";
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
}
