/* globals Chart:false, feather:false */

(async () => {
    'use strict'

    feather.replace({'aria-hidden': 'true'})
    //  Fetching Data from server
    let response = await fetch(window.location.origin+"/api/inflation")
    let content = await response.json()
    let labelList = [] //
    let priceList = []
    for (let key in content){
        const currentPrice = content[key].averagePrice
        priceList.push(currentPrice)
        labelList.push(content[key].updatedTime)

    }

    // Graphs
    const ctx = document.getElementById('productsInflation')

    // eslint-disable-next-line no-unused-vars
    const myChart = new Chart(ctx, {
        type: 'line',
        data: {
            labels: labelList,
            datasets: [{
                label: "Индекс инфляции",
                data: priceList,
                lineTension: 0,
                backgroundColor: 'transparent',
                borderColor: '#007bff',
                borderWidth: 4,
                pointBackgroundColor: '#007bff'
            }]
        },
        options: {
            plugins: {
                legend: {
                    display: true,
                    onClick: (() => {console.log("Hide disabled")}),
                    labels: {
                        borderRadius: 1
                    }
                },
                tooltip: {
                    boxPadding: 3
                }
            }
        }
    })

    // List
    let prevPrice, colorized, sign
    let list = document.querySelector('.productsInflation')
    for (let key = 0; key < content.length; key++){
        const currentPrice = content[key].averagePrice
        if (key===0){
            prevPrice = content[key].averagePrice
        } else {
            prevPrice = content[key-1].averagePrice
        }
        let changedValue = currentPrice - prevPrice
        let changedPercent = (Math.abs(100 - (currentPrice / prevPrice) * 100)).toFixed(2)
        if(Math.sign(changedValue) === 1){
            colorized = '<td class="text-danger">'
            sign = '+'
        } else if (Math.sign(changedValue) === 0){
            colorized = '<td class="text-secondary">'
            sign = ''
        } else {
            colorized = '<td class="text-success">'
            sign = '-'
        }

        list.innerHTML =       '<tr>\n' +
            '                        <td>' + content[key].updatedTime + '</td>\n' +
            '                        <td>'  + currentPrice + ' тг.</td>\n' +
            colorized + sign + Math.abs(changedValue) + ' тг.</td>\n' +
            colorized + sign + changedPercent + '% </td>\n' +
            '                    </tr>' + list.innerHTML

    }


})()