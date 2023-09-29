import { useState, useEffect, ChangeEvent, FormEvent } from "react";
import { useParams, useHistory } from "react-router-dom";
import axios from "axios";
import Person from "../models/Person";

function EditView() {
  // Retrieving the ID from the URL parameters
  const { id } = useParams<{ id: string }>();
  const history = useHistory();

  // State to handle and display errors
  const [error, setError] = useState<string | null>(null);

  // State to hold and manage form data
  const [formData, setFormData] = useState<Person>({
    firstName: "",
    lastName: "",
  });

  // Effect hook to fetch the person's data when the component mounts
  useEffect(() => {
    // Fetch person data by id and set to formData
    const fetchData = async () => {
      try {
        // Resetting error state before making a new request
        setError(null);

        const response = await axios.get(`http://localhost:8080/all`);

        // Finding the person with the specific ID from the response
        const person = response.data.find((p: Person) => p.id === parseInt(id));
        if (person) {
          setFormData(person);
        } else {
          // Setting error state if the person is not found
          setError("Person not found");
        }
      } catch (error) {
        // Handling errors during the data fetch operation
        console.error("Error fetching person:", error);
        setError("Error fetching person data");
      }
    };

    fetchData();
  }, [id]);

  // Handler for form submission
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

      // If the update is successful, redirect to the main list view
      if (response.status === 200) {
        history.push("/");
      } else {
        // Setting error state if the update operation fails
        setError("Failed to update person");
      }
    } catch (error) {
      // Handling errors during the update operation
      console.error("Error updating person:", error);
      setError("Error updating person data");
    }
  };

  return (
    <>
      {error && <div className="error-message">{error}</div>}
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
