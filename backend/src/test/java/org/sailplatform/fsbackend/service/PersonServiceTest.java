package org.sailplatform.fsbackend.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.sailplatform.fsbackend.model.Person;
import org.sailplatform.fsbackend.repository.PersonRepository;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

	@Mock
	private PersonRepository personRepository;

	@InjectMocks
	private PersonService personService;

	@Test
	public void testAddPerson() {
		Person person = new Person();
		person.setFirstName("Zhihan");
		person.setLastName("Zhang");

		when(personRepository.save(any(Person.class))).thenReturn(person);

		Person savedPerson = personService.add(person);

		assertThat(savedPerson).isNotNull();
		assertThat(savedPerson.getFirstName()).isEqualTo("Zhihan");
		assertThat(savedPerson.getLastName()).isEqualTo("Zhang");
	}

	@Test
	public void testUpdatePerson() {
		Person existingPerson = new Person();
		existingPerson.setId(1L);
		existingPerson.setFirstName("Zhihan");
		existingPerson.setLastName("Zhang");

		Person updatedPersonData = new Person();
		updatedPersonData.setFirstName("Anya");
		updatedPersonData.setLastName("John");

		when(personRepository.existsById(1L)).thenReturn(true);
		when(personRepository.save(any(Person.class))).thenAnswer(invocation -> invocation.getArgument(0));

		Person savedPerson = personService.update(updatedPersonData, 1L);

		assertThat(savedPerson).isNotNull();
		assertThat(savedPerson.getFirstName()).isEqualTo("Anya");
		assertThat(savedPerson.getLastName()).isEqualTo("John");
	}

	@Test
	public void testDeletePerson() {
		Long personIdToDelete = 1L;

		when(personRepository.existsById(personIdToDelete)).thenReturn(true);
		doNothing().when(personRepository).deleteById(personIdToDelete);

		assertThatCode(() -> personService.delete(personIdToDelete)).doesNotThrowAnyException();

		verify(personRepository, times(1)).deleteById(personIdToDelete);
	}
}
