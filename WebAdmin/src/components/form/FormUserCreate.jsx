import React, { useState, useEffect } from 'react';
import Swal from "sweetalert2";
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

import './FormCreate.scss';

function FormCreateUser() {



  const initialValues = { name: "",phone: "", password: "", confirm: ""};
  const [formValues, setFormValues] = useState(initialValues);
  const [formErrors, setFormErrors] = useState({});
  const [isSubmit, setIsSubmit] = useState(false);

  const navigate = useNavigate()
  // const form = useRef();

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormValues({ ...formValues, [name]: value });
  };


  



  
  const formCurrent = {
    ...formValues
  }
  console.log(formCurrent)
  const validate = (values) => {
    const errors = {};
    // const regex = /^[^\s@]+@[^\s@]+\.[^\s@]{2,}$/i;
    const number = /^[0-9]+$/;
    if (!values.name) {
      errors.name = "Name is required!";
    }
    if (!values.phone) {
      errors.phone = "Phone is required!";
    } else if (!number.test(values.phone)) {
      errors.phone = "This is not a valid number format!";
    }
    if (!values.password) {
      errors.password = "Password is required!";
    }
    if (!values.confirm) {
      errors.confirm = "Confirm is required!"
    }
    if (!(formValues.password === formValues.confirm) && values.confirm.length > 0) {
      errors.check = "Password does not match!"
    }
    return errors;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    
    setFormErrors(validate(formValues));
    setIsSubmit(true);
  };
  

  useEffect(() => {
    (async function () {
      console.log(formErrors, isSubmit);
      if (Object.keys(formErrors).length === 0 && isSubmit) {
        try {
          await axios.post("https://api-coffee-e8kl.onrender.com/api/user/add", formCurrent);
          Swal.fire({
            position: "center",
            icon: "success",
            title: "Success",
            showConfirmButton: false,
            timer: 1500,
            timerProgressBar: true,
          });
          setTimeout(() => {
            setFormValues(initialValues)
            navigate("/users");
          }, 1500);
        } catch (err) {
          Swal.fire({
            position: "center",
            icon: "error",
            title: "Oops...",
            text: "Something went wrong!",
            showConfirmButton: false,
            timer: 1500,
            timerProgressBar: true,
          });
          console.log(err);
          // setError(true);
        }
      } else {
        console.log("error");
      }
    })();
    // eslint-disable-next-line
  }, [formErrors]);
  return (
    <>
      <form className="contact-us">
        <div className="title">
          <h1>Add User</h1>
        </div>
        <div className="form">
          <div className="form-items">
            <div className='heading'>Name:</div>
            <div className='wrap__input-text'>
              <input
                type="text"
                className="input"
                spellCheck="false"
                placeholder="Name"
                name="name"
                required
                value={formValues.name}
                onChange={handleChange}
              />
              <p>{formErrors.name}</p>
            </div>
          </div>
          <div className="form-items">
            <div className='heading'>Phone:</div>
            <div className='wrap__input-text'>
              <input
                type="number"
                className="input"
                spellCheck="false"
                placeholder="Phone"
                name="phone"
                required
                value={formValues.phone}
                onChange={handleChange}
              />
              <p>{formErrors.phone}</p>
            </div>
          </div>


          <div className="form-items">
            <div className='heading'>Password:</div>
            <div className='wrap__input-text'>
              <input
                type="password"
                className="input"
                spellCheck="false"
                placeholder="Password"
                name="password"
                value={formValues.password}
                onChange={handleChange}
              />
              <p>{formErrors.password}</p>
            </div>
          </div>
          <div className="form-items">
            <div className='heading'>Confirm:</div>
            <div className='wrap__input-text'>
              <input
                type="password"
                className="input"
                spellCheck="false"
                placeholder="Confirm"
                name="confirm"
                required
                value={formValues.confirm}
                onChange={handleChange}
              />
              <p>{formErrors.confirm}</p>
              <p>{formErrors.check}</p>
            </div>
          </div>



        </div>

        <button className="btn" type="submit" value="Send" onClick={handleSubmit}>
          Submit
        </button>
      </form>
    </>
  );
}

export default FormCreateUser;
