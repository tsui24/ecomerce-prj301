/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.MessageDAO;
import dal.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import model.User;

/**
 *
 * @author admin
 */
@WebServlet(name = "UserControl", urlPatterns = {"/usercontrol"})
public class UserControl extends HttpServlet {

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
            out.println("<title>Servlet UserControl</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UserControl at " + request.getContextPath() + "</h1>");
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
        UserDAO ud = new UserDAO();
        List<User> l = ud.getAllUser();

        MessageDAO m = new MessageDAO();
        int is_read = m.getQuantitiesMessages(user.getId());
        request.setAttribute("isread", is_read);

        request.setAttribute("luser", l);
        request.getRequestDispatcher("admin-user.jsp").forward(request, response);
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
        String action = request.getParameter("action"); // Determine the action (add, update, delete)

        switch (action) {
            case "add":
                addUser(request, response);
                break;
            case "edit":
                updateUser(request, response);
                break;
            case "delete":

                break;
            default:
                response.sendRedirect("usercontrol");
        }
    }
    private UserDAO userDAO = new UserDAO(); // DAO instance

    private void addUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserDAO userDAO = new UserDAO();
        String username = request.getParameter("userName");
        String password = request.getParameter("password");
        String role = request.getParameter("role");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        if (userDAO.existsByUsername(username)) {
            request.setAttribute("errorMessage", "Username already exists!");
            request.getRequestDispatcher("admin-user.jsp");
        } else {
            User newUser = new User(firstName, lastName, username, userDAO.hashPassword(password), role, false, email);
            userDAO.addUserByAdmin(newUser);
            response.sendRedirect("usercontrol"); // Refresh the user list
        }

    }

    // Method to update an existing user
    private void updateUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String role = request.getParameter("role");
        boolean isBan = Boolean.parseBoolean(request.getParameter("isBanned"));
        User updatedUser = new User(id, firstName, lastName, role, isBan, email);
        userDAO.updateUserByAdmin(updatedUser);
        response.sendRedirect("usercontrol"); // Refresh the user list
    }

    // Method to delete a user
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
