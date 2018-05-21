
var prefix = "/account/accountCancel"
$(function() {
	load();
});

function load() {
	$('#exampleTable')
			.bootstrapTable(
					{
						method : 'get', // 服务器数据的请求方式 get or post
						url : prefix + "/list", // 服务器数据的加载地址
					//	showRefresh : true,
					//	showToggle : true,
					//	showColumns : true,
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
						//search : true, // 是否显示搜索框
						showColumns : false, // 是否显示内容下拉框（选择显示的列）
						sidePagination : "server", // 设置在哪里进行分页，可选值为"client" 或者 "server"
						queryParams : function(params) {
							return {
								//说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
								limit: params.limit,
								offset:params.offset,
								searchName:$('#searchName').val(),
								dealresult:$('#dealresult').val(),
					            refundresult:$('#refundresult').val(),
                                merchantCode:$('#merchantCode').val(),
					            isday:$("input[type='checkbox']").is(':checked')
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
									field : 'accountno', 
									align : 'center',
									title : '账号' 
								},
																{
									field : 'accountTypeName', 
									align : 'center',
									title : '类型' 
								},
																{
									field : 'stateName', 
									align : 'left',
									title : '状态' 
								},
																{
									field : 'mobilephone', 
									align : 'center',
									title : '手机号码' 
								},
																{
									field : 'submittime', 
									align : 'center',
									title : '申请时间' 
								},
																{
									field : 'abalance', 
									title : '账户余额',
									align:'right',
									formatter : function(value, row, index) {
										if(value!=null){
											return value.toFixed(2);
										}
									}
								},
																{
									field : 'cbalance', 
									align : 'center',
									title : '可退金额',
									align:'right',
									formatter : function(value, row, index) {
										if(value!=null){
											return value.toFixed(2);
										}
									}
								},
																{
									field : 'servicefee', 
									title : '服务费',
									align:'right',
									formatter : function(value, row, index) {
										if(value!=null){
											return value.toFixed(2);
										}
									}
								},
																{
									field : 'refundtime', 
									title : '退款时间',
									align : 'center',
									formatter: function(value){
										if(value=='1900-01-01 00:00:00'){
											return '-';
										}else{
											return value;
										}
									}
								},
																{
									field : 'dealresult', 
									title : '审核状态',
									align : 'center',
									formatter: function(value){
										if(value==0){
											return '<span class="label label-info">审核不通过</span>';
										}else if(value==1){
											return '<span class="label label-success">审核通过</span>';
										}
										else if(value==-1 || value==null){
											return '<span class="label label-danger">未审核</span>';
										}
										/*else{
											return '-';
										}	*/
									}
								},
																{
									field : 'dealtime', 
									title : '审核时间',
									align : 'center',
									formatter: function(value){
										if(value=='1900-01-01 00:00:00'){
											return '-';
										}else{
											return value;
										}
									}
								},
																{
									title : '操作',
									field : 'dealresult',
									align : 'center',
									formatter : function(value, row, index) {
											/*var e = '<a class="btn btn-primary btn-sm '+s_deal_h+'" href="#" mce_href="#" title="审核" onclick="deal(\''
													+ row.id
													+ '\')"><i class="fa fa-edit"></i></a> ';*/
										if(row.stateName!="注销"){
											var b = '<button class="layui-btn layui-btn-sm'+s_deal_h+'" type="button" onclick="deal(\''+ row.id													
													+ '\')">审核</button> ';										
											return b ;
										}
									}
								} ]
					});
}
function reLoad() {
	$('#exampleTable').bootstrapTable('refresh');
}



function excel(){
	var searchName = $("#searchName").val();
	var dealresult = $("#dealresult").val();
	var refundresult = $("#refundresult").val();
	
	layer.confirm('您确定导出吗？', {
		  btn: ['是','否']
	}, function(){
		window.location.href= prefix + '/expexcel?searchName=' + searchName + '&dealresult=' + dealresult + '&refundresult=' + refundresult;
		layer.closeAll('dialog');
	}, function(){
	});
}

function deal(id) {
	layer.open({
		type : 2,
		title : '账户注销审核',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '800px', '560px' ],
		content : prefix + '/deal/' + id // iframe的url
	});
}

