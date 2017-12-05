/* Creates all the tables on initialize if they dont exist.
 * 
 */
package servlets;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.annotation.Resource;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

/**
 * Application Lifecycle Listener implementation class DatabaseManager
 *
 */
@WebListener
public class DatabaseManager implements ServletContextListener {

	@Resource(name="jdbc/database")
	private DataSource dataSource;
	
	/**
	 * Default constructor. 
	 */
	public DatabaseManager() {

	}

	/**
	 * @see ServletContextListener#contextDestroyed(ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent arg0)  { 
		/*	try {
			DriverManager.getConnection("jdbc:derby:C:\\Users\\Jeremiah\\Dropbox\\Eclipse_Workspace\\COM3JeremiahCotterOOServerSideAss2\\WebContent\\WEB-INF\\database;shutdown=true");
			System.out.println("Database Shutdown");
    	} catch (SQLException e) {
			e.printStackTrace();
		}*/
	}

	/**
	 * @see ServletContextListener#contextInitialized(ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent arg0)  {
		Connection con = null;
		try {
			con = dataSource.getConnection();
			DatabaseMetaData dbm = con.getMetaData();
			ResultSet tables = dbm.getTables(null, null, "account", null);
			if (!tables.next()) {
				Statement stmt = con.createStatement(); 
			    stmt.executeUpdate("CREATE TABLE account ("
			    		+ "id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
			    		+ "name VARCHAR(45) NOT NULL,"
			    		+ "password VARCHAR(45) NOT NULL,"
			    		+ "register BIGINT NOT NULL,"
			    		+ "messages INTEGER NOT NULL,"
			    		+ "type INTEGER NOT NULL,"
			    		+ "PRIMARY KEY(id),"
			    		+ "UNIQUE(name))");
			    String query = "INSERT INTO account (name, password, register, messages, type) VALUES('Jer', '', " + System.currentTimeMillis() + ", 0, 0)";
				Statement stmt2 = con.createStatement();
				stmt2.execute(query);
			}
			tables = dbm.getTables(null, null, "forum", null);
			if (!tables.next()) {
				Statement stmt = con.createStatement(); 
			    stmt.executeUpdate("CREATE TABLE forum ("
			    		+ "id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
			    		+ "name VARCHAR(45) NOT NULL,"
			    		+ "type INTEGER NOT NULL,"
			    		+ "PRIMARY KEY(id),"
			    		+ "UNIQUE(name))");
			    String query = "insert into forum (NAME, TYPE) values('Main Forum', 0)";
				Statement stmt2 = con.createStatement();
				stmt2.execute(query);
				query = "insert into forum (NAME, TYPE) values('Shit Talk', 0)";
			    stmt2.execute(query);
			    query = "insert into forum (NAME, TYPE) values('All Abourd', 0)";
			    stmt2.execute(query);
			}
			tables = dbm.getTables(null, null, "thread", null);
			if (!tables.next()) {
				Statement stmt = con.createStatement(); 
			    stmt.executeUpdate("CREATE TABLE thread ("
			    		+ "id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
			    		+ "name VARCHAR(45) NOT NULL,"
			    		+ "forumId INTEGER references forum(id),"
			    		+ "PRIMARY KEY(id))");
			}
			tables = dbm.getTables(null, null, "post", null);
			if (!tables.next()) {
				Statement stmt = con.createStatement(); 
			    stmt.executeUpdate("CREATE TABLE post ("
			    		+ "id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
			    		+ "post VARCHAR(2000) NOT NULL,"
			    		+ "postTime BIGINT NOT NULL,"
			    		+ "threadId INTEGER references thread(id),"
			    		+ "posterId INTEGER references account(id),"
			    		+ "PRIMARY KEY(id))");
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(con!=null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
