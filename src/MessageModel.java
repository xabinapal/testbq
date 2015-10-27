/**
 * Modelo de datos de la base de datos con la información de un mensaje.
 */
public class MessageModel {
	private String user;
	private String message;
	
	public MessageModel(String user, String message) {
		this.user = user;
		this.message = message;
	}
	
	public String getUser() {
		return this.user;
	}
	
	public String getMessage() {
		return this.message;
	}
}
