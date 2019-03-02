const copydir = require('copy-dir');
const path = require('path');
const fromDir = path.resolve(__dirname, '../dist');
const toDir = path.resolve(__dirname, '../../src/main/resources/static');

console.log('start to copy to ' + toDir)
copydir(fromDir, toDir, function(err){
    if(err){
        console.log(err);
    } else {
        console.log('copy done');
    }
});