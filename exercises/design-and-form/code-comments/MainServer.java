package ch.epfl.sweng.coding_style.exercise;

import java.io.IOException;

/**
 * Main class running the server.
 * 
 * The Server simply listens and respond to the requests from the client
 * indefinitely.
 * 
 * @author John Doe
 */
public class MainServer {
	public static void main(String[] args) throws IOException {
		Server server = new Server();
		System.out.println("Server instance created");

		while (true) {
			server.listenAndReply();
		}
	}
}
