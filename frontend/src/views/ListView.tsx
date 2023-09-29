import { useState, useEffect } from "react";
import Person from "../models/Person";
import { AxiosResponse } from "axios";
import axios from "axios";
import styled from "styled-components";
import { Link } from "react-router-dom";
import { useHistory } from "react-router-dom";

function ListView() {
  const [people, setPeople] = useState<Person[]>([]);
  const [loadingComplete, setLoadingComplete] = useState<boolean>(false);

  const history = useHistory();

  const handleEdit = (id: number) => {
    history.push(`/edit/${id}`);
  };

  const fetchPeople = async () => {
    try {
      const response: AxiosResponse<Person[]> = await axios(
        "http://localhost:8080/all"
      );
      setPeople(response.data);
      setLoadingComplete(true);
    } catch (error) {
      console.error("Error fetching people:", error);
    }
  };

  const handleDelete = async (id: number) => {
    try {
      const response = await axios.delete(`http://localhost:8080/delete/${id}`);
      if (response.status === 200) {
        await fetchPeople();
      } else {
        console.error("Failed to delete person");
      }
    } catch (error) {
      console.error("Error deleting person:", error);
    }
  };

  useEffect(() => {
    fetchPeople();
  }, []);

  return (
    <>
      <h1>List of all People</h1>
      {loadingComplete && (
        <div data-testid="people-list">
          {people.map((person: Person) => {
            return (
              <PersonRow key={person.id}>
                <PersonCell>{person.firstName}</PersonCell>
                <PersonCell>{person.lastName}</PersonCell>
                <button onClick={() => handleEdit(person.id!)}>Edit</button>
                <button onClick={() => handleDelete(person.id!)}>Delete</button>
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
