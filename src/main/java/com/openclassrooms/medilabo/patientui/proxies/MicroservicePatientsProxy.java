package com.openclassrooms.medilabo.patientui.proxies;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.openclassrooms.medilabo.patientui.beans.PatientBean;
import com.openclassrooms.medilabo.patientui.config.FeignAuthConfig;

/**
 * Proxy Feign pour communiquer avec le microservice de gestion des patients.
 * 
 * Cette interface permet à l’application PatientUI d’interagir avec le service
 * medilabo-patient-service via la gateway.
 *
 * Elle utilise la configuration {@link FeignAuthConfig} pour ajouter
 * automatiquement un en-tête d’authentification Basic à chaque requête
 * sortante.
 *
 */
@FeignClient(name = "medilabo-patient-service", url = "http://gateway:8081/medilabo-patient-service", configuration = FeignAuthConfig.class)
public interface MicroservicePatientsProxy {

	/**
	 * Récupère la liste de tous les patients enregistrés.
	 *
	 * @return liste des PatientBean existants
	 */
	@GetMapping(value = "/patient/all")
	List<PatientBean> getAllPatients();

	/**
	 * Récupère un patient à partir de son identifiant unique.
	 *
	 * @param id identifiant du patient
	 * @return PatientBean correspondant au patient trouvé
	 */
	@GetMapping(value = "/patient/{id}")
	PatientBean getPatientById(@PathVariable("id") Integer id);

	/**
	 * Crée un nouveau patient dans le système.
	 *
	 * @param patient objet PatientBean contenant les informations du patient
	 * @return PatientBean correspondant au patient créé
	 */
	@PostMapping(value = "/patient")
	PatientBean savePatient(@RequestBody PatientBean patient);

	/**
	 * Met à jour les informations d’un patient existant.
	 *
	 * @param id      identifiant du patient à mettre à jour
	 * @param patient objet PatientBean contenant les nouvelles informations
	 * @return PatientBean mis à jour
	 */
	@PutMapping(value = "/patient/{id}")
	PatientBean updatePatient(@PathVariable("id") Integer id, @RequestBody PatientBean patient);
}
