const axios = require('axios');

module.exports.sendVerificationCode = (phone,code) => {
    // var config = {
    //     method: 'post',
    //     url: 'https://api.sms.ir/v1/send/verify',
    //     headers: {
    //         'Content-Type': 'application/json',
    //         'Accept': 'text/plain',
    //         'x-api-key': '1fO7fhGxcBzufHHa4scndB21aHerKWv0U0TKqLrXkxx1n7Bmmk70zxv2usafXml2'
    //     },
    //     data: JSON.stringify({
    //         "mobile":phone,
    //         "templateId": "100000",
    //         "parameters":[
    //             {name:"Code",value:code},
    //         ],
    //     }),
    // }
    // return axios(config);
    console.log('send verification 1234');
}