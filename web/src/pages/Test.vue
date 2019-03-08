<template>
    <div class="full-width" style="padding: 2em 0 0 0;height: 100%;">
        <grid :headers="headers" :columns="columns" v-on:selected="onSelectedRecord"></grid>
    </div>
</template>

<script>

import $ from "../utils"
import Services from '../services/Services'
import MessageBox from '../services/MessageBox';

export default {
    data(){
        return {
            headers: ['Name', 'Age', 'Score'],
            columns: [{
                Name: 'Tom',
                Age: 18,
                Score: 99.8
            },{
                Name: 'Jimmy',
                Age: 19,
                Score: 56.8
            },{
                Name: 'Kerry',
                Age: 17,
                Score: 79.8
            }]
        };
    },
    mounted(){

    },
    methods: {
        onSelectedRecord(record){
            console.log(record);
        }
    }
}
</script>
