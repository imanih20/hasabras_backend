const request = require('supertest');
const mongoose = require('mongoose');
const {Product} = require('../../models/product');
const {User} = require('../../models/user');

describe('/api/product',()=>{
    let server;
    beforeEach(async ()=>{
        server = require('../../index');
    });
    afterEach(async ()=>{
        await server.close();
        await Product.deleteMany({});
        await User.deleteMany({});
    });

    describe('DELETE /api/product/',()=>{
        let token;
        let id;
        let user;
        const exec = function (){
            return request(server)
                .delete('/api/product/'+id)
                .set('x-auth-token',token);
        }
        beforeEach(async ()=>{
            user = new User({name: 'name',phone: '0933333333'});
            token = user.generateAuthToken();
            const product = new Product({
                title: 'p1',
                quantity:10,
                price: 1000,
                profit: 200
            });
            await product.save();
            id = product._id;
        });
        it('should return 404 if product nof found.',async ()=>{
            id = user._id;
            const res = await exec();
            expect(res.status).toBe(404);
        });
        it('should return deleted product',async ()=>{
            const res = await exec();
            expect(res.status).toBe(200);
            expect(res.body).toHaveProperty('title','p1');
        });
    });
    describe('GET /api/product/shareholder',()=>{
        let id;
        let token;
        const exec = function (){
            return request(server)
                .get('/api/product/shareholder/'+id)
                .set('x-auth-token',token);
        }
        beforeEach(async()=>{
            id = new mongoose.Types.ObjectId();
            await Product.insertMany([
                {title:'p1',quantity:2,price:1000,profit:100,owner:id},
                {title:'p2',quantity:2,price:1000,profit:100,owner:new mongoose.Types.ObjectId()},
                {title:'p3',quantity:2,price:1000,profit:100,owner:id},
                {title:'p4',quantity:2,price:1000,profit:100,owner:new mongoose.Types.ObjectId()},
                {title:'p5',quantity:2,price:1000,profit:100,owner:id},
            ]);
            const user = new User({name: 'name',phone: '0933333333'});
            token = user.generateAuthToken();
        });
        it('should return all of shareholder products',async ()=>{
            const res = await exec();
            expect(res.status).toBe(200);
            expect(res.body.length).toBe(3);
            expect(res.body.some(p=>p.title==='p1')).toBeTruthy();
        });
    });
    describe('GET /api/product/market',()=>{
        let id;
        let token;
        const exec = function (){
            return request(server)
                .get('/api/product/market/'+id)
                .set('x-auth-token',token);
        }
        beforeEach(async()=>{
            id = new mongoose.Types.ObjectId();
            await Product.insertMany([
                {title:'p1',quantity:2,price:1000,profit:100,market:id},
                {title:'p2',quantity:2,price:1000,profit:100,market:new mongoose.Types.ObjectId()},
                {title:'p3',quantity:2,price:1000,profit:100,market:id},
                {title:'p4',quantity:2,price:1000,profit:100,market:new mongoose.Types.ObjectId()},
                {title:'p5',quantity:2,price:1000,profit:100,market:id},
            ]);
            const user = new User({name: 'name',phone: '0933333333'});
            token = user.generateAuthToken();
        });
        it('should return all of market products',async ()=>{
            const res = await exec();
            expect(res.status).toBe(200);
            expect(res.body.length).toBe(3);
            expect(res.body.some(p=>p.title==='p1')).toBeTruthy();
        });
    });
});