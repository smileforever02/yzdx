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
            <span v-on:mousedown="move(-1)" class="glyphicon glyphicon-chevron-left chart-mover" aria-hidden="true" style="left: 1em;"></span>
            <span v-on:mousedown="move(1)" class="glyphicon glyphicon-chevron-right chart-mover" aria-hidden="true" style="right: 1em;"></span>
        </div>
    </div>
</template>

<script>
import $ from "../utils"
import Services from '../services/Services'
import MessageBox from '../services/MessageBox';

const MAX_DISPLAYED = 300;
const STEP = 10;
const LEFT = -1;
const RIGHT = 1;

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
            chartData: {}
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
        initChartData(){
            let size = 1000;
            let labels = [];
            let data1 = [];
            let data2 = [];
            for(let i = 0; i < size; i++){
                labels.push(i+1);
                data1.push(size - i);
                data2.push(i);
            }
            this.chartData = {
                size: size,
                labels: labels,
                data1: data1,
                data2: data2
            };
            var lineChartData = {
                labels: [],
                datasets: [{
                    label: 'data1',
                    borderColor: 'red',
                    backgroundColor: 'red',
                    fill: false,
                    data: [],
                    yAxisID: 'y-axis-1',
                }, {
                    label: 'data2',
                    borderColor: 'blue',
                    backgroundColor: 'blue',
                    fill: false,
                    data: [],
                    yAxisID: 'y-axis-2'
                }]
            };

            this.chartCfg = {
				data: lineChartData,
				options: {
					responsive: true,
					hoverMode: 'index',
					stacked: false,
					title: {
						display: true,
						text: '数据中心'
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
        },
        getDisplayedChartData(){
            let end = Math.min(this.startIdx + this.maxDisplayed, this.chartData.size);
            return {
                labels: this.chartData.labels.slice(this.startIdx, end),
                data1: this.chartData.data1.slice(this.startIdx, end),
                data2: this.chartData.data2.slice(this.startIdx, end)
            };
        },
        drawChart(){
            console.log(this.$store.state.chartDataQuery);
            this.initChartData();
            this.draw();
        },
        draw(){
            let displayedChartData = this.getDisplayedChartData();
            let d = this.chartCfg.data;
            d.labels = displayedChartData.labels;
            d.datasets[0].data = displayedChartData.data1;
            d.datasets[1].data = displayedChartData.data2;
            this.chart.update();
        }
    }
}
</script>