import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom'
import { Toaster } from 'react-hot-toast'
import { AuthProvider, useAuth } from './context/AuthContext'
import Navbar from './components/Navbar'
import PrivateRoute from './components/PrivateRoute'

import Login from './pages/auth/Login'
import SignUp from './pages/auth/SignUp'
import ConsumerDashboard from './pages/consumer/ConsumerDashboard'
import RestaurantMenu from './pages/consumer/RestaurantMenu'
import Cart from './pages/consumer/Cart'
import RestaurantDashboard from './pages/restaurant/RestaurantDashboard'
import ManageMenu from './pages/restaurant/ManageMenu'
import DeliveryDashboard from './pages/delivery/DeliveryDashboard'
import AdminDashboard from './pages/admin/AdminDashboard'

function RoleRedirect() {
  const { role } = useAuth()
  const map = {
    ROLE_CONSUMER: '/consumer',
    ROLE_RESTAURENT_PARTNER: '/restaurant',
    ROLE_DELIVERY_PARTNER: '/delivery',
    ROLE_ADMIN: '/admin',
  }
  return <Navigate to={map[role] || '/login'} replace />
}

function Layout({ children }) {
  return (
    <div className="min-h-screen bg-gray-50">
      <Navbar />
      <main>{children}</main>
    </div>
  )
}

export default function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
        <Toaster position="top-right" toastOptions={{ duration: 3000 }} />
        <Routes>
          <Route path="/login" element={<Login />} />
          <Route path="/signup" element={<SignUp />} />
          <Route path="/" element={<PrivateRoute><RoleRedirect /></PrivateRoute>} />
          <Route path="/unauthorized" element={<Layout><div className="text-center py-20 text-gray-500 text-xl">Access Denied</div></Layout>} />

          <Route path="/consumer" element={<PrivateRoute allowedRoles={['ROLE_CONSUMER']}><Layout><ConsumerDashboard /></Layout></PrivateRoute>} />
          <Route path="/consumer/restaurant/:restaurantId" element={<PrivateRoute allowedRoles={['ROLE_CONSUMER']}><Layout><RestaurantMenu /></Layout></PrivateRoute>} />
          <Route path="/consumer/cart/:restaurantId" element={<PrivateRoute allowedRoles={['ROLE_CONSUMER']}><Layout><Cart /></Layout></PrivateRoute>} />

          <Route path="/restaurant" element={<PrivateRoute allowedRoles={['ROLE_RESTAURENT_PARTNER']}><Layout><RestaurantDashboard /></Layout></PrivateRoute>} />
          <Route path="/restaurant/menu/:restaurantId" element={<PrivateRoute allowedRoles={['ROLE_RESTAURENT_PARTNER']}><Layout><ManageMenu /></Layout></PrivateRoute>} />

          <Route path="/delivery" element={<PrivateRoute allowedRoles={['ROLE_DELIVERY_PARTNER']}><Layout><DeliveryDashboard /></Layout></PrivateRoute>} />

          <Route path="/admin" element={<PrivateRoute allowedRoles={['ROLE_ADMIN']}><Layout><AdminDashboard /></Layout></PrivateRoute>} />

          <Route path="*" element={<Navigate to="/" replace />} />
        </Routes>
      </BrowserRouter>
    </AuthProvider>
  )
}
