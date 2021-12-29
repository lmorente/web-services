export async function GetWeather(call, callback) {
    await sleep(getRandomTimeout(1000, 3000));
    console.log('Request received: ' + call.request.city);
    const response = createResponse(call.request.city)
    callback(null, response);
}

function getRandomTimeout(min, max) {
    return Math.floor((Math.random() * (max - min)) + min);
}

function sleep(millis) {
    return new Promise(resolve => {
        setTimeout(() => resolve(), millis);
    })
}

function createResponse(city) {
    return {city: city, weather: findweather(city)};
}

function findweather(city) {
    let weather = "";
    if (city && city.length > 0)
        weather = isVocal(city) ? "Rainy" : "Sunny";
    return weather;
}

function isVocal(value) {
    const vocals = ["a", "e", "i", "o", "u"];
    return vocals.includes(value.toLowerCase().charAt(0));
}