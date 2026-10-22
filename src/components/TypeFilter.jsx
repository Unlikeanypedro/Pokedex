function TypeFilter({ types, value, onChange }) {
    return (
        <select value={value} onChange={(e) => onChange(e.target.value)}>
            <option value="all">Todos os tipos</option>
            {types.map((t) => (
                <option key={t.id} value={t.name}>
                    {t.name}
                </option>
            ))}
        </select>
    );
}
export default TypeFilter;
