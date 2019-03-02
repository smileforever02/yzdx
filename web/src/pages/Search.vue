<template>
    <div class="full-width center-content">
        <form class="full-width form-inline" style="position: absolute;top: 39px;left: 0px;padding: 0 1em 0;background-color: white;height: 45px;line-height: 45px;z-index: 10;" v-on:submit.prevent="submit">
            <div class="form-group">
                <label for="userName">姓名</label>
                <input class="form-control" id="userName" placeholder="姓名" v-model.lazy.trim="userName">
            </div>
            <div class="form-group">
                <label for="age">年龄</label>
                <input class="form-control" id="fromAge" placeholder="开始年龄" type="number" max="120" min="0" v-model.lazy.trim="fromAge">
                <label>--</label>
                <input class="form-control" id="toAge" placeholder="结束年龄" type="number" max="120" min="0" v-model.lazy.trim="toAge">
            </div>
            <div class="form-group">
                <label for="gender">性别</label>
                <select class="form-control" id="gender" v-model.lazy.trim="gender">
                    <option value="ALL" selected>全部</option>
                    <option value="M">男性</option>
                    <option value="F">女性</option>
                </select>
            </div>
            <button type="submit" class="btn btn-primary">搜索</button>
            <button type="reset" class="btn">重置</button>
            <button class="btn" type="button" @click="checkAvg">查看平均</button>
        </form>
        <ul class="page-content item-list" style="padding: 5em 1em 0em 1em;">
            <li v-for="item in items" v-bind:key="item.userId" v-bind:data-userid="item.userId">
                <input type="checkbox" :value="item.checked" v-model="item.checked"><span v-on:click="display(item)">{{item.nickName}}</span>
            </li>
        </ul>
        <div v-if="this.items != null && this.items.filter(u => u.checked).length > 0" style="position: fixed; left: 0px; top: 100px;">
            <button>看平均</button>
        </div>
    </div>
</template>

<script>
import $ from "../utils"
import Services from '../services/Services'
import MessageBox from '../../../../displayer/src/services/MessageBox';
export default {
    data(){
        return {
            userName: null,
            fromAge: null,
            toAge: null,
            gender: 'ALL',
            items: []
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
            console.log(this.items);
            Services.search({userName: this.userName, fromAge: this.fromAge, toAge: this.toAge, gender: this.gender})
                .done((data) => {
                    console.log(data)
                    this.items = data;
                    this.items.forEach(i => i.checked = false);
                    // this.$router.push('/user/' + this.userId);
                }).fail(() => console.log(arguments))
        },
        display(user){
            console.log('check user');
            console.log(user);
            this.$store.commit('updateChartQuery', user);
            this.$router.push('/chart');
        },
        checkAvg(){
            let selects = (this.items || []).filter(u => u.checked);
            if(selects.length === 0){
                MessageBox.info('请至少选择一行记录.');
            }else{
                console.log(selects);
            }
        }
    }
}
</script>
