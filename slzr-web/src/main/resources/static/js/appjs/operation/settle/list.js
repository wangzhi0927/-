var prefix = "/operation/settle"
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
						settletype:$('#settletype').val(),
						checktype:$('#checktype').val(),
						transstatus:$('#transstatus').val(),
						datetype:$('#datetype').val(),
                        merchantCode:$('#merchantCode').val(),
						sdate:$('#sdate').val(),
						edate:$('#edate').val()
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
						field : 'merchantCode',
						title : '商户代码',
						align:'center'
					},
					{
						field : 'merchantName',
						title : '商户简称',
						align:'left'
					},
					{
						field : 'topupAmount',
						title : '充值金额',
						align:'right',
						formatter : function(value, row, index) {
							return value.toFixed(2);
						}
					},
					{
						field : 'topupNum',
						title : '充值笔数',
						align:'center'
					},
					{
						field : 'debitAmount',
						title : '消费金额',
						align:'right',
						formatter : function(value, row, index) {
							return value.toFixed(2);
						}
					},
					{
						field : 'debitNum',
						title : '消费笔数',
						align:'center'
					},
					{
						field : 'refundAmount',
						title : '退款金额',
						align:'right',
						formatter : function(value, row, index) {
							return value.toFixed(2);
						}
					},
					{
						field : 'refundNum',
						title : '退款笔数',
						align:'center'
					},
					{
						field : 'refundServiceFee',
						title : '服务费',
						align:'right',
						formatter : function(value, row, index) {
							return value.toFixed(2);
						}
					},
					{
						field : 'settleMethod',
						title : '结算方式',
						align:'center',
						formatter : function(value, row, index) {
							if (value == '1') {
								return '充值金额';
							} else if (value == '2') {
								return '消费金额';
							}
						}
					},
					{
						field : 'settleRate',
						title : '结算费率',
						align:'right',
						formatter : function(value, row, index) {
							return value.toFixed(2);
						}
					},
					{
						field : 'settleAmount',
						title : '结算金额',
						align:'right',
						formatter : function(value, row, index) {
							return value.toFixed(2);
						}
					},
					{
						field : 'transferStatus',
						title : '转账状态',
						align : 'center',
						formatter : function(value, row, index) {
							if (value == '0') {
								return '未转账';
							} else if (value == '1') {
								return '已转账';
							}
						}
					},
					{
						field : 'auditStatus',
						title : '审核状态',
						align : 'center',
						formatter : function(value, row, index) {
							if (value == '-1') {
								return '<span class="label label-danger">未审核</span>';
							} else if (value == '0') {
								return '<span class="label label-info">审核不通过</span>';
							}else if(value=='1'){
								return '<span class="label label-success">审核通过</span>';
							}else{
								return '<span class="label label-warning">未知</span>';
							}
						}
					},
					{
						title : '操作',
						field : 'id',
						align : 'center',
						formatter : function(value, row, index) {
							var detailhtml="";
							detailhtml+="<tr><td>商户代码</td><td>"+row.merchantCode+"</td></tr>";
							detailhtml+="<tr><td>商户简称</td><td>"+row.merchantName+"</td></tr>";
							detailhtml+="<tr><td>审核时间</td><td>"+(row.auditDateTime!=null?row.auditDateTime:"&nbsp;")+"</td></tr>";
							if (row.auditStatus == '-1') {
								detailhtml+="<tr><td>审核状态</td><td>未审核</td></tr>";
							} else if (row.auditStatus == '0') {
								detailhtml+="<tr><td>审核状态</td><td>审核不通过</td></tr>";
							}else if(row.auditStatus=='1'){
								detailhtml+="<tr><td>审核状态</td><td>审核通过</td></tr>";
							}else{
								detailhtml+="<tr><td>审核状态</td><td>未知</td></tr>";
							}
							
							detailhtml+="<tr><td>审核人</td><td>"+(row.auditUserName!=null?row.auditUserName:"&nbsp;")+"</td></tr>";
							detailhtml+="<tr><td>消费金额</td><td>"+row.debitAmount+"</td></tr>";
							detailhtml+="<tr><td>消费笔数</td><td>"+row.debitNum+"</td></tr>";
							detailhtml+="<tr><td>退款金额</td><td>"+row.refundAmount+"</td></tr>";
							detailhtml+="<tr><td>退款笔数</td><td>"+row.refundNum+"</td></tr>";
							detailhtml+="<tr><td>退款服务费</td><td>"+row.refundServiceFee+"</td></tr>";
							detailhtml+="<tr><td>结算服务费</td><td>"+row.serviceCharge+"</td></tr>";
							detailhtml+="<tr><td>结算金额</td><td>"+row.settleAmount+"</td></tr>";
							detailhtml+="<tr><td>结算起始日期</td><td>"+row.settleFromDate+"</td></tr>";
							detailhtml+="<tr><td>结算结束日期</td><td>"+row.settleEndDate+"</td></tr>";
							if (row.settleMethod == '1') {
								detailhtml+="<tr><td>结算方式</td><td>充值金额</td></tr>";
							} else if (row.settleMethod == '2') {
								detailhtml+="<tr><td>结算方式</td><td>消费金额</td></tr>";
							}
							detailhtml+="<tr><td>结算费率</td><td>"+row.settleRate+"</td></tr>";
							detailhtml+="<tr><td>结算时间</td><td>"+row.summaryDateTime+"</td></tr>";
							detailhtml+="<tr><td>充值金额</td><td>"+row.topupAmount+"</td></tr>";
							detailhtml+="<tr><td>充值笔数</td><td>"+row.topupNum+"</td></tr>";
							detailhtml+="<tr><td>转账金额</td><td>"+(row.transferAmount!=null?row.transferAmount:"&nbsp;")+"</td></tr>";
							detailhtml+="<tr><td>转账时间</td><td>"+(row.transferDateTime!=null?row.transferDateTime:"&nbsp;")+"</td></tr>";
							if(row.transferOrderNO)
								detailhtml+="<tr><td>转账单号</td><td>"+row.transferOrderNO+"</td></tr>";
							else
								detailhtml+="<tr><td>转账单号</td><td></td></tr>";
							detailhtml+="<tr><td>转账说明</td><td>"+(row.transferRemark!=null?row.transferRemark:"&nbsp;")+"</td></tr>";
							if (row.transferStatus == '0') {
								detailhtml+="<tr><td>转账状态</td><td>未转账</td></tr>";
							} else if (row.transferStatus == '1') {
								detailhtml+="<tr><td>转账状态</td><td>已转账</td></tr>";
							}
							var d = '<button onmouseover=\'over(this)\' onmouseleave=\'leave(this)\' class="btndetail btn btn-warning btn-sm ' + s_detail_h + '" href="javascript:void(0)" title="详情" data-container="body" data-toggle="popover" data-placement="left" data-html="true" data-content="<div style=\'height:520px;\'><table class=\'popdetailtable layui-table\'><tbody>'+detailhtml+'</tbody></table></div>"><i class="fa fa-ellipsis-h "></i></button> ';
							var c = '<a class="btn btn-warning btn-sm ' + s_check_h + '" href="#" title="审核"  mce_href="#" onclick="check(\''
								+ row.id
								+ '\')"><i class="fa fa-check"></i></a> ';
							if(row.auditStatus==-1 || row.auditStatus==null)
								return d + c;
							else
								return d;
						}
					} ]
			});
}
function reLoad() {
	$('#exampleTable').bootstrapTable('refresh');
}
//function detail(id) {
//	layer.open({
//		type : 2,
//		title : '详情',
//		maxmin : true,
//		shadeClose : false, 
//		area : [ '800px', '520px' ],
//		content : prefix + '/detail/'+id
//	});
//}
function check(id) {
	layer.open({
		type : 2,
		title : '审核',
		maxmin : true,
		shadeClose : false, 
		area : [ '800px', '550px' ],
		content : prefix + '/check/'+id
	});
}
function excel(){
	layer.confirm('确定导出吗?', {
		  btn: ['是','否']
	}, function(){
		layer.closeAll('dialog');
		window.location.href= prefix + '/expexcel?searchvalue=' + $('#searchName').val() + '&settletype=' + $('#settletype').val() + '&checktype=' + $('#checktype').val() + '&transstatus=' + $('#transstatus').val() + '&datetype=' + $('#datetype').val() + '&sdate=' + $('#sdate').val() + '&edate=' + $('#edate').val();
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