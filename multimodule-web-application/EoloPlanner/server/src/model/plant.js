import { sequelize, Model, DataTypes } from '../mysqlConfiguration.js';

export class Plant extends Model { }
Plant.init({
    city: DataTypes.STRING,
    planning: DataTypes.STRING
}, { sequelize, modelName: 'plant' });

await sequelize.sync();