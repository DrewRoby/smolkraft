const csrfToken = document.getElementById("csrfToken").value;
const validateRoute = document.getElementById("validateRoute").value;
//const tasksRoute = document.getElementById("tasksRoute").value;
const createRoute = document.getElementById("createRoute").value;
//const deleteRoute = document.getElementById("deleteRoute").value;
//const addRoute = document.getElementById("addRoute").value;
//const logoutRoute = document.getElementById("logoutRoute").value;

function login() {
  const username = document.getElementById("loginName").value;
  const password = document.getElementById("loginPassword").value;
  fetch(validateRoute, {
    method: 'POST',
    headers: {'Content-Type':'application/json', 'Csrf-Token': csrfToken},
    body: JSON.stringify({ username, password })
    }).then(res => res.json()).then(data => {
      if(data) {
        document.getElementById("login-section").hidden = true;
        document.getElementById("ingredients-section").hidden = false;
//        document.getElementById("create-message").innerHTML = "";
        document.getElementById("login-message").innerHTML = "";
        loadTasks();
      } else {
        document.getElementById("login-message").innerHTML = "Login Failed";
      }
    });
}

function createUser() {
  const username = document.getElementById("createName").value;
  const password = document.getElementById("createPassword").value;
  fetch(createRoute, {
    method: 'POST',
    headers: {'Content-Type': 'application/json', 'Csrf-Token': csrfToken},
    body: JSON.stringify({username: username, password: password}) //Does this work?
  }).then(res => res.json()).then(data => {
    if(data) {
      document.getElementById("create")
    }
  })
  document.getElementById("create-section").hidden = true;
  document.getElementById("login-section").hidden = false;

}

function showCreateUser() {
  document.getElementById("create-section").hidden = false;
  document.getElementById("login-section").hidden = true;
}