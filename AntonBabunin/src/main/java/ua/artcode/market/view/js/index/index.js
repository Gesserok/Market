
function login() {
    // end of state change: it can be after some time (async)
    var xhr = new XMLHttpRequest();

    // we get the returned data
    var login = document.getElementById("login").value;
    var password = document.getElementById("password").value;

    xhr.onreadystatechange = function () {
        if (this.readyState !== 4) return;

        if (this.status === 200) {

            var data = JSON.parse(this.responseText);

        }
    };
    xhr.open("POST", "/login", true);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.send(JSON.stringify({
        login: login,
        password: password
    }));
}

var myNodelist = document.getElementsByTagName("LI");
var i;
for (i = 0; i < myNodelist.length-1; i++) {
    var span = document.createElement("SPAN");
    var txt = document.createTextNode("OK");
    span.id = i;
    span.className = "button";
    span.appendChild(txt);
    myNodelist[i].appendChild(span);
}



