/* globals Chart:false, feather:false */

(async () => {
    'use strict'

    feather.replace({'aria-hidden': 'true'})
    // Data from server

    let response = await fetch(window.location.origin+"/api/pharmacy/changed-list")
    let content = await response.json()


    // Graphs
    const growth = document.getElementById('growthTopPharmacy')
    const fall = document.getElementById('fallTopPharmacy')

    let topGrowth = content.filter(e => e.changeValue >= 0).sort((a, b) => {
        if(a.changePercent > b.changePercent){
            return -1;
        }
    })

    for (let key = 0; key < 6; key++){
        growth.innerHTML += '<a href="pharmacy/'+ topGrowth[key].articul + '" class="list-group-item list-group-item-action" aria-current="true">\n' +
            '                <div class="d-flex w-100 justify-content-between">\n' +
            '                <h6 class="mb-1">'+ topGrowth[key].name + '</h6>\n' +
            '                <small>' + topGrowth[key].price + ' тг.</small>\n' +
            '                </div>\n' +
            '                <p class="mb-1 ">Изменение: <span class="badge rounded-pill text-bg-danger">+' + Math.abs(topGrowth[key].changeValue) + ' тг. (+' + topGrowth[key].changePercent.toFixed(2) + '%)</span></p>\n' +
            '                <small>Артикул: ' + topGrowth[key].articul +'</small>\n' +
            '                </a>'
    }


    let topFall = content.filter(e => e.changeValue < 0).sort((a, b) => {
        if(a.changePercent > b.changePercent){
            return -1;
        }
    })

    for (let key = 0; key < 6; key++){
        fall.innerHTML += '<a href="pharmacy/'+ topFall[key].articul + '" class="list-group-item list-group-item-action" aria-current="true">\n' +
            '                <div class="d-flex w-100 justify-content-between">\n' +
            '                <h6 class="mb-1">'+ topFall[key].name + '</h6>\n' +
            '                <small>' + topFall[key].price + ' тг.</small>\n' +
            '                </div>\n' +
            '                <p class="mb-1 ">Изменение: <span class="badge rounded-pill text-bg-success">' + topFall[key].changeValue + ' тг. (' + topFall[key].changePercent.toFixed(2) + '%)</span></p>\n' +
            '                <small>Артикул: ' + topFall[key].articul +'</small>\n' +
            '                </a>'
    }

})()