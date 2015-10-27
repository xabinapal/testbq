import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet crear nuevos mensajes.
 */
@WebServlet("/Message")
public class Message extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Message() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * Método POST: recibe un mensaje y lo almacena en caso de provenir de un usuario válido.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter writer = response.getWriter();
		writer.println("<!doctype html>");
		
		String identifier = request.getParameter("identifier");
		int numericIdentifier = -1;
		Boolean isValidIdentifier;
		
		if (identifier == null || identifier.length() == 0 || identifier.length() > 11) {
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
			String message = request.getParameter("message");
			saveMessage(numericIdentifier, message);
		} else {
			logError(identifier);
		}
	}
	
	private void saveMessage(int identifier, String message) {
	
	}
	
	private void logError(String identifier) {
		// TODO: podría almacenarse la IP desde la que se ha producido el error.
	}
}
