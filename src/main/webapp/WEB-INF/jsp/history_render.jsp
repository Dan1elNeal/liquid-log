<%@page import="ru.naumen.sd40.log.parser.Constants"%>
<%@page import="ru.naumen.sd40.log.parser.Render.RenderConstants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Date" %>
<%@ page import="org.influxdb.dto.QueryResult.Series" %>

<html>

<head>
    <title>SD40 Performance indicator</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.5/css/bootstrap.min.css"
          integrity="sha384-AysaV+vQoT3kOAXZkl02PThvDr8HYKPZhNT5h/CXfBThSRXQ6jW5DO2ekP5ViFdi" crossorigin="anonymous"/>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.5/js/bootstrap.min.js"
            integrity="sha384-BLiI7JTZm+JWlgKa0M0kGRpJbF2J8q+qreVrKBC47e3K6BW78kGLrCkeRX6I9RoK"
            crossorigin="anonymous"></script>
    <link rel="stylesheet" href="/css/style.css"/>
</head>

<body>

<script src="http://code.highcharts.com/highcharts.js"></script>
<%
    Number times[] = (Number[])request.getAttribute(Constants.TIME);
    Number count[]= (Number[])request.getAttribute(RenderConstants.COUNT);
    Number min[] = (Number[])request.getAttribute(RenderConstants.MIN);
    Number max[] = (Number[])request.getAttribute(RenderConstants.MAX);
    Number mean[]= (Number[])request.getAttribute(RenderConstants.MEAN);
    Number stddev[]= (Number[])request.getAttribute(RenderConstants.STDDEV);
    Number p50[] = (Number[])request.getAttribute(RenderConstants.PERCENTILE50);
    Number p95[] = (Number[])request.getAttribute(RenderConstants.PERCENTILE95);
    Number p99[] = (Number[])request.getAttribute(RenderConstants.PERCENTILE99);
    Number p999[] = (Number[])request.getAttribute(RenderConstants.PERCENTILE999);
%>

<div class="container">
	<br>
    <h1>Performance data for "${client}"</h1>
    <h4 id="date_range"></h4>
    <p>
        Feel free to hide/show specific data by clicking on chart's legend
    </p>
</div>

<div class="container">
<div id="render-chart-container" style="height: 600px"></div>
<div class="scroll-container">
	<table class="table table-fixed header-fixed">
        <thead class="thead-inverse">
            <th class="col-xs-3">Time</th>
            <th class="col-xs-1">Count</th>
            <th class="col-xs-1">MIN</th>
            <th class="col-xs-1">MAX</th>
            <th class="col-xs-1">MEAN</th>
            <th class="col-xs-1">STD DEV</th>
            <th class="col-xs-1">P 50</th>
            <th class="col-xs-1">P 95</th>
            <th class="col-xs-1">P 99</th>
            <th class="col-xs-1">P 99,9</th>
        </thead>
        <tbody >
            <% for(int i=0;i<times.length;i++) {%>
                <tr class="row">
                    <td class="col-xs-3" style="text-align:center;">
                       <%= new java.util.Date(times[i].longValue()).toString() %>
                    </td>
                    <td class="col-xs-1">
                        <%= java.lang.Math.round(count[i].doubleValue()) %>
                    </td>
                    <td class="col-xs-1">
                        <%= java.lang.Math.round(min[i].doubleValue()) %>
                    </td>
                    <td class="col-xs-1">
                        <%= java.lang.Math.round(max[i].doubleValue()) %>
                    </td>
                    <td class="col-xs-1">
                        <%= java.lang.Math.round(mean[i].doubleValue()) %>
                    </td>
                    <td class="col-xs-1">
                        <%= java.lang.Math.round(stddev[i].doubleValue()) %>
                    </td>
                    <td class="col-xs-1">
                        <%= java.lang.Math.round(p50[i].doubleValue()) %>
                    </td>
                    <td class="col-xs-1">
                        <%= java.lang.Math.round(p95[i].doubleValue()) %>
                    </td>
                    <td class="col-xs-1">
                        <%= java.lang.Math.round(p99[i].doubleValue()) %>
                    </td>
                    <td class="col-xs-1">
                        <%= java.lang.Math.round(p999[i].doubleValue()) %>
                    </td>

                </tr>
            <% } %>
        </tbody>
    </table>
</div>

</div>
<script type="text/javascript">
var times = [];
var count = [];
var min = [];
var max = [];
var mean = [];
var stddev = [];
var p50 = [];
var p95 = [];
var p99 = [];
var p999 = [];

<% for(int i=0;i<times.length;i++) {%>
    times.push((<%=times[i]%>));
    count.push([new Date(<%= times[i] %>), <%= java.lang.Math.round(count[i].doubleValue()) %>]);
    min.push([new Date(<%= times[i] %>), <%= java.lang.Math.round(min[i].doubleValue()) %>]);
    max.push([new Date(<%= times[i] %>), <%= java.lang.Math.round(max[i].doubleValue()) %>]);
    mean.push([new Date(<%= times[i] %>), <%= java.lang.Math.round(mean[i].doubleValue()) %>]);
    stddev.push([new Date(<%= times[i] %>), <%= java.lang.Math.round(stddev[i].doubleValue()) %>]);
    p50.push([new Date(<%= times[i] %>), <%= java.lang.Math.round(p50[i].doubleValue()) %>]);
    p95.push([new Date(<%= times[i] %>), <%= java.lang.Math.round(p95[i].doubleValue()) %>]);
    p99.push([new Date(<%= times[i] %>), <%= java.lang.Math.round(p99[i].doubleValue()) %>]);
    p999.push([new Date(<%= times[i] %>), <%= java.lang.Math.round(p999[i].doubleValue()) %>]);
<% } %>

document.getElementById('date_range').innerHTML += 'From: '+new Date(times[<%=times.length%>-1])+'<br/>To:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' +new Date(times[0])

if(localStorage.getItem('count')==null){
    localStorage.setItem('count', 'false');
}
if(localStorage.getItem('min')==null){
    localStorage.setItem('min', 'false');
}
if(localStorage.getItem('max')==null){
    localStorage.setItem('max', 'false');
}
if(localStorage.getItem('mean')==null){
    localStorage.setItem('mean', 'false');
}
if(localStorage.getItem('stddev')==null){
    localStorage.setItem('stddev', 'false');
}
if(localStorage.getItem('p50')==null){
    localStorage.setItem('p50', 'false');
}
if(localStorage.getItem('p95')==null){
    localStorage.setItem('p95', 'false');
}
if(localStorage.getItem('p99')==null){
    localStorage.setItem('p99', 'true');
}
if(localStorage.getItem('p999')==null){
    localStorage.setItem('p999', 'false');
}


var countIsVisible = localStorage.getItem('count')==='true';
var minIsVisible = localStorage.getItem('min')==='true';
var maxIsVisible = localStorage.getItem('max')==='true';
var meanIsVisible = localStorage.getItem('mean')==='true';
var stddevIsVisible = localStorage.getItem('stddev')==='true';
var p50IsVisible = localStorage.getItem('p50')==='true';
var p95IsVisible = localStorage.getItem('p95')==='true';
var p99IsVisible = localStorage.getItem('p99')==='true';
var p999IsVisible = localStorage.getItem('p999')==='true';

Highcharts.setOptions({
	global: {
		useUTC: false
	}
});

var myChart = Highcharts.chart('render-chart-container', {
        chart: {
                zoomType: 'x,y'
            },

        title: {
            text: 'Render Time Information'
        },

        tooltip: {
            formatter: function() {
                            var index = this.point.index;
                            var date =  new Date(times[index]);
                            return Highcharts.dateFormat('%a %d %b %H:%M:%S', date)
                            + '<br/> <b>'+this.series.name+'</b> '+ this.y + ' '+this.series.options.unit+ '<br/>'
                        }
        },

        xAxis: {
            labels:{
                formatter:function(obj){
//                        var index = this.point.index;
//                        var date =  new Date(times[index]);
                        return Highcharts.dateFormat('%a %d %b %H:%M:%S', new Date(times[this.value]));
                    }
                },
                reversed: true
        },

        yAxis: {
            title: {
                text: 'Render'
            },
            plotLines: [{
                value: 0,
                width: 1,
                color: '#808080'
            }]
        },
        plotOptions: {
            line: {
                marker: {
                    enabled: false
                },
                events: {
                    legendItemClick: function(event) {
                        var series = this.yAxis.series;
                        seriesLen = series.length;

                        if(event.target.index==0){
                            localStorage.setItem('count', !series[0].visible);
                        }
                        if(event.target.index==1){
                            localStorage.setItem('min', !series[1].visible);
                        }
                        if(event.target.index==2){
                            localStorage.setItem('max', !series[2].visible);
                        }
                        if(event.target.index==3){
                            localStorage.setItem('mean', !series[3].visible);
                        }
                        if(event.target.index==4){
                            localStorage.setItem('stddev', !series[4].visible);
                        }
                        if(event.target.index==5){
                            localStorage.setItem('p50', !series[5].visible);
                        }
                        if(event.target.index==6){
                            localStorage.setItem('p95', !series[6].visible);
                        }
                        if(event.target.index==7){
                            localStorage.setItem('p99', !series[7].visible);
                        }
                        if(event.target.index==8){
                            localStorage.setItem('p999', !series[8].visible);
                        }

                    }
                }
            }
        },
        series: [{
            name: 'Count',
            data: count,
            visible: countIsVisible,
            unit: 'times',
            turboThreshold: 10000
        }, {
            name: 'Min',
            data: min,
            visible: minIsVisible,
            unit: 'ms',
            turboThreshold: 10000

        }, {
            name: 'Max',
            data: max,
            visible: maxIsVisible,
            unit: 'ms',
            turboThreshold: 10000
        }, {
            name: 'Mean',
            data: mean,
            visible: meanIsVisible,
            unit: 'ms',
            turboThreshold: 10000
        }, {
            name: 'Stddev',
            data: stddev,
            visible: stddevIsVisible,
            unit: 'ms',
            turboThreshold: 10000
        }, {
            name: 'Percentile 50',
            data: p50,
            visible: p50IsVisible,
            unit: 'ms',
            turboThreshold: 10000
        }, {
            name: 'Percentile 95',
            data: p95,
            visible: p95IsVisible,
            unit: 'ms',
            turboThreshold: 10000
        }, {
            name: 'Percentile 99',
            data: p99,
            visible: p99IsVisible,
            unit: 'ms',
            turboThreshold: 10000
        }, {
            name: 'Percentile 99,9',
            data: p999,
            visible: p999IsVisible,
            unit: 'ms',
            turboThreshold: 10000
        },

        ]
});

</script>

</body>

</html>