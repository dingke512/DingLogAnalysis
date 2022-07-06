 $(function(){
						
	$a = $(window).height();
						
	$("#left-nav").height($a);
	// 隐藏
	$("#btn").click(function(){
		$("#left-nav").animate({left:'-15%'});
		$("#head-nav").animate({top:'-6%'});
		$("#btnb").delay(100).animate({left:'0'});
		$("#right-box").animate({width:'100%'});
		$("#child-page").animate({height:'100%'});
		
		//刷新子页面调整iframe echarts图表大小
		setTimeout(function(){ 
			 // $("#myframe").contents().find("#V_ORGID").val('111');
			 
			document.getElementById('child-page').contentWindow.location.reload(true);
		}, 380);
		//等待刷新完成，设置背景
		setTimeout(function(){
			
			var child = document.getElementById("child-page").contentWindow.document.body;
			// alert(document.getElementById("child-page").contentWindow.document)
			child.style.backgroundImage='url(../static/images/bg3.jpg)';
		
		}, 500);
		
	});
	//显示
	$("#btnb").click(function(){
		$("#btnb").animate({left:'-50px'});
		$("#left-nav").delay(100).animate({left:'0'});
		$("#head-nav").animate({top:'0'});
		$("#right-box").animate({width:'85%'});
		$("#child-page").animate({height:'94%'});
		var child = document.getElementById("child-page").contentWindow.document.body;
		child.style.backgroundImage='none';
		child.style.background="#33414b";
		setTimeout(function(){ document.getElementById('child-page').contentWindow.location.reload(true);}, 350);
		
							
		});
	//左侧菜单	
	var Accordion = function(el, multiple) {
	    this.el = el || {};
	    this.multiple = multiple || false;
	
	    // Variables privadas
	    var links = this.el.find('.link');
	    // Evento
	    links.on('click', {
	        el: this.el,
	        multiple: this.multiple
	    }, this.dropdown)
	}
	
	Accordion.prototype.dropdown = function(e) {
	    var $el = e.data.el;
	    $this = $(this),
	        $next = $this.next();
	
	    $next.slideToggle();
	    $this.parent().toggleClass('open');
	
	    if (!e.data.multiple) {
	        $el.find('.submenu').not($next).slideUp().parent().removeClass('open');
	    };
	}
	var accordion = new Accordion($('#accordion'), false);
	
	
	//设置头像
	// var bkgurl = '../static/images/bg1.jpg'
	// var bg = "url(\""+bkgurl+"\")";
	// console.log(bg)
	// $("#user-image").css("background-image",bg);
});

