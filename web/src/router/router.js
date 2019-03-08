import Vue from 'vue'///dist/vue.js'
import VueRouter from 'vue-router'
import Vuex from 'vuex'
// import App from './App.vue'
import Chart from '../components/Chart.vue'
import Grid from '../components/Grid.vue'

import Home from '../pages/Home.vue'
import Upload from '../pages/Upload.vue'
import Search from '../pages/Search.vue'
import Test from '../pages/Test.vue'

Vue.use(VueRouter);
Vue.use(Vuex);
Vue.component('chart', Chart);
Vue.component('grid', Grid);

const routes = [
    { path: '/', component: Home },
    { path: '/upload', component: Upload },
    { path: '/search', component: Search },
    { path: '/chart', component: Chart },
    { path: '/test', component: Test },
];
export default new VueRouter({
    // mode: 'history',
    mode: 'hash',
    base: __dirname,
    routes // (缩写) 相当于 routes: routes
})