import { useEffect, useState } from 'react';
import ProductTable from '../components/table/product'
import { getDatabase, ref, onValue } from "firebase/database";
import { initializeApp } from "firebase/app";


const firebaseConfig = {
  apiKey: "AIzaSyAp3tjI0cIy3vcTLjLjSmM9-Zs3r7MVFxg",
  authDomain: "coffeeapp-20ccf.firebaseapp.com",
  databaseURL: "https://coffeeapp-20ccf-default-rtdb.firebaseio.com",
  projectId: "coffeeapp-20ccf",
  storageBucket: "coffeeapp-20ccf.appspot.com",
  messagingSenderId: "600333067225",
  appId: "1:600333067225:web:1430255bf4b31429442fc8",
  measurementId: "G-6JRVTPKVD9"
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);
const db = getDatabase();

function Product() {
  const [data, setData] = useState(null);

  useEffect(() => {
  
    const productsRef = ref(db, 'products');
    onValue(productsRef, (snapshot) => {
      const products = [];
      snapshot.forEach((childSnapshot) => {
        const product = childSnapshot.val();
        product.id = childSnapshot.key;
        products.push(product);
      });
      setData(products);
    });

    
  }, []);

  return (
    <div>
      <h2 style={{paddingBottom: '12px'}}>All Products</h2>
      {data ? <ProductTable data={data} handleDelete={() => {}} /> : <p>Loading...</p>}
    </div>
  );
}

export default Product;
