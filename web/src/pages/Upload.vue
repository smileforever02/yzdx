<template>
    <div class="full-width center-content">
        <form class="" style="width: 90%;" v-on:submit.prevent="submit">
            <div class="form-group">
                <input @change="previewFiles($event.target.files)" ref="file" class="form-control" type="file" required multiple accept="application/excel, application/vnd.ms-excel, application/x-excel, application/x-msexcel, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" placeholder="上传文件">
            </div>
            <div class="preview">
                <ul>
                    <li v-for="file in files" v-bind:key="file.name">
                        <span style="color: green;margin-right: 2em;">{{file.name}}</span> <span>{{file.size}}</span>
                    </li>
                </ul>
            </div>
            <button type="submit" class="btn btn-primary">上传</button>
            <button type="reset" class="btn" @click="reset">重置</button>
        </form>
    </div>
</template>

<script>
import MessageBox from '../services/MessageBox'
import Services from '../services/Services'
const def = [{
                name: '未选择任何文件上传',
                size: ''
            }];
export default {
    data(){
        return {
            files: def
        };
    },
    methods: {
        reset(){
            this.files = def;
        },
        previewFiles(files){
            console.log(files);
            if(files && files.length > 0){
                this.files = Array.prototype.slice.call(files).map(f => {
                    return {
                        name: f.name,
                        size: (f.size/1024).toFixed(1) + ' KB'
                    };
                });
            }else{
                this.files = def;
            }
        },
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
