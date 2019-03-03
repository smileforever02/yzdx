import $ from '../utils'

const mask = $('#mask');
export default {
    search(query){
        return this.get('/search?' + this._toQueryString(query))
    },
    uploadDataFile(files){
        let deffer = $.Deferred()
        files = files || [];
        if(files.length === 0){
            deffer.reject('no files');
            return deffer;
        }
        let formData = new FormData();
        Array.prototype.slice.call(files).forEach(f => formData.append("files", f));
        mask.show();
        $.ajax({
            type: 'POST',
            url: '/uploadMultipleFiles',
            data: formData,
            processData: false,
            contentType: false
        }).done(data => deffer.resolve(data))
          .fail((arg1, arg2, arg3) => deffer.reject(arg1, arg2, arg3))
          .always(() => mask.hide());
        return deffer;
    },
    post(url, data){
        return this.send(url, 'POST', data);
    },
    get(url){
        return this.send(url, 'GET');
    },
    send(url, method, data){
        let options = {
            url: url,
            type: method,
            dataType: 'json'
        };
        if(method === 'POST' || method === 'PUT'){
            options.data = JSON.stringify(data);
            options.contentType = "application/json; charset=utf-8";
        }
        return $.ajax(options);
    },
    _toQueryString(query){
        let q = [];
        for(var v in query){
            if(query.hasOwnProperty(v)){
                query[v] && q.push(v + '=' + encodeURIComponent(query[v]));
            }
        }
        return q.join('&');
    }
}