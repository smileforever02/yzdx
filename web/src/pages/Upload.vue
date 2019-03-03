<template>
    <div class="full-width center-content">
        <form class="" style="width: 90%;" v-on:submit.prevent="submit">
            <div class="form-group">
                <input ref="file" class="form-control" type="file" required multiple accept="application/excel, application/vnd.ms-excel, application/x-excel, application/x-msexcel, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" placeholder="上传文件">
            </div>
            <!-- <div class="preview">
                <p>{{description}}</p>
            </div> -->
            <button type="submit" class="btn btn-primary">上传</button>
            <button type="reset" class="btn">重置</button>
        </form>
    </div>
</template>

<script>
import MessageBox from '../services/MessageBox'
import Services from '../services/Services'

export default {
    data(){
        return {
            description: '未选择任何文件上传'
        };
    },
    methods: {
        submit(){
            Services.uploadDataFile(this.$refs.file.files)
                .done((data) => {
                    let errs = data.filter(r => r.errMsg).map(r => r.errMsg);
                    if(errs.length === 0){
                        MessageBox.success('上传成功');
                        this.$router.push('/search');
                    }else{
                        MessageBox.error(errs.join('; '));
                    }
                }).fail(() => console.log(arguments));
        }
    }
}
</script>
