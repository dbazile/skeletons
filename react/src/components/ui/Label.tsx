import * as React from 'react'
import * as $ from 'classnames'

import styles from './Label.less'


export const Label = ({
    children,
    classes,
    className,
    disabled,
    size,
    text,
    tooltip = text,
}: IProps) => (
    <label
        className={$(
            styles.root,
            className,
            size === 'large' && styles.isLarge,
            size === 'large' && classes && classes.isLarge,
            size === 'small' && styles.isSmall,
            size === 'small' && classes && classes.isSmall,
            disabled && styles.isDisabled,
            disabled && classes && classes.isDisabled,
        )}
        title={tooltip}
    >
        <span className={$(styles.text, classes && classes.text)}>
            {text}
        </span>
        {children}
    </label>
)


interface IProps {
    className?: string
    children?: any

    classes?: IClasses
    disabled?: boolean
    size?: 'large' | 'small'
    text: string
    tooltip?: string
}


interface IClasses {
    text?: string
    isDisabled?: string
    isLarge?: string
    isSmall?: string
}
