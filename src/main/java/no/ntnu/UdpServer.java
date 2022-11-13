package no.ntnu;

import java.io.IOException;
import java.net.*;
import java.util.Random;

/**
 * An example UDP server. The example from the lecture slides, a bit cleaned.
 * Waits for an incoming UDP datagram with a text message. Transforms that message to uppercase and
 * sends it back to the client. Then waits for the next client datagram (from the same or another client), etc.
 */
public class UdpServer extends General {
    public final static int SERVER_PORT = 9877;
    public boolean taskStarted;
    public boolean validateAnswer;

    Random generator = new Random();
    String[] array =new String[] {"Hello im server.", "Im server?"};
    /**
     * return random string from array
     * @return a random string from array
     */
    public String getRandom() {
        int randomIndex = generator.nextInt(array.length);
        return array[randomIndex];
    }
//package count = 1 then redo, now its 2 and message is not task, use boolean instead...
    //or fix logic
    //double boolean for fix?
    // can keep server alive when fixed
    /**
     * Starts the UDP server, never returns (goes into a never-ending loop)
     */
    public void run() {
        System.out.println("Starting UDP server on port " + SERVER_PORT);
        byte[] receiveData = new byte[1024];
        DatagramSocket serverSocket;
        try {
            serverSocket = new DatagramSocket(SERVER_PORT);
        } catch (IOException e) {
            System.out.println("Socket error, shutting down the server: " + e.getMessage());
            return;
        }
        String task = getRandom();
        byte[] sendData;
        validateAnswer = false;
        taskStarted = true;
        while (true) {
            try {
                // Receive a datagram from a client (any client)
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);
                String sentence = new String(receivePacket.getData(), 0, receivePacket.getLength());
                //get address and port
                InetAddress clientAddress = receivePacket.getAddress();
                int port = receivePacket.getPort();

//find out the type and word count of the sentence you sent
//then check if that is equal to what the user sent, if so
//print ok, else print error
                if (validateAnswer) {
                    String finalMessage;
                    if (taskAnswer(task).equals(sentence)) {
                        finalMessage = "OK";
                        System.out.println("OK... CORRECT ANSWER! imma go integrate ur important answer it into Ethereum blockchain :)");
                    } else {
                        finalMessage = "ERROR";
                        System.out.println("ERROR... Try again noob");
                    }
                    sendData = finalMessage.getBytes();

                    DatagramPacket responsePacket = new DatagramPacket(sendData, sendData.length, clientAddress, port);
                    serverSocket.send(responsePacket);
                    break;
                }


                // if first message not task then warning
                if(!sentence.equals("task") && taskStarted){
                    System.out.println(" Recieved package from client, must state 'task to recieve task'");
                    taskStarted = false;
                    break;
                }
                //if its task then send task
                else{
                    sendData = task.getBytes();

                    DatagramPacket responsePacket = new DatagramPacket(sendData, sendData.length, clientAddress, port);
                    serverSocket.send(responsePacket);
                    System.out.println("started task");
                    taskStarted = true;
                    validateAnswer = true;
                }

            } catch (IOException e) {
                System.out.println("Socket error for the client: " + e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }
}
