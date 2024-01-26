package payroll;

import java.util.List;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.composite.CompositeMeterRegistry;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class EmployeeController {

	private final EmployeeRepository repository;
//	private final CompositeMeterRegistry meterRegistry;
	private final Counter readCounter;
	private final Counter createdCounter;
	private final Counter updatedCounter;
	private final Counter deletedCounter;
	EmployeeController(EmployeeRepository repository, CompositeMeterRegistry meterRegistry) {
		this.repository = repository;
//		this.meterRegistry = meterRegistry;
		this.readCounter = meterRegistry.counter("employees.read");
		this.createdCounter = meterRegistry.counter("employees.created");
		this.updatedCounter = meterRegistry.counter("employees.updated");
		this.deletedCounter = meterRegistry.counter("employees.deleted");
	}


	// Aggregate root
	// tag::get-aggregate-root[]
	@GetMapping("/employees")
	List<Employee> all() {
		List<Employee> emps =repository.findAll();
		readCounter.increment(emps.size());
		return emps;
	}
	// end::get-aggregate-root[]

	@PostMapping("/employees")
	Employee newEmployee(@RequestBody Employee newEmployee) {
		createdCounter.increment();
		return repository.save(newEmployee);
	}

	// Single item
	
	@GetMapping("/employees/{id}")
	Employee one(@PathVariable Long id) {
		readCounter.increment();
		return repository.findById(id)
			.orElseThrow(() -> new EmployeeNotFoundException(id));
	}

	@PutMapping("/employees/{id}")
	Employee replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {
		updatedCounter.increment();
		return repository.findById(id)
			.map(employee -> {
				employee.setName(newEmployee.getName());
				employee.setRole(newEmployee.getRole());
				return repository.save(employee);
			})
			.orElseGet(() -> {
				newEmployee.setId(id);
				return repository.save(newEmployee);
			});
	}

	@DeleteMapping("/employees/{id}")
	void deleteEmployee(@PathVariable Long id) {
		deletedCounter.increment();
		repository.deleteById(id);
	}

}
