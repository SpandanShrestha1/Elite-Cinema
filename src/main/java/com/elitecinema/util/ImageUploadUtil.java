package com.elitecinema.util;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * Utility class for handling image uploads
 */
public class ImageUploadUtil {
    
    private static final String UPLOAD_DIRECTORY = "images/uploads";
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB
    private static final String[] ALLOWED_EXTENSIONS = {".jpg", ".jpeg", ".png", ".gif"};
    
    /**
     * Upload an image file
     * @param request HttpServletRequest containing the file
     * @param fieldName Name of the file input field
     * @param applicationPath Application real path
     * @return Path to the uploaded file, or null if upload failed
     */
    public static String uploadImage(HttpServletRequest request, String fieldName, String applicationPath) 
            throws IOException, ServletException {
        
        Part filePart = request.getPart(fieldName);
        if (filePart == null || filePart.getSize() == 0) {
            return null;
        }
        
        // Check file size
        if (filePart.getSize() > MAX_FILE_SIZE) {
            throw new IOException("File size exceeds the maximum limit of 5MB");
        }
        
        // Get file name and extension
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        String fileExtension = getFileExtension(fileName).toLowerCase();
        
        // Check file extension
        boolean isValidExtension = false;
        for (String ext : ALLOWED_EXTENSIONS) {
            if (fileExtension.equals(ext)) {
                isValidExtension = true;
                break;
            }
        }
        
        if (!isValidExtension) {
            throw new IOException("Invalid file type. Allowed types: JPG, JPEG, PNG, GIF");
        }
        
        // Generate unique file name
        String uniqueFileName = UUID.randomUUID().toString() + fileExtension;
        
        // Create upload directory if it doesn't exist
        String uploadPath = applicationPath + File.separator + UPLOAD_DIRECTORY;
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        
        // Save the file
        String filePath = uploadPath + File.separator + uniqueFileName;
        filePart.write(filePath);
        
        // Return the relative path to the file
        return UPLOAD_DIRECTORY + "/" + uniqueFileName;
    }
    
    /**
     * Delete an image file
     * @param imagePath Path to the image file
     * @param applicationPath Application real path
     * @return true if deletion successful, false otherwise
     */
    public static boolean deleteImage(String imagePath, String applicationPath) {
        if (imagePath == null || imagePath.isEmpty()) {
            return false;
        }
        
        try {
            String fullPath = applicationPath + File.separator + imagePath;
            return Files.deleteIfExists(Paths.get(fullPath));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Get file extension from file name
     * @param fileName File name
     * @return File extension including the dot (e.g., ".jpg")
     */
    private static String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex > 0) {
            return fileName.substring(lastDotIndex);
        }
        return "";
    }
}
