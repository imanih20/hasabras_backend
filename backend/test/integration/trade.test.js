const request = require('supertest');
const {Trade} = require('../../models/trade');
const {MonthStatistic} = require('../../models/month_statistic');
const {MarketStatistic} = require('../../models/market_statistic');
const {Product} = require('../../models/product');
const {User} = require('../../models/user');
const { default: mongoose } = require('mongoose');
const { Market } = require('../../models/market');
const { Shareholder } = require('../../models/shareholder');

describe('/api/trade',()=>{
    let server;
    beforeEach(()=>{
        server = require('../../index');
    });
    afterEach(async ()=>{
        await server.close();
        await Trade.deleteMany({});
        await User.deleteMany({});
        await Product.deleteMany({});
        await MonthStatistic.deleteMany({});
        await MarketStatistic.deleteMany({});
        await Shareholder.deleteMany({});
    });
    describe('POST /api/trade/purchase/{id}',()=>{
        let token;
        let id;
        let productInfo;
        let product;
        const exec = function (){
            return request(server)
                .post('/api/trade/purchase/'+id)
                .set('x-auth-token',token)
                .send(productInfo);
        }
        beforeEach(async ()=>{
            token = new User({name: 'Name',phone: '09333333333'}).generateAuthToken();
            id = new mongoose.Types.ObjectId();
            // id = '';
            productInfo = {
                title: "product",
                quantity: 3,
                totalPrice: 9000,
                date: '1401-04-17',
                marketId: new mongoose.Types.ObjectId().toString(),
                ownerId: new mongoose.Types.ObjectId().toString(),
                salePrice: 4000,
            },
            product = new Product({
                title: 'p1',
                quantity:10,
                price: 1000,
                profit: 200
            });
            await product.save();
        });
        it('should return 400 if request body was invalid',async ()=>{
            productInfo = '';
            const res = await exec();
            expect(res.status).toBe(400);
        });
        it('should return trade info',async ()=>{
            const res = await exec();
            expect(res.status).toBe(200);
            expect(res.body).toHaveProperty('type','p');
        });
        it('should return trade info and save market owner statistics',async()=>{
            id= product._id;
            const res = await exec();
            expect(res.status).toBe(200);
        });
    });

    describe('POST /api/trade/sale',()=>{
        let token;
        let id;
        let saleInfo;
        let p2;
        const exec = function (){
            return request(server)
                .post('/api/trade/sale/'+id)
                .set('x-auth-token',token)
                .send(saleInfo);
        }
        beforeEach(async()=>{
            token = new User({name: 'Name',phone: '09333333333'}).generateAuthToken();
            const product = new Product({
                title: 'p1',
                quantity:10,
                price: 1000,
                profit: 200
            });
            id= product._id;
            await product.save();
            const sh = new Shareholder({name: '1234',phone:'1345',share:0.5});
            await sh.save();
            const ownerId = sh._id;
            p2 = new Product({
                title: 'p1',
                quantity:10,
                price: 1000,
                profit: 200,
                owner: ownerId
            });
            await p2.save();
            saleInfo = {
                quantity : 5,
                totalPrice: 5000,
                date:'1401-04-18'
            }
        });
        it('should return 400 if request body was invalid',async ()=>{
            saleInfo.date = 1401;
            const res = await exec();
            expect(res.status).toBe(400);
        });
        it('should return 404 if product not found',async ()=>{
            id = new mongoose.Types.ObjectId();
            const res = await exec();
            expect(res.status).toBe(404);
        });
        it('should return trade info',async ()=>{
            const res = await exec();
            expect(res.status).toBe(200);
            expect(res.body).toHaveProperty('type','s');
        });
        it('should save shareholder statistic and return product info',async ()=>{
            id = p2._id;
            const res = await exec();
            expect(res.status).toBe(200);
            expect(res.body).toHaveProperty('type','s');
        });
    });

    describe('GET /api/trade/sale/{date}', ()=>{
        let user;
        let shareholder;
        let token;
        let date;
        const exec = function(){
            return request(server)
                .get('/api/trade/sale/'+date)
                .set('x-auth-token',token);
        }
        beforeEach(async ()=>{
            user = new User({name: 'Name',phone: '09333333333'});
            token = user.generateAuthToken();
            const p1 = new mongoose.Types.ObjectId();
            const p2 = new mongoose.Types.ObjectId();
            const p3 = new mongoose.Types.ObjectId();
            date = '';
            shareholder = new Shareholder({name: 'name',phone:'0933333333',share: 0.6});
            const market = new Market({
                name:'test',
                owner: user._id
            });
            await market.save();

            await Product.insertMany([
                {
                    _id: p1,
                    title: 'p1',
                    quantity: 10,
                    price: 1000,
                    profit: 100,
                    owner: shareholder._id,
                    market: market._id
                },
                {
                    _id: p2,
                    title: 'p2',
                    quantity: 10,
                    price: 1000,
                    profit: 100,
                    owner: new mongoose.Types.ObjectId(),
                    market: market._id
                },
                {
                    _id: p3,
                    title: 'p3',
                    quantity: 10,
                    price: 1000,
                    profit: 100,
                    owner: shareholder._id,
                    market: market._id
                }
            ]);
            const type = 's';
            await Trade.insertMany([
                {product:p1,quantity:10,totalPrice:100000,type:type,date:'1401-01-02'},
                {product:p2,quantity:10,totalPrice:100000,type:type,date:'1401-01-02'},
                {product:p3,quantity:10,totalPrice:100000,type:type,date:'1401-02-02'},
                {product:p3,quantity:10,totalPrice:100000,type:type,date:'1401-01-02'},
                {product:p2,quantity:10,totalPrice:100000,type:type,date:'1401-04-02'},
                {product:p1,quantity:10,totalPrice:100000,type:type,date:'1401-03-02'},
                {product:p1,quantity:10,totalPrice:100000,type:type,date:'1401-01-02'},
            ]);
        });
        it('should return all of market owner trades info',async ()=>{
            const res = await exec();
            expect(res.status).toBe(200);
            expect(res.body.length).toBe(7);
        });
        it('should return all of shareholder trades info',async ()=>{
            token = shareholder.generateAuthToken();
            const res = await exec();
            expect(res.status).toBe(200);
            expect(res.body.length).toBe(5);
        });
        it('should return all of market owner trades info for given date',async ()=>{
            date = '1401-01-02';
            const res = await exec();
            expect(res.status).toBe(200);
            expect(res.body.length).toBe(4);
        });
        it('should return all of shareholder trades info for given date',async ()=>{
            date = '1401-01-02';
            token = shareholder.generateAuthToken();
            const res = await exec();
            expect(res.status).toBe(200);
            expect(res.body.length).toBe(3);
        });
    });

    describe('GET /api/trade/purchase/{date}', ()=>{
        let user;
        let shareholder;
        let token;
        let date;
        const exec = function(){
            return request(server)
                .get('/api/trade/purchase/'+date)
                .set('x-auth-token',token);
        }
        beforeEach(async ()=>{
            user = new User({name: 'Name',phone: '09333333333'});
            token = user.generateAuthToken();
            const p1 = new mongoose.Types.ObjectId();
            const p2 = new mongoose.Types.ObjectId();
            const p3 = new mongoose.Types.ObjectId();
            date = '';
            shareholder = new Shareholder({name: 'name',phone:'0933333333',share: 0.6});
            const market = new Market({
                name:'test',
                owner: user._id
            });
            await market.save();

            await Product.insertMany([
                {
                    _id: p1,
                    title: 'p1',
                    quantity: 10,
                    price: 1000,
                    profit: 100,
                    owner: shareholder._id,
                    market: market._id
                },
                {
                    _id: p2,
                    title: 'p2',
                    quantity: 10,
                    price: 1000,
                    profit: 100,
                    owner: new mongoose.Types.ObjectId(),
                    market: market._id
                },
                {
                    _id: p3,
                    title: 'p3',
                    quantity: 10,
                    price: 1000,
                    profit: 100,
                    owner: shareholder._id,
                    market: market._id
                }
            ]);
            const type = 'p';
            await Trade.insertMany([
                {product:p1,quantity:10,totalPrice:100000,type:type,date:'1401-01-02'},
                {product:p2,quantity:10,totalPrice:100000,type:type,date:'1401-01-02'},
                {product:p3,quantity:10,totalPrice:100000,type:type,date:'1401-02-02'},
                {product:p3,quantity:10,totalPrice:100000,type:type,date:'1401-01-02'},
                {product:p2,quantity:10,totalPrice:100000,type:type,date:'1401-04-02'},
                {product:p1,quantity:10,totalPrice:100000,type:type,date:'1401-03-02'},
                {product:p1,quantity:10,totalPrice:100000,type:type,date:'1401-01-02'},
            ]);
        });
        it('should return all of market owner trades info',async ()=>{
            const res = await exec();
            expect(res.status).toBe(200);
            expect(res.body.length).toBe(7);
        });
        it('should return all of shareholder trades info',async ()=>{
            token = shareholder.generateAuthToken();
            const res = await exec();
            expect(res.status).toBe(200);
            expect(res.body.length).toBe(5);
        });
        it('should return all of market owner trades info for given date',async ()=>{
            date = '1401-01-02';
            const res = await exec();
            expect(res.status).toBe(200);
            expect(res.body.length).toBe(4);
        });
        it('should return all of shareholder trades info for given date',async ()=>{
            date = '1401-01-02';
            token = shareholder.generateAuthToken();
            const res = await exec();
            expect(res.status).toBe(200);
            expect(res.body.length).toBe(3);
        });
    });
    describe('DELETE /api/trade/{id}',()=>{
        let token;
        let id;

        let tid2;
        let tid3;
        let tid4;
        const exec = function(){
            return request(server)
                .delete('/api/trade/'+id)
                .set('x-auth-token',token);
        }
        beforeEach(async ()=>{
            token = new User({name: 'Name',phone: '09333333333'}).generateAuthToken();
            const marketId= new mongoose.Types.ObjectId();
            const userId= new mongoose.Types.ObjectId();
            const p1= new mongoose.Types.ObjectId();
            const p2= new mongoose.Types.ObjectId();
            const p3= new mongoose.Types.ObjectId();
            const tid1= new mongoose.Types.ObjectId();
            tid2= new mongoose.Types.ObjectId();
            tid3= new mongoose.Types.ObjectId();
            tid4= new mongoose.Types.ObjectId();
            await Product.insertMany([
                {
                    _id: p1,
                    title: 'p',
                    quantity: 20,
                    price: 300000,
                    profit : 400,
                    market: marketId,
                    owner: userId
                },
                {
                    _id: p2,
                    title: 'p',
                    quantity: 20,
                    price: 300000,
                    profit : 400,
                },
                {
                    _id: p3,
                    title: 'p',
                    quantity: 20,
                    price: 300000,
                    profit : 400,
                    market: marketId,
                    owner: new mongoose.Types.ObjectId()
                }
            ])



            const marketStat = new MarketStatistic({
                market: marketId,
                monthSale: 1000000,
                monthProfit: 100000000,
                monthPurchase: 10000000,
                monthOwnerSale: 1000000,
                monthOwnerProfit: 100000000,
                monthOwnerPurchase: 10000000,
                year: 1401,
                month: 1
            });
            await marketStat.save();

            const monthStat = new MonthStatistic({
                user: userId,
                totalPurchase: 1000000,
                totalSale: 100000000,
                profit: 1000000,
                year: 1401,
                month: 1
            })
            await monthStat.save();
            
            await Trade.insertMany([
                {_id:tid1,product:p1,quantity:10,totalPrice:100000,type:'s',date:'1401-01-02'},
                {_id:tid2,product:p2,quantity:10,totalPrice:100000,type:'s',date:'1401-01-02'},
                {_id:tid3,product:p3,quantity:10,totalPrice:100000,type:'s',date:'1401-01-02'},
                {_id:tid4,quantity:10,totalPrice:100000,type:'s',date:'1401-01-02'},

            ])
            id = tid1;
        });
        it('should return 404 if trade not found.',async ()=>{
            id = new mongoose.Types.ObjectId();
            const res = await exec();
            expect(res.status).toBe(404);
        });
        it('should return 404 if product not found.',async ()=>{
            id = tid4;
            const res = await exec();
            expect(res.status).toBe(404);
        });
        it('should return 404 if month statistic not found.',async ()=>{
            id = tid3;
            const res = await exec();
            expect(res.status).toBe(404);
        });
        it('should return 404 if market statistic not found.',async ()=>{
            id = tid2;
            const res = await exec();
            expect(res.status).toBe(404);
        });
        it('should return deleted trade info',async ()=>{
            const res = await exec();
            expect(res.status).toBe(200);
        });
    });
});