package com.openclassrooms.medilabo.patientui.proxies;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.openclassrooms.medilabo.patientui.beans.PatientBean;
import com.openclassrooms.medilabo.patientui.config.FeignAuthConfig;

@FeignClient(name = "patient-service", url = "http://localhost:8080", configuration = FeignAuthConfig.class)
public interface MicroservicePatientsProxy {

	@GetMapping(value = "/patient/all")
	List<PatientBean> getAllPatients();
	
	@GetMapping(value = "/patient/{id}")
	PatientBean getPatientById(@PathVariable("id") Integer id);
}
