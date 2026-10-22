function TrainerList({ trainers, pokemons }) {
    const byId = Object.fromEntries(pokemons.map((p) => [p.id, p]));

    return (
        <div className="trainer-list">
            {trainers.map((t) => (
                <article key={t.id} className="card">
                    <h3>{t.name}</h3>
                    <p> {t.badges} insígnias</p>
                    <ul>
                        {t.pokemonIds.map((pid, i) => (
                            <li key={`${pid}-${i}`}>{byId[pid]?.name}</li>
                        ))}
                    </ul>
                </article>
            ))}
        </div>
    );
}
export default TrainerList;
