package com.example.demo;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

@WebServlet("/htmlgt")
public class HttpServletExample extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Writer writer = response.getWriter();

        writer.write("<!doctype html>");
        writer.write("<html lang=\"en\">");
        writer.write("<head>");
        writer.write("<meta charset=\"UTF-8\">");
        writer.write("<title>ExtendedServlet</title>");
        writer.write("</head>");
        writer.write("<body>");
        writer.write("<h1>HttpServletExampleExt</h1>");
        writer.write("</body>");
        writer.write("</html>");

    }

}
