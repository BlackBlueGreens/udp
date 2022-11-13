package no.ntnu;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

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
    private boolean answerStage;
    private int packetCount;
    byte[] responseDataBuffer;

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

    //use scanner instead
/**
     * Starts the client, according to the protocol described above.
     */
    public void run() throws IOException {
        System.out.print("Enter a message: ");
        packetCount = 0;
        Scanner userInput = new Scanner(System.in);

        while (true) {
            String messageToSend = userInput.nextLine();

            try {

                packetCount++;
                // Send a datagram with the message to the server
                byte[] dataToSend = messageToSend.getBytes();
                DatagramSocket clientSocket = new DatagramSocket();
                InetAddress serverAddress = InetAddress.getByName(SERVER_ADDRESS);
                DatagramPacket sendPacket = new DatagramPacket(dataToSend, dataToSend.length, serverAddress,
                        UdpServer.SERVER_PORT);
                clientSocket.send(sendPacket);
                //break when not task
                if (!messageToSend.equals("task") && packetCount == 1) {
                    System.out.println("first message must be task");
                    packetCount = 0;
                    break;
                } else{
                    // Wait for a response from the server
                    responseDataBuffer = new byte[1024];
                    DatagramPacket receivePacket = new DatagramPacket(responseDataBuffer, responseDataBuffer.length);
                    clientSocket.receive(receivePacket);
                    String modifiedSentence = new String(receivePacket.getData(), 0, receivePacket.getLength());
                    System.out.println("Response from the server: " + modifiedSentence);
                    if(answerStage) {
                        break;
                    }else {
                    answerStage = true;
                }
                }




            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

}
