//Implemented the ability to add multiple menus
//Triggered when the mouse cursor hovers over the menu
let menuFocus = document.getElementsByClassName('menu');
//Set the listener on each menu, I have one
for (let i = 0; i < menuFocus.length ; i++) {
	menuFocus[i].addEventListener('mouseenter', showElement, false);
	menuFocus[i].addEventListener('mouseleave', hideElement, false);
}

/* Working with level 2 */
let subSubmenu = document.getElementsByClassName('liob');
//On each root we hang up the listener
for (let i = 0; i < subSubmenu.length ; i++) {
	subSubmenu[i].addEventListener('mouseenter', showElement, false);
	subSubmenu[i].addEventListener('mouseleave', hideElement, false);
}

let subText = document.getElementsByClassName('infoSubText');
for (let i = 0; i < subText.length ; i++) {
	subText[i].addEventListener('click', infoFromSub, false);
}

function showElement(){
	this.children[1].style.display = "block";
}

function hideElement(){
	this.children[1].style.display = "none";
}

function infoFromSub(){
	let val = this.innerHTML;
	let infoSub = document.getElementById('info');
	infoSub.innerHTML = val;
}



/* TIME */
function clock() {
	let d = new Date();
	let month_num = d.getMonth()
	let day = d.getDate();
	let hours = d.getHours();
	let minutes = d.getMinutes();
	let seconds = d.getSeconds();
	 
	month = new Array("января", "февраля", "марта", "апреля", "мая", "июня",
	"июля", "августа", "сентября", "октября", "ноября", "декабря");
	 
	if (day <= 9) day = "0" + day;
	if (hours <= 9) hours = "0" + hours;
	if (minutes <= 9) minutes = "0" + minutes;
	if (seconds <= 9) seconds = "0" + seconds;
	 
	date_time = "Сегодня - " + day + " " + month[month_num] + " " + d.getFullYear() +
	" г.&nbsp;&nbsp;&nbsp;Текущее время - "+ hours + ":" + minutes + ":" + seconds;
	if (document.layers) {
	 document.layers.doc_time.document.write(date_time);
	 document.layers.doc_time.document.close();
	}
	else document.getElementById("doc_time").innerHTML = date_time;
	 setTimeout("clock()", 1000);
}