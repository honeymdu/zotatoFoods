import { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import api from '../../api/axios'
import toast from 'react-hot-toast'
import { Store, ShoppingCart, Star } from 'lucide-react'

export default function ConsumerDashboard() {
  const [restaurants, setRestaurants] = useState([])
  const [loading, setLoading] = useState(true)
  const navigate = useNavigate()

  useEffect(() => {
    api.get('/consumer/list/get-available-restaurant?PageOffset=0&PageSize=20')
      .then(r => setRestaurants(r.data.content || []))
      .catch(() => toast.error('Failed to load restaurants'))
      .finally(() => setLoading(false))
  }, [])

  if (loading) return (
    <div className="flex items-center justify-center h-64">
      <div className="animate-spin rounded-full h-10 w-10 border-4 border-orange-500 border-t-transparent" />
    </div>
  )

  return (
    <div className="max-w-7xl mx-auto px-4 py-8">
      <h2 className="text-2xl font-bold text-gray-800 mb-6">Available Restaurants</h2>
      {restaurants.length === 0 ? (
        <div className="text-center py-20 text-gray-400">
          <Store size={48} className="mx-auto mb-3 opacity-40" />
          <p>No restaurants available right now</p>
        </div>
      ) : (
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
          {restaurants.map(r => (
            <div key={r.id} onClick={() => navigate(`/consumer/restaurant/${r.id}`)}
              className="bg-white rounded-xl shadow-sm border border-gray-100 p-5 cursor-pointer hover:shadow-md hover:border-orange-200 transition">
              <div className="flex items-start justify-between mb-3">
                <div className="bg-orange-100 text-orange-600 p-2 rounded-lg">
                  <Store size={22} />
                </div>
                <span className={`text-xs px-2 py-1 rounded-full font-medium ${r.isAvailable ? 'bg-green-100 text-green-700' : 'bg-red-100 text-red-600'}`}>
                  {r.isAvailable ? 'Open' : 'Closed'}
                </span>
              </div>
              <h3 className="font-semibold text-gray-800 text-lg">{r.name}</h3>
              <p className="text-sm text-gray-500 mt-1">GST: {r.gstNumber}</p>
              <div className="flex items-center gap-1 mt-3 text-amber-500">
                <Star size={16} fill="currentColor" />
                <span className="text-sm font-medium text-gray-700">{r.rating?.toFixed(1) || 'New'}</span>
              </div>
              <button className="mt-4 w-full bg-orange-500 hover:bg-orange-600 text-white text-sm font-medium py-2 rounded-lg transition flex items-center justify-center gap-2">
                <ShoppingCart size={16} /> View Menu
              </button>
            </div>
          ))}
        </div>
      )}
    </div>
  )
}
