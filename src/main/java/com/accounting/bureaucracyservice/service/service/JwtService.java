package com.accounting.bureaucracyservice.service.service;

import org.springframework.security.core.Authentication;

public interface JwtService {
    Authentication parseToken(String jwt);
}
