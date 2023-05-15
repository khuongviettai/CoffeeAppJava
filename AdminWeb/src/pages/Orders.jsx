import React, { useEffect, useState, useRef } from 'react'
import OrderTable from '../components/table/Order'
import axios from 'axios'
function Order() {
    const [data, setData] = useState([])
   
    // const deletePromiseRef = useRef(null)
    useEffect(() => {
        const fetchData = async () => {
          const response = await fetch('https://api-coffee-e8kl.onrender.com/api/user')
          const data = await response.json()
          setData(data)
        }
    
        fetchData()
        //react-hooks/exhaustive-deps
        // return () => {
        //   if (deletePromiseRef.current) {
        //     deletePromiseRef.current.cancel()
        //   }
        // }
        
        
      }, [])
    
    //   const handleDelete = (id) => {
    //     deletePromiseRef.current = axios.delete(`https://api-coffee-e8kl.onrender.com/api/user/${id}`)
    //       .then(() => {
    //         setData(data.filter(item => item._id !== id))
    //       })
    //       .catch(err => {
    //         console.log(err)
    //       })
    //   }

    return (
        <div>
            
            <h2 style={{paddingBottom: '12px'}}>All Ordes</h2>

            <OrderTable value={data} />

        </div>
    )
}

export default Order