//The method works depending on the required operation
function operation(v1){
	let num1,num2,res;
	//Get the value from the first input
	num1 = document.getElementById('number1').value;
	num1 = parseInt(num1); 

	//Get the value from the second input
	num2 = document.getElementById('number2').value;
	num2 = parseInt(num2);

	switch(v1){
		case 1:
			res = num1+num2;
		break;

		case 2:
			res = num1-num2;
		break;

		case 3:
			res = num1 * num2;
		break;

		case 4:
			//If the second number is not 0, then division will be performed
			res = "Error,division by 0";
			if(num2 != 0){
				res = num1 / num2;
			}
		break;
	}

	//Incorrect data entry
	if(isNaN(num1) || isNaN(num2)){
		res = "Error,invalid input format";
	}

	document.getElementById('result').innerHTML = "Result: " + res;
}