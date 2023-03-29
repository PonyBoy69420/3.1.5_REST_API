const newUserUserName = document.getElementById('firstName');
const newUserLastName = document.getElementById('lastName');
const newUserEmail = document.getElementById('email');
const newUserPassword = document.getElementById('password');
const newUserRoles = document.getElementById('select-for-roles');
const newUserAge = document.getElementById('age');


async function postUser(){
    let roles=[];
    for(let i=0;i<newUserRoles.options.length;i++){
        if(newUserRoles.options[i].selected){
            roles.push(newUserRoles.options[i].value);
        }
    }
    let method={
        method: 'POST',
        mode:'cors',
        headers:{
            "Content-Type":"application/json"
        },
        body:JSON.stringify({
            username: newUserUserName.value ,
            lastName: newUserLastName.value ,
            age:newUserAge.value,
            email: newUserEmail.value ,
            password: newUserPassword.value,
            roles: roles
        })}
    await fetch('/admin/api/users/create-new',method)
        .then(function(response){
            console.log(response);
            getAllUsers();
        })
}