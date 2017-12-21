
// Create a "close" button and append it to each list item
var myNodelist = document.getElementsByTagName("LI");
var i;
for (i = 0; i < myNodelist.length; i++) {
    var span = document.createElement("SPAN");
    var txt = document.createTextNode("\u00D7");
    span.className = "close";
    span.appendChild(txt);
    myNodelist[i].appendChild(span);
}

// Click on a close button to hide the current list item
var close = document.getElementsByClassName("close");
var i;
for (i = 0; i < close.length; i++) {
    close[i].onclick = function() {
        var div = this.parentElement;
        div.style.display = "none";
    }
}







// Login when clicking on the "Login" button
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


var close = document.getElementsByClassName("close");
var i;
for (i = 0; i < close.length; i++) {
    close[i].onclick = function() {
        var div = this.parentElement;
        div.style.display = "visible";
    }
}