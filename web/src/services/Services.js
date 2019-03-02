import $ from '../utils'

const mask = $('#mask');
export default {
    search(query){
        return this.get('/search?' + this._toQueryString(query))
    },
    uploadDataFile(file){
        let deffer = $.Deferred()
        let formData = new FormData();
        formData.append("file", file);
        mask.show();
        $.ajax({
            type: 'POST',
            url: '/uploadFile',
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