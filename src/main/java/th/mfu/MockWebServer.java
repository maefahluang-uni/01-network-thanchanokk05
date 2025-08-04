package th.mfu;

import java.io.*;
import java.net.*;

public class MockWebServer implements Runnable {

    private int port;

    public MockWebServer(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(port)) { 
            System.out.println("Mock Web Server running on port " + port + "...");

            while (true) {
                try {
                    Socket clientSocket = serverSocket.accept(); 

            
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                    
                    String inputLine;
                    while ((inputLine = in.readLine()) != null && !inputLine.isEmpty()) {
                        System.out.println("Received from client on port " + port + ": " + inputLine);
                    }

                    
                    String response = "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n\r\n"
                            + "<html><body>Hello, Web! on Port " + port + "</body></html>";
                    out.println(response);

                    
                    clientSocket.close();
                } catch (IOException e) {
                    System.err.println("Error handling client on port " + port + ": " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Could not start server on port " + port + ": " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Thread server1 = new Thread(new MockWebServer(8080));
        server1.start();

        Thread server2 = new Thread(new MockWebServer(8081));
        server2.start();

        
        System.out.println("Press any key to stop the server...");
        try {
            System.in.read();
            
            System.out.println("Mock web server stopped.");
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
