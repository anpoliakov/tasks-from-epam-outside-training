let registerButton = document.getElementById('registerButton');
registerButton.addEventListener("click", submit);

function submit (){
    let user = makeUserObject(); // обьект из полей формы ключ:значение

    if (validateData(user) == true) {
        addUserToDataBase(user);
    } else {
        alert("Enter valid data");
    }
}

function makeUserObject () { // получаем данные из формы, создаем объект user
    let user = {};
    let data = document.getElementsByClassName('register'); //получение элементов по Class
    for (let i = 0; i < data.length; i++) { 
        let name = data[i].name;  // из массива data берём [i-й элемент] и получаем его ИМЯ
        let value = data[i].value; // из массива data берём [i-й элемент] и получаем его ЗНАЧЕНИЕ
        user[name] = value; 
    }
    return(user);
}

function validateData(user) { // проверяем валидность данных с помощью регулярных выражений
    let validateName = /[a-zA-Z0-9]/;
    let validateEmail = /^[-\w.]+@([A-z0-9][-A-z0-9]+\.)+[A-z]{2,4}$/;
    let validatePassword = /[a-zA-Z0-9]/;
    let nameError = "Name can only contains numbers and letters"
    let emailError = "Wrong Email format"
    let passwordError = "Password can only contains numbers and letters"

    // код выше - валидация с помощью регулярных выражений и тест ошибок
    if (validateName.test(user.name) != true) {
        document.getElementById('validateerrors').innerHTML = nameError; 
        return false;
    } else if (validateEmail.test(user.email) != true) {
        document.getElementById('validateerrors').innerHTML = emailError;
    } else if (validatePassword.test(user.password) != true) {
        document.getElementById('validateerrors').innerHTML = passwordError; 
        return false;
    } else {
        return true;
    }
}

function addUserToDataBase(obj) {
    let json = JSON.stringify(obj);
    console.log(json);

    let xhr = new XMLHttpRequest();

    xhr.responseType = "json";
    xhr.onload = function(){
        if(xhr.status == 200){
            checkBD(xhr.response);
        }else{
            alert("ERROR: " + xhr.statusText + " status: " + xhr.status);
        }
    } 

    xhr.open('POST', '/firstservlet');
    xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');
    xhr.send(json);
}


function checkBD(infoFromBD){
    let statusRequest = infoFromBD.status;
    if(statusRequest == -1){
        document.getElementById('validateerrors').innerHTML = "Such a user exists!";
    }else{
        alert("Successful registration");
        document.location.href = "signin.html";
    }
}
