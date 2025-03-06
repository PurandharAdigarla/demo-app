import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

const Register = () => {
  const navigate = useNavigate();

  const [user, setUser] = useState({
    name: "",
    email: "",
    password: "",
    confirmPassword: "",
  });

  const handleChange = (e) => {
    setUser({ ...user, [e.target.name]: e.target.value });
  };


  const handleSubmit = async (e) => {
    e.preventDefault();

    
    if (user.password !== user.confirmPassword) {
      alert("Passwords do not match!");
      return;
    }

    try {
      const response = await axios.post("http://localhost:8080/user/register", {
        name: user.name,
        email: user.email,
        password: user.password,
      });
      alert(response.data.message || "Registration successful! Redirecting to login...");
      
      navigate("/login"); 
    } catch (error) {
      alert("Error registering user: " + (error.response?.data || error.message));
    }
  };

  return (
    <div>
      <h2>Register</h2>
      <form onSubmit={handleSubmit}>
        <input type="text" name="name" placeholder="Name" value={user.name} onChange={handleChange} required />
        <input type="email" name="email" placeholder="Email" value={user.email} onChange={handleChange} required />
        <input type="password" name="password" placeholder="Password" value={user.password} onChange={handleChange} required />
        <input type="password" name="confirmPassword" placeholder="Confirm Password" value={user.confirmPassword} onChange={handleChange} required />
        <button type="submit">Register</button>
      </form>
      <p>Already have an account? <a href="/">Login</a></p>
    </div>
  );
};

export default Register;
