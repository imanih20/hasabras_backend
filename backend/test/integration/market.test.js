const request = require('supertest');
const {Market} = require('../../models/market');
const {User} = require('../../models/user');
const {Shareholder} = require('../../models/shareholder');
const mongoose = require('mongoose');

describe('/api/market',()=>{
    let server;
    beforeEach(()=>{
        server = require('../../index');
    });
    afterEach(async()=>{
        await server.close();
        await Market.deleteMany({});
        await User.deleteMany({});
    });

    describe('POST /api/market/',()=>{
        let user;
        let token;
        let name;
        const exec = function (){
            return request(server)
                .post('/api/market/')
                .set('x-auth-token',token)
                .send({
                    name: name,
                });
        }

        beforeEach(async()=>{
            user = new User({name: 'mohyeddin',phone:'09345751523'});
            await user.save();
            token = user.generateAuthToken();
            name = 'new market'
        });
        it('should return 403 if shareholder request this url',async ()=>{
            token = new Shareholder({name:'test',phone:'test',share:1}).generateAuthToken();
            const res = await exec();
            expect(res.status).toBe(403);
        });
        it('should return 400 if invalid request body was send',async ()=>{
            name = 2;
            const res = await exec();
            expect(res.status).toBe(400);
        });
        it('should return 200 and created market',async ()=>{
            const res = await exec();
            expect(res.status).toBe(200);
            expect(res.body).toHaveProperty('owner');
        });
    });

    describe('DELETE /api/market/',()=>{
        let user;
        let id;
        let token;
        const exec = function(){
            return request(server)
                .delete('/api/market/'+id)
                .set('x-auth-token',token);
        }
        beforeEach(async ()=>{
            user = new User({name: 'name',phone: '0933333333'});
            token = user.generateAuthToken();
            const market = new Market({name: 'market',owner:user._id});
            id = market._id;
            await market.save();
        });

        it('should return 400 if id was invalid',async ()=>{
            id = "1234";
            const res = await exec();
            expect(res.status).toBe(400);
        });
        it('should return 404 if market was not found',async()=>{
            id = user._id;
            const res = await exec();
            expect(res.status).toBe(404);
        });
        it('should return market',async ()=>{
            const res = await exec();
            expect(res.status).toBe(200);
            expect(res.body).toHaveProperty('owner')
        });
    });
    describe('PUT /api/market/',()=>{
        let token;
        let name;
        let id;
        let user;
        const exec = function () {
            return request(server)
                .put('/api/market/'+id)
                .set('x-auth-token',token)
                .send({
                    name:name
                });
        }
        beforeEach(async ()=>{
            user = new User({name: 'name',phone: '0933333333'});
            token = user.generateAuthToken();;
            const market = new Market({
                name: 'test',
                owner: user._id
            });
            await market.save();
            name = 'new name';
            id = market._id;
        });
        it('should return 400 if request body was invalid',async ()=>{
            name = 3;
            const res = await exec();
            expect(res.status).toBe(400);
        });
        it('should return 404 if market not found',async ()=>{
            id = user._id;
            const res = await exec();
            expect(res.status).toBe(404);
        });
        it('should update market name and return it',async ()=>{
            const res = await exec();
            expect(res.status).toBe(200);
            expect(res.body).toHaveProperty('name',name);
        });
    });
    describe('GET /api/market/',()=>{
        let token;
        let user;
        const exec = function (){
            return request(server)
                .get('/api/market/')
                .set('x-auth-token',token);
        }
        beforeEach(async()=>{
            user = new User({name: 'name',phone:'09888888888'})
            token = user.generateAuthToken();
            const market = new Market({
                name: 'market',
                owner: user._id
            });
            await market.save();
        });
        it('should return 404 if market was not found.',async ()=>{
            user._id = new mongoose.Types.ObjectId();
            token = user.generateAuthToken();
            const res = await exec();
            expect(res.status).toBe(404);
        });
        it('should return market',async ()=>{
            const res = await exec();
            expect(res.status).toBe(200);
            expect(res.body).toHaveProperty('name','market');
        });
    });
});