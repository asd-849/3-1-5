fetch("/api/user").then(res => res.json())
    .then(data => {
        let roles = '';
        data.roles.forEach(role => {
            roles += role.roleName.replace('ROLE_', '') + " "
        });
        let userTable = `
        <tr>
            <td>${data.id}</td>
            <td>${data.name}</td>
            <td>${data.lastName}</td>
            <td>${data.age}</td>
            <td>${data.username}</td>
            <td>${roles}</td>)
            </tr>`;
        $('#userData').append(userTable);
        $('#navUsername').append(data.username);
        $('#navRoles').append(roles);
    })