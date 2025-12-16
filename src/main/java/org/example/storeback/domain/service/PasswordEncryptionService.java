package org.example.storeback.domain.service;


public interface PasswordEncryptionService {
    String encryptPassword(String rawPassword);
    boolean matches(String rawPassword, String encodedPassword);
}

