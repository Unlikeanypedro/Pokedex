import { getTypeColor } from '../utils/typeColors.js'

export default function TypeFilter({ types, selectedType, onChange }) {
  const options = ['All', ...types]

  return (
    <div className="type-filter">
      {options.map((type) => (
        <button
          key={type}
          className={selectedType === type ? 'type-button active' : 'type-button'}
          style={type === 'All' ? undefined : { borderColor: getTypeColor(type) }}
          onClick={() => onChange(type)}
        >
          {type === 'All' ? 'Todos' : type}
        </button>
      ))}
    </div>
  )
}
