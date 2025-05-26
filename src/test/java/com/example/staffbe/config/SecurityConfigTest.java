package com.example.staffbe.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SecurityConfigTest {

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private SecurityConfig securityConfig;

    @Test
    void testSecurityFilterChain() throws Exception {
        // Arrange
        HttpSecurity httpSecurity = mock(HttpSecurity.class);
        DefaultSecurityFilterChain filterChain = mock(DefaultSecurityFilterChain.class);
        when(httpSecurity.cors(any())).thenReturn(httpSecurity);
        when(httpSecurity.csrf(any())).thenReturn(httpSecurity);
        when(httpSecurity.sessionManagement(any())).thenReturn(httpSecurity);
        when(httpSecurity.authorizeHttpRequests(any())).thenReturn(httpSecurity);
        when(httpSecurity.addFilterBefore(any(JwtAuthenticationFilter.class), eq(UsernamePasswordAuthenticationFilter.class)))
                .thenReturn(httpSecurity);
        when(httpSecurity.build()).thenReturn(filterChain);

        // Act
        SecurityFilterChain result = securityConfig.securityFilterChain(httpSecurity);

        // Assert
        assertNotNull(result);
        verify(httpSecurity).cors(any());
        verify(httpSecurity).csrf(any());
        verify(httpSecurity).sessionManagement(any());
        verify(httpSecurity).authorizeHttpRequests(any());
        verify(httpSecurity).addFilterBefore(any(JwtAuthenticationFilter.class), eq(UsernamePasswordAuthenticationFilter.class));
    }

    @Test
    void testJwtAuthenticationFilter() {
        // Act
        JwtAuthenticationFilter filter = securityConfig.jwtAuthenticationFilter();

        // Assert
        assertNotNull(filter);
    }

    @Test
    void testCorsConfigurationSource() {
        // Act
        CorsConfigurationSource corsConfig = securityConfig.corsConfigurationSource();

        // Assert
        assertNotNull(corsConfig);
    }
} 