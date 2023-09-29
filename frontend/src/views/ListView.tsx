import { useState, useEffect, ChangeEvent } from "react";
import Person from "../models/Person";
import { AxiosResponse } from "axios";
import axios from "axios";
import styled from "styled-components";
import { Link } from "react-router-dom";
import { useHistory } from "react-router-dom";

function ListView() {
  // State for holding the list of people
  const [people, setPeople] = useState<Person[]>([]);

  // State for tracking the completion of data loading
  const [loadingComplete, setLoadingComplete] = useState<boolean>(false);

  // State for handling the search term for filtering by first name
  const [searchTerm, setSearchTerm] = useState<string>("");

  // State for handling and displaying errors
  const [error, setError] = useState<string | null>(null);

  const history = useHistory();

  // Handler for redirecting to the edit view with the selected person’s ID
  const handleEdit = (id: number) => {
    history.push(`/edit/${id}`);
  };

  // Asynchronous function for fetching people data from the provided URL
  const fetchPeople = async (url: string) => {
    try {
      // Resetting the error state before making a new request
      setError(null);

      const response: AxiosResponse<Person[]> = await axios.get(url);
      setPeople(response.data);
      setLoadingComplete(true);
    } catch (error) {
      console.error("Error fetching people:", error);
      setError("Error fetching people data");
    }
  };

  // Handler for deleting a person by ID
  const handleDelete = async (id: number) => {
    try {
      const response = await axios.delete(`http://localhost:8080/delete/${id}`);
      if (response.status === 200) {
        await fetchPeople("http://localhost:8080/all");
      } else {
        console.error("Failed to delete person");
        setError("Error deleting deleting person");
      }
    } catch (error) {
      console.error("Error deleting person:", error);
      setError("Error deleting deleting person");
    }
  };

  // Handler for executing search by first name
  const handleSearch = async () => {
    // Using a specific endpoint for searching if a search term is provided
    if (searchTerm) {
      fetchPeople(`http://localhost:8080/searchByFirstName/${searchTerm}`);
    } else {
      // Fetching all people if no search term is provided
      fetchPeople("http://localhost:8080/all");
    }
  };

  // Fetching all people when the component mounts
  useEffect(() => {
    fetchPeople("http://localhost:8080/all");
  }, []);

  // Handler for capturing and setting the search term from the input field
  const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
    setSearchTerm(e.target.value);
  };

  // Handler for clearing the search term
  const handleReset = () => {
    setSearchTerm(""); // Clear the search term
    fetchPeople("http://localhost:8080/all"); // Fetch all people again
  };

  return (
    <>
      {error && <div className="error-message">{error}</div>}
      <h1>List of all People</h1>
      <div>
        <input
          type="text"
          value={searchTerm}
          onChange={handleChange}
          placeholder="firstName(Case Sensitive)"
        ></input>

        <button onClick={handleSearch}>Search By First Name</button>
        <button onClick={handleReset}>Reset</button>
      </div>
      <hr />
      {loadingComplete && (
        <div data-testid="people-list">
          {people.map((person: Person) => {
            return (
              <PersonRow key={person.id}>
                <button onClick={() => handleDelete(person.id!)}>Delete</button>
                <button onClick={() => handleEdit(person.id!)}>Edit</button>
                <PersonCell>{person.firstName}</PersonCell>
                <PersonCell>{person.lastName}</PersonCell>
              </PersonRow>
            );
          })}
        </div>
      )}
      <Link to={"/add"}>Add Person</Link>
    </>
  );
}

export default ListView;

const PersonRow = styled.div``;

const PersonCell = styled.span`
  padding: 1rem;
`;
