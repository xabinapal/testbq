import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
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
		
		try {
			Map<Integer, String> users = User.getList();

			writer.println("<form method=\"get\">");
			
			writer.println("<select name=\"identifier\">");
			writer.println("<option value=\"-1\">All</option>");
			users.forEach((k,v) -> {
				writer.println("<option value=\"" + k + "\">" + v + "</option>");
			});

			writer.println("</select>");
			writer.println("<input type=\"submit\" value=\"View\">");
			writer.println("</form>");
		} catch (Exception e) {
			e.printStackTrace(writer);
		} 

		// Mostrar listado de mensajes
		
		List<MessageModel> messages = null;
		if (isValidIdentifier) {
			String name = "";
			try {
				name = User.getName(numericIdentifier);
			} catch (Exception e) {
				e.printStackTrace(writer);
			}
			
			writer.println("<h1>Messages " + name + "</h1>");
			try {
				messages = getUserMessages(numericIdentifier);
			} catch (Exception e) {
				e.printStackTrace(writer);
			}
		} else {
			writer.println("<h1>Messages</h1>");
			
			try {
				messages = getAllMessages();
			} catch (Exception e) {
				e.printStackTrace(writer);
			}
		}

		if (messages != null) {
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
		}

		writer.println("</ul>");

		writer.println("<a href=\"index.html\">Go back</a>");
	}

	private List<MessageModel> getUserMessages(int identifier) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		ResultSet rs = DatabaseHelper
				.getInstance()
				.executeQuery("SELECT `a`.`name`, `b`.`message` FROM `users` AS `a` INNER JOIN `messages` AS `b` ON `a`.`id` = `b`.`user` WHERE `a`.`id` = " + identifier + " ORDER BY `b`.`date` DESC;");
		
		return getMessages(rs);
	}
	
	private List<MessageModel> getAllMessages() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		ResultSet rs = DatabaseHelper
				.getInstance()
				.executeQuery("SELECT `a`.`name`, `b`.`message` FROM `users` AS `a` INNER JOIN `messages` AS `b` ON `a`.`id` = `b`.`user` ORDER BY `b`.`date` DESC;");
		
		return getMessages(rs);
	}
	
	private List<MessageModel> getMessages(ResultSet rs) throws SQLException {
		List<MessageModel> messages = new ArrayList<MessageModel>();
		
		while (rs.next()) {
			String user = rs.getString(1);
			String message = rs.getString(2);
			
			MessageModel msg = new MessageModel(user, message);
			messages.add(msg);
		}
		
		return messages;
	}
}
