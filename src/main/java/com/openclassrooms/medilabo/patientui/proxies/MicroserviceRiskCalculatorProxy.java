package com.openclassrooms.medilabo.patientui.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.openclassrooms.medilabo.patientui.beans.PatientRiskLevelBean;
import com.openclassrooms.medilabo.patientui.config.FeignAuthConfig;

/**
 * Proxy Feign pour interagir avec le microservice d'évaluation du risque de
 * diabète.
 * 
 * Cette interface permet à l’application PatientUI de communiquer avec le
 * service medilabo-risk-diabetes-service via la gateway.
 *
 * Elle utilise la configuration {@link FeignAuthConfig} afin d’ajouter
 * automatiquement un en-tête d’authentification Basic à chaque requête
 * sortante.
 */
@FeignClient(name = "medilabo-risk-diabetes-service", url = "http://gateway:8081/medilabo-risk-diabetes-service", configuration = FeignAuthConfig.class)
public interface MicroserviceRiskCalculatorProxy {

	/**
	 * Récupère le niveau de risque de diabète pour un patient donné.
	 *
	 * @param patientId identifiant du patient dont on souhaite évaluer le risque
	 * @return un objet PatientRiskLevelBean contenant le niveau de risque et les
	 *         détails associés
	 */
	@GetMapping(value = "/calculateRisk/{patientId}")
	PatientRiskLevelBean getRiskLevelByPatientId(@PathVariable String patientId);
}
