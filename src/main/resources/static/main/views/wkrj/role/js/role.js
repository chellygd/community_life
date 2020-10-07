var role_id="";	
var xtree1;		
layui.use(['admin','table','form'], function(){
    var $ = layui.$
    ,admin = layui.admin
    ,view = layui.view
    ,table = layui.table
    ,form = layui.form;
    getRoleTree();
    //
    table.render({
			 elem: '#LAY-role-list'
			,url:'wkrjsystemnew/wkrjRole/getRoleList'
			,cols: [[{type:'checkbox'}
		   	     /*,{fixed: '',title: '序号',width:'8%',templet: '#indexTpl1'}*/
		   	   	 ,{fixed: '', width:'20%', title: '操作',align:'center', toolbar: '#barDemo'}
		   	     ,{field:'roleName', width:'20%', title: '角色名称'}
		   	     ,{field:'roleType', width:'20%', title: '类型',templet: '#rolerType'}
		   	     ,{field:'roleOther',  title: '备注'}
	   	    ]]
			,page: true
			,limit:15
			,limits:[15,30,45,60,75]
			,done: function(res, curr, count){
		  		var response = layui.setter.response;
		  		//登录状态失效，清除本地 access_token，并强制跳转到登入页
		  		if(res[response.statusName] == response.statusCode.logout){
		  			view.exit();
		  		}
		  		var o = document.getElementById("box1");
		  		var h = o.clientHeight||o.offsetHeight;
		  		document.getElementById("xtree1").style.height=(h-68)+"px";
		  	}
		  	,where: { //通过 request 头传递
		  		access_token: layui.data('layuiAdmin').access_token
		  	}
		  	,id:'LAY-role-list'
		  	,height: 'full-150'
		  	//,width:'full'
	  });
  //监听工具条
   	table.on('tool(LAY-role-list)', function(obj){
      var data = obj.data;
      if(obj.event === 'warrant'){
    	  role_id="";
    	  role_id = data.roleId;
	      getRoleTree();
      } 
    });
    var active = {
    		//删除角色
      role_del: function(){
        var checkStatus = table.checkStatus('LAY-role-list')
        ,data = checkStatus.data; //得到选中的数据
        if(data.length === 0){
          return layer.msg('请选择数据');
        }
        var ids = new Array(); 	
     	for(var i=0;i<data.length;i++){
			ids.push(data[i].roleId);
		}
        layer.confirm('确定删除吗？', function(index) {
	          admin.req({
		       		url:'wkrjsystemnew/wkrjRole/delRole',
		       		type:'get',
		       		data:{id:ids.join(",")},
		       		success:function(result){
		       			if(result.success){
		       				table.reload('LAY-role-list');
		       				layer.close(index); //执行关闭 
		          			layer.msg('已删除');
		       			}else{
		       				layer.msg(result.msg);
		       				layer.close(index); //执行关闭 
		       			}
		       		}		        			        	
	       	});
          
        });
      }
      
      //添加
      ,role_add: function(othis){
        admin.popup({
          title: '添加角色'
          ,area: ['550px', '400px']
          ,id: 'LAY-role-add'
          ,success: function(layero, index){
            view(this.id).render('wkrj/role/role_add').done(function(){
              form.render(null, 'layuiadmin-role-add-list');
              
              //监听提交
              form.on('submit(layuiadmin-role-add-submit)', function(data){
                var field = data.field; //获取提交的字段
                //提交 Ajax 成功后，关闭当前弹层并重载表格
                admin.req({
		        	url:'wkrjsystemnew/wkrjRole/addRole',
		        	data:field,
		        	type:'post',
		        	success:function(data){
		        		if(data.success){
		        			layer.msg("保存成功！");
		        			table.reload('LAY-role-list');
                			layer.close(index); //执行关闭 
		        		}else{
		        			layer.msg(data.msg);
		        		}
		        	}
		        });
                //$.ajax({});
                layui.table.reload('LAY-role-list'); //重载表格
                layer.close(index); //执行关闭 
              });
            });
          }
        });
      }
      //修改
      ,role_update: function(othis){
        var checkStatus = table.checkStatus('LAY-role-list'); 
    	var data = checkStatus.data;
    	if(data.length==1){
        }else{
          	layer.msg("请选中一条数据进行修改！");
          	return;
        }
        admin.popup({
          title: '修改角色'
          ,area: ['550px', '400px']
          ,id: 'LAY-role-update'
          ,success: function(layero, index){
            view(this.id).render('wkrj/role/role_update',data[0]).done(function(){
              form.render(null, 'layuiadmin-role-update-list');
              
              //监听提交
              form.on('submit(layuiadmin-role-update-submit)', function(data){
                var field = data.field; //获取提交的字段
                //提交 Ajax 成功后，关闭当前弹层并重载表格
                admin.req({
		        	url:'wkrjsystemnew/wkrjRole/updateRole',
		        	data:field,
		        	type:'post',
		        	success:function(data){
		        		if(data.success){
		        			layer.msg("保存成功！");
		        			table.reload('LAY-role-list');
                			layer.close(index); //执行关闭 
		        		}else{
		        			layer.msg(data.msg);
		        		}
		        	}
		        });
                //$.ajax({});
                layer.close(index); //执行关闭 
              });
            });
          }
        });
      }
      ,role_copy:function(){
      	 var checkStatus = table.checkStatus('LAY-role-list');          
      	 data = checkStatus.data; 
      	 if(data.length === 1){
          
         }else{
         	return layer.msg('请选择一条数据进行复制');
         } 
      	 layer.confirm(' 确定要复制吗？', function(index){
        	admin.req({
        		url:'wkrjsystemnew/wkrjRole/copyRole',
        		type:'get',
        		data:{role_id:data[0].roleId},
        		success:function(result){
        			if(result.success){
        				layer.msg("复制成功");
        				table.reload('LAY-role-list');
        			}else{
        				layer.msg(result.msg);
        			}
        		}		        			        	
        	});       
	        layer.close(index);
		  });				 
      }
      ,per_save: function(){
    	  
    	  if(isObjEmpty(role_id)){
    	      	layer.msg("请选择授权对象！！");
    	      	return;
	      }
	      /*if(xtree1.GetChecked().length==0){
	      	layer.msg("请选择权限！！");
	      	return;
	      } */
	       var data1 = xtree1.GetChecked(); 
	       var data2=xtree1.GetChecked1();
	       var perm_ids="";
	       for (var i = 0; i < data1.length; i++) {
	    		 	if(perm_ids !=""){
	    		 	perm_ids+=","+data1[i].value ;
	    		 	}else{
	    		 	perm_ids=data1[i].value;
	    		 	}
	       } 
	       var menu_ids="";
	       for (var i = 0; i < data2.length; i++) {	
     		 	if(menu_ids !=""){
     		 	menu_ids+=","+data2[i].value ;
     		 	}else{
     		 	menu_ids=data2[i].value;
     		 	}
	       }
	       var menu_order="";
	       for (var i = 0; i < data2.length; i++) {	
	     		 	if(menu_order !=""){
	     		 	menu_order+=","+data2[i].getAttribute('menu_order');
	     		 	}else{
	     		 	menu_order=data2[i].getAttribute('menu_order');
	     		 	}
	       }
	       admin.req({
			 	url:'wkrjsystemnew/wkrjRole/setMenuPermission',
			 	data:{rolerId:role_id,perm_ids:perm_ids,
			 	menu_ids:menu_ids,menu_order:menu_order},
			 	success:function(result){
			 		layer.msg(result.msg);  
			 		getRoleTree();
			 	}		 				 
			 });
      }
    }; 

    $('.layui-btn.layuiadmin-btn-list').on('click', function(){
      var type = $(this).data('type');
      active[type] ? active[type].call(this) : '';
    });
    
    function getRoleTree(){
    	$("#xtree1").html("");
		admin.req({
			url:'wkrjsystemnew/wkrjRole/getMenuPermission',
			type:'get',
			data:{role_id:role_id},
			success:function(result){
				settree(result.data);
			}
		});
 	}
	 
	 function settree(result){
	 	 xtree1 = new layuiXtree({
	 	            elem: 'xtree1'   
	 	            , form: form
	 	            , ckall: false
	 	            ,isopen: true
	 	            ,data:result
	 	                  
	       });      	
	 }
  });
