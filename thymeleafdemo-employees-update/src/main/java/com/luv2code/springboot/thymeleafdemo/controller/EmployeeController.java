package com.luv2code.springboot.thymeleafdemo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.luv2code.springboot.thymeleafdemo.entity.Employee;
import com.luv2code.springboot.thymeleafdemo.service.EmployeeService;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

	//load employee data
	
	private EmployeeService employeeServie;
	
	public EmployeeController(EmployeeService employeeServie) {
		this.employeeServie = employeeServie;
	}

	//add mapping for "/list"
	@GetMapping("/list")
	public String listEmployees(Model theModel)
	{
		List<Employee> theEmployees = employeeServie.findAll();
		//add to the spring model
		theModel.addAttribute("employees", theEmployees);
		
		return "employees/list-employees";
	}
	
	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel)
	{
		Employee theEmployee =  new Employee();
		theModel.addAttribute("employee", theEmployee);
		
		return "employees/employee-form";
	}
	
	@PostMapping("/save") // employee ---> form data 
	public String saveEmployee(@ModelAttribute("employee") Employee theEmployee)
	{
		//save the employee
		employeeServie.save(theEmployee);
		
		//Employee Controller -> Employee Service -> Employee Repository -> Database
		
		//use a redirect to prevent duplicate submission
		
		return "redirect:/employees/list";//redirect to request mapping /employees/list
	}
	
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("employeeId") int theId, Model theModel)
	{
		//get the employee form the service
		Employee theEmployee = employeeServie.findById(theId);
		
		//set employee as a model attribute to pre-populate the form
		theModel.addAttribute("employee", theEmployee);
		
		//send over to our form
		
		return "employees/employee-form";
	}
	
	@GetMapping("/showFormForDelete")
	public String showFormForDelete(@RequestParam("employeeId") int theId, Model theModel)
	{
		Employee theEmployee = employeeServie.findById(theId);
		
		employeeServie.deleteById(theEmployee.getId());
		
		theModel.addAttribute("employee", theEmployee);
		
		return "employees/employee-form";
		
	}
	
}
