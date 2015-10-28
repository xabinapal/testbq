import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet encargado de mostrar el listado de mensajes.
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
	 * Método GET: Devuelve el listado de mensajes completo o filtrado por usuario.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter writer = response.getWriter();
		writer.println("<!doctype html>");
		
		String identifier = request.getParameter("identifier");
		int numericIdentifier = -1;
		Boolean isValidIdentifier;
		
		if (identifier == null || identifier.length() == 0 || identifier.length() > 11) {
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
			try {
				isValidIdentifier = User.checkIdentifier(numericIdentifier);
			} catch (Exception e) {
				e.printStackTrace(writer);
				return;
			}
		}
		
		// Mostrar listado de usuarios
		writer.println("<form method=\"get\">");
		writer.println("<select name=\"identifier\">");
		writer.println("<option value=\"-1\">All</option>");
		
		Map<Integer, String> users = User.getList();
		users.forEach((k,v) -> {
			writer.println("<option value=\"" + k + "\">" + v + "</option>");
		});
		
		writer.println("</select>");
		writer.println("<input type=\"submit\" value=\"View\">");
		writer.println("</form>");


		// Mostrar listado de mensajes
		
		List<MessageModel> messages;
		if (isValidIdentifier) {
			writer.println("<h1>Messages " + User.getName(numericIdentifier) + "</h1>");
			messages = getUserMessages(numericIdentifier);
		} else {
			writer.println("<h1>Messages</h1>");
			
			messages = getAllMessages();
		}

		if (messages.isEmpty()) {
			writer.println("No messages");
		} else {
			writer.println("<ul>");
			
			messages.forEach(m -> {
				writer.println("<li>");
				writer.println("<b>" + m.getUser() + ":</b> " + m.getMessage());
				writer.println("</li>");
			});
		}

		writer.println("</ul>");
	}

	private List<MessageModel> getUserMessages(int identifier) {
		return new ArrayList<MessageModel>();
	}
	
	private List<MessageModel> getAllMessages() {
		return new ArrayList<MessageModel>();
	}
}
