package com.gtn.backendservlet;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "registerServlet", value = "/api/auth/signup")
public class UserRegisterServlet extends HttpServlet {
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
        // Convert JSON data to Employee object
        Users newUser = objectMapper.readValue(jsonStringBuilder.toString(), Users.class);
        System.out.println(newUser.getFirstName());
        // Save the user to the database
        if (saveUserToDatabase(newUser)) {
            response.getWriter().println("User registered successfully!");
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("Error registering user");
        }
    }

    private boolean saveUserToDatabase(Users users) {
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

    public void destroy() {
    }
}