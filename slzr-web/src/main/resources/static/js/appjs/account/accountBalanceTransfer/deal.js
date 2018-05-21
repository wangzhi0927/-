$().ready(function() {
	validateRule();
});

$.validator.setDefaults({
	submitHandler : function() {
		update();
	}
});
function update() {
	var accountBalance=Number($('#accountBalance').val())
	var serviceFee=Number($('#serviceFee').val())
	var transferAmount=Number($('#transferAmount').val()) 
	
	var t = transferAmount+serviceFee;
	var s = $('#auditResult').val();
	
	if(t>accountBalance){	
		/*layer.alert("此账户余额不够，审核不能通过！", {icon: 2});*/
		layer.alert("转赠金额需小于账号余额减去服务费", {icon: 2});
		return;
	}
	$.ajax({
		cache : true,
		type : "POST",
		url : "/account/accountBalanceTransfer/update",
		data : $('#signupForm').serialize(),// 你的formid
		async : false,
		error : function(request) {
			parent.layer.alert("Connection error");
		},
		success : function(data) {
			if (data.code == 0) {
				parent.layer.msg(data.msg);
				parent.reLoad();
				var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
				parent.layer.close(index);

			} else {
				parent.layer.alert(data.msg)
			}

		}
	});

}

function validateRule() {
	var icon = "<i class='fa fa-times-circle'></i> ";
	$("#signupForm").validate({
		rules : {
			transferAmount : {
				required : true,
				min:0.1,
				maxlength:5
			},
			serviceFee : {
				required : true,
				min:0,
				maxlength:5
			},
			auditMsg : {
				required : true,
				maxlength:10
			},
			
			agree : "required"
		},
		messages : {
			transferAmount : {
				required : icon + "转赠金额不能为空",
				min : icon + "转赠金额必须是数字且不能小于0.1",
				maxlength : icon + "转赠金额不能大于5位"
			},
			serviceFee : {
				required : icon + "请输入服务费",
				min : icon + "服务费必须是数字且不能小于0",
				maxlength : icon + "服务费不能大于5位"
			},
			auditMsg : {
				required : icon + "请输入审核描述",
				maxlength : icon + "审核描述最多10个字符"
			}
		}
	})
}