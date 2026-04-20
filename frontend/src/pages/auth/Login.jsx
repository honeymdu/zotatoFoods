import { useState } from 'react'
import { useNavigate, Link } from 'react-router-dom'
import { useAuth } from '../../context/AuthContext'
import api from '../../api/axios'
import toast from 'react-hot-toast'
import { jwtDecode } from 'jwt-decode'
import { UtensilsCrossed } from 'lucide-react'

export default function Login() {
  const [form, setForm] = useState({ email: '', password: '' })
  const [loading, setLoading] = useState(false)
  const { login, role: currentRole } = useAuth()
  const navigate = useNavigate()

  const roleRedirect = {
    ROLE_CONSUMER: '/consumer',
    ROLE_RESTAURENT_PARTNER: '/restaurant',
    ROLE_DELIVERY_PARTNER: '/delivery',
    ROLE_ADMIN: '/admin',
  }

  const handleSubmit = async (e) => {
    e.preventDefault()
    setLoading(true)
    try {
      const { data } = await api.post('/auth/login', form, { withCredentials: true })
      login(data.accessToken)
      const decoded = jwtDecode(data.accessToken)
      const rawRole = decoded?.Roles?.match(/\[(.+?)\]/)?.[1]?.trim()
      const role = rawRole ? 'ROLE_' + rawRole : null
      toast.success('Welcome back!')
      navigate(roleRedirect[role] || '/')
    } catch (err) {
      toast.error(err.response?.data?.error?.message || 'Invalid credentials')
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="min-h-screen flex items-center justify-center bg-gradient-to-br from-orange-50 to-orange-100">
      <div className="bg-white rounded-2xl shadow-xl p-8 w-full max-w-md">
        <div className="flex flex-col items-center mb-8">
          <div className="bg-orange-500 text-white p-3 rounded-full mb-3">
            <UtensilsCrossed size={32} />
          </div>
          <h1 className="text-2xl font-bold text-gray-800">ZotatoFoods</h1>
          <p className="text-gray-500 text-sm mt-1">Sign in to your account</p>
        </div>
        <form onSubmit={handleSubmit} className="space-y-4">
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-1">Email</label>
            <input
              type="email" required
              className="w-full border border-gray-300 rounded-lg px-4 py-2.5 focus:outline-none focus:ring-2 focus:ring-orange-400"
              value={form.email}
              onChange={(e) => setForm({ ...form, email: e.target.value })}
              placeholder="you@example.com"
            />
          </div>
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-1">Password</label>
            <input
              type="password" required
              className="w-full border border-gray-300 rounded-lg px-4 py-2.5 focus:outline-none focus:ring-2 focus:ring-orange-400"
              value={form.password}
              onChange={(e) => setForm({ ...form, password: e.target.value })}
              placeholder="••••••••"
            />
          </div>
          <button
            type="submit" disabled={loading}
            className="w-full bg-orange-500 hover:bg-orange-600 text-white font-semibold py-2.5 rounded-lg transition disabled:opacity-60"
          >
            {loading ? 'Signing in...' : 'Sign In'}
          </button>
        </form>
        <p className="text-center text-sm text-gray-500 mt-6">
          Don&apos;t have an account?{' '}
          <Link to="/signup" className="text-orange-500 font-medium hover:underline">Sign Up</Link>
        </p>
      </div>
    </div>
  )
}
