$(function() {
	$('#dg')
			.datagrid(
					{
						url : 'qf/question/datagrid',
						title : '问题管理',
						fitColumns : true,
						loadMsg : '数据加载中,请稍后...',
						rownumbers : true,
						singleSelect : true,
						checkOnSelect : true,
						columns : [ [
								{
									field : 'id',
									title : '编号',
									checkbox : true
								},
								{
									field : 'stem',
									title : '题干',	
									width : 100
								},
								{
									field : 'chapterId',
									title : '章节id',	
									width : 100,
									formatter : function(value, row, index) {
										return row.chapter.chapterName;
									}
								},
								{
									field : 'parentId',
									title : '父类id',
									width : 100
								},
								{
									field : 'level',
									title : '等级',
									width : 100
								},
								{
									field : 'questionType',
									title : '题目类别',
									width : 100,
									formatter : function(value, row, index) {
										return row.questionType.type_name;
									}
								},{
									field : 'answerNum',
									title : '回答数目',
									width : 100
								},{
									field : 'correctNum',
									title : '正确数目',
									width : 100
								},
								{
									field : 'value',
									title : '分值',
									width : 100
								},
								{
									field : 'correct',
									title : '是否正确',
									width : 100
								},
								{
									field : 'bankType',
									title : '题库类别',
									width : 100
								},
								 ] ],
						pagination : true,
						pageSize : 10,
						pageList : [ 10, 20, 35, 50 ],
						toolbar : '#tb'
					});
})




