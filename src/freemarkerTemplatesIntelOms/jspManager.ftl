<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/common/base.jsp"%>
<%@ taglib prefix="ss" uri="http://www.springframework.org/tags" %>
<!doctype html>
<html>
<head>
	<title>${codeName}</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	
	<link href="<c:url value="/resources/bui/css/bs3/dpl-min.css"/>" rel="stylesheet">
	<link href="<c:url value="/resources/bui/css/bs3/bui-min.css"/>" rel="stylesheet">
	<link href="<c:url value="/resources/css/iov_common.css"/>" rel="stylesheet">
	<script type="text/javascript" src="<c:url value="/resources/bui/jquery-min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/bui/bui-min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/js/common.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/js/dateExtend.js"/>"></script>
	
	<style type="text/css">
		
</style>
</head>

<body>
<div style="padding: 10px;">
<!-- 搜索页 ================================================== -->
    <div class="row">
      <div class="span24">
        <form id="searchForm" class="form-horizontal" method="post" tabindex="0" style="outline: none;">
          <div class="row">
			<div class="control-group span8">
				<label class="span3 control-label">名称</label>
				<input type="text" class="control-text" name="name">
			</div>
			<div class="control-group" style="width:465px;">
				<label class="span3 text-right">入库时间从</label>
                <input class="span5 calendarSelf calendar-time" type="text" value="" name="startCreatedTime">
                <label class="span1 text-right timeSelf">到</label>
                <input class="span5 calendarSelf calendar-time" type="text" value="" name="endCreatedTime">
			</div>
		  </div>
          <div class="row">
            <div class="control-group span8 btnGroupSelf">
            	<div class="controls">
	              	<button id="btnSearch" type="button" class="button button-primary">搜索</button>
	              	<button id="btnClean" type="button" class="button button-primary">重置</button>
              	</div>
            </div>
          </div>
        </form>
      </div>
    </div> 

    <div class="row btnExSelf">
    	<div class="controls">
          	<button id="btnCreate" type="button" class="button button-primary">新建</button>
       	</div>
    </div>
    <div class="search-grid-container">
      <div id="grid"></div>
    </div>
    
    <div id="dialogWindow" class="bui-hidden">
     <div id="dialogForm" class="form-horizontal">
     	<form id="dialog_form" name="dialog_form">
			   <div class="panel" style="padding-bottom:10px;">
                     <div class="row">
                         <div class="span15">
                             <label class="span3 text-right"><span class="star">*</span>名称</label>
                             <input class="span5" type="text" name="name" data-rules="{required:true}">
                         </div>
                     </div>
                     <div class="row">
                         <div class="span15">
                             <label class="span3 text-right"><span class="star">*</span>生效时间</label>
                             <input class="span5 calendarSelf calendar-time" type="text" value="" name="startTime" data-rules="{required:true}">
                             <label class="span1 text-right timeSelf">到</label>
                			 <input class="span5 calendarSelf calendar-time" type="text" value="" name="endTime" data-rules="{required:true}">
                         </div>
                     </div>
                     <div class="row">
                         <div class="span15">
                             <label class="span3 text-right"><span class="star">*</span>发布对象</label>
                             <select class="span5" name="publishObject" onchange="addPublishObjectUsers($(this));" data-rules="{required:true}">
                             	<option value="1">所有用户</option>
                             	<option value="2">指定用户</option>
                             	<option value="3">条件过滤</option>
                             </select>
                         </div>
                         <div class="span15" style="">
                         	<textarea style="display:none;" class="userPhonesTextArea" rows="" cols="" name="cusPhones"></textarea>
                         </div>
                     </div>
                     <div class="row">
                         <div class="span15">
                             <label class="span3 text-right"><span class="star">*</span>广告界面</label>
                             <input name="" />
                         </div>
                     </div>
                      <div class="row">
                         <div class="span15">
                             <label class="span3 text-right">活动说明</label>
                             <textarea rows="" cols="" name="content" ></textarea>
                         </div>
                     </div>
	             </div>
			
			<input type="hidden" name="id">
		 </form>
      </div>
  </div>
      
</div>
<script type="text/javascript">
 
	BUI.use([ 'bui/overlay', 'bui/grid', 'bui/data', 'bui/form', 'bui/calendar', 'bui/select', 'bui/tab'],
		function(Overlay, Grid, Data, Form, Calendar, Select, Tab, Uploader) {
			// 日历选择器
			new Calendar.DatePicker({
				trigger : '.calendarSelf',
				minDate : new Date(new Date()-24*60*60*1000).format("yyyy-MM-dd hh:mm:ss"),
				autoRender : true,
				dateMask : 'yyyy-mm-dd HH:MM:ss'
			});
			var urls = {
				addOrupdate : '11bb{contextPath}/${lowerName}/addOrupdate.json',
				list : '11bb{contextPath}/${lowerName}/list.json',
				updateStatusOrDel : '11bb{contextPath}/${lowerName}/updateStatusOrDel.json'
			},operationBtn = function(value,o){
				return '<a>编辑</a><a>删除</a><a>查看兑换码</a>';
			}, columns = [ {
				title : '序号',
				dataIndex : 'id',
				width : 50,
				sortable : true
			}, {
				title : '操作',
				dataIndex : 'action',
				width : 200,
				sortable : true,
				renderer : function(value, obj) {
					return operationBtn(obj);
				}
			}],Store = Data.Store,
			store = new Store({
				url : urls.list,
				proxy : {
					method : 'post'
				},
				autoLoad : true,
				remoteSort : true,
				pageSize : 10,
				listeners : {
					load : function() {
						$('#total_count').text('共' + store.getTotalCount() + '条记录');
					}
				}
			}), grid = new Grid.Grid({
				render : '#grid',
				loadMask : true,
				forceFit: true,
				columns : columns,
				idField : 'id',
				store : store,
				bbar : {
					pagingBar : true
				},
				itemStatusFields : {//设置数据跟状态的对应关系
					selected : 'selected',
					disabled : 'disabled'
				},
				emptyDataTpl: '<div class="centered">----暂无信息----</div>',
				plugins : [ Grid.Plugins.CheckSelection, Grid.Plugins.AutoFit ] // 插件形式引入多选表格
			});
			grid.render();

			// 条件搜索相关
			var formSearch = new Form.Form({
				srcNode : '#searchForm'
			}).render();
			$('#btnSearch').on('click', function() {
				var obj = formSearch.serializeToObject(); // 序列化成对象
				obj.pageIndex = 0;
				obj.start = 0; // 返回第一页
				store.load(obj);
			});
			$('#btnClean').on('click', function() { // 清除按钮事件
				formSearch.updateRecord({
					name : "",startCreatedTime : "",endCreatedTime : ""
				});
			});
			
			 var dialogForm = new Form.Form({srcNode: '#dialog_form'}).render();
                 dialogForm.set('action', urls.addOrupdate);
                 dialogForm.set('method', 'post');
			//奖品弹出窗
            var ${lowerName}Dialog = new Overlay.Dialog({
                title: '奖品编辑',
                width: 550,
                height: 600,
                mask:false,
                contentId: 'dialogWindow',
                success: function () {
                    if (dialogForm.isValid()) { 
                    	var data = iov.common.getFormJson($('#dialog_form'));
                        dialogForm.ajaxSubmit({
                            data:data,
                            success: function (data) {
                                store.load({});
                                // 清除提交历史
                                dialogForm.clearFields();
                                dialogForm.clearErrors();
								
                                ${lowerName}Dialog.close();
                            },
                            error: function () {
                                BUI.Message.Alert('服务器出错!', 'error');
                            }
                        }); 
                    }
                }
            });
			$('#btnCreate').on('click',function(){ // 新增逻辑
				dialogForm.clearFields();
                dialogForm.clearErrors();
				${lowerName}Dialog.show();
			});
			
			// 删除发布下架相关
			var updateStatusOrDel = function(id,status,del) {
				var title = '确认要删除选中的记录么？',
					reqData = {'id':id,isDeleted:1};
				
				BUI.Message.Confirm(title, function() {
					$.ajax({
						url : urls.updateStatusOrDel,
						dataType : 'json',
						type : "POST",
						data :reqData,
						success : function(data) {
							if (data == 1) { // 操作成功
								store.load();
							} else { // 操作失败
								BUI.Message.Alert('操作失败！');
							}
						}
					});
				}, 'question');
			}
			
			// 修改逻辑 
			var update = function(record){
				dialogForm.updateRecord(record);
				${lowerName}Dialog.show();
			};
			
			// 各种事件绑定相关
			// 监听事件，删除一条记录
			grid.on('cellclick', function(e) {
			 	var id = e.record.id,
			 		dom = e.domTarget.innerHTML;
				if (e.field === "action") {
	               	 if (dom === '编辑') {// 编辑
	               		 update(e.record);
	               	 }
					 if (dom === '删除') {// 删除
						 updateStatusOrDel(id,null,1);               		 
	               	 }
					 if (dom === '查看兑换码') {// 查看兑换码
						 updateStatusOrDel(id,1,null);	 
					 }
				}
			});
			
		});
</script>
</body>
</body>
</html>

