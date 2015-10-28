import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Singleton clase para hacer más sencillas las peticiones a la base de datos.
 * @author xabito
 *
 */
public class DatabaseHelper {
	private final String url = "jdbc:mysql://localhost:3306/testbq";
	private final String user = "root";
	private final String password = "qwerty";
	
	private Connection connection;
	
	private static DatabaseHelper instance;
	
	private DatabaseHelper() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		this.connection = DriverManager.getConnection(url, user, password);
	}
	
	public static DatabaseHelper getInstance() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		if (DatabaseHelper.instance == null) {
			DatabaseHelper.instance = new DatabaseHelper();
		}
		
		return DatabaseHelper.instance;
	}
	
	public ResultSet executeQuery(String query) throws SQLException {
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(query);
		return resultSet;
	}
}
