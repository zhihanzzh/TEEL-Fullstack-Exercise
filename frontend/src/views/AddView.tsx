import { useState, ChangeEvent, FormEvent } from "react";
import { useHistory } from "react-router-dom";

import axios from "axios";

function AddView() {
  const history = useHistory();

  // State to hold form data
  const [formData, setFormData] = useState({
    firstName: "",
    lastName: "",
  });

  // State to handle error messages
  const [error, setError] = useState<string | null>(null);

  /**
   * Handle changes in the input fields
   * Updates the form data state with current input values
   */
  const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };

  /**
   * Handle form submission
   * Makes a POST request to add a new person
   */
  const handleSubmit = async (e: FormEvent) => {
    e.preventDefault();

    try {
      // Resetting error state to null to clear any previous errors
      setError(null);

      // Make a POST request to the /add endpoint with the form data
      const response = await axios.post("http://localhost:8080/add", formData);

      // If successful, redirect to the ListView
      if (response.status === 200) {
        history.push("/");
      } else {
        // If HTTP status is other than 200, set an error message
        setError(`HTTP Error ${response.status}`);
      }
    } catch (error) {
      // Log error and set error message in case of an exception
      console.error("Error adding person:", error);
      setError("Internal Server Error : Unable to add the person.");
    }
  };

  return (
    <>
      {error && <div className="error-message">{error}</div>}
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
