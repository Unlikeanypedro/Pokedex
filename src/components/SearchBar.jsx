function searchBar({ value, onChange }) {
    return (
        <input
            type="text"
            placeholder="Pesquisar por nome ou ID..."
            value={value}
            onChange={(e) => onChange(e.target.value)}
        />
    );
}

export default SearchBar;
