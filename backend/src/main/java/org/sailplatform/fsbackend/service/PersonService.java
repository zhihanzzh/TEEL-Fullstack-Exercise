package org.sailplatform.fsbackend.service;

import java.util.List;

import org.sailplatform.fsbackend.model.Person;
import org.sailplatform.fsbackend.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

	@Autowired
	PersonRepository personRepository;

	public Person add(Person toAdd) {
		// firstName/lastName doesn't exist, save as null.
		// firstName/lastName : null, save as ""
		// Id is auto generated, the ID in the request body will be overwritten
		return personRepository.save(toAdd);
	}

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
	
	public List<Person> searchByFirstName(String firstName) {
		return personRepository.findByFirstName(firstName);
	}

	public void delete(Long id) {
		if (personRepository.existsById(id)) {
			personRepository.deleteById(id);
		} else {
			throw new IllegalArgumentException("The person with ID " + id + " doesn't exist in the system");
		}

	}
}
