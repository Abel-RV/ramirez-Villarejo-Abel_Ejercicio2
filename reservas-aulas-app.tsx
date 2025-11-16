import React, { useState, useEffect } from 'react';
import { Calendar, Clock, Users, DoorOpen, LogIn, LogOut, Plus, Trash2, Edit, X } from 'lucide-react';

const API_URL = 'http://localhost:8080';

export default function ReservasAulasApp() {
  const [token, setToken] = useState(localStorage.getItem('token') || '');
  const [usuario, setUsuario] = useState(null);
  const [vista, setVista] = useState('login');
  const [aulas, setAulas] = useState([]);
  const [reservas, setReservas] = useState([]);
  const [horarios, setHorarios] = useState([]);
  const [mensaje, setMensaje] = useState({ tipo: '', texto: '' });

  // Forms
  const [loginForm, setLoginForm] = useState({ email: '', password: '' });
  const [registerForm, setRegisterForm] = useState({ email: '', password: '', nombre: '', apellidos: '' });
  const [aulaForm, setAulaForm] = useState({ nombre: '', capacidad: '', esAulaOrdenadores: false, numeroOrdenadores: 0 });
  const [reservaForm, setReservaForm] = useState({ fecha: '', motivo: '', numeroAsistentes: '', aulaId: '', usuarioId: '', horarioId: [] });

  useEffect(() => {
    if (token) {
      cargarPerfil();
      cargarDatos();
    }
  }, [token]);

  const mostrarMensaje = (tipo, texto) => {
    setMensaje({ tipo, texto });
    setTimeout(() => setMensaje({ tipo: '', texto: '' }), 3000);
  };

  const headers = {
    'Authorization': `Bearer ${token}`,
    'Content-Type': 'application/json'
  };

  const cargarPerfil = async () => {
    try {
      const res = await fetch(`${API_URL}/auth/perfil`, { headers });
      if (res.ok) {
        const data = await res.json();
        setUsuario(data);
      }
    } catch (error) {
      console.error('Error al cargar perfil:', error);
    }
  };

  const cargarDatos = async () => {
    try {
      const [aulasRes, reservasRes, horariosRes] = await Promise.all([
        fetch(`${API_URL}/aula`, { headers }),
        fetch(`${API_URL}/reserva`, { headers }),
        fetch(`${API_URL}/tramo-horario`, { headers })
      ]);

      if (aulasRes.ok) setAulas(await aulasRes.json());
      if (reservasRes.ok) setReservas(await reservasRes.json());
      if (horariosRes.ok) setHorarios(await horariosRes.json());
    } catch (error) {
      console.error('Error al cargar datos:', error);
    }
  };

  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      const res = await fetch(`${API_URL}/auth/login`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(loginForm)
      });

      const data = await res.json();
      if (res.ok && data.token) {
        setToken(data.token);
        localStorage.setItem('token', data.token);
        setVista('aulas');
        mostrarMensaje('success', '¡Inicio de sesión exitoso!');
      } else {
        mostrarMensaje('error', data.error || 'Error al iniciar sesión');
      }
    } catch (error) {
      mostrarMensaje('error', 'Error de conexión');
    }
  };

  const handleRegister = async (e) => {
    e.preventDefault();
    try {
      const res = await fetch(`${API_URL}/auth/register`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(registerForm)
      });

      const data = await res.json();
      if (res.ok) {
        mostrarMensaje('success', 'Usuario registrado correctamente');
        setVista('login');
      } else {
        mostrarMensaje('error', data.error || 'Error al registrar');
      }
    } catch (error) {
      mostrarMensaje('error', 'Error de conexión');
    }
  };

  const handleLogout = () => {
    setToken('');
    setUsuario(null);
    localStorage.removeItem('token');
    setVista('login');
    mostrarMensaje('success', 'Sesión cerrada');
  };

  const crearAula = async (e) => {
    e.preventDefault();
    try {
      const res = await fetch(`${API_URL}/aula`, {
        method: 'POST',
        headers,
        body: JSON.stringify({
          ...aulaForm,
          capacidad: parseInt(aulaForm.capacidad),
          numeroOrdenadores: parseInt(aulaForm.numeroOrdenadores)
        })
      });

      if (res.ok) {
        mostrarMensaje('success', 'Aula creada correctamente');
        cargarDatos();
        setAulaForm({ nombre: '', capacidad: '', esAulaOrdenadores: false, numeroOrdenadores: 0 });
      } else {
        mostrarMensaje('error', 'Error al crear aula');
      }
    } catch (error) {
      mostrarMensaje('error', 'Error de conexión');
    }
  };

  const eliminarAula = async (id) => {
    if (!confirm('¿Estás seguro de eliminar esta aula?')) return;
    
    try {
      const res = await fetch(`${API_URL}/aula/${id}`, {
        method: 'DELETE',
        headers
      });

      if (res.ok) {
        mostrarMensaje('success', 'Aula eliminada');
        cargarDatos();
      } else {
        mostrarMensaje('error', 'Error al eliminar aula');
      }
    } catch (error) {
      mostrarMensaje('error', 'Error de conexión');
    }
  };

  const crearReserva = async (e) => {
    e.preventDefault();
    try {
      const res = await fetch(`${API_URL}/reserva`, {
        method: 'POST',
        headers,
        body: JSON.stringify({
          ...reservaForm,
          numeroAsistentes: parseInt(reservaForm.numeroAsistentes),
          aulaId: parseInt(reservaForm.aulaId),
          usuarioId: usuario.id,
          horarioId: reservaForm.horarioId.map(id => parseInt(id))
        })
      });

      if (res.ok) {
        mostrarMensaje('success', 'Reserva creada correctamente');
        cargarDatos();
        setReservaForm({ fecha: '', motivo: '', numeroAsistentes: '', aulaId: '', usuarioId: '', horarioId: [] });
      } else {
        const error = await res.json();
        mostrarMensaje('error', error.error || 'Error al crear reserva');
      }
    } catch (error) {
      mostrarMensaje('error', 'Error de conexión');
    }
  };

  const eliminarReserva = async (id) => {
    if (!confirm('¿Estás seguro de eliminar esta reserva?')) return;
    
    try {
      const res = await fetch(`${API_URL}/reserva/${id}`, {
        method: 'DELETE',
        headers
      });

      if (res.ok) {
        mostrarMensaje('success', 'Reserva eliminada');
        cargarDatos();
      } else {
        mostrarMensaje('error', 'Error al eliminar reserva');
      }
    } catch (error) {
      mostrarMensaje('error', 'Error de conexión');
    }
  };

  if (!token) {
    return (
      <div className="min-h-screen bg-gradient-to-br from-blue-50 to-indigo-100 flex items-center justify-center p-4">
        <div className="bg-white rounded-2xl shadow-2xl p-8 w-full max-w-md">
          <div className="text-center mb-8">
            <DoorOpen className="w-16 h-16 mx-auto text-indigo-600 mb-4" />
            <h1 className="text-3xl font-bold text-gray-800">Sistema de Reservas</h1>
            <p className="text-gray-600 mt-2">Gestión de Aulas</p>
          </div>

          {mensaje.texto && (
            <div className={`mb-4 p-3 rounded-lg ${mensaje.tipo === 'success' ? 'bg-green-100 text-green-700' : 'bg-red-100 text-red-700'}`}>
              {mensaje.texto}
            </div>
          )}

          <div className="flex gap-2 mb-6">
            <button
              onClick={() => setVista('login')}
              className={`flex-1 py-2 rounded-lg font-medium transition ${vista === 'login' ? 'bg-indigo-600 text-white' : 'bg-gray-100 text-gray-600'}`}
            >
              Iniciar Sesión
            </button>
            <button
              onClick={() => setVista('register')}
              className={`flex-1 py-2 rounded-lg font-medium transition ${vista === 'register' ? 'bg-indigo-600 text-white' : 'bg-gray-100 text-gray-600'}`}
            >
              Registrarse
            </button>
          </div>

          {vista === 'login' ? (
            <form onSubmit={handleLogin} className="space-y-4">
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">Email</label>
                <input
                  type="email"
                  required
                  value={loginForm.email}
                  onChange={(e) => setLoginForm({ ...loginForm, email: e.target.value })}
                  className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-transparent"
                  placeholder="tu@email.com"
                />
              </div>
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">Contraseña</label>
                <input
                  type="password"
                  required
                  value={loginForm.password}
                  onChange={(e) => setLoginForm({ ...loginForm, password: e.target.value })}
                  className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-transparent"
                  placeholder="••••••"
                />
              </div>
              <button
                type="submit"
                className="w-full bg-indigo-600 text-white py-3 rounded-lg font-medium hover:bg-indigo-700 transition flex items-center justify-center gap-2"
              >
                <LogIn className="w-5 h-5" />
                Iniciar Sesión
              </button>
            </form>
          ) : (
            <form onSubmit={handleRegister} className="space-y-4">
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">Nombre</label>
                <input
                  type="text"
                  required
                  value={registerForm.nombre}
                  onChange={(e) => setRegisterForm({ ...registerForm, nombre: e.target.value })}
                  className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-transparent"
                />
              </div>
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">Apellidos</label>
                <input
                  type="text"
                  value={registerForm.apellidos}
                  onChange={(e) => setRegisterForm({ ...registerForm, apellidos: e.target.value })}
                  className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-transparent"
                />
              </div>
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">Email</label>
                <input
                  type="email"
                  required
                  value={registerForm.email}
                  onChange={(e) => setRegisterForm({ ...registerForm, email: e.target.value })}
                  className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-transparent"
                />
              </div>
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">Contraseña</label>
                <input
                  type="password"
                  required
                  value={registerForm.password}
                  onChange={(e) => setRegisterForm({ ...registerForm, password: e.target.value })}
                  className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-transparent"
                />
              </div>
              <button
                type="submit"
                className="w-full bg-indigo-600 text-white py-3 rounded-lg font-medium hover:bg-indigo-700 transition"
              >
                Registrarse
              </button>
            </form>
          )}
        </div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gray-50">
      {/* Header */}
      <header className="bg-white shadow-sm border-b">
        <div className="max-w-7xl mx-auto px-4 py-4 flex items-center justify-between">
          <div className="flex items-center gap-3">
            <DoorOpen className="w-8 h-8 text-indigo-600" />
            <div>
              <h1 className="text-2xl font-bold text-gray-800">Reservas de Aulas</h1>
              <p className="text-sm text-gray-600">
                {usuario?.nombre} - <span className="text-indigo-600">{usuario?.roles}</span>
              </p>
            </div>
          </div>
          <button
            onClick={handleLogout}
            className="flex items-center gap-2 px-4 py-2 bg-red-500 text-white rounded-lg hover:bg-red-600 transition"
          >
            <LogOut className="w-5 h-5" />
            Cerrar Sesión
          </button>
        </div>
      </header>

      {/* Mensajes */}
      {mensaje.texto && (
        <div className="max-w-7xl mx-auto px-4 mt-4">
          <div className={`p-4 rounded-lg ${mensaje.tipo === 'success' ? 'bg-green-100 text-green-700' : 'bg-red-100 text-red-700'}`}>
            {mensaje.texto}
          </div>
        </div>
      )}

      {/* Navigation */}
      <nav className="max-w-7xl mx-auto px-4 mt-6">
        <div className="flex gap-2">
          <button
            onClick={() => setVista('aulas')}
            className={`px-6 py-3 rounded-lg font-medium transition ${vista === 'aulas' ? 'bg-indigo-600 text-white' : 'bg-white text-gray-600 hover:bg-gray-50'}`}
          >
            Aulas
          </button>
          <button
            onClick={() => setVista('reservas')}
            className={`px-6 py-3 rounded-lg font-medium transition ${vista === 'reservas' ? 'bg-indigo-600 text-white' : 'bg-white text-gray-600 hover:bg-gray-50'}`}
          >
            Reservas
          </button>
          <button
            onClick={() => setVista('horarios')}
            className={`px-6 py-3 rounded-lg font-medium transition ${vista === 'horarios' ? 'bg-indigo-600 text-white' : 'bg-white text-gray-600 hover:bg-gray-50'}`}
          >
            Horarios
          </button>
        </div>
      </nav>

      {/* Content */}
      <main className="max-w-7xl mx-auto px-4 py-6">
        {vista === 'aulas' && (
          <div className="grid md:grid-cols-2 gap-6">
            {/* Formulario Crear Aula */}
            {usuario?.roles === 'ROLE_ADMIN' && (
              <div className="bg-white rounded-xl shadow-lg p-6">
                <h2 className="text-xl font-bold text-gray-800 mb-4 flex items-center gap-2">
                  <Plus className="w-6 h-6 text-indigo-600" />
                  Crear Nueva Aula
                </h2>
                <form onSubmit={crearAula} className="space-y-4">
                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-1">Nombre</label>
                    <input
                      type="text"
                      required
                      value={aulaForm.nombre}
                      onChange={(e) => setAulaForm({ ...aulaForm, nombre: e.target.value })}
                      className="w-full px-4 py-2 border rounded-lg focus:ring-2 focus:ring-indigo-500"
                    />
                  </div>
                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-1">Capacidad</label>
                    <input
                      type="number"
                      required
                      value={aulaForm.capacidad}
                      onChange={(e) => setAulaForm({ ...aulaForm, capacidad: e.target.value })}
                      className="w-full px-4 py-2 border rounded-lg focus:ring-2 focus:ring-indigo-500"
                    />
                  </div>
                  <div className="flex items-center gap-2">
                    <input
                      type="checkbox"
                      checked={aulaForm.esAulaOrdenadores}
                      onChange={(e) => setAulaForm({ ...aulaForm, esAulaOrdenadores: e.target.checked })}
                      className="w-4 h-4"
                    />
                    <label className="text-sm font-medium text-gray-700">¿Tiene ordenadores?</label>
                  </div>
                  {aulaForm.esAulaOrdenadores && (
                    <div>
                      <label className="block text-sm font-medium text-gray-700 mb-1">Número de Ordenadores</label>
                      <input
                        type="number"
                        value={aulaForm.numeroOrdenadores}
                        onChange={(e) => setAulaForm({ ...aulaForm, numeroOrdenadores: e.target.value })}
                        className="w-full px-4 py-2 border rounded-lg focus:ring-2 focus:ring-indigo-500"
                      />
                    </div>
                  )}
                  <button
                    type="submit"
                    className="w-full bg-indigo-600 text-white py-2 rounded-lg hover:bg-indigo-700 transition"
                  >
                    Crear Aula
                  </button>
                </form>
              </div>
            )}

            {/* Lista de Aulas */}
            <div className="bg-white rounded-xl shadow-lg p-6">
              <h2 className="text-xl font-bold text-gray-800 mb-4">Aulas Disponibles</h2>
              <div className="space-y-3">
                {aulas.map((aula) => (
                  <div key={aula.id} className="border rounded-lg p-4 hover:shadow-md transition">
                    <div className="flex items-start justify-between">
                      <div>
                        <h3 className="font-bold text-lg text-gray-800">{aula.nombre}</h3>
                        <p className="text-gray-600 flex items-center gap-2 mt-1">
                          <Users className="w-4 h-4" />
                          Capacidad: {aula.capacidad} personas
                        </p>
                        {aula.esAulaOrdenadores && (
                          <p className="text-indigo-600 text-sm mt-1">
                            💻 {aula.numeroOrdenadores} ordenadores
                          </p>
                        )}
                      </div>
                      {usuario?.roles === 'ROLE_ADMIN' && (
                        <button
                          onClick={() => eliminarAula(aula.id)}
                          className="text-red-500 hover:text-red-700"
                        >
                          <Trash2 className="w-5 h-5" />
                        </button>
                      )}
                    </div>
                  </div>
                ))}
              </div>
            </div>
          </div>
        )}

        {vista === 'reservas' && (
          <div className="grid md:grid-cols-2 gap-6">
            {/* Formulario Crear Reserva */}
            <div className="bg-white rounded-xl shadow-lg p-6">
              <h2 className="text-xl font-bold text-gray-800 mb-4 flex items-center gap-2">
                <Calendar className="w-6 h-6 text-indigo-600" />
                Nueva Reserva
              </h2>
              <form onSubmit={crearReserva} className="space-y-4">
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-1">Fecha</label>
                  <input
                    type="date"
                    required
                    value={reservaForm.fecha}
                    onChange={(e) => setReservaForm({ ...reservaForm, fecha: e.target.value })}
                    className="w-full px-4 py-2 border rounded-lg focus:ring-2 focus:ring-indigo-500"
                  />
                </div>
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-1">Motivo</label>
                  <input
                    type="text"
                    required
                    value={reservaForm.motivo}
                    onChange={(e) => setReservaForm({ ...reservaForm, motivo: e.target.value })}
                    className="w-full px-4 py-2 border rounded-lg focus:ring-2 focus:ring-indigo-500"
                  />
                </div>
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-1">Número de Asistentes</label>
                  <input
                    type="number"
                    required
                    value={reservaForm.numeroAsistentes}
                    onChange={(e) => setReservaForm({ ...reservaForm, numeroAsistentes: e.target.value })}
                    className="w-full px-4 py-2 border rounded-lg focus:ring-2 focus:ring-indigo-500"
                  />
                </div>
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-1">Aula</label>
                  <select
                    required
                    value={reservaForm.aulaId}
                    onChange={(e) => setReservaForm({ ...reservaForm, aulaId: e.target.value })}
                    className="w-full px-4 py-2 border rounded-lg focus:ring-2 focus:ring-indigo-500"
                  >
                    <option value="">Seleccionar aula</option>
                    {aulas.map((aula) => (
                      <option key={aula.id} value={aula.id}>
                        {aula.nombre} (Cap: {aula.capacidad})
                      </option>
                    ))}
                  </select>
                </div>
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-1">Horarios</label>
                  <select
                    multiple
                    required
                    value={reservaForm.horarioId}
                    onChange={(e) => setReservaForm({ ...reservaForm, horarioId: Array.from(e.target.selectedOptions, option => option.value) })}
                    className="w-full px-4 py-2 border rounded-lg focus:ring-2 focus:ring-indigo-500"
                    size="4"
                  >
                    {horarios.map((horario) => (
                      <option key={horario.id} value={horario.id}>
                        {horario.diasSemana} - Sesión {horario.sesionDia}
                      </option>
                    ))}
                  </select>
                  <p className="text-xs text-gray-500 mt-1">Mantén Ctrl para seleccionar varios</p>
                </div>
                <button
                  type="submit"
                  className="w-full bg-indigo-600 text-white py-2 rounded-lg hover:bg-indigo-700 transition"
                >
                  Crear Reserva
                </button>
              </form>
            </div>

            {/* Lista de Reservas */}
            <div className="bg-white rounded-xl shadow-lg p-6">
              <h2 className="text-xl font-bold text-gray-800 mb-4">Mis Reservas</h2>
              <div className="space-y-3">
                {reservas.map((reserva) => (
                  <div key={reserva.id} className="border rounded-lg p-4 hover:shadow-md transition">
                    <div className="flex items-start justify-between">
                      <div>
                        <h3 className="font-bold text-gray-800">{reserva.motivo}</h3>
                        <p className="text-sm text-gray-600 mt-1">
                          📅 {reserva.fecha}
                        </p>
                        <p className="text-sm text-gray-600">
                          👥 {reserva.numeroAsistentes} asistentes
                        </p>
                        <p className="text-sm text-indigo-600">
                          🚪 {reserva.aula?.nombre || 'Aula no disponible'}
                        </p>
                      </div>
                      {(usuario?.roles === 'ROLE_ADMIN' || reserva.usuario?.id === usuario?.id) && (
                        <button
                          onClick={() => eliminarReserva(reserva.id)}
                          className="text-red-500 hover:text-red-700"
                        >
                          <Trash2 className="w-5 h-5" />
                        </button>
                      )}
                    </div>
                  </div>
                ))}
              </div>
            </div>
          </div>
        )}

        {vista === 'horarios' && (
          <div className="bg-white rounded-xl shadow-lg p-6">
            <h2 className="text-xl font-bold text-gray-800 mb-4 flex items-center gap-2">
              <Clock className="w-6 h-6 text-indigo-600" />
              Tramos Horarios
            </h2>
            <div className="grid md:grid-cols-3 gap-4">
              {horarios.map((horario) => (
                <div key={horario.id} className="border rounded-lg p-4 hover:shadow-md transition">
                  <h3 className="font-bold text-gray-800">{horario.diasSemana}</h3>
                  <p className="text-sm text-gray-600 mt-2">Sesión {horario.sesionDia}</p>
                  <p className="text-sm text-gray-600">
                    ⏰ {horario.horarioInicio} - {horario.horarioFin}
                  </p>
                  <span className="inline-block mt-2 px-3 py-1 bg-indigo-100 text-indigo-700 rounded-full text-xs">
                    {horario.tramoHorario}
                  </span>
                </div>
              ))}
            </div>
          </div>
        )}
      </main>
    </div>
  );
}