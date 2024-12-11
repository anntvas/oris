package com.example.demo;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/")
public class MainServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Устанавливаем параметры для шаблона
        request.setAttribute("title", "Welcome to My Simple Site!");
        request.setAttribute("description", "This is the home page of our simple website.");

        // Перенаправляем на шаблон
        request.getRequestDispatcher("index.thtml").forward(request, response);
    }
}
