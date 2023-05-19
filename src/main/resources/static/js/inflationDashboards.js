/* globals Chart:false, feather:false */

(async () => {
    'use strict'

    feather.replace({'aria-hidden': 'true'})
    // Data from server

    let response = await fetch(window.location.origin+"/api/inflation")
    let content = await response.json()
    let labelList = []
    let priceList = []
    let list = document.querySelector('.productsInfo')
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
                label: content[0].name,
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
})()