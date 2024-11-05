/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dal.BillDAO;
import dal.BillDetailDAO;
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
import java.util.Date;
import java.util.List;
import model.Bill;
import model.ProductVariant;
import model.User;

/**
 *
 * @author admin
 */
@WebServlet(name="BillServlet", urlPatterns={"/bill"})
public class BillServlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
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
            out.println("<title>Servlet BillServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet BillServlet at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        DetailDAO dd = new DetailDAO();
        BillDAO billDAO = new BillDAO();
        BillDetailDAO bdDAO = new BillDetailDAO();
        String customerName = request.getParameter("customerName");
        String customerPhone = request.getParameter("customerPhone");
        String customerAddress = request.getParameter("customerAddress");
        double total = Double.parseDouble(request.getParameter("total"));
        int totalProduct = Integer.parseInt(request.getParameter("totalProduct"));
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");        
        Bill bill = new Bill(new Date(), user.getId(), customerPhone, customerAddress, "processing");
        String[] variantIdsArray = request.getParameterValues("variantIds");
        String[] quantitiesArray = request.getParameterValues("quantities");
        Integer billId = billDAO.insertBill(bill);
        List<Integer> variantIds = new ArrayList<>();
        List<Integer> quantities = new ArrayList<>();

        try {
            for (String id : variantIdsArray) {
                variantIds.add(Integer.parseInt(id));
            }
            for (String qty : quantitiesArray) {
                quantities.add(Integer.parseInt(qty));
            }
            for(int i = 0; i < variantIds.size(); i++){
                bdDAO.insertBillDetai(billId, variantIds.get(i), quantities.get(i));
                int quantity = dd.getVariant(variantIds.get(i)).getQuantity() - quantities.get(i);
                int sold_quantity =dd.getVariant(variantIds.get(i)).getSoldQuantity() + quantities.get(i);
                billDAO.updateQuantity(quantity, sold_quantity, variantIds.get(i));
            }
        } catch (Exception e) {
        }
        if (billId != null) {
            System.out.println("Bill created with ID: " + billId);
            response.sendRedirect(request.getContextPath() +"/producthome");
        } else {
            response.getWriter().println("Thanh toán thất bại!");
        }
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
