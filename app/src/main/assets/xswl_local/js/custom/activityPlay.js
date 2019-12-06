window.initMusic=0;
window.userid = uid;
window.action_ = null;
window.games = [];
window.activityData = JSON.parse(readCookie('activityData'));
var soundFlag = true;

//棋力等级过滤器
Vue.filter("leve",function(leve){
	if(leve>25){
		return (leve-25)+'D'
	}else if(leve<=25 && leve>0){
		return (26-leve)+'K'
	}else if(leve==0){
		return '0'
	}
})
var vmGameInfo = new Vue({
	el : '#game_info_box',
	data : {
		game : {},
		currentGame:{},
		resFlag:false,//对弈结果弹框
		sttscFlag:false,//数棋弹框
		chess:{},
		chessRes:'win'
	},
	methods : {
		//根据uid查询棋局
		loadGameInfoByUserId(callback) {
			function success(data) {
				if (data.error.returnCode == 0) {
					if(callback) {
						callback(data.data);
					}
				} else if (data.error.returnCode == 1002) {
					layerTC('无对局信息！')
					document.location.href ="myschema://go?a=1"
				} else {
					layerTC(data.error.returnMessage);
				}
			}

			$.ajax({
				type : "POST",
				url : http+"/consumerXSWQ/ChessController/getGameInfoByUserId",
				dataType : 'json',
				data:"token="+token+"&uid="+uid,
				success : success,
				error : function(XMLHttpRequest, data) {
					document.location.href ="myschema://go?a=0"
				}
			});
		},
		//进入棋局
		enterGame(gameId, otherId) {
			console.log("EnterGame", gameId);
			eve.f("HandlerEnterGame", gameId)();
			document.location.href ="myschema://go?a=0"
	        window.gamesTimeInfo();
		},
		//加载棋局信息
		onGameLoaded(game) {
			console.log("OnGameLoaded: ", game);
			var whiteLevel = game.whiteLevel.toString();
			var blackLevel = game.blackLevel.toString();
			if(whiteLevel.indexOf('K')!=-1||whiteLevel.indexOf('D')!=-1||blackLevel.indexOf('K')!=-1||blackLevel.indexOf('D')!=-1){
				vmGameInfo.game = game;
			}else{
				if(game.whiteLevel>25){
					game.whiteLevel = (game.whiteLevel-25)+'D';
				}else if(game.whiteLevel<=25&&game.whiteLevel>0){
					game.whiteLevel = (26-game.whiteLevel)+'K';
				}else if(game.whiteLevel==0||game.whiteLevel==''){
					game.whiteLevel = '25k';
				}
				if(game.blackLevel>25){
					game.blackLevel = (game.blackLevel-25)+'D';
				}else if(game.blackLevel<=25&&game.blackLevel>0){
					game.blackLevel = (26-game.blackLevel)+'K';
				}else if(game.blackLevel==0||game.blackLevel==''){
					game.blackLevel = '25k';
				}
				vmGameInfo.game = game;
			}
			vmGameInfo.currentGame = game;
		    vmGameInfo.currentGame.token =token;
			vmGameInfo.currentGame.uid = uid
			myRole = userid == vmGameInfo.currentGame.blackUserId ? "black" : userid == vmGameInfo.currentGame.whiteUserId ? "white" : "visitor";
			this.enterGame(game.gameId);
			
		},
		//加载棋局信息
		onGamesLoaded(gameList, opts) {
			console.log("onGamesLoaded: ", gameList, opts);
			window.games = gameList;
			var target = 0;
			if(opts && opts.playingId) {
				for(var i = 0; i < games.length; i++) {
					if(games[i].gameId == opts.playingId) {
						target = i;
						break;
					}
				}
			}
			this.onGameLoaded(gameList[target]);
		},
		//根据gameId查询正在对局的信息
		loadMatchInfoByGameId(gameId, callback) {
			console.log("loadMatchInfoByGameId", gameId);
			$.ajax({
				type : "GET",
				url : http+'/consumerXSWQ/ChessController/loadMatchInfo',
				dataType : 'json',
				data:"chessid="+gameId+"&token="+token+"&uid="+uid,
				success : function(data) {
					if (data.error.returnCode == 0) {
						if (callback) {
							callback(data.data);
						}
					} else {
						layerTC(data.error.returnUserMessage);
					}
				},
				error : function(XMLHttpRequest, data) {
					document.location.href ="myschema://go?a=0"
				}
			});
		},
		//关闭棋局
		closeGame(gameInfo, callback) {
			// 对弈结束时
			$.ajax({
				type : "POST",
				url : http+"/consumerXSWQ/ChessController/closeGame",
				data : $.param(gameInfo),
				dataType : 'json',
				success : function(data) {
					console.log('关闭棋局',data.data)
					if (data.error.returnCode > 0) {
						console.log("更新对局状态失败。", data.error);
					} else {
						if (callback) {
							callback(data.data);
						}
					}
				},
				error : function(XMLHttpRequest, data) {
					document.location.href ="myschema://go?a=0"
				}
			});
		},
		//关闭棋局回调
		closePlaying(chessid, callback) {
			$.ajax({
				type : "POST",
				url : http+"/consumerXSWQ/ChessController/closePlaying",
				data : $.param({chessid: chessid,token:token,uid:uid}),
				dataType : 'json',
				success : function(data) {
					if (data.error.returnCode > 0) {
						console.log("更新用户比赛状态失败。", data.error);
					} else {
						if (callback) {
							callback(data.data);
						}
					}
				},
				error : function(XMLHttpRequest, data) {
					document.location.href ="myschema://go?a=0"
				}
			});
		},
		//离开房间
		quitGame() {
			var myUserId = uid;
			if(vmGameInfo.currentGame && (vmGameInfo.currentGame.blackUserId == myUserId || vmGameInfo.currentGame.whiteUserId == myUserId)) { // 我时游戏其中一方，则需要首先认输
				vmGameInfo.loadMatchInfoByGameId(vmGameInfo.currentGame.gameId, function(game) {
					if(game.playsts == 1) {
						layerTC("您正在对局中，退出将视为认输，是否退出？",function() {
							vmGameInfo.handleAskForAdmitDefeat();
						},layer.closeAll());
					} else {
						vmGameInfo.closePlaying(vmGameInfo.currentGame.gameId, function() {
							setTimeout(function(){
								
							},1000)
						});
					}
				});
			} else {
	 			document.location.href = "myschema://go?a=1";
			}
		},
		//发起数棋请求9路
		handlerStatisticalReq9() {
			
			$.ajax({
				type : "POST",
				url : http+"/consumerXSWQ/GodController/getAN9Result",
				data : $.param({chessId: vmGameInfo.currentGame.gameId,token:token,uid:uid}),
				dataType : 'json',
				success : function(data) {
					if (data.error.returnCode > 0) {
						layerTC("请先选择落点~");
					} else {
						vmGameInfo.sttscFlag = true;
						var text = $('.tzinfo').text();
						vmGameInfo.game.resultInfo = data.data.resultInfo;
						vmGameInfo.game.tzinfo = text;
					}
				},
				error : function(XMLHttpRequest, data) {
					document.location.href ="myschema://go?a=0"
				}
			});
		},
		//发起数棋请求19路
		handlerStatisticalReq19() {
			eve.f('HandlerStatisticalReq')();
			layerTC({content:"发起数棋请求",btn:['确定']});
		},
		//发起悔棋请求
		handlerRequestUndo() {
			eve.f('HadlerRequestUndo')();
			//sendTipMsg("发起悔棋请求", readCookie("username"));
		},
		//发起认输
		handleAskForAdmitDefeat() {
			eve.f('HandleAskForAdmitDefeat')();
		},
		//播放音效
		/*playSound(soundId) {
			if(soundId == "stepmusic" || soundId == "takemusic") {
				if(soundOpts & 1 == 1) { // 落子音
					$("#" + soundId)[0].play();
				}
			} else {
				if(soundOpts & 2 == 2) { // 提示音
					$("#" + soundId)[0].play();
				}
			}
		},*/
		socketStart() {
			eve.f("SocketStart")();
		}
	},
	 mounted() {
	   this.socketStart();
	   if(parseInt(window.activityData.ruleNum)!=window.activityData.ruleNum){
		   window.activityData.ruleNum = parseInt(window.activityData.ruleNum)+'¾';
	   }
	   window.activityData.rule == 1?$('.tzinfo').text('提'+window.activityData.ruleNum+'子胜'):$('.tzinfo').text('黑让'+window.activityData.ruleNum+'子');
	 },
	 components:{
 		'chessResult':{
 			data:function(){
 				return {
 					aa:'',
 				}
 			},
 			props:['chess'],
 			methods:{
 				vmGameNone(){
 					vmGameInfo.resFlag = false;
 					document.location.href ="myschema://go?a=1"
 				}
 			},
 			mounted:function(){
 				//this
 			},
 			template:'#chessResult'
 		},
 		'statisticalResult':{
 			data:function(){
 				return {
 					aa:'',
 				}
 			},
 			props:['game'],
 			methods:{
 				closeSq(){
 					vmGameInfo.sttscFlag = false;
 				},
 				//发起数棋请求
 				sendSq() {
 					eve.f('HandlerStatisticalReq')();
 					vmGameInfo.sttscFlag = false;
 				}
 			},
 			mounted:function(){
 				//this
 				console.log(this)
 			},
 			template:'#statisticalResult'
 		},
	 }
});
// 棋盘加载完毕通知
eve.on("AppOnEnter", function() {
	eve.f("SocketStart")();
	if(initMusic==0){//请多指教
      
		//playSound("startmusic");
       document.location.href = "myschema://go?a=8&music=START";
	}
});
/**
 * 消息通知
 */
eve.on("NotifyPushToUserMessage", function(ack, msg) {
	console.log("MessageNotify " + ack);
	console.log("MessageNotify " + msg);
	var msgInf = Base.decode(msg);
	console.log("MessageNotify " + msgInf);
	var result = JSON.parse(msgInf);
	var code = result.code;
	var info = result.info;
	console.log("Message_info: " + msg);
	switch (code) {
	case '1':
		debugger
		// 对方接受挑战
		vmGameInfo.loadGameInfoByUserId(function(data) {
			console.log("对方接受应战", data, Tool.isArray(data));
			if(Tool.isArray(data)) {
				vmGameInfo.onGamesLoaded(data);
			} else {
				vmGameInfo.onGameLoaded(data);
			}
			
		});
		
		break;
	default:
		console.log("无用消息" + msgInf);
	}
});
var takeBlack_now = 0;
var takeWhite_now = 0;
eve.on("_ShowTake_", function(takeBlack, takeWhite) {
	document.getElementById("blacktake").innerText = takeWhite;
	document.getElementById("whitetake").innerText = takeBlack;
	if(takeBlack>takeBlack_now && Tool.queryString('effectId')==0){//提子音
		document.location.href = "myschema://go?a=8&music=ChipMany";
		takeBlack_now = takeBlack;
		return;
	}
	if(takeWhite>takeWhite_now && Tool.queryString('effectId')==0){//提子音
		document.location.href = "myschema://go?a=8&music=ChipMany";
		takeWhite_now = takeWhite;
		return;
	}
	/*if(initMusic>0){//落子
		document.location.href = "myschema://go?a=8&music=STONE0";
	}
	initMusic++;*/
});
eve.on("_ConfirmTouchEnded_", function(p1, p2) {
	var gameId = GGame.get("game:id");
	var blackUserid = GGame.get("game:"+gameId+":black");
	var whiteUserid = GGame.get("game:"+gameId+":white");
	var tryPlay = GGame.get("game:"+gameId+":TryPlay");
	var steps = GGame.incrBy("game:" + gameId + ":steps", 0);
	var first = GGame.incrBy("game:" + gameId + ":first", 0);
	if(tryPlay === 0){
		if(blackUserid == userid){
			p2 = "B"+p2.substring(1,p2.length);
		}else if(whiteUserid == userid){
			p2 = "W"+p2.substring(1,p2.length);
		}
		if (steps % 2 === first) {
			//当前状态走黑棋
			if(p2.charAt(0) == "W"){
				return;
			}
		}else{
			if(p2.charAt(0) == "B"){
				//("当前白棋落子")
				return;
			}
		}
	}
  	eve.f("HandReply",p2)();
  	action_ = p2;
});

function getInfo(blackSum, whiteSum) {
	 console.log('sum==>',blackSum,whiteSum)
	if (blackSum === undefined || whiteSum === undefined) 	
    return "";
	var sum =0;
	if((blackSum+whiteSum)==361){
		sum = blackSum - 180.5 - 3.75;
	}else{
		sum = blackSum - whiteSum - 3.75;
	}
	var sumstr = Math.abs(sum)+"";
	var result = sumstr.substr(0,sumstr.indexOf("."));
	var desimal = sumstr.substr(sumstr.indexOf(".")+1,sumstr.length);
	if(desimal=="25"){
		desimal = "¼";
	}else {
		desimal = "¾";
	}
	if(result=="0"){
		result = desimal;
	}else{
		result += desimal;
	}
	
    if (sum > 0) {
        return "黑棋胜" + result + "子";
    } else if (sum < 0) {
        return "白棋胜" + result + "子";
    }else {
        return "平局";
    }
}

/*
 * 获取当前用户 胜负结果
 */



function getInfo2(blackSum, whiteSum) {
	var sum;
	if (blackSum === undefined || whiteSum === undefined){
			var chessColor = '' //白棋或黑旗
			var vd = ''//胜负
			var domid = $('div[data-uid='+uid+']').attr('id')//获取当前用户所在的‘阵营’
			//如果当前登入用户  包含user_2_avater类  说明是黑棋  否则白棋
			domid&&domid=='user_2_avater'?chessColor = 1:chessColor = 0
			//如果在 #win 的元素上不能查找到 lose类 说明胜利  否则 失败
			vmGameInfo.chess.chessRes!='lose'?vd = 1:vd = 0
			
			if(chessColor){ //黑旗
				if(vd){
					sum = 10;
					return sum
					//alert('黑旗胜')
				}else{
					sum = -10;
					return sum
					//alert('黑旗败')
				}
			}else{ //白棋
				if(vd){
					sum = -10;
					return sum
					//alert('白旗胜')
				}else{
					sum = 10;
					return sum
					//alert('白棋败')
				}
			}
	}else{
		sum = blackSum - 180.5 - 3.75;
		return sum
	}
}



/**
 * 游戏结束
 */
eve.on("_NotifyGameOver_", function(status, blackSum, whiteSum) {
	var msg;
	var info;
	console.log('status==>',status)
	if (status === 1) {
		 msg = "白中盘胜！";//黑旗认输
	} else if (status === 2) {
		msg = "黑中盘胜！";//白旗认输
	} else if (status === 5) {
		msg = "黑棋超时，棋局已结束！";
	} else if (status === 6) {
		msg = "白棋超时，棋局已结束！";
	} else if (status === 8) {//白棋请求数棋
		info = getInfo(blackSum, whiteSum);
		msg = info;
		if((blackSum+whiteSum)==361){
			vmGameInfo.currentGame.resultSum = blackSum - 180.5 - 3.75;
		}else{
			vmGameInfo.currentGame.resultSum = blackSum - whiteSum - 3.75;
		}
	} else if (status === 9) {//黑棋请求数棋
		info = getInfo(blackSum, whiteSum);
		msg =  info;
		if((blackSum+whiteSum)==361){
			vmGameInfo.currentGame.resultSum = blackSum - 180.5 - 3.75;
		}else{
			vmGameInfo.currentGame.resultSum = blackSum - whiteSum - 3.75;
		}
	} else if (status === 10) {
		info = getInfo(blackSum, whiteSum);
		msg = info;
		if((blackSum+whiteSum)==361){
			vmGameInfo.currentGame.resultSum = blackSum - 180.5 - 3.75;
		}else{
			vmGameInfo.currentGame.resultSum = blackSum - whiteSum - 3.75;
		}
	} else if (status === 11) {
		info = getInfo(blackSum, whiteSum);
		msg = info;
		if((blackSum+whiteSum)==361){
			vmGameInfo.currentGame.resultSum = blackSum - 180.5 - 3.75;
		}else{
			vmGameInfo.currentGame.resultSum = blackSum - whiteSum - 3.75;
		}
	}  else if (status === 14) {//9路黑胜
		info = getInfo2(blackSum, whiteSum);
		msg = "游戏结束 ";
	}  else if (status === 15) {//9路白胜
		info = getInfo2(blackSum, whiteSum);
		msg = "游戏结束 ";
	}else {
		msg = "游戏结束 ";
	}
	
	if(vmGameInfo.currentGame.infoflag == "close"){
		return;
	}
	vmGameInfo.closeGame(vmGameInfo.currentGame, function(data) {
		vmGameInfo.currentGame.infoflag = "close";
		
		//胜负结果信息
		vmGameInfo.chess.msg = msg;
		vmGameInfo.chess.gameInfo = vmGameInfo.currentGame;
		vmGameInfo.chess.closeInfo = data;
		console.log(vmGameInfo.chess.closeInfo)
		getInfo(blackSum, whiteSum);
		//胜负结果页
		vmGameInfo.resFlag = true;
		console.log(vmGameInfo.currentGame)
		if(data.playResult!=4){
			var myColor = userid == vmGameInfo.currentGame.blackUserId ? 1 : 2;
			if(myColor == 1 && (status == 1 || status == 5 || status == 15)) {//黑棋负
				vmGameInfo.chess.chessRes = 'lose';
				document.location.href = "myschema://go?a=8&music=COMFORT";
			} else if(myColor == 2 && ((status == 2 || status == 6  || status == 14))) {//白棋负
				document.location.href = "myschema://go?a=8&music=COMFORT";
				
			} else if(status==3||status==4){
				vmGameInfo.chess.chessRes = 'pingju';
	 			document.location.href = "myschema://go?a=8&music=COMFORT";
	 		} else if(myColor == 1 && vmGameInfo.currentGame.resultSum<0) {
	 			vmGameInfo.chess.chessRes = 'lose';
				document.location.href = "myschema://go?a=8&music=COMFORT";
	 		} else if(myColor == 2 && vmGameInfo.currentGame.resultSum>0) {
	 			vmGameInfo.chess.chessRes = 'lose';
	 			document.location.href = "myschema://go?a=8&music=COMFORT";
	 		} else{
	 			vmGameInfo.chess.chessRes = 'win';
	 			document.location.href = "myschema://go?a=8&music=CELEBRATE";
	 		}
			$(".win-result").html(msg);
			var blacktake = $("#blacktake").text();
			var whitetake = $("#whitetake").text();
			$(".chessBlack").text('黑方提子'+blacktake);
			$(".chessWhite").text('白方提子'+whitetake);
			
		}else{
			vmGameInfo.chess.chessRes = 'invalid';
		}
        vmGameInfo.closePlaying(vmGameInfo.currentGame.gameId,function(){layer.closeAll()});
	});
});

/**
 * 连接断开
 */
eve.on("SocketClose", function() {
	eve.f("SocketStart")();
});

/**
 * 连接成功
 */
eve.on("SocketOpen", function() {
	// 连接成功
	eve.f("HandlerSignInReq", uid)();
});

/**
 * 用户登录响应用户登录响应
 */
eve.on("SignInResponse", function() {
	vmGameInfo.loadGameInfoByUserId(function(data) {
		console.log("loadGameInfoByUserId - callback ", data, Tool.isArray(data));
		if(Tool.isArray(data)) {
			
			vmGameInfo.onGamesLoaded(data);
		} else {
			vmGameInfo.onGameLoaded(data);
		}
	});
});

/**
 * 对方悔棋请求
 */
eve.on("UndoNotiy", function() {
	layerTC({
		content : "对方请求悔棋？",
		btn : ["同意",'不同意']
	},function(){
		eve.f("HadlerAnswerUndoYes")();
	},function(){
		eve.f("HadlerAnswerUndoNo",vmGameInfo.currentGame.gameId)();
	});
});

/**
 * 响应数子通知
 */
eve.on("StatisticalNotify", function() {
	layerTC({
		content : "对方请求数子？",
		btn : ["同意",'不同意']
	},function(){
		eve.f("HadlerStatisticalYes")();
	},function(){
		eve.f("HadlerStatisticalNo",vmGameInfo.currentGame.gameId)();
	});
});

/**
 * 数子不同意通知
 * 
 * @param message
 */
eve.on("_StatisticalNoNotify_", function() {
	layerTC( {
		content : "对方拒绝了数子！",
		btn : ["确认"]
	});
});

/**
 * 数子限制次数已到
 */
eve.on("_StatisticalLimit_", function() {

	document.getElementById("input1").disabled = false;
	document.getElementById("input2").disabled = true;
	layerTC( {
		content : "数子请求达到限制次数！",
		btn : ["确认"]
	});
	//sendTipMsg("数子请求已超限", readCookie("username"));
});



/**
 * 悔棋请求达到限制次数
 * 
 * @param message
 */
eve.on("_AnswerUndoLimit_", function() {
	layerTC( {
		content : "悔棋请求达到限制次数！",
		btn : ["确认"]
	});
	//sendTipMsg("悔棋请求次数超限", readCookie("username"));
});

/**
 * 悔棋不同意通知
 * 
 * @param message
 */
eve.on("_AnswerUndoNoNotify_", function() {
	layerTC( {
		content : "对方拒绝了悔棋！",
		btn : ["确认"]
	});
});

/**
 * 确定认输
 * 
 * @param message
 */
eve.on("_CanHandleAdmitDefeat_", function() {
	layerTC("确定认输？",function(){
		eve.f("HadlerAdmitDefeat")();
	},layer.closeAll());
});

// 通知棋局数据有更新
eve.on("_NotifyUpdate_", function(gameId) {
	document.location.href = "myschema://go?a=8&music=STONE0";
});


/**
 * 推送消息给用户
 */
function pushToUserMessage(msg) {
	console.log("推送消息：" + msg);
	eve.f("HandlerPushToUserMessage", 1, msg)();
}

/**
 * 询问能否请求认输
 */
function admitDefeat() {
	eve.f("HandleAskForAdmitDefeat")();
}

/**
 * 进行连接
 */

function confirmStep(){
    if(action_==null){
        layerTC("请选择落点");
    }else{
        var gameId = GGame.get("game:id");
        eve.f("HandlerAction", action_)();
        soundFlag = false;
        setTimeout(function(){
           var queue = GGame.get("game:"+gameId+":queue");
                   if(action_ == queue[queue.length-1]){
                   action_ = null;
           }
       },1000);
    }
}
window.gamesTimeInfo = function(){
	function two_char(n) {
	    return n >= 10 ? n : "0" + n;
	}

	function showTime(seconds, baseSeconds) {
	    if (seconds > baseSeconds)
	        seconds = baseSeconds;
	    var date = new Date(0, 0);
	    date.setSeconds(seconds);
	    var h = date.getHours(), m = date.getMinutes(), s = date.getSeconds();
	    return (two_char(h) + ":" + two_char(m) + ":" + two_char(s));
	}
	function showTimeOnDom(bt, wt, baseSeconds) {
	    document.getElementById("blacktime").innerText = showTime(baseSeconds-bt > 0 ? baseSeconds-bt:0, baseSeconds);
	    document.getElementById("whitetime").innerText = showTime(baseSeconds-wt > 0 ? baseSeconds-wt:0, baseSeconds);
	}

	var flagwt =0;
	var flagbt =0;
	//每一步的时间
	var stepTime = activityData.stepTime;
	//读秒次数
	var countNum = activityData.countNum;
	document.getElementById("white_Seconds").innerText = stepTime+'秒';
    document.getElementById("black_Seconds").innerText = stepTime+'秒';
	document.getElementById("black_counts").innerText = countNum+'次';
	document.getElementById("white_counts").innerText = countNum+'次';
	function showSecOnDom(bt, wt, bc, wc) {
	    document.getElementById("white_Seconds").innerText = stepTime-wt+'秒';
	    document.getElementById("black_Seconds").innerText = stepTime-bt+'秒';
	    document.getElementById("black_counts").innerText = countNum-bc+'次';
	    document.getElementById("white_counts").innerText = countNum-wc+'次';
	    if(wt>0 && flagwt==0){
	    	$("#countdownmusic")[0].play();
	    	flagwt++;
	    }
	    if(bt>0 && flagbt==0){
	    	$("#countdownmusic")[0].play();
	    	flagbt++;
	    }
	    if(stepTime > 10){
	    	 if((stepTime-wt)==10){
	    	    soundFlag = true;
	 			secondCount();
	 	    }
	 	    if((stepTime-bt)==10){
	 	    	soundFlag = true;
	 	    	secondCount();
	 	    }
	    }
	}

	//10秒倒计时
	var secondindex = 0;
	function secondCount(){
		$(".second")[secondindex].play();
		setTimeout(function(){
			if( secondindex++ < $(".second").length-1 && soundFlag){
				secondCount();
			}else{
				secondindex = 0;
			}
		},1000);
	}
	    
	function showUserTime(gameId, baseSeconds) {
	    var bt = GGame.hincrBy("game:" + gameId + ":seconds", "black", 0);
	    var wt = GGame.hincrBy("game:" + gameId + ":seconds", "white", 0);
	    showTimeOnDom(bt, wt, baseSeconds);
	}

	function showUserTimeIncrBlack(gameId, baseSeconds) {
	    var bt = GGame.hincrBy("game:" + gameId + ":seconds", "black", 1);
	    var wt = GGame.hincrBy("game:" + gameId + ":seconds", "white", 0);
	    showTimeOnDom(bt, wt, baseSeconds);
	}

	function showUserTimeIncrWhite(gameId, baseSeconds) {
	    var bt = GGame.hincrBy("game:" + gameId + ":seconds", "black", 0);
	    var wt = GGame.hincrBy("game:" + gameId + ":seconds", "white", 1);
	    showTimeOnDom(bt, wt, baseSeconds);
	}

	function showUserSecondsIncrBlack(gameId) {
	    var bt = GGame.incrBy("game:" + gameId + ":last_second", 1);
	    var bc = GGame.incrBy("game:" + gameId + ":black_counts", 0);
	    var wc = GGame.incrBy("game:" + gameId + ":white_counts", 0);

	    // console.log(" bt  "+bt);

	    if (bt >= stepTime) {
	        GGame.set("game:" + gameId + ":last_second", 0);
	        bc = GGame.incrBy("game:" + gameId + ":black_counts", 1);
	        eve.f("CountingCounts", "black")();
	    }
	    
	    showSecOnDom(bt, 0, bc, wc);
	}

	function showUserSecondsIncrWhite(gameId) {
	    var wt = GGame.incrBy("game:" + gameId + ":last_second", 1);
	    var bc = GGame.incrBy("game:" + gameId + ":black_counts", 0);
	    var wc = GGame.incrBy("game:" + gameId + ":white_counts", 0);

	    console.log(" wt  " + wt);

	    if (wt >= 30) {
	        GGame.set("game:" + gameId + ":last_second", 0);
	        wc = GGame.incrBy("game:" + gameId + ":white_counts", 1);
	        eve.f("CountingCounts", "white")();
	    }
	    showSecOnDom(0, wt, bc, wc);
	}
	//启动秒计时器
	setInterval(function () {
	    var gameId = GGame.get("game:id");
	    var whiteSeconds = 0, blackSeconds = 0;
	    var white_Seconds = 0, black_Seconds = 0;

	    if (gameId !== undefined) {


	        var counter = GGame.incrBy("game:" + gameId + ":counter", 0);
	        if (counter > 0) {
	            var road = GGame.get("game:" + gameId + ":road");
	            var baseSecond =  activityData.baseTime;
	            var matchStatus = GGame.hincrBy("game:" + gameId + ":status", "match", 0);

	            if (matchStatus === 0) {

	                var steps = GGame.incrBy("game:" + gameId + ":steps", 0);
	                var first = GGame.incrBy("game:" + gameId + ":first", 0);

	                if (steps % 2 === first) {
	                    if (GGame.hincrBy("game:" + gameId + ":seconds", "black", 0) < baseSecond) {
	                        showUserTimeIncrBlack(gameId, baseSecond);
	                    } else {//读秒
	                        showUserTime(gameId, baseSecond);
	                        showUserSecondsIncrBlack(gameId);
	                    }
	                } else {
	                    if (GGame.hincrBy("game:" + gameId + ":seconds", "white", 0) < baseSecond) {
	                        showUserTimeIncrWhite(gameId, baseSecond);
	                    } else {//读秒
	                        showUserTime(gameId, baseSecond);
	                        showUserSecondsIncrWhite(gameId);
	                    }
	                }
	            }// if (matchStatus === 0) {

	        }
	    }


	    // document.getElementById("blacktime").innerText = showTime(blackSeconds);
	    // document.getElementById("whitetime").innerText = showTime(whiteSeconds);
	}, 1000);
}
