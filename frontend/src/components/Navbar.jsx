import { useAuth } from '../context/AuthContext'
import { useNavigate } from 'react-router-dom'
import { LogOut, UtensilsCrossed } from 'lucide-react'

const roleLabel = {
  ROLE_CONSUMER: 'Consumer',
  ROLE_RESTAURENT_PARTNER: 'Restaurant Partner',
  ROLE_DELIVERY_PARTNER: 'Delivery Partner',
  ROLE_ADMIN: 'Admin',
}

export default function Navbar() {
  const { user, role, logout } = useAuth()
  const navigate = useNavigate()

  const handleLogout = () => {
    logout()
    navigate('/login')
  }

  return (
    <nav className="bg-orange-500 text-white shadow-md">
      <div className="max-w-7xl mx-auto px-4 py-3 flex items-center justify-between">
        <div className="flex items-center gap-2 text-xl font-bold cursor-pointer" onClick={() => navigate('/')}>
          <UtensilsCrossed size={24} />
          ZotatoFoods
        </div>
        {user && (
          <div className="flex items-center gap-4">
            <span className="text-sm bg-orange-600 px-3 py-1 rounded-full">{roleLabel[role] || role}</span>
            <span className="text-sm hidden sm:block">{user.sub}</span>
            <button onClick={handleLogout} className="flex items-center gap-1 bg-white text-orange-500 px-3 py-1 rounded-lg text-sm font-medium hover:bg-orange-50 transition">
              <LogOut size={16} /> Logout
            </button>
          </div>
        )}
      </div>
    </nav>
  )
}
