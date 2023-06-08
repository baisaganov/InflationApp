/* globals Chart:false, feather:false */

(async () => {
    'use strict'

    feather.replace({'aria-hidden': 'true'})
    // Data from server

    let mrpResponse = await fetch(window.location.origin+"/api" + window.location.pathname + "/mrp")
    let mrpContent = await mrpResponse.json()
    mrpContent.sort(function (a, b) {
        return a.year - b.year
    })
    let mrpDateList = []
    let mrpValueList = []
    for (let key in mrpContent){
        mrpValueList.push(mrpContent[key].value)
        mrpDateList.push(mrpContent[key].year)
    }

    let mzpResponse = await fetch(window.location.origin+"/api" + window.location.pathname + "/mzp")
    let mzpContent = await mzpResponse.json()
    mzpContent.sort(function (a, b) {
        return a.year - b.year
    })
    let mzpDateList = []
    let mzpValueList = []
    for (let key in mzpContent){
        mzpValueList.push(mzpContent[key].value)
        mzpDateList.push(mzpContent[key].year)
    }



    // Graphs
    const ctx = document.getElementById('myChart')

    // eslint-disable-next-line no-unused-vars
    const myChart = new Chart(ctx, {
        type: 'line',
        data: {
            labels: mrpDateList,
            datasets: [{
                label: 'МРП',
                data: mrpValueList,
                lineTension: 0,
                backgroundColor: 'transparent',
                borderColor: '#ff6200',
                borderWidth: 4,
                pointBackgroundColor: '#ff6200'
            },
                {
                    label: 'МЗП',
                    data: mzpValueList,
                    lineTension: 0,
                    backgroundColor: 'transparent',
                    borderColor: '#007bff',
                    borderWidth: 4,
                    pointBackgroundColor: '#007bff'
                }]
        },
        options: {
            plugins: {

                tooltip: {
                    boxPadding: 3
                }
            }
        }
    })


    // List of mrp
    let list = document.querySelector('.mrpInfo')
    let prevPrice
    for (let key = 0; key < mrpContent.length; key++){
        const currentPrice = mrpContent[key].value
        if (key===0){
            prevPrice = mrpContent[key].value
        } else {
            prevPrice = mrpContent[key-1].value
        }
        let changedValue = currentPrice - prevPrice
        let changedPercent = ((currentPrice - prevPrice)/currentPrice*100).toFixed(2)

        list.innerHTML =       '<tr>\n' +
            '                        <td>' + mrpContent[key].year + '</td>\n' +
            '                        <td>' + currentPrice + ' тг.</td>\n' +
            '                        <td>' + changedValue + ' тг.</td>\n' +
            '                        <td>' + changedPercent + '% </td>\n' +
            '                    </tr>' + list.innerHTML
    }

    // List of mrp
    let listMzp = document.querySelector('.mzpInfo')
    prevPrice = 0
    for (let key = 0; key < mzpContent.length; key++){
        const currentPrice = mzpContent[key].value
        if (key===0){
            prevPrice = mzpContent[key].value
        } else {
            prevPrice = mzpContent[key-1].value
        }
        let changedValue = currentPrice - prevPrice
        let changedPercent = ((currentPrice / prevPrice) * 100).toFixed(2)

        listMzp.innerHTML =       '<tr>\n' +
            '                        <td>' + mzpContent[key].year + '</td>\n' +
            '                        <td>' + currentPrice + ' тг.</td>\n' +
            '                        <td>' + changedValue + ' тг.</td>\n' +
            '                        <td>' + changedPercent + '% </td>\n' +
            '                    </tr>' + listMzp.innerHTML
    }
})()