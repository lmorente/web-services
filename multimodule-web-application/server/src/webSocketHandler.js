import { WebSocketServer } from 'ws';

const wss = new WebSocketServer({ port: 3001 });
let socketId = 0;
const sockets = new Map();
const plants = new Map();

wss.on('connection', (ws) => {
    console.log("Client connected");
    socketId++;
    ws.send(JSON.stringify({socketId: socketId}));
    sockets.set(socketId, ws);
})

export function sendMessage(plant){
    if(plants.get(plant.id) &&  sockets.get(plants.get(plant.id)))
        sockets.get(plants.get(plant.id)).send(JSON.stringify(plant));
}

export function pushPlant(plantId, socketId){
    if(socketId)
        plants.set(plantId, socketId);
}

