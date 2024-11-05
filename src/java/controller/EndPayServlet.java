/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import static java.lang.Integer.sum;
import java.util.ArrayList;
import java.util.List;
import model.User;

/**
 *
 * @author admin
 */
@WebServlet(name = "EndPayServlet", urlPatterns = {"/endpay"})
public class EndPayServlet extends HttpServlet {

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
            out.println("<title>Servlet EndPayServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EndPayServlet at " + request.getContextPath() + "</h1>");
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
        if (user == null || !"user".equalsIgnoreCase(user.getRole())) {
            request.setAttribute("errorMessage", "You do not have permission to access this page.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
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
        HttpSession session = request.getSession(false); // Không tạo session mới
        if (session == null) {
            request.setAttribute("errorMessage", "You do not have permission to access this page.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }

        User user = (User) session.getAttribute("user");
        if (user == null || !"user".equalsIgnoreCase(user.getRole())) {
            request.setAttribute("errorMessage", "You do not have permission to access this page.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }

        String[] variantIdsArray = request.getParameterValues("variantIds");
        String[] quantitiesArray = request.getParameterValues("quantities");

        List<Integer> variantIds = new ArrayList<>();
        List<Integer> quantities = new ArrayList<>();
        int totalSP = 0;

        try {
            if (variantIdsArray != null && quantitiesArray != null) {
                for (String id : variantIdsArray) {
                    variantIds.add(Integer.parseInt(id));
                }
                for (String quantity : quantitiesArray) {
                    int qty = Integer.parseInt(quantity);
                    if (qty < 0) {
                        throw new NumberFormatException("Quantity cannot be negative.");
                    }
                    quantities.add(qty);
                    totalSP += qty; 
                }
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing variantIds or quantities.");
                return;
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid input data.");
            return;
        }

        String customerName = request.getParameter("customerName");
        String customerPhone = request.getParameter("customerPhone");
        String customerAddress = request.getParameter("customerAddress");
        String totalPay = request.getParameter("total");

        if (customerName == null || customerPhone == null || customerAddress == null || totalPay == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing customer information or total payment.");
            return;
        }

        request.setAttribute("variantIds", variantIds);
        request.setAttribute("quantities", quantities);
        request.setAttribute("customerName", customerName);
        request.setAttribute("customerPhone", customerPhone);
        request.setAttribute("customerAddress", customerAddress);
        request.setAttribute("totalproduct", totalSP);
        request.setAttribute("total", totalPay);

        request.getRequestDispatcher("payment.jsp").forward(request, response);
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
