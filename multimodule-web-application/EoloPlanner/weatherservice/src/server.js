import { Server, ServerCredentials } from '@grpc/grpc-js';
import { Wheatherservice} from './proto.js';
import { GetWeather } from './service.js';

const server = new Server();
server.addService(Wheatherservice.service, { GetWeather });
server.bindAsync('0.0.0.0:9090', ServerCredentials.createInsecure(), () => {
    server.start();
    console.log('gRPC server running at http://127.0.0.1:9090');
});