const config = require('config');
const mongoose = require('mongoose');
const Joi = require('joi');
Joi.objectId = require('joi-objectid')(Joi);
const express = require('express');
const helmet = require('helmet');
const compression = require('compression')
const auth = require('./routes/auth');
const market = require('./routes/market');
const statistic = require('./routes/statistic');
const product = require('./routes/product');
const shareholder = require('./routes/shareholder');
const trade = require('./routes/trade');
const user = require('./routes/user');


const app = express();

if(!config.get('jwtPrivateKey')){
    process.exit(1);
}
const db = config.get('db');
mongoose.connect(db)
    .then(()=>console.log(`connect to ${db}`));

app.use(express.json());
app.use('/api/auth',auth);
app.use('/api/market',market);
app.use('/api/product',product);
app.use('/api/shareholder',shareholder);
app.use('/api/statistic',statistic);
app.use('/api/trade',trade);
app.use('/api/user',user);
app.use(helmet());
app.use(compression());

const port = process.env.PORT;



const server = app.listen(port, () => console.log(`Listening on port ${port}`));


module.exports = server;

