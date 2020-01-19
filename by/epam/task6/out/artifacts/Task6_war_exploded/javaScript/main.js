 function sendData(){

	//Сhecking fields
	if(checkFields()){
		let nameValue = document.getElementById("name").value;
		let surnameValue = document.getElementById("surname").value;
		let body = "name=" + encodeURIComponent(nameValue) + "&surname=" + encodeURIComponent(surnameValue);

		let xhr = new XMLHttpRequest();
		xhr.open("POST", "/mainservlet", true);
		xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

		//block of code - set response
		xhr.responseType = "json";
		xhr.onload = function(){
			if(xhr.status == 200){
				enterResponse(xhr.response);
			}else{
				alert("ERROR: " + xhr.statusText + " status: " + xhr.status);
			}
		}

		xhr.send(body);
	}
}

function checkFields() {
	let isFill = false;
	let valueName = document.getElementById('name').value;
	let valueSurname = document.getElementById('surname').value;

	if(valueName.length <= MIN_LENGTH_NAME || valueSurname.length <= MIN_LENGTH_SURNAME ){
		document.getElementById('info').innerHTML = "[Error] Field 'name' or 'surname' < 3 characters!";
	}else{
		isFill = true;
		document.getElementById('info').innerHTML = " ";
	}
	return isFill;
}

function enterResponse(objectHuman) {
	let id = objectHuman.id;
	let name = objectHuman.name;
	let surname = objectHuman.surname;
	let role = objectHuman.role;
	var tableString = "";

	var tableString = "<table border=\"1px\">";
    body = document.getElementsByTagName('body')[0];
    div = document.createElement('div');


    tableString += "<tr> <td style='color: blue'>ID</td> <td>Name</td> <td>Surname</td> <td style='color: blue'>Role</td> </tr>"
    tableString += "<tr> <td>" + id + "</td> <td>" + name + 
    "</td> <td>" + surname + "</td> <td>" + role + "</td> </tr>"

	tableString += "</table>";
	div.innerHTML = tableString;
	body.appendChild(div);
}