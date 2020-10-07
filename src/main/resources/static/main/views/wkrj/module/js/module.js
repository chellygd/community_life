var id = "";
var treeTable; 
    layui.use(['admin','element', 'tree', 'layer', 'form', 'upload', 'treeGrid'], function () {
    	var $ = layui.jquery, tree = layui.tree;	
        var table = layui.table;
        var treeGrid = layui.treeGrid; //很重要
        var admin = layui.admin;
        var view = layui.view;
        var form = layui.form;
       	treeTable = treeGrid.render({
       		url:'wkrjsystemnew/wkrjModule/getGridInfo'
            ,elem: '#lay_module_list'
            , cellMinWidth: 100
            , treeId: 'module_id'//树形id字段名称
            , treeUpId: 'module_parent_id'//树形父id字段名称
            , treeShowName: 'module_name'//以树形式显示的字段
            , cols: [[
                {type: 'checkbox'}
                /*,{fixed: '',title: '#',width:50,templet: '#indexTpl1'}*/
                , {field: 'module_id',   title: '操作',  width: '15%', toolbar: '#barModule'}
                , {field: 'module_name',  width: '20%', title: '菜单名称'}
                , {field: 'module_url',  width: '30%', title: '菜单地址'}
                , {field: 'module_is_display',  width: '10%', title: '状态'}
                , {field: 'module_order', title: '菜单顺序'}		                		                
                , {field: 'module_icon',  title: '图标',templet: '#module_icon_f'}
            ]]
	       	,done: function(res, curr, count){
		  		var response = layui.setter.response;
		  		//登录状态失效，清除本地 access_token，并强制跳转到登入页
		  		if(res[response.statusName] == response.statusCode.logout){
		  			view.exit();
		  		}
		  	}
            , page: false
            ,id:'lay_module_list'
        	,height: 'full-150'
        	//,width:'100%'
            ,where: { //通过 request 头传递
			  		access_token: layui.data('layuiAdmin').access_token
			  	}
        });
      //监听工具条
       	treeGrid.on('tool(lay_module_list)', function(obj){
          var data = obj.data;
          if(obj.event === 'deleteModule'){
            layer.confirm('你确定要删除吗？',function(index){
            	var module_id=data.module_id
            	 admin.req({
	        		url:'wkrjsystemnew/wkrjModule/delModule',
	        		type:'get',
	        		data:{id:module_id},
	        		success:function(result){
	        			if(result.success){
	        				layer.msg(result.msg);
	        				reload();
                			layer.close(index); //执行关闭 
	        			}else{
	        				layer.msg(result.msg);
                			layer.close(index); //执行关闭 
	        			}
	        		}		        			        	
	        	});
            });
          } else if(obj.event === 'setPre'){//设置权限
        	  var module_id=data.module_id;
        	  id = module_id;
        	  layui.use('table', function(){
        			var table = layui.table;
        			var form = layui.form;
        			 table.reload('lay_permission_list', {
        	                page: {
        	                  curr: 1 //重新从第 1 页开始
        	                }
        	                ,where: {
        	                	  module_id: id
        	                }
        	              });
        			 $("#left_module").addClass("layui-col-md8");
        			 $("#right_per").show();
        	  });
        	  
          }
        });
       	//
        var active = {
        	module_add:function(){
        		var checkStatus = treeGrid.checkStatus('lay_module_list'); 
        		data = checkStatus.data;
        		var module_id="";
        		if(data.length==1){
	            	 module_id=data[0].module_id;
	            }
	            var data1=new Object();
	            data1.module_id=module_id;
        		admin.popup({
		          title: '添加模块'
		          ,area: ['550px', '550px']
		          ,id: 'LAY-module-add'
		          ,success: function(layero, index){
		            view(this.id).render('wkrj/module/module_add',data1).done(function(){
		              form.render(null, 'layuiadmin-module-add-list');
		              //监听提交
		              form.on('submit(layuiadmin-module-add-submit)', function(data){
		                var field = data.field; //获取提交的字段
		                admin.req({
				        	url:'wkrjsystemnew/wkrjModule/addModule',
				        	data:field,
				        	type:'post',
				        	success:function(data){
				        		if(data.success){
				        			layer.msg("保存成功！");
				        			reload();
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
        	module_update:function(){
        		var checkStatus = treeGrid.checkStatus('lay_module_list'); 
        		var data = checkStatus.data;
        		if(data.length==1){
	            	 module_id=data[0].module_id;
	            }else{
	            	layer.msg("请选中一条数据进行修改！");
	            	return;
	            }
        		admin.popup({
		          title: '修改模块'
		          ,area: ['550px', '550px']
		          ,id: 'LAY-module-update'
		          ,success: function(layero, index){
		            view(this.id).render('wkrj/module/module_update',data[0]).done(function(){
		              form.render(null, 'layuiadmin-module-update-list');
		              //监听提交
		              form.on('submit(layuiadmin-module-update-submit)', function(data){
		                var field = data.field; //获取提交的字段
		                admin.req({
				        	url:'wkrjsystemnew/wkrjModule/updateModule',
				        	data:field,
				        	type:'post',
				        	success:function(data){
				        		if(data.success){
				        			layer.msg("保存成功！");
				        			reload();
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
        	
        }
        function reload() {
        	layui.use(['table','treeGrid'], function () {
	        	layui.treeGrid.reload('lay_module_list');
        	});
	    }
        function reload1() {
        	layui.use(['table','treeGrid'], function () {
	        	layui.table.reload('lay_permission_list');
        	});
	    }
	layui.use('table', function(){
		var table = layui.table;
		var form = layui.form;
        table.render({
			 elem: '#lay_permission_list'
			,url:'wkrjsystemnew/wkrjPermission/getPermissionList'
			,cols: [[{type: 'checkbox'}
		   	     ,{fixed: '',title: '#',width:50,templet: '#indexTpl2'}
		   	     ,{field:'permIcon', width:60, title: '图标',templet: '#per_icon_g'}
		   	     ,{field:'permName', width:100, title: '名称'}
		   	     ,{field:'permFlag', width:200, title: '权限'}
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
		  		,module_id:id
		  	}
		  	,id:'lay_permission_list'
		  	,height: 'full-150'
	  });
	 var active = {
			 permission_add:function(){
	        		if(isObjEmpty(id)){
	        			layer.msg("请选择设置权限模块！");
	        			return;
	        		}
	        		var data1=new Object();
		            data1.moduleId=id;
	        		admin.popup({
	  		          title: '添加权限'
	  		          ,area: ['550px', '400px']
	  		          ,id: 'LAY-per-add'
	  		          ,success: function(layero, index){
	  		            view(this.id).render('wkrj/module/permission_add',data1).done(function(){
	  		              form.render(null, 'layuiadmin-per-add-list');
	  		              //监听提交
	  		              form.on('submit(layuiadmin-per-add-submit)', function(data){
	  		                var field = data.field; //获取提交的字段
	  		                admin.req({
	  				        	url:'wkrjsystemnew/wkrjPermission/addPermission',
	  				        	data:field,
	  				        	type:'post',
	  				        	success:function(data){
	  				        		if(data.success){
	  				        			layer.msg("保存成功！");
	  				        			reload1();
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
	        	permission_update:function(){
	        		var checkStatus = table.checkStatus('lay_permission_list'); 
	        		var data = checkStatus.data;
	        		if(data.length==1){
		            }else{
		            	layer.msg("请选中一条数据进行修改！");
		            	return;
		            }
	        		admin.popup({
	  		          title: '修改权限'
	  		          ,area: ['550px', '400px']
	  		          ,id: 'LAY-per-update'
	  		          ,success: function(layero, index){
	  		            view(this.id).render('wkrj/module/permission_update',data[0]).done(function(){
	  		              form.render(null, 'layuiadmin-per-update-list');
	  		              //监听提交
	  		              form.on('submit(layuiadmin-per-update-submit)', function(data){
	  		                var field = data.field; //获取提交的字段
	  		                admin.req({
	  				        	url:'wkrjsystemnew/wkrjPermission/updatePermission',
	  				        	data:field,
	  				        	type:'post',
	  				        	success:function(data){
	  				        		if(data.success){
	  				        			layer.msg("保存成功！");
	  				        			reload1();
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
	        	permission_del:function(){
	        		var checkStatus = table.checkStatus('lay_permission_list'); 
	        		var data = checkStatus.data;
	        		if(data.length<1){
	        			layer.msg("请选中数据进行删除！");
		            	return;
		            }
	        		var ids = new Array(); 	
		      		for(var i=0;i<data.length;i++){
						ids.push(data[i].permId);
					}
		      		layer.confirm('你确定要删除吗？',function(index){
		             	 admin.req({
			 	        		url:'wkrjsystemnew/wkrjPermission/delPermission',
			 	        		type:'get',
			 	        		data:{id:ids.join(",")},
			 	        		success:function(result){
			 	        			if(result.success){
			 	        				layer.msg(result.msg);
			 	        				reload1();
			                 			layer.close(index); //执行关闭 
			 	        			}else{
				        				layer.msg(result.msg);
			                			layer.close(index); //执行关闭 
				        			}
			 	        		}		        			        	
			 	        	});
		             });
	        	}
	   		}
			 $('.layui-btn.layuiadmin-btn-list').on('click', function(){
			      var type = $(this).data('type');
			      active[type] ? active[type].call(this) : '';
			});
    	});
    $('.layui-btn.layuiadmin-btn-list').on('click', function(){
	      var type = $(this).data('type');
	      active[type] ? active[type].call(this) : '';
	});
    });