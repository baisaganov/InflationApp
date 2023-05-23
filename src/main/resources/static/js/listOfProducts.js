/* globals Chart:false, feather:false */

async function getData(page= 0){

    feather.replace({'aria-hidden': 'true'})
    // Data from server
    let loadingButton = document.querySelector('.counter')

    loadingButton.innerHTML = '<button class="btn btn-primary" type="button" disabled>\n' +
        '                <span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span>\n' +
        '                Loading...\n' +
        '            </button>'

    let response = await fetch(window.location.origin+"/api/products/unique?page=" + page)
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

    page+=1
    loadingButton.innerHTML = '<button class="btn btn-primary d-inline-flex align-items-right" onclick="getData(' + page + ')" type="button">\n' +
        '    Далее →' +
        '<script src="/js/listOfProducts.js"></script>' +
        '  </button>'
}