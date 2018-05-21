
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
            lineCode : {
                required : true,
                maxlength: 5

            },
            lineName : {
		        required : true
		    },
            state : {
		        required : true
		    },
            ticketPrice : {
                required : false,
                number : true
            },
            attachFee : {
                required : false,
                number : true,
            },
            busNum : {
                required : false,
                number : true,
            },
            stationNum : {
                required : false,
                number : true,
            },
            distance : {
                required : false,
                number : true,
            }
        },
        messages : {
            lineCode : {
                required : icon + "线路代码不能为空",
                maxlength : icon + "输入长度不能超过5位数"

            },
            lineName : {
	            required : icon + "线路名称不能为空"
	        },
            state : {
	            required : icon + "状态不能为空"
	        },
            ticketPrice : {
                required : icon + "票价不能为空",
                number : icon + "必须为数字"
            },
            attachFee : {
                required : icon + "附加费不能为空",
                number : icon + "必须为数字"
            },
            busNum : {
                required : icon + "车辆数不能为空",
                number : icon + "必须为数字"
            },
            stationNum : {
                required : icon + "站点数不能为空",
                number : icon + "必须为数字"
            },
            distance : {
                required : icon + "里程数不能为空",
                number : icon + "必须为数字"
            }
        }
    });
}

function save() {
    $.ajax({
        cache : true,
        type : "POST",
        url : "/operation/optline/api/edit",
        data : {
            id:$("#id").val(),
            lineCode:$("#lineCode").val(),
            lineName:$("#lineName").val(),
            state:$("#state").val(),
            ticketPrice:$("#ticketPrice").val(),
            attachFee:$("#attachFee").val(),
            busNum:$("#busNum").val(),
            masterLineID:$("#masterLineID").val(),
            stationNum:$("#stationNum").val(),
            distance:$("#distance").val(),
            deptID:$("#deptID").val()

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
