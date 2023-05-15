import React from 'react'
import { useParams } from 'react-router-dom'
import FormUpdate from '../components/form/FormUpdate'
import { Link } from 'react-router-dom'
import Button from '../components/button/Buttons'
function UpdateProduct() {
    const { id } = useParams();
    return (
        <div>
            <div style={{ display: 'flex' }}>
                <Link to='/products'>
                    <Button text={"Back"} type={"primary"} />
                </Link>
            </div>
            <FormUpdate id={id} />
        </div>
    )
}

export default UpdateProduct