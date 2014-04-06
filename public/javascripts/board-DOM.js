/////////// DOM ////////////

	var initContent="<div id='list1' class='list'>"
					+"<h3>List 1</h3>"
						+"<ul class='droptrue'>"
						+"</ul>"
					+"<input type='submit' class='addObject' value='Add a new object' />"
				+"</div>";

	function addList(listNum){
		var list="<div id='"+listNum+"'class='list'>"
						+"<h3 class='editableTitle'>Click me to change the text</h3>"
						+"<ul class='droptrue'>"
						+"</ul>"
						+"<input type='submit' class='addObject' value='Add a new object' />"
					+"</div>";
		return list;
	};

	function addObject(objectNum){
		var object="<li id='"+objectNum+"' class='objectContainer'>"
						+"<div class='editableObject object'>Double click me to change the text</div>"
					+"</li>";
		return object;
	};

