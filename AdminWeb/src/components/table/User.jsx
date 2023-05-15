import React from 'react'
import './table.scss'
import { Link } from 'react-router-dom'
import Button from '../button/Buttons'
function UserTable({ value, handleDelete }) {

    let boolean = (value.length > 0) ? true : false

    // const handleDelete = (id) => {
    //     axios.delete(`https://api-coffee-e8kl.onrender.com/api/product/${id}`)

    //     .catch(err => {
    //         console.log(err)
    //     })
    // }

    return (
        <div>

            {boolean &&
                <>
                    <div style={{ display: 'flex', paddingBottom: '16px' }}>
                        <Link to='/adduser'>
                            <Button text={('Add User')} type={('edit')} />
                        </Link>
                    </div>
                    <table>
                        <thead>
                            <tr>
                                <th className='table__phone'>Phone</th>
                                <th className='table__name-user'>Name</th>
                                <th className='table__password'>Password</th>
                                <th className='table__order'>Orders</th>
                                <th className='table__function'></th>
                            </tr>
                        </thead>

                        <tbody>
                            {value.map((item) => (
                                <tr key={item._id}>
                                    <td>
                                        {item.phone}
                                    </td>
                                    <td>{item.name}</td>
                                    <td>{item.password}</td>
                                    <td>
                                        <Button text={"Show"} type={"edit"} />
                                    </td>
                                    <td>
                                        <div className='wrap__option'>
                                            <Link to={`/update/user/${item._id}`}
                                            // onClick={() => console.log(item)}
                                            >
                                                <div className='button edit' >Edit</div>
                                            </Link>
                                            <div className='button delete' onClick={() => handleDelete(item._id)} >Delete</div>
                                        </div>
                                    </td>
                                </tr>
                            ))}


                        </tbody>
                    </table>
                </>}
        </div>
    )
}

export default UserTable