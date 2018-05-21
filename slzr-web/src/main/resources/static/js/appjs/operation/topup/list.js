var prefix = "/operation/topup"
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
						auditRemark : $('#searchName').val(),
						sdate : $('#sdate').val(),
						edate : $('#edate').val(),
						rechargetype:$('#rechargetype').val(),
                        //:$('#merchantCode').val(),
						checktype:$('#checktype').val(),
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
						field : 'summaryDateTimeText',
						title : '日结日期',
						align:'center'
					},
					{
						field : 'payMethodId',
						title : '充值方式',
						formatter : function(value, row, index) {
							if(value==0)
								return "微信";
							if(value==1)
								return "支付宝";
						}
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
						field : 'enterAmount',
						title : '入账金额',
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
						field : 'serviceFee',
						title : '服务费',
						align:'right',
						formatter : function(value, row, index) {
							return value.toFixed(2);
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
						field : 'auditDateTime',
						title : '审核时间',
						align:'center'
					},
					{
						title : '操作',
						field : 'id',
						align : 'center',
						formatter : function(value, row, index) {
							var d = '<a  class="btn btn-warning btn-sm ' + s_do_h + '" href="#" mce_href="#" title="重新日结" onclick="againdo('
								+ row.iD
								+ ')"><i class="fa fa-retweet "></i></a> ';
							var c = '<a class="btn btn-warning btn-sm ' + s_check_h + '" href="#" title="审核"  mce_href="#" onclick="check('
								+ row.iD
								+ ')"><i class="fa fa-check"></i></a> ';
							if(!s_do_h && !s_check_h)
							{
								return c + d;
							}else
							{
								if(row.auditStatus==-1)
									return c;
								else
									return d;
							}
						}
					} ]
			});
}
function reLoad() {
	$('#exampleTable').bootstrapTable('refresh');
}
function againdo(id) {
	layer.confirm('您确认要对指定日期的充值交易进行重新日结吗？该操作将删除旧的日结数据！将耗费较多时间,请勿在高峰时期执行！', {
		  btn: ['是','否']
	}, function(){
	    $.ajax({
	      cache : true,
	      type : "POST",
	      url : "/operation/topup/api/again",
	      data : {
	      	id:id
	      },
	      async : false,
	      error : function(request) {
	          layer.alert("Connection error");
	      },
	      success : function(data) {
	          if (data.code == 0) {
	              layer.msg("操作成功");
	              reLoad();
	              var index = layer.getFrameIndex(window.name); 
	              layer.close(index);
	
	          } else {
	              layer.alert(data.msg);
	          }
	
	      }
	  });
	}, function(){
	});
}
function check(id) {
	layer.open({
		type : 2,
		title : '审核',
		maxmin : true,
		shadeClose : false, 
		area : [ '800px', '350px' ],
		content : prefix + '/check/'+id
	});
}
function excel(){
	layer.confirm('确定导出吗?', {
		  btn: ['是','否']
	}, function(){
		layer.closeAll('dialog');
		window.location.href= prefix + '/expexcel?auditRemark=' + $('#searchName').val() + '&sdate=' + $('#sdate').val() + '&edate=' + $('#edate').val() + '&rechargetype=' + $('#rechargetype').val() + '&checktype=' + $('#checktype').val();
	}, function(){
	});
}