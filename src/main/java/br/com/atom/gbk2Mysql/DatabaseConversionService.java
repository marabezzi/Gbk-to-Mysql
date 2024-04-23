package br.com.atom.gbk2Mysql;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatabaseConversionService {
	
	 @Autowired
	    private DataSource firebirdDataSource;

	    @Autowired
	    private DataSource mySQLDataSource;

	    public void convertFirebirdToMySQL(String firebirdBackupFilePath) {
	        try (Connection firebirdConn = firebirdDataSource.getConnection();
	             Connection mysqlConn = mySQLDataSource.getConnection()) {
	            extractSchemaAndDataFromFirebirdBackup(firebirdConn, mysqlConn, firebirdBackupFilePath);
	            System.out.println("Conversão concluída com sucesso!");
	        } catch (SQLException | IOException e) {
	            e.printStackTrace();
	        }
	    }

	    private void extractSchemaAndDataFromFirebirdBackup(Connection firebirdConn, Connection mysqlConn, String firebirdBackupFilePath) throws SQLException, IOException {
	        try (BufferedReader reader = new BufferedReader(new FileReader(firebirdBackupFilePath))) {
	            StringBuilder createTableQuery = new StringBuilder();
	            StringBuilder insertDataQuery = new StringBuilder();
	            String line;
	            while ((line = reader.readLine()) != null) {
	                if (line.startsWith("CREATE TABLE")) {
	                    createTableQuery.setLength(0);
	                    createTableQuery.append(line);
	                    while (!line.endsWith(";")) {
	                        line = reader.readLine();
	                        createTableQuery.append(" ").append(line.trim());
	                    }
	                    createMySQLTable(mysqlConn, createTableQuery.toString());
	                } else if (line.startsWith("INSERT INTO")) {
	                    insertDataQuery.setLength(0);
	                    insertDataQuery.append(line);
	                    while (!line.endsWith(";")) {
	                        line = reader.readLine();
	                        insertDataQuery.append(" ").append(line.trim());
	                    }
	                    transferDataFromFirebirdToMySQL(mysqlConn, insertDataQuery.toString());
	                }
	            }
	        }
	    }

	    private void createMySQLTable(Connection mysqlConn, String createTableQuery) throws SQLException {
	        try (Statement statement = mysqlConn.createStatement()) {
	            statement.executeUpdate(createTableQuery);
	        }
	    }

	    private void transferDataFromFirebirdToMySQL(Connection mysqlConn, String insertDataQuery) throws SQLException {
	        try (Statement statement = mysqlConn.createStatement()) {
	            statement.executeUpdate(insertDataQuery);
	        }
	    }

}
