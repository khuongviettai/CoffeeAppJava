import React, { useEffect, useState } from 'react'
import OrderTable from '../components/table/Order'
import { getDatabase, ref, onValue} from "firebase/database";

function Order() {
  const db = getDatabase();

  const [data, setData] = useState([])

  // const deletePromiseRef = useRef(null)
  useEffect(() => {
    const usersRef = ref(db, 'users');
    onValue(usersRef, (snapshot) => {
      const users = [];
      snapshot.forEach((childSnapshot) => {
        const user = childSnapshot.val();
        user.id = childSnapshot.key;
        users.push(user);
      });
      setData(users);
    });


  }, [db])

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

      <h2 style={{ paddingBottom: '12px' }}>All Ordes</h2>

      <OrderTable value={data} />

    </div>
  )
}

export default Order