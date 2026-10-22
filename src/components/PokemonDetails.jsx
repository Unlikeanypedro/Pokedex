const LABELS = {
    hp: "HP",
    attack: "Attack",
    defense: "Defense",
    specialAttack: "Special Attack",
    specialDefense: "Special Defense",
    speed: "Speed",
};

function PokemonDetails({ pokemon, onClose }) {
    const { id, name, types, stats } = pokemon;

    return (
        <div className="overlay" onClick={onClose}>
            <div className="modal" onClick={(e) => e.stopPropagation()}>
                <button className="close" onClick={onClose}>
                    ✕
                </button>
                <h2>
                    #{String(id).padStart(3, "0")} {name}
                </h2>

                <div className="types">
                    {types.length === 0 ? (
                        <span className="type typeless">Typeless</span>
                    ) : (
                        types.map((t) => (
                            <span
                                key={t.id}
                                className={`type ${t.name.toLowerCase()}`}
                            >
                                {t.name}
                            </span>
                        ))
                    )}
                </div>

                {Object.entries(stats).map(([key, value]) => (
                    <div key={key} className="stat">
                        <span className="stat-label">{LABELS[key]}</span>
                        <span className="stat-value">{value}</span>
                        <div className="bar">
                            <div
                                className="bar-fill"
                                style={{ width: `${(value / 255) * 100}%` }}
                            />
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
}
export default PokemonDetails;
