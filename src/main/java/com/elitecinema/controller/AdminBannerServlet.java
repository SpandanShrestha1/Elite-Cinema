package com.elitecinema.controller;

import com.elitecinema.dao.BannerDAO;
import com.elitecinema.dao.BannerDAOImpl;
import com.elitecinema.model.Banner;
import com.elitecinema.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * Servlet for admin banner management
 */
@WebServlet(name = "AdminBannerServlet", urlPatterns = {"/admin/banners", "/admin/banner/*"})
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024, // 1 MB
    maxFileSize = 5 * 1024 * 1024,   // 5 MB
    maxRequestSize = 10 * 1024 * 1024 // 10 MB
)
public class AdminBannerServlet extends HttpServlet {

    private BannerDAO bannerDAO = new BannerDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Check if user is logged in and is admin
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null || 
                !((User) session.getAttribute("user")).isAdmin()) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        String pathInfo = request.getPathInfo();
        String servletPath = request.getServletPath();
        
        if (servletPath.equals("/admin/banners")) {
            // List all banners
            List<Banner> banners = bannerDAO.getAllBanners();
            request.setAttribute("banners", banners);
            request.getRequestDispatcher("/WEB-INF/views/admin/admin-banners.jsp").forward(request, response);
        } else if (pathInfo != null) {
            if (pathInfo.equals("/add")) {
                // Show add banner form
                request.getRequestDispatcher("/WEB-INF/views/admin/admin-banner-form.jsp").forward(request, response);
            } else if (pathInfo.equals("/edit")) {
                // Show edit banner form
                try {
                    int bannerId = Integer.parseInt(request.getParameter("id"));
                    Banner banner = bannerDAO.getBannerById(bannerId);
                    
                    if (banner != null) {
                        request.setAttribute("banner", banner);
                        request.getRequestDispatcher("/WEB-INF/views/admin/admin-banner-form.jsp").forward(request, response);
                    } else {
                        response.sendRedirect(request.getContextPath() + "/admin/banners");
                    }
                } catch (NumberFormatException e) {
                    response.sendRedirect(request.getContextPath() + "/admin/banners");
                }
            } else if (pathInfo.equals("/delete")) {
                // Delete banner
                try {
                    int bannerId = Integer.parseInt(request.getParameter("id"));
                    
                    // Get banner to delete its image file
                    Banner banner = bannerDAO.getBannerById(bannerId);
                    if (banner != null && banner.getImagePath() != null) {
                        // Delete image file
                        String imagePath = getServletContext().getRealPath("") + File.separator + banner.getImagePath();
                        File imageFile = new File(imagePath);
                        if (imageFile.exists()) {
                            imageFile.delete();
                        }
                    }
                    
                    bannerDAO.deleteBanner(bannerId);
                    response.sendRedirect(request.getContextPath() + "/admin/banners?message=Banner deleted successfully");
                } catch (NumberFormatException e) {
                    response.sendRedirect(request.getContextPath() + "/admin/banners?error=Invalid banner ID");
                }
            } else if (pathInfo.equals("/toggle")) {
                // Toggle banner status
                try {
                    int bannerId = Integer.parseInt(request.getParameter("id"));
                    bannerDAO.toggleBannerStatus(bannerId);
                    response.sendRedirect(request.getContextPath() + "/admin/banners?message=Banner status updated successfully");
                } catch (NumberFormatException e) {
                    response.sendRedirect(request.getContextPath() + "/admin/banners?error=Invalid banner ID");
                }
            } else {
                response.sendRedirect(request.getContextPath() + "/admin/banners");
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/admin/banners");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Check if user is logged in and is admin
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null || 
                !((User) session.getAttribute("user")).isAdmin()) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        String pathInfo = request.getPathInfo();
        
        if (pathInfo != null) {
            if (pathInfo.equals("/add") || pathInfo.equals("/edit")) {
                // Process banner form submission
                String action = request.getParameter("action");
                
                if ("add".equals(action) || "edit".equals(action)) {
                    // Get form data
                    String title = request.getParameter("title");
                    String description = request.getParameter("description");
                    boolean active = request.getParameter("active") != null;
                    
                    // Validate form data
                    if (title == null || title.trim().isEmpty()) {
                        request.setAttribute("error", "Title is required");
                        request.getRequestDispatcher("/WEB-INF/views/admin/admin-banner-form.jsp").forward(request, response);
                        return;
                    }
                    
                    // Handle file upload
                    Part filePart = request.getPart("image");
                    String imagePath = null;
                    
                    if (filePart != null && filePart.getSize() > 0) {
                        // Generate unique filename
                        String fileName = UUID.randomUUID().toString() + getFileExtension(filePart);
                        
                        // Create uploads directory if it doesn't exist
                        String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads" + File.separator + "banners";
                        File uploadDir = new File(uploadPath);
                        if (!uploadDir.exists()) {
                            uploadDir.mkdirs();
                        }
                        
                        // Save file
                        filePart.write(uploadPath + File.separator + fileName);
                        
                        // Set image path
                        imagePath = "uploads/banners/" + fileName;
                    }
                    
                    if ("add".equals(action)) {
                        // Create new banner
                        if (imagePath == null) {
                            request.setAttribute("error", "Image is required");
                            request.getRequestDispatcher("/WEB-INF/views/admin/admin-banner-form.jsp").forward(request, response);
                            return;
                        }
                        
                        Banner banner = new Banner(title, description, imagePath, active);
                        int bannerId = bannerDAO.createBanner(banner);
                        
                        if (bannerId > 0) {
                            response.sendRedirect(request.getContextPath() + "/admin/banners?message=Banner added successfully");
                        } else {
                            request.setAttribute("error", "Failed to add banner");
                            request.getRequestDispatcher("/WEB-INF/views/admin/admin-banner-form.jsp").forward(request, response);
                        }
                    } else if ("edit".equals(action)) {
                        // Update existing banner
                        try {
                            int bannerId = Integer.parseInt(request.getParameter("bannerId"));
                            Banner banner = bannerDAO.getBannerById(bannerId);
                            
                            if (banner != null) {
                                // Update banner data
                                banner.setTitle(title);
                                banner.setDescription(description);
                                banner.setActive(active);
                                
                                // Update image if provided
                                if (imagePath != null) {
                                    // Delete old image file
                                    if (banner.getImagePath() != null) {
                                        String oldImagePath = getServletContext().getRealPath("") + File.separator + banner.getImagePath();
                                        File oldImageFile = new File(oldImagePath);
                                        if (oldImageFile.exists()) {
                                            oldImageFile.delete();
                                        }
                                    }
                                    
                                    // Set new image path
                                    banner.setImagePath(imagePath);
                                }
                                
                                boolean updated = bannerDAO.updateBanner(banner);
                                
                                if (updated) {
                                    response.sendRedirect(request.getContextPath() + "/admin/banners?message=Banner updated successfully");
                                } else {
                                    request.setAttribute("error", "Failed to update banner");
                                    request.setAttribute("banner", banner);
                                    request.getRequestDispatcher("/WEB-INF/views/admin/admin-banner-form.jsp").forward(request, response);
                                }
                            } else {
                                response.sendRedirect(request.getContextPath() + "/admin/banners?error=Banner not found");
                            }
                        } catch (NumberFormatException e) {
                            response.sendRedirect(request.getContextPath() + "/admin/banners?error=Invalid banner ID");
                        }
                    }
                } else {
                    response.sendRedirect(request.getContextPath() + "/admin/banners");
                }
            } else {
                response.sendRedirect(request.getContextPath() + "/admin/banners");
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/admin/banners");
        }
    }
    
    /**
     * Get file extension from Part
     * @param part Part object
     * @return File extension with dot (e.g., ".jpg")
     */
    private String getFileExtension(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        
        for (String item : items) {
            if (item.trim().startsWith("filename")) {
                String fileName = item.substring(item.indexOf("=") + 2, item.length() - 1);
                int dotIndex = fileName.lastIndexOf(".");
                if (dotIndex > 0) {
                    return fileName.substring(dotIndex);
                }
            }
        }
        
        return "";
    }
}
