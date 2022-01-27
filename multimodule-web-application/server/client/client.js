//Websocket
const webSocket = new WebSocket("ws://localhost:3001");
let socketId;

webSocket.onmessage = (event => {
    const data = JSON.parse(event.data);
    if (data.socketId) {
        socketId = data.socketId;
        return;
    }
    removeProgress();
    if (data.progress < 100) {
        createProgress(data.progress);
        return;
    }
    enableCreationButton();
    createPlantView(data);
});

function removeProgress() {
    const percentage = document.getElementById("percentage");
    while (percentage.firstChild)
        percentage.removeChild(percentage.lastChild);
    document.getElementById("progress").style.display = "none"; //Element will not be displayed
}

function createProgress(progress) {
    const percentage = document.getElementById("percentage");
    percentage.appendChild(document.createTextNode("Progress: " + progress.toString() + "%"));

    const bar = document.getElementById("progress");
    bar.value = progress.toString();
    bar.style.display = "inline"; //Element is rendered as an inline element.
}


// Plant management functions
async function createPlant() {
    const city = document.querySelector('#city').value;
    disableCreationButton();
    const query = `mutation($eoloPlant: EoloPlantInput) {
    createEoloPlant(eoloPlant: $eoloPlant) {
      id
      city
      planning
    }
  }`;
    const response = await graphql(query, {eoloPlant: {socketId: socketId, city: city}});
    const plant = response.data.createEoloPlant;
    console.log(JSON.stringify(plant));
    createPlantView(plant);
    enableCreationButton();
}

async function getAllPlants() {
    const query = `query {
    eoloPlants {
      id
      city
      planning
    }
  }`;
    const response = await graphql(query);
    const plants = response.data.eoloPlants;
    plants.map(createPlantView);
}

function deletePlant(id) {
    const query = `mutation($id: ID!) {
    deleteEoloPlant(id: $id) {
      id
      city
      planning
    }
  }`;
    graphql(query, {id});
    deletePlantView(id);
}

async function graphql(request, variables) {
    const response = await fetch('/graphql', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
        },
        body: JSON.stringify({query: request, variables})
    });
    return await response.json();
}

// Plant View functions
function disableCreationButton() {
    document.querySelector('#generate').style.display = 'none';
    document.querySelector('#creationSpinner').style.display = 'block';
}

function enableCreationButton() {
    document.querySelector('#generate').style.display = 'block';
    document.querySelector('#creationSpinner').style.display = 'none';
}

function createPlantView(plant) {
    const htmlContent = `<div id="plant-${plant.id}" class="col"></div>`;
    const current = document.querySelector('#plants').innerHTML;
    document.querySelector('#plants').innerHTML = htmlContent + current;
    document.querySelector('#plants-title').style.display = 'block';
    createOrUpdatePlanView(plant);
}

function deletePlantView(id) {
    const elem = document.querySelector('#plant-' + id);
    elem.parentNode.removeChild(elem);
    if (document.querySelector('#plants').childElementCount === 0) {
        document.querySelector('#plants-title').style.display = 'none';
    }
}

function createOrUpdatePlanView(plant) {
    const plantElement = document.querySelector('#plant-' + plant.id);
    if (!plantElement) {
        createPlantView(plant);
    } else {
        document.querySelector('#plant-' + plant.id).innerHTML = `
    <div class="card mb-4 shadow-sm">
      <div class="card-header">
        <h4 class="my-0 fw-normal">${plant.city}</h4>
      </div>
      <div class="card-body px-2">
        <ul class="list-unstyled mt-3 mb-4">
          <li class="weather">Planning: ${plant.planning}</li>
        </ul>
        <div class="d-flex align-items-center">
          <button type="button" onClick="deletePlant(${plant.id})" class="btn btn-danger btn-sm d-none><i class="fas fa-trash-alt"></i>Delete</button>
        </div>
      </div>
    </div>
  `;
    }
}

// -------------
getAllPlants();