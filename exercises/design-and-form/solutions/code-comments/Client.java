package ch.epfl.sweng.coding_style.solution;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import static ch.epfl.sweng.coding_style.ex1.Constant.ADDRESS;
import static ch.epfl.sweng.coding_style.ex1.Constant.PORT;
import static ch.epfl.sweng.coding_style.ex1.Constant.BUFFER_SIZE;

/**
 * Client Object.
 * 
 * Offers a non-static method to request the n-th Recaman's sequence term.
 * 
 * @author John Doe
 */

public class Client {
	private final DatagramSocket socket;
	private final InetAddress address;
	private byte[] buf;

	/**
	 * Constructor
	 * 
	 * @throws IOException if an error occurs during DatagramSocket creation or
	 *                     during the IP address resolution
	 */
	public Client() throws IOException {
		socket = new DatagramSocket();
		address = InetAddress.getByName(ADDRESS);
	}

	/**
	 * Sends a positive integer n to the server, and listens to receive the n first
	 * terms of the Recaman's sequence
	 * 
	 * @param n the positive number of terms of the Recaman's sequence we want
	 * @throws IOException              if there is a problem while sending or
	 *                                  receiving over UDP
	 * @throws IllegalArgumentException if the message is more than 4096 bytes long
	 */
	public String requestRecamanSequence(int n) throws IOException {
		// Prepares buffer
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		baos.write(n);
		buf = baos.toByteArray();
		if (buf.length > BUFFER_SIZE) {
			throw new IllegalArgumentException("You cannot send a message " + "of more than " + BUFFER_SIZE + " bytes");
		}

		// Sends packet
		DatagramPacket packet = new DatagramPacket(buf, buf.length, address, PORT);
		socket.send(packet);

		// Receives the data back from server
		buf = new byte[BUFFER_SIZE];
		packet = new DatagramPacket(buf, buf.length);
		socket.receive(packet);

		return new String(packet.getData());
	}

	/**
	 * Closes the datagram socket
	 */
	public void close() {
		socket.close();
	}
}
