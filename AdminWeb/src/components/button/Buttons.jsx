import React from 'react'
import './button.scss'
function Button({text, type}) {
  return (
    <div style={{display: 'flex', justifyContent: 'center'}}>
      <div className={`wrap__button ${type}`}>{text}</div>
    </div>
  )
}

export default Button