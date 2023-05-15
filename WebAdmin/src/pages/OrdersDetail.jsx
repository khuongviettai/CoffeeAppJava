import React, { useState, useEffect } from 'react'
import { useParams } from 'react-router-dom'
import { Link } from 'react-router-dom';
import Button from '../components/button/Buttons';
import { getDatabase, ref, onValue } from "firebase/database";


function OrdersDetail() {
    const db = getDatabase();
    const [data, setData] = useState(null);
    const { id } = useParams();
    console.log(id)
    useEffect(() => {

        const ordersRef = ref(db, `booking/${id}`);
        onValue(ordersRef, (snapshot) => {
            const orders = [];
            snapshot.forEach((childSnapshot) => {
                const order = childSnapshot.val();
                order.id = childSnapshot.key;
                orders.push(order);
            });
            setData(orders);
        });


    }, [db, id]);
    console.log(data)
    return (

        <>
            <h1>Orders Detail</h1>
            <div style={{ display: 'flex' }}>
                <Link to='/orders'>
                    <Button text={"Back"} type={"primary"} />
                </Link>
            </div>
            {data && data.map((item) =>
            (
                <div className='wrap__order-form'>
                    <div className='wrap__order-detail'>
                        <div className='flex'>
                            <div className='heading'>Name:</div>
                            <div>{item.name}</div>
                        </div>
                        <div className='flex'>
                            <div className='heading'>Chi tiết đơn hàng:</div>
                            <div>{item.foods}</div>
                        </div>
                        <div className='flex'>
                            <div className='heading'>Tổng tiền:</div>
                            <div>{item.amount}</div>
                        </div>
                    </div>
                </div>

            ))}
        </>
    )
}

export default OrdersDetail