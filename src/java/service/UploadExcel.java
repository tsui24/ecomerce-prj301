/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package service;

//import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.*;

/**
 *
 * @author admin
 */
@MultipartConfig
@WebServlet(name="UploadExcel", urlPatterns={"/uploadExcel"})
public class UploadExcel extends HttpServlet {
   
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
            out.println("<title>Servlet UploadExcel</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UploadExcel at " + request.getContextPath () + "</h1>");
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
//        Part filePart = request.getPart("file"); // Lấy file từ form upload
//        String tableName = request.getParameter("tableName"); // Tên bảng từ form
//        if (filePart != null && tableName != null) {
//            try (InputStream inputStream = filePart.getInputStream();
//                 Workbook workbook = new XSSFWorkbook(inputStream)) {
//
//                StringWriter sqlWriter = new StringWriter();
//
//                Sheet sheet = workbook.getSheetAt(0);
//                Row headerRow = sheet.getRow(0);
//
//                // Lấy tên các cột
//                int columnCount = headerRow.getPhysicalNumberOfCells();
//                String[] columns = new String[columnCount];
//                for (int i = 0; i < columnCount; i++) {
//                    columns[i] = headerRow.getCell(i).getStringCellValue();
//                }
//
//                // Đọc các dòng dữ liệu và tạo câu lệnh INSERT
//                for (int r = 1; r <= sheet.getLastRowNum(); r++) {
//                    Row row = sheet.getRow(r);
//                    StringBuilder sql = new StringBuilder("INSERT INTO " + tableName + " (");
//
//                    // Thêm tên cột vào câu lệnh
//                    for (int i = 0; i < columnCount; i++) {
//                        sql.append(columns[i]);
//                        if (i < columnCount - 1) {
//                            sql.append(", ");
//                        }
//                    }
//                    sql.append(") VALUES (");
//
//                    // Thêm giá trị vào câu lệnh
//                    for (int i = 0; i < columnCount; i++) {
//                        Cell cell = row.getCell(i);
//                        if (cell != null) {
//                            switch (cell.getCellType()) {
//                                case STRING:
//                                    sql.append("'").append(cell.getStringCellValue().replace("'", "''")).append("'");
//                                    break;
//                                case NUMERIC:
//                                    sql.append(cell.getNumericCellValue());
//                                    break;
//                                case BOOLEAN:
//                                    sql.append(cell.getBooleanCellValue());
//                                    break;
//                                default:
//                                    sql.append("NULL");
//                            }
//                        } else {
//                            sql.append("NULL");
//                        }
//                        if (i < columnCount - 1) {
//                            sql.append(", ");
//                        }
//                    }
//                    sql.append(");");
//                    sqlWriter.write(sql.toString() + "\n");
//                }
//
//                // Xuất file SQL cho client
//                response.setContentType("text/sql");
//                response.setHeader("Content-Disposition", "attachment; filename=\"" + tableName + "_data.sql\"");
//                response.getWriter().write(sqlWriter.toString());
//
//            } catch (Exception e) {
//                e.printStackTrace();
//                response.getWriter().write("Lỗi khi xử lý file: " + e.getMessage());
//            }
//        } else {
//            response.getWriter().write("Vui lòng tải lên file Excel và cung cấp tên bảng.");
//        }
//    }
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
