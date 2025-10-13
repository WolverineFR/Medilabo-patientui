package com.openclassrooms.medilabo.patientui.beans;

public class PatientRiskLevelBean {

	public enum DiabetesRiskLevel {
		NONE, BORDERLINE, INDANGER, EARLYONSET
	}

	private String patientId;
	private DiabetesRiskLevel diabetesRiskLevel;
	private Integer triggerWordsCounter;

	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	public DiabetesRiskLevel getDiabetesRiskLevel() {
		return diabetesRiskLevel;
	}

	public void setDiabetesRiskLevel(DiabetesRiskLevel diabetesRiskLevel) {
		this.diabetesRiskLevel = diabetesRiskLevel;
	}

	public Integer getTriggerWordsCounter() {
		return triggerWordsCounter;
	}

	public void setTriggerWordsCounter(Integer triggerWordsCounter) {
		this.triggerWordsCounter = triggerWordsCounter;
	}

}
