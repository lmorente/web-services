import { credentials } from '@grpc/grpc-js';
import { Wheatherservice } from './proto.js';
import {getAll, deleteById, save} from '../repository/plantRepository.js';
import axios from 'axios';

const topoBaseUrl = 'http://localhost:8080/api/topographicdetails/';
const weatherService = new Wheatherservice('localhost:9090', credentials.createInsecure());

export function getAllPlants() {
    return getAll();
}

export function deletePlant(id) {
    return deleteById(id);
}

export async function createPlant(city) {
    await sleep(getRandomTimeout(1000, 3000));
    let planning = [];
    planning.isCompleted = () => planning.length === 3;

    //City
    planning.push(city.city)

    //Ladscape
    axios.get(topoBaseUrl + city.city).then(res => {
        planning.push(res.data.landscape);
        if (planning.isCompleted()) {
            return savePlant(city.city, planning);
        }
    }).catch(() => console.log('Error getting topographic details.'));

    //Weather
    weatherService.GetWeather(city, (error, res) => {
        if (error) {
            return console.log('Error getting weather details.');
        }
        planning.push(res.weather);
        if (planning.isCompleted()) {
            return savePlant(city.city, planning);
        }
    });
}

function savePlant(city, planning) {
    return save(city, planingFormatter(planning.join('-')))
}

function planingFormatter(planning) {
    return planning.toLowerCase().charAt(0) > 'm' ? planning.toUpperCase() : planning.toLowerCase();
}

function sleep(millis) {
    return new Promise(resolve => {
        setTimeout(() => resolve(), millis);
    })
}

function getRandomTimeout(min, max) {
    return Math.floor(Math.random() * (max - min)) + min;
}

export default {getAllPlants, createPlant, deletePlant}