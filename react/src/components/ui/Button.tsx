import * as React from 'react'
import * as $ from 'classnames'

import styles from './Button.less'


export const Button = ({
    className,
    classes,
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
            primary && styles.primary,
            danger && styles.danger,
        )}
        onClick={onClick}
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

    primary?: boolean
    danger?: boolean
    icon?: any
    label?: string
    classes?: IClasses

    onClick?(): void
}


interface IClasses {
    icon?: string
    label?: string
}
