$(document).ready(function() {

	/**
	 * 绑定事件Ajax Load 使用方法: 在点击标签中加入 class="ajaxLink" 异步加载返回页面到contentDiv
	 */
	$("div").on("click", ".ajaxLink", function() {
		return ajaxLinkLoad($(this));
	});

	/**
	 * AJAX表单提交 使用方法： 在form标签中加入 class="ajaxForm"
	 * 若是返回页面需要填充到contentDvi,则不写回调函数ajaxFormCallBack
	 */
	$("div").on("submit", ".ajaxForm", function() {
		return ajaxFormLoad($(this));
	});
	
});

/**
 * 设置选中菜单
 * @param target
 */
function setMenuSelect(target){
	//如果是菜单
		// 左侧选中
		var allLi = $(".sidebar-nav > ul > li > a");
		$.each(allLi, function() {
			$(this).removeClass("navSelect");
			$(this).css("color", "#00659d");
		});
		target.addClass("navSelect");
		target.css("color", "#fff");
}

function showModalFromUrl(modalId,url,data){
	showLoading();
	if(data){
		data.random = HC.generateMixed(7);
	}else{
		data = {random:HC.generateMixed(7)};
	}
	data.loadPage = 1;

	$("#"+modalId+"Body").load(url,data, function(response) {
		hideLoading();
		if(response.indexOf("timeout") > 0){
			showLoginModal();
		}else{
			$("#"+modalId).modal("show");
		}
	});
}


/**
 * 异步加载页面代码到contentDiv
 * @param url
 */
function loadPage(url,data) {
	showLoading();
	data.loadPage = 1;

	$("#contentDiv").load(url,data, function(response) {
		hideLoading();
		$(this).html(response);
		// 定义加载事件，以代替ajax页面的加载事件，以解决执行两次的问题
		if(window.onPageLoad){
			onPageLoad();
			// 执行完毕后移除，以免在load其他页面时再次执行
			window.onPageLoad = undefined;
		}
	});
}

/**
 * 将表单序列化成对象
 */
function serializeObject(form) {
	var o = {};
	$.each(form.serializeArray(), function(index) {
		if (o[this['name']]) {
			o[this['name']] = o[this['name']] + "," + this['value'];
		} else {
			o[this['name']] = this['value'];
		}
	});
	return o;
} 

/**
 * 显示加载中图标
 */
function showLoading() {
	$("#progressDiv").show();
}

/**
 * 隐藏加载中图标
 */
function hideLoading() {
	$("#progressDiv").hide();
}

/**
 * 显示错误信息
 * @param message
 */
function showGlobalErrorMessage(message){
	if(message == "timeout"){
		message = "请求超时!";
	}
	$("#globalModalMessage").html();
	var messageHtml = "<i class=\"icon-warning-sign modal-icon failTip\"></i>"+message;
	$("#globalModalMessage").html(messageHtml);
	$("#globalModal").modal("show");
}

function showGlobalSuccessMessage(message){
	if(message == "timeout"){
		message = "请求超时!";
	}
	$("#globalModalMessage").html();
	var messageHtml = "<i class=\"icon-ok modal-icon successTip\"></i>"+message;
	$("#globalModalMessage").html(messageHtml);
	$("#globalModal").modal("show");
}

function ajaxLoad(url,data,callback){
	if(url){
		showLoading();
		$.ajax({
			type : 'POST',
			url : url,
			data:data,
			dataType : 'json',
			success:function(response) {
				hideLoading();
				if(response.code == -1){
					showGlobalErrorMessage(response.message);
				}else if(response.code == -2){
					showLoginModal();
				}else{
					callback(response);
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				hideLoading();
				// 全局ERROR处理函数
				showGlobalErrorMessage(textStatus);
			}
	});
	}
}

/**
 * 超链接Ajax提交加载
 * @param sender
 * @returns {Boolean}
 */
function ajaxLinkLoad(sender) {
	var url = sender.prop("href");
	if (!url && (url == window.location.href + "#"))
		return false;

	var hasPageCallBackFun = false;

	try {
		//是否存在页面自定义回调函数 ajaxLinkCallBack
		if (typeof (eval(ajaxLinkCallBack)) == "function") {
			hasPageCallBackFun = true;
		}
	} catch (e) {

	}
	
	//判断是否存在传入的回调函数或页面回调函数
	if (hasPageCallBackFun && !sender.hasClass("menu")) {
		showLoading();
		$.ajax({
				type : 'POST',
				url : url,
				dataType : 'json',
				success:function(response) {
					hideLoading();
					if(response.code == -1){
						showGlobalErrorMessage(response.message);
					}else if(response.code == -2){
						showLoginModal();
					}else{
						ajaxLinkCallBack(response);
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					hideLoading();
					// 全局ERROR处理函数
					showGlobalErrorMessage(textStatus);
				}
		});
	} else {
		//不存在则认为是异步加载页面
		loadPage(url);
		setMenuSelect(sender);
	}
	
	return false;
}

/**
 * Form表单Ajax提交
 * @param sender
 * @param callBack
 * @returns {Boolean}
 */
function ajaxFormLoad(sender, callBack) {
	var url = sender.prop("action");
	var data = serializeObject(sender);
	var hasPageCallBackFun = false;

	try {
		//是否存在自定义回调函数 ajaxLinkCallBack
		if (typeof (eval(ajaxFormCallBack)) == "function") {
			hasPageCallBackFun = true;
		}
	} catch (e) {

	}

	if (callBack || hasPageCallBackFun) {
		showLoading();
		$.ajax({
			type : 'POST',
			url : url,
			data : data,
			dataType : 'json',
			success : function(response) {
				hideLoading();
				if(response.code == -1){
					showGlobalErrorMessage(response.message);
				}else if(response.code == -2){
					showLoginModal();
				}else{
					if (callBack) {
						callBack(response);
					} else {
						ajaxFormCallBack(response);
					}
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				hideLoading();
				// 全局ERROR处理函数
				showGlobalErrorMessage(textStatus);
			}
		});

	} else {
		loadPage(url,data);
	}
	return false;
}

/**
 * ajax上传文件
 * @param sender 提交的form实例
 * @param callBack 回调函数
 */
function ajaxFormUpload(sender,callBack){
	var url = $(sender).prop("action");
	showLoading();
	$(sender).ajaxSubmit({
        type:'post',
        url:url,
        dataType : "json",
        success:function(response){
        	hideLoading();
        	if(callBack){
        		callBack(response);
        	}else if(response.code == -2){
				showLoginModal();
			}else{
        		if(response.code == -1){
        			showGlobalErrorMessage(response.message);
        		}else{
        			showGlobalSuccessMessage(response.message);
        		}
        	}
        },
        error:function(response){
        	hideLoading();
            showGlobalErrorMessage(response.message);
        }
	});
}

/**
 * 提示错误信息, 页面必须有 "&lt;label id="messageLabel"&gt;&nbsp;&lt;/label&gt;"
 * @param message
 */
function error(message,lableId){
	var content = "&nbsp;";
	if(message != null && message != ""){
		content = "<i class='icon-minus-sign failTip'></i>&nbsp;<font class='failTip'>"+message+"</font>";
	}
	if(lableId){
		$("#"+lableId).html(content);
	}else{
		$("#messageLabel").html(content);
		$("#messageLabel").show();
	}
}

/**
 * 提示成功信息，页面必须有 "&lt;label id="messageLabel"&gt;&nbsp;&lt;/label&gt;"
 * @param message
 */
function success(message,lableId){
	var content = "&nbsp;";
	if(message != null && message != ""){
		content = "<i class='icon-ok-sign successTip'></i>&nbsp;<font class='successTip'>"+message+"</font>";
	}
	if(lableId){
		$("#"+lableId).html(content);
	}else{
		$("#messageLabel").html(content);
		$("#messageLabel").show();
	}
}

/**
 * 显示确认框
 * @param message 提示信息
 * @param callback_OK 点击OK函数
 * @param callback_cancle 点击取消函数
 */
function showComfirmModal(message,callback_OK,callback_cancle){
	$("#confirmMessage").html(message);
	$("#confirmModal").modal({keyboard: true});
	$('#confirmCancle').unbind("click");
	$('#confirmOK').unbind("click");
	$("#confirmOK").click(function(){
		if(callback_OK && typeof callback_OK == "function")
			callback_OK();
	});
	$("#confirmCancle").click(function(){
		if(callback_cancle && typeof callback_cancle == "function")
			callback_cancle();
	});
}

function showLoginModal(){
	var isAdmin = $("#isAdmin").val();
	if(isAdmin && isAdmin == '0'){
		window.location.href = "admin/index.do";
	}else{
		$("#loginModal").modal("show");
	}
}
