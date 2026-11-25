export default function Header({ view, onChangeView }) {
  return (
    <header className="header">
      <div className="brand">
        <div className="brand-mark">P</div>
        <div>
          <h1>Pokédex</h1>
          <span>Catálogo interativo de Pokémon</span>
        </div>
      </div>

      <nav className="view-toggle">
        <button
          className={view === 'pokemon' ? 'active' : ''}
          onClick={() => onChangeView('pokemon')}
        >
          Pokémon
        </button>
        <button
          className={view === 'trainers' ? 'active' : ''}
          onClick={() => onChangeView('trainers')}
        >
          Treinadores
        </button>
      </nav>
    </header>
  )
}
