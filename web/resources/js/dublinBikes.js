DUBBIKES =
{};

// Dublin's Canals Boundary Coordinates
DUBBIKES.BOUNDS =
{
	COORDINATES :
	{
		SOUTH : 53.3285828,
		WEST : -6.3109122,
		NORTH : 53.3600169,
		EAST : -6.2348616
	},
	asGMapBounds : function()
	{
		var southWest = new google.maps.LatLng(this.COORDINATES.SOUTH, this.COORDINATES.WEST);
		var northEast = new google.maps.LatLng(this.COORDINATES.NORTH, this.COORDINATES.EAST);
		return new google.maps.LatLngBounds(southWest, northEast);
	}
};

DUBBIKES.GRAPHICSPREFERENCES =
{
	CYLINDER_RADIUS : 7,
	HEIGHT_PER_BIKE : 2,
	COLORS :
	{
		CYLINDER_BLUE : 0x3333FF,
		CYLINDER_RED : 0xFF3333
	}
};

var stationMeshes = [];

DUBBIKES.removeAllStations = function(scene)
{
	for(var i=0; i<stationMeshes.length; i++)
	{
		scene.remove(stationMeshes[i]);
	}
	stationMeshes = [];
}

/**
 * @Stands A JSON array of JSONified StandSnapshot objects from Java
 */
DUBBIKES.addStations = function(scene, stands)
{
	// PLANE & TEXTURE
	
	var texture = THREE.ImageUtils.loadTexture('resources/images/map_texture.png');
	texture.minFilter = THREE.NearestFilter;
	var planeMaterial   = new THREE.MeshPhongMaterial({
		map: texture
	});
	var planeWidth = 990;
    var planeHeight = 700;
	var planeMesh= new THREE.Mesh( new THREE.PlaneGeometry( planeWidth, planeHeight ), planeMaterial );
	planeMesh.receiveShadow  = true;
	scene.add(planeMesh);
	
	var minXPos = planeMesh.position.x - (planeWidth) / 2;
	var minYPos = planeMesh.position.y - (planeHeight) / 2;
	var maxXPos = planeMesh.position.x + (planeWidth) / 2;
	var maxYPos = planeMesh.position.y + (planeHeight) / 2;

	// These two padding values are from manual tweaking - seems that the Gmap
	// bounds don't precisely line up otherwise
	var paddingAppliedY = 70;
	var paddingAppliedX = 60;

	for (var i = 0; i < stands.length; i++)
	{
		var occupiedSpaces = stands[i].currentBikes;
		var availableSpaces = stands[i].currentSpaces;
		
		var isFull = availableSpaces == 0;
		var isEmpty = occupiedSpaces == 0;

		var xPos = getXPos(minXPos + paddingAppliedX, maxXPos - paddingAppliedX, stands[i].description.longitude);
		var yPos = getYPos(minYPos + paddingAppliedY, maxYPos - paddingAppliedY, stands[i].description.latitude);
		
		var heightSolid = occupiedSpaces * this.GRAPHICSPREFERENCES.HEIGHT_PER_BIKE;
		var heightTransparent = availableSpaces * this.GRAPHICSPREFERENCES.HEIGHT_PER_BIKE;

		var color = !(isFull || isEmpty) ? DUBBIKES.GRAPHICSPREFERENCES.COLORS.CYLINDER_BLUE : DUBBIKES.GRAPHICSPREFERENCES.COLORS.CYLINDER_RED;
		
		if (!isFull)
		{
			var cylinderTransparentMesh = createCylinder(heightTransparent, true, xPos, yPos, (heightTransparent / 2) + heightSolid, color);
			cylinderTransparentMesh.snapshot = stands[i];
			scene.add(cylinderTransparentMesh);
			stationMeshes.push(cylinderTransparentMesh);
		}
		if (!isEmpty)
		{
			var cylinderSolidMesh = createCylinder(heightSolid, false, xPos, yPos, heightSolid / 2, color);
			cylinderSolidMesh.snapshot = stands[i];
			scene.add(cylinderSolidMesh);
			stationMeshes.push(cylinderSolidMesh);
		}
	}
	console.log('Cylinders plotted: '+stands.length);
};

var createCylinder = function(requiredHeight, isTransparent, xPos, yPos, zPos, cColor)
{
	var radius = DUBBIKES.GRAPHICSPREFERENCES.CYLINDER_RADIUS;
	var geometry = new THREE.CylinderGeometry(radius, radius, requiredHeight, 16);
	

	var material = new THREE.MeshLambertMaterial(
	{
		color : cColor,
		opacity : isTransparent ? 0.5 : 1,
		transparent: isTransparent
	});
	var result = new THREE.Mesh(geometry, material);
	result.receiveShadow  = true;
	result.castShadow = true;
	result.position.x = xPos;
	result.position.y = yPos;
	result.position.z = zPos;
	result.rotation.x = 90 * 3.14 / 180;
	return result;
};

var getRandomInt = function(min, max)
{
	return Math.floor(Math.random() * (max - min + 1)) + min;
};

var getXPos = function(minXPos, maxXPos, longitude)
{
	var eastWestLatRange = DUBBIKES.BOUNDS.COORDINATES.WEST - DUBBIKES.BOUNDS.COORDINATES.EAST;
	var eastWestLatFraction = (DUBBIKES.BOUNDS.COORDINATES.WEST - longitude) / eastWestLatRange;
	return minXPos + ((maxXPos - minXPos) * eastWestLatFraction);
};
var getYPos = function(minYPos, maxYPos, latitude)
{
	var northSouthLngRange = DUBBIKES.BOUNDS.COORDINATES.SOUTH - DUBBIKES.BOUNDS.COORDINATES.NORTH;
	var northSouthFraction = (DUBBIKES.BOUNDS.COORDINATES.SOUTH - latitude) / northSouthLngRange;
	return minYPos + ((maxYPos - minYPos) * northSouthFraction);
};
