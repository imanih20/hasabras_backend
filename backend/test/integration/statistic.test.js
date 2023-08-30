const request = require('supertest');
const mongoose = require('mongoose');
const {User} = require('../../models/user');
const {MonthStatistic} = require('../../models/month_statistic');
const {MarketStatistic} = require('../../models/market_statistic');

describe('/api/statistic',()=>{
    let server;
    beforeEach(()=>{
        server =require('../../index');
    });
    afterEach(async ()=>{
        await server.close();
        await MonthStatistic.deleteMany({});
        await MarketStatistic.deleteMany({});
    });
    describe('GET /api/statistic/market/{id}',()=>{
        let token;
        let id;
        const exec = function (){
            return request(server)
                .get('/api/statistic/market/'+id)
                .set('x-auth-token',token);
        }
        beforeEach(async()=>{
            const user = new User({name: 'name',phone: '093333333333'});
            token = user.generateAuthToken();
            id = new mongoose.Types.ObjectId();
            await MarketStatistic.insertMany([
                {market: id,year:1401,month:2},
                {market: new mongoose.Types.ObjectId(),year:1401,month:3},
                {market: id,year:1400,month:3},
                {market: new mongoose.Types.ObjectId(),year:1400,month:6},
                {market: id,year:1401,month:1},
            ]);
        });
        it('should return all of market statistics with given market id',async ()=>{
            const res = await exec();
            expect(res.status).toBe(200);
            expect(res.body.length).toBe(3);
        });
    });

    describe('GET /api/statistic/market/{id}/{year}/{month}',()=>{
        let token;
        let id;
        let year;
        let month;
        const exec = function(){
            return request(server)
                .get(`/api/statistic/market/${id}/${year}/${month}`)
                .set('x-auth-token',token);
        }
        beforeEach(async ()=>{
            const user = new User({name: 'name',phone: '093333333333'});
            token = user.generateAuthToken();
            id = new mongoose.Types.ObjectId();
            year = 1401;
            month = 2;
            await MarketStatistic.insertMany([
                {market: id,year:year,month:month},
                {market: new mongoose.Types.ObjectId(),year:year,month:3},
                {market: id,year:1400,month:month},
                {market: new mongoose.Types.ObjectId(),year:1400,month:6},
                {market: id,year:year,month:1},
            ]);
        });
        it('should return 404 if statistic not found.',async()=>{
            year = 1399;
            const res = await exec();
            expect(res.status).toBe(404);
        });
        it('should return statistic with given year and month',async ()=>{
            const res = await exec();
            expect(res.status).toBe(200);
            expect(res.body).toHaveProperty('year',year);
        });
    });


    describe('GET /api/statistic/shareholder/{id}',()=>{
        let token;
        let id;
        const exec = function (){
            return request(server)
                .get('/api/statistic/shareholder/'+id)
                .set('x-auth-token',token);
        }
        beforeEach(async()=>{
            const user = new User({name: 'name',phone: '093333333333'});
            token = user.generateAuthToken();
            id = new mongoose.Types.ObjectId();
            await MonthStatistic.insertMany([
                {user: id,year:1401,month:2},
                {user: new mongoose.Types.ObjectId(),year:1401,month:3},
                {user: id,year:1400,month:3},
                {user: new mongoose.Types.ObjectId(),year:1400,month:6},
                {user: id,year:1401,month:1},
            ]);
        });
        it('should return all of shareholder statistics with given market id',async ()=>{
            const res = await exec();
            expect(res.status).toBe(200);
            expect(res.body.length).toBe(3);
        });
    });


    describe('GET /api/statistic/shareholder/{id}/{year}/{month}',()=>{
        let token;
        let id;
        let year;
        let month;
        const exec = function(){
            return request(server)
                .get(`/api/statistic/shareholder/${id}/${year}/${month}`)
                .set('x-auth-token',token);
        }
        beforeEach(async ()=>{
            const user = new User({name: 'name',phone: '093333333333'});
            token = user.generateAuthToken();
            id = new mongoose.Types.ObjectId();
            year = 1401;
            month = 2;
            await MonthStatistic.insertMany([
                {user: id,year:year,month:month},
                {user: new mongoose.Types.ObjectId(),year:year,month:3},
                {user: id,year:1400,month:month},
                {user: new mongoose.Types.ObjectId(),year:1400,month:6},
                {user: id,year:year,month:1},
            ]);
        });
        it('should return 404 if statistic not found.',async()=>{
            year = 1399;
            const res = await exec();
            expect(res.status).toBe(404);
        });
        it('should return statistic with given year and month',async ()=>{
            const res = await exec();
            expect(res.status).toBe(200);
            expect(res.body).toHaveProperty('year',year);
        });
    });

    describe('PUT /api/statistic/shareholder/{id}',()=>{
        let token;
        let id;
        let isPaid;
        const exec = function(){
            return request(server)
                .put('/api/statistic/shareholder/'+id)
                .set('x-auth-token',token)
                .send({isPaid: isPaid});
        }
        beforeEach(async ()=>{
            token = new User({name: 'Name',phone: '09333333333'}).generateAuthToken();
            const stat = new MonthStatistic({
                user:new mongoose.Types.ObjectId(),
                year:1401,
                month: 4
            });
            isPaid = true;
            id = stat._id;
            await stat.save();
        });
        it('should return 400 if request body was invalid',async()=>{
            isPaid = 1;
            const res = await exec();
            expect(res.status).toBe(400);
        });
        it('should return 404 if statistic not found',async()=>{
            id = new mongoose.Types.ObjectId();
            const res = await exec();
            expect(res.status).toBe(404);
        });
        it('should change isPaid property and return new statistic info',async ()=>{
            const res = await exec();
            expect(res.status).toBe(200);
            expect(res.body).toHaveProperty('isPaid',true);
        });
    });
});