/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

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
import java.util.List;
import model.ProductOption;
import model.ProductOptionValue;
import model.ProductVariant;
import model.User;

/**
 *
 * @author admin
 */
@WebServlet(name = "VariantServlet", urlPatterns = {"/managevariant"})
public class VariantServlet extends HttpServlet {

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
            out.println("<title>Servlet VariantServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet VariantServlet at " + request.getContextPath() + "</h1>");
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
        String productId_raw = request.getParameter("productId");
        ProductDAO pd = new ProductDAO();
        DetailDAO dd = new DetailDAO();
        int productId;
        try {
            productId = Integer.parseInt(productId_raw);
            List<ProductVariant> lv = dd.getListProductVariants(productId);
            request.setAttribute("productId", productId);
            request.setAttribute("lvariant", lv);
            request.getRequestDispatcher("admin-variant.jsp").forward(request, response);
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
        DetailDAO dd = new DetailDAO();
        ProductDAO pd = new ProductDAO();
        String productId_raw = request.getParameter("productId");
        String option = request.getParameter("option");
        String storage = request.getParameter("storage");
        String optionValue = request.getParameter("optionvalue");
        String price_raw = request.getParameter("price");
        String quantity_raw = request.getParameter("quantity");
        int productId, quantity;
        double price;
   
        try {
            productId = Integer.parseInt(productId_raw);
            price = Double.parseDouble(price_raw);
            quantity = Integer.parseInt(quantity_raw);
            
            int productOptionId;
            if (pd.productOptionExistByProductIdAndOptionName(productId, option)) {
                productOptionId = pd.getProductOptionId(productId, option);
            } else {
                ProductOption po = new ProductOption();
                po.setProductId(productId);
                po.setOptionName(option);
                po.setStorage("yes");
                productOptionId = pd.insertOption(po);
            }
            if(pd.isExistOptionValue(productOptionId, optionValue)){
                
            } else{
                ProductOptionValue pov = new ProductOptionValue();
                pov.setSku(optionValue);
                pov.setStorage(storage);
                pov.setProductOptionId(productOptionId);
                pd.insertOptionValue(pov);
            }
            String skuForPv = dd.getProductDetai(productId).getSlug()+"-"+option+"-"+optionValue;
            ProductVariant pv = new ProductVariant(productId, skuForPv, price, quantity, 0, storage);
            pd.insertProductVariant(pv);
            
        } catch (Exception e) {
        }
        response.sendRedirect("managevariant?productId="+productId_raw);
    
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
