import Vue from 'vue'///dist/vue.js'
import VueRouter from 'vue-router'
import Vuex from 'vuex'
// import App from './App.vue'
import Home from '../pages/Home.vue'
import Upload from '../pages/Upload.vue'
import Search from '../pages/Search.vue'
import Chart from '../pages/Chart.vue'

Vue.use(VueRouter);
Vue.use(Vuex)
const routes = [
    { path: '/', component: Home },
    { path: '/upload', component: Upload },
    { path: '/search', component: Search },
    { path: '/chart', component: Chart }
];
export default new VueRouter({
    // mode: 'history',
    mode: 'hash',
    base: __dirname,
    routes // (缩写) 相当于 routes: routes
})