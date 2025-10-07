package com.openclassrooms.medilabo.patientui.proxies;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.openclassrooms.medilabo.patientui.beans.NoteBean;
import com.openclassrooms.medilabo.patientui.config.FeignAuthConfig;

@FeignClient(name = "medilabo-note-service", url = "http://localhost:8081/medilabo-note-service", configuration = FeignAuthConfig.class)
public interface MicroserviceNotesProxy {

	@GetMapping(value = "/notes/patient/{patientId}")
	List<NoteBean> getNotesByPatientId(@PathVariable("patientId") String patientId);

	@GetMapping(value = "/notes/all")
	List<NoteBean> getAllNotes();

	@GetMapping(value = "/notes/{id}")
	NoteBean getNoteById(@PathVariable("id") String id);

	@PostMapping(value = "/notes")
	NoteBean saveNote(@RequestBody NoteBean note);

	@PutMapping(value = "/notes/{id}")
	NoteBean updateNote(@PathVariable("id") String id, @RequestBody NoteBean note);

}
