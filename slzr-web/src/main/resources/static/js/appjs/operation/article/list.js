var prefix = "/operation/article"
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
                        merchantCode:$('#merchantCode').val(),
                        type:$('#arttype').val(),
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
						field : 'id',
						title : '文章编号',
						align:'center'
					},
					{
						field : 'articleType',
						title : '文章类别',
						align:'left',
						formatter : function(value, row, index) {
							if(value==1)
								return "公告";
							if(value==2)
								return "失物招领";
                            if(value==3)
                                return "联系我们";
						}
					},
					{
						field : 'title',
						title : '文章标题',
						align:'left'
					},
					{
						field : 'abstracts',
						title : '文章摘要',
						align:'left'
					},
                    {
                        field : 'createdDate',
                        title : '创建时间',
						align:'center'
                    },
                    {
                        title: '操作',
                        field: 'isresolved',
                        align: 'center',
                        formatter: function (value, row, index) {
                            if (row.isresolved == 0) {
                                // var e = '<a  class="btn btn-primary btn-sm '  + '" href="#" mce_href="#" title="回复" onclick="edit(\'' +  row.mid + '\' , \'' +  row.gid + '\' ,  \'' +row.nickname +'\' ,\''+row.msgcontent+'\', \''+row.mobilephone+'\',  \''+row.rid+'\',  \''+row.replycontent+'\')"><i class="fa fa-edit "></i></a> ';
                                // return e;

                                var e = '<a  class="btn btn-primary btn-sm ' + '" href="#" mce_href="#" title="编辑" onclick="edit(\'' + row.id + '\')"><i class="fa fa-edit "></i></a> ';
                                var d = '<a class="btn btn-warning btn-sm ' + '" href="#" title="删除"  mce_href="#" onclick="deleteart(\''
                                    + row.id
                                    + '\' )"><i class="fa fa-remove"></i></a> ';
                                return e + d;
                            } else {
                                var e = '<a  class="btn btn-primary btn-sm ' + '" href="#" mce_href="#" title="编辑" onclick="edit(\'' + row.id + '\')"><i class="fa fa-edit "></i></a> ';
                                var d = '<a class="btn btn-warning btn-sm ' + '" href="#" title="删除"  mce_href="#" onclick="deleteart(\''
                                    + row.id
                                    + '\' )"><i class="fa fa-remove"></i></a> ';
                                return e + d;
                            }
                        }
                    }]
			});
}
function reLoad() {
	$('#exampleTable').bootstrapTable('refresh');
}

function deleteart(id) {
    layer.confirm('确定要删除选中的记录？', {
        btn : [ '确定', '取消' ]
    }, function() {
        $.ajax({
            url : prefix +"/deleteart",
            type : "post",
            data : {
                'id' : id
            },
            success : function(r) {
                if (r.code == 0) {
                    layer.msg(r.msg);
                    reLoad();
                    load(deptId);
                } else {
                    layer.msg(r.msg);
                }
            }
        });
    })

}

function edit(id) {
	layer.open({
		type : 2,
		title : '编辑',
		maxmin : true,
		shadeClose : false, 
		area : [ '800px', '520px' ],
		content : prefix + '/edit/'+id
	});
}

function add(){
	layer.open({
		type : 2,
		title : '添加',
		maxmin : true,
		shadeClose : false, 
		area : [ '800px', '520px' ],
		content : prefix + '/add'
	});
}