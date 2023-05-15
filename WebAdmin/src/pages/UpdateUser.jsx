import React from 'react'
import { useParams } from 'react-router-dom'
import FormUpdateUser from '../components/form/FormUserUpdate'
import { Link } from 'react-router-dom'
import Button from '../components/button/Buttons'
function UpdateUser() {
    const { id } = useParams();
    return (
        <div>
            <div style={{ display: 'flex' }}>
                <Link to='/users'>
                    <Button text={"Back"} type={"primary"} />
                </Link>
            </div>
            <FormUpdateUser id={id} />
        </div>
    )
}

export default UpdateUser