package com.openclassrooms.medilabo.patientui.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.openclassrooms.medilabo.patientui.beans.PatientBean;
import com.openclassrooms.medilabo.patientui.proxies.MicroservicePatientsProxy;

@Controller
public class PatientController {
	
	private final MicroservicePatientsProxy patientsProxy;
	
	public PatientController (MicroservicePatientsProxy patientsProxy) {
		this.patientsProxy = patientsProxy;
	}

	@RequestMapping("/patients")
	public String listPatient(Model model) {
		List<PatientBean> patients = patientsProxy.getAllPatients();
		model.addAttribute("patients",patients);
		return "patients";
	}
	
	@GetMapping("/patient/{id}")
    public String patientDetail(@PathVariable Integer id, Model model) {
        model.addAttribute("patient", patientsProxy.getPatientById(id));
        return "patient-info";
    }
}
