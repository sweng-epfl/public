package ch.epfl.sweng.coding_style.exercise;

import static ch.epfl.sweng.coding_style.exercise.Constant.ADDRESS;
import static ch.epfl.sweng.coding_style.exercise.Constant.BUFFER_SIZE;
import static ch.epfl.sweng.coding_style.exercise.Constant.PORT;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client {
	private final DatagramSocket socket;
	private final InetAddress address;
	private byte[] buf;

	public Client() throws IOException {
		socket = new DatagramSocket();
		address = InetAddress.getByName(ADDRESS);
	}

	public String requestRecamanSequence(int n) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// We write our number as a byte array
		baos.write(n);
		buf = baos.toByteArray();

		// We make sure that the message is not too long for our buffer
		if (buf.length > BUFFER_SIZE) {
			throw new IllegalArgumentException("You cannot send a message " + "of more than " + BUFFER_SIZE + " bytes");
		}

		// We create a DatagramPacket that will be used to send the data
		DatagramPacket packet = new DatagramPacket(buf, buf.length, address, PORT);
		socket.send(packet);

		// We reset the buffer size
		buf = new byte[BUFFER_SIZE];

		// We create a new packet to receive datas
		packet = new DatagramPacket(buf, buf.length);
		socket.receive(packet);

		return new String(packet.getData());
	}

	public void close() {
		socket.close();
	}
}
