/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.CartDAO;
import dal.DetailDAO;
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
import model.ProductVariant;
import model.User;

/**
 *
 * @author admin
 */
@WebServlet(name = "CheckoutServlet", urlPatterns = {"/checkout"})
public class CheckoutServlet extends HttpServlet {

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
            out.println("<title>Servlet PaymentServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PaymentServlet at " + request.getContextPath() + "</h1>");
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
        CartDAO cd = new CartDAO();
        DetailDAO dd = new DetailDAO();
        String[] variantIds = request.getParameterValues("variantId");
        String[] quantities = request.getParameterValues("quantity");

        // Validate if items are selected in the cart
        if (variantIds == null || quantities == null || variantIds.length == 0) {
            request.setAttribute("errorMessage", "Vui lòng chọn ít nhất một sản phẩm.");
            request.getRequestDispatcher("cart.jsp").forward(request, response);
            return;
        }

        List<ProductVariant> variants = new ArrayList<>();
        List<Integer> quantitys = new ArrayList<>();
        List<String> imgList = new ArrayList<>();
        double sum = 0;
        try {
            for (int i = 0; i < variantIds.length; i++) {
                int variantId = Integer.parseInt(variantIds[i].trim());
                int quantity = Integer.parseInt(quantities[i].trim());

                if (quantity <= 0) {
                    throw new NumberFormatException("Số lượng phải lớn hơn 0.");
                }
                ProductVariant pv = cd.getProductVariantById(variantId);
                sum += pv.getPrice() * quantity;
                String sku = pv.getSku();
                String[] need = sku.split("-");
                imgList.add(dd.getImgForProductDetail(pv.getProductId(), need[need.length-1]));
                variants.add(pv);
                quantitys.add(quantity);
            }
        } catch (NumberFormatException e) {
            // Handle invalid input (non-integer or negative values)
            request.setAttribute("errorMessage", "Dữ liệu sản phẩm không hợp lệ: " + e.getMessage());
            request.getRequestDispatcher("cart.jsp").forward(request, response);
            return;
        }
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        request.setAttribute("total", sum);
        request.setAttribute("user", user);
        request.setAttribute("variantItems",  variants);
        request.setAttribute("quantity", quantitys);
        request.setAttribute("limg", imgList);
        request.getRequestDispatcher("checkout.jsp").forward(request, response);
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
