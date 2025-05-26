package com.example.staffbe.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    @InjectMocks
    private JwtService jwtService;

    private static final String SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";
    private static final long EXPIRATION = 86400000; // 24 hours

    @BeforeEach
    void setUp() {
        // Set the JWT_TOKEN environment variable for testing
        System.setProperty("JWT_TOKEN", Base64.getEncoder().encodeToString(SECRET_KEY.getBytes()));
        ReflectionTestUtils.setField(jwtService, "key", Keys.hmacShaKeyFor(SECRET_KEY.getBytes()));
    }

    @Test
    void testExtractUserIdFromToken() {
        // Arrange
        String token = generateTestToken("user123", "test@example.com", "ADMIN", "John Doe");
        
        // Act
        String userId = jwtService.extractUserIdFromToken(token);
        
        // Assert
        assertEquals("user123", userId);
    }

    @Test
    void testExtractUserIdFromToken_InvalidToken() {
        // Act
        String userId = jwtService.extractUserIdFromToken("invalid.token.here");
        
        // Assert
        assertNull(userId);
    }

    @Test
    void testExtractAllClaims() {
        // Arrange
        String token = generateTestToken("user123", "test@example.com", "ADMIN", "John Doe");
        
        // Act
        Claims claims = jwtService.extractAllClaims(token);
        
        // Assert
        assertNotNull(claims);
        assertEquals("user123", claims.get("userId", String.class));
        assertEquals("test@example.com", claims.get("email", String.class));
        assertEquals("ADMIN", claims.get("role", String.class));
        assertEquals("John Doe", claims.get("fullName", String.class));
    }

    @Test
    void testExtractAllClaims_InvalidToken() {
        // Act
        Claims claims = jwtService.extractAllClaims("invalid.token.here");
        
        // Assert
        assertNull(claims);
    }

    @Test
    void testValidateToken() {
        // Arrange
        String token = generateTestToken("user123", "test@example.com", "ADMIN", "John Doe");
        
        // Act & Assert
        assertTrue(jwtService.validateToken(token));
    }

    @Test
    void testValidateToken_InvalidToken() {
        // Act & Assert
        assertFalse(jwtService.validateToken("invalid.token.here"));
    }

    @Test
    void testExtractEmail() {
        // Arrange
        String token = generateTestToken("user123", "test@example.com", "ADMIN", "John Doe");
        
        // Act
        String email = jwtService.extractEmail(token);
        
        // Assert
        assertEquals("test@example.com", email);
    }

    @Test
    void testExtractEmail_InvalidToken() {
        // Act
        String email = jwtService.extractEmail("invalid.token.here");
        
        // Assert
        assertNull(email);
    }

    @Test
    void testExtractRole() {
        // Arrange
        String token = generateTestToken("user123", "test@example.com", "ADMIN", "John Doe");
        
        // Act
        String role = jwtService.extractRole(token);
        
        // Assert
        assertEquals("ADMIN", role);
    }

    @Test
    void testExtractRole_InvalidToken() {
        // Act
        String role = jwtService.extractRole("invalid.token.here");
        
        // Assert
        assertNull(role);
    }

    @Test
    void testExtractFullName() {
        // Arrange
        String token = generateTestToken("user123", "test@example.com", "ADMIN", "John Doe");
        
        // Act
        String fullName = jwtService.extractFullName(token);
        
        // Assert
        assertEquals("John Doe", fullName);
    }

    @Test
    void testExtractFullName_InvalidToken() {
        // Act
        String fullName = jwtService.extractFullName("invalid.token.here");
        
        // Assert
        assertNull(fullName);
    }

    private String generateTestToken(String userId, String email, String role, String fullName) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("email", email);
        claims.put("role", role);
        claims.put("fullName", fullName);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }
} 