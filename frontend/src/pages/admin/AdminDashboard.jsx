import { useEffect, useState } from 'react'
import api from '../../api/axios'
import toast from 'react-hot-toast'
import { Shield, Users, Store, CheckCircle, ChevronLeft, ChevronRight } from 'lucide-react'

export default function AdminDashboard() {
  const [tab, setTab] = useState('restaurants')
  const [restaurants, setRestaurants] = useState([])
  const [partners, setPartners] = useState([])
  const [loading, setLoading] = useState(true)
  const [page, setPage] = useState(0)
  const [totalPages, setTotalPages] = useState(1)

  const [onboardForm, setOnboardForm] = useState({ userId: '', type: 'restaurant', vehicleType: '', vehicleNumber: '', licenseNumber: '', restaurantName: '', gstNumber: '' })
  const [showOnboard, setShowOnboard] = useState(false)

  const loadRestaurants = async (p = 0) => {
    setLoading(true)
    try {
      const { data } = await api.get(`/admin/list/get-all-restaurant?PageOffset=${p}&PageSize=10`)
      setRestaurants(data.content || [])
      setTotalPages(data.totalPages || 1)
    } catch { toast.error('Failed to load restaurants') }
    finally { setLoading(false) }
  }

  const loadPartners = async (p = 0) => {
    setLoading(true)
    try {
      const { data } = await api.get(`/admin/list/get-all-Delivery-Partner?PageOffset=${p}&PageSize=10`)
      setPartners(data.content || [])
      setTotalPages(data.totalPages || 1)
    } catch { toast.error('Failed to load partners') }
    finally { setLoading(false) }
  }

  useEffect(() => {
    setPage(0)
    if (tab === 'restaurants') loadRestaurants(0)
    else loadPartners(0)
  }, [tab])

  const verifyRestaurant = async (id) => {
    try {
      await api.post(`/admin/varify-Restaurant/${id}`)
      toast.success('Restaurant verified!')
      loadRestaurants(page)
    } catch (err) {
      toast.error(err.response?.data?.error?.message || 'Failed to verify')
    }
  }

  const handleOnboard = async (e) => {
    e.preventDefault()
    try {
      if (onboardForm.type === 'restaurant') {
        await api.post(`/admin/onBoard-Restaurant-Partner/${onboardForm.userId}`, {
          restaurantName: onboardForm.restaurantName, gstNumber: onboardForm.gstNumber
        })
      } else {
        await api.post(`/admin/onBoard-Delivery-Partner/${onboardForm.userId}`, {
          vehicleType: onboardForm.vehicleType, vehicleNumber: onboardForm.vehicleNumber, licenseNumber: onboardForm.licenseNumber
        })
      }
      toast.success('Onboarded successfully!')
      setShowOnboard(false)
    } catch (err) {
      toast.error(err.response?.data?.error?.message || 'Onboarding failed')
    }
  }

  const changePage = (newPage) => {
    setPage(newPage)
    if (tab === 'restaurants') loadRestaurants(newPage)
    else loadPartners(newPage)
  }

  return (
    <div className="max-w-6xl mx-auto px-4 py-8">
      <div className="flex items-center justify-between mb-6">
        <div className="flex items-center gap-3">
          <div className="bg-orange-500 text-white p-2.5 rounded-xl"><Shield size={24} /></div>
          <h2 className="text-2xl font-bold text-gray-800">Admin Dashboard</h2>
        </div>
        <button onClick={() => setShowOnboard(true)} className="flex items-center gap-2 bg-orange-500 text-white px-4 py-2 rounded-lg text-sm font-medium hover:bg-orange-600 transition">
          <Users size={16} /> Onboard Partner
        </button>
      </div>

      <div className="flex gap-1 bg-gray-100 rounded-xl p-1 mb-6 w-fit">
        {[['restaurants', Store, 'Restaurants'], ['partners', Users, 'Delivery Partners']].map(([key, Icon, label]) => (
          <button key={key} onClick={() => setTab(key)}
            className={`flex items-center gap-2 px-4 py-2 rounded-lg text-sm font-medium transition ${tab === key ? 'bg-white text-orange-600 shadow-sm' : 'text-gray-500 hover:text-gray-700'}`}>
            <Icon size={15} /> {label}
          </button>
        ))}
      </div>

      {loading ? (
        <div className="flex items-center justify-center h-48">
          <div className="animate-spin rounded-full h-10 w-10 border-4 border-orange-500 border-t-transparent" />
        </div>
      ) : tab === 'restaurants' ? (
        <div className="bg-white rounded-xl border border-gray-100 shadow-sm overflow-hidden">
          <table className="w-full text-sm">
            <thead className="bg-gray-50 text-gray-500 uppercase text-xs">
              <tr>
                <th className="px-4 py-3 text-left">ID</th>
                <th className="px-4 py-3 text-left">Name</th>
                <th className="px-4 py-3 text-left">GST</th>
                <th className="px-4 py-3 text-left">Rating</th>
                <th className="px-4 py-3 text-left">Status</th>
                <th className="px-4 py-3 text-left">Action</th>
              </tr>
            </thead>
            <tbody className="divide-y divide-gray-50">
              {restaurants.map(r => (
                <tr key={r.id} className="hover:bg-gray-50">
                  <td className="px-4 py-3 text-gray-500">{r.id}</td>
                  <td className="px-4 py-3 font-medium text-gray-800">{r.name}</td>
                  <td className="px-4 py-3 text-gray-500">{r.gstNumber}</td>
                  <td className="px-4 py-3 text-gray-500">{r.rating?.toFixed(1) || '-'}</td>
                  <td className="px-4 py-3">
                    <span className={`px-2 py-0.5 rounded-full text-xs font-medium ${r.isVarified ? 'bg-green-100 text-green-700' : 'bg-yellow-100 text-yellow-700'}`}>
                      {r.isVarified ? 'Verified' : 'Pending'}
                    </span>
                  </td>
                  <td className="px-4 py-3">
                    {!r.isVarified && (
                      <button onClick={() => verifyRestaurant(r.id)} className="flex items-center gap-1 text-green-600 hover:text-green-700 font-medium text-xs">
                        <CheckCircle size={14} /> Verify
                      </button>
                    )}
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      ) : (
        <div className="bg-white rounded-xl border border-gray-100 shadow-sm overflow-hidden">
          <table className="w-full text-sm">
            <thead className="bg-gray-50 text-gray-500 uppercase text-xs">
              <tr>
                <th className="px-4 py-3 text-left">ID</th>
                <th className="px-4 py-3 text-left">Name</th>
                <th className="px-4 py-3 text-left">Vehicle</th>
                <th className="px-4 py-3 text-left">Rating</th>
                <th className="px-4 py-3 text-left">Status</th>
              </tr>
            </thead>
            <tbody className="divide-y divide-gray-50">
              {partners.map(p => (
                <tr key={p.id} className="hover:bg-gray-50">
                  <td className="px-4 py-3 text-gray-500">{p.id}</td>
                  <td className="px-4 py-3 font-medium text-gray-800">{p.user?.name}</td>
                  <td className="px-4 py-3 text-gray-500">{p.vehicleType} · {p.vehicleNumber}</td>
                  <td className="px-4 py-3 text-gray-500">{p.rating?.toFixed(1) || '-'}</td>
                  <td className="px-4 py-3">
                    <span className={`px-2 py-0.5 rounded-full text-xs font-medium ${p.isAvailable ? 'bg-green-100 text-green-700' : 'bg-gray-100 text-gray-600'}`}>
                      {p.isAvailable ? 'Available' : 'Offline'}
                    </span>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}

      {totalPages > 1 && (
        <div className="flex items-center justify-center gap-3 mt-4">
          <button onClick={() => changePage(page - 1)} disabled={page === 0} className="p-2 rounded-lg border border-gray-300 disabled:opacity-40 hover:bg-gray-50">
            <ChevronLeft size={16} />
          </button>
          <span className="text-sm text-gray-600">Page {page + 1} of {totalPages}</span>
          <button onClick={() => changePage(page + 1)} disabled={page >= totalPages - 1} className="p-2 rounded-lg border border-gray-300 disabled:opacity-40 hover:bg-gray-50">
            <ChevronRight size={16} />
          </button>
        </div>
      )}

      {showOnboard && (
        <div className="fixed inset-0 bg-black/40 flex items-center justify-center z-50">
          <div className="bg-white rounded-2xl shadow-xl p-6 w-full max-w-md">
            <h3 className="text-lg font-bold text-gray-800 mb-4">Onboard Partner</h3>
            <form onSubmit={handleOnboard} className="space-y-3">
              <input className="w-full border border-gray-300 rounded-lg px-4 py-2.5 focus:outline-none focus:ring-2 focus:ring-orange-400" placeholder="User ID" required value={onboardForm.userId} onChange={e => setOnboardForm(f => ({ ...f, userId: e.target.value }))} />
              <select className="w-full border border-gray-300 rounded-lg px-4 py-2.5 focus:outline-none focus:ring-2 focus:ring-orange-400" value={onboardForm.type} onChange={e => setOnboardForm(f => ({ ...f, type: e.target.value }))}>
                <option value="restaurant">Restaurant Partner</option>
                <option value="delivery">Delivery Partner</option>
              </select>
              {onboardForm.type === 'restaurant' ? <>
                <input className="w-full border border-gray-300 rounded-lg px-4 py-2.5 focus:outline-none focus:ring-2 focus:ring-orange-400" placeholder="Restaurant Name" value={onboardForm.restaurantName} onChange={e => setOnboardForm(f => ({ ...f, restaurantName: e.target.value }))} />
                <input className="w-full border border-gray-300 rounded-lg px-4 py-2.5 focus:outline-none focus:ring-2 focus:ring-orange-400" placeholder="GST Number" value={onboardForm.gstNumber} onChange={e => setOnboardForm(f => ({ ...f, gstNumber: e.target.value }))} />
              </> : <>
                <input className="w-full border border-gray-300 rounded-lg px-4 py-2.5 focus:outline-none focus:ring-2 focus:ring-orange-400" placeholder="Vehicle Type" value={onboardForm.vehicleType} onChange={e => setOnboardForm(f => ({ ...f, vehicleType: e.target.value }))} />
                <input className="w-full border border-gray-300 rounded-lg px-4 py-2.5 focus:outline-none focus:ring-2 focus:ring-orange-400" placeholder="Vehicle Number" value={onboardForm.vehicleNumber} onChange={e => setOnboardForm(f => ({ ...f, vehicleNumber: e.target.value }))} />
                <input className="w-full border border-gray-300 rounded-lg px-4 py-2.5 focus:outline-none focus:ring-2 focus:ring-orange-400" placeholder="License Number" value={onboardForm.licenseNumber} onChange={e => setOnboardForm(f => ({ ...f, licenseNumber: e.target.value }))} />
              </>}
              <div className="flex gap-3 pt-2">
                <button type="button" onClick={() => setShowOnboard(false)} className="flex-1 border border-gray-300 py-2.5 rounded-lg text-gray-600 hover:bg-gray-50 transition">Cancel</button>
                <button type="submit" className="flex-1 bg-orange-500 text-white py-2.5 rounded-lg font-medium hover:bg-orange-600 transition">Onboard</button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  )
}
