import React, { useState, useEffect } from 'react';
import Swal from "sweetalert2";
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

import './FormCreate.scss';

function FormCreate() {



  const initialValues = { name: "", price: "", sale: "", category: "", image: "", quantity: "", description: "" };
  const [formValues, setFormValues] = useState(initialValues);
  const [sizes, setSizes] = useState([]);
  const [formErrors, setFormErrors] = useState({});
  const [isSubmit, setIsSubmit] = useState(false);

  const navigate = useNavigate()
  // const form = useRef();

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormValues({ ...formValues, [name]: value });
  };


  const handleSizeChange = (event) => {
    const { value, checked } = event.target;
    if (checked) {
      setSizes([...sizes, value]);
    } else {
      setSizes(sizes.filter((size) => size !== value));
    }
  };



  // add input 
  const [inputValues, setInputValues] = useState([]);

  const handleAddInput = (e) => {
    e.preventDefault()
    const allValuesFilled = inputValues.every(value => value !== '')
    if (allValuesFilled) {
      setInputValues([...inputValues, '']);
    }
  };

  const handleInputChange = (index, value) => {
    const newInputValues = [...inputValues];
    newInputValues[index] = value;
    setInputValues(newInputValues);
    const values = inputValues.filter(value => value !== '');
    console.log(values)

  };
  console.log(inputValues)
  const formCurrent = {
    size: sizes,
    topping: inputValues,
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
    if (!values.price) {
      errors.price = "Price is required!";
    } else if (!number.test(values.numberPhone)) {
      // errors.numberPhone = "This is not a valid number format!";
    }
    if (!values.image) {
      errors.image = "Link Image is required!";
    }
    if (!values.quantity) {
      errors.quantity = "Quantity is required!"
    }
    if (!values.sale) {
      errors.sale = "Sale is required!"
    }
    if (!values.category) {
      errors.category = "Category is required!"
    }
    if(sizes.length === 0) {
      errors.size = "Choose at least 1 size"
    }
    if (!values.description) {
      errors.description = "Description is required!"
    }
    return errors;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setFormErrors(validate(formValues));
    setIsSubmit(true);
  };
  // const handleSubmits = (e) => {
  //   e.preventDefault();
  //   try {
  //     axios.post('https://api-coffee-e8kl.onrender.com/api/product/add', formCurrent)
  //     setFormValues(initialValues)
  //     navigate('/products')
  //   }
  //   catch (err) {
  //     console.log(err)
  //   }
  // }

  useEffect(() => {
    (async function () {
      console.log(formErrors, isSubmit);
      if (Object.keys(formErrors).length === 0 && isSubmit) {
        try {
          await axios.post("https://api-coffee-e8kl.onrender.com/api/product/add", formCurrent);
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
            navigate("/products");
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
          <h1>Add Product</h1>
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
            <div className='heading'>Price:</div>
            <div className='wrap__input-text'>
              <input
                type="number"
                className="input"
                spellCheck="false"
                placeholder="Price"
                name="price"
                required
                value={formValues.price}
                onChange={handleChange}
              />
              <p>{formErrors.price}</p>
            </div>
          </div>
          <div className="form-items">
            <div className='heading'>Link Image:</div>
            <div className='wrap__input-text'>
              <input
                type="text"
                className="input"
                spellCheck="false"
                placeholder="Image"
                name="image"
                required
                value={formValues.image}
                onChange={handleChange}
              />
              <p>{formErrors.image}</p>
            </div>
          </div>
          <div className="form-items">
            <div className='heading'>Quantity:</div>
            <div className='wrap__input-text'>
              <input
                type="text"
                className="input"
                spellCheck="false"
                placeholder="Quantity"
                name="quantity"
                required
                value={formValues.quantity}
                onChange={handleChange}
              />
              <p>{formErrors.quantity}</p>
            </div>
          </div>
          <div className="form-items">
            <div className='heading'>Sale:</div>
            <div className='wrap__input-text'>
              <input
                type="number"
                className="input"
                spellCheck="false"
                placeholder="Sale"
                name="sale"
                value={formValues.sale}
                onChange={handleChange}
              />
              <p>{formErrors.sale}</p>
            </div>
          </div>
          <div className="form-items">
            <div className='heading'>Category:</div>
            <div className='wrap__input-text'>
              <input
                type="text"
                className="input"
                spellCheck="false"
                placeholder="Category"
                name="category"
                required
                value={formValues.category}
                onChange={handleChange}
              />
              <p>{formErrors.category}</p>
            </div>
          </div>
          <div className='form-items'>
            <div className='heading'>Size:</div>
            <div className='wrap__input-text'>
              <div className='wrap__option-sizes'>
                <label>
                  <input
                    className='checkbox'
                    type="checkbox"
                    name="Size"
                    value="S"
                    onChange={handleSizeChange}
                  />
                  Size S
                </label>
                <label>
                  <input
                    className='checkbox'
                    type="checkbox"
                    name="Size"
                    value="M"
                    onChange={handleSizeChange}
                  />
                  Size M
                </label>
                <label>
                  <input
                    className='checkbox'
                    type="checkbox"
                    name="Size"
                    value="L"
                    onChange={handleSizeChange}
                  />
                  Size L
                </label>
              </div>
              <p>{formErrors.size}</p>
            </div>
          </div>
          <div className='form-items'>
            <div className='heading'>Topping:</div>
            <div className='wrap__input'>
              {inputValues.map((value, index) => (
                <input
                  className='input'
                  key={index}
                  type="text"
                  value={value}
                  onChange={(event) => handleInputChange(index, event.target.value)}
                  required={index > 0}
                />
              ))}
              <button className='btn__addinput' onClick={handleAddInput} disabled={inputValues.some(value => value === '')}>
                Add More Topping
              </button>
            </div>
          </div>
          <div className="form-items">
            <div className='wrap__input-text'>
              <textarea
                className="input message"
                spellCheck="false"
                cols="30"
                rows="10"
                placeholder="Description....."
                name="description"
                required
                value={formValues.description}
                onChange={handleChange}
              ></textarea>
              <p>{formErrors.description}</p>
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

export default FormCreate;
