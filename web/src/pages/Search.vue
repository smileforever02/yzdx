<template>
    <div class="full-width" style="display: flex;height: 100%;">
        <form class="full-width form-inline" style="position: absolute;top: 28px;left: 0px;padding: 0 1em 0;background-color: white;height: 93px;line-height: 45px;z-index: 10;" v-on:submit.prevent="submit">
            <div class="form-group">
                <label for="reportName">报表名称</label>
                <input class="form-control" id="reportName" placeholder="报表名称" v-model.lazy.trim="reportName">
            </div>
            <div class="form-group">
                <label for="description">报表名描述</label>
                <input class="form-control" id="description" placeholder="报表名描述" v-model.lazy.trim="description">
            </div>
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
        <div class="records-list" style="border-right: 2px solid #7d7d7d;">
            <ul class="item-list">
                <li v-for="item in items" v-bind:key="item.recordId" v-bind:data-recordId="item.recordId">
                    <input type="checkbox" :value="item.checked" v-model="item.checked"><span v-on:click="display(item)">{{item.userId + '&nbsp;&nbsp;&nbsp;' + item.testDate + '&nbsp;&nbsp;&nbsp;' + item.bodyPart}}</span>
                </li>
            </ul>
        </div>
        <div class="records-list">
            <ul class="item-list">
                <li v-for="item in items" v-bind:key="item.recordId" v-bind:data-recordId="item.recordId">
                    <input type="checkbox" :value="item.checked" v-model="item.checked"><span v-on:click="display(item)">{{item.userId + '&nbsp;&nbsp;&nbsp;' + item.testDate + '&nbsp;&nbsp;&nbsp;' + item.bodyPart}}</span>
                </li>
            </ul>
        </div>
        <chart v-if="showChart === true" class="dx-chart" :chartQuery="chartQuery"></chart>
        <span v-if="showChart === true" @click="closeChart(-1)" class="glyphicon glyphicon-remove-circle" aria-hidden="true" style="position: absolute;z-index: 120;top: 0.5em;right: 0.5em;font-size: 2em;cursor: pointer;color: red;"></span>
    </div>
</template>

<script>
import $ from "../utils"
import Services from '../services/Services'
import MessageBox from '../services/MessageBox';
export default {
    data(){
        return {
            description: null,
            userName: null,
            reportName: null,
            fromAge: null,
            toAge: null,
            gender: 'ALL',
            bodyPart: 'ALL',
            fromDate: null,
            toDate: null,
            items: [],
            showChart: true,
            chartQuery: null
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
            Services.search(this.__fetchParam())
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
            this.chartQuery = user;
            this.showChart = true;
            // this.$store.commit('updateChartQuery', user);
            // this.$router.push('/chart');
        },
        calculateAvg(){
            let that = this;
            let param = this.__fetchParam();
            var dialog = new BootstrapDialog({
                title: '创建',
                // size: BootstrapDialog.SIZE_LARGE,
                closable: false,
                cssClass: 'allen-dialg',
                message: function(dialogRef){
                    // var $message = $('<div class="row"><input class="col-xs-12 report-name" placeholder="报表名称"></div>' + 
                    //     '<div class="row"><input class="col-xs-12 report-desc" placeholder="报表描述"></div>');
                    var $message = $(`
                                <form class="full-width form-inline" style=";padding: 0 1em 0;">
                                    <div class="form-group">
                                        <label for="reportName">报表名称</label>
                                        <input class="form-control report-name" placeholder="报表名称">
                                    </div>
                                    <div class="form-group">
                                        <label for="description">报表名描述</label>
                                        <input class="form-control report-desc" placeholder="报表名描述">
                                    </div>
                                </form>
                        `);
                    return $message;
                },
                buttons: [{
                    label: '创建',
                    cssClass: 'btn-success',
                    action: function(dialog) {
                        let name = dialog.$modalBody[0].querySelector('.report-name').value.trim();
                        let desc = dialog.$modalBody[0].querySelector('.report-desc').value.trim();
                        if(name.length === 0){
                            MessageBox.info('请输入报表名称');
                            dialog.$modalBody[0].querySelector('.report-name').focus();
                            return;
                        }
                        param.avgRecordName = name;
                        param.description = desc;
                        Services.genAvgRecordDetail(param)
                           .done(data => {
                               dialog.close();
                               MessageBox.success('创建成功');
                           }).fail(() => {
                               MessageBox.error('创建失败');
                           });
                    }
                },{
                    label: '取消',
                    cssClass: 'btn-warning',
                    action: function(dialog) {
                        dialog.close();
                    }
                }]
            });
            dialog.open();
        },
        checkAvg(){
            let selects = (this.items || []).filter(u => u.checked);
            if(selects.length === 0){
                MessageBox.info('请至少选择一行记录.');
            }else{
                console.log(selects);
            }
        },
        __fetchParam(){
            return {
                description: this.description, 
                recordName: this.reportName, 
                userId: this.userName, 
                fromAge: this.fromAge, 
                toAge: this.toAge, 
                gender: this.gender, 
                bodyPart: this.bodyPart
            };
        },
        closeChart(){
            this.showChart = false;
        }
    }
}
</script>
