import { useEffect, useState } from 'react'
import { useParams, useNavigate } from 'react-router-dom'
import api from '../../api/axios'
import toast from 'react-hot-toast'
import { ShoppingCart, Plus, ArrowLeft, Package } from 'lucide-react'

export default function RestaurantMenu() {
  const { restaurantId } = useParams()
  const navigate = useNavigate()
  const [menu, setMenu] = useState(null)
  const [cart, setCart] = useState(null)
  const [loading, setLoading] = useState(true)
  const [addingItem, setAddingItem] = useState(null)

  useEffect(() => {
    Promise.all([
      api.get(`/consumer/view-menu/${restaurantId}`),
      api.get(`/consumer/view-cart/${restaurantId}`).catch(() => ({ data: null }))
    ]).then(([menuRes, cartRes]) => {
      setMenu(menuRes.data)
      setCart(cartRes.data)
    }).catch(() => toast.error('Failed to load menu'))
      .finally(() => setLoading(false))
  }, [restaurantId])

  const addToCart = async (itemId) => {
    setAddingItem(itemId)
    try {
      const { data } = await api.post(`/consumer/prepareCart/${restaurantId}/${itemId}`)
      setCart(data)
      toast.success('Added to cart!')
    } catch (err) {
      toast.error(err.response?.data?.error?.message || 'Failed to add item')
    } finally {
      setAddingItem(null)
    }
  }

  if (loading) return (
    <div className="flex items-center justify-center h-64">
      <div className="animate-spin rounded-full h-10 w-10 border-4 border-orange-500 border-t-transparent" />
    </div>
  )

  const cartCount = cart?.cartItems?.length || 0
  const cartTotal = cart?.cartItems?.reduce((s, i) => s + (i.totalPrice || 0), 0) || 0

  return (
    <div className="max-w-4xl mx-auto px-4 py-6">
      <button onClick={() => navigate('/consumer')} className="flex items-center gap-2 text-gray-500 hover:text-orange-500 mb-4 text-sm">
        <ArrowLeft size={16} /> Back to Restaurants
      </button>

      <div className="flex items-center justify-between mb-6">
        <h2 className="text-2xl font-bold text-gray-800">{menu?.restaurant?.name || 'Menu'}</h2>
        {cartCount > 0 && (
          <button onClick={() => navigate(`/consumer/cart/${restaurantId}`)}
            className="flex items-center gap-2 bg-orange-500 text-white px-4 py-2 rounded-lg text-sm font-medium hover:bg-orange-600 transition">
            <ShoppingCart size={16} /> {cartCount} items · ₹{cartTotal.toFixed(2)}
          </button>
        )}
      </div>

      {!menu?.menuItems?.length ? (
        <div className="text-center py-16 text-gray-400">
          <Package size={48} className="mx-auto mb-3 opacity-40" />
          <p>No menu items available</p>
        </div>
      ) : (
        <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
          {menu.menuItems.map(item => (
            <div key={item.id} className="bg-white rounded-xl border border-gray-100 shadow-sm p-4 flex justify-between items-start">
              <div className="flex-1">
                <h3 className="font-semibold text-gray-800">{item.name}</h3>
                <span className="text-xs text-gray-500 bg-gray-100 px-2 py-0.5 rounded-full mt-1 inline-block">{item.foodCategory}</span>
                <p className="text-orange-600 font-bold mt-2">₹{item.price?.toFixed(2)}</p>
              </div>
              <button
                onClick={() => addToCart(item.id)}
                disabled={addingItem === item.id}
                className="ml-3 bg-orange-500 hover:bg-orange-600 text-white p-2 rounded-lg transition disabled:opacity-60"
              >
                <Plus size={18} />
              </button>
            </div>
          ))}
        </div>
      )}
    </div>
  )
}
