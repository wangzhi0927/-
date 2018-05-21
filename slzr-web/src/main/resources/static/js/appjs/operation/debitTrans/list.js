var prefix = "/operation/debitTrans"
$(function() {
	var Id = '';
	load(Id);
});

function load(Id) {
	$('#exampleTable')
		.bootstrapTable(
			{
				method : 'get', // 服务器数据的请求方式 get or post
				url : prefix + "/api/list", // 服务器数据的加载地址
				// showRefresh : true,
				// showToggle : true,
				// showColumns : true,
				iconSize : 'outline',
				toolbar : '#exampleToolbar',
				striped : true, // 设置为true会有隔行变色效果
				dataType : "json", // 服务器返回的数据类型
				pagination : true, // 设置为true会在底部显示分页条
				// queryParamsType : "limit",
				// //设置为limit则会发送符合RESTFull格式的参数
				singleSelect : false, // 设置为true将禁止多选
				// contentType : "application/x-www-form-urlencoded",
				// //发送到服务器的数据编码类型
				pageSize : 10, // 如果设置了分页，每页数据条数
				pageNumber : 1, // 如果设置了分布，首页页码
				// search : true, // 是否显示搜索框
				showColumns : false, // 是否显示内容下拉框（选择显示的列）
				sidePagination : "server", // 设置在哪里进行分页，可选值为"client" 或者
				// "server"
				queryParams : function(params) {
					return {
						// 说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
						limit : params.limit,
						offset : params.offset,
						searchvalue : $('#searchName').val(),
						sdate : $('#sdate').val(),
						edate : $('#edate').val(),
						deptName : $('#deptName').val(),
						lineName : $('#lineName').val(),
						busNO : $('#busNO').val(),
                        merchantCode:$('#merchantCode').val(),
						driverName : $('#driverName').val(),
						busNO : $('#busNO').val(),
						status : $('#status').val(),
						Id : Id
					};
				},
				// //请求服务器数据时，你可以通过重写参数的方式添加一些额外的参数，例如 toolbar 中的参数 如果
				// queryParamsType = 'limit' ,返回参数必须包含
				// limit, offset, search, sort, order 否则, 需要包含:
				// pageSize, pageNumber, searchText, sortName,
				// sortOrder.
				// 返回false将会终止请求
				columns : [
					{
						field : 'accountNO',
						title : '账号',
						align:'center'
					},
					{
						field : 'lineNO',
						title : '线路',
						align:'center'
					},
					{
						field : 'terminalNO',
						title : '车辆',
						align:'center'
					},
					{
						field : 'driverNO',
						title : '司机',
						align:'center'
					},
					{
						field : 'txnAmount',
						title : '消费金额',
						align:'right',
						formatter : function(value, row, index) {
							return value.toFixed(2);
						}
					},
					{
						field : 'balance',
						title : '账号余额',
						align:'right',
						formatter : function(value, row, index) {
							return value.toFixed(2);
						}
					},
					{
						field : 'txnDateTime',
						title : '消费时间',
						align:'center'
					},
					{
						field : 'debitStatus',
						title : '交易状态',
						align:'leftr',
						formatter : function(value, row, index) {
							if(value==1)
								return "正常扣费";
							if(value==2)
								return "未补扣";
							if(value==3)
								return "已补扣";
						}
					},
                    {
                        field : 'debitFrom',
                        title : '扣费来源',
                        align:'center',
                        formatter : function(value, row, index) {
                            if(value==1) {
                                return "赠送余额";
							}
                        }
                    },
					{
						title : '操作',
						field : 'id',
						align : 'center',
						formatter : function(value, row, index) {
							var detailhtml="";
							detailhtml+="<tr><td>补扣时间</td><td>"+(row.updateStatusDateTime!=null?row.updateStatusDateTime:"&nbsp;")+"</td></tr>";
							detailhtml+="<tr><td>上车站点</td><td>"+(row.startStationCode!=null?row.startStationCode:"&nbsp;")+"</td></tr>";
							detailhtml+="<tr><td>上车时间</td><td>"+(row.startStationTime!=null?row.startStationTime:"&nbsp;")+"</td></tr>";
							detailhtml+="<tr><td>下车站点</td><td>"+(row.endStationCode!=null?row.endStationCode:"&nbsp;")+"</td></tr>";
							detailhtml+="<tr><td>下车时间</td><td>"+(row.endStaionTime!=null?row.endStaionTime:"&nbsp;")+"</td></tr>";
							detailhtml+="<tr><td>票价</td><td>"+(row.ticketPrice!=null?row.ticketPrice:"&nbsp;")+"</td></tr>";
							detailhtml+="<tr><td>折扣</td><td>"+(row.discount!=null?row.discount:"&nbsp;")+"</td></tr>";
							detailhtml+="<tr><td>方向</td><td>"+(row.direction!=null?row.direction:"&nbsp;")+"</td></tr>";
							var d = '<button onmouseover=\'over(this)\' onmouseleave=\'leave(this)\' class="btndetail btn btn-warning btn-sm" href="javascript:void(0)" title="详情" data-container="body" data-toggle="popover" data-placement="left" data-html="true" data-content="<div style=\'height:230px;\'><table class=\'popdetailtable layui-table\'><tbody>'+detailhtml+'</tbody></table></div>"><i class="fa fa-ellipsis-h "></i></button> ';
							return d;
						}
					}
				]
			});
}
function reLoad() {
	$('#exampleTable').bootstrapTable('refresh');
}
function excel(){
	layer.confirm('确定导出吗?', {
		  btn: ['是','否']
	}, function(){
		layer.closeAll('dialog');
		var elseparams='';
		elseparams+='&deptName=' + $('#deptName').val();
		elseparams+='&lineName=' + $('#lineName').val();
		elseparams+='&busNO=' + $('#busNO').val();
		elseparams+='&driverName=' + $('#driverName').val();
		elseparams+='&busNO=' + $('#busNO').val();
		elseparams+='&status=' + $('#status').val();
		window.location.href= prefix + '/expexcel?searchvalue=' + $('#searchName').val() + '&sdate=' + $('#sdate').val() + '&edate=' + $('#edate').val()+elseparams;
	}, function(){
	});
}
function over(o)
{
	$(o).popover('show');
}
function leave(o)
{
	$(o).popover('hide');
}