import React from 'react'
import FormCreate from '../components/form/FormCreates'
import { Link } from 'react-router-dom'
import Button from '../components/button/Buttons'
function CreateProduct() {
  return (
    <div>
        <div style={{display: 'flex'}}>
            <Link to='/products'>
                <Button text={"Back"} type={"primary"}/>
            </Link>
        </div>
        <FormCreate />
    </div>
  )
}

export default CreateProduct