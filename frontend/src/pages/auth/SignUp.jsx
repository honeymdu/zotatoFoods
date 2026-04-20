import { useState } from 'react'
import { useNavigate, Link } from 'react-router-dom'
import api from '../../api/axios'
import toast from 'react-hot-toast'
import { UtensilsCrossed, MapPin, Loader } from 'lucide-react'

export default function SignUp() {
  const [form, setForm] = useState({
    name: '', email: '', password: '', contact: '',
    addresses: { street: '', city: '', state: '', postalCode: '', userLocation: { type: 'Point', coordianates: [0, 0] } }
  })
  const [loading, setLoading] = useState(false)
  const [locating, setLocating] = useState(false)
  const navigate = useNavigate()

  const detectLocation = () => {
    if (!navigator.geolocation) { toast.error('Geolocation not supported'); return }
    setLocating(true)
    navigator.geolocation.getCurrentPosition(
      ({ coords }) => {
        setForm(f => ({ ...f, addresses: { ...f.addresses, userLocation: { type: 'Point', coordianates: [coords.longitude, coords.latitude] } } }))
        toast.success('Location detected!')
        setLocating(false)
      },
      () => { toast.error('Could not get location. Allow browser access.'); setLocating(false) }
    )
  }

  const set = (field, value) => setForm(f => ({ ...f, [field]: value }))
  const setAddr = (field, value) => setForm(f => ({ ...f, addresses: { ...f.addresses, [field]: value } }))

  const handleSubmit = async (e) => {
    e.preventDefault()
    setLoading(true)
    try {
      await api.post('/auth/SignUp', form)
      toast.success('Account created! Please login.')
      navigate('/login')
    } catch (err) {
      toast.error(err.response?.data?.error?.message || 'Registration failed')
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="min-h-screen flex items-center justify-center bg-gradient-to-br from-orange-50 to-orange-100 py-10">
      <div className="bg-white rounded-2xl shadow-xl p-8 w-full max-w-md">
        <div className="flex flex-col items-center mb-6">
          <div className="bg-orange-500 text-white p-3 rounded-full mb-3">
            <UtensilsCrossed size={28} />
          </div>
          <h1 className="text-2xl font-bold text-gray-800">Create Account</h1>
          <p className="text-gray-500 text-sm mt-1">Join ZotatoFoods today</p>
        </div>
        <form onSubmit={handleSubmit} className="space-y-3">
          <input className="w-full border border-gray-300 rounded-lg px-4 py-2.5 focus:outline-none focus:ring-2 focus:ring-orange-400" placeholder="Full Name" required value={form.name} onChange={e => set('name', e.target.value)} />
          <input type="email" className="w-full border border-gray-300 rounded-lg px-4 py-2.5 focus:outline-none focus:ring-2 focus:ring-orange-400" placeholder="Email" required value={form.email} onChange={e => set('email', e.target.value)} />
          <input type="password" className="w-full border border-gray-300 rounded-lg px-4 py-2.5 focus:outline-none focus:ring-2 focus:ring-orange-400" placeholder="Password (min 8 chars)" required minLength={8} value={form.password} onChange={e => set('password', e.target.value)} />
          <input className="w-full border border-gray-300 rounded-lg px-4 py-2.5 focus:outline-none focus:ring-2 focus:ring-orange-400" placeholder="Contact (e.g. +911234567890)" value={form.contact} onChange={e => set('contact', e.target.value)} />

          <p className="text-xs font-semibold text-gray-500 uppercase tracking-wider pt-1">Address</p>
          <input className="w-full border border-gray-300 rounded-lg px-4 py-2.5 focus:outline-none focus:ring-2 focus:ring-orange-400" placeholder="Street" required value={form.addresses.street} onChange={e => setAddr('street', e.target.value)} />
          <div className="grid grid-cols-2 gap-3">
            <input className="border border-gray-300 rounded-lg px-4 py-2.5 focus:outline-none focus:ring-2 focus:ring-orange-400" placeholder="City" required value={form.addresses.city} onChange={e => setAddr('city', e.target.value)} />
            <input className="border border-gray-300 rounded-lg px-4 py-2.5 focus:outline-none focus:ring-2 focus:ring-orange-400" placeholder="State" required value={form.addresses.state} onChange={e => setAddr('state', e.target.value)} />
          </div>
          <input className="w-full border border-gray-300 rounded-lg px-4 py-2.5 focus:outline-none focus:ring-2 focus:ring-orange-400" placeholder="Postal Code" required value={form.addresses.postalCode} onChange={e => setAddr('postalCode', e.target.value)} />
          <button type="button" onClick={detectLocation} disabled={locating}
            className="w-full flex items-center justify-center gap-2 border border-orange-400 text-orange-500 py-2.5 rounded-lg hover:bg-orange-50 transition disabled:opacity-60">
            {locating ? <Loader size={16} className="animate-spin" /> : <MapPin size={16} />}
            {locating ? 'Detecting...' : form.addresses.userLocation.coordianates[0] !== 0 ? '✓ Location Detected' : 'Use Current Location'}
          </button>

          <button type="submit" disabled={loading} className="w-full bg-orange-500 hover:bg-orange-600 text-white font-semibold py-2.5 rounded-lg transition disabled:opacity-60 mt-2">
            {loading ? 'Creating account...' : 'Sign Up'}
          </button>
        </form>
        <p className="text-center text-sm text-gray-500 mt-4">
          Already have an account?{' '}
          <Link to="/login" className="text-orange-500 font-medium hover:underline">Sign In</Link>
        </p>
      </div>
    </div>
  )
}
