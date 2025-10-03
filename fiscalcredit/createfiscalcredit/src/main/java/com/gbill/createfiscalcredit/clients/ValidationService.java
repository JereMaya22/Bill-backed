package com.gbill.createfiscalcredit.clients;

import com.gbill.createfiscalcredit.exception.ConnectionFailedAuthenticationException;
import com.gbill.createfiscalcredit.exception.InvalidTokenException;
import com.gbill.createfiscalcredit.exception.InvalidUserException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class ValidationService {

    @Value("${app.client.validation}")
    private String validationUrl;

    private final RestTemplate restTemplate;

    public ValidationService() {
        this.restTemplate = new RestTemplate();
    }

    /**
     * Valida el token de autenticación con el servicio externo
     * @param token Token de autenticación (Bearer token)
     * @return Email del usuario validado
     */
    public String validateToken(String token) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", token);
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<Map> response = restTemplate.exchange(
                    validationUrl,
                    HttpMethod.POST,
                    entity,
                    Map.class
            );

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                Map<String, Object> responseBody = response.getBody();
                return (String) responseBody.get("email");
            } else {
                throw new InvalidTokenException("Token inválido");
            }

        } catch (HttpClientErrorException.Unauthorized e) {
            throw new InvalidTokenException("Token no autorizado");
        } catch (HttpClientErrorException.Forbidden e) {
            throw new InvalidUserException("Usuario no tiene permisos");
        } catch (Exception e) {
            throw new ConnectionFailedAuthenticationException("Error al conectar con el servicio de autenticación: " + e.getMessage());
        }
    }
}
