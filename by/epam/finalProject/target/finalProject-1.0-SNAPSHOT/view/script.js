let registerButton = document.getElementById('registerButton');
registerButton.addEventListener("click", submit);

function submit(){
    let user = makeUserObject(); 

    if (validateData(user) == true) {
        addUserToDataBase(user);
    } else {
        alert("Enter valid data");
    }
}

function makeUserObject () { 
    let user = {};
    let data = document.getElementsByClassName('register'); 

    for (let i = 0; i < data.length; i++) { 
        let name = data[i].name;  
        let value = data[i].value; 
        user[name] = value; 
    }
    return(user);
}

function validateData(user) { 


    let validateName = /[a-zA-Z0-9]/;
    let validateSurname = /[a-zA-Z0-9]/;
    let validateEmail = /^[-\w.]+@([A-z0-9][-A-z0-9]+\.)+[A-z]{2,4}$/;
    let validatePassword = /[a-zA-Z0-9]/;


    /*Errors:*/
    let nameError = "Name can only contains numbers and letters"
    let SurnameError = "Surname can only contains numbers and letters"
    let emailError = "Wrong Email format"
    let passwordError = "Password can only contains numbers and letters"
    let repeatPasswordError = "Repeat password can only contains numbers and letters"
    let notMatchPassword = "Passwords must match"

    // код выше - валидация с помощью регулярных выражений и тест ошибок
    if (validateName.test(user.name) != true) {
        document.getElementById('validateerrors').innerHTML = nameError; 
        return false;
    } else if (validateSurname.test(user.surname) != true) {
        document.getElementById('validateerrors').innerHTML = SurnameError; 
        return false;
    } else if (validateEmail.test(user.email) != true) {
        document.getElementById('validateerrors').innerHTML = emailError;
    } else if (validatePassword.test(user.password) != true) {
        document.getElementById('validateerrors').innerHTML = passwordError; 
        return false;
    } else if (validatePassword.test(user.passwordRepeat) != true) {
        document.getElementById('validateerrors').innerHTML = repeatPasswordError; 
        return false;
    } else if (user.password != user.passwordRepeat) {
        document.getElementById('validateerrors').innerHTML = notMatchPassword; 
        return false;
    } else {
        return true;
    }
}

function addUserToDataBase(obj) {
    let json = JSON.stringify(obj);
    console.log(json);
    let xhr = new XMLHttpRequest();


    xhr.responseType = "text/plain";
    xhr.onload = function(){
        if(xhr.status == 200){
            console.log(xhr.response);
            checkBD(xhr.response);
        }else{
            alert("ERROR: " + xhr.statusText + " status: " + xhr.status);
        }
    } 

    xhr.open('POST', '/mainServlet', true); //прописал true ! correct
    xhr.setRequestHeader('Content-type', 'application/json; charset=UTF-8');
    xhr.send(json);
}


function checkBD(doesHumanExist) {
    if (doesHumanExist == "exist") {
        document.getElementById('validateerrors').innerHTML = "Such a user exists!";
    } else {
        alert("Successful registration");
        document.location.href = "signin.html";
    }
}