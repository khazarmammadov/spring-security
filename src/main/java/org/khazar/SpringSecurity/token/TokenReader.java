package org.khazar.SpringSecurity.token;

public interface TokenReader<T> {
    T read(String token);
}
