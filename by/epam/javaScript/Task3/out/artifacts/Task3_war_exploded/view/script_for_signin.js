function submit() {
    let user = getFormData();
    authenticateUser(user);
}


function getFormData() { //получаем данные при сабмите
    let user = {};
    let data = document.getElementsByClassName('signin');
    for (let i = 0; i < data.length; i++) {
        let name = data[i].name;
        let value = data[i].value;
        user[name] = value;
    }
    return(user);
}


function authenticateUser(user) {
    let json = JSON.stringify(user);
    let xhr = new XMLHttpRequest();

    xhr.responseType = "json";
    xhr.onload = function(){
        if(xhr.status == 200){
            checkStatusRequest(xhr.response);
        }else{
            alert("ERROR: " + xhr.statusText + " status: " + xhr.status);
        }
    } 

    xhr.open('POST', '/firstservlet', true);
    xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');
    xhr.send(json);
    
}


function checkStatusRequest(object){
    let name = object.name;
    let statusReq = object.status;

    if(statusReq == -2){
        document.getElementById('validerrors').innerHTML = "There is no such user!";
    }else{
        console.log("Имя - " + name);
        document.getElementById('title').innerHTML = "Hello " + name;
        document.getElementById('bodyPage').innerHTML = "<center><p>Your Page .... </p></center>"
    }
}

