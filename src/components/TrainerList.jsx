import TrainerCard from './TrainerCard.jsx'

export default function TrainerList({ trainers, pokemonById, onSelectPokemon }) {
  return (
    <section className="trainer-grid">
      {trainers.map((trainer) => (
        <TrainerCard
          key={trainer.id}
          trainer={trainer}
          pokemonById={pokemonById}
          onSelectPokemon={onSelectPokemon}
        />
      ))}
    </section>
  )
}
