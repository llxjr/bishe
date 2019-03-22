<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../common/head.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="js/question/question.js"></script>
<script type="text/javascript">
	var url = "${pageContext.request.contextPath}/qf/question";
	
	var method;
	
	function searchQuestion() {
		$("#dg").datagrid('load', {
			"stem" : $("#s_stem").val()
		});
	}

	function deleteQuestion() {
		var selectedRows = $("#dg").datagrid('getSelections');
		if (selectedRows.length == 0) {
			$.messager.alert("系统提示", "请选择要删除的数据！");
			return;
		}
		var strIds = [];
		for ( var i = 0; i < selectedRows.length; i++) {
			var id = selectedRows[i].id;
			if (id == 1) {
				$.messager.alert("系统提示", "操作失败!");
				return;
			}
			strIds.push(id);
		}
		var ids = strIds.join(",");
		$.messager.confirm("系统提示", "您确认要删除这<font color=red>"
				+ selectedRows.length + "</font>条数据吗？", function(r) {
			if (r) {
				$.ajax({
					type : "DELETE",//方法类型
					dataType : "json",//预期服务器返回的数据类型
					url : url + '/' + ids,//url
					data : {},
					success : function(result) {
						//console.log(result);//打印服务端返回的数据
						if (result.resultCode == 200) {
							$.messager.alert("系统提示", "数据已成功删除！");
							$("#dg").datagrid("reload");
						} else {
							$.messager.alert("系统提示", "数据删除失败！");
						}

						;
					},
					error : function() {
						$.messager.alert("ERROR！");
					}
				});
			}
		});

	}

	function openQuestionAddDialog() {
		$("#dlg").dialog("open").dialog("setTitle", "添加用户信息");
		method = "POST";
	}

	function saveQuestion() {
		var id = $("#id").val();
		var stem = $("#stem").val();
		var chapterId = $("#chapterId").val();
		var parentId = $("#parentId").val();
		var level = $("#level").val();
		var questionType = $("#questionType").val();
		var answerNum = $("#answerNum").val();
		var correctNum = $("#correctNum").val();
		var value = $("#value").val();
		var correct = $("input[name='correct']:checked").val();
		var bankType = $("#bankType").val();
		
		
		var data = {
			"id":id,
			"stem" : stem,
			"chapterId" : chapterId,
			"parentId" : parentId,
			"level" : level,
			"questionType" : questionType,
			"answerNum" : answerNum,
			"correctNum" : correctNum,
			"correctNum" : correctNum,
			"value" : value,
			"correct" : correct,
			"bankType": bankType
		}
		$.ajax({
			type : method,//方法类型
			dataType : "json",//预期服务器返回的数据类型
			url : url,//url
			contentType : "application/json; charset=utf-8",
			data : JSON.stringify(data),
			success : function(result) {
				//console.log(result);//打印服务端返回的数据
				if (result.resultCode == 200) {
					$.messager.alert("系统提示", "保存成功");
					$("#dlg").dialog("close");
					$("#dg").datagrid("reload");
					resetValue();
				} else {
					$.messager.alert("系统提示", "操作失败");
					$("#dlg").dialog("close");
					resetValue();
				}
				;
			},
			error : function() {
				$.messager.alert("系统提示", "操作失败");
			}
		});
	}

	function openQuestionModifyDialog() {
		var selectedRows = $("#dg").datagrid('getSelections');
		if (selectedRows.length != 1) {
			$.messager.alert("系统提示", "请选择一条要编辑的数据！");
			return;
		}
		var row = selectedRows[0];
		if (row.id == 1) {
			$.messager.alert("系统提示", "操作失败！");
			return;
		}
		if (row.roleId != '' && row.roleId != '') {
			$('#role').find("option[ value='"+ row.roleId + "']").attr("selected", true);
		}
		$("#dlg").dialog("open").dialog("setTitle", "编辑用户信息");
		$('#fm').form('load', row);
		$("#password").val("******");
		$("#userId").val(row.id);
		method = "PUT";
	}

	function resetValue() {
		
	}

	function closeQuestionDialog() {
		$("#dlg").dialog("close");
		resetValue();
	}
	
	
</script>
</head>
<body style="margin:1px;">
	<!--easyui中用于显示的，数据的来源在js里面  -->
	<table id="dg"></table>
	<div id="tb">
		<div>
			<a href="javascript:openQuestionAddDialog()" class="easyui-linkbutton"
				iconCls="icon-add" plain="true">添加</a> <a
				href="javascript:openQuestionModifyDialog()" class="easyui-linkbutton"
				iconCls="icon-edit" plain="true">修改</a> <a
				href="javascript:deleteQuestion()" class="easyui-linkbutton"
				iconCls="icon-remove" plain="true">删除</a>
		</div>
		<div>
			&nbsp;用户名：&nbsp;<input type="text" id="s_stem" size="20"
				onkeydown="if(event.keyCode==13) searchQuestion()" /> <a
				href="javascript:searchQuestion()" class="easyui-linkbutton"
				iconCls="icon-search" plain="true">搜索</a>
		</div>
	</div>

	<div id="dlg" class="easyui-dialog"
		style="width: 620px;height:250px;padding: 10px 20px" closed="true"
		buttons="#dlg-buttons">
		<form id="fm" method="post">
			<table cellspacing="8px">
				<tr>
					<td>题干：</td>
					<td><input type="text" id="stem" name="stem"
						class="easyui-validatebox" required="true" />&nbsp;<font
						color="red">*</font> <input type="hidden" id="id" value="0" name="id">
					</td>
				</tr>
				<tr>
					<td>章节id：</td>
					<td><input type="text" id="chapterId" name="chapterId"
						class="easyui-validatebox" required="true" />&nbsp;<font
						color="red">*</font></td>
				</tr>
				<tr>
					<td>父类id：</td>
					<td><input type="text" id="parentId" name="parentId"
						class="easyui-validatebox" required="true" />&nbsp;<font
						color="red">*</font></td>
				</tr>
				<tr>
					<td>等级：</td>
					<td><input type="text" id="level" name="level"
						class="easyui-validatebox" required="true" />&nbsp;<font
						color="red">*</font></td>
				</tr>
				<tr>
					<td>问题类别：</td>
					<td><input type="text" id="questionType" name="questionType"
						class="easyui-validatebox" required="true" />&nbsp;<font
						color="red">*</font></td>
				</tr>
				<tr>
					<td>回答数：</td>
					<td><input type="text" id="answerNum" name="answerNum"
						class="easyui-validatebox" required="true" />&nbsp;<font
						color="red">*</font></td>
				</tr>
				<tr>
					<td>正确数：</td>
					<td><input type="text" id="correctNum" name="correctNum"
						class="easyui-validatebox" required="true" />&nbsp;<font
						color="red">*</font></td>
				</tr>
				<tr>
					<td>分值：</td>
					<td><input type="text" id="value" name="value"
						class="easyui-validatebox" required="true" />&nbsp;<font
						color="red">*</font></td>
				</tr>
				<tr>
					<td>正确与否：</td>
					<td>
						正确<input type="radio" name="correct"  value="1">
						错误<input type="radio" name="correct"  value="0">
					</td>
				</tr>
				<tr>
					<td>题库类别：</td>
					<td><select id='bankType' name='bankType'>
							<option value='-1'>---请选择课程---</option>
							<option value='1'>---基础题库题目---</option>
							<option value='2'>---强化题库题目---</option>
							<option value='99'>---考卷题目---</option>
						</select>
						&nbsp;<font color="red">*</font>
					</td>
				</tr>
			</table>
		</form>
	</div>

	<div id="dlg-buttons">
		<a href="javascript:saveQuestion()" class="easyui-linkbutton"
			iconCls="icon-ok">保存</a> <a href="javascript:closeQuestionDialog()"
			class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
	</div>
</body>
</html>