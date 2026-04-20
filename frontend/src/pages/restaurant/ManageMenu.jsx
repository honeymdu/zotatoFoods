import { useEffect, useState } from 'react'
import { useParams, useNavigate } from 'react-router-dom'
import api from '../../api/axios'
import toast from 'react-hot-toast'
import { ArrowLeft, Plus, UtensilsCrossed, Trash2 } from 'lucide-react'

const CATEGORIES = ['VEG', 'NON_VEG', 'VEGAN', 'BEVERAGE', 'DESSERT']

const categoryColors = {
  VEG: 'bg-green-100 text-green-700',
  NON_VEG: 'bg-red-100 text-red-700',
  VEGAN: 'bg-emerald-100 text-emerald-700',
  BEVERAGE: 'bg-blue-100 text-blue-700',
  DESSERT: 'bg-pink-100 text-pink-700',
}

export default function ManageMenu() {
  const { restaurantId } = useParams()
  const navigate = useNavigate()
  const [menu, setMenu] = useState(null)
  const [loading, setLoading] = useState(true)
  const [creating, setCreating] = useState(false)
  const [showAdd, setShowAdd] = useState(false)
  const [adding, setAdding] = useState(false)
  const [item, setItem] = useState({ name: '', price: '', foodCategory: 'VEG' })

  const loadMenu = async () => {
    try {
      const { data } = await api.get(`/restaurant-partner/view-menu/${restaurantId}`)
      setMenu(data)
    } catch {
      setMenu(null)
    } finally {
      setLoading(false)
    }
  }

  useEffect(() => { loadMenu() }, [restaurantId])

  const createMenu = async () => {
    setCreating(true)
    try {
      const { data } = await api.post(`/restaurant-partner/create-restaurant-menu/${restaurantId}`, {})
      setMenu(data)
      toast.success('Menu created!')
    } catch (err) {
      toast.error(err.response?.data?.error?.message || 'Failed to create menu')
    } finally {
      setCreating(false)
    }
  }

  const addItem = async (e) => {
    e.preventDefault()
    if (!item.name.trim() || !item.price) { toast.error('Fill all fields'); return }
    setAdding(true)
    try {
      const { data } = await api.post(`/restaurant-partner/add-my-restaurant-menu-item/${restaurantId}`, {
        ...item, price: parseFloat(item.price)
      })
      setMenu(data)
      setShowAdd(false)
      setItem({ name: '', price: '', foodCategory: 'VEG' })
      toast.success('Item added!')
    } catch (err) {
      toast.error(err.response?.data?.error?.message || 'Failed to add item')
    } finally {
      setAdding(false)
    }
  }

  if (loading) return (
    <div className="flex items-center justify-center h-64">
      <div className="animate-spin rounded-full h-10 w-10 border-4 border-orange-500 border-t-transparent" />
    </div>
  )

  const items = menu?.menuItems || []

  return (
    <div className="max-w-4xl mx-auto px-4 py-6">
      <button onClick={() => navigate('/restaurant')} className="flex items-center gap-2 text-gray-500 hover:text-orange-500 mb-5 text-sm">
        <ArrowLeft size={16} /> Back to Dashboard
      </button>

      <div className="flex items-center justify-between mb-6">
        <div>
          <h2 className="text-2xl font-bold text-gray-800">Manage Menu</h2>
          <p className="text-sm text-gray-500 mt-0.5">Restaurant #{restaurantId}</p>
        </div>
        {menu && (
          <button onClick={() => setShowAdd(true)}
            className="flex items-center gap-2 bg-orange-500 hover:bg-orange-600 text-white px-4 py-2 rounded-lg text-sm font-medium transition">
            <Plus size={16} /> Add Item
          </button>
        )}
      </div>

      {!menu ? (
        <div className="text-center py-20 bg-white rounded-2xl border border-gray-100 shadow-sm">
          <div className="bg-orange-100 text-orange-500 w-16 h-16 rounded-full flex items-center justify-center mx-auto mb-4">
            <UtensilsCrossed size={32} />
          </div>
          <h3 className="text-lg font-semibold text-gray-800 mb-2">No menu yet</h3>
          <p className="text-gray-500 text-sm mb-6">Create a menu to start adding food items</p>
          <button onClick={createMenu} disabled={creating}
            className="bg-orange-500 hover:bg-orange-600 text-white px-6 py-2.5 rounded-lg font-medium transition disabled:opacity-60">
            {creating ? 'Creating...' : 'Create Menu'}
          </button>
        </div>
      ) : items.length === 0 ? (
        <div className="text-center py-16 bg-white rounded-2xl border border-gray-100 shadow-sm">
          <p className="text-gray-400 mb-4">Menu created! Add your first item.</p>
          <button onClick={() => setShowAdd(true)}
            className="flex items-center gap-2 bg-orange-500 text-white px-5 py-2.5 rounded-lg font-medium mx-auto hover:bg-orange-600 transition">
            <Plus size={16} /> Add First Item
          </button>
        </div>
      ) : (
        <>
          <div className="mb-3 text-sm text-gray-500">{items.length} item{items.length !== 1 ? 's' : ''}</div>
          <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4">
            {items.map(it => (
              <div key={it.id} className="bg-white rounded-xl border border-gray-100 shadow-sm p-4 hover:shadow-md transition">
                <div className="flex items-start justify-between mb-2">
                  <h3 className="font-semibold text-gray-800 text-base leading-tight">{it.name}</h3>
                </div>
                <span className={`text-xs px-2 py-0.5 rounded-full font-medium ${categoryColors[it.foodCategory] || 'bg-gray-100 text-gray-600'}`}>
                  {it.foodCategory?.replace('_', ' ')}
                </span>
                <p className="text-orange-600 font-bold text-lg mt-3">₹{it.price?.toFixed(2)}</p>
              </div>
            ))}
          </div>
        </>
      )}

      {showAdd && (
        <div className="fixed inset-0 bg-black/40 flex items-center justify-center z-50 px-4">
          <div className="bg-white rounded-2xl shadow-xl p-6 w-full max-w-md">
            <h3 className="text-lg font-bold text-gray-800 mb-4">Add Menu Item</h3>
            <form onSubmit={addItem} className="space-y-3">
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">Item Name</label>
                <input
                  className="w-full border border-gray-300 rounded-lg px-4 py-2.5 focus:outline-none focus:ring-2 focus:ring-orange-400"
                  placeholder="e.g. Paneer Butter Masala"
                  required value={item.name}
                  onChange={e => setItem(i => ({ ...i, name: e.target.value }))}
                />
              </div>
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">Price (₹)</label>
                <input
                  type="number" step="0.01" min="0"
                  className="w-full border border-gray-300 rounded-lg px-4 py-2.5 focus:outline-none focus:ring-2 focus:ring-orange-400"
                  placeholder="0.00" required value={item.price}
                  onChange={e => setItem(i => ({ ...i, price: e.target.value }))}
                />
              </div>
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">Category</label>
                <div className="grid grid-cols-3 gap-2">
                  {CATEGORIES.map(c => (
                    <button key={c} type="button" onClick={() => setItem(i => ({ ...i, foodCategory: c }))}
                      className={`py-2 rounded-lg text-xs font-medium border transition ${item.foodCategory === c ? 'bg-orange-500 text-white border-orange-500' : 'border-gray-300 text-gray-600 hover:border-orange-300'}`}>
                      {c.replace('_', ' ')}
                    </button>
                  ))}
                </div>
              </div>
              <div className="flex gap-3 pt-2">
                <button type="button" onClick={() => { setShowAdd(false); setItem({ name: '', price: '', foodCategory: 'VEG' }) }}
                  className="flex-1 border border-gray-300 py-2.5 rounded-lg text-gray-600 hover:bg-gray-50 transition">
                  Cancel
                </button>
                <button type="submit" disabled={adding}
                  className="flex-1 bg-orange-500 text-white py-2.5 rounded-lg font-medium hover:bg-orange-600 transition disabled:opacity-60">
                  {adding ? 'Adding...' : 'Add Item'}
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  )
}
