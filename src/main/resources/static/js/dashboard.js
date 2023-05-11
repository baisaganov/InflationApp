/* globals Chart:false, feather:false */

(async () => {
    'use strict'

    feather.replace({'aria-hidden': 'true'})
    // Data from server

    let response = await fetch("http://localhost:8080/api" + window.location.pathname)
    let content = await response.json()
    let labelList = []
    let priceList = []
    let list = document.querySelector('.productsInfo')
    for (let key in content){
        const currentPrice = content[key].price
        priceList.push(currentPrice)
        labelList.push(content[key].updatedTime)

    }

    // Graphs
    const ctx = document.getElementById('myChart')

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

    // List
    let prevPrice
    for (let key = content.length-1; key>=0; key--){
        const currentPrice = content[key].price

        if (!prevPrice){
            prevPrice = content[key].price
        } else {
            prevPrice = content[key+1].price
        }

        let changedValue = currentPrice - prevPrice
        let changedPercent = ((currentPrice - prevPrice)/prevPrice*100).toFixed(2)

        list.innerHTML +=       '<tr>\n' +
            '                        <td>' + content[key].updatedTime + '</td>\n' +
            '                        <td>' + currentPrice + '</td>\n' +
            '                        <td>' + changedValue + '</td>\n' +
            '                        <td>' + changedPercent + '</td>\n' +
            '                    </tr>'
    }
})()