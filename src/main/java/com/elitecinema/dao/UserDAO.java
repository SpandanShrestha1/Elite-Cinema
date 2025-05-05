package com.elitecinema.dao;

import com.elitecinema.model.User;
import java.util.List;

/**
 * Interface for User data access operations
 */
public interface UserDAO {
    
    /**
     * Create a new user
     * @param user User object to create
     * @return User ID if successful, -1 if failed
     */
    int createUser(User user);
    
    /**
     * Get user by ID
     * @param userId User ID
     * @return User object if found, null otherwise
     */
    User getUserById(int userId);
    
    /**
     * Get user by email
     * @param email User email
     * @return User object if found, null otherwise
     */
    User getUserByEmail(String email);
    
    /**
     * Update user information
     * @param user User object with updated information
     * @return true if successful, false otherwise
     */
    boolean updateUser(User user);
    
    /**
     * Delete user by ID
     * @param userId User ID
     * @return true if successful, false otherwise
     */
    boolean deleteUser(int userId);
    
    /**
     * Get all users
     * @return List of all users
     */
    List<User> getAllUsers();
    
    /**
     * Authenticate user with email and password
     * @param email User email
     * @param password User password
     * @return User object if authentication successful, null otherwise
     */
    User authenticate(String email, String password);
}
