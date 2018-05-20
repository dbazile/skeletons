// Styles
import './styles/globals.less'


// Polyfills
import 'core-js/es6'


// Bootstrapping
import * as React from 'react'
import * as ReactDOM from 'react-dom'
import { Provider } from 'mobx-react'

import Application from './components/Application'

import store from './store'


ReactDOM.render(
    <Provider {...{ store }}>
        <Application/>
    </Provider>,
    document.querySelector('#Application'),
)
