package ir.aut.ftp2p.core;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.*;

import static ir.aut.ftp2p.util.Parser.data;

/**
 * Created by Rahimi on 12/22/18.
 */
public class Receiver {

    public Receiver() {

    }

    String data = "";
    int current_offset = 0;

    void append_data(StringBuilder __data) {
        String _data = __data.toString();
        String[] lines = _data.split("=");
        if (lines[0].equals("OFFSET")) {
            int offset = Integer.valueOf(lines[1]);
            if (offset == current_offset) {
                data += _data.substring(lines[0].length() + lines[1].length() + 2);
                current_offset += 1;
            } else {
                System.out.println("Invalid OFFSET");
            }
        }
    }

    public void receive(String file) {

        try {
            DatagramSocket socket = new DatagramSocket();
            String req = "File Request:\n" + file;
            byte[] buf = req.getBytes();
            DatagramPacket packet = new DatagramPacket(buf,0,buf.length, InetAddress.getByName("255.255.255.255"), 8027);
            socket.send(packet);
            byte[] receive_bytes = new byte[100];
            DatagramPacket DpReceive;
            while (!data(receive_bytes).toString().equals("#END"))
            {
                receive_bytes = new byte[100];
                DpReceive = new DatagramPacket(receive_bytes, receive_bytes.length);

                socket.receive(DpReceive);

                System.out.println("Client: " + data(receive_bytes));
                append_data(data(receive_bytes));

            }
            FileWriter fw = new FileWriter("_" + file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(data);
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
