package org.sailplatform.fsbackend.repository;

import java.util.List;

import org.sailplatform.fsbackend.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing and managing Person entities in the
 * database.
 */
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

	/**
	 * Find Person entities by their first name.
	 *
	 * @param firstName The first name to search for.
	 * @return A list of matching Person entities.
	 */
	List<Person> findByFirstName(String firstName);

}
