$().ready(function() {
    validateRule();
});

$.validator.setDefaults({
    submitHandler : function() {
        save();
    }
});
function save() {
    $.ajax({
        cache : true,
        type : "POST",
        url : "/operate/operateSet/update",
        data : $('#signupForm').serialize(),// 你的formid
        async : false,
        error : function(request) {
            parent.layer.alert("Connection error");
        },
        success : function(data) {
            if (data.code == 0) {
                parent.layer.msg("操作成功");
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
            CityCode : {
                required : true
            },
            TradeCode : {
                required : true
            },
            CompanyCode : {
                required : true
            },
            MinTopupAmount : {
                required : true,
                number : true
            },
            MinBalance : {
                required : true,
                number : true
            },
            MaxDebitAmount : {
                required : true,
                number : true
            },
            KeyMakePeriod : {
                required : true
            },
            DataSavePath : {
                required : true
            },
            FileUploadPath : {
                required : true
            },
            topic : {
                required : "#newsletter:checked",
                minlength : 2
            },
            agree : "required"
        },
        messages : {
            CityCode : {
                required : icon + "请输入城市代码"
            },
            TradeCode : {
                required : icon + "请输入行业代码"
            },
            CompanyCode : {
                required : icon + "请输入机构代码"
            },
            MinTopupAmount : {
                required : icon + "请输入最小充值金额",
                number :icon +"请输入数字格式"
            },
            MinBalance : {
                required : icon + "请输入账号最低余额",
                number :icon +"请输入数字格式"
            },
            MaxDebitAmount : {
                required : icon + "请输入单次最大消费金额",
                number :icon +"请输入数字格式"
            },
            KeyMakePeriod : {
                required : icon + "请输入秘钥生成周期"
            },
            DataSavePath : {
                required : icon + "请输入数据存放路径"
            },
            FileUploadPath : {
                required : icon + "请输入文件上传目录"
            }


        }
    })
}
