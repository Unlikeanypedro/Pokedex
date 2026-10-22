export default function SearchBar({ value, onChange }) {
  return (
    <label className="search">
      <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
        <circle cx="11" cy="11" r="7" />
        <path d="m20 20-3.5-3.5" />
      </svg>
      <input
        type="search"
        placeholder="Pesquisar Pokémon por nome ou ID..."
        value={value}
        onChange={(event) => onChange(event.target.value)}
      />
    </label>
  )
}
