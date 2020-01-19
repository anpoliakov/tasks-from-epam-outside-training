let signInButton = document.getElementById('signinButton');
signInButton = addEventListener("click", submit);

function submit(event) {
    event.preventDefault();
    let user = getFormData();
    authenticateUser(user);
}

function getFormData() { //получаем данные при сабмите
let user = {};
    let data = document.getElementsByClassName('signin');
    for (let i = 0; i < data.length; i++) {
        let name = data[i].name;
        let value = data[i].value;
        user[name] = [value];
    }
    console.log(user);
        return(user);
}

function authenticateUser(user) {
    let dataBase = JSON.parse(localStorage.getItem('usersDb'));
    if (dataBase.hasOwnProperty(user.name) == true && dataBase[user.name].password == user.password) { // если в объекте пристутствует объект с ключом user.name, то сравниваем пароль. 
        document.location.href = "userpage.html"; // загрузка новой страницы
    } else {
        alert("Please enter correct data or register");
    }
}
