

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MessageExport
 */
@WebServlet("/export.csv")
public class MessageExport extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MessageExport() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * Método GET: exporta un listado de mensajes a csv
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/csv");
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
		List<MessageModel> messages = null;
		try {
			if (isValidIdentifier) {
				messages = MessageList.getUserMessages(numericIdentifier);
			} else {
				messages = MessageList.getAllMessages();
			}
		} catch (Exception e) {
			e.printStackTrace(writer);
		}

		if (messages != null) {
			messages.forEach(m -> {
				writer.println(m.getUser() + "," + m.getMessage());
			});
		}
	}
}
