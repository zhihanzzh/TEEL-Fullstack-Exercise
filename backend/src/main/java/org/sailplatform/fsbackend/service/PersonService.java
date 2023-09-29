package org.sailplatform.fsbackend.service;

import java.util.List;

import org.sailplatform.fsbackend.model.Person;
import org.sailplatform.fsbackend.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class responsible for handling business logic related to Person
 * entities.
 */
@Service
public class PersonService {

	@Autowired
	PersonRepository personRepository;

	/**
	 * Add a new Person entity.
	 *
	 * @param toAdd The Person object to add.
	 * @return The added Person object.
	 */
	public Person add(Person toAdd) {
		// firstName/lastName doesn't exist, save as null.
		// firstName/lastName : null, save as ""
		// Id is auto generated, the ID in the request body will be overwritten
		return personRepository.save(toAdd);
	}

	/**
	 * Retrieve a list of all Person entities.
	 *
	 * @return A list of Person entities.
	 */
	public List<Person> getAll() {
		return personRepository.findAll();
	}

	public Person update(Person toUpdate, Long id) {
		if (toUpdate == null) {
			throw new IllegalArgumentException("The update request contains no data");
		}

		if (toUpdate.getId() != null && !toUpdate.getId().equals(id)) {
			throw new IllegalArgumentException("The ID in the request body does not match the ID in the path");
		}

		if (personRepository.existsById(id)) {
			if (toUpdate.getId() == null) {
				toUpdate.setId(id);
			}
			return personRepository.save(toUpdate);
		} else {
			throw new IllegalArgumentException("The person with ID " + id + " doesn't exist in the system");
		}

	}

	/**
	 * Search for Person entities by their first name.
	 *
	 * @param firstName The first name to search for.
	 * @return A list of matching Person entities.
	 */
	public List<Person> searchByFirstName(String firstName) {
		return personRepository.findByFirstName(firstName);
	}

	/**
	 * Delete a specific Person entity by their ID.
	 *
	 * @param id The ID of the person to be deleted.
	 * @throws IllegalArgumentException if the person with the specified ID doesn't
	 *                                  exist.
	 */
	public void delete(Long id) {
		if (personRepository.existsById(id)) {
			personRepository.deleteById(id);
		} else {
			throw new IllegalArgumentException("The person with ID " + id + " doesn't exist in the system");
		}

	}
}
