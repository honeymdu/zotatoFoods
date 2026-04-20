import { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import api from '../../api/axios'
import toast from 'react-hot-toast'
import { Store, Plus, ClipboardList, ChevronRight } from 'lucide-react'

const ORDER_STATUSES = ['PREPARING', 'READY_FOR_PICKUP']

export default function RestaurantDashboard() {
  const [restaurantId, setRestaurantId] = useState(localStorage.getItem('restaurantId') || '')
  const [restaurant, setRestaurant] = useState(null)
  const [orderRequests, setOrderRequests] = useState([])
  const [loadingProfile, setLoadingProfile] = useState(false)
  const [showAddRestaurant, setShowAddRestaurant] = useState(false)
  const [newRestaurant, setNewRestaurant] = useState({ name: '', gstNumber: '', restaurantLocation: { type: 'Point', coordianates: [0, 0] } })
  const [updatingOrder, setUpdatingOrder] = useState(null)
  const navigate = useNavigate()

  const loadRestaurant = async (id) => {
    setLoadingProfile(true)
    try {
      const [profileRes, ordersRes] = await Promise.all([
        api.get(`/restaurant-partner/view-my-restaurant-profile/${id}`),
        api.get(`/restaurant-partner/view-Order-Requests/${id}`)
      ])
      setRestaurant(profileRes.data)
      setOrderRequests(ordersRes.data || [])
    } catch {
      toast.error('Failed to load restaurant')
    } finally {
      setLoadingProfile(false)
    }
  }

  useEffect(() => {
    if (restaurantId) loadRestaurant(restaurantId)
  }, [])

  const handleSetId = () => {
    localStorage.setItem('restaurantId', restaurantId)
    loadRestaurant(restaurantId)
  }

  const createRestaurant = async (e) => {
    e.preventDefault()
    try {
      const { data } = await api.post('/restaurant-partner/add-my-restaurant', newRestaurant)
      setRestaurantId(data.id.toString())
      localStorage.setItem('restaurantId', data.id)
      setRestaurant(data)
      setShowAddRestaurant(false)
      toast.success('Restaurant created!')
    } catch (err) {
      toast.error(err.response?.data?.error?.message || 'Failed to create restaurant')
    }
  }

  const acceptOrder = async (orderId) => {
    setUpdatingOrder(orderId)
    try {
      await api.post(`/restaurant-partner/accept-order-Request/${orderId}`)
      toast.success('Order accepted!')
      loadRestaurant(restaurantId)
    } catch (err) {
      toast.error(err.response?.data?.error?.message || 'Failed to accept order')
    } finally {
      setUpdatingOrder(null)
    }
  }

  const updateStatus = async (orderId, status) => {
    try {
      await api.post(`/restaurant-partner/update-order-status/${orderId}`, { orderStatus: status })
      toast.success('Status updated!')
      loadRestaurant(restaurantId)
    } catch (err) {
      toast.error(err.response?.data?.error?.message || 'Failed to update status')
    }
  }

  return (
    <div className="max-w-5xl mx-auto px-4 py-8">
      <div className="flex items-center justify-between mb-6">
        <h2 className="text-2xl font-bold text-gray-800">Restaurant Dashboard</h2>
        <button onClick={() => setShowAddRestaurant(true)} className="flex items-center gap-2 bg-orange-500 text-white px-4 py-2 rounded-lg text-sm font-medium hover:bg-orange-600 transition">
          <Plus size={16} /> Add Restaurant
        </button>
      </div>

      {!restaurant && (
        <div className="bg-white rounded-xl border border-gray-100 shadow-sm p-6 mb-6">
          <p className="text-gray-600 text-sm mb-3">Enter your Restaurant ID to load your dashboard:</p>
          <div className="flex gap-3">
            <input className="border border-gray-300 rounded-lg px-4 py-2 text-sm flex-1 focus:outline-none focus:ring-2 focus:ring-orange-400" placeholder="Restaurant ID" value={restaurantId} onChange={e => setRestaurantId(e.target.value)} />
            <button onClick={handleSetId} className="bg-orange-500 text-white px-4 py-2 rounded-lg text-sm font-medium hover:bg-orange-600 transition">Load</button>
          </div>
        </div>
      )}

      {showAddRestaurant && (
        <div className="fixed inset-0 bg-black/40 flex items-center justify-center z-50">
          <div className="bg-white rounded-2xl shadow-xl p-6 w-full max-w-md">
            <h3 className="text-lg font-bold text-gray-800 mb-4">Add Restaurant</h3>
            <form onSubmit={createRestaurant} className="space-y-3">
              <input className="w-full border border-gray-300 rounded-lg px-4 py-2.5 focus:outline-none focus:ring-2 focus:ring-orange-400" placeholder="Restaurant Name" required value={newRestaurant.name} onChange={e => setNewRestaurant(r => ({ ...r, name: e.target.value }))} />
              <input className="w-full border border-gray-300 rounded-lg px-4 py-2.5 focus:outline-none focus:ring-2 focus:ring-orange-400" placeholder="GST Number" required value={newRestaurant.gstNumber} onChange={e => setNewRestaurant(r => ({ ...r, gstNumber: e.target.value }))} />
              <div className="grid grid-cols-2 gap-3">
                <input type="number" step="any" className="border border-gray-300 rounded-lg px-4 py-2.5 focus:outline-none focus:ring-2 focus:ring-orange-400" placeholder="Longitude" onChange={e => setNewRestaurant(r => ({ ...r, restaurantLocation: { ...r.restaurantLocation, coordianates: [parseFloat(e.target.value), r.restaurantLocation.coordianates[1]] } }))} />
                <input type="number" step="any" className="border border-gray-300 rounded-lg px-4 py-2.5 focus:outline-none focus:ring-2 focus:ring-orange-400" placeholder="Latitude" onChange={e => setNewRestaurant(r => ({ ...r, restaurantLocation: { ...r.restaurantLocation, coordianates: [r.restaurantLocation.coordianates[0], parseFloat(e.target.value)] } }))} />
              </div>
              <div className="flex gap-3 pt-2">
                <button type="button" onClick={() => setShowAddRestaurant(false)} className="flex-1 border border-gray-300 py-2.5 rounded-lg text-gray-600 hover:bg-gray-50 transition">Cancel</button>
                <button type="submit" className="flex-1 bg-orange-500 text-white py-2.5 rounded-lg font-medium hover:bg-orange-600 transition">Create</button>
              </div>
            </form>
          </div>
        </div>
      )}

      {restaurant && (
        <>
          <div className="bg-white rounded-xl border border-gray-100 shadow-sm p-5 mb-6">
            <div className="flex items-start justify-between">
              <div className="flex items-center gap-3">
                <div className="bg-orange-100 text-orange-500 p-2.5 rounded-xl"><Store size={24} /></div>
                <div>
                  <h3 className="font-bold text-gray-800 text-lg">{restaurant.name}</h3>
                  <p className="text-sm text-gray-500">GST: {restaurant.gstNumber}</p>
                </div>
              </div>
              <div className="flex gap-2">
                <button onClick={() => navigate(`/restaurant/menu/${restaurantId}`)} className="flex items-center gap-1 text-sm text-orange-500 border border-orange-300 px-3 py-1.5 rounded-lg hover:bg-orange-50 transition">
                  <ClipboardList size={14} /> Manage Menu
                </button>
              </div>
            </div>
            <div className="mt-3 flex gap-3">
              <span className={`text-xs px-2 py-1 rounded-full font-medium ${restaurant.isAvailable ? 'bg-green-100 text-green-700' : 'bg-red-100 text-red-600'}`}>{restaurant.isAvailable ? 'Open' : 'Closed'}</span>
              <span className={`text-xs px-2 py-1 rounded-full font-medium ${restaurant.isVarified ? 'bg-blue-100 text-blue-700' : 'bg-yellow-100 text-yellow-700'}`}>{restaurant.isVarified ? 'Verified' : 'Pending Verification'}</span>
            </div>
          </div>

          <h3 className="text-lg font-bold text-gray-800 mb-3">Pending Order Requests</h3>
          {orderRequests.length === 0 ? (
            <div className="text-center py-10 text-gray-400 bg-white rounded-xl border border-gray-100">No pending orders</div>
          ) : (
            <div className="space-y-3">
              {orderRequests.map(order => (
                <div key={order.id} className="bg-white rounded-xl border border-gray-100 shadow-sm p-4">
                  <div className="flex items-center justify-between mb-2">
                    <span className="font-semibold text-gray-800">Order #{order.id}</span>
                    <span className="text-xs bg-yellow-100 text-yellow-700 px-2 py-0.5 rounded-full">{order.orderRequestStatus}</span>
                  </div>
                  <p className="text-sm text-gray-500 mb-1">Payment: {order.paymentMethod}</p>
                  <p className="text-sm text-gray-500 mb-3">Total: ₹{order.fare?.toFixed(2)}</p>
                  <div className="flex gap-2 flex-wrap">
                    <button onClick={() => acceptOrder(order.id)} disabled={updatingOrder === order.id}
                      className="bg-green-500 text-white px-4 py-1.5 rounded-lg text-sm font-medium hover:bg-green-600 transition disabled:opacity-60">
                      Accept
                    </button>
                    {ORDER_STATUSES.map(s => (
                      <button key={s} onClick={() => updateStatus(order.id, s)}
                        className="border border-orange-300 text-orange-600 px-3 py-1.5 rounded-lg text-sm hover:bg-orange-50 transition">
                        → {s.replace(/_/g, ' ')}
                      </button>
                    ))}
                  </div>
                </div>
              ))}
            </div>
          )}
        </>
      )}
    </div>
  )
}
