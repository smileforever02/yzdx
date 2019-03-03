<template>
    <div class="full-width center-content">
        <form class="full-width form-inline" style="position: absolute;top: 28px;left: 0px;padding: 0 1em 0;background-color: white;height: 45px;line-height: 45px;z-index: 10;" v-on:submit.prevent="submit">
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
            <div class="form-group">
                <label for="bodyPart">部位</label>
                <select class="form-control" id="bodyPart" v-model.lazy.trim="bodyPart">
                    <option value="ALL" selected>全部</option>
                    <option value="LL">左胳膊</option>
                    <option value="RL">右胳膊</option>
                    <option value="LH">左手指</option>
                    <option value="RH">右手指</option>
                </select>
            </div>
            <div class="form-group">
                <label for="age">时间</label>
                <input class="form-control" id="fromDate" placeholder="2019-01-01 12:00:00" v-model.lazy.trim="fromDate">
                <label>--</label>
                <input class="form-control" id="toDate" placeholder="2019-01-01 12:00:00" v-model.lazy.trim="toDate">
            </div>
            <button type="submit" class="btn btn-primary">搜索</button>
            <button type="reset" class="btn">重置</button>
            <button class="btn" type="button" @click="calculateAvg">统计平均</button>
            <button class="btn" type="button" @click="checkAvg">平均图</button>
        </form>
        <ul class="page-content item-list" style="padding: 6em 1em 0em 1em;">
            <li v-for="item in items" v-bind:key="item.recordId" v-bind:data-recordId="item.recordId">
                <input type="checkbox" :value="item.checked" v-model="item.checked"><span v-on:click="display(item)">{{item.userId + '&nbsp;&nbsp;&nbsp;' + item.testDate + '&nbsp;&nbsp;&nbsp;' + item.bodyPart}}</span>
            </li>
        </ul>
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
            bodyPart: 'ALL',
            fromDate: null,
            toDate: null,
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
            Services.search({name: this.userName, fromAge: this.fromAge, toAge: this.toAge, gender: this.gender, bodyPart: this.bodyPart})
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
        calculateAvg(){
            
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
