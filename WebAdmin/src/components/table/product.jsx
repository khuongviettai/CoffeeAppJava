import React from 'react'
import './table.scss'
import { Link } from 'react-router-dom'
import Button from '../button/Buttons'

function ProductTable({ data, handleDelete }) {
    
    const boolean = data && data.length > 0;

    return (
        <div>
            {boolean && 
            <>
                <div style={{display: 'flex', paddingBottom: '16px'}}>
                <Link to='/addproduct'>
                    <Button text={('Add Product')} type={('edit')} />
                </Link>
            </div>
                <table>
                    <thead>
                        <tr>
                            <th className='table__img'>Image</th>
                            <th className='table__name'>Name</th>
                            <th className='table__price'>Price</th>
                            <th className='table__function'></th>
                        </tr>
                    </thead>
        
                    <tbody>
                        {data.map((item) => (
                            <tr key={item.id}>
                                <td>
                                    <img src={item.image} alt=''/>
                                </td>
                                <td>{item.name}</td>
                                <td>{item.price}</td>
                                <td>
                                    <div className='wrap__option'>
                                        <Link to={`/update/product/${item.id}`}
                                        onClick={() => console.log(item)}
                                        >
                                        <div className='button edit' >Edit</div>
                                        </Link>
                                        <div className='button delete' onClick={() => handleDelete(item.id)}>Delete</div>
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

export default ProductTable
