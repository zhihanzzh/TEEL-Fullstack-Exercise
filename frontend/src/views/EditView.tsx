import { useState, useEffect, ChangeEvent, FormEvent } from "react";
import { useParams, useHistory } from "react-router-dom";
import axios from "axios";
import Person from "../models/Person";

function EditView() {
  const { id } = useParams<{ id: string }>();
  const history = useHistory();

  const [formData, setFormData] = useState<Person>({
    firstName: "",
    lastName: "",
  });

  useEffect(() => {
    // Fetch person data by id and set to formData
    const fetchData = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/all`);
        const person = response.data.find((p: Person) => p.id === parseInt(id));
        if (person) {
          setFormData(person);
        }
      } catch (error) {
        console.error("Error fetching person:", error);
      }
    };

    fetchData();
  }, [id]);

  const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };

  const handleSubmit = async (e: FormEvent) => {
    e.preventDefault();
    try {
      const response = await axios.put(
        `http://localhost:8080/update/${id}`,
        formData
      );
      if (response.status === 200) {
        history.push("/");
      } else {
        console.error("Failed to update person");
      }
    } catch (error) {
      console.error("Error updating person:", error);
    }
  };

  return (
    <>
      <h1>Edit Person</h1>
      <form onSubmit={handleSubmit}>
        <div>
          <label htmlFor="firstName">First Name:</label>
          <input
            type="text"
            id="firstName"
            name="firstName"
            value={formData.firstName}
            onChange={handleChange}
            required
          />
        </div>
        <div>
          <label htmlFor="lastName">Last Name:</label>
          <input
            type="text"
            id="lastName"
            name="lastName"
            value={formData.lastName}
            onChange={handleChange}
            required
          />
        </div>
        <div>
          <button type="submit">Update Person</button>
        </div>
      </form>
    </>
  );
}
export default EditView;
