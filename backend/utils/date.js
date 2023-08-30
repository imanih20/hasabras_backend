module.exports.getDate = function(date){
    const dateArray = date.trim().split('-');
    if(dateArray.length === 3){
        const year = parseInt(dateArray[0]);
        if(!isNaN(year)){
            const month = parseInt(dateArray[1]);
            if(!isNaN(month)) {
                const day = parseInt(dateArray[2]);
                if(!isNaN(day)){
                    return {
                        year: year,
                        month: month,
                        day: day
                    };
                }
            }
        }
    }
    return null;
}