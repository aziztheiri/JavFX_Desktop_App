let map;

async function initMap(callback) {
    const {Map} = await google.maps.importLibrary("maps");
    var myLatlng = new google.maps.LatLng(36.80766601939333, 10.180356455967974);

    map = new Map(document.getElementById("map"), {
        center: {lat: 36.80766601939333, lng: 10.180356455967974},
        zoom: 8,
    });
    var marker = new google.maps.Marker({
        position: myLatlng,
        map: map,
        draggable: true,
        title: "Drag me!"
    });
    google.maps.event.addListener(map, 'click', function (event) {
        marker.setPosition(event.latLng);
        reverseGeocode(event.latLng.lat(),event.latLng.lng());

    });
}
    function reverseGeocode(lat, lng) {
        var geocoder = new google.maps.Geocoder();

        var latlng = { lat: parseFloat(lat), lng: parseFloat(lng) };

        geocoder.geocode({ 'location': latlng }, function (results, status) {
            if (status === 'OK') {
                if (results[0]) {
                    var locationName = results[0].formatted_address;
                    javaConnector.updateMarker(locationName);

                } else {
                    console.error('No results found');
                }
            } else {
                console.error('Geocoder failed due to: ' + status);
            }
        });
    }

initMap();