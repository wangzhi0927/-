
var reporturl="/report/template/Print";
var xmlName = "file:LineBus.ureport.xml"
$("#document").ready(function(){
	init();
});
function init(){
    $.ajax({
        cache : true,
        type : "GET",
        url : "/report/utils/dwmq",
        data : {
        },
        async : false,
        error : function(request) {
            parent.layer.alert("Connection error");
        },
        success : function(data) {
        	initselect("#sweek,#eweek",data.wValues);
        	$("#sweek").val(data.startW);
        	$("#eweek").val(data.endW);
        	initselect("#smonth,#emonth",data.mValues);
        	$("#smonth").val(data.startM);
        	$("#emonth").val(data.endM);
        	initselect("#squarter,#equarter",data.qValues);
        	$("#squarter").val(data.startQ);
        	$("#equarter").val(data.endQ);
        	$("#sdate").val(data.startDay);
        	$("#edate").val(data.endDay);
        	display("day");
        	query();
        }
    });
}
function initselect(select,data){
	$.each(data,function(i,item){
    	$(select).append("<option>"+item+"</option>");
	});
}
function display(which){
	which="."+which;
	$(".day,.week,.month,.quarter").addClass("hidden");
	$(which).removeClass("hidden");
}
function datetypeChange(o){
	var v=$(o).val();
	switch(v)
	{
		case "D":
			  display("day");
			  break;
		case "W":
			  display("week");
			  break;
		case "M":
			  display("month");
			  break;
		case "Q":
			  display("quarter");
			  break;
		default:break;
	}
}

function query(){
	var type=$("#datetype").val();
	if(type=="D")
	{
		changeFrameSrc(reporturl+"?datetype=D&startDate="+$("#sdate").val()+"&endDate="+$("#edate").val()+"&lineCode="+$("#lineName").val()+"&busNO="+$("#driverName").val()+"&xmlName="+xmlName);
	}
	if(type=="W")
	{
		changeFrameSrc(reporturl+"?datetype=W&startDate="+$("#sweek").val()+"&endDate="+$("#eweek").val()+"&lineCode="+$("#lineName").val()+"&busNO="+$("#driverName").val()+"&xmlName="+xmlName);
	}
	if(type=="M")
	{
		changeFrameSrc(reporturl+"?datetype=M&startDate="+$("#smonth").val()+"&endDate="+$("#emonth").val()+"&lineCode="+$("#lineName").val()+"&busNO="+$("#driverName").val()+"&xmlName="+xmlName);
	}
	if(type=="Q")
	{
		changeFrameSrc(reporturl+"?datetype=Q&startDate="+$("#squarter").val()+"&endDate="+$("#equarter").val()+"&lineCode="+$("#lineName").val()+"&busNO="+$("#driverName").val()+"&xmlName="+xmlName);
	}
}
function changeFrameSrc(url){
	var iframe = document.getElementById('iframe1');
    iframe.src = url;
}