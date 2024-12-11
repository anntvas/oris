package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Server {
    public static final int SERVER_PORT = 50000;
    public static final String ROOT_DIR = "/Users/ann/IdeaProjects/untitled2/src/main/java/org/example/";

    public static void main(String[] args) {

        try {
            Map<String, IResourceHandler> resouces = new HashMap<>();
            resouces.put("home.html", new HomeResourceHandler());
            ServerSocket server = new ServerSocket(SERVER_PORT);
            System.out.println("Start server");
            Socket clientSocket = server.accept();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            OutputStream os = clientSocket.getOutputStream();
            String headerLine = bufferedReader.readLine();
            String[] header = headerLine.split("\\s+");
            String method = header[0];
            String query = header[1].substring(1);
            String httpVersion = header[2];

            Map<String, String> queryPairs = new LinkedHashMap<>();
            if (query.contains("?")) {
                String uri = query.substring(0, query.indexOf("?"));
                String[] pairs = query.substring(query.indexOf("?") + 1).split("&");
                for (String parameter : pairs) {
                    int index = parameter.indexOf("=");
                    queryPairs.put(URLDecoder.decode(parameter.substring(0, index), StandardCharsets.UTF_8), URLDecoder.decode(parameter.substring(index + 1)));
                }
                ResponceContent responceContent = null;
                System.out.println(uri);
                IResourceHandler handler = resouces.get(uri);

                if (handler != null) {
                    responceContent = handler.handle(queryPairs);
                }
                String[] responce = {
                        "HTTP/1.1 200 OK\r\n",
                        "Server: MySimpleServer\r\n",
                        "Content-Type: " + responceContent.getMimeType() + "\r\n",
                        "Content-Length: " + responceContent.getContent().length + "\r\n",
                        "\r\n"
                };

                for (String responseHeaderLine : responce) {
                    os.write(responseHeaderLine.getBytes());
                    os.flush();
                }
                os.write(responceContent.getContent());
                os.flush();

            } else {
                String[] response = {
                        "HTTP/1.1 404 OK\r\n",
                        "Server: MySimpleServer\r\n",
                        "\r\n"
                };

                for (String responseHeaderLine : response) {
                    os.write(responseHeaderLine.getBytes());
                    os.flush();
                }
            }

            while (headerLine != null && !headerLine.isEmpty()) {
                headerLine = bufferedReader.readLine();
                System.out.println(headerLine);
            }
            clientSocket.close();

            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
