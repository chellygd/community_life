//生成左右两个tree    
layui.use(['admin', 'element', 'tree', 'layer', 'form', 'upload', 'selectForm'], function () {
    var $ = layui.jquery, tree = layui.tree;
    var admin = layui.admin;
    var form = layui.form;
    var selectForm = layui.selectForm;//将form 对select的渲染重新修改重新
    $(function () {
        sjcd();
        selectForm.render();
        jianting();
    });

    function jianting() {
        form.on('submit(add_class_save)', function (data) {
            var d = data.field;
            if ($("#menu_id").val() == "1") {
                submitData(d);
            } else {
                layer.msg("请选择右侧菜单进行添加！");
            }
            return false;
        });
        form.on('submit(update_class_save)', function (data) {
            var d = data.field;
            if ($("#menu_id").val() == "1" || $("#menu_id").val() == "" || $("#menu_id").val() == null || $("#menu_id").val() == undefined) {
                layer.msg("请选择左侧菜单进行修改！");
            } else {
                submitData1(d);
            }
            return false;
        });
    }

    function sjcd() {
        layui.formSelects.data('example6_5', 'server', {
            url: 'wkrjsystemnew/wkrjMenu/getTreeInfo',
            data: {access_token: layui.data('layuiAdmin').access_token},
            success: function () {
            }
        });
    }

    $("#deleteModule").click(function () {
        var module_id = $("#menu_id").val();
        if (module_id == "" || module_id == undefined || module_id == null || $("#menu_id").val() == "1") {
            layer.msg("请选择左侧菜单进行删除！！");
            return false;
        }
        admin.req({
            url: 'wkrjsystemnew/wkrjMenu/delMenu',
            data: {id: module_id},
            success: function (result) {
                if (result.success) {
                    layer.msg(result.msg);
                    $("#center_menu input").val("");
                    //sjcd();
                    //getLeftTree();
                    layui.index.render();
                } else {
                    layer.msg(result.msg);
                }
            }

        });
        return false;
    });

    function submitData1(data) {
        data.access_token = layui.data('layuiAdmin').access_token;
        $.ajax({
            url: 'wkrjsystemnew/wkrjMenu/updateMenu',
            data: data,
            type: 'post',
            success: function (json) {
                try {
                    json = eval('(' + json + ')');
                } catch (e) {
                }
                if (json.success) {
                    layer.msg("修改成功");
                    layui.index.render();
                } else {
                    layer.msg(json.msg);
                }
            }
        });
    }

    function submitData(data) {
        data.access_token = layui.data('layuiAdmin').access_token;
        $.ajax({
            url: 'wkrjsystemnew/wkrjMenu/addMenu',
            data: data,
            type: 'post',
            success: function (json) {
                //sjcd();
                try {
                    json = eval('(' + json + ')');
                } catch (e) {
                }
                if (json.success) {
                    layer.msg("添加成功");
                    layui.index.render();
                } else {
                    layer.msg(json.msg);
                }
            }
        });
    }

    getLeftTree();
    admin.req({
        url: 'wkrjsystemnew/wkrjMenu/getGridInfo',
        success: function (result) {
            tree.render({
                elem: '#rightTree'
                , data: result.data
                , showLine: false
                , click: function (obj) {
                    var node = obj.data;
                    $("#uploadfile1").empty();
                    $("#uploadfile").empty();
                    var is = "0";
                    if (node.is) {
                        is = "1";
                    }
                    $("#menu_id").val("1");
                    $("#menu_name").val(node.module_name);
                    $("#menu_order").val(node.module_order);
                    $("#menu_other").val(node.module_other);
                    $("#menu_icon").val(node.iconCls);
                    if (isObjNotEmpty(node.module_icon_new)) {
                        $("#menu_icon_new").val(node.module_icon_new);
                        $("#module_icon_new").val(node.module_icon_new);
                    } else {
                        $("#menu_icon_new").val("");
                        $("#module_icon_new").val("");
                    }
                    //selectForm.render('select');
                    if (node.iconCls != "" && node.iconCls != null && node.iconCls != undefined) {
                        $("#uploadfile").append("<img alt='图片不存在' height='50' width='50' src='" + node.iconCls + "'>");
                    }

                    //$("input[name='menu[0].menuIsDisplay'][value="+is+"] ").attr("checked",true);
                    var radio = document.getElementsByName("menu[0].menuIsDisplay");
                    var radioLength = radio.length;
                    for (var i = 0; i < radioLength; i++) {
                        if (is == radio[i].value) {
                            $(radio[i]).next().click();
                        }

                    }
                    selectForm.render('radio');
                    $("#module_view").val(node.module_url);
                    $("#module_order").val(node.module_order);
                    $("#module_name").val(node.module_name);
                    $("#module_id").val(node.module_id);
                    $("#module_icon").val(node.module_icon);
                    if (node.module_icon != "" && node.module_icon != null && node.module_icon != undefined) {
                        $("#uploadfile1").append("<img alt='图片不存在' height='50' width='50' src='" + node.module_icon + "'>");
                    }
                    selectForm.render();
                }
            });
        }
    });

});
layui.use(['upload', 'layer'], function () {
    var upload = layui.upload, layer = layui.layer;
    var form = layui.form;
    //自定义验证
    /*form.verify({
        number_new: function(value, item){ //value：表单的值、item：表单的DOM对象
          if(value<0){
            return '不能小于0';
          }
        }
    });*/
    //普通图片上传
    var uploadInst = upload.render({
        elem: '#module_img',//绑定的元素
        url: 'wkrjsystemnew/upload/uploadModule?access_token=' + layui.data('layuiAdmin').access_token,
        //data: {access_token: layui.data('layuiAdmin').access_token},
        auto: true,//是否自动上传
        accept: "images",//指定允许上传的文件类型
        multiple: true,//支持多文件上传
        size: 1024 * 2,
        done: function (res, index, upload) { //假设code=0代表上传成功
            if (isObjNotEmpty(res.data)) {
                if (res.data.length > 0) {
                    $("#menu_icon").val(res.data[0].fileurl);
                    $("#uploadfile").html("<img height='50px' width='50px' src='" + res.data[0].fileurl + "'>");
                }
            }
        }
    });
});

function getLeftTree() {
    layui.use(['admin', 'element', 'tree', 'layer', 'form', 'upload', 'selectForm'], function () {
        var $ = layui.jquery, tree = layui.tree;
        var admin = layui.admin;
        var form = layui.form;
        var selectForm = layui.selectForm;//将form 对select的渲染重新修改重新
        admin.req({
            url: 'wkrjsystemnew/wkrjMenu/getTreeInfo',
            success: function (result) {
                $("#leftTree").html("");
                tree.render({
                    elem: '#leftTree'
                    , data: result.data
                    , showLine: false
                    , click: function (obj) {
                        var node = obj.data;
                        $("#uploadfile").empty();
                        $("#uploadfile1").empty();
                        var is = "0";
                        if (node.is) {
                            is = "1";
                        }
                        $("#menu_id").val(node.menu_id);//
                        $("#menu_name").val(node.menu_name);
                        $("#menu_order").val(node.menu_order);
                        $("#menu_other").val(node.menu_other);
                        $("#menu_icon").val(node.menu_icon);
                        if (node.menu_icon != "" && node.menu_icon != null && node.menu_icon != undefined) {
                            $("#uploadfile").append("<img alt='图片不存在' height='50' width='50' src='" + node.menu_icon + "'>");
                        }
                        if (isObjNotEmpty(node.menu_icon_new)) {
                            $("#menu_icon_new").val(node.menu_icon_new);
                            //$("#module_icon_new").val(node.module_icon_new);
                        } else {
                            $("#menu_icon_new").val("");
                            //$("#module_icon_new").val("");
                        }
                        //$("#menu_parent_id").val(node.menu_parent_id);//
                        //$("input[name='menu[0].menuIsDisplay'][value="+is+"] ").attr("checked",true);
                        var radio = document.getElementsByName("menu[0].menuIsDisplay");
                        var radioLength = radio.length;
                        for (var i = 0; i < radioLength; i++) {
                            if (is == radio[i].value) {
                                $(radio[i]).next().click();
                            }

                        }
                        selectForm.render('select');
                        $("#module_view").val(node.attributes.wkrjModule.module_url);
                        $("#module_order").val(node.attributes.wkrjModule.module_order);
                        $("#module_name").val(node.attributes.wkrjModule.module_name);
                        $("#module_id").val(node.attributes.wkrjModule.module_id);
                        $("#module_icon").val(node.attributes.wkrjModule.module_icon);
                        $("#module_icon_new").val(node.attributes.wkrjModule.module_icon_new);
                        if (node.attributes.wkrjModule.module_icon != "" && node.attributes.wkrjModule.module_icon != null && node.attributes.wkrjModule.module_icon != undefined) {
                            $("#uploadfile1").append("<img alt='图片不存在' height='50' width='50' src='" + node.attributes.wkrjModule.module_icon + "'>");
                        }
                        layui.formSelects.value('example6_5', [node.menu_parent_id]);
                        selectForm.render();
                    }
                });
            }
        });
    });
}

layui.use(['upload', 'layer', 'admin', 'selectForm'], function () {
    var admin = layui.admin;
    var selectForm = layui.selectForm;//将form 对select的渲染重新修改重新
    admin.req({
        url: 'wkrjsystemnew/wkrjMenu/getIcon',
        success: function (data) {
            var html = " <option value=''>请选择图标</option>";
            if (isObjNotEmpty(data.data)) {
                $.each(data.data, function (key, value) {
                    html += "<option value='" + value.name + "' icon='" + value.icon + "'>" + value.icon + "</option>";
                });
            }
            $("#module_icon_new").append(html);
            $("#menu_icon_new").append(html);
            selectForm.render('select');
        }
    });
});