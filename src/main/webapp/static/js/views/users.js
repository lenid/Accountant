function deleteUser(dialogHeader, dialogBody, id) {
    deleteConfirm(dialogHeader, dialogBody, function() {
        $.ajax({
            headers : {
                'Accept' : 'application/json',
                'Content-Type' : 'application/json'
            },
            url : "user/delete",
            datatype : "json",
            type : "POST",
            async : 'false',
            data : JSON.stringify({
                "id" : id,
                "token" : csrf_token
            }),
            beforeSend : function(xhr) {
                xhr.setRequestHeader('X-CSRF-Token', csrf_token);
            },
            success : function(response) {
                alert("ok");
            },
            error : function(request, status, error) {
                alert("ERROR!!!!!!!");
            }
        });
    });
}

function getUser(id) {
    $.ajax({
        url : "user/" + id,
        type : 'GET',
        async : 'true',
        success : function(data) {
            $('#modalContentUserForm').html(data);
            validateUserForm('#userForm', true);
            $('#userFormModal').modal('show');
        },
        error : function(e) {
            //alert("error" + e);
        }
    });
}