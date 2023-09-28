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
    
    
    public Person add(Person toAdd){
    	//firstname/lastname doesn't exist, save as null.
    	//firstName/lastName : null, save as ""
    	//Id is auto generated
    	
        System.out.println("add - ID " + toAdd.getId());
        System.out.println("add - fn " + toAdd.getFirstName());
        System.out.println("add - ln " + toAdd.getLastName());
        return personRepository.save(toAdd);
    }

    public List<Person> getAll(){
        return personRepository.findAll();
    }
    
    public Person update(Person toUpdate, Long id){
        // The 'id' parameter is taken from the URL path variable, 
        // not from the ID field in the request body. If both are provided,
        // the ID in the URL takes precedence to ensure consistency
        // and clarity on which resource is being updated.
    	
    	if (toUpdate == null) {
            throw new IllegalArgumentException("The update request contains no data");
        }
    	
    	if (personRepository.existsById(id))  {
    		return personRepository.save(toUpdate);
    	} else {
    		throw new IllegalArgumentException("The person with ID " + id + " doesn't exist in the system");
    	}
    	
    }
    
    
    public void delete(Long id){
    	System.out.println("delete + ID " + id);
    	if (personRepository.existsById(id))  {
    		personRepository.deleteById(id);
    	} else {
    		throw new IllegalArgumentException("The person with ID " + id + " doesn't exist in the system");
    	}
  
    }
}
