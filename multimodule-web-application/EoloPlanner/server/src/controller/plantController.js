import plantService from '../service/plantService.js';

export function getAllPlants() {
    return plantService.getAllPlants();
}

export function createPlant(city) {
    return plantService.createPlant(city);
}

export function deletePlant(id) {
    return plantService.deletePlant(id);
}





