<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	$(function(){
	
		var perms = "${sessionScope.userPermission}";
		var userTypeCount = "${sessionScope.userCountType}";
		var pp ="";
		
		if(1!=userTypeCount){
			
			if(top.$('#mainTabs').length>0){
				pp = top.$('#mainTabs').tabs('getSelected');
			
			try{
	   			var iframe = pp.find("iframe");
	   			var btns = iframe[0].contentWindow.$(".easyui-linkbutton");
				
				btns.each(function(){
				
					var perm = $(this).attr('ekper');
					
					if(typeof(perm)!="undefined"){
						if(perms.indexOf(perm)>=0){
							$(this).show();
						}else{
							$(this).hide();
						}
					}
					
				});
						
			}catch(e){}
		}
		}
		
	});
</script>

