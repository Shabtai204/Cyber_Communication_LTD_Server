const inputs = document.querySelectorAll(".input");


function addcl() {
	let parent = this.parentNode.parentNode;
	parent.classList.add("focus");
}

function remcl() {
	let parent = this.parentNode.parentNode;
	if (this.value == "") {
		parent.classList.remove("focus");
	}
}


inputs.forEach(input => {
	input.addEventListener("focus", addcl);
	input.addEventListener("blur", remcl);
});

function myWindow() {
	window.open("popupWindow.html", "_blank", "height=500", "width=500", "left=100", "top=100", "resizable=yes", "scrollbars=yes",
		"toolbar=yes", "menubar=no", "location=no", "directories=no", "status=yes");

	// Name
	var idNum = document.getElementById('idNum').value;
	document.getElementById('name').innerHTML = idNum;

	// Address and phone number
	var address = document.getElementById('address').value;
	var phoneNum = document.getElementById('phoneNum').value;
	document.getElementById('address/phone').innerHTML = address + '/' + phoneNum;
}

function handleSubmit () {
    const id = document.getElementById('idNum').value;
    const name = document.getElementById('firstName').value;

    // to set into local storage
    /* localStorage.setItem("NAME", name);
    localStorage.setItem("SURNAME", surname); */
    
    sessionStorage.setItem("ID", id);
    sessionStorage.setItem("NAME", name);

    return;
}