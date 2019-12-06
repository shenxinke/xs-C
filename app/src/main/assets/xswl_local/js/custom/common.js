// 工具方法
const Tool = {
	isEmpty : function(obj) {
		return (obj.length === 0 || !obj.trim());
	},
	queryString : function(val) {
		var uri = window.location.search;
		var re = new RegExp("" + val + "=([^&?]*)", "ig");
		return uri.match(re) ? uri.match(re)[0].substr(val.length + 1) : null;
	},
	isMobile : function(val) {
		var reg = /^[1][3,4,5,7,8][0-9]{9}$/;
		return reg.test(val);
	},
	isEmail : function(val) {
		var reg = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
		return reg.test(val);
	},
	isArray: function(obj) {
		return typeof obj === 'object' && !isNaN(obj.length);
	}
}