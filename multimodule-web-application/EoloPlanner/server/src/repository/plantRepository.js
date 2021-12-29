import { Plant } from '../model/plant.js';

export function getAll() {
    return Plant.findAll();
}

export function deleteById(id) {
    return Plant.destroy({
        id: id
    });
}

export async function save(city, planning) {
    return Plant.create({
        city: city,
        planning: planning
    })
}