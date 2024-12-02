package org.sailplatform.fsbackend.controller;

import java.util.List;

import org.sailplatform.fsbackend.model.Person;
import org.sailplatform.fsbackend.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller responsible for handling REST endpoints related to managing Person
 * entities.
 */
@RestController
@CrossOrigin(origins = { "*" })
public class PersonController {

	@Autowired
	PersonService personService;

	/**
	 * Retrieve a list of all Person entities.
	 *
	 * @return A list of Person entities.
	 */
	@GetMapping("/all")
	public List<Person> getAll() {
		return personService.getAll();
	}

	/**
	 * Search for Person entities by their first name.
	 *
	 * @param firstName The first name to search for.
	 * @return A list of matching Person entities.
	 */
	@GetMapping("/searchByFirstName/{firstName}")
	public List<Person> searchByFirstName(@PathVariable String firstName) {
		System.out.println("test");
		System.out.println("test");
		System.out.println("test");
		System.out.println("test");
		System.out.println("test");
		return personService.searchByFirstName(firstName);
	}

	/**
	 * Add a new Person entity.
	 *
	 * @param toAdd The Person object to add.
	 * @return The added Person object.
	 */
	@PostMapping("/add")
	public Person add(@RequestBody Person toAdd) {
		return personService.add(toAdd);
	}

	/**
	 * Update the information of a specific Person entity identified by their ID.
	 *
	 * @param toUpdate The updated person data provided in the request body.
	 * @param id       The ID of the person to be updated.
	 * @return The updated person object if the person with the specified ID exists.
	 * @throws IllegalArgumentException if the request body is null, the ID in the
	 *                                  request body doesn't match the ID in the
	 *                                  path, or the person with the specified ID
	 *                                  doesn't exist.
	 */
	@PutMapping("/update/{id}")
	public Person update(@RequestBody Person toUpdate, @PathVariable Long id) {
		return personService.update(toUpdate, id);
	}

	/**
	 * Delete a specific Person entity by their ID.
	 *
	 * @param id The ID of the person to be deleted.
	 */
	@DeleteMapping("/delete/{id}")
	public void delete(@PathVariable Long id) {
		personService.delete(id);
	}
}
