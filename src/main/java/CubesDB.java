

import java.sql.*;
import java.util.ArrayList;

/**
 This class makes connection to the database and
 performs various actions.
 */
public class CubesDB {
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";        //Configure the driver needed
    private static final String DB_CONNECTION_URL = "jdbc:mysql://localhost:3306/cubes";     //Connection to the database
    private static final String USER = "khatiwoda";   //MYSQL username
    private static final String PASSWORD = "Saanvi";   //MYSQL password
    private static final String TABLE_NAME = "cubes"; //table name
    private static final String CUBE_SOLVER_COLUMN = "CubeSolver";//column name
    private static final String TIME_TAKEN_COLUMN = "TimeTaken";//column name
    //constructor
    CubesDB() {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Can't instantiate driver class; check you have drives and classpath configured correctly?");
            cnfe.printStackTrace();
            System.exit(-1);  //quit the program
        }
    }
    //defines method
    void createTable() {

        try (Connection connection = DriverManager.getConnection(DB_CONNECTION_URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {

            String createSQLTableTemplate = "CREATE TABLE IF NOT EXISTS %s (%s VARCHAR (50), %s DOUBLE)";
            String createSQLTable = String.format(createSQLTableTemplate, TABLE_NAME, CUBE_SOLVER_COLUMN, TIME_TAKEN_COLUMN);

            statement.executeUpdate(createSQLTable);

            statement.close();
            connection.close();

        } catch (SQLException se) {
            se.printStackTrace();
        }
    }
    //defines method that add records to the database
    void addRecord(Cubes cubes)  {

        try (Connection connection = DriverManager.getConnection(DB_CONNECTION_URL, USER, PASSWORD)) {

            String addSQLcubes = "INSERT INTO " + TABLE_NAME + " VALUES ( ? , ? ) " ;
            PreparedStatement addSQLcubesRecord = connection.prepareStatement(addSQLcubes);
            addSQLcubesRecord.setString(1, cubes.name);
            addSQLcubesRecord.setDouble(2, cubes.time);

            addSQLcubesRecord.execute();

            //TO DO add a message dialog box with "Added cube solver record for 'cubesolver name'" message.

            addSQLcubesRecord.close();
            connection.close();

        } catch (SQLException se) {
            se.printStackTrace();
        }

    }
    //defines method that deletes data from the database
    void delete(Cubes cubes){
        try(Connection connection = DriverManager.getConnection(DB_CONNECTION_URL, USER, PASSWORD)){
            String deleteSQLcubes = "DELETE FROM %s WHERE %s = ? AND %s = ?";
            String deleteSQLcubesRecord = String.format(deleteSQLcubes,TABLE_NAME,CUBE_SOLVER_COLUMN,TIME_TAKEN_COLUMN);
            PreparedStatement deletePreparedStatement = connection.prepareStatement(deleteSQLcubesRecord);
            deletePreparedStatement.setString(1,cubes.name);
            deletePreparedStatement.setDouble(2,cubes.time);
            deletePreparedStatement.execute();
            deletePreparedStatement.close();
            connection.close();
        }catch (SQLException sqle){
            sqle.printStackTrace();
        }
    }
    //defines method that updates record in the database.
    void updateRecord(Cubes cubes){
        try(Connection connection = DriverManager.getConnection(DB_CONNECTION_URL,USER,PASSWORD)){
            String updateSQLcubes = "UPDATE %s SET %s = ? WHERE %s = ?";
            String updateSQLcubesRecord = String.format(updateSQLcubes,TABLE_NAME,TIME_TAKEN_COLUMN,CUBE_SOLVER_COLUMN);
            PreparedStatement updatePreparedStatement = connection.prepareStatement(updateSQLcubesRecord);
            updatePreparedStatement.setDouble(1,cubes.time);
            updatePreparedStatement.setString(2,cubes.name);
            updatePreparedStatement.execute();
            updatePreparedStatement.close();
            connection.close();
        }catch (SQLException se){
            se.printStackTrace();
        }
    }
    //defines method that displays record from the database
    ArrayList<Cubes> fetchAllRecords() {
        ArrayList<Cubes> allRecords = new ArrayList();
        try (Connection connection = DriverManager.getConnection(DB_CONNECTION_URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {
            String selectSQLtable = "SELECT * FROM " + TABLE_NAME;
            ResultSet rs = statement.executeQuery(selectSQLtable);
            while (rs.next()) {
                String name = rs.getString(CUBE_SOLVER_COLUMN);
                double time = rs.getDouble(TIME_TAKEN_COLUMN);
                Cubes cubeSolverRecord = new Cubes(name, time);
                allRecords.add(cubeSolverRecord);}
            rs.close();
            statement.close();
            connection.close();
            return allRecords;
        } catch (SQLException se) {
            se.printStackTrace();
            return null;
             }
    }
}
