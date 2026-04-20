import { useEffect, useState } from 'react'
import { useParams, useNavigate } from 'react-router-dom'
import api from '../../api/axios'
import toast from 'react-hot-toast'
import { Trash2, ArrowLeft, MapPin, CreditCard, Loader } from 'lucide-react'

const PAYMENT_METHODS = ['CASH', 'CARD', 'UPI']

export default function Cart() {
  const { restaurantId } = useParams()
  const navigate = useNavigate()
  const [cart, setCart] = useState(null)
  const [loading, setLoading] = useState(true)
  const [paymentMethod, setPaymentMethod] = useState('CASH')
  const [location, setLocation] = useState({ lat: null, lng: null })
  const [locating, setLocating] = useState(false)

  const detectLocation = () => {
    if (!navigator.geolocation) { toast.error('Geolocation not supported'); return }
    setLocating(true)
    navigator.geolocation.getCurrentPosition(
      ({ coords }) => { setLocation({ lat: coords.latitude, lng: coords.longitude }); toast.success('Location detected!'); setLocating(false) },
      () => { toast.error('Could not get location. Allow browser access.'); setLocating(false) }
    )
  }
  const [ordering, setOrdering] = useState(false)
  const [preOrder, setPreOrder] = useState(null)

  useEffect(() => {
    api.get(`/consumer/view-cart/${restaurantId}`)
      .then(r => setCart(r.data))
      .catch(() => toast.error('Failed to load cart'))
      .finally(() => setLoading(false))
  }, [restaurantId])

  const removeItem = async (cartItemId) => {
    try {
      const { data } = await api.post(`/consumer/remove-cartItem/${cart.id}/${cartItemId}`)
      setCart(data)
      toast.success('Item removed')
    } catch {
      toast.error('Failed to remove item')
    }
  }

  const buildLocationBody = () => ({
    paymentMethod,
    userLocation: { type: 'Point', coordianates: [location.lng, location.lat] }
  })

  const viewPreOrder = async () => {
    if (!location.lat || !location.lng) { toast.error('Detect your location first'); return }
    try {
      const { data } = await api.post(`/consumer/view-pre-order-request/${restaurantId}`, buildLocationBody())
      setPreOrder(data)
    } catch (err) {
      toast.error(err.response?.data?.error?.message || 'Failed to fetch estimate')
    }
  }

  const placeOrder = async () => {
    if (!location.lat || !location.lng) { toast.error('Detect your location first'); return }
    setOrdering(true)
    try {
      const { data } = await api.post(`/consumer/create-order-request/${restaurantId}`, buildLocationBody())
      toast.success('Order placed successfully!')
      navigate('/consumer')
    } catch (err) {
      toast.error(err.response?.data?.error?.message || 'Failed to place order')
    } finally {
      setOrdering(false)
    }
  }

  if (loading) return (
    <div className="flex items-center justify-center h-64">
      <div className="animate-spin rounded-full h-10 w-10 border-4 border-orange-500 border-t-transparent" />
    </div>
  )

  const items = cart?.cartItems || []
  const total = items.reduce((s, i) => s + (i.totalPrice || 0), 0)

  return (
    <div className="max-w-2xl mx-auto px-4 py-6">
      <button onClick={() => navigate(`/consumer/restaurant/${restaurantId}`)} className="flex items-center gap-2 text-gray-500 hover:text-orange-500 mb-4 text-sm">
        <ArrowLeft size={16} /> Back to Menu
      </button>
      <h2 className="text-2xl font-bold text-gray-800 mb-5">Your Cart</h2>

      {items.length === 0 ? (
        <div className="text-center py-16 text-gray-400">Cart is empty</div>
      ) : (
        <>
          <div className="bg-white rounded-xl border border-gray-100 shadow-sm divide-y divide-gray-50 mb-5">
            {items.map(item => (
              <div key={item.id} className="flex items-center justify-between px-4 py-3">
                <div>
                  <p className="font-medium text-gray-800">{item.menuItem?.name}</p>
                  <p className="text-sm text-gray-500">Qty: {item.quantity}</p>
                </div>
                <div className="flex items-center gap-3">
                  <span className="font-semibold text-orange-600">₹{item.totalPrice?.toFixed(2)}</span>
                  <button onClick={() => removeItem(item.id)} className="text-red-400 hover:text-red-600">
                    <Trash2 size={16} />
                  </button>
                </div>
              </div>
            ))}
            <div className="flex justify-between px-4 py-3 font-bold text-gray-800">
              <span>Total</span>
              <span className="text-orange-600">₹{total.toFixed(2)}</span>
            </div>
          </div>

          <div className="bg-white rounded-xl border border-gray-100 shadow-sm p-4 mb-4 space-y-3">
            <div className="flex items-center gap-2 text-gray-700 font-medium mb-1">
              <MapPin size={16} className="text-orange-500" /> Delivery Location
            </div>
            <button type="button" onClick={detectLocation} disabled={locating}
              className="w-full flex items-center justify-center gap-2 border border-orange-400 text-orange-500 py-2.5 rounded-lg hover:bg-orange-50 transition disabled:opacity-60 text-sm">
              {locating ? <Loader size={15} className="animate-spin" /> : <MapPin size={15} />}
              {locating ? 'Detecting...' : location.lat ? `✓ ${location.lat.toFixed(4)}, ${location.lng.toFixed(4)}` : 'Use Current Location'}
            </button>
            <div className="flex items-center gap-2 text-gray-700 font-medium mb-1">
              <CreditCard size={16} className="text-orange-500" /> Payment Method
            </div>
            <div className="flex gap-2">
              {PAYMENT_METHODS.map(m => (
                <button key={m} onClick={() => setPaymentMethod(m)}
                  className={`flex-1 py-2 rounded-lg text-sm font-medium border transition ${paymentMethod === m ? 'bg-orange-500 text-white border-orange-500' : 'border-gray-300 text-gray-600 hover:border-orange-300'}`}>
                  {m}
                </button>
              ))}
            </div>
          </div>

          {preOrder && (
            <div className="bg-orange-50 border border-orange-200 rounded-xl p-4 mb-4 text-sm space-y-1">
              <p className="font-semibold text-orange-700">Order Estimate</p>
              <p>Subtotal: ₹{preOrder.foodAmount?.toFixed(2)}</p>
              <p>Delivery Fare: ₹{preOrder.deliveryFee?.toFixed(2)}</p>
              <p>Platform Commission: ₹{preOrder.platformFee?.toFixed(2)}</p>
              <p className="font-bold text-orange-700">Total: ₹{preOrder.totalPrice?.toFixed(2)}</p>
            </div>
          )}

          <div className="flex gap-3">
            <button onClick={viewPreOrder} className="flex-1 border border-orange-500 text-orange-500 py-2.5 rounded-lg font-medium hover:bg-orange-50 transition">
              Get Estimate
            </button>
            <button onClick={placeOrder} disabled={ordering} className="flex-1 bg-orange-500 hover:bg-orange-600 text-white py-2.5 rounded-lg font-medium transition disabled:opacity-60">
              {ordering ? 'Placing...' : 'Place Order'}
            </button>
          </div>
        </>
      )}
    </div>
  )
}
