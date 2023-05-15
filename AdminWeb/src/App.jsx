import './assets/libs/boxicons-2.1.1/css/boxicons.min.css'
import './scss/App.scss'
import { BrowserRouter, Routes, Route } from 'react-router-dom'
import Blank from './pages/Blank'
import Dashboard from './pages/Dashboard'
import MainLayout from './layout/MainLayout'

import Product from './pages/Product'
import CreateProduct from './pages/CreateProduct'

import UpdateProduct from './pages/UpdateProduct'
import UpdateUser from './pages/UpdateUser'
import User from './pages/Users'
import Order from './pages/Orders'
import CreateUser from './pages/CreateUser'
function App() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<MainLayout />}>
                    <Route index element={<Dashboard />} />
                    <Route path="products" element={<Product />} />

                    <Route path="addproduct" element={<CreateProduct />} />
                    <Route path="adduser" element={<CreateUser />} />

                    <Route path="update/product/:id" element={<UpdateProduct />} />
                    <Route path="update/user/:id" element={<UpdateUser />} />

                    <Route path="orders" element={<Order />} />

                    <Route path="users" element={<User />} />
                    <Route path="settings" element={<Blank />} />
                    <Route path="stats" element={<Blank />} />
                </Route>
            </Routes>
        </BrowserRouter>
    )
}

export default App
