/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.CartDAO;
import dal.DetailDAO;
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
import model.Cart;
import model.Product;
import model.ProductVariant;
import model.User;
import org.apache.tomcat.util.openssl.openssl_h;

/**
 *
 * @author admin
 */
@WebServlet(name = "CartServlet", urlPatterns = {"/cart"})
public class CartServlet extends HttpServlet {

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
            out.println("<title>Servlet CartServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CartServlet at " + request.getContextPath() + "</h1>");
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
        String id_raw = request.getParameter("id");
        CartDAO cd = new CartDAO();
        ProductDAO pd = new ProductDAO();
        DetailDAO dp = new DetailDAO();
        int id;
        try {
            id = Integer.parseInt(id_raw);
            List<ProductVariant> l = new ArrayList<>();
            List<Product> lpProducts = new ArrayList<>();

            l = cd.getListProductInCart(id);
            List<String> limg = new ArrayList<>();
            List<String> ltype = new ArrayList<>();
            for (ProductVariant p : l) {
                String sku = p.getSku();
                String[] need = sku.split("-");
                ltype.add(need[need.length - 1]);
                limg.add(dp.getImgForProductDetail(p.getProductId(), need[need.length - 1]));
                lpProducts.add(dp.getProductDetai(p .getProductId()));
            }
            List<Cart> lcart = new ArrayList<>();
            lcart = cd.getListCart(id);
            request.setAttribute("lcart", lcart);
            request.setAttribute("product", lpProducts);
            request.setAttribute("list", l);
            request.setAttribute("limg", limg);
            request.setAttribute("ltype", ltype);
            request.setAttribute("userId", id);
            request.getRequestDispatcher("cart.jsp").forward(request, response);
        } catch (Exception e) {
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
        String action = request.getParameter("action");

    if ("add".equals(action)) {
        String userIdRaw = request.getParameter("userId");
        String variantIdRaw = request.getParameter("variantId");
        
        try {
            int userId = Integer.parseInt(userIdRaw);
            int variantId = Integer.parseInt(variantIdRaw);

            CartDAO cd = new CartDAO();
            boolean exists = cd.checkProductInCart(userId, variantId);
            if (!exists) {
                cd.addProductInCart(userId, variantId);
            } else{
                
            }

            response.sendRedirect("cart?id=" + userId); 
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid ID");
        }
    } 
    else if ("delete".equals(action)) {
        String userIdRaw = request.getParameter("userId");
        String variantIdRaw = request.getParameter("variantId");

        try {
            int userId = Integer.parseInt(userIdRaw);
            int variantId = Integer.parseInt(variantIdRaw);
            CartDAO cd = new CartDAO();
            cd.deleteProductVariantsInCart(userId, variantId);
            response.sendRedirect("cart?id=" + userId);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid ID");
        }
    } else {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
    }
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
