import React from 'react'
import './table.scss'
import { Link } from 'react-router-dom'
import Button from '../button/Buttons'
function OrderTable({ value }) {

    let boolean = (value.length > 0) ? true : false



    return (
        <div>

            {boolean &&
                <>
                    
                    <table>
                        <thead>
                            <tr>
                                <th className='table__phone'>Phone</th>
                                <th className='table__name-user'>Name</th>
                                <th className='table__password'>Address</th>
                                <th className='table__order'>Orders</th>
                                <th className='table__function'></th>
                            </tr>
                        </thead>

                        <tbody>
                            {value?.map((item) => (
                                <tr key={item.id}>
                                    <td>
                                        {item.phone}
                                    </td>
                                    <td>{item.fullName}</td>
                                    <td>{item.address}</td>
                                    <td>
                                        <Link to={`/orders/${item.id}`}>
                                            <Button text={"Show"} type={"edit"} />
                                        </Link>
                                    </td>
                                    <td>
                                        <div className='wrap__option'>
                                            <Link to={`/update/user/${item.id}`}
                                            // onClick={() => console.log(item.id)}
                                            >
                                                <div className='button edit' >Edit</div>
                                            </Link>
                                            <div className='button delete' onClick={() => console.log(item._id)} >Delete</div>
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

export default OrderTable