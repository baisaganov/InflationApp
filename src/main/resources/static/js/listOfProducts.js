/* globals Chart:false, feather:false */

(async () => {
    'use strict'

    feather.replace({'aria-hidden': 'true'})
    // Data from server

    let response = await fetch("http://localhost:8080/api/products/unique")
    let content = await response.json()
    // List
    let prevPrice
    let list = document.querySelector('.productsList')
    for (let key in content){

        list.innerHTML +=       '<tr>\n' +
            '                        <td>' + content[key].articul + '</td>\n' +
            '                        <td>' + content[key].name + '</td>\n' +
            '                        <td>' + '-' + '</td>\n' +
            '                        <td>' + <a href={products/content[key].articul}>Подробнее...</a>+ '</td>\n' +
            '                    </tr>'
    }
})()