<template>
    <div class="full-width center-content">
        <form class="full-width page-content form-inline" v-on:submit.prevent="submit">
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
        </form>
        <ul class="item-list no-padding">
            <li v-for="item in items" v-bind:key="item.userId" v-bind:data-userid="item.userId">
                <input type="checkbox"><span v-on:click="checkUser(item.userId, item.nickName)">{{item.nickName}}</span><span v-on:click="addFriend(item.userId, item.nickName)" class="glyphicon glyphicon-plus-sign right" aria-hidden="true"></span>
            </li>
        </ul>
    </div>
</template>

<script>
import $ from "../utils"
import Services from '../services/Services'
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
            Services.search({userName: this.userName, fromAge: this.fromAge, toAge: this.toAge, gender: this.gender})
                .done((data) => {
                    console.log(data)
                    // this.$router.push('/user/' + this.userId);
                }).fail(() => console.log(arguments))
        }
    }
}
</script>
