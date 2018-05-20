import * as React from 'react'
import * as $ from 'classnames'

import styles from './TextField.less'


const DEFAULT_TYPE = 'text'
const DEFAULT_LINES = 3


export const TextField = ({
    className,
    disabled,
    lines = DEFAULT_LINES,
    multiline,
    maxLength,
    placeholder,
    type = DEFAULT_TYPE,
    value,
    onChange,
}: IProps) => (
    multiline ? (
        <textarea
            disabled={disabled}
            className={$(
                styles.root,
                className,
            )}
            maxLength={maxLength}
            placeholder={placeholder}
            value={value}
            rows={lines}
            onChange={disabled ? undefined : e => onChange && onChange(e.target.value)}
        />
    ) : (
        <input
            disabled={disabled}
            className={$(
                styles.root,
                className,
            )}
            maxLength={maxLength}
            type={type}
            placeholder={placeholder}
            value={value}
            onChange={disabled ? undefined : e => onChange && onChange(e.target.value)}
        />
    )
)


interface IProps {
    className?: string
    tabIndex?: number

    disabled?: boolean
    lines?: number
    maxLength?: number
    multiline?: boolean
    placeholder?: string
    type?: 'email' | 'tel' | 'text'
    value: string

    onChange?(value: string): void
}
