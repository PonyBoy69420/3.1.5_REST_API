const deleteUserName = document.getElementById('delete-firstname-field');
const deleteLastName = document.getElementById('delete-lastname-field');
const deleteEmail = document.getElementById('delete-email-field');
const deleteId = document.getElementById('delete-id-field');
const deleteRoles = document.getElementById('select-for-roles-delete');
const deleteAge = document.getElementById('delete-age-field');



async function editDeleteForm(id){
    let userInfo = await fetch('/api/admin/users/'+id);
    if(userInfo.ok){
        let letUserData = await userInfo.json().then(async user=>{
            deleteId.value=user.id;
            deleteUserName.value=user.username;
            deleteLastName.value=user.lastName;
            deleteEmail.value=user.email;
            deleteAge.value=user.age;
            deleteRoles.value=user.roles;
        })
    }else{
        alert('Error,${userInfo.status}');
    }
}

async function deleteUser(){
    await fetch('/api/admin/users/'+deleteId.value,{
        method:'DELETE',
        headers:{
            "Content-Type":"application/json"
        }
    }).then(function(response){
        console.log(response);
        getAllUsers();
        $('#delete-form-close-button').click();
    })
}