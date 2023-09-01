package com.unipi.backend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.unipi.backend.exception.ResourceNotFoundException;
import com.unipi.backend.model.Appointment;
import com.unipi.backend.repository.AppointmentRepository;
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
public class AppointmentController {
	@Autowired
	private AppointmentRepository appointmentRepository;

	public AppointmentController() {
	}

	@CrossOrigin(
			origins = {"http://localhost:4200"}
	)
	@GetMapping({"/appointments"})
	public List<Appointment> getAllAppointments() {
		return this.appointmentRepository.findAll();
	}

	@CrossOrigin(
			origins = {"http://localhost:4200"}
	)
	@PostMapping({"/appointments"})
	public Appointment createCandidate(@RequestBody Appointment appointment) {
		return (Appointment)this.appointmentRepository.save(appointment);
	}

	@CrossOrigin(
			origins = {"http://localhost:4200"}
	)
	@GetMapping({"/appointments/{id}"})
	public ResponseEntity<Appointment> getAppointmentById(@PathVariable Long id) {
		Appointment appointment = (Appointment)this.appointmentRepository.findById(id).orElseThrow(() -> {
			return new ResourceNotFoundException("Appointment doesn't exist with id: " + id);
		});
		return ResponseEntity.ok(appointment);
	}

	@CrossOrigin(
			origins = {"http://localhost:4200"}
	)
	@PutMapping({"/appointments/{id}"})
	public ResponseEntity<Appointment> updateAppointment(@PathVariable Long id, @RequestBody Appointment appointmentDetails) {
		Appointment appointment = (Appointment)this.appointmentRepository.findById(id).orElseThrow(() -> {
			return new ResourceNotFoundException("Appointment doesn't exist with id: " + id);
		});
		appointment.setCandidateId(appointmentDetails.getCandidateId());
		appointment.setRecruiterId(appointmentDetails.getRecruiterId());
		appointment.setLocation(appointmentDetails.getLocation());
		appointment.setDateTime(appointmentDetails.getDateTime());
		Appointment updatedAppointment = (Appointment)this.appointmentRepository.save(appointment);
		return ResponseEntity.ok(updatedAppointment);
	}

	@CrossOrigin(
			origins = {"http://localhost:4200"}
	)
	@DeleteMapping({"/appointments/{id}"})
	public ResponseEntity<Map<String, Boolean>> deleteAppointment(@PathVariable Long id) {
		Appointment appointment = (Appointment)this.appointmentRepository.findById(id).orElseThrow(() -> {
			return new ResourceNotFoundException("Appointment doesn't exist with id: " + id);
		});
		this.appointmentRepository.delete(appointment);
		Map<String, Boolean> response = new HashMap();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
}
