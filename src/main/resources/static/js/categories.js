(async () => {
    // Select category

    let categoryDropdownResponse = await fetch(window.location.origin + '/api/products/categories')
    let categoryDropdownList = await categoryDropdownResponse.json();
    let categoryDropdown = document.querySelector('.categories-list')

    categoryDropdown.innerHTML = '<li><a class="dropdown-item" href="?category=0">Все</a></li>\n' +
        '                        <li><hr class="dropdown-divider"></li>'
    for (let key in categoryDropdownList){
        categoryDropdown.innerHTML += '<li><a class="dropdown-item" href="?category='+ categoryDropdownList[key].id +'">' + categoryDropdownList[key].name +'</a></li>'
    }
})()