package com.openclassrooms.medilabo.patientui.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.openclassrooms.medilabo.patientui.beans.PatientBean;
import com.openclassrooms.medilabo.patientui.proxies.MicroservicePatientsProxy;

@Controller
public class PatientController {
	
	private final MicroservicePatientsProxy patientsProxy;
	
	public PatientController (MicroservicePatientsProxy patientsProxy) {
		this.patientsProxy = patientsProxy;
	}

	@RequestMapping("/")
	public String accueil(Model model) {
		List<PatientBean> patients = patientsProxy.getAllPatients();
		model.addAttribute("patients",patients);
		return "Accueil";
	}
}
