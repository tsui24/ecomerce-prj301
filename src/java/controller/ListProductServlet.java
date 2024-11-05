/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.MessageDAO;
import dal.ProductDAO;
import dal.ProductImageDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.Product;
import model.ProductImage;
import model.User;

/**
 *
 * @author admin
 */
@MultipartConfig(
 fileSizeThreshold = 1024 * 1024,  // 1 MB
    maxFileSize = 1024 * 1024 * 10,   // 10 MB
    maxRequestSize = 1024 * 1024 * 50 )
@WebServlet(name = "ListProductServlet", urlPatterns = {"/control"})
public class ListProductServlet extends HttpServlet {

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
            out.println("<title>Servlet ListProductServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ListProductServlet at " + request.getContextPath() + "</h1>");
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
    private static final long serialVersionUID = 1L;
    private ProductDAO productDAO;

    public void init() {
        productDAO = new ProductDAO();
    }

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
        String search = request.getParameter("search");
        
        List<Product> products = productDAO.getAllProduct(search);
        MessageDAO m = new MessageDAO();
        int is_read = m.getQuantitiesMessages(user.getId());
        
        request.setAttribute("isread", is_read);
        request.setAttribute("listp", products);
        request.getRequestDispatcher("admin-product.jsp").forward(request, response);
    }
    private static final String SAVE_DIR = "D:\\FPTU\\TemNo_2\\Java core\\ProjectEnd\\web\\asset\\image";

    // Xử lý POST request để thêm/sửa sản phẩm
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("add".equals(action)) {
            // Thêm sản phẩm mới
            String name = request.getParameter("name");
            String slug = request.getParameter("slug");
            String description = request.getParameter("description");
            int categoryId = Integer.parseInt(request.getParameter("categoryId"));
            String storage = request.getParameter("storage");
            double priceDefault = Double.parseDouble(request.getParameter("priceDefault"));
            String brand = request.getParameter("brand");
            Date currentDate = new Date();

            Product newProduct = new Product(currentDate, name, slug, description, categoryId, storage, priceDefault, brand);
            productDAO.addProduct(newProduct);

        } else if ("edit".equals(action)) {
            // Cập nhật sản phẩm hiện có
            String id_raw = request.getParameter("id");
            String categoryId_raw = request.getParameter("categoryId");
            int id, categoryId;
            try {
                id = Integer.parseInt(id_raw);
                String name = request.getParameter("name");
                String slug = request.getParameter("slug");
                String description = request.getParameter("description");
                categoryId = Integer.parseInt(categoryId_raw);
                String storage = request.getParameter("storage");
                double priceDefault = Double.parseDouble(request.getParameter("priceDefault"));
                String brand = request.getParameter("brand");
                Product existingProduct = new Product(id, name, slug, description, categoryId, storage, priceDefault, brand);
                productDAO.updateProduct(existingProduct);

            } catch (Exception e) {
                System.out.println("Loi");
            }

        } else if ("delete".equals(action)) {
            // Xử lý xóa sản phẩm
            String id_raw1 = request.getParameter("id");
            int id;
            try {
                id = Integer.parseInt(id_raw1);
                productDAO.deleteProduct(id);
            } catch (Exception e) {
                System.out.println("Loi");
            }

        } else if ("addImage".equals(action)) {
            ProductImageDAO pid = new ProductImageDAO();
            String productId_raw = request.getParameter("productId");
            String altText = request.getParameter("alt");
            String type = request.getParameter("type");
            Part filePart = request.getPart("img");
            int productId;
            
                try {
                    productId = Integer.parseInt(productId_raw);
                    if (filePart != null && filePart.getSize() > 0) {
                        String fileName = filePart.getSubmittedFileName();
                        String filePath = SAVE_DIR + File.separator + fileName;
                        File saveDir = new File(SAVE_DIR);
                        if (!saveDir.exists()) {
                            saveDir.mkdirs();
                        }

                        filePart.write(filePath);
                        ProductImage pnewImage = new ProductImage();
                        pnewImage.setProductId(productId);
                        pnewImage.setImg(fileName);
                        pnewImage.setAlt(altText);
                        pnewImage.setType(type);
                        pid.addImgProduct(pnewImage);
                    } else {
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid product ID: " + e.getMessage());
                }
            
        }

        response.sendRedirect("control");
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
