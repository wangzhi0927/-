
var prefix = "/account/account"
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
                                merchantCode:$('#merchantCode').val(),
								state:$('#state').val()
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
									title : '账户类型' 
								},
																{
									field : 'stateName', 
									align : 'left',
									title : '账户状态' 
								},
																{
									field : 'mobilephone', 
									align : 'center',
									title : '手机号码' 
								},
																{
									field : 'createddate', 
									align : 'center',
									title : '注册时间' 
								},
																{
									field : 'balance', 									
									title : '账户余额',
									align:'right',
									formatter : function(value, row, index) {
										if(value) {
                                            return value.toFixed(2);
										}else {
                                            return '-';
										}
									}
								},
                            								  {
									field : 'freebalance',
									title : '赠送余额',
									align:'right',
									formatter : function(value, row, index) {
                                        if(value) {
                                            return value.toFixed(2);
                                        }else {
                                            return '-';
                                        }
                               		 }
                           		 },
																{
									field : 'lastdebittime', 
									title : '最后消费时间',
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
									field : 'lastdebitamount', 
									title : '最后消费金额',
									align:'right',
									formatter : function(value, row, index) {
										return value.toFixed(2);
									}
								},
																{
									field : 'certphotourl', 
									align : 'center',
									title : '身份证正面照' ,
									formatter : function(value, row, index) {
										var imgurl;
							
										row.certphotourl==""? imgurl="../img/null.jpg" : imgurl=row.certphotourl;
										
									var e = '<a href="#" mce_href="#" onclick="showimg(\''
										+ imgurl
										+ '\')">点击查看</a> ';
									return e;
									}
								},
																{
									field : 'personphotourl', 
									align : 'center',
									title : '个人照', 
									formatter : function(value, row, index) {
										var imgurl;
										
										row.certphotourl==""? imgurl="../img/null.jpg" : imgurl=row.certphotourl;
										
										var p = '<a href="#" mce_href="#" onclick="showimg(\''
											+ imgurl
											+ '\')">点击查看</a> ';
										return p;
										}
								},
																{
									title : '操作',
									field : 'dealresult',
									align : 'center',
									formatter : function(value, row, index) {
										/*	var e = '<a class="btn btn-primary btn-sm '+s_deal_h+'" href="#" mce_href="#" title="审核" onclick="deal(\''
													+ row.id
													+ '\')"><i class="fa fa-edit"></i></a> ';*/
										
										if(row.stateName=='审核通过'){
										/*	var z = '<button class="layui-btn layui-btn-sm'+s_g_h+'" type="button" onclick="operation(\''+ row.accountno													
											+ '\',-3)">注销</button> ';*/
											var g = '<button class="layui-btn layui-btn-sm'+s_g_h+'" type="button" onclick="operation(\''+ row.accountno													
											+ '\',-1)">挂失</button> ';
											return g ;
										}
										if(row.stateName=='锁定'){
											var j = '<button class="layui-btn layui-btn-sm'+s_g_h+'" type="button" onclick="operation(\''+ row.accountno													
											+ '\',1)">解锁</button> ';	
											return j ;
										}
										if(row.stateName=='待审核'){
											var s = '<button class="layui-btn layui-btn-sm'+s_g_h+'" type="button" onclick="operation(\''+ row.accountno	
											+ '\',11)">审核</button> ';												
											
											return s ;
										}
							/*			if(row.stateName=='注销'){
											var s = '<button class="layui-btn layui-btn-sm'+s_g_h+'" type="button" onclick="operation(\''+ row.accountno	
											+ '\',1)">取消注销</button> ';												
											
											return s ;
										}*/
									}
								} ]
					});
}
function reLoad() {
	$('#exampleTable').bootstrapTable('refresh');
}



function excel(){
	var searchName = $("#searchName").val();
	var astate = $("#state").val();
	
	layer.confirm('您确定导出吗？', {
		  btn: ['是','否']
	}, function(){
		window.location.href= prefix + '/expexcel?searchName=' + searchName + '&state=' + astate;
		layer.closeAll('dialog');
	}, function(){
	});
}

function showimg(url) {
    layer.open({
        type: 1,
        title: false, //不显示标题
        //skin: 'layui-layer-rim', //加上边框
        area: ['640px', '435px'], //宽高
        content: '<div style="padding:0px 0px;"><img src="'+ url + '"/></div>'
    });

}

function operation(accountno,operationType) {
	/*alert(operationType);*/
	if(operationType==-3){
		layer.confirm('您确认要注销该账号吗？', {
			btn : [ '确定', '取消' ]
		}, function() {
			$.ajax({
				url : "/account/account/operation",
				type : "post",
				data : {
					'accountno' : accountno,
					'operationType' : "-3"
				},
				success : function(r) {
					if (r.code == 0) {
						layer.msg(r.msg);
						reLoad();
					} else {
						layer.msg(r.msg);
					}
				}
			});
		})
	}
	if(operationType==-1){
		layer.confirm('您将对该账号进行挂失？', {
			btn : [ '确定', '取消' ]
		}, function() {
			$.ajax({
				url : "/account/account/operation",
				type : "post",
				data : {
					'accountno' : accountno,
					'operationType' : "-1"
				},
				success : function(r) {
					if (r.code == 0) {
						layer.msg(r.msg);
						reLoad();
					} else {
						layer.msg(r.msg);
					}
				}
			});
		})
	}
	if(operationType==1){
		layer.confirm('您将解锁该账号？', {
			btn : [ '确定', '取消' ]
		}, function() {
			$.ajax({
				url : "/account/account/operation",
				type : "post",
				data : {
					'accountno' : accountno,
					'operationType' : "1"
				},
				success : function(r) {
					if (r.code == 0) {
						layer.msg(r.msg);
						reLoad();
					} else {
						layer.msg(r.msg);
					}
				}
			});
		})
	}
	if(operationType==11){
		layer.confirm('确认通过审核？', {
			btn : [ '确定', '取消' ]
		}, function() {
			$.ajax({
				url : "/account/account/operation",
				type : "post",
				data : {
					'accountno' : accountno,
					'operationType' : "1"
				},
				success : function(r) {
					if (r.code == 0) {
						layer.msg(r.msg);
						reLoad();
					} else {
						layer.msg(r.msg);
					}
				}
			});
		})
	}
}



