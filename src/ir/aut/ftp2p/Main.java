package ir.aut.ftp2p;

/**
 * Created by Rahimi on 12/22/18.
 */

import ir.aut.ftp2p.core.Receiver;
import ir.aut.ftp2p.core.Transfer;

public class Main {

    private static Receiver receiver = null;
    private static Transfer transfer = null;

    private static void showHelp() {
        System.out.println("usage:\n" +
                "p2p -receive [name]\n" +
                "p2p –serve -name [name] -path [path]");
    }

    public static void main(String[] args) {
        receiver = new Receiver();
        transfer = new Transfer();
        if (args.length < 2) {
            showHelp();
        } else {
            if (args[0].equals("-receive")) {
                receiver.receive(args[1]);
            } else if (args.length == 5 && args[0].equals("-serve")) {
                transfer.send(args[2], args[4]);
            } else {
                showHelp();
            }
        }
    }
}
