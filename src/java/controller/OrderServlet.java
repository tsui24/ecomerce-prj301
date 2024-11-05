/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.BillDAO;
import dal.BillDetailDAO;
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
import java.util.ArrayList;
import java.util.List;
import model.Bill;
import model.BillDetail;
import model.ProductVariant;
import model.User;

/**
 *
 * @author admin
 */
@WebServlet(name = "OrderServlet", urlPatterns = {"/order"})
public class OrderServlet extends HttpServlet {

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
            out.println("<title>Servlet OrderServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet OrderServlet at " + request.getContextPath() + "</h1>");
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
    BillDAO b = new BillDAO();
    BillDetailDAO bd = new BillDetailDAO();
    UserDAO ud = new UserDAO();

    String status = request.getParameter("status");
    String billId = request.getParameter("billId");

    List<Bill> l;

    // Check if both status and billId are provided, or if only one is used.
    if (billId != null && !billId.isEmpty()) {
        try {
            int id = Integer.parseInt(billId);
            l = new ArrayList<>();
            Bill bill = b.getBillById(id); // Assuming this method exists.
            if (bill != null) {
                l.add(bill);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid Bill ID format.");
            l = new ArrayList<>();
        }
    } else if (status != null && !status.isEmpty() && !status.equals("most")) {
        l = b.getAllBillSearch(status);
    } 
//    else if(status.equals("most")){
//        
//    }    
    else {
        l = b.getAllBill();
    }

    List<String> ldetail = new ArrayList<>();
    List<Double> ltotal = new ArrayList<>();
    List<String> lfullname = new ArrayList<>();

    // Process each bill to generate details, totals, and user names.
    for (Bill i : l) {
        List<BillDetail> bdetail = bd.getBillDetailByBillId(i.getId());

        StringBuilder detail = new StringBuilder();
        double totalPrice = 0;

        for (BillDetail j : bdetail) {
            ProductVariant p = b.getProductVariantById(j.getProductVariantId());
            detail.append(p.getSku()).append(" ").append(j.getQuantity()).append(", ");
            totalPrice += j.getQuantity() * p.getPrice();
        }

        lfullname.add(ud.getFullnameById(i.getUserId()));
        ldetail.add(detail.toString());
        ltotal.add(totalPrice);
    }
    MessageDAO m = new MessageDAO();
        int is_read = m.getQuantitiesMessages(user.getId());
        request.setAttribute("isread", is_read);
    request.setAttribute("lfullname", lfullname);
    request.setAttribute("lbill", l);
    request.setAttribute("ldetail", ldetail);
    request.setAttribute("ltotal", ltotal);
    request.setAttribute("selectedStatus", status); 
    request.setAttribute("searchedBillId", billId); 

    request.getRequestDispatcher("admin-order.jsp").forward(request, response);
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
        BillDAO billDAO = new BillDAO();
        BillDetailDAO bd = new BillDetailDAO();
        String status = request.getParameter("status");
        String orderIdStr = request.getParameter("id");
        
        int orderId;
        try {
            orderId = Integer.parseInt(orderIdStr);
            if (status.equals("cancel")) {
                billDAO.updateStatusBill(orderId, status);
                List<BillDetail> lbiDetails = bd.getBillDetailByBillId(orderId);
                for (BillDetail i : lbiDetails) {
                    ProductVariant pv = billDAO.getProductVariantById(i.getProductVariantId());
                    int quantity = pv.getQuantity() + i.getQuantity();
                    int sold = pv.getSoldQuantity() - i.getQuantity();
                    billDAO.updateQuantity(quantity, sold, i.getProductVariantId());
                }
            } else {
                billDAO.updateStatusBill(orderId, status);
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Order ID");
            return;
        }

        response.sendRedirect("order");
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
