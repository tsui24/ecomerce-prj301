/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.ProductDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import model.Product;

/**
 *
 * @author admin
 */
@WebServlet(name = "ProductCategoryServlet", urlPatterns = {"/productcategory"})
public class ProductCategoryServlet extends HttpServlet {

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
            out.println("<title>Servlet ProductCategoryServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ProductCategoryServlet at " + request.getContextPath() + "</h1>");
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

        String id_raw = request.getParameter("id");
        String page_raw = request.getParameter("page");
        String minPrice_raw = request.getParameter("minPrice");
        String maxPrice_raw = request.getParameter("maxPrice");
        String sortBy = request.getParameter("sortBy");
        int id, page;
        int pageSize = 4;
        double minPrice = 0, maxPrice = Double.MAX_VALUE;

        try {
            id = Integer.parseInt(id_raw);
            page = page_raw == null ? 1 : Integer.parseInt(page_raw);

            if (minPrice_raw != null && !minPrice_raw.isEmpty()) {
                minPrice = Double.parseDouble(minPrice_raw);
            }
            if (maxPrice_raw != null && !maxPrice_raw.isEmpty()) {
                maxPrice = Double.parseDouble(maxPrice_raw);
            }

            ProductDAO pd = new ProductDAO();

            List<Product> l = pd.findProductsForPage(page, pageSize, id, minPrice, maxPrice, sortBy);

            int totalProducts = pd.getTotalProductsByCategory(id, minPrice, maxPrice);
            int totalPages = (int) Math.ceil((double) totalProducts / pageSize);
            if (totalProducts <= pageSize) {
                totalPages = 1;
            }

            if (l.isEmpty()) {
            request.setAttribute("message", "Không có sản phẩm phù hợp.");
        } else {
            // Lấy danh sách hình ảnh cho mỗi sản phẩm
            List<String> limg = new ArrayList<>();
            for (Product p1 : l) {
                limg.add(pd.getImageDefault(p1.getId()));
            }

            request.setAttribute("listimg", limg);
            request.setAttribute("data", l);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("cateid", id);
            request.setAttribute("sortBy", sortBy);
        }
            request.getRequestDispatcher("productcate.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
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
