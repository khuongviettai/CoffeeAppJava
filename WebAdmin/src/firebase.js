import {initializeApp} from 'firebase/app'
import {getDatabase} from "firebase/database"

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

const app = initializeApp(firebaseConfig)
export const database = getDatabase()