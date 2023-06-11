/* globals Chart:false, feather:false */

async function getData(page= 0){

    feather.replace({'aria-hidden': 'true'})
    // Select category
    let categoryDropdownResponse = await fetch(window.location.origin + '/api/products/categories')
    let categoryDropdownList = await categoryDropdownResponse.json();
    let categoryDropdown = document.querySelector('.categories-list')

    categoryDropdown.innerHTML = '<li><a class="dropdown-item" href="?category=0">Все</a></li>\n' +
        '                        <li><hr class="dropdown-divider"></li>'
    for (let key in categoryDropdownList){
        categoryDropdown.innerHTML += '<li><a class="dropdown-item" href="?category='+ categoryDropdownList[key].id +'">' + categoryDropdownList[key].name +'</a></li>'
    }

    // Data from server
    let loadingButton = document.querySelector('.counter')

    loadingButton.innerHTML = '<button class="btn btn-primary" type="button" disabled>\n' +
        '                <span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span>\n' +
        '                Loading...\n' +
        '            </button>'

    let selectedCategory = (new URL(document.location)).searchParams;
    let displayedName = document.querySelector('.displayed-name')
    displayedName.innerHTML = selectedCategory.get('category')==null || selectedCategory.get('category')==='0' ? 'Выберите категорию' : categoryDropdownList[selectedCategory.get('category')-1].name

    let response = selectedCategory.get('category') === null ? await fetch(
        window.location.origin+"/api/products/unique?page=" + page) :
        await fetch(window.location.origin+"/api/products/unique?page=" +
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
            '<a href="/products/' + content[key].articul + '" class="btn btn-sm btn-ouclassName-secondary">Подробнее</a></td>' +
            '                    </tr>'
    }

    loadingButton.innerHTML = '<button class="btn btn-primary d-inline-flex align-items-right" onclick="getData(' + (page+1) + ')" type="button">\n' +
        '    Далее →' +
        // '<script src="/js/listOfProducts.js"></script>' +
        '  </button>'

    if (page > 0){
        loadingButton.innerHTML = '<button class="btn btn-primary d-inline-flex align-items-right" onclick="getData(' + (page-1) + ')" type="button">\n' +
            '← Назад' +
        // '<script src="/js/listOfProducts.js"></script>' +
        '  </button>' + loadingButton.innerHTML
    } else if (page===0){
        loadingButton.innerHTML = '<button class="btn btn-primary d-inline-flex align-items-right" onclick="getData(' + (page+1) + ')" type="button">\n' +
            '    Далее →' +
            // '<script src="/js/listOfProducts.js"></script>' +
            '  </button>'
    }
}