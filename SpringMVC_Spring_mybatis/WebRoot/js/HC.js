
/**
 * 封装了一些常用方法 
 * @returns {___anonymous68_69}
 */

//随机数
var chars = ['0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'];

function HCJs(){
		var hc = new Object; 
		//显示弹出框
		//HC.showPopover(...)
		hc.showPopover = function(ctrId,content,title,postion){
		var ctr = $("#"+ctrId);
			ctr.attr("title",title);
		
		if(postion != null && postion !="" && (postion == "left" || postion == "right" || postion == "top" || postion == "bottom")){
			ctr.attr("data-placement",postion);
		}else{
			ctr.attr("data-placement","right");
		}
		ctr.attr("data-toggle","popover");
		ctr.attr("data-content",content);
		ctr.popover('show');
    }
		
	//隐藏弹出框
	hc.hidenPopover = function(ctrId){
		var ctr = $("#"+ctrId);
		ctr.popover('hide');
	}
	
	//产生随机数, n为随机数的长度
	hc.generateMixed = function(n){
		var res = "";
	     for(var i = 0; i < n ; i ++) {
	         var id = Math.ceil(Math.random()*35);
	         res += chars[id];
	     }
	     return res;
	}
	
	//验证source是否为Null或“”
	hc.isEmpty = function(source){
		if(source == null || source == ""|| source == "null"){
			return true;
		}
		return false;
	}
	
	//是否正整型数字
	hc.isInteger = function(source){
		 return !!source.match(/^[0-9]+[0-9]*]*$/);
	}
	
	//是否手机号码
	hc.isMobile = function(source){
		return !!source.match(/^(13[0-9]|15[012356789]|17[0-9]|18[0-9]|14[0-9])[0-9]{8}$/);
	}
		
	//是否身份证号码
	hc.isIDCard = function(source){
		return !!source.match(/(^\d{15}$)|(^\d{17}([0-9]|X)$)/);
	}
	
	//是否邮箱地址
	hc.isEmial = function(source){
		return !!source.match(/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/);
	}
	
	hc.startDateBiggerThanEndDate = function(startDate,endDate){
		startDate = parseInt(startDate.replace("-","").replace("-",""));
		endDate = parseInt(endDate.replace("-","").replace("-",""));
		return startDate > endDate;
	}
	//金额转换大写
	hc.convertCurrencyMoney = function convertCurrency(money) {
		  //汉字的数字
		  var cnNums = new Array('零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖');
		  //基本单位
		  var cnIntRadice = new Array('', '拾', '佰', '仟');
		  //对应整数部分扩展单位
		  var cnIntUnits = new Array('', '万', '亿', '兆');
		  //对应小数部分单位
		  var cnDecUnits = new Array('角', '分', '毫', '厘');
		  //整数金额时后面跟的字符
		  var cnInteger = '整';
		  //整型完以后的单位
		  var cnIntLast = '元';
		  //最大处理的数字
		  var maxNum = 999999999999999.9999;
		  //金额整数部分
		  var integerNum;
		  //金额小数部分
		  var decimalNum;
		  //输出的中文金额字符串
		  var chineseStr = '';
		  //分离金额后用的数组，预定义
		  var parts;
		  if (money == '') { return ''; }
		  money = parseFloat(money);
		  if (money >= maxNum) {
		    //超出最大处理数字
		    return '';
		  }
		  if (money == 0) {
		    chineseStr = cnNums[0] + cnIntLast + cnInteger;
		    return chineseStr;
		  }
		  //转换为字符串
		  money = money.toString();
		  if (money.indexOf('.') == -1) {
		    integerNum = money;
		    decimalNum = '';
		  } else {
		    parts = money.split('.');
		    integerNum = parts[0];
		    decimalNum = parts[1].substr(0, 4);
		  }
		  //获取整型部分转换
		  if (parseInt(integerNum, 10) > 0) {
		    var zeroCount = 0;
		    var IntLen = integerNum.length;
		    for (var i = 0; i < IntLen; i++) {
		      var n = integerNum.substr(i, 1);
		      var p = IntLen - i - 1;
		      var q = p / 4;
		      var m = p % 4;
		      if (n == '0') {
		        zeroCount++;
		      } else {
		        if (zeroCount > 0) {
		          chineseStr += cnNums[0];
		        }
		        //归零
		        zeroCount = 0;
		        chineseStr += cnNums[parseInt(n)] + cnIntRadice[m];
		      }
		      if (m == 0 && zeroCount < 4) {
		        chineseStr += cnIntUnits[q];
		      }
		    }
		    chineseStr += cnIntLast;
		  }
		  //小数部分
		  if (decimalNum != '') {
		    var decLen = decimalNum.length;
		    for (var i = 0; i < decLen; i++) {
		      var n = decimalNum.substr(i, 1);
		      if (n != '0') {
		        chineseStr += cnNums[Number(n)] + cnDecUnits[i];
		      }
		    }
		  }
		  if (chineseStr == '') {
		    chineseStr += cnNums[0] + cnIntLast + cnInteger;
		  } else if (decimalNum == '') {
		    chineseStr += cnInteger;
		  }
		  return chineseStr;
		}
	
	return hc;
}

var HC = HCJs();
		
