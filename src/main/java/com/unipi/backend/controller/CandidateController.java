package com.unipi.backend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.unipi.backend.exception.ResourceNotFoundException;
import com.unipi.backend.model.Candidate;
import com.unipi.backend.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/api/"})
public class CandidateController {
	@Autowired
	private CandidateRepository candidateRepository;

	public CandidateController() {
	}

	@CrossOrigin(
			origins = {"http://localhost:4200"}
	)
	@GetMapping({"/candidates"})
	public List<Candidate> getAllCandidates() {
		return this.candidateRepository.findAll();
	}

	@CrossOrigin(
			origins = {"http://localhost:4200"}
	)
	@PostMapping({"/candidates"})
	public Candidate createCandidate(@RequestBody Candidate candidate) {
		return (Candidate)this.candidateRepository.save(candidate);
	}

	@CrossOrigin(
			origins = {"http://localhost:4200"}
	)
	@GetMapping({"/candidates/{id}"})
	public ResponseEntity<Candidate> getCandidateById(@PathVariable Long id) {
		Candidate candidate = (Candidate)this.candidateRepository.findById(id).orElseThrow(() -> {
			return new ResourceNotFoundException("User doesn't exist with id: " + id);
		});
		return ResponseEntity.ok(candidate);
	}

	@CrossOrigin(
			origins = {"http://localhost:4200"}
	)
	@PutMapping({"/candidates/{id}"})
	public ResponseEntity<Candidate> updateCandidate(@PathVariable Long id, @RequestBody Candidate candidateDetails) {
		Candidate candidate = (Candidate)this.candidateRepository.findById(id).orElseThrow(() -> {
			return new ResourceNotFoundException("User doesn't exist with id: " + id);
		});
		candidate.setUsername(candidateDetails.getUsername());
		candidate.setPassword(candidateDetails.getPassword());
		candidate.setLastName(candidateDetails.getLastName());
		candidate.setFirstName(candidateDetails.getFirstName());
		candidate.setLastName(candidateDetails.getLastName());
		candidate.setEmail(candidateDetails.getEmail());
		candidate.setBirthDate(candidateDetails.getBirthDate());
		candidate.setNotes(candidateDetails.getNotes());
		Candidate updatedCandidate = (Candidate)this.candidateRepository.save(candidate);
		return ResponseEntity.ok(updatedCandidate);
	}

	@CrossOrigin(
			origins = {"http://localhost:4200"}
	)
	@DeleteMapping({"/candidates/{id}"})
	public ResponseEntity<Map<String, Boolean>> deleteUser(@PathVariable Long id) {
		Candidate candidate = (Candidate)this.candidateRepository.findById(id).orElseThrow(() -> {
			return new ResourceNotFoundException("User doesn't exist with id: " + id);
		});
		this.candidateRepository.delete(candidate);
		Map<String, Boolean> response = new HashMap();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
}
