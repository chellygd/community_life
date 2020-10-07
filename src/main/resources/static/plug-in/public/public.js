
//form_load加载数据
function loadFormData(jsonStr){
	try {
    	var obj = eval("("+jsonStr+")");
	} catch (e) {
		obj = jsonStr;
	}
    var key,value,tagName,type,arr;
    for(x in obj){
        key = x;
        value = obj[x];
        $("[name='"+key+"'],[name='"+key+"[]']").each(function(){
            tagName = $(this)[0].tagName;
            type = $(this).attr('type');
            if(tagName=='INPUT'){
                if(type=='radio'){
                    $(this).attr('checked',$(this).val()==value);
                }else if(type=='checkbox'){
                    arr = value.split(',');
                    for(var i =0;i<arr.length;i++){
                        if($(this).val()==arr[i]){
                            $(this).attr('checked',true);
                            break;
                        }
                    }
                }else{
                    $(this).val(value);
                }
            }else if(tagName=='SELECT' || tagName=='TEXTAREA'){
                $(this).val(value);
            }
        });
    }
	
}

//判断变量（字符串）是否为空或null
function isObjEmpty(obj){
	if(obj==null||obj==undefined||obj==""||obj=="null"||obj=="undefinet"){
		return true;
	}else{
		return false;
	}
}
function isObjNotEmpty(obj){
	return !isObjEmpty(obj);
}

//时间格式化  new Date(mydate).Format("yyyy-MM-dd")
Date.prototype.Format = function (fmt) {
	if (!fmt) {
		fmt = "yyyy-MM-dd HH:mm:ss";
	}
	var o = {
			"M+" : this.getMonth() + 1, // 月份
			"d+" : this.getDate(), // 日
			"h+" : this.getHours() % 12 == 0 ? 12 : this.getHours() % 12, //小时
			"H+" : this.getHours(), // 小时
			"m+" : this.getMinutes(), // 分
			"s+" : this.getSeconds(), // 秒
			"q+" : Math.floor((this.getMonth() + 3) / 3), // 季度
			"S" : this.getMilliseconds()
			// 毫秒
	};
	if (/(y+)/.test(fmt))
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	for ( var k in o)
		if (new RegExp("(" + k + ")").test(fmt))
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
					: (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
};
//当前时间，加几天
Date.prototype.addDays = function(number) {
	var dayTime = number * (24 * 60 * 60 * 1000); //参数天数的时间戳
    var nowTime = this.getTime(); //当天的时间戳
    return new Date(nowTime + dayTime);
}
//获取当前“星期几”
Date.prototype.getWeekStr = function(leftStr){
	if (!leftStr) {
		leftStr = "星期";//星期/周
	}
	var weekArr = new Array(leftStr+'日',leftStr+'一',leftStr+'二',leftStr+'三',leftStr+'四',leftStr+'五',leftStr+'六');
	return weekArr[this.getDay()];
}


//验证身份证号
function checkIdcard(value){
	var isIDCard1=/^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$/;//(15位)
	var isIDCard2=/^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|X)$/;//(18位)
	
	var idcard_boolean = false;//精确验证身份证号，计算验证码，防止随便修改身份证号
	if(isIDCard2.test(value)){
		if (value.length == 18) {
			var idCardWi = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2);
			//将前17位加权因子保存在数组里
			var idCardY = new Array(1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2);
			//这是除以11后，可能产生的11位余数、验证码，也保存成数组
			var idCardWiSum = 0;
			//用来保存前17位各自乖以加权因子后的总和
			for (var i = 0; i < 17; i++) {
				idCardWiSum += value.substring(i, i + 1) * idCardWi[i];
			}
			var idCardMod = idCardWiSum % 11;
			var idCardLast = value.substring(17);
			//如果等于2，则说明校验码是10，身份证号码最后一位应该是X
			if (idCardMod == 2) {
				if (idCardLast == "X") {
					idcard_boolean=true;
				} else {
					idcard_boolean=false;
				}
			} else {
				//用计算出的验证码与最后一位身份证号码匹配，如果一致，说明通过，否则是无效的身份证号码
				if (idCardLast == idCardY[idCardMod]) {
	            	idcard_boolean=true;
	            } else {
	            	idcard_boolean=false;
	            }
	        }
	    }
	}
	return (isIDCard1.test(value)) || idcard_boolean;
}
