package com.gtn.backendsocket;


import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SocketServer {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            System.out.println("Server is running...");

            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(() -> handleClient(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket socket) {
        try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)
        ) {
            // Read the HTTP request line
            String requestLine = reader.readLine();
            if (requestLine != null && requestLine.startsWith("POST")) {
                // Extract the URI from the request line
                String[] requestParts = requestLine.split("\\s");
                String uri = requestParts[1];

                if ("/api/auth/signup".equals(uri)) {
                    handleSignUp(reader, writer);
                } else if ("/api/auth/signin".equals(uri)) {
                    handleSignIn(reader, writer);
                } else {
                    // Handle other endpoints or return 404
                    sendHttpResponse(writer, "HTTP/1.1 404 Not Found", "");
                }
            } else {
                // Handle other HTTP methods or return 400 Bad Request
                sendHttpResponse(writer, "HTTP/1.1 400 Bad Request", "");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleSignUp(BufferedReader reader, PrintWriter writer) {
        try {
            // Read the HTTP headers
            StringBuilder headers = new StringBuilder();
            String line;
            while (!(line = reader.readLine()).isEmpty()) {
                headers.append(line).append("\r\n");
            }

            // Read the HTTP body
            StringBuilder body = new StringBuilder();
            while (reader.ready()) {
                body.append((char) reader.read());
            }

            // Parse the JSON data in the HTTP body
            Users newUser = parseJsonBody(body.toString(), Users.class);

            // Process the signup logic (for example, save the user to the database)
            if (saveUserToDatabase(newUser)) {
                sendHttpResponse(writer, "HTTP/1.1 200 OK", "User registered successfully!");
            } else {
                sendHttpResponse(writer, "HTTP/1.1 500 Internal Server Error", "Error registering user");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleSignIn(BufferedReader reader, PrintWriter writer) {
        try {
            // Read the HTTP headers
            StringBuilder headers = new StringBuilder();
            String line;
            while (!(line = reader.readLine()).isEmpty()) {
                headers.append(line).append("\r\n");
            }

            // Read the HTTP body
            StringBuilder body = new StringBuilder();
            while (reader.ready()) {
                body.append((char) reader.read());
            }

            // Parse the JSON data in the HTTP body
            Users user = parseJsonBody(body.toString(), Users.class);

            // Process the signin logic (for example, validate user credentials)
            if (validateUserCredentials(user)) {
                sendHttpResponse(writer, "HTTP/1.1 200 OK", "User signed in successfully!");
            } else {
                sendHttpResponse(writer, "HTTP/1.1 401 Unauthorized", "Invalid username or password");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static <T> T parseJsonBody(String json, Class<T> valueType) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, valueType);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle JSON parsing exception, e.g., log and return null or throw a custom exception
            return null;
        }
    }

    private static boolean saveUserToDatabase(Users users) {
        try (Connection connection = DBUtil.getConnection()) {
            String sql = "INSERT INTO users (first_name, last_name, username, password) VALUES (?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, users.getFirstName());
                statement.setString(2, users.getLastName());
                statement.setString(3, users.getUsername());
                statement.setString(4, users.getPassword());
                int rowsAffected = statement.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean validateUserCredentials(Users user) {
        try (Connection connection = DBUtil.getConnection()) {
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, user.getUsername());
                statement.setString(2, user.getPassword());
                try (ResultSet resultSet = statement.executeQuery()) {
                    return resultSet.next(); // User found in the database
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    private static void sendHttpResponse(PrintWriter writer, String statusLine, String responseBody) {
        writer.println(statusLine);
        writer.println("Content-Type: text/plain");
        writer.println("Content-Length: " + responseBody.length());
        writer.println();
        writer.println(responseBody);
    }
}
