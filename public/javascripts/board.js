$(function() {

/****** Read Json ******/
	var listCount = 0;
	var objectCount = 0;
	if (boardContent == "{}"){
		listCount++;
		var board = {"list1":{"id":"list1","objects":[]}};
		$(".queryWorkbench").append(initContent);
	} else {
		var board = boardContent;
		loadFromJson(board);
	}

	function loadFromJson(board){
		for (list in board){
			listCount++;
			var listId= board[list]["id"];
			listDom=addList(listId);
			$(".queryWorkbench").append(listDom);
			var title=board[listId]["title"];
			$("#"+listId+" h3").html(title);
			$.each(board[listId]["objects"],function(index,value){
				objectCount++;
				var objectId= value["id"];
				objectDom=addObject(objectId);
				$(".queryWorkbench #"+listId+" ul").append(objectDom);
				var objectContent=board[listId]["objects"][objectId]["objectContent"];
				$("#"+objectId+" .object").html(objectContent);
			})
		}
	};


	$("#btnAdd").click(function() {
		var num = listCount;
		var newNum= new Number(listCount + 1);
		var list = "list"+newNum;
		listCount++;
		var newAndElem = addList(list);
		$(".queryWorkbench").append(newAndElem);
		$('#list' + newNum).hide().fadeIn();
		var title= $("#"+list+" h3").text();
		board[list]={
			id:list,
			title:title,
			objects:{}
		}
		updateBoard(board);
	});

	$(".addObject").livequery(function(){ 
		$(this).click(function() {
			var listNum=$(this).parent().attr("id");
			var num = objectCount;
			var newNum= new Number(objectCount + 1);
			var object = "object"+newNum;
			objectCount++;
			var newAndElem = addObject(object);
			$("#"+listNum+" ul").append(newAndElem);
			$('#object' + newNum).hide().fadeIn();
			objectContent=$("#"+object+" .object").text();
			board[listNum]["objects"][object]={
				id:object,
				content:objectContent
			}
			updateBoard(board);
		})
	})

	$(".list").livequery(function(){ 
		$( "ul.droptrue" ).sortable({
			connectWith: "ul",
			dropOnEmpty: true,
			update : function () {
				$(".list").each(function(){
					var listNum=$(this).attr("id");
					var title=$("#"+listNum+" h3").text();
					var listObjects = $(this).find("ul").sortable('toArray');
					board[listNum]={
							id:listNum,
							title:title,
							objects:{}
						}
					$.each(listObjects, function(index, value){
						objectContent=$("#"+listNum+" #"+value+" .object").text();
						board[listNum]["objects"][value]={
							id:value,
							objectContent:objectContent
						}
					})
				})
			updateBoard(board);
			}
		});
		$( "ul" ).disableSelection();
	})

	function updateBoard(board){
		$.ajax( { 
			url : updateBoardURL, 
			data : JSON.stringify(board) ,
			type: "POST",
			contentType: "application/json", 
		});
	}

	$(".list").livequery(function(){ 
		$('.editableTitle').editable(function(value, settings) {
			var currentList= $(this).parent().attr("id");
			board[currentList]["title"]= value;
			updateBoard(board);
			return(value);
		},{
			type : 'textarea',
			submit : 'OK'
		})
	})



	$(".object").livequery(function(){ 
		$('.editableObject').editable(function(value, settings) {
			var currentList= $(this).parent().parent().parent().attr("id");
			var currentObject= $(this).parent().attr("id");
			board[currentList]["objects"][currentObject]["objectContent"]= value;
			updateBoard(board);
			return(value);
		},{
			type : 'textarea',
			submit : 'OK',
			event : "dblclick"
		})
	})




});
