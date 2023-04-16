let editForm=document.getElementById('edit-form');
const editAge=document.getElementById('edit-age-field');
const editUserName = document.getElementById('edit-firstname-field');
const editLastName = document.getElementById('edit-lastname-field');
const editEmail = document.getElementById('edit-email-field');
const editId = document.getElementById('edit-id-field');
const editPassword = document.getElementById('edit-password-field');
const editRoles = document.getElementById('select-for-roles-edit');


async function editModalForm(id){
    let userInfo = await fetch('/api/admin/users/'+id);
    if(userInfo.ok){
        let letUserData = await userInfo.json().then(async user=>{
            editId.value=user.id;
            editUserName.value=user.username;
            editLastName.value=user.lastName;
            editEmail.value=user.email;
            editAge.value=user.age;
            editPassword.value=user.password;
            editRoles.value=user.roles;
        })
    }else{
        alert('Error,${userInfo.status}');
    }
}

async function editUser(){
    let roles=[];
    for(let i=0;i<editRoles.options.length;i++){
        if(editRoles.options[i].selected){
            roles.push(editRoles.options[i].value);
        }
    }
    let method={
        method: 'PUT',
        mode:'cors',
        headers:{
            "Content-Type":"application/json"
        },
        body:JSON.stringify({
            id: editId.value,
            username: editUserName.value ,
            lastName: editLastName.value ,
            email: editEmail.value ,
            password: editPassword.value,
            age:editAge.value,
            roles: roles
        })}
    console.log(editId.value,editUserName.value,editLastName.value,editEmail.value,editAge.value,editPassword.value,roles);
    await fetch('/api/admin/users/'+editId.value,method)
        .catch(err=>{
            console.log(err.response());
        })
    .then(function(response){
        console.log(response.text());
        getAllUsers();
        $('#edit-form-close-button').click();

    })

}
