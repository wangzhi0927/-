
$().ready(function() {
    validateRule();
});

$.validator.setDefaults({
    submitHandler : function() {
        save();
    }
});

function validateRule() {
    var icon = "<i class='fa fa-times-circle'></i> ";
    $("#form").validate({
        rules : {
            busCode : {
                required : true,
                maxlength: 5

            },
            lineID : {
		        required : true
		    },
            terminalNum : {
		        required : true,
                number : true
		    },
            state : {
                required : true
            }
        },
        messages : {
            busCode : {
                required : icon + "请填写车辆代码",
                maxlength : icon + "输入长度不能超过5位数"

            },
            lineID : {
	            required : icon + "请填写线路"
	        },
            terminalNum : {
	            required : icon + "请填写终端数",
                number :icon +"必须为数字"
	        },
            state : {
                required : icon + "请选择状态"
            }
        }
    });
}

function save() {
	// var layedit = layui.layedit;
	// var newscontent=layedit.getContent(buildindex);
	// if(!newscontent)
	// {
	// 	layer.alert('请填写正文内容');
	// }


    $.ajax({
        cache : true,
        type : "POST",
        url : "/operation/optbus/api/edit",
        data : {
        	id:$("#id").val(),
            deptName:$("#deptName").val(),
            busCode:$("#busCode").val(),
            busNO:$("#busNO").val(),
            busModel:$("#busModel").val(),
            busPlateNO:$("#busPlateNO").val(),
            runCardNO:$("#runCardNO").val(),
            lineName:$("#lineName").val(),
            terminalNum:$("#terminalNum").val(),
            factory:$("#factory").val(),
            buyDate:$("#buyDate").val(),
            state:$("#state").val(),
            remark:$("#remark").val(),
            lineID:$("#lineID").val(),
            deptCode:$("#deptCode").val()


        },
        async : false,
        error : function(request) {
            parent.layer.alert("Connection error");
        },
        success : function(data) {
            if (data.code == 0) {
                parent.layer.msg("操作成功");
                parent.reLoad();
                var index = parent.layer.getFrameIndex(window.name); 
                parent.layer.close(index);

            } else {
                parent.layer.alert(data.msg)
            }

        }
    });
}
