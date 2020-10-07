//整合tree
   layui.use(['eleTree','admin','table','form'], function(){
   		var eleTree = layui.eleTree
   			,view = layui.view
		    ,table = layui.table
		    ,form = layui.form
   			,admin = layui.admin;
   		//渲染左侧tree
		var tree=eleTree.render({
		    elem: '.ele1',
		    url: 'wkrjsystemnew/wkrjDept/getDeptTree_lazy?access_token='+layui.data('layuiAdmin').access_token,
		    lazy: true, 
		    load: function(data,callback) {
		    	getData_lazy(data.dept_id,callback)
		    },
		    request: {     // 对后台返回的数据格式重新定义
			    name: "dept_name",
			    key: "dept_id",
			},
			response: {
		        statusName: "code",
		        statusCode: 0,
		        dataName: "data"
		    },
		    showCheckbox: false,
		}); 
		eleTree.on("nodeClick(data1)",function(d) {
		    console.log(d.data.currentData.dept_id);    // 点击节点对应的数据
//		    console.log(d.event);   // event对象
//		    console.log(d.node);    // 点击的dom节点
//		    console.log(this);      // 与d.node相同
		    table.reload('LAY-user-list', {
    	        page: {
    	          curr: 1 //重新从第 1 页开始
    	        }
    	        ,where: {
    	        	deptId:d.data.currentData.dept_id
    	        }
    	      });
		    
		})
  		function getData_lazy(dept_id,callback){
  			admin.req({
  				url:'wkrjsystemnew/wkrjDept/getDeptTree_lazy',
  				data:{dept_id:dept_id},
  				success:function(data){
  					callback(data.data);
  				}
  			});
  		}
  		table.render({
			 elem: '#LAY-user-list'
			,url:'wkrjsystemnew/wkrjUser/getUserList'
			,cols: [[{type:'checkbox'}
		   	    /*,{fixed: '',title: '序号',width:'7%',templet: '#indexTpl'}	   	*/   	 
		   	      ,{field:'deptName', width:'14%', title: '单位名称',templet: '#dept_name'}
		   	      ,{field:'userName', width:'14%', title: '用户名'}
		   	      ,{field:'userRealname', width:'14%', title: '管理员姓名'}
		   	      ,{field:'userTel', width:'15%', title: '电话'}
		   	      ,{field:'userIsEnable', width:'10%', title: '禁用',templet: '#user_is_enable'}
		   	      ,{field:'userOrder', width:'7%', title: '排序'}
		   	      ,{field:'userRole', title: '角色',templet: '#role'}
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
		  	}
		  	,where: { //通过 request 头传递
		  		access_token: layui.data('layuiAdmin').access_token
		  	}
		  	,id:'LAY-user-list'
		  	,height: 'full-150'
	  });
	 var active = {
			 user_add:function(){
	        		admin.popup({
	  		          title: '添加用户'
	  		          ,area: ['550px', '600px']
	  		          ,id: 'LAY-user-add'
	  		          ,success: function(layero, index){
	  		            view(this.id).render('wkrj/user/user_add').done(function(){
	  		              form.render(null, 'layuiadmin-user-add-list');
	  		              //监听提交
	  		              form.on('submit(layuiadmin-user-add-submit)', function(data){
	  		                var field = data.field; //获取提交的字段
	  		                //角色
	  		                admin.req({
	  				        	url:'wkrjsystemnew/wkrjUser/addUser',
	  				        	data:field,
	  				        	type:'post',
	  				        	success:function(data){
	  				        		if(data.success){
	  				        			layer.msg("保存成功！");
	  				        			table.reload('LAY-user-list');
	  		                			layer.close(index); //执行关闭 
	  				        		}else{
	  				        			layer.msg(data.msg);
	  				        		}
	  				        	}
	  				        });
	  		               
	  		              });
	  		            });
	  		          }
	  		        });
	        	},
	        	user_update:function(){
	        		var checkStatus = table.checkStatus('LAY-user-list'); 
	        		var data = checkStatus.data;
	        		if(data.length==1){
		            }else{
		            	layer.msg("请选中一条数据进行修改！");
		            	return;
		            }
	        		admin.popup({
	  		          title: '修改用户'
	  		          ,area: ['550px', '600px']
	  		          ,id: 'LAY-user-update'
	  		          ,success: function(layero, index){
	  		            view(this.id).render('wkrj/user/user_update',data[0]).done(function(){
	  		              form.render(null, 'layuiadmin-user-update-list');
	  		              //监听提交
	  		              form.on('submit(layuiadmin-user-update-submit)', function(data){
	  		                var field = data.field; //获取提交的字段
	  		                admin.req({
	  				        	url:'wkrjsystemnew/wkrjUser/updateUser',
	  				        	data:field,
	  				        	type:'post',
	  				        	success:function(data){
	  				        		if(data.success){
	  				        			layer.msg("保存成功！");
	  				        			table.reload('LAY-user-list');
	  		                			layer.close(index); //执行关闭 
	  				        		}else{
	  				        			layer.msg(data.msg);
	  				        		}
	  				        	}
	  				        });
	  		               
	  		              });
	  		            });
	  		          }
	  		        });
	        	},
	        	user_del:function(){
	        		var checkStatus = table.checkStatus('LAY-user-list'); 
	        		var data = checkStatus.data;
	        		if(data.length<1){
	        			layer.msg("请选中数据进行删除！");
		            	return;
		            }
	        		var ids = new Array(); 	
		      		for(var i=0;i<data.length;i++){
						ids.push(data[i].userId);
					}
		      		layer.confirm('你确定要删除吗？',function(index){
		             	 admin.req({
			 	        		url:'wkrjsystemnew/wkrjUser/delUser',
			 	        		type:'get',
			 	        		data:{id:ids.join(",")},
			 	        		success:function(result){
			 	        			if(result.success){
			 	        				layer.msg(result.msg);
			 	        				table.reload('LAY-user-list');
			                 			layer.close(index); //执行关闭 
			 	        			}else{
			 	        				layer.msg(result.msg);
			                 			layer.close(index); //执行关闭 
			 	        			}
			 	        		}		        			        	
			 	        	});
		             });
	        	},
	        	user_disable:function(){
	        		var checkStatus = table.checkStatus('LAY-user-list'); 
	        		var data = checkStatus.data;
	        		if(data.length<1){
	        			layer.msg("请选中数据进行禁用！");
		            	return;
		            }
	        		var ids = new Array(); 	
		      		for(var i=0;i<data.length;i++){
						ids.push(data[i].userId);
					}
		      		layer.confirm('你确定要禁用吗？',function(index){
		             	 admin.req({
			 	        		url:'wkrjsystemnew/wkrjUser/forbiddenUser',
			 	        		type:'get',
			 	        		data:{id:ids.join(","),flag:1},
			 	        		success:function(result){
			 	        			if(result.success){
			 	        				layer.msg(result.msg);
			 	        				table.reload('LAY-user-list');
			                 			layer.close(index); //执行关闭 
			 	        			}else{
			 	        				layer.msg(result.msg);
			                 			layer.close(index); //执行关闭 
			 	        			}
			 	        		}		        			        	
			 	        	});
		             });
	        	},
	        	user_enable:function(){
	        		var checkStatus = table.checkStatus('LAY-user-list'); 
	        		var data = checkStatus.data;
	        		if(data.length<1){
	        			layer.msg("请选中数据进行启用！");
		            	return;
		            }
	        		var ids = new Array(); 	
		      		for(var i=0;i<data.length;i++){
						ids.push(data[i].userId);
					}
		      		layer.confirm('你确定要启用吗？',function(index){
		             	 admin.req({
			 	        		url:'wkrjsystemnew/wkrjUser/forbiddenUser',
			 	        		type:'get',
			 	        		data:{id:ids.join(","),flag:0},
			 	        		success:function(result){
			 	        			if(result.success){
			 	        				layer.msg(result.msg);
			 	        				table.reload('LAY-user-list');
			                 			layer.close(index); //执行关闭 
			 	        			}else{
			 	        				layer.msg(result.msg);
			                 			layer.close(index); //执行关闭 
			 	        			}
			 	        		}		        			        	
			 	        	});
		             });
	        	},
	        	user_repassword:function(){
	        		var checkStatus = table.checkStatus('LAY-user-list'); 
	        		var data = checkStatus.data;
	        		if(data.length<1){
	        			layer.msg("请选中数据进行重置密码！");
		            	return;
		            }
	        		var ids = new Array(); 	
		      		for(var i=0;i<data.length;i++){
						ids.push(data[i].userId);
					}
		      		layer.confirm('你确定要重置密码吗？新密码：123',function(index){
		             	 admin.req({
			 	        		url:'wkrjsystemnew/wkrjUser/repeatPw',
			 	        		type:'get',
			 	        		data:{id:ids.join(","),flag:0},
			 	        		success:function(result){
			 	        			if(result.success){
			 	        				layer.msg(result.msg);
			 	        				table.reload('LAY-user-list');
			                 			layer.close(index); //执行关闭 
			 	        			}else{
			 	        				layer.msg(result.msg);
			                 			layer.close(index); //执行关闭 
			 	        			}
			 	        		}		        			        	
			 	        	});
		             });
	        	},
	        	user_allshow:function(){
	        		/*table.reload('LAY-user-list',
	        				
	        				where:{dept_id:""}
	        		);*/
	        		//执行重载
	        	      table.reload('LAY-user-list', {
	        	        page: {
	        	          curr: 1 //重新从第 1 页开始
	        	        }
	        	        ,where: {
	        	        	deptId:""
	        	        }
	        	      });
	        		
	        	}
	        	
	   		}
			 $('.layui-btn.layuiadmin-btn-list').on('click', function(){
			      var type = $(this).data('type');
			      active[type] ? active[type].call(this) : '';
			});
  });