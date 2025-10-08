package com.openclassrooms.medilabo.patientui.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.openclassrooms.medilabo.patientui.beans.PatientRiskLevelBean;
import com.openclassrooms.medilabo.patientui.config.FeignAuthConfig;

@FeignClient(name ="medilabo-risk-diabetes-service", url = "http://gateway:8081/medilabo-risk-diabetes-service", configuration = FeignAuthConfig.class)
public interface MicroserviceRiskCalculatorProxy {

	@GetMapping(value = "/calculateRisk/{patientId}")
	PatientRiskLevelBean getRiskLevelByPatientId(@PathVariable String patientId);
}
