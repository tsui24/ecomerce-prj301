package controller;

import dal.DetailDAO;
import dal.ProductDAO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Product;
import model.ProductOption;
import model.ProductOptionValue;
import model.ProductVariant;
import model.User;

@WebServlet(name = "DetailProduct", urlPatterns = {"/detail"})
public class DetailProduct extends HttpServlet {

    @Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    response.setCharacterEncoding("UTF-8");

    DetailDAO detailDAO = new DetailDAO();
    ProductDAO productDAO = new ProductDAO();
    String idRaw = request.getParameter("id");
    String sku = request.getParameter("sku");

    // Lấy user từ session
    HttpSession session = request.getSession();
    User user = (User) session.getAttribute("user");
    
    // Kiểm tra xem user có tồn tại không và lấy userId
    Integer userId = null;
    if (user != null) {
        userId = user.getId();  // Lấy userId nếu user không null
    } else {
        // Người dùng chưa đăng nhập, bạn có thể xử lý theo cách của bạn, chẳng hạn:
        System.out.println("User chưa đăng nhập");
    }

    try {
        int productId = Integer.parseInt(idRaw);
        if (sku == null || sku.isEmpty()) {
            sku = "default";
        }

        String[] skuParts = sku.split("-");

        // Lấy thông tin sản phẩm
        Product product = detailDAO.getProductDetai(productId);
        if (product == null) {
            throw new Exception("Không tìm thấy sản phẩm với ID: " + productId);
        }

        String defaultImage = detailDAO.getImgForProductDetail(productId, skuParts[skuParts.length - 1]);
        List<ProductOption> options = detailDAO.getListOption(productId);

        if (options == null || options.isEmpty()) {
            request.setAttribute("options", new ArrayList<>()); 
            request.setAttribute("optionValues", new ArrayList<>());  
        } else {
            List<ProductOptionValue> optionValues = detailDAO.getProductOptionValues(options.get(0).getId());
            request.setAttribute("options", options);
            request.setAttribute("optionValues", optionValues);
        }
        
        ProductVariant variant = detailDAO.getProductVariant(productId, sku);
        List<Product> relatedProducts = detailDAO.getListProductRelate(productId, product.getBrand());

        List<String> relatedImages = new ArrayList<>();
        for (Product p : relatedProducts) {
            String img = productDAO.getImageDefault(p.getId());
            relatedImages.add(img != null ? img : "default.jpg");
        }
        
        // Đưa userId vào request
        request.setAttribute("userId", userId); // Gửi userId đến JSP

        request.setAttribute("product", product);
        request.setAttribute("image", defaultImage != null ? defaultImage : "default.jpg");
        request.setAttribute("variant", variant);
        request.setAttribute("relate", relatedProducts);
        request.setAttribute("listimgrelate", relatedImages);

        request.getRequestDispatcher("productdetail.jsp").forward(request, response);

    } catch (NumberFormatException e) {
        response.sendRedirect("error1.jsp");
    } catch (Exception e) {
        e.printStackTrace();
        response.sendRedirect("error2.jsp");
    }
}



    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
