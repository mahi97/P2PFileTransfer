package ir.aut.ftp2p.core;

import java.io.IOException;
import java.net.*;

import static ir.aut.ftp2p.util.Parser.data;

/**
 * Created by Rahimi on 12/22/18.
 */
public class Receiver {

    public Receiver() {

    }


    public void receive(String file) {
        InetAddress addr = null;
        try {
            addr = InetAddress.getByName("192.168.43.255");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        try {
            DatagramSocket socket = new DatagramSocket(8027);
            socket.setBroadcast(true);
            socket.connect(InetAddress.getByName("255.255.255.255"), 8027);
            String req = "File Request:\n\n" + file;
            byte[] buf = req.getBytes();
            DatagramPacket packet = new DatagramPacket(buf,0,buf.length);
            socket.send(packet);
            byte[] receive = new byte[100];
            DatagramPacket DpReceive;
            while (true)
            {
                DpReceive = new DatagramPacket(receive, receive.length);

                socket.receive(DpReceive);

                System.out.println("Client:-" + data(receive));

                receive = new byte[100];
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
