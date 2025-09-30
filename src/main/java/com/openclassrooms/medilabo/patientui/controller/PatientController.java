package com.openclassrooms.medilabo.patientui.controller;

import java.util.List;

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

import jakarta.validation.Valid;

@Controller
public class PatientController {

	private final MicroservicePatientsProxy patientsProxy;
	private final MicroserviceNotesProxy notesProxy;

	public PatientController(MicroservicePatientsProxy patientsProxy, MicroserviceNotesProxy notesProxy) {
		this.patientsProxy = patientsProxy;
		this.notesProxy = notesProxy;
	}

	@RequestMapping("/patients")
	public String listPatient(Model model) {
		List<PatientBean> patients = patientsProxy.getAllPatients();
		model.addAttribute("patients", patients);
		return "patients/patient-list";
	}

	@GetMapping("/patient/{id}")
	public String patientDetail(@PathVariable Integer id, Model model) {
		String idToString = Integer.toString(id);
		List<NoteBean> notes = notesProxy.getNotesByPatientId(idToString);
		model.addAttribute("patient", patientsProxy.getPatientById(id));
		model.addAttribute("notes", notes);
		return "patients/patient-info";
	}

	@GetMapping("patient/add")
	public String showAddForm(Model model) {

		model.addAttribute("patient", new PatientBean());
		return "patients/patient-add";
	}

	@PostMapping("patient/add")
	public String savePatient(@Valid @ModelAttribute("patient") PatientBean patient, BindingResult result,
			Model model) {
		if (result.hasErrors()) {
			return "patients/patient-add";
		}
		patientsProxy.savePatient(patient);
		return "redirect:/patients";
	}

	@GetMapping("/patient/update/{id}")
	public String showUpdateForm(@PathVariable Integer id, Model model) {
		model.addAttribute("patient", patientsProxy.getPatientById(id));
		return "patients/patient-update";
	}

	@PostMapping("/patient/update/{id}")
	public String updatePatient(@PathVariable Integer id, @Valid @ModelAttribute("patient") PatientBean patient,
			BindingResult result) {
		if (result.hasErrors()) {
			return "patients/patient-update";
		}
		patientsProxy.updatePatient(id, patient);
		return "redirect:/patients";
	}

	@GetMapping("/patient/{id}/note/add")
	public String showAddNoteForm(@PathVariable Integer id, Model model) {
		model.addAttribute("patient", patientsProxy.getPatientById(id));
		model.addAttribute("note", new NoteBean());
		return "notes/note-add";
	}

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

	@GetMapping("/patient/{patientId}/note/update/{id}")
	public String showUpdateNoteForm(@PathVariable String id, @PathVariable String patientId, Model model) {
		Integer strToIntID = Integer.valueOf(patientId);
		model.addAttribute("patient", patientsProxy.getPatientById(strToIntID));
		model.addAttribute("note", notesProxy.getNoteById(id));
		return "notes/note-update";
	}

	@PostMapping("/patient/{patientId}/note/update/{id}")
	public String updateNoteForm(@PathVariable String id, @PathVariable String patientId,@Valid @ModelAttribute("note") NoteBean note,
			BindingResult result, Model model) {
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
