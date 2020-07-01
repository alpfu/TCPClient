import java.net.*;
import java.io.*;
import java.util.*;

/**
 *
 * @author urkmez, af 17.11.2018 Last Edited: 27.11.2018 by urkmez, af
 *
 */

public class TCPClient {

    public static void main(String args[]) throws Exception {

        Thread clientThread = new Thread(new ClientProcess("127.0.0.1", 7896));
        clientThread.start();
    }
}

class ClientProcess implements Runnable {

    private Socket clientSocket;
    private static DataInputStream rcvMsg;
    private DataOutputStream sndMsg;

    static Scanner keyboard = new Scanner(System.in);

    public ClientProcess(String host, int port) {
        try {
            System.out.println("connection establishing...");
            clientSocket = new Socket(host, port);
            System.out.println("connected to server !\ntype \"/quit\" to disconnect !");
            rcvMsg = new DataInputStream(clientSocket.getInputStream());
            sndMsg = new DataOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void run() {

        String msg = "";
        new Thread(receiver).start();

        while (!msg.equals("/quit")) {
            try {
                msg = keyboard.nextLine();
                sndMsg.writeUTF(msg);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

        System.out.println("goodbye !");
    }

    Runnable receiver = new Runnable() {
        public void run() {
            while (true) {
                try {
                    System.out.println(rcvMsg.readUTF());
                } catch (IOException e) {
                    System.out.println("disconnected !");
                    System.exit(0);
                }
            }
        }
    };

} // end class ClientProcess