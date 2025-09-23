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

@FeignClient(name = "patient-service", url = "http://localhost:8080", configuration = FeignAuthConfig.class)
public interface MicroservicePatientsProxy {

	@GetMapping(value = "/patient/all")
	List<PatientBean> getAllPatients();
	
	@GetMapping(value = "/patient/{id}")
	PatientBean getPatientById(@PathVariable("id") Integer id);
	
	@PostMapping(value = "/patient")
    PatientBean savePatient(@RequestBody PatientBean patient);
	
	@PutMapping(value = "/patient/{id}")
    PatientBean updatePatient(@PathVariable("id") Integer id, @RequestBody PatientBean patient);
}
