/* globals Chart:false, feather:false */

async function getData(page= 0){
    feather.replace({'aria-hidden': 'true'})
    let loadingButton = document.querySelector('.counter')

    loadingButton.innerHTML = '<button class="btn btn-primary" type="button" disabled>\n' +
        '                <span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span>\n' +
        '                Loading...\n' +
        '            </button>'


    // Data from server


    let selectedCategory = (new URL(document.location)).searchParams;
    let displayedName = document.querySelector('.displayed-name')
    let categoryDropdown = Array.from(document.querySelector('.categories-list').querySelectorAll('.dropdown-item')).map(elem => elem.innerText)
    let pathName = window.location.pathname //  pathName = "/pharmacy"

    displayedName.innerHTML = selectedCategory.get('category')==null || selectedCategory.get('category')==='0' ? 'Выберите категорию' : categoryDropdown[selectedCategory.get('category')]

    let response = selectedCategory.get('category') === null ? await fetch(
        window.location.origin+"/api" + pathName + "/unique?page=" + page) :
        await fetch(window.location.origin+"/api" + pathName + "/unique?page=" +
            page + "&category=" + selectedCategory.get("category"))


    let content = await response.json()

    // List
    let list = document.querySelector('.productsList')
    list.innerHTML = ""
    for (let key in content){

        list.innerHTML +=       '<tr>\n' +
            '                        <td>' + content[key].articul + '</td>\n' +
            '                        <td>' + content[key].name + '</td>\n' +
            '                        <td>' + content[key].category.name + '</td>\n' +
            '                        <td>' + content[key].updatedTime + '</td>\n' +

            '                        <td>\n' +
            '<a href="' + pathName + '/' + content[key].articul + '" class="btn btn-sm btn-ouclassName-secondary">Подробнее</a></td>' +
            '                    </tr>'
    }
    console.log(content.length) // Обработать кнопку если на странице нет элементов
    loadingButton.innerHTML = '<button class="btn btn-primary d-inline-flex align-items-right" onclick="getData(' + (page+1) + ')" type="button">\n' +
        '    Далее →' +
        '  </button>'

    if (page > 0){
        loadingButton.innerHTML = '<button class="btn btn-primary d-inline-flex align-items-right" onclick="getData(' + (page-1) + ')" type="button">\n' +
            '← Назад' +
        '  </button>' + loadingButton.innerHTML
    } else if (page===0){
        loadingButton.innerHTML = '<button class="btn btn-primary d-inline-flex align-items-right" onclick="getData(' + (page+1) + ')" type="button">\n' +
            '    Далее →' +
            '  </button>'
    }
}