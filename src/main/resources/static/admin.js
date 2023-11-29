let tableDiv = document.querySelector('.tableUsers');
let usersTableDiv = $('#usersTable');
let newUserDiv = $('#newUser');

class Role {
    constructor(id, roleName) {
        this.id = id;
        this.roleName = roleName;
    }
}

$(document).ready(function () {
    window.addEventListener('load', getUserTable);
    window.addEventListener('load', getAdmin);
});

function getAdmin() {
    fetch("/api/admin/curuser").then(res => res.json())
        .then(data => {
            let roles = '';
            data.roles.forEach(role => {
                roles += role.roleName.replace('ROLE_', '') + " "
            });
            $('#navUsername').append(data.username);
            $('#navRoles').append(roles);
        })
}

function getUserTable() {
    fetch("/api/admin")
        .then((response) => {
            return response.json();
        })
        .then((data) => {
            let userRow = "";
            let roleList = "";
            data.forEach((user) => {
                userRow += "<tr class='table'>";
                userRow += "<th id='id'>" + user.id + "</th>";
                userRow += "<td id='name'>" + user.name + "</td>";
                userRow += "<td id='lastName'>" + user.lastName + "</td>";
                userRow += "<td id='age'>" + user.age + "</td>";
                userRow += "<td id='username'>" + user.username + "</td>";
                user.roles.forEach((role) => {
                    roleList += role.roleName.replace("ROLE_","") + " ";
                })
                userRow += "<td>" + roleList + "</td>";
                roleList = "";
                userRow += "<td><button type='button' data-toggle='modal' " +
                    "class='btn btn-primary btn-sm text-white btn-info' data-target='#editModal' onclick='showModal(" + user.id + ")'>Edit</button></td>";
                userRow += "<td><button type='submit' class='btn btn-primary btn-sm btn-danger' onclick='deleteUser(" + user.id + ")'>Delete</button></td>";
                userRow += "</tr>";
            })
            tableDiv.innerHTML = "";
            tableDiv.innerHTML += userRow
            userRow = "";
        })
}

async function deleteUser(id) {
    let response = await fetch("/api/admin/" + id, {
        method: 'DELETE'
    }).then(getUserTable);
}

async function newUser() {
    let form = document.querySelector('.newUserTable');
    let formData = new FormData(form);
    let currentRoles = [];
    let existingRoles = Array.from(formData.getAll('roles'))

    for (let i = 0; i < existingRoles.length; i++) {
        let id = existingRoles[i];
        let roleName = id == 1 ? `ROLE_ADMIN` : `ROLE_USER`;
        currentRoles.push(new Role(id, roleName));
    }

    let data = {
        name: formData.get('name'),
        lastName: formData.get('lastName'),
        age: formData.get('age'),
        username: formData.get('username'),
        password: formData.get('password'),
        roles: currentRoles
    }
    let response = await fetch("/api/admin", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify(data)
    }).then(resp => resp.json()).then(data => {
        form.reset();
    })
    getUserTable();
    usersTableDiv.click();
}

document.querySelector("#newUserButton").addEventListener('click', newUser);

usersTableDiv.click(() => {
    if (!usersTableDiv.attr('class').includes('active')) {
        getUserTable();
        usersTableDiv.attr('class', "nav-link active");
        newUserDiv.attr('class', "nav-link text-primary");
        document.querySelector('.secondCard').style.display = "none";
        document.querySelector('.firstCard').style.display = "block";
    }
})

newUserDiv.click(() => {
    if (!newUserDiv.attr('class').includes("active")) {
        newUserDiv.attr('class', "nav-link active");
        usersTableDiv.attr('class', "nav-link text-primary");
        document.querySelector('.secondCard').style.display = "block";
        document.querySelector('.firstCard').style.display = "none";
    }
});

function showModal(id) {
    let body = "";
    fetch("/api/admin/" + id)
        .then((resp) => resp.json())
        .then((data) => {
            body += `
            <form className="text-center" id="editUserModal">
                    <div class="form-group">
                    <label class="font-weight-bold" for="id">Id:</label>
                    <input class="form-control" type="text" id="id" name="id" value="${data.id}" readonly>
                </div>
                <div class="form-group">
                    <label class="font-weight-bold" for="name">Name:</label>
                    <input class="form-control" type="text" id="name" name="name" value="${data.name}">
                </div>
                <div class="form-group">
                    <label class="font-weight-bold" for="lastName">Last name:</label>
                    <input class="form-control" type="text" value="${data.lastName}" id="lastName" name="lastName">
                </div>
                <div class="form-group">
                    <label class="font-weight-bold" for="password">Password:</label>
                    <input class="form-control" type="password" value="${data.password}" name="password" id="password">
                </div>
                <div class="form-group">
                    <label class="font-weight-bold" for="age">Age:</label>
                    <input class="form-control" type="number" value="${data.age}" name="age" id="age">
                </div>
                <div class="form-group">
                    <label class="font-weight-bold" for="username">Email:</label>
                    <input class="form-control" type="text" value="${data.username}" name="username" id="username">
                </div>
                <div class="form-group" style="display: flex; flex-direction: column">
                    <label class="font-weight-bold" for="roles">Role:</label>
                      <select multiple class="mb-3 custom-select" id="roles" name="roles">
                        <option value="1">User</option>
                        <option value="2">Admin</option>
                      </select>
                </div>
            </form>`
            document.querySelector('.modalEdit').innerHTML = body;
        })
}

async function editUser() {
    let currentRoles = [];
    let formData = await new FormData(await document.querySelector("#editUserModal"));
    let existingRoles = Array.from(formData.getAll('roles'))

    for (let i = 0; i < existingRoles.length; i++) {
        let id = existingRoles[i];
        let roleName = id == 1 ? `ROLE_USER` : `ROLE_ADMIN`;
        currentRoles.push(new Role(id, roleName));
    }
    let data = {
        id: formData.get('id'),
        name: formData.get('name'),
        lastName: formData.get('lastName'),
        age: formData.get('age'),
        username: formData.get('username'),
        password: formData.get('password'),
        roles: currentRoles
    }
    let response = await fetch("/api/admin", {
        method: "PATCH",
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify(data)
    }).then(resp => resp.json()).then(data => {
        $('#editModal').modal('hide');
        getUserTable();
    })
}

document.querySelector('#editUser').addEventListener('click', editUser);