import axios from 'axios'

const api = axios.create({ baseURL: '/api' })

api.interceptors.request.use((config) => {
  const token = localStorage.getItem('accessToken')
  if (token) config.headers.Authorization = `Bearer ${token}`
  return config
})

api.interceptors.response.use(
  (res) => {
    // Unwrap backend's { timestamp, data, error } envelope
    if (res.data && 'data' in res.data && 'error' in res.data) {
      res.data = res.data.data
    }
    return res
  },
  async (err) => {
    if (err.response?.status === 401) {
      try {
        const { data } = await axios.post('/api/auth/refresh', {}, { withCredentials: true })
        localStorage.setItem('accessToken', data.accessToken)
        err.config.headers.Authorization = `Bearer ${data.accessToken}`
        return api(err.config)
      } catch {
        localStorage.clear()
        window.location.href = '/login'
      }
    }
    return Promise.reject(err)
  }
)

export default api
