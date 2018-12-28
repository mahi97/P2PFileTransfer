package ir.aut.ftp2p.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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

    String getFileName(StringBuilder req) {
        String t = req.toString();
        String[] lines = t.split("\\r?\\n");
        if (lines.length == 2 && lines[0].equals("File Request:")) {
            return lines[1];
        }
        return "NOFILE";
    }

    static final int MSG_LEN = 8;

    public void send(String file, String path) {
        try {
            DatagramSocket socket = new DatagramSocket(8027, InetAddress.getByName("0.0.0.0"));
            byte[] buf = new byte[100];
            DatagramPacket packet = new DatagramPacket(buf, 0, buf.length);
            socket.receive(packet);
            String req_file = getFileName(data(buf));
            System.out.println("Request:" + req_file);
            if (file.equals(req_file)) {
                DatagramSocket ssocker = new DatagramSocket(5000);
                FileReader f = new FileReader(path);
                BufferedReader br = new BufferedReader(f);
                File ff = new File(path);
                long l = ff.length();
                for (int i = 0; i < Math.ceil(l / MSG_LEN) + 1; i++) {
                    char[] chr = new char[MSG_LEN];
                    int n = br.read(chr, 0,MSG_LEN);
                    String line = "OFFSET=" + (i) + "=" + String.valueOf(chr);
                    byte[] buf2 = line.getBytes();
                    DatagramPacket ppacket = new DatagramPacket(buf2,0,buf2.length, packet.getAddress(), packet.getPort());
                    ssocker.send(ppacket);
                }
                String line = "#END";
                byte[] buf2 = line.getBytes();
                DatagramPacket ppacket = new DatagramPacket(buf2,0,buf2.length, packet.getAddress(), packet.getPort());
                ssocker.send(ppacket);
                ssocker.send(ppacket);
                br.close();
                f.close();
            } else {
                System.out.println("Invalid Requested File");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
