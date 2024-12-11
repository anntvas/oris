package com.example.demo;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

@WebServlet("*.thtml")
public class TemplateHandlerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String servletPath = request.getServletPath();

        // Путь к файлу шаблона
        InputStream inputStream = getServletContext().getResourceAsStream("/template" + servletPath);
        if (inputStream == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("Template not found: " + servletPath);
            return;
        }

        // Чтение шаблона
        byte[] content = inputStream.readAllBytes();
        StringBuilder template = new StringBuilder(new String(content));

        // Замена параметров в шаблоне
        replacePlaceholders(request, template);

        // Установка MIME-тип и вывод
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(template.toString());
    }

    private void replacePlaceholders(HttpServletRequest request, StringBuilder template) {
        // Заменяем параметры из request.getAttribute
        Iterator<String> attributeNames = request.getAttributeNames().asIterator();
        while (attributeNames.hasNext()) {
            String name = attributeNames.next();
            Object value = request.getAttribute(name);
            if (value != null) {
                replaceVariable(template, name, value.toString());
            }
        }

        // Заменяем параметры из request.getParameter
        request.getParameterMap().forEach((paramName, values) -> {
            if (values.length > 0) {
                replaceVariable(template, paramName, values[0]);
            }
        });
    }

    private void replaceVariable(StringBuilder template, String variableName, String value) {
        String placeholder = "${" + variableName + "}";
        int startIndex;
        while ((startIndex = template.indexOf(placeholder)) != -1) {
            template.replace(startIndex, startIndex + placeholder.length(), value);
        }
    }
}
