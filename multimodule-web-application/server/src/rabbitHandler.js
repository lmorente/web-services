import {connect} from 'amqplib';
import {processNotification} from './services/plantService.js';

let channelIn = null;
let channelOut = null;

const queueInput = "eoloplantCreationProgressNotifications";
const queueOutput = "eoloplantCreationRequests";

process.on('exit', () => {
    channelIn.close();
    channelOut.close();
    console.log('Closing rabbitmq channel');
});
const rabbitClient = await connect('amqp://guest:guest@localhost');

channelIn = await rabbitClient.createChannel();
channelIn.assertQueue(queueInput);//, { autoDelete: true, exclusive: true, durable: false });

channelOut = await rabbitClient.createChannel();
channelOut.assertQueue(queueOutput);//, { autoDelete: true, exclusive: true, durable: false });


//Consumer
channelIn.consume(queueInput, (msg) => {
    console.log("Consumed from queue: '", msg.content.toString() + "'");
    processNotification(JSON.parse(msg.content.toString()));
}, {noAck: true});

//Producer
export async function sendRequest(socketId, plant) {
    const request = {"id": plant.id, "city": plant.city};
    console.log("Produced to queue: '" + JSON.stringify(request) + "'");
    await channelOut.sendToQueue(queueOutput, Buffer.from(JSON.stringify(request)));
}