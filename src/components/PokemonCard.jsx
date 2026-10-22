function PokemonCard({ pokemon, onSelect }) {
    const { id, name, types, stats } = pokemon;

    return (
        <article className="card" onClick={() => onSelect(pokemon)}>
            <span className="card-id">#{String(id).padStart(3, "0")}</span>
            <h3>{name}</h3>
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
            <ul className="mini-stats">
                <li>HP {stats.hp}</li>
                <li>ATK {stats.attack}</li>
                <li>DEF {stats.defense}</li>
                <li>SP.ATK {stats.specialAttack}</li>
                <li>SP.DEF {stats.specialDefense}</li>
                <li>SPD {stats.speed}</li>
            </ul>
        </article>
    );
}
export default PokemonCard;
