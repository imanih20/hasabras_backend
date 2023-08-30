const request = require('supertest');
const mongoose = require('mongoose');
const {User} = require('../../models/user');
const {Shareholder} = require('../../models/shareholder');

describe('/api/shareholder',()=>{
    let server;
    beforeEach(()=>{
        server = require('../../index');
    });
    afterEach(async ()=>{
        await server.close();
        await Shareholder.deleteMany({});
    });

    describe('POST /api/shareholder/',()=>{
        let token;
        let user;
        let shareholder;
        const exec = function (){
            return request(server)
                .post('/api/shareholder/')
                .set('x-auth-token',token)
                .send(shareholder);
        }
        beforeEach(async ()=>{
            user = new User({name: 'n',phone:'0933333333333'});
            token = user.generateAuthToken();
            shareholder = {
                name: 'h1',
                phone: '0933333333',
                share: 0.5,
                marketId: new mongoose.Types.ObjectId()
            }
        });
        it('should return 400 if invalid request body was send',async ()=>{
            shareholder.market = 'test';
            const res = await exec();
            expect(res.status).toBe(400);
        });
        it('should return shareholder info',async ()=>{
            const res = await exec();
            expect(res.status).toBe(200);
            expect(res.body).toHaveProperty('phone',shareholder.phone);
        });
    });
    describe('DELETE /api/shareholder/',()=>{
        let token;
        let user;
        let shareholder;
        let id;
        const exec = function (){
            return request(server)
                .delete('/api/shareholder/'+id)
                .set('x-auth-token',token);
        }
        beforeEach(async ()=>{
            user = new User({name: 'n',phone:'0933333333333'});
            token = user.generateAuthToken();
            shareholder = new Shareholder({
                name: 'h1',
                phone: '0933333333',
                share: 0.5,
                marketId: new mongoose.Types.ObjectId()
            });
            await shareholder.save();
            id = shareholder._id;
        });
        it('should return 404 if id is invalid.',async ()=>{
            id = user._id;
            const res = await exec();
            expect(res.status).toBe(404);
        });
        it('should return shareholder info',async ()=>{
            const res = await exec();
            expect(res.status).toBe(200);
            expect(res.body).toHaveProperty('phone',shareholder.phone);
        });
    });
    describe('PUT /api/shareholder/',()=>{
        let token;
        let user;
        let shareholder;
        let id;
        let name;
        const exec = function (){
            return request(server)
                .put('/api/shareholder/'+id)
                .set('x-auth-token',token)
                .send({
                    name: name,
                    phone: shareholder.phone,
                    share: shareholder.share,
                })
        }
        beforeEach(async ()=>{
            user = new User({name: 'n',phone:'0933333333333'});
            token = user.generateAuthToken();
            name = 'new h2';
            shareholder = new Shareholder({
                name: 'h1',
                phone: '0933333333',
                share: 0.5,
                marketId: new mongoose.Types.ObjectId()
            });
            await shareholder.save();
            id = shareholder._id;
        });
        it('should return 400 if request body was invalid',async ()=>{
            name = 3;
            const res = await exec();
            expect(res.status).toBe(400);
        })
        it('should return 404 if id is invalid.',async ()=>{
            id = user._id;
            const res = await exec();
            expect(res.status).toBe(404);
        });
        it('should return shareholder info',async ()=>{
            const res = await exec();
            expect(res.status).toBe(200);
            expect(res.body).toHaveProperty('phone',shareholder.phone);
            expect(res.body).toHaveProperty('name',name);

        });
    });
    describe('GET /api/shareholder/market',()=>{
        let token;
        let id;
        const exec = function (){
            return request(server)
                .get('/api/shareholder/market/'+id)
                .set('x-auth-token',token);
        }
        beforeEach(async()=>{
            const user = new User({name: 'name',phone: '09345455445'});
            token = user.generateAuthToken();
            id = new mongoose.Types.ObjectId();
            await Shareholder.insertMany([
                {name:'sh1',phone:'0933333333333',share: 0.5,market: id},
                {name:'sh2',phone:'0933313333333',share: 0.5,market: id},
                {name:'sh3',phone:'0933323333333',share: 0.5,market: new mongoose.Types.ObjectId()},
                {name:'sh4',phone:'0933343333333',share: 0.5,market: id},
                {name:'sh5',phone:'0933363333333',share: 0.5,market: id},
            ]);
        });
        it('should return all of market shareholders',async ()=>{
            const res = await exec();
            expect(res.status).toBe(200);
            expect(res.body.length).toBe(4);
            expect(res.body.some(s=>s.name === 'sh1'));
        });
    });
});
