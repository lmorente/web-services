import sequelize_pkg from 'sequelize';
export const { Sequelize, Model, DataTypes} = sequelize_pkg;
export const sequelize = new Sequelize('eoloplantsDB', 'root', 'root', { dialect: 'mysql'});