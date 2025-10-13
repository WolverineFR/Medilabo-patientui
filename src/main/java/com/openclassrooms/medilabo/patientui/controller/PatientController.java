package com.openclassrooms.medilabo.patientui.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.openclassrooms.medilabo.patientui.beans.NoteBean;
import com.openclassrooms.medilabo.patientui.beans.PatientBean;
import com.openclassrooms.medilabo.patientui.proxies.MicroserviceNotesProxy;
import com.openclassrooms.medilabo.patientui.proxies.MicroservicePatientsProxy;
import com.openclassrooms.medilabo.patientui.proxies.MicroserviceRiskCalculatorProxy;

import jakarta.validation.Valid;

/**
 * Contrôleur principal pour la gestion des patients et de leurs notes dans
 * l’interface utilisateur.
 *
 * Ce contrôleur interagit avec trois microservices via Feign :
 * MicroservicePatientsProxy — gestion des patients MicroserviceNotesProxy —
 * gestion des notes médicales MicroserviceRiskCalculatorProxy — calcul du
 * risque de diabète
 *
 * Il gère les pages de la couche vue (Thymeleaf) permettant d’afficher, créer
 * et modifier des patients ainsi que leurs notes associées.
 */
@Controller
public class PatientController {

	@Autowired
	private final MicroservicePatientsProxy patientsProxy;

	@Autowired
	private final MicroserviceNotesProxy notesProxy;

	@Autowired
	private final MicroserviceRiskCalculatorProxy riskCalcProxy;

	public PatientController(MicroservicePatientsProxy patientsProxy, MicroserviceNotesProxy notesProxy,
			MicroserviceRiskCalculatorProxy riskCalcProxy) {
		this.patientsProxy = patientsProxy;
		this.notesProxy = notesProxy;
		this.riskCalcProxy = riskCalcProxy;
	}

	/**
	 * Redirige la racine du site vers la liste des patients.
	 */
	@GetMapping("/")
	public String homeRedirect() {
		return "redirect:/patients";
	}

	/**
	 * Affiche la liste de tous les patients.
	 *
	 * @param model modèle pour injecter les données dans la vue
	 * @return nom de la vue Thymeleaf affichant la liste des patients
	 */
	@GetMapping("/patients")
	public String listPatient(Model model) {
		List<PatientBean> patients = patientsProxy.getAllPatients();
		model.addAttribute("patients", patients);
		return "patients/patient-list";
	}

	/**
	 * Affiche les détails d’un patient, ses notes et son niveau de risque.
	 *
	 * @param id    identifiant du patient
	 * @param model modèle pour injecter les données dans la vue
	 * @return page détaillant le patient
	 */
	@GetMapping("/patient/{id}")
	public String patientDetail(@PathVariable Integer id, Model model) {
		String idToString = Integer.toString(id);
		List<NoteBean> notes = notesProxy.getNotesByPatientId(idToString);
		model.addAttribute("patient", patientsProxy.getPatientById(id));
		model.addAttribute("notes", notes);
		model.addAttribute("riskLevel", riskCalcProxy.getRiskLevelByPatientId(idToString));
		return "patients/patient-info";
	}

	/**
	 * Affiche le formulaire d’ajout d’un nouveau patient.
	 */
	@GetMapping("patient/add")
	public String showAddForm(Model model) {

		model.addAttribute("patient", new PatientBean());
		return "patients/patient-add";
	}

	/**
	 * Traite la soumission du formulaire d’ajout d’un patient.
	 *
	 * @param patient le patient à créer
	 * @param result  résultats de la validation du formulaire
	 * @param model   modèle de la vue
	 */
	@PostMapping("patient/add")
	public String savePatient(@Valid @ModelAttribute("patient") PatientBean patient, BindingResult result,
			Model model) {
		if (result.hasErrors()) {
			return "patients/patient-add";
		}
		patientsProxy.savePatient(patient);
		return "redirect:/patients";
	}

	/**
	 * Affiche le formulaire de mise à jour d’un patient existant.
	 *
	 * @param id    identifiant du patient
	 * @param model modèle pour injecter les données du patient
	 */
	@GetMapping("/patient/update/{id}")
	public String showUpdateForm(@PathVariable Integer id, Model model) {
		model.addAttribute("patient", patientsProxy.getPatientById(id));
		return "patients/patient-update";
	}

	/**
	 * Traite la mise à jour d’un patient.
	 *
	 * @param id      identifiant du patient
	 * @param patient patient modifié
	 * @param result  résultats de la validation
	 */
	@PostMapping("/patient/update/{id}")
	public String updatePatient(@PathVariable Integer id, @Valid @ModelAttribute("patient") PatientBean patient,
			BindingResult result) {
		if (result.hasErrors()) {
			return "patients/patient-update";
		}
		patientsProxy.updatePatient(id, patient);
		return "redirect:/patients";
	}

	/**
	 * Affiche le formulaire d’ajout d’une note pour un patient.
	 */
	@GetMapping("/patient/{id}/note/add")
	public String showAddNoteForm(@PathVariable Integer id, Model model) {
		model.addAttribute("patient", patientsProxy.getPatientById(id));
		model.addAttribute("note", new NoteBean());
		return "notes/note-add";
	}

	/**
	 * Traite l’ajout d’une nouvelle note pour un patient.
	 *
	 * @param id identifiant du patient (String pour compatibilité avec le service
	 *           Notes)
	 */
	@PostMapping("/patient/{id}/note/add")
	public String addNote(@PathVariable String id, @Valid @ModelAttribute("note") NoteBean note, BindingResult result,
			Model model) {
		Integer strToIntID = Integer.valueOf(id);
		PatientBean patient = patientsProxy.getPatientById(strToIntID);

		if (result.hasErrors()) {
			model.addAttribute("patient", patient);
			return "notes/note-add";
		}
		note.setId(null);
		note.setPatientId(id);
		note.setPatientName(patient.getLastName());
		notesProxy.saveNote(note);

		return "redirect:/patient/" + id;
	}

	/**
	 * Affiche le formulaire de mise à jour d’une note d’un patient.
	 */
	@GetMapping("/patient/{patientId}/note/update/{id}")
	public String showUpdateNoteForm(@PathVariable String id, @PathVariable String patientId, Model model) {
		Integer strToIntID = Integer.valueOf(patientId);
		model.addAttribute("patient", patientsProxy.getPatientById(strToIntID));
		model.addAttribute("note", notesProxy.getNoteById(id));
		return "notes/note-update";
	}

	/**
	 * Traite la mise à jour d’une note.
	 */
	@PostMapping("/patient/{patientId}/note/update/{id}")
	public String updateNoteForm(@PathVariable String id, @PathVariable String patientId,
			@Valid @ModelAttribute("note") NoteBean note, BindingResult result, Model model) {
		Integer patientStrToIntID = Integer.valueOf(patientId);
		PatientBean patient = patientsProxy.getPatientById(patientStrToIntID);

		if (result.hasErrors()) {
			model.addAttribute("patient", patient);
			return "notes/note-update";
		}
		note.setId(id);
		note.setPatientId(patientId);
		note.setPatientName(patient.getLastName());
		notesProxy.updateNote(id, note);

		return "redirect:/patient/" + patientId;
	}

}
