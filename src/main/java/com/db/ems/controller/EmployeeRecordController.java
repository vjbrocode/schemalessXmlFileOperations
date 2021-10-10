package com.db.ems.controller;

import static com.db.ems.common.Constants.FAILED;
import static com.db.ems.common.Constants.SUCCESS;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.db.ems.service.EmployeeRecordService;

/**
 * 
 * @author vijaysingh
 * 
 * Controller for Employee Management Service
 * Endpoints to do following operations:
 * 1 - Add new employee
 * 2 - Delete employee by id
 * 3 - Delete employee by name or designation
 * 4 - Get all employees
 * 5 - Get employee by id
 */
@RestController
@RequestMapping("/api/v1/")
public class EmployeeRecordController {

	private EmployeeRecordService employeeRecordService;

	EmployeeRecordController(EmployeeRecordService employeeRecordService) {
		this.employeeRecordService = employeeRecordService;
	}

	@GetMapping("/employees")
	public ResponseEntity<String> getAllEmployees() {
		return ResponseEntity.ok().body(employeeRecordService.getAllRecords());
	}

	@GetMapping("/employee/{id}")
	public ResponseEntity<String> getEmployeeById(@PathVariable String id) {
		return ResponseEntity.ok().body(employeeRecordService.getRecordById(id));
	}

	@PostMapping("/employee")
	public ResponseEntity<String> addOneEmployee(@RequestBody String oneEmployee) {
		return buildResponse(employeeRecordService.addRecord(oneEmployee));
	}

	@DeleteMapping("/employee/{id}")
	public ResponseEntity<String> deleteEmployeeById(@PathVariable String id) {
		return buildResponse(employeeRecordService.deleteRecordById(id));
	}

	@DeleteMapping("/employee")
	public ResponseEntity<String> deleteEmployees(@RequestParam("property") String property,
			@RequestParam("value") String value) {
		if (Validator.notValid(property)) {
			return ResponseEntity.badRequest()
					.body("FAILED. Allowed Properties are " + Validator.getSupportedProperties());
		}
		return buildResponse(employeeRecordService.deleteRecordsByProperty(property, value));
	}

	private ResponseEntity<String> buildResponse(boolean isSuccess) {
		return isSuccess ? ResponseEntity.ok().body(SUCCESS) : ResponseEntity.internalServerError().body(FAILED);
	}

}
