package ir.aut.ftp2p.core;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import static ir.aut.ftp2p.util.Parser.data;

/**
 * Created by Rahimi on 12/22/18.
 */
public class Transfer {

    public Transfer() {

    }


    public void send(String file, String path) {
        try {
            DatagramSocket socket = new DatagramSocket(8027, InetAddress.getByName("0.0.0.0"));
            byte[] buf = new byte[100];
            DatagramPacket packet = new DatagramPacket(buf, 0, buf.length);
            socket.receive(packet);
            System.out.println(data(buf));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
