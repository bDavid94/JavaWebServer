import java.io.IOException;
import java.net.ServerSocket;

public class Main {

    public static void main(String[] args) {
	// write your code here
        try {
            ServerSocket serverSocket = new ServerSocket((5000));
            while(true) {
                new Handler(serverSocket.accept()).start();
                System.out.println("Client Connected");
            }
        }   catch (IOException e) {
            System.out.println("Server exception " + e.getMessage());
        }
    }
}
