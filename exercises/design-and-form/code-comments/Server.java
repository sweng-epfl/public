package ch.epfl.sweng.coding_style.exercise;

import static ch.epfl.sweng.coding_style.exercise.Constant.BUFFER_SIZE;
import static ch.epfl.sweng.coding_style.exercise.Constant.PORT;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * Server Object.
 * 
 * This class offers two non-static methods. One to listen and reply over UDP
 * and also one that computes the n-th term of the Recaman's sequence.
 * 
 * @author John Doe
 */

public class Server {
	private final DatagramSocket socket;
	private byte[] buf;

	/**
	 * Constructor
	 * 
	 * @throws SocketException if the DatagramSocket cannot be initialised correctly
	 */
	public Server() throws SocketException {
		socket = new DatagramSocket(PORT);
		buf = new byte[BUFFER_SIZE];
	}

	/**
	 * Listens on port 12345 for a positive integer, computes the n first terms of
	 * the Recaman's sequence and sends it back as a string
	 * 
	 * @return a string, that is the datas that were received on the UDP connection
	 * @throws IOException if the socket was not able to send or receive a packet or
	 *                     if the received data is not an integer
	 */
	public void listenAndReply() throws IOException {
		/*
		 * The method assumes that it receives an integer from the client. An
		 * IOException will thrown if that is not the case.
		 */

		// Creating a 4096 bytes buffer in order to receive the data
		DatagramPacket packet = new DatagramPacket(buf, buf.length);
		socket.receive(packet);

		// Getting the address and port and calling the method computeRecaman
		// in order to send the data back
		InetAddress address = packet.getAddress();
		int port = packet.getPort();
		packet = new DatagramPacket(buf, buf.length, address, port);
		ByteArrayInputStream bais = new ByteArrayInputStream(buf);

		buf = computeRecaman(bais.read()).getBytes();
		packet = new DatagramPacket(buf, buf.length, address, port);
		socket.send(packet);
	}

	/**
	 * Compute the n first terms of the Recaman's sequence and create a string of
	 * the terms, comma-separated
	 * 
	 * @param n the number of Recaman's sequence terms that we want to compute
	 */
	public String computeRecaman(int n) {
		StringBuilder string = new StringBuilder("0");
		int arr[] = new int[n];
		arr[0] = 0;

		int curr = 0;
		for (int i = 1; i < n; i++) {
			// Keeps track of whether arr[i-1]-i is positive and not known
			boolean positiveAndUnknown = true;
			if (arr[i - 1] - i > 0) {
				curr = arr[i - 1] - i;
				// Positive, we check whether it is already known
				for (int j = 0; j < i; j++) {
					if (arr[j] == curr) {
						positiveAndUnknown = false;
					}
				}
			} else {
				positiveAndUnknown = false;
			}
			// We cannot use arr[i-1]-i, we have to take arr[i-1]+i
			if (!positiveAndUnknown) {
				curr = arr[i - 1] + i;
			}
			arr[i] = curr;
			string.append(", " + curr);
		}

		return string.toString();
	}
}
