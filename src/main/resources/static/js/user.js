document.addEventListener("DOMContentLoaded", function (event) {
    addUserBtnEvent();
    eventForUserPage();
});

function addUserBtnEvent() {
    document.getElementById('addBtn').addEventListener('click', function (event) {
        event.preventDefault();
        let href = this.getAttribute('href');
        fetch(href).then(response => response.text()).then(fragment => {
            document.querySelector("#addModal").innerHTML = fragment;
        }).then(() => {
            let model = new bootstrap.Modal(document.getElementById('addModal'), {});
            model.show();
            document.getElementById("add_user").addEventListener('submit', event => submitNewUserForm(event))
        });
    });
}

function editUser(href) {

    fetch(href).then(response => response.text()).then(fragment => {
        document.querySelector("#editModal").innerHTML = fragment;
    }).then(() => {
        let model = new bootstrap.Modal(document.getElementById('editModal'), {});
        model.show();
        document.getElementById("edit_user").addEventListener('submit', event => submitEditUserForm(event))
    });
}


function eventForUserPage() {
    document.querySelectorAll('.table tbody tr').forEach(editTr =>
        editTr.addEventListener('dblclick', function (event) {
            event.preventDefault()
            let href = editTr.querySelector("a").getAttribute('href');
            editUser(href)

        }))
    document.querySelectorAll('.table .editBtn').forEach(editBtn =>
        editBtn.addEventListener('click', function (event) {
            event.preventDefault()
            let href = this.getAttribute('href');
            editUser(href)
        }))

    document.querySelectorAll('.table .deleteBtn').forEach(deleteBtn =>
        deleteBtn.addEventListener('click', function (event) {
            event.preventDefault()
            let href = this.getAttribute('href');
            document.querySelector('#deleteModal .modal-footer a').setAttribute('href', href)
            let model = new bootstrap.Modal(document.getElementById('deleteModal'), {});
            model.show();
            document.getElementById('delUser').addEventListener('click', function (event) {
                event.preventDefault()
                fetch(href).then(response => response.text()).then(fragment => {
                    document.querySelector(".user_list").innerHTML = fragment;
                    model.hide()
                    eventForUserPage();
                })
            })
        }))
}

async function submitNewUserForm(event) {
    event.preventDefault();
    let formData = new FormData(event.target),
        request = new Request(event.target.action, {
            method: 'POST',
            body: formData
        });
    const param = new URLSearchParams({
        "login": formData.get('login'),
    });
    fetch("users/checkLogin?" + param).then(response => {
            if (response.ok) {
                saveUser(request)
            } else {
                return Promise.reject(response)
            }
        }
    ).catch(error => error.text()).then(errorEr => {
        document.querySelector("#addModal .custom-alert").innerHTML = errorEr

    })
}

async function saveUser(request) {
    let response = await fetch(request);
    let userTable = await response.text();
    let modal = bootstrap.Modal.getInstance(document.getElementById('addModal'))
    modal.hide();
    document.querySelector(".user_list").innerHTML = userTable;
    eventForUserPage()
}



async function submitEditUserForm(event) {
    event.preventDefault();
    let formData = new FormData(event.target),
        request = new Request(event.target.action, {
            method: 'POST',
            body: formData
        });
    let response = await fetch(request);
    let userTable = await response.text();
    let modal = bootstrap.Modal.getInstance(document.getElementById('editModal'))
    modal.hide();
    document.querySelector(".user_list").innerHTML = userTable;

    eventForUserPage()

}


function tableSearch() { // поиск по реальному времени
    let phrase = document.getElementById('search-text');
    let table = document.getElementById('info-table');
    let regPhrase = new RegExp(phrase.value, 'i');
    let flag = false;
    for (let i = 1; i < table.rows.length; i++) {
        flag = false;
        for (let j = table.rows[i].cells.length - 1; j >= 0; j--) {
            flag = regPhrase.test(table.rows[i].cells[j].innerHTML);
            if (flag) break;
        }
        if (flag) {
            table.rows[i].style.display = "";
        } else {
            table.rows[i].style.display = "none";
        }

    }
}

const path = "http://localhost:8080/users"

async function filterUser() {
    let searchWord = document.getElementById('searchWord').value
    const param = new URLSearchParams({
        "s": searchWord
    })
    fetch(path + "/filter?" + param).then(response => response.text()).then(fragment => {
        document.querySelector(".user_list").innerHTML = fragment
        eventForUserPage();
    })


}

