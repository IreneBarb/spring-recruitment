package com.unipi.backend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.unipi.backend.exception.ResourceNotFoundException;
import com.unipi.backend.model.Recruiter;
import com.unipi.backend.repository.RecruiterRepository;
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
public class RecruiterController {
	@Autowired
	private RecruiterRepository recruiterRepository;

	public RecruiterController() {
	}

	@CrossOrigin(
			origins = {"http://localhost:4200"}
	)
	@GetMapping({"/recruiters"})
	public List<Recruiter> getAllRecruiters() {
		return this.recruiterRepository.findAll();
	}

	@CrossOrigin(
			origins = {"http://localhost:4200"}
	)
	@PostMapping({"/recruiters"})
	public Recruiter createRecruiter(@RequestBody Recruiter recruiter) {
		return (Recruiter)this.recruiterRepository.save(recruiter);
	}

	@CrossOrigin(
			origins = {"http://localhost:4200"}
	)
	@GetMapping({"/recruiters/{id}"})
	public ResponseEntity<Recruiter> getRecruiterById(@PathVariable Long id) {
		Recruiter recruiter = (Recruiter)this.recruiterRepository.findById(id).orElseThrow(() -> {
			return new ResourceNotFoundException("User doesn't exist with id: " + id);
		});
		return ResponseEntity.ok(recruiter);
	}

	@CrossOrigin(
			origins = {"http://localhost:4200"}
	)
	@PutMapping({"/recruiters/{id}"})
	public ResponseEntity<Recruiter> updateRecruiter(@PathVariable Long id, @RequestBody Recruiter recruiterDetails) {
		Recruiter recruiter = (Recruiter)this.recruiterRepository.findById(id).orElseThrow(() -> {
			return new ResourceNotFoundException("User doesn't exist with id: " + id);
		});
		recruiter.setUsername(recruiterDetails.getUsername());
		recruiter.setPassword(recruiterDetails.getPassword());
		recruiter.setLastName(recruiterDetails.getLastName());
		recruiter.setFirstName(recruiterDetails.getFirstName());
		recruiter.setLastName(recruiterDetails.getLastName());
		recruiter.setEmail(recruiterDetails.getEmail());
		recruiter.setToken(recruiterDetails.getToken());
		Recruiter updatedRecruiter = (Recruiter)this.recruiterRepository.save(recruiter);
		return ResponseEntity.ok(updatedRecruiter);
	}

	@CrossOrigin(
			origins = {"http://localhost:4200"}
	)
	@DeleteMapping({"/recruiters/{id}"})
	public ResponseEntity<Map<String, Boolean>> deleteRecruiter(@PathVariable Long id) {
		Recruiter recruiter = (Recruiter)this.recruiterRepository.findById(id).orElseThrow(() -> {
			return new ResourceNotFoundException("User doesn't exist with id: " + id);
		});
		this.recruiterRepository.delete(recruiter);
		Map<String, Boolean> response = new HashMap();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
}
