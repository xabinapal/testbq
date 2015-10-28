import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet encargado de crear nuevos usuarios.
 */
@WebServlet("/user")
public class User extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public User() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * Método POST: crea un usuario con los datos recibidos del formulario.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter writer = response.getWriter();
		writer.println("<!doctype html>");
		
		String identifier = request.getParameter("identifier");
		int numericIdentifier = -1;
		Boolean isValidIdentifier;
		
		if (identifier.length() == 0 || identifier.length() > 11) {
			writer.append("Invalid identifier. ");
			isValidIdentifier = false;
		} else {
			try {
				numericIdentifier = Integer.parseUnsignedInt(identifier);
				isValidIdentifier = true;
			} catch (NumberFormatException nfe) {
				writer.append("Invalid identifier. ");
				isValidIdentifier = false;
			}
		}
		
		if (isValidIdentifier) {
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			
			Boolean identifierExists;
			try {
				identifierExists = checkIdentifier(numericIdentifier);
			} catch (Exception e) {
				e.printStackTrace(writer);
				return;
			}
			
			if (identifierExists) {
				writer.println("Identifier already exists. ");
			} else {
				Boolean userCreated = createUser(numericIdentifier, name, email, writer);
				
				if (userCreated) {
					sendMail(numericIdentifier, name, email);
					writer.println("User created. ");
				} else {
					writer.println("Error creating user.");
				}
			}
		}
		
		writer.println("<a href=\"index.html\">Go back</a>");
	}
	
	/**
	 * Comprueba si un identificador de usuario existe.
	 */
	public static Boolean checkIdentifier(int identifier) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		ResultSet rs = DatabaseHelper
				.getInstance()
				.executeQuery("SELECT 1 FROM `users` WHERE `id` = " + identifier + ";");
		
		Boolean exists = rs.next();
		rs.close();
		
		return exists;
	}
	
	/**
	 * Devuelve el nombre de un usuario según su identificador
	 */
	public static String getName(int identifier) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		ResultSet rs = DatabaseHelper
				.getInstance()
				.executeQuery("SELECT `name` FROM `users` WHERE `id` = " + identifier + ";");
		
		rs.next();
		
		String name = rs.getString(1);
		return name;
	}
	
	/**
	 * Devuelve el listado completo de usuarios.
	 */
	public static Map<Integer, String> getList() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		ResultSet rs = DatabaseHelper
				.getInstance()
				.executeQuery("SELECT `id`, `name` FROM `users`;");
		
		Map<Integer, String> users = new HashMap<Integer, String>();
		
		while (rs.next()) {
			int identifier = rs.getInt(1);
			String name = rs.getString(2);
			
			users.put(identifier, name);
		}
		
		return users;
	}

	/**
	 * Crea un usuario en la base de datos.
	 */
	private Boolean createUser(int identifier, String name, String email, PrintWriter writer) {
		// TODO: evitar inyección SQL en los parámetros
		
		try {
			DatabaseHelper
					.getInstance()
					.executeUpdate("INSERT INTO `users` VALUES (" + identifier + ", '" + name + "', '" + email + "');");
			return true;
		} catch (Exception e) {
			e.printStackTrace(writer);
			return false;
		}
	}
	
	/**
	 * Envía un email al usuario registrado.
	 */
	private void sendMail(int identifier, String name, String email) {
		
	}
}
