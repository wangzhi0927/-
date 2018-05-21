$().ready(function() {
	validateRule();
});

$.validator.setDefaults({
	submitHandler : function() {
		update();
	}
});
function update() {
	var abalance=Number($('#abalance').val())
	var servicefee=Number($('#servicefee').val())
	
	if(abalance<servicefee && $('#dealresult').val()==1){	
		layer.alert("此账户余额不够，不能选择可注销！", {icon: 2});
		return;
	}
	$.ajax({
		cache : true,
		type : "POST",
		url : "/account/accountCancel/update",
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
function fillB(){
	var abalance=document.getElementById("abalance").value
	abalance = new BigDecimal(abalance);
    var servicefee=document.getElementById("servicefee").value
    if(!isNaN(servicefee)){
    	
    	servicefee = new BigDecimal(servicefee);
        var total= abalance.subtract(servicefee);
        document.getElementById("balance").value=total;
    }/*else{
    	servicefee = new BigDecimal(0.0);
    }*/
    

}
function validateRule() {
	var icon = "<i class='fa fa-times-circle'></i> ";
	$("#signupForm").validate({
		rules : {
			servicefee : {
				required : true,
				min:2,
				maxlength:3
			},
			dealmsg : {
				required : true,
				maxlength:10
			},
			
			agree : "required"
		},
		messages : {

			servicefee : {
				required : icon + "请输入服务费",
				min : icon + "服务费必须是数字且不能小于2",
				maxlength : icon + "服务费不能大于3位数"
			},
			dealmsg : {
				required : icon + "请输入审核描述",
				maxlength : icon + "审核描述最多10个字符"
			}
		}
	})
}