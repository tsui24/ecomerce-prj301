/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.CategoryDAO;
import dal.MessageDAO;
import dal.ProductDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import model.Category;
import model.Product;
import model.User;

/**
 *
 * @author admin
 */
@WebServlet(name = "ProductHomeServlet", urlPatterns = {"/producthome"})
public class ProductHomeServlet extends HttpServlet {

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
            out.println("<title>Servlet ProductHomeServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ProductHomeServlet at " + request.getContextPath() + "</h1>");
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
        ProductDAO pd = new ProductDAO();
        List<Product> l = pd.getTopProductWithCategory(2);
        List<String> limg = new ArrayList<>();
        for (Product p1 : l) {
            limg.add(pd.getImageDefault(p1.getId()));
        }
        List<Product> l2 = pd.getTopProductWithCategory(3);
        List<String> limg2 = new ArrayList<>();
        for (Product i : l2) {
            limg.add(pd.getImageDefault(i.getId()));
        }

        CategoryDAO cd = new CategoryDAO();
        List<Category> l3 = cd.getAllCategory();
        request.setAttribute("listp", l);
        request.setAttribute("listimg", limg);
        request.setAttribute("list2", l2);
        request.setAttribute("listimg2", limg2);
        request.setAttribute("listcate", l3);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user != null) {
            MessageDAO m = new MessageDAO();
            int is_read = m.getQuantitiesMessages(user.getId());
            request.setAttribute("isread", is_read);
        }

        request.getRequestDispatcher("home.jsp").forward(request, response);
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
