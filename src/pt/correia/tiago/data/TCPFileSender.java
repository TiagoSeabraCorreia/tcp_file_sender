package pt.correia.tiago.data;

import java.io.*;
import java.net.Socket;
import java.net.ServerSocket;
public class TCPFileSender {
    Socket socket;
    Integer chunks;
    Integer port;
    Integer chunk;

    OutputStream fos;
    InputStream fis;

    //Input -> receives from something
    //Output -> sends to something
    FileOutputStream fileOutputStream;
    ServerSocket serverSocket;
    String fileName;
    public TCPFileSender(String ip, int port) throws IOException {
        serverSocket = new ServerSocket(port);

    }
    public void receiveFileName() throws IOException {
        byte[] data = new byte[256];
        this.fis.read(data);
        String fileName = new String(data).trim();
        System.out.println("Received file name: " + fileName);
        this.fileName = fileName;
    }

    public void acceptDownloader() throws IOException {
        this.socket = serverSocket.accept();
        fos = socket.getOutputStream();
        fis = socket.getInputStream();
    }

    public void receiveChunkSize() throws IOException {
        byte[] data = new byte[256];
        this.fis.read(data);
        String chunkSize = new String(data).trim();
        System.out.println("Received chunk size: " + chunkSize);
        this.chunk = Integer.parseInt(chunkSize);
    }

    public void sendResponse() throws IOException {
        File f = new File("./files/" + fileName);
        System.out.println(f.length());
        fos.write(String.valueOf(f.length()  / chunk).getBytes());
        this.chunks = (int) (f.length() / chunk);
    }

    public void sendFile() throws IOException {
        FileInputStream fileInputStream = new FileInputStream("./files/" + fileName);
        for (int i = 0; i < chunks; i++) {
            byte[] data = new byte[chunk];
            fileInputStream.read(data);
            fos.write(data);
        }
    }

}
