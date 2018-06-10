// Styles
import './styles/globals.less'


// Polyfills
import 'core-js/es6'


// Bootstrapping
import Vue from 'vue'

import router from './router'
import store from './store'

import Application from './components/Application.vue'


Vue.config.productionTip = false


window['___vm___'] = new Vue({  // tslint:disable-line
    router,
    store,
    el: '#Application',
    render: (h: any) => h(Application),
})
