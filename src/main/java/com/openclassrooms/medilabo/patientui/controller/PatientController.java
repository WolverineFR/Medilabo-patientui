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

	public PatientController(MicroservicePatientsProxy patientsProxy,MicroserviceNotesProxy notesProxy ) {
		this.patientsProxy = patientsProxy;
		this.notesProxy = notesProxy;
	}

	@RequestMapping("/patients")
	public String listPatient(Model model) {
		List<PatientBean> patients = patientsProxy.getAllPatients();
		model.addAttribute("patients", patients);
		return "patient-list";
	}

	@GetMapping("/patient/{id}")
	public String patientDetail(@PathVariable Integer id, Model model) {
		String idToString = Integer.toString(id);
		List<NoteBean> notes = notesProxy.getNotesByPatientId(idToString);
		model.addAttribute("patient", patientsProxy.getPatientById(id));
		model.addAttribute("notes", notes);
		return "patient-info";
	}

	@GetMapping("patient/add")
	public String showAddForm(Model model) {
		
		model.addAttribute("patient", new PatientBean());
		return "patient-add";
	}

	@PostMapping("patient/add")
	public String savePatient(@Valid @ModelAttribute("patient") PatientBean patient, BindingResult result,
			Model model) {
		if (result.hasErrors()) {
			return "patient-add";
		}
		patientsProxy.savePatient(patient);
		return "redirect:/patients";
	}

	@GetMapping("/patient/update/{id}")
	public String showUpdateForm(@PathVariable Integer id, Model model) {
		model.addAttribute("patient", patientsProxy.getPatientById(id));
		return "patient-update";
	}

	@PostMapping("/patient/update/{id}")
	public String updatePatient(@PathVariable Integer id, @Valid @ModelAttribute("patient") PatientBean patient,
			BindingResult result) {
		if (result.hasErrors()) {
			return "patient-update";
		}
		patientsProxy.updatePatient(id, patient);
		return "redirect:/patients";
	}
	
	@PostMapping("/patients/{id}/notes")
    public String addNote(@PathVariable String id, @RequestParam String noteContent) {
        NoteBean note = new NoteBean();
        note.setPatientId(id);
        note.setNote(noteContent);
        notesProxy.saveNote(note);

        return "redirect:/patients/" + id;
    }

}
