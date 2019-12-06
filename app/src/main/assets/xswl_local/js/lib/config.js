
//var http = 'http://192.168.2.109:8080';
//var http = 'https://ct.xswq361.cn';
var http = 'https://ca.xswq361.cn';
var uid = Tool.queryString("uid")||'2281';
var token =  Tool.queryString("token")||'FFE69FD3370F932EF8B4222B3873AE0E';
var levelAI = Tool.queryString("levelAI");


////用户是否过期验证
/*$.ajax({
    url:http+ "/gobangteach/UserBaseController/getUserFullInfoById",
    data: {
        userid: uid,
        token:token,
            uid:uid
    },
    success: function(data) {
        if(data.error.returnCode==='10048' && !$('.login')[0] && !$('.loginContent')[0]){
            layerTC('用户登入过期请重新登入', function() {
                document.location.href = "myschema://go?a=2"
            }, function() {
                document.location.href = "myschema://go?a=2"
            })
        }
    }
});
*/
