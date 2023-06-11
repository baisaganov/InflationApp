/* globals Chart:false, feather:false */

async function getRef(){
    'use strict'

    feather.replace({'aria-hidden': 'true'})
    // Data from server

    let refResponse = await fetch(window.location.origin+"/api" + window.location.pathname + "/refinancing-bids")
    let refContent = await refResponse.json()

    let refDateList = []
    let refValueList = []
    for (let key in refContent){
        refValueList.push(refContent[key].percent)
        refDateList.push(refContent[key].year)
    }
    console.log(refContent)

    // Graphs refs
    const ctx2 = document.getElementById('myChart2')

    // eslint-disable-next-line no-unused-vars refs
    const myChart2 = new Chart(ctx2, {
        type: 'line',
        data: {
            labels: refDateList,
            datasets: [{
                label: 'Базовая ставка',
                data: refValueList,
                lineTension: 0,
                backgroundColor: 'transparent',
                borderColor: '#1d732b',
                borderWidth: 4,
                pointBackgroundColor: '#1d732b'
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

    // list
    let list = document.querySelector('.refInfo')
    let prevPrice
    for (let key = 0; key < refContent.length; key++){
        const currentPrice = refContent[key].percent
        if (key===0){
            prevPrice = refContent[key].percent
        } else {
            prevPrice = refContent[key-1].percent
        }
        let changedValue = currentPrice - prevPrice
        let changedPercent = (Math.abs(100 - (currentPrice / prevPrice) * 100)).toFixed(2)

        list.innerHTML =       '<tr>\n' +
            '                        <td>' + refContent[key].year + '</td>\n' +
            '                        <td>' + currentPrice + '%</td>\n' +
            '                        <td>' + changedValue + '</td>\n' +
            '                        <td>' + changedPercent + '% </td>\n' +
            '                    </tr>' + list.innerHTML
    }

}