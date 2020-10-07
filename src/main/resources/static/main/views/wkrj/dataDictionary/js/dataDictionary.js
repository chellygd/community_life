var treeTable; 
    layui.use(['admin','element', 'tree', 'layer', 'form', 'upload', 'treeGrid'], function () {
    	var $ = layui.jquery, tree = layui.tree;	
        var table = layui.table;
        var treeGrid = layui.treeGrid; //很重要
        var admin = layui.admin;
        var view = layui.view;
        var form = layui.form;
        treeTable = treeGrid.render({
       		url:'wkrjsystemnew/wkrjDataDictionary/getDataDictionary'
            ,elem: '#lay_data_list'
            , cellMinWidth: 100
            , treeId: 'id'//树形id字段名称
            , treeUpId: 'typeparentid'//树形父id字段名称
            , treeShowName: 'text'//以树形式显示的字段
        	, cols: [[
	             {type: 'checkbox'}
	             /*,{fixed: '',title: '序号',width:'5%',templet: '#number'}*/
	             , {field: 'text',  width: '40%', title: '部门名称'}
	             , {field: 'id',  width: '20%', title: '字典id'}
	             , {field: 'typecode',   title: '字典编码'}
	         ]]
	       	,done: function(res, curr, count){
		  		var response = layui.setter.response;
		  		//登录状态失效，清除本地 access_token，并强制跳转到登入页
		  		if(res[response.statusName] == response.statusCode.logout){
		  			view.exit();
		  		}
		  	}
            , page: false
            ,id:'lay_data_list'
            ,height: 'full-150'
            ,where: { //通过 request 头传递
			  		access_token: layui.data('layuiAdmin').access_token
			  	}
        });
       	//
        var active = {
        	
        	data_add:function(){
        		var checkStatus = treeGrid.checkStatus('lay_data_list'); 
        		data = checkStatus.data;
        		//console.dir(data)
        		var typeparentid="";
        		if(data.length==1){
        			typeparentid=data[0].id;
	            }
	            var data1=new Object();
	            data1.typeparentid=typeparentid;
        		admin.popup({
		          title: '添加数据字典'
		          ,area: ['550px', '550px']
		          ,id: 'LAY-data-add'
		          ,success: function(layero, index){
		            view(this.id).render('wkrj/dataDictionary/dataDictionary_add',data1).done(function(){
		              form.render(null, 'layuiadmin-data-add-list');
		              //监听提交
		              form.on('submit(layuiadmin-data-add-submit)', function(data){
		                var field = data.field; //获取提交的字段
		                admin.req({
				        	url:'wkrjsystemnew/wkrjDataDictionary/addDataDictionary',
				        	data:field,
				        	type:'post',
				        	success:function(data){
				        		if(data.success){
				        			layer.msg("保存成功！");
		                			layer.close(index); //执行关闭 
		                			treeGrid.reload('lay_data_list');
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
        	data_update:function(){
        		var checkStatus = treeGrid.checkStatus('lay_data_list'); 
        		var data = checkStatus.data;
        		if(data.length==1){
	            }else{
	            	layer.msg("请选中一条数据进行修改！");
	            	return;
	            }
        		admin.popup({
		          title: '修改组织机构'
		          ,area: ['550px', '550px']
		          ,id: 'LAY-data-update'
		          ,success: function(layero, index){
		            view(this.id).render('wkrj/dataDictionary/dataDictionary_update',data[0]).done(function(){
		              form.render(null, 'layuiadmin-data-update-list');
		              //监听提交
		              form.on('submit(layuiadmin-data-update-submit)', function(data){
		                var field = data.field; //获取提交的字段
		                admin.req({
				        	url:'wkrjsystemnew/wkrjDataDictionary/updateDataDictionary',
				        	data:field,
				        	type:'post',
				        	success:function(data){
				        		if(data.success){
				        			layer.msg("保存成功！");
		                			layer.close(index); //执行关闭 
		                			treeGrid.reload('lay_data_list');
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
        	//删除
        	data_del:function(){
        		var checkStatus = treeGrid.checkStatus('lay_data_list'); 
        		data = checkStatus.data;
        		if(data.length===1){
	            }else{
	            	layer.msg("请选中一条数据进行删除！");
	            	return;
	            }
        		layer.confirm('你确定要删除吗？',function(index){
	        		admin.req({
		        		url:'wkrjsystemnew/wkrjDataDictionary/delDataDictionary',
		        		type:'get',
		        		data:{id:data[0].id},
		        		success:function(result){
		        			if(result.success){
		        				layer.msg(result.msg);
		        				treeGrid.reload('lay_data_list');
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