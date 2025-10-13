package com.openclassrooms.medilabo.patientui.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Base64;

/**
 * Configuration Feign pour ajouter automatiquement une authentification Basic à
 * chaque requête envoyée par les clients Feign.
 *
 * Cette classe crée un {@link RequestInterceptor} qui ajoute un en tête HTTP
 * Authorization: Basic ... à toutes les requêtes sortantes.
 *
 * Les identifiants sont ici codés en dur pour simplifier la démonstration. En
 * production, ils doivent être externalisés (ex. : variables d’environnement,
 * configuration chiffrée dans {@code application.yml}
 */
@Configuration
public class FeignAuthConfig {

	/**
	 * Définit un intercepteur Feign pour injecter les informations
	 * d’authentification Basic dans les requêtes HTTP sortantes.
	 *
	 * @return un {@link RequestInterceptor} configuré pour l’authentification Basic
	 */
	@Bean
	public RequestInterceptor basicAuthRequestInterceptor() {
		return template -> {
			String username = "admin";
			String password = "admin";
			String auth = username + ":" + password;
			String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
			template.header("Authorization", "Basic " + encodedAuth);
		};
	}
}
