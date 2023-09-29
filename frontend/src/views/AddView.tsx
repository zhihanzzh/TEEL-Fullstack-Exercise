import { useState, ChangeEvent, FormEvent } from "react";
import { useHistory } from "react-router-dom";

import axios from "axios";

function AddView() {
  const history = useHistory();

  const [formData, setFormData] = useState({
    firstName: "",
    lastName: "",
  });

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
      // Make a POST request to the /add endpoint with the form data
      const response = await axios.post("http://localhost:8080/add", formData);

      // Check if the request was successful and the person was added
      if (response.status === 200) {
        console.log("Person added:", response.data);
        // Redirect back to the ListView
        history.push("/");
      } else {
        console.error("Failed to add person");
      }
    } catch (error) {
      console.error("Error adding person:", error);
    }
  };

  return (
    <>
      <h1>Add Person</h1>
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
          <button type="submit">Add Person</button>
        </div>
      </form>
    </>
  );
}

export default AddView;
