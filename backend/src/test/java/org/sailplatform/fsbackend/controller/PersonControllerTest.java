package org.sailplatform.fsbackend.controller;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.sailplatform.fsbackend.model.Person;
import org.sailplatform.fsbackend.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(PersonController.class)
@ExtendWith(SpringExtension.class)
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService; // Use @MockBean to mock the service

    @Test
    public void testAddPerson() throws Exception {
        Person person = new Person();
        person.setFirstName("Zhihan");
        person.setLastName("Zhang");

        when(personService.add(any(Person.class))).thenReturn(person);

        mockMvc.perform(post("/add")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(person)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.firstName", is("Zhihan")))
            .andExpect(jsonPath("$.lastName", is("Zhang")));
    }
    
    @Test
    public void testUpdatePerson() throws Exception {
        // Arrange
        Person updatedPerson = new Person();
        updatedPerson.setId(1L);
        updatedPerson.setFirstName("Updated");
        updatedPerson.setLastName("Person");

        Mockito.when(personService.update(any(Person.class), any(Long.class))).thenReturn(updatedPerson);

        // Act and Assert
        mockMvc.perform(put("/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"firstName\": \"Updated\", \"lastName\": \"Person\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is("Updated")))
                .andExpect(jsonPath("$.lastName", is("Person")));
    }
    
    @Test
    public void testDeletePerson() throws Exception {
        // Act and Assert
        mockMvc.perform(delete("/delete/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}