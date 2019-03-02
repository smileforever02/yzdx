<template>
    <div class="full-width center-content">
        <form class="" style="width: 90%;" v-on:submit.prevent="submit">
            <div class="form-group">
                <input ref="file" class="form-control" type="file" required accept="application/excel, application/vnd.ms-excel, application/x-excel, application/x-msexcel" placeholder="上传文件">
            </div>
            <button type="submit" class="btn btn-primary">上传</button>
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
import MessageBox from '../services/MessageBox'
import Services from '../services/Services'

export default {
    data(){
        return {};
    },
    methods: {
        submit(){
            Services.uploadDataFile(this.$refs.file.files[0])
                .done((data) => {
                    MessageBox.success('上传成功');
                    this.$router.push('/search');
                }).fail(() => console.log(arguments));
        }
    }
}
</script>
