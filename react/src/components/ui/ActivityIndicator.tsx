import * as React from 'react'
import * as $ from 'classnames'

import styles from './ActivityIndicator.less'


export const MercuryAnimation = ({ className }: IProps) => (
    <svg className={$(styles.mercury, className)} viewBox="0 0 40 10">
        <line className={styles.mercury__track} x1="5" x2="35" y1="5" y2="5" />
        <line className={styles.mercury__stream} x1="5" x2="35" y1="5" y2="5" />
        <circle className={styles.mercury__dropletLeft} cx="5" cy="5" r="4" />
        <circle className={styles.mercury__dropletRight} cx="35" cy="5" r="4" />
    </svg>
)


// tslint:disable:max-line-length
export const DiscAnimation = ({ className }: IProps) => (
    <svg className={$(styles.disc, className)} viewBox="0 0 30 30">
        <g className={styles.disc__group} x="0" y="0" transform="translate(0 0)">
            <path
                className={styles.disc__disc}
                d="M15,29 C7.2680135,29 1,22.7319865 1,15 C1,7.2680135 7.2680135,1 15,1 C22.7319865,1 29,7.2680135 29,15 C29,22.7319865 22.7319865,29 15,29 Z M15,18 C16.6568542,18 18,16.6568542 18,15 C18,13.3431458 16.6568542,12 15,12 C13.3431458,12 12,13.3431458 12,15 C12,16.6568542 13.3431458,18 15,18 Z"
            />
            <path
                className={styles.disc__glare}
                d="M14.0041809,18.3600006 L11.1854401,27.9499969 C12.4145524,28.3165232 13.6864647,28.4997864 15.0011771,28.4997864 C16.3158894,28.4997864 17.5878018,28.3165232 18.8169141,27.9499969 L15.9997711,18.3600006 C15.6527634,18.4551849 15.3195064,18.5027771 15,18.5027771 C14.6804936,18.5027771 14.3485539,18.4551849 14.0041809,18.3600006 Z"
            />
        </g>
    </svg>
)


interface IProps {
    className?: string
}
