<template>
    <div class="full-width center-content">
        <div class="chart-container" style="position: relative; width:90%;margin: auto;margin-top: 5em;">
            <canvas id="scoreChart"></canvas>
        </div>
    </div>
</template>

<script>
import $ from "../utils"
import Services from '../services/Services'
import MessageBox from '../../../../displayer/src/services/MessageBox';
export default {
    data(){
        return {
            chartData: {}
        }
    },
    mounted(){
        console.log('mount chart');
        this.drawChart();
    },
    methods: {
        drawChart(){
            console.log(this.$store.state.chartDataQuery);
            let size = 100;
            let labels = [];
            let data1 = [];
            let data2 = [];
            for(let i = 0; i < size; i++){
                labels.push(i+1);
                data1.push(size - i);
                data2.push(i);
            }
            var lineChartData = {
                labels: labels,
                datasets: [{
                    label: 'data1',
                    borderColor: 'red',
                    backgroundColor: 'red',
                    fill: false,
                    data: data1,
                    yAxisID: 'y-axis-1',
                }, {
                    label: 'data2',
                    borderColor: 'blue',
                    backgroundColor: 'blue',
                    fill: false,
                    data: data2,
                    yAxisID: 'y-axis-2'
                }]
            };
            var ctx = document.getElementById('scoreChart').getContext('2d');
			Chart.Line(ctx, {
				data: lineChartData,
				options: {
					responsive: true,
					hoverMode: 'index',
					stacked: false,
					title: {
						display: true,
						text: 'Chart.js Line Chart - Multi Axis'
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
								drawOnChartArea: false, // only want the grid lines for one axis to show up
							},
						}],
					}
				}
			});
        }
    }
}
</script>