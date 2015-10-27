

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MessageList
 */
@WebServlet("/messageList")
public class MessageList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MessageList() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * Devuelve el listado de mensajes completo o filtrado por usuario.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter writer = response.getWriter();
		writer.println("<!doctype html>");
		
		String identifier = request.getParameter("identifier");
		int numericIdentifier = -1;
		Boolean isValidIdentifier;
		
		if (identifier.length() == 0 || identifier.length() > 11) {
			isValidIdentifier = false;
		} else {
			try {
				numericIdentifier = Integer.parseUnsignedInt(identifier);
				isValidIdentifier = true;
			} catch (NumberFormatException nfe) {
				isValidIdentifier = false;
			}
		}
		
		if (isValidIdentifier) {
			isValidIdentifier = User.checkIdentifier(numericIdentifier);
		}
		
		List<MessageModel> messages;
		
		if (isValidIdentifier) {
			messages = getUserMessages(numericIdentifier);
		} else {
			messages = getAllMessages();
		}

		writer.println("<ul>");
		
		for (MessageModel message : messages) {
			//TODO: controlar inyección HTML, escapando caracteres
			
			writer.println("<li>");
			writer.println("<b>" + message.getUser() + ":</b> " + message.getMessage());
			writer.println("</li>");
		}

		writer.println("</ul>");
	}

	private List<MessageModel> getUserMessages(int identifier) {
		return null;
	}
	
	private List<MessageModel> getAllMessages() {
		return null;
	}
}
