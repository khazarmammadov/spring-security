package org.khazar.SpringSecurity.token;

public interface TokenGenerator<T> {

    String generateToken(T object);
}
