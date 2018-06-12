import * as React from 'react'
import * as $ from 'classnames'

import styles from './Button.less'


export const Button = ({
    className,
    classes,
    disabled,
    primary,
    danger,
    icon,
    children,
    label,
    onClick,
}: IProps) => (
    <button
        className={$(
            styles.root,
            className,
            disabled && styles.disabled,
            primary && styles.primary,
            danger && styles.danger,
            disabled && classes && classes.disabled,
        )}
        disabled={disabled}
        onClick={disabled ? undefined : onClick}
    >
        {icon && (
            <span className={$(styles.icon, classes && classes.icon)}>{icon}</span>
        )}
        {label && (
            <span className={$(styles.label, classes && classes.label)}>{label}</span>
        )}
        {children}
    </button>
)


interface IProps {
    className?: string
    children?: any

    classes?: IClasses
    danger?: boolean
    disabled?: boolean
    icon?: any
    label?: string
    primary?: boolean

    onClick?(): void
}


interface IClasses {
    disabled?: string
    icon?: string
    label?: string
}
