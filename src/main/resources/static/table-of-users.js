async function getAllUsers(){
    let listUsers = await fetch('/api/admin/users');
    if(listUsers.ok){
        let table = document.getElementById('usersTable');
        let users = await listUsers.json();
        let tr = '';
        for(let user of users) {
            let roles = [];
            for (let role of user.roles) {
                roles.push(' ' + role.name.toString());
            }
            tr+=
            `
            <tr>
            <td>${user.id}</td>
            <td>${user.username}</td>
            <td>${user.lastName}</td>
            <td>${user.age}</td>
            <td>${user.email}</td>
            <td>${roles}</td>
            <td><button class="btn btn-info" data-bs-toggle="modal" data-bs-target="#edit-form" onclick="editModalForm(${user.id})">Edit</td>
            <td><button class="btn btn-info" data-bs-toggle="modal" data-bs-target="#delete-form" onclick="editDeleteForm(${user.id})">Delete</td>
            </tr>`
        }
        table.innerHTML = tr;
    }else{
        alert('Error,${page.status}');
    }
}

getAllUsers();