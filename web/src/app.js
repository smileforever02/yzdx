import Vue from 'vue'///dist/vue.js'
import Vuex, {mapGetters} from 'vuex'
import router from './router/router'
import Services from './services/Services'
import MessageBox from './services/MessageBox'
import '../assets/app.styl'

// new Vue({
//     el: '#app',
//     render: h => h(App)
// })
Vue.use(Vuex)

const globalData = {
  count: 0
}

const store = new Vuex.Store({
    state: {
        count: 0,
        chartDataQuery: {}
    },
    getters: {
      countMul100(state){
        return state.count * 100
      }
    },
    mutations: {
        increment(state){
            state.count++
        },
        updateChartQuery(state, query){
          state.chartDataQuery = query;
        }
    },
    actions: {
        increment(context){
            context.commit('increment')
        }
    }
})
let idx = 0;
const level = ['success', 'info', 'warn', 'error']
const app = new Vue({
    el: '#app',
    router,
    store,
    data: {
      logonUser: null,
      storex: 'Hello world'
    },
    // data: globalData,
    beforeCreate(){
      console.log('app => beforcreate')
    },
    created(){
      console.log('app => created')
    },
    mounted(el){
      console.log('app => mounted:')
      console.log(this.$el)
    },
    methods: {
      changeStore(){
        MessageBox[level[idx%4]]('some thing happend' + (idx++))
        // this.$store.commit('increment')
        // console.log(this.$store.state)
        // this.$store.state.count++
        // this.count++
      },
      showme(){
        this.$router.push('/user/' + this.logonUser)
      },
      logout(){
        Services.logout().done(() => {
          this.logonUser = null
          this.$router.push('/login')
        })
      }
    }
  });//.$mount('#app')