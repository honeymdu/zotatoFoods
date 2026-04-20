import { useState } from 'react'
import api from '../../api/axios'
import toast from 'react-hot-toast'
import { Truck, CheckCircle, XCircle, Package } from 'lucide-react'

export default function DeliveryDashboard() {
  const [deliveryRequestId, setDeliveryRequestId] = useState('')
  const [restaurantOtp, setRestaurantOtp] = useState('')
  const [consumerOtp, setConsumerOtp] = useState('')
  const [loading, setLoading] = useState(null)

  const action = async (type) => {
    if (!deliveryRequestId) { toast.error('Enter Delivery Request ID'); return }
    setLoading(type)
    try {
      if (type === 'accept') {
        await api.post(`/delivery-partner/accept-delivery-request/${deliveryRequestId}`)
        toast.success('Delivery request accepted!')
      } else if (type === 'cancel') {
        await api.post(`/delivery-partner/cancel-delivery-request/${deliveryRequestId}`)
        toast.success('Delivery request cancelled')
      } else if (type === 'pickup') {
        if (!restaurantOtp) { toast.error('Enter restaurant OTP'); setLoading(null); return }
        await api.post(`/delivery-partner/pick-up-order/${deliveryRequestId}`, { restaurantOTP: restaurantOtp })
        toast.success('Order picked up!')
      } else if (type === 'complete') {
        if (!consumerOtp) { toast.error('Enter consumer OTP'); setLoading(null); return }
        await api.post(`/delivery-partner/complete-delivery-request/${deliveryRequestId}`, { consumerOTP: consumerOtp })
        toast.success('Delivery completed!')
        setDeliveryRequestId('')
        setRestaurantOtp('')
        setConsumerOtp('')
      }
    } catch (err) {
      toast.error(err.response?.data?.error?.message || 'Action failed')
    } finally {
      setLoading(null)
    }
  }

  return (
    <div className="max-w-2xl mx-auto px-4 py-8">
      <div className="flex items-center gap-3 mb-8">
        <div className="bg-orange-500 text-white p-2.5 rounded-xl"><Truck size={24} /></div>
        <h2 className="text-2xl font-bold text-gray-800">Delivery Partner Dashboard</h2>
      </div>

      <div className="bg-white rounded-xl border border-gray-100 shadow-sm p-6 space-y-5">
        <div>
          <label className="block text-sm font-medium text-gray-700 mb-1">Delivery Request ID</label>
          <input
            className="w-full border border-gray-300 rounded-lg px-4 py-2.5 focus:outline-none focus:ring-2 focus:ring-orange-400"
            placeholder="Enter delivery request ID"
            value={deliveryRequestId}
            onChange={e => setDeliveryRequestId(e.target.value)}
          />
        </div>

        <div className="flex gap-3">
          <button onClick={() => action('accept')} disabled={loading === 'accept'}
            className="flex-1 flex items-center justify-center gap-2 bg-green-500 hover:bg-green-600 text-white py-2.5 rounded-lg font-medium transition disabled:opacity-60">
            <CheckCircle size={16} /> {loading === 'accept' ? 'Accepting...' : 'Accept'}
          </button>
          <button onClick={() => action('cancel')} disabled={loading === 'cancel'}
            className="flex-1 flex items-center justify-center gap-2 bg-red-500 hover:bg-red-600 text-white py-2.5 rounded-lg font-medium transition disabled:opacity-60">
            <XCircle size={16} /> {loading === 'cancel' ? 'Cancelling...' : 'Cancel'}
          </button>
        </div>

        <hr className="border-gray-100" />

        <div>
          <label className="block text-sm font-medium text-gray-700 mb-1">Restaurant OTP (for pickup)</label>
          <div className="flex gap-3">
            <input
              className="flex-1 border border-gray-300 rounded-lg px-4 py-2.5 focus:outline-none focus:ring-2 focus:ring-orange-400"
              placeholder="4-digit OTP from restaurant"
              value={restaurantOtp}
              onChange={e => setRestaurantOtp(e.target.value)}
              maxLength={4}
            />
            <button onClick={() => action('pickup')} disabled={loading === 'pickup'}
              className="flex items-center gap-2 bg-orange-500 hover:bg-orange-600 text-white px-4 py-2.5 rounded-lg font-medium transition disabled:opacity-60">
              <Package size={16} /> {loading === 'pickup' ? 'Picking...' : 'Pick Up'}
            </button>
          </div>
        </div>

        <div>
          <label className="block text-sm font-medium text-gray-700 mb-1">Consumer OTP (to complete delivery)</label>
          <div className="flex gap-3">
            <input
              className="flex-1 border border-gray-300 rounded-lg px-4 py-2.5 focus:outline-none focus:ring-2 focus:ring-orange-400"
              placeholder="4-digit OTP from consumer"
              value={consumerOtp}
              onChange={e => setConsumerOtp(e.target.value)}
              maxLength={4}
            />
            <button onClick={() => action('complete')} disabled={loading === 'complete'}
              className="flex items-center gap-2 bg-blue-500 hover:bg-blue-600 text-white px-4 py-2.5 rounded-lg font-medium transition disabled:opacity-60">
              <CheckCircle size={16} /> {loading === 'complete' ? 'Completing...' : 'Complete'}
            </button>
          </div>
        </div>
      </div>

      <div className="bg-orange-50 border border-orange-200 rounded-xl p-4 mt-5 text-sm text-orange-700">
        <p className="font-semibold mb-1">Delivery Flow:</p>
        <ol className="list-decimal ml-4 space-y-1">
          <li>Enter Delivery Request ID → Accept the request</li>
          <li>Go to restaurant → Enter Restaurant OTP → Pick Up order</li>
          <li>Deliver to consumer → Enter Consumer OTP → Complete delivery</li>
        </ol>
      </div>
    </div>
  )
}
