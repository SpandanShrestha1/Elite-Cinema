package com.elitecinema.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Servlet for ticket rate page
 */
@WebServlet(name = "TicketRateServlet", urlPatterns = {"/ticket-rate"})
public class TicketRateServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Forward to ticket rate page
        request.getRequestDispatcher("/WEB-INF/views/ticket-rate.jsp").forward(request, response);
    }
}
