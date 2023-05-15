import React from 'react'
import { Link } from 'react-router-dom'
import Button from '../components/button/Buttons'
import FormCreateUser from '../components/form/FormUserCreate'
function CreateUser() {
  return (
    <div>
        <div style={{display: 'flex'}}>
            <Link to='/users'>
                <Button text={"Back"} type={"primary"}/>
            </Link>
        </div>
        <FormCreateUser />
    </div>
  )
}

export default CreateUser