/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.BillDAO;
import dal.ChartDAO;
import dal.MessageDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import model.User;

/**
 *
 * @author admin
 */
@WebServlet(name = "CharServlet", urlPatterns = {"/chart"})
public class CharServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet CharServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CharServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false); // Không tạo session mới
        if (session == null) {
            request.setAttribute("errorMessage", "You do not have permission to access this page.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }

        User user = (User) session.getAttribute("user");
        if (user == null || !"admin".equalsIgnoreCase(user.getRole())) {
            request.setAttribute("errorMessage", "You do not have permission to access this page.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }
        ChartDAO chartDAO = new ChartDAO();
        String yearRaw = request.getParameter("year");
        String type = request.getParameter("type");
        String month_Raw = request.getParameter("month");
        if (type.equals("sales")) {

            int year;

            try {
                // Input validation
                if (yearRaw == null || yearRaw.isEmpty()) {
                    year = java.time.Year.now().getValue();
                } else {
                    year = Integer.parseInt(yearRaw);
                }

                List<Double> revenues = chartDAO.getTotalRevenue(year);
                List<Integer> quantities = chartDAO.getTotalQuantity(year);
                MessageDAO m = new MessageDAO();
                int is_read = m.getQuantitiesMessages(user.getId());
                request.setAttribute("isread", is_read);
                request.setAttribute("year", year);
                request.setAttribute("revenues", revenues);
                request.setAttribute("quantities", quantities);
                request.setAttribute("type", type);
                request.getRequestDispatcher("/chart.jsp").forward(request, response);
            } catch (NumberFormatException e) {

            } catch (Exception e) {

            }
        } else if (type.equals("quantity")) {
            LocalDate today = LocalDate.now();
            int month;
            int year;
            try {

                if (yearRaw == null || month_Raw == null) {
                    month = today.getMonthValue();
                    year = today.getYear();
                } else {
                    month = Integer.parseInt(month_Raw);
                    year = Integer.parseInt(yearRaw);
                }
                Map<String, Integer> data = chartDAO.getQuantityProductByMonth(month, year);
                if (data.size() > 0) {
                    List<String> productNames = new ArrayList<>();
                    for (String i : data.keySet()) {
                        productNames.add("\"" + i + "\"");
                    }
                    List<Integer> quantities = new ArrayList<>(data.values());
                    String pro = productNames.toString();
                    String quan = quantities.toString();
                    request.setAttribute("productNames", pro);
                    request.setAttribute("quantities", quan);
                } else {
                    request.setAttribute("error", "Thang nay chua ban sang pham nao");
                }

            } catch (NumberFormatException e) {
            }
            MessageDAO m = new MessageDAO();
            int is_read = m.getQuantitiesMessages(user.getId());
            request.setAttribute("isread", is_read);
            request.setAttribute("type", type);
            request.getRequestDispatcher("chart.jsp").forward(request, response);
        }

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
