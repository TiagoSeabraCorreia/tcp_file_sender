package pt.correia.tiago;

import pt.correia.tiago.data.TCPFileSender;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        TCPFileSender server = new TCPFileSender("localhost", 6000);
        server.acceptDownloader();
        server.receiveFileName();
        server.receiveChunkSize();
        server.sendResponse();
        server.sendFile();
    }
}