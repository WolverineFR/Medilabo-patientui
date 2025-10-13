package com.openclassrooms.medilabo.patientui.beans;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class PatientBean {

	private Integer id;

	@NotBlank(message = "Le prénom est obligatoire")
	private String firstName;

	@NotBlank(message = "Le nom est obligatoire")
	private String lastName;

	@NotNull(message = "La date de naissance est obligatoire")
	@Past(message = "La date doit être dans le passé")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate birthDate;

	@NotNull(message = "Le genre est obligatoire")
	private Gender gender;

	@Size(max = 255, message = "L'adresse ne doit pas dépasser 255 caractères")
	private String address;

	@Pattern(regexp = "^(\\+?[0-9 .-]{6,20})?$", message = "Téléphone invalide")
	private String phone;

	public enum Gender {
		M, F
	}

	public PatientBean() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
