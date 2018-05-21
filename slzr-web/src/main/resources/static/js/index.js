(function(){
	$("#merchslct").val(params('code'));
	var jsondata=JSON.parse($("#weeks").html());
	var chart1 = echarts.init(document.getElementById('chart1')); 
	var option = {
	    title : {
	        text: '最近一周交易情况'
	    },
	    tooltip : {
	        trigger: 'axis'
	    },
	    legend: {
	        data:jsondata.legends,
	        y:'bottom'
	    },
	    toolbox: {
	        show : false
	    },
	    calculable : true,
	    xAxis : [
	        {
	            type : 'category',
	            boundaryGap : false,
	            data : jsondata.weeknames
	        }
	    ],
	    yAxis : [
	        {
	            type : 'value',
	            axisLabel : {
	                formatter: '{value}'
	            }
	        }
	    ],
	    series : [
	        {
	            name:jsondata.legends[0],
	            type:'line',
	            data:jsondata.data1
	        },
	        {
	            name:jsondata.legends[1],
	            type:'line',
	            data:jsondata.data2
	        },
	        {
	            name:jsondata.legends[2],
	            type:'line',
	            data:jsondata.data3
	        },
	        {
	            name:jsondata.legends[3],
	            type:'line',
	            data:jsondata.data4
	        },
	        {
	            name:jsondata.legends[4],
	            type:'line',
	            data:jsondata.data5
	        },
	        {
	            name:jsondata.legends[5],
	            type:'line',
	            data:jsondata.data6
	        }
	    ]
	};
	chart1.setOption(option);
})();
function change(o){
	var v = $(o).val();
	var dv=params('code')?params('code'):"";
	if(dv!=v)
		location.href='main?code='+v;
}
function params(n) { 
	var i = new RegExp("(^|&)" + n + "=([^&]*)(&|$)", "i"), t = window.location.search.substr(1).match(i); return t != null ? unescape(t[2]) : null 
}