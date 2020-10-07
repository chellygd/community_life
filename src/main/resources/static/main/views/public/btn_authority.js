/**

 @Name：按钮权限控制
 @Author：CXR
 @Site：WKRJ
 @License：
    
 */
/*<script type="text/javascript" src="./plug-in/jquery/jquery-1.9.1.min.js"></script> 
<script type="text/javascript" src="./btn_authority.js"></script>*/
layui.use(['admin', 'useradmin', 'table'], function(){
	view = layui.view,
	admin = layui.admin;
	
	$(function(){
		
		try {
			var btns=$(".layui-btn");
			btns.each(function(){
				var perm_this=$(this);
				var perm = perm_this.attr("ekper");
				if(typeof(perm) != "undefined"){
					admin.req({
						url:'wkrjsystem/wkrjAuthority/judgeAuthority',
						data:{perm:perm},
						done:function(res){
							if(res.data.Authority=="2"){
								perm_this.show();
							}else if(res.data.Authority=="1"){
								perm_this.hide();
							}else{
								//layer.msg("按钮权限，用户未登录，跳转退出！（功能）");
								view.exit();
							}
						}
					});
				}
			});
		} catch (e) {
		}
		
	});	
	
});
  

