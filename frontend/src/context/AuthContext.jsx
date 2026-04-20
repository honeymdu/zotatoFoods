import { createContext, useContext, useState, useEffect } from 'react'
import { jwtDecode } from 'jwt-decode'

const AuthContext = createContext(null)

export function AuthProvider({ children }) {
  const [user, setUser] = useState(null)
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    const token = localStorage.getItem('accessToken')
    if (token) {
      try {
        const decoded = jwtDecode(token)
        if (decoded.exp * 1000 > Date.now()) setUser(decoded)
        else localStorage.removeItem('accessToken')
      } catch {
        localStorage.removeItem('accessToken')
      }
    }
    setLoading(false)
  }, [])

  const login = (token) => {
    localStorage.setItem('accessToken', token)
    setUser(jwtDecode(token))
  }

  const logout = () => {
    localStorage.removeItem('accessToken')
    setUser(null)
  }

  // JWT stores role as "Roles": "[CONSUMER]" (Java Set.toString())
  const parseRole = (decoded) => {
    const raw = decoded?.Roles
    if (!raw) return null
    const match = raw.match(/\[(.+?)\]/)
    return match ? 'ROLE_' + match[1].trim() : null
  }
  const role = user ? parseRole(user) : null

  return (
    <AuthContext.Provider value={{ user, role, login, logout, loading }}>
      {children}
    </AuthContext.Provider>
  )
}

export const useAuth = () => useContext(AuthContext)
