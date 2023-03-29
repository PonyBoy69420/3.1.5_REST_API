async function getUserInfo(){
    let info = await fetch('/api/principal');
    if(info.ok){
        let user = await info.json();
        let roles=[];
        for(let role of user.roles){
            roles.push(' ' + role.name.toString());
        }
        let tr = document.createElement('tr');
        tr.innerHTML =`
        <tr>
        <td>${user.id}</td>
        <td>${user.username}</td>
        <td>${user.lastName}</td>
        <td>${user.age}</td>
        <td>${user.email}</td>
        <td>${roles}</td>
        </tr>`

        document.getElementById('table-about-user').append(tr);
        let header = document.getElementById('header-user');
        header.innerText= user.email + " with roles: " + roles;
    }else{
        alert('Error,${page.status}');
    }
}

getUserInfo();