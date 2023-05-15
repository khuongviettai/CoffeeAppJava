import React, { useEffect, useState } from 'react';
import './FormCreate.scss';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

function FormUpdateUser({ id }) {
    const initialValues = { name: "", price: "", sale: "", category: "", image: "", quantity: "" };
    const [formValues, setFormValues] = useState(initialValues);
    const [sizes, setSizes] = useState([]);
    const [inputValues, setInputValues] = useState([]);
    const [formCurrent, setFormCurrent] = useState({
        name: "",
        phone: "",
        password: "",
        confirm: "",
    });
    const navigate = useNavigate()

    // get data
    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await axios.get(`https://api-coffee-e8kl.onrender.com/api/user/${id}`);
                setFormValues(response.data);
                setInputValues(response.data?.topping)
                setSizes(response.data?.size)
            } catch (error) {
                console.error(error);
            }
        }
        fetchData()
    }, [id])
    // const form = useRef();


    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormValues({ ...formValues, [name]: value });
    };


    

    // Delete Topping
    

    // set Form when modify
    useEffect(() => {
        setFormCurrent({
            ...formValues,
            size: sizes,
            topping: inputValues,
        });
    }, [formValues, sizes, inputValues]);

    // console.log(formCurrent)
    const handleSubmit = (e) => {
        e.preventDefault();
        if (formValues.sale > 0 && formValues.sale < 100) {
            try {
                axios.put(`https://api-coffee-e8kl.onrender.com/api/product/${id}`, formCurrent)
                setFormValues(initialValues)
                navigate('/products')
            }
            catch (err) {
                console.log(err)
            }
        } else {
            alert("Vui lòng nhập giá trị sale từ 0 đến 99")
        }
    }

    return (
        <>
            <form className="contact-us">
                <div className="title">
                    <h1>Update User</h1>
                </div>
                <div className="form">
                    <div className="form-items">
                        <div className='heading'>Name:</div>
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
                    </div>
                    <div className="form-items">
                        <div className='heading'>Phone:</div>
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
                    </div>
                    <div className="form-items">
                        <div className='heading'>Password:</div>
                        <input
                            type="password"
                            className="input"
                            spellCheck="false"
                            placeholder="Password"
                            name="password"
                            required
                            value={formValues.password}
                            onChange={handleChange}
                        />
                    </div>
                    <div className="form-items">
                        <div className='heading'>Confirm:</div>
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
                    </div>

                </div>

                <button className="btn" type="submit" value="Send" onClick={handleSubmit}>
                    Submit
                </button>
            </form>
        </>
    );
}

export default FormUpdateUser;
