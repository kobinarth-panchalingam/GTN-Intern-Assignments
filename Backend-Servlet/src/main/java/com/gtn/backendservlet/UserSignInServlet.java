package com.gtn.backendservlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;

@WebServlet(name = "signInServlet", value = "/api/auth/signin")
public class UserSignInServlet extends HttpServlet {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        out.println("</body></html>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BufferedReader reader = request.getReader();

        // Read JSON data from the request body
        StringBuilder jsonStringBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonStringBuilder.append(line);
        }

        // Convert JSON data to UserSignIn object
        Users user = objectMapper.readValue(jsonStringBuilder.toString(), Users.class);

        // Validate the user credentials
        if (validateUserCredentials(user)) {
            response.getWriter().println("User signed in successfully!");
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().println("Invalid username or password");
        }
    }

    private boolean validateUserCredentials(Users user) {
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

    public void destroy() {
    }
}

