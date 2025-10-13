package com.openclassrooms.medilabo.patientui.proxies;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.openclassrooms.medilabo.patientui.beans.NoteBean;
import com.openclassrooms.medilabo.patientui.config.FeignAuthConfig;

/**
 * Proxy Feign pour communiquer avec le microservice de gestion des notes
 * médicales.
 * 
 * Ce client Feign permet à l’application PatientUI d’interagir avec le service
 * medilabo-note-service via la gateway.
 *
 * Il utilise la configuration {@link FeignAuthConfig} pour ajouter un en tête
 * d’authentification Basic à chaque requête sortante.
 */
@FeignClient(name = "medilabo-note-service", url = "http://gateway:8081/medilabo-note-service", configuration = FeignAuthConfig.class)
public interface MicroserviceNotesProxy {

	/**
	 * Récupère la liste de toutes les notes associées à un patient.
	 *
	 * @param patientId identifiant du patient
	 * @return liste des NoteBean correspondant au patient
	 */
	@GetMapping(value = "/notes/patient/{patientId}")
	List<NoteBean> getNotesByPatientId(@PathVariable("patientId") String patientId);

	/**
	 * Récupère la liste complète de toutes les notes existantes.
	 *
	 * @return liste de toutes les NoteBean disponibles
	 */
	@GetMapping(value = "/notes/all")
	List<NoteBean> getAllNotes();

	/**
	 * Récupère une note à partir de son identifiant.
	 *
	 * @param id identifiant unique de la note
	 * @return objet NoteBean correspondant
	 */
	@GetMapping(value = "/notes/{id}")
	NoteBean getNoteById(@PathVariable("id") String id);

	/**
	 * Crée une nouvelle note.
	 *
	 * @param note objet NoteBean contenant les informations à enregistrer
	 * @return la NoteBean créée avec son identifiant généré
	 */
	@PostMapping(value = "/notes")
	NoteBean saveNote(@RequestBody NoteBean note);

	/**
	 * Met à jour une note existante identifiée par son ID.
	 *
	 * @param id   identifiant de la note à mettre à jour
	 * @param note objet NoteBean contenant les données modifiées
	 * @return la NoteBean mise à jour
	 */
	@PutMapping(value = "/notes/{id}")
	NoteBean updateNote(@PathVariable("id") String id, @RequestBody NoteBean note);

}
