<template>
    <div class="full-width">
        <div v-if="headers && headers.length > 0" class="grid-header">
            <div class="grid-column grid-column-first">选择</div>
            <div v-for="header in headers" v-bind:key="header" class="grid-column">{{header}}</div>
        </div>
        <div class="grid-content">
            <div v-for="record in columns" v-bind:key="record.name" class="grid-row">
                <div class="grid-column grid-column-first">
                    <input type="checkbox" @change="onChange(record, $event.target.checked)">
                </div>
                <div v-for="c in record" v-bind:key="c" class="grid-column">{{c}}</div>
            </div>
        </div>
    </div>
</template>

<script>
import $ from "../utils"
import Services from '../services/Services'
import MessageBox from '../services/MessageBox';

const MAX_DISPLAYED = 100;
const STEP = 1;
const LEFT = -1;
const RIGHT = 1;
const SPEED = 250;

export default {
    props: {
        headers: {
            type: Array
        },
        columns: {
            type: Array,
            required: true
        }
    },
    data(){
        return {
            selected: []
        };
    },
    mounted(){
        
    },
    methods: {
        onChange(record, selected){
            if(selected){
                this.selected.push(record);
                this.$emit('selected', record);
            }else{
                let idx = this.selected.indexOf(record);
                idx >= 0 && this.selected.splice(idx, 1);
            }
        },
        getSelected(){
            return this.selected.map(r => r);
            // return this.selected;
        }
    }
}
</script>