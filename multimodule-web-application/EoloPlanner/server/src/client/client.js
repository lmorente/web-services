//Load Plants
window.loadPlants = loadPlants;
async function loadPlants() {
    const result = await getPlants();
    const plants = document.getElementById('plants');
    while (plants && plants.lastChild){
        plants.removeChild(plants.lastChild);
    }
    plants.appendChild(createList(result.data.plants));
}

async function getPlants() {
    let query = (`
        query {
            plants {
                city
                planning
            }
        }
    `);
    const response = await fetch('http://localhost:3000/graphql', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
        },
        body: JSON.stringify({query})
    });
    return await response.json();
}

function createList(items) {
    const lists = document.createElement('ol');
    for (var i = 0; i < items.length; i++) {
        const plant = document.createElement('ul');
        const city = document.createElement('li');
        city.appendChild(document.createTextNode("City: " + items[i].city));
        city.appendChild(document.createTextNode(" Planning: " + items[i].planning));
        plant.appendChild(city);
        lists.appendChild(plant);
        lists.appendChild(document.createElement('br'));
    }
    return lists;
}


//Create plants
window.createPlant = createPlant;
async function createPlant() {
    const button = document.getElementById('button-save');
    const spinner = document.getElementById('spinner');
    button.disabled = true;
    spinner.style = "display: block";

    await savePlant(document.getElementById("new").value);

    button.disabled = false;
    spinner.style = "display: none";
    await loadPlants();
}

async function savePlant(value) {
    const query = (`
        mutation {
            createPlant(city: "${value}") {
                city
                planning
            }
        }
    `);
    await fetch('http://localhost:3000/graphql', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
        },
        body: JSON.stringify({query})
    });
}

await loadPlants();
