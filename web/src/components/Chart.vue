<template>
    <div class="full-width center-content">
        <form class="full-width form-inline" style="position: absolute;top: 28px;left: 0px;padding: 0 1em 0;background-color: white;height: 45px;line-height: 45px;z-index: 10;" v-on:submit.prevent="setMax">
            <div class="form-group">
                <label for="startIdx">开始点</label>
                <input class="form-control" id="startIdx" min="0" type="number" v-model.lazy.trim="startIdx">
            </div>
            <div class="form-group">
                <label for="maxDisplayed">最大显示</label>
                <input class="form-control" id="maxDisplayed" min="10" type="number" v-model.lazy.trim="maxDisplayed">
            </div>
            <!-- <div class="form-group">
                <label for="age">年龄</label>
                <input class="form-control" id="fromAge" placeholder="开始年龄" type="number" max="120" min="0" v-model.lazy.trim="fromAge">
                <label>--</label>
                <input class="form-control" id="toAge" placeholder="结束年龄" type="number" max="120" min="0" v-model.lazy.trim="toAge">
            </div> -->
            <button type="submit" class="btn btn-primary">确定</button>
        </form>
        <div class="chart-container" style="position: relative; width:90%;margin: auto;margin-top: 5em;">
            <canvas id="scoreChart"></canvas>
            <span @mouseenter="startMove(-1)" @mouseleave="stop()" class="glyphicon glyphicon-chevron-left chart-mover" aria-hidden="true" style="left: 1em;"></span>
            <span @mouseenter="startMove(1)" @mouseleave="stop()" class="glyphicon glyphicon-chevron-right chart-mover" aria-hidden="true" style="right: 1em;"></span>
        </div>
    </div>
</template>

<script>
import $ from "../utils"
import Services from '../services/Services'
import MessageBox from '../services/MessageBox';

const MAX_DISPLAYED = 100;
const STEP = 1;
const LEFT = -1;
const RIGHT = 1;
const SPEED = 250;

export default {
    props: {
        chartQuery: {
            type: Object
        }
    },
    data(){
        return {
            startIdx: 0,
            step: STEP,
            maxDisplayed: MAX_DISPLAYED,
            chart: null,
            chartCfg: null,
            chartData: {},
            tag: -1
        }
    },
    mounted(){
        console.log('mount chart');
        console.log(`chart query: ${JSON.stringify(this.chartQuery)}`);
        this.drawChart();
    },
    methods: {
        setMax(){
            try{
                this.maxDisplayed = parseInt(this.maxDisplayed);
            }catch(e){
                this.maxDisplayed = MAX_DISPLAYED;
            }
            try{
                this.startIdx = parseInt(this.startIdx);
            }catch(e){
                this.startIdx = 0;
            }
            this.draw();
        },
        startMove(direction){
            clearInterval(this.tag);
            this.tag = setInterval(() => this.move(direction), SPEED);
        },
        stop(){
            clearInterval(this.tag);
        },
        move(direction){
            console.log(`direction: ${direction}`)
            console.log(`startIdx: ${this.startIdx}`)
            if(this.startIdx === 0 && direction === LEFT){
                console.log('left')
                return;
            }
            if(this.startIdx + this.maxDisplayed >= this.chartData.size && direction === RIGHT){
                console.log('right')
                return;
            }
            this.startIdx += (direction * STEP);
            this.draw();
        },
        initChartData(callback){
            if(this.chartQuery.isCompare){
                $.when(Services.queryRecordDetail({recordId: this.chartQuery.record.recordId}),
                    Services.queryAvgRecordDetail({avgRecordId: this.chartQuery.avgRecord.avgRecordId})).done((nor, avg) => {
                         this.__normalizeRecordDetail(nor, avg);
                         this.__initChart(this.chartQuery.record.userId + '  ' + this.chartQuery.avgRecord.avgRecordName + '  ' + this.chartQuery.record.testDate + '  ' + this.chartQuery.record.bodyPart + '  比较图');
                         callback();
                    }).fail(d => {
                        MessageBox.warn('查询数据失败');
                    });
                return;
            }

            let record = this.chartQuery.record;
            if(this.chartQuery.isAvg){
                Services.queryAvgRecordDetail({avgRecordId: this.chartQuery.record.avgRecordId}).done(data => {
                    this.__normalizeRecordDetail(data);
                    this.__initChart(record.avgRecordName + '  ' + record.userId + '  ' + record.testDate + '  ' + record.bodyPart);
                    callback();
                }).fail(d => {
                    MessageBox.warn('查询数据失败');
                });
            }else{
                Services.queryRecordDetail({recordId: this.chartQuery.record.recordId}).done(data => {
                    this.__normalizeRecordDetail(data);
                    this.__initChart(record.userId + '  ' + record.testDate + '  ' + record.bodyPart);
                    callback();
                }).fail(d => {
                    MessageBox.warn('查询数据失败');
                });
            }
        },
        getDisplayedChartData(){
            let end = Math.min(this.startIdx + this.maxDisplayed, this.chartData.size);
            let d = {
                labels: this.chartData.labels.slice(this.startIdx, end),
                data1: this.chartData.data1.slice(this.startIdx, end),
                data2: this.chartData.data2.slice(this.startIdx, end)
            };
            if(this.chartQuery.isCompare){
                d.data11 = this.chartData.data11.slice(this.startIdx, end);
                d.data22 = this.chartData.data22.slice(this.startIdx, end);
            }
            return d;
        },
        drawChart(){
            this.initChartData(this.draw.bind(this));
        },
        draw(){
            let displayedChartData = this.getDisplayedChartData();
            let d = this.chartCfg.data;
            d.labels = displayedChartData.labels;
            d.datasets[0].data = displayedChartData.data1;
            d.datasets[1].data = displayedChartData.data2;
            if(this.chartQuery.isCompare){
                d.datasets[2].data = displayedChartData.data11;
                d.datasets[3].data = displayedChartData.data22;
            }
            this.chart.update(0);
        },
        __normalizeRecordDetail(data, _data){
            let size = data.length;
            let labels = data.map(d => d.time);
            let data1 = data.map(d => d.deltaCapPerc);
            let data2 = data.map(d => d.power);
            this.chartData = {
                size: size,
                labels: labels,
                data1: data1,
                data2: data2
            };
            if(_data){
                size = Math.min(size, _data.length);
                this.chartData.size = size;
                this.chartData.data11 = _data.map(d => d.deltaCapPerc);
                this.chartData.data22 = _data.map(d => d.power);
            }
        },
        __initChart(title){
            var lineChartData = {
                labels: [],
                datasets: [{
                    label: '电容',
                    borderColor: 'red',
                    backgroundColor: 'red',
                    fill: false,
                    data: [],
                    yAxisID: 'y-axis-1',
                }, {
                    label: '力',
                    borderColor: 'blue',
                    backgroundColor: 'blue',
                    fill: false,
                    data: [],
                    yAxisID: 'y-axis-2'
                }]
            };
            if(this.chartQuery.isCompare){
                lineChartData.datasets.push({
                    label: '平均电容',
                    borderColor: '#337ab7',
                    backgroundColor: '#337ab7',
                    fill: false,
                    data: [],
                    yAxisID: 'y-axis-1',
                });
                lineChartData.datasets.push({
                    label: '平均力',
                    borderColor: 'green',
                    backgroundColor: 'green',
                    fill: false,
                    data: [],
                    yAxisID: 'y-axis-2',
                });
            }
            this.chartCfg = {
				data: lineChartData,
				options: {
					responsive: true,
					hoverMode: 'index',
					stacked: false,
					title: {
						display: true,
						text: title // record.userId + '  ' + record.testDate + '  ' + record.bodyPart
					},
					scales: {
						yAxes: [{
							type: 'linear', // only linear but allow scale type registration. This allows extensions to exist solely for log scale for instance
							display: true,
							position: 'left',
							id: 'y-axis-1',
						}, {
							type: 'linear', // only linear but allow scale type registration. This allows extensions to exist solely for log scale for instance
							display: true,
							position: 'right',
							id: 'y-axis-2',
							// grid line settings
							gridLines: {
								drawOnChartArea: false // only want the grid lines for one axis to show up
							},
						}],
					}
				}
			};
			this.chart = Chart.Line(document.getElementById('scoreChart').getContext('2d'), this.chartCfg);
        }
    }
}
</script>