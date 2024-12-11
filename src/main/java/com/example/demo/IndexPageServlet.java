package com.example.demo;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/index")
public class IndexPageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        // Устанавливаем параметры для шаблона
        request.setAttribute("param1", "Hello from Index Page!");
        request.setAttribute("param2", "This is a parameterized page.");

        try {
            // Перенаправляем запрос на шаблон index.thtml
            request.getRequestDispatcher("/template/index.thtml").forward(request, response);
        } catch (ServletException | IOException e) {
            throw new RuntimeException("Error forwarding to the template", e);
        }
    }
}
