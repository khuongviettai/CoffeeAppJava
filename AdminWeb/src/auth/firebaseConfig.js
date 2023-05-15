// Import the functions you need from the SDKs you need
import { initializeApp } from "firebase/app";
import { getAnalytics } from "firebase/analytics";
// TODO: Add SDKs for Firebase products that you want to use
// https://firebase.google.com/docs/web/setup#available-libraries

// Your web app's Firebase configuration
// For Firebase JS SDK v7.20.0 and later, measurementId is optional
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
const productsRef = ref(db, 'products');
const analytics = getAnalytics(app);