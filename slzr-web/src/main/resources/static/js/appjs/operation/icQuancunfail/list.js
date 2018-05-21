
var prefix = "/operation/icQuancunfail"
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
								searchName:$('#searchName').val()
					           // name:$('#searchName').val(),
					           // username:$('#searchName').val()
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
										field : 'cardno', 
										align : 'center',
										title : '卡号' 
									},	
									{
										field : 'orderno', 
										align : 'center',
										title : '订单号' 
									},

									{
										field : 'txnamount', 										
										title : '交易金额',
										align:'right',
										formatter : function(value, row, index) {
											return value.toFixed(2);
										}
									},	
									{
										field : 'balance', 
										title : '余额',
										align:'right',
										formatter : function(value, row, index) {
											return value.toFixed(2);
										}
									},
								
									{
										field : 'loadstate', 
										align : 'left',
										title : '状态' ,
										formatter: function(value){
											if(value=='4'){
												return '锁定';
											}else if(value=='5'){
												return '圈存失败';
											}else if(value=='6'){
												return '圈存成功';
											}else if(value=='7'){
												return '已退款';
											}else if(value=='1'){
												return '未支付';
											}else if(value=='2'){
												return '支付成功';
											}else if(value=='3'){
												return '支付失败';
											}
										}
									},
									{
										field : 'loaddescribe', 
										align : 'left',
										title : '描述' 
									},
									{
										field : 'loadbackdate', 
										align : 'center',
										title : '交易时间' 
									},
										
									{
										field : 'terminalno', 
										align : 'center',
										title : '自助终端编号' 
									},
							
									{
									title : '操作',
									field : 'id',
									align : 'center',
									formatter : function(value, row, index) {
									/*	var e = '<a class="btn btn-primary btn-sm '+s_edit_h+'" href="#" mce_href="#" title="编辑" onclick="edit(\''
												+ row.id
												+ '\')"><i class="fa fa-edit"></i></a> ';*/
										var s = '<button class="layui-btn layui-btn-sm'+s_g_h+'" type="button" onclick="edit(\''+ row.id	
										+ '\')">审核</button> ';											
										
										return s ;
								
									}
								} ]
					});
}
function reLoad() {
	$('#exampleTable').bootstrapTable('refresh');
}

function edit(id) {
	layer.open({
		type : 2,
		title : '编辑',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '800px', '520px' ],
		content : prefix + '/edit/' + id // iframe的url
	});
}

function excel(){
	var searchName = $("#searchName").val();
	
	layer.confirm('您确定导出吗？', {
		  btn: ['是','否']
	}, function(){
		window.location.href= prefix + '/expexcel?searchName=' + searchName;
		layer.closeAll('dialog');
	}, function(){
	});
}

