import { sequelize } from '../mysql.js';
import sequelizePkg from 'sequelize';

const { DataTypes, Model } = sequelizePkg;

export class Plant extends Model { }

Plant.init({
  id: {
    type: DataTypes.INTEGER,
    autoIncrement: true,
    primaryKey: true
  },
  city: {
    type: DataTypes.STRING,
    allowNull: false
  },
  planning: DataTypes.STRING,
  progress: DataTypes.INTEGER
}, {
  sequelize,
  modelName: 'Plant',
  tableName: 'EoloPlants',
  timestamps: false
});
