import { Plant } from '../models/plant.js';
import {sendRequest} from '../rabbitHandler.js';
import {pushPlant, sendMessage} from "../webSocketHandler.js";

export async function getAllPlants() {
    return Plant.findAll();
}

export async function getEoloPlantById(id) {
    return await Plant.findOne({ where: { id } });
}

export async function deleteEoloPlantById(id) {
    const plant = await getEoloPlantById(id);
    if (plant !== null) {
        plant.destroy();
    }
    return plant;
}

export async function createProcess (plant){
    const save  = await postEoloPlant({"id": plant.id, "city": plant.city, "progress": 0});
    pushPlant(save.id, plant.socketId);
    sendRequest(plant.socketId, save);
}

export async function postEoloPlant(plant) {
    return await Plant.create(plant);
}

export function processNotification(plant) {
    sendMessage(plant);
    if (plant.progress === 100)
        updateEoloPlant(plant);
}

async function updateEoloPlant(plant) {
    await Plant.update(plant, { where: { id: plant.id } });
}
