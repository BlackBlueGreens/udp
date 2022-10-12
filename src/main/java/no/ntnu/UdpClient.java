package no.ntnu;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

/**
 * An example UDP client - shown in the lecture (a bit cleaned code).
 * Sends a given string message to the server, then waits for the server's response and prints the response to the
 * console. Then exits.
 */
class UdpClient extends General{
    // The address of the server. It can be either a domain name, or an IP address
    // `localhost` is a special domain name meaning "this same machine"
    private static final String SERVER_ADDRESS = "localhost";
    String type = null;

    /**
     *
     * @param input the semtence to determine wether its a question or statement
     * @return a string, either question or statement
     */
    public String type(String input) {
    if(input == "?") {
    return type = "question";
    } else {
        return type = "statement";
    }
    }

/**
     * Starts the client, according to the protocol described above.
     * @param messageToSend The message to send to the server
     */
    public void run(String messageToSend) {
        try {
            // Send a datagram with the message to the server
            byte[] dataToSend = messageToSend.getBytes();
            DatagramSocket clientSocket = new DatagramSocket();
            InetAddress serverAddress = InetAddress.getByName(SERVER_ADDRESS);
            DatagramPacket sendPacket = new DatagramPacket(dataToSend, dataToSend.length, serverAddress,
                    UdpServer.SERVER_PORT);
            clientSocket.send(sendPacket);


            // Wait for a response from the server
            byte[] responseDataBuffer = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(responseDataBuffer, responseDataBuffer.length);
            clientSocket.receive(receivePacket);
            String modifiedSentence = new String(receivePacket.getData(), 0, receivePacket.getLength());

            //type and word count
            String type = type(getLastChar(modifiedSentence));
            int wordCount = countWords(modifiedSentence);
            sendPacket = new DatagramPacket(type.getBytes(StandardCharsets.UTF_8), length(type), serverAddress,
                    UdpServer.SERVER_PORT);
            clientSocket.send(sendPacket);
            clientSocket.close();


            System.out.println("Response from the server: " + modifiedSentence );
            System.out.println("Last Character " + getLastChar(modifiedSentence));
            System.out.println("word count " + countWords(modifiedSentence));
            System.out.println("OK");







        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
