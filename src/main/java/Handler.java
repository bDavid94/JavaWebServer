import java.io.*;
import java.net.Socket;
import java.util.Date;

public class Handler extends Thread {

    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;
    private BufferedReader fileReader;

    private File file;
    private RubyRunner rubyRunner;

    private final static String BASE_PATH = "C:\\Users\\BogdanDavid\\angular\\Sockets\\src\\com\\company\\";
    private final static String HOME_PATH = "/home.html";
    private final static String FILE_NOT_FOUND_PATH = "/not-found.html";

    public Handler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
             input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             output = new PrintWriter(socket.getOutputStream(), true);

             String request;
             request = input.readLine();
             String requestedFile;
             if (request != null) {
                 requestedFile = request.split(" ")[1];
//                 System.out.println(requestedFile);
                 System.out.println("request:" + request);

                 if (request.startsWith("GET") && !requestedFile.equals("/favicon.ico")) {
                     if (requestedFile.equals("/run-ruby")) {
                         rubyRunner = new RubyRunner();
                         String rubyOutput = rubyRunner.runRuby();
                         output.println("HTTP/1.0 200 OK\r\n" +
                                 "Content-Type: " + "text/html" + "\r\n" +
                                 "Date: " + new Date() + "\r\n" +
                                 "Server: FileServer 1.0\r\n\r\n");
                         output.println(rubyOutput);

                     } else {
                         try {
                             if (requestedFile.equals("/")) {
                                 requestedFile = HOME_PATH;
                             }
                             file = new File(BASE_PATH + requestedFile);

                             fileReader = new BufferedReader(new FileReader(file));
                             output.println("HTTP/1.0 200 OK\r\n" +
                                     "Content-Type: " + "text/html" + "\r\n" +
                                     "Date: " + new Date() + "\r\n" +
                                     "Server: FileServer 1.0\r\n\r\n");

                         } catch (FileNotFoundException fe) {
                             file = new File(BASE_PATH + FILE_NOT_FOUND_PATH);
                             fileReader = new BufferedReader(new FileReader(file));
                             output.println("HTTP/1.0 404 Not Found\r\n" +
                                     "Content-Type: " + "text/html" + "\r\n" +
                                     "Date: " + new Date() + "\r\n" +
                                     "Server: FileServer 1.0\r\n\r\n");
                         }
                         String line;
                         while ((line = fileReader.readLine()) != null) {
                             output.println(line);
                         }
                     }
                 }
             }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                socket.close();
            }   catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
