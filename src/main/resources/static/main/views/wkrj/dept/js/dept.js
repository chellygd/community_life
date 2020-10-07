var treeTable; 
    layui.use(['admin','element', 'tree', 'layer', 'form', 'upload', 'treeGrid'], function () {
    	var $ = layui.jquery, tree = layui.tree;	
        var table = layui.table;
        var treeGrid = layui.treeGrid; //很重要
        var admin = layui.admin;
        var view = layui.view;
        var form = layui.form;
        treeTable = treeGrid.render({
       		url:'wkrjsystemnew/wkrjDept/getDeptAndSchoolGridList'
            ,elem: '#lay_dept_list'
            , cellMinWidth: 100
            , treeId: 'id'//树形id字段名称
            , treeUpId: 'pid'//树形父id字段名称
            , treeShowName: 'text'//以树形式显示的字段
            , cols: [[
                {type: 'checkbox'}
                , {field: 'text',  width: '40%', title: '部门名称'}
                , {field: 'dept_order',  width: '10%', title: '顺序'}
                , {field: 'dept_other',  width: '30%', title: '备注'}
                , {field: '',   title: '操作'}
            ]]
	       	,done: function(res, curr, count){
		  		var response = layui.setter.response;
		  		//登录状态失效，清除本地 access_token，并强制跳转到登入页
		  		if(res[response.statusName] == response.statusCode.logout){
		  			view.exit();
		  		}
		  	}
            , page: false
            ,id:'lay_dept_list'
            ,height: 'full-150'
            ,where: { //通过 request 头传递
			  		access_token: layui.data('layuiAdmin').access_token
			  	}
        });
       	//
        var active = {
        	//删除
        	dept_del:function(){
        		var checkStatus = treeGrid.checkStatus('lay_dept_list'); 
        		data = checkStatus.data;
        		var dept_id="";
        		if(data.length===1){
        			dept_id=data[0].id;
	            }else{
	            	layer.msg("请选中一条数据进行删除！");
	            	return;
	            }
        		layer.confirm('你确定要删除吗？',function(index){
	        		admin.req({
		        		url:'wkrjsystemnew/wkrjDept/delDept',
		        		type:'get',
		        		data:{id:dept_id},
		        		success:function(result){
		        			if(result.success){
		        				layer.msg(result.msg);
		        				treeGrid.reload('lay_dept_list');
	                			layer.close(index); //执行关闭 
		        			}else{
		        				layer.msg(result.msg);
	                			layer.close(index); //执行关闭 
		        			}
		        		}		        			        	
		        	 });
        		 });
        	}
        	,
        	dept_add:function(){
        		var checkStatus = treeGrid.checkStatus('lay_dept_list'); 
        		data = checkStatus.data;
        		var dept_parent_id="";
        		if(data.length==1){
        			dept_parent_id=data[0].id;
	            }
	            var data1=new Object();
	            data1.dept_parent_id=dept_parent_id;
        		admin.popup({
		          title: '添加组织机构'
		          ,area: ['550px', '550px']
		          ,id: 'LAY-dept-add'
		          ,success: function(layero, index){
		            view(this.id).render('wkrj/dept/dept_add',data1).done(function(){
		              form.render(null, 'layuiadmin-dept-add-list');
		              //监听提交
		              form.on('submit(layuiadmin-dept-add-submit)', function(data){
		                var field = data.field; //获取提交的字段
		                admin.req({
				        	url:'wkrjsystemnew/wkrjDept/addDept',
				        	data:field,
				        	type:'post',
				        	success:function(data){
				        		if(data.success){
				        			layer.msg("保存成功！");
		                			layer.close(index); //执行关闭 
		                			treeGrid.reload('lay_dept_list');
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
        	dept_update:function(){
        		var checkStatus = treeGrid.checkStatus('lay_dept_list'); 
        		var data = checkStatus.data;
        		if(data.length==1){
	            }else{
	            	layer.msg("请选中一条数据进行修改！");
	            	return;
	            }
        		admin.popup({
		          title: '修改组织机构'
		          ,area: ['550px', '550px']
		          ,id: 'LAY-dept-update'
		          ,success: function(layero, index){
		            view(this.id).render('wkrj/dept/dept_update',data[0]).done(function(){
		              form.render(null, 'layuiadmin-dept-update-list');
		              //监听提交
		              form.on('submit(layuiadmin-dept-update-submit)', function(data){
		                var field = data.field; //获取提交的字段
		                admin.req({
				        	url:'wkrjsystemnew/wkrjDept/updateDept',
				        	data:field,
				        	type:'post',
				        	success:function(data){
				        		if(data.success){
				        			layer.msg("保存成功！");
		                			layer.close(index); //执行关闭 
		                			treeGrid.reload('lay_dept_list');
				        		}else{
				        			layer.msg(data.msg);
				        		}
				        	},error:function(){
				        		layer.close(index); //执行关闭 
				        	}
				        }); 
		               
		              });
		            });
		          }
		        });
        	},
        	
        }
        $('.layui-btn.layuiadmin-btn-list').on('click', function(){
	  	      var type = $(this).data('type');
	  	      active[type] ? active[type].call(this) : '';
	  	});
        
    });