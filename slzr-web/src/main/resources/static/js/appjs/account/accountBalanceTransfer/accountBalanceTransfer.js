
var prefix = "/account/accountBalanceTransfer"
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
								auditResult:$('#auditResult').val(),
					       
								merchantCode:$('#merchantCode').val(),
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
									field : 'fromAccountNo', 
									align : 'center',
									title : '转赠账户' 
								},
																{
									field : 'toAccountNo', 
									align : 'center',
									title : '受赠账户' 
								},
																{
									field : 'applyNickName', 
									align : 'center',
									title : '申请人' 
								},
																{
									field : 'applyTime', 
									align : 'center',
									title : '申请时间' 
								},
																{
									field : 'accountBalance', 
									align : 'right',
									title : '账户余额' 
								},
																{
									field : 'transferAmount', 
									align : 'right',
									title : '转赠金额' 
								},
																{
									field : 'serviceFee', 
									align : 'right',
									title : '服务费' 
								},
																{
									field : 'auditUserName', 
									align : 'center',
									title : '审核人' 
								},
																{
									field : 'auditResult', 
									align : 'center',
									title : '审核状态',
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
									field : 'auditDateTime', 
									align : 'center',
									title : '审核时间' 
								},
																{
									field : 'auditMsg', 
									align : 'center',
									title : '审核描述' 
								},
																{
									field : 'userConfirmResult', 
									align : 'center',
									title : '确认结果',
									formatter: function(value){
										if(value==1){
											return '<span class="label label-success">已确认</span>';
										}
									}
								},
																{
									field : 'userConfirmDateTime', 
									align : 'center',
									title : '确认时间' 
								},									
																{
									title : '操作',
									field : 'id',
									align : 'center',
									formatter : function(value, row, index) {

										
										if(row.auditResult!=1 || row.userConfirmResult!=1){
											var e = '<button class="layui-btn layui-btn-sm'+s_deal_h+'" type="button" onclick="deal(\''+ row.id													
											+ '\')">审核</button> ';	
											return e ;
										}
									}
								} ]
					});
}
function reLoad() {
	$('#exampleTable').bootstrapTable('refresh');
}

function deal(id) {
	layer.open({
		type : 2,
		title : '转赠审核',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '800px', '560px' ],
		content : prefix + '/deal/' + id // iframe的url
	});
}

function excel(){
	var searchName = $("#searchName").val();
	var auditResult = $("#auditResult").val();
	var merchantCode = $("#merchantCode").val();
	
	layer.confirm('您确定导出吗？', {
		  btn: ['是','否']
	}, function(){
		window.location.href= prefix + '/expexcel?searchName=' + searchName + '&auditResult=' + auditResult + '&merchantCode=' + merchantCode;
		layer.closeAll('dialog');
	}, function(){
	});
}

