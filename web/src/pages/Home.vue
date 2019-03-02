<template>
    <div class="full-width center-content">
        <h1>欢迎使用本系统</h1>
    </div>
</template>

<script>
import $ from "../utils"
import Services from '../services/Services'
export default {
    data(){
        return {
            userId: null,
            password: null
        }
    },
    mounted(){
        // console.log('mounted')
        // console.log($)
        // console.log($('form'))
        // $('form').validator()
    },
    methods: {
        submit(){
            Services.login({userId: this.userId, password: this.password})
                .done(() => {
                    this.$root.logonUser = this.userId;
                    $('#name').val(this.userId);
                    setTimeout((typeof register === 'function'? register : function(){console.log('no register function')}), 0);
                    this.$router.push('/user/' + this.userId);
                }).fail(() => console.log(arguments))
        }
    }
}
</script>
