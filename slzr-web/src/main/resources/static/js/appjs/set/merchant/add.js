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
        url : "/Merchant/merchantSet/save",
        data : $('#signupForm').serialize(),// 你的formid
        async : false,
        error : function(request) {
            parent.layer.alert("Connection error");
        },
        success : function(data) {
            if (data.code == 0) {
                parent.layer.msg("操作成功");
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
            name : {
                required : true
            },
            merchantCode : {
                required : true,
                maxlength : 6,
                remote : {
                    url : "/Merchant/merchantSet/exit", // 后台处理程序
                    type : "post", // 数据发送方式
                    dataType : "json", // 接受数据格式
                    data : { // 要传递的数据
                        merchantCode : function() {
                            return $("#merchantCode").val();
                        }
                    }
                }
            },
            mapMerchantCode : {
                required : true,
                maxlength : 6,
                remote : {
                    url : "/Merchant/merchantSet/exit", // 后台处理程序
                    type : "post", // 数据发送方式
                    dataType : "json", // 接受数据格式
                    data : { // 要传递的数据
                        mapMerchantCode : function() {
                            return $("#mapMerchantCode").val();
                        }
                    }
                }
            },
            merchantName : {
                required : true,
                minlength : 2,
                remote : {
                    url : "/Merchant/merchantSet/exit", // 后台处理程序
                    type : "post", // 数据发送方式
                    dataType : "json", // 接受数据格式
                    data : { // 要传递的数据
                        merchantName : function() {
                            return $("#merchantName").val();
                        }
                    }
                }
            },
            shortName : {
                required : true,
                minlength : 2,
                remote : {
                    url : "/Merchant/merchantSet/exit", // 后台处理程序
                    type : "post", // 数据发送方式
                    dataType : "json", // 接受数据格式
                    data : { // 要传递的数据
                        shortName : function() {
                            return $("#shortName").val();
                        }
                    }
                }
            },
            cityCode : {
                required : true,
                remote : {
                    url : "/Merchant/merchantSet/check", // 后台处理程序
                    type : "post", // 数据发送方式
                    dataType : "json", // 接受数据格式
                    data : { // 要传递的数据
                        cityCode : function() {
                            return $("#cityCode").val();
                        }
                    }
                }
            },
            settleMethod : {
                required : true,
            },
            settlePeriod  :{
                required : true,
            },
            settlePeriodUnit :{
                required : true,
            },
            settleDay :{
                required : true,
            },
            settleRate :{
                range : [0,99.99],
                required : true,
                number : true,
            },
            serviceFee :{
                required : true,
                digits:true
            },
            topic : {
                required : "#newsletter:checked",
                minlength : 2
            },
            agree : "required"
        },
        messages : {

            cityCode : {
                required : icon + "请输入城市代码",
                remote :icon + "请输入六个以内的字母或者数字"
            },
            merchantCode : {
                required : icon + "请输入商户代码",
                maxlength : icon + "请输入六个以内的字母或者数字",
                remote : icon + "商户代码已经存在"
            },
            mapMerchantCode : {
                required : icon + "请输入映射商户代码",
                maxlength : icon + "请输入六个以内的字母或者数字",
                remote : icon + "映射商户代码已经存在"
            },
            merchantName : {
                required : icon + "请输入商户名称",
                minlength : icon + "商户名称必须两个字符以上",
                remote : icon + "商户名称已经存在"
            },
            shortName : {
                required : icon + "请输入商户简称",
                minlength : icon + "商户简称必须两个字符以上",
                remote : icon + "商户简称已经存在"
            },
            settleMethod : {
                required : icon + "请选择结算方式",
            },
            settlePeriod  :{
                required : icon + "请选择结算周期",
            },
            settlePeriodUnit :{
                required : icon + "请选择结算单位",
            },
            settleDay  :{
                required : icon + "请选择结算日",
            },
            settleRate   :{
                range : icon+"输入值必须介于 0 到 99.99 之间",
                required : icon + "请输入结算费率",
                number :icon +"请输入数字格式"
            },
            serviceFee   :{
                digits :icon +"请输入整数（元）",
                required : icon + "请输入服务费"
            }
        }
    })
}
