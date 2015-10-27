import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class User
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
			
			Boolean identifierExists = checkIdentifier(numericIdentifier);
			
			if (identifierExists) {
				writer.println("Identifier already exists. ");
			} else {
				Boolean userCreated = createUser(numericIdentifier, name, email);
				
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
	public static Boolean checkIdentifier(int identifier) {
		return true;
	}

	/**
	 * Crea un usuario en la base de datos.
	 */
	private Boolean createUser(int identifier, String name, String email) {
		return true;
	}
	
	/**
	 * Envía un email al usuario registrado.
	 */
	private void sendMail(int identifier, String name, String email) {
		
	}
}
