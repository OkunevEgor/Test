
addBookBtnEvent();


function addBookBtnEvent() {
    document.getElementById('addBtn').addEventListener('click', function (event) {
        event.preventDefault();
        let href = this.getAttribute('href');
        fetch(href).then(response => response.text()).then(fragment => {
            document.querySelector("#addModal").innerHTML = fragment;
        }).then(() => {
            let model = new bootstrap.Modal(document.getElementById('addModal'), {});
            model.show();
            document.getElementById("add_book").addEventListener('submit', event => submitNewBookForm(event))
        });
    });
}
async function submitNewBookForm(event) {
    event.preventDefault();
    let formData = new FormData(event.target),
        request = new Request(event.target.action, {
            method: 'POST',
            body: formData
        });
    fetch(request).then(response =>response.text().then(fr=>{
        let modal = bootstrap.Modal.getInstance(document.getElementById('addBookModal'))
        modal.hide()
        document.querySelector('.book_list').innerHTML =fr
        addBookBtnEvent();
    }))
    /*const param = new URLSearchParams({
        "login": formData.get('login'),
    });
    fetch("books/checkLogin?" + param).then(response => {
            if (response.ok) {
                saveUser(request)
            } else {
                return Promise.reject(response)
            }
        }
    ).catch(error => error.text()).then(errorEr => {
        document.querySelector("#addModal .custom-alert").innerHTML = errorEr

    })*/
}
function eventForBookPage() {
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
            document.getElementById('delBook').addEventListener('click', function (event) {
                event.preventDefault()
                fetch(href).then(response => response.text()).then(fragment => {
                    document.querySelector(".book_list").innerHTML = fragment;
                    model.hide()
                    eventForUserPage();
                })
            })
        }))
}
