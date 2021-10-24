var lat;
var lng;
$(document).ready(function () {
    var app = new Mapp({
        element: '#app',
        presets: {
            latlng: {
                lat: 35.73249,
                lng: 51.42268,
            },
            zoom: 10
        },
        apiKey: 'eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6IjYxOTE4MGM3MWU1NzExODE0NDAzZTk3MTFiZjcxN2ZhODIwMTM5ZjViM2Y3MDNhYjVlOTcxZmRkZDZmMTMwNGYxOTRiMTliMjgwYjcwOTZjIn0.eyJhdWQiOiIxNDYyNiIsImp0aSI6IjYxOTE4MGM3MWU1NzExODE0NDAzZTk3MTFiZjcxN2ZhODIwMTM5ZjViM2Y3MDNhYjVlOTcxZmRkZDZmMTMwNGYxOTRiMTliMjgwYjcwOTZjIiwiaWF0IjoxNjI0NzM1NzI1LCJuYmYiOjE2MjQ3MzU3MjUsImV4cCI6MTYyNzMyNzcyNSwic3ViIjoiIiwic2NvcGVzIjpbImJhc2ljIl19.F1pDeSlV3lhpispxOjHdX0pwY80XYyzEMmqJYdRNbMm30LMMtWiswYSNjpgC11VOPEG9fJiljF355qmX_ZIq93xaOO2Ff1gNw6FUgackl5TW534wwP678L5zs-xFDncESn90lqKa4OCZh-TH_u5bq7BZMSymZVby-hRVQOd8l1K9Z2RYfgUYF5EoUPxMqffIERbPZJcniu0nSVHijegPaAqiO9uScr5kwhaHOh8Oar4HI-dNz2jvVGOaMU3ozafIhRDIp4vZuFp4LW-zpUbaaMQf2wtBDSzAfYqCd5BfU1Zt4ASPwTnnu-NPayKlvlXC36Ce45GCZ_O7JYZqSVq8fw'
    });
    app.addLayers();
    app.map.on('click', function (e) {
        lat = e.latlng.lat;
        lng = e.latlng.lng;
        console.log(lat)
        console.log(lng)
        document.getElementById("lat").value=lat;
        document.getElementById("lng").value=lng;
        var marker = app.addMarker({
            name: 'advanced-marker',
            latlng: {
                lat: e.latlng.lat,
                lng: e.latlng.lng,
            },
            icon: app.icons.red,
            popup: {
                title: {
                    i18n: 'marker-title',
                },
                description: {
                    i18n: 'marker-description',
                },
                class: 'marker-class',
                open: true,
            },
            pan: false,
            draggable: true,
            history: false,
            on: {
                click: function () {
                    console.log('Click callback');
                },
                contextmenu: function () {
                    console.log('Contextmenu callback');
                },
            },
        });
        app.showReverseGeocode({
            state: {
                latlng: {
                    lat: e.latlng.lat,
                    lng: e.latlng.lng,
                },
                zoom: 16,
            },
        });
    })
});