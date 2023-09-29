package org.sailplatform.fsbackend.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.sailplatform.fsbackend.model.Person;
import org.sailplatform.fsbackend.repository.PersonRepository;

/**
 * Unit tests for the PersonService class.
 */
@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

	@Mock
	private PersonRepository personRepository;

	@InjectMocks
	private PersonService personService;

	/**
     * Test adding a new Person entity.
     */
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

	/**
     * Test updating an existing Person entity.
     */
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
	
	/**
	 * Test updating a Person entity with a null request body.
	 */
	@Test
	public void testUpdatePersonWithNullRequestBody() {
	    Long personId = 1L;

	    assertThatThrownBy(() -> personService.update(null, personId))
	            .isInstanceOf(IllegalArgumentException.class)
	            .hasMessageContaining("The update request contains no data");

	    verify(personRepository, never()).save(any());
	}
	
	/**
	 * Test updating a Person entity with an ID mismatch between request body and path.
	 */
	@Test
	public void testUpdatePersonWithIdMismatch() {
	    Long personId = 1L;
	    Person updatedPersonData = new Person();
	    updatedPersonData.setId(2L); 

	    assertThatThrownBy(() -> personService.update(updatedPersonData, personId))
	            .isInstanceOf(IllegalArgumentException.class)
	            .hasMessageContaining("The ID in the request body does not match the ID in the path");

	    verify(personRepository, never()).save(any());
	}
	
	/**
	 * Test updating a non-existent Person entity.
	 */
	@Test
	public void testUpdateNonExistentPerson() {
	    Long nonExistentPersonId = 3L;
	    Person updatedPersonData = new Person();
	    updatedPersonData.setFirstName("Updated");
	    updatedPersonData.setLastName("Person");

	    when(personRepository.existsById(nonExistentPersonId)).thenReturn(false);

	    assertThatThrownBy(() -> personService.update(updatedPersonData, nonExistentPersonId))
	            .isInstanceOf(IllegalArgumentException.class)
	            .hasMessageContaining("The person with ID " + nonExistentPersonId + " doesn't exist in the system");

	    // Verify that save method is not called.
	    verify(personRepository, never()).save(any());
	}

	/**
     * Test deleting an existing Person entity.
     */
	@Test
	public void testDeletePerson() {
		Long personIdToDelete = 1L;

		when(personRepository.existsById(personIdToDelete)).thenReturn(true);
		doNothing().when(personRepository).deleteById(personIdToDelete);

		assertThatCode(() -> personService.delete(personIdToDelete)).doesNotThrowAnyException();

		verify(personRepository, times(1)).deleteById(personIdToDelete);
	}
	
	/**
	 * Test deleting a non-existent Person entity.
	 */
	@Test
	public void testDeleteNonExistentPerson() {
	    Long nonExistentPersonId = 3L;

	    when(personRepository.existsById(nonExistentPersonId)).thenReturn(false);

	    assertThatThrownBy(() -> personService.delete(nonExistentPersonId))
	            .isInstanceOf(IllegalArgumentException.class)
	            .hasMessageContaining("The person with ID " + nonExistentPersonId + " doesn't exist in the system");

	    // Verify that deleteById method is not called.
	    verify(personRepository, never()).deleteById(any());
	}
	
	/**
     * Test searching for Person entities by first name.
     */
	@Test
	public void testSearchByFirstName() {
	    String firstNameToSearch = "Zhihan";
	    List<Person> expectedResults = createMockPersonList(firstNameToSearch);
	    
	    when(personRepository.findByFirstName(firstNameToSearch)).thenReturn(expectedResults);

	    List<Person> actualResults = personService.searchByFirstName(firstNameToSearch);

	    assertThat(actualResults).isNotNull();
	    assertThat(actualResults).hasSize(expectedResults.size());
	    assertThat(actualResults.get(0).getFirstName()).isEqualTo("Zhihan");
	    assertThat(actualResults.get(1).getLastName()).isEqualTo("Zhang");
	    assertThat(actualResults.get(2).getFirstName()).isNotNull();

	}
	
	private List<Person> createMockPersonList(String firstName) {
        List<Person> persons = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Person person = new Person();
            person.setId((long) i);
            person.setFirstName(firstName);
            person.setLastName("Zhang");
            persons.add(person);
        }
        return persons;
    }
}
