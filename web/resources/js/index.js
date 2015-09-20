var map, controls, camera, scene, pointLight, raycaster;
var mouse = new THREE.Vector2(), INTERSECTED;
var CONTAINER_WIDTH, CONTAINER_HEIGHT;

function init3d()
{
	container = document.getElementById( 'container' );
	
	// CAMERA
	CONTAINER_WIDTH = container.offsetWidth;
	CONTAINER_HEIGHT = container.offsetHeight;
	var VIEW_ANGLE = 45, ASPECT = CONTAINER_WIDTH / CONTAINER_HEIGHT, NEAR = 0.1, FAR = 10000;
	camera = new THREE.PerspectiveCamera( VIEW_ANGLE, ASPECT, NEAR, FAR);
	camera.position.set(0,-422,1017);
	camera.up.set(0,0,1);

	//CONTROLS
	controls = new THREE.OrbitControls(camera);
	controls.addEventListener( 'change', render );
	
	// SCENE
	scene = new THREE.Scene();
	scene.add(camera);
	
	// MOUSE/RAYCASTER
	raycaster = new THREE.Raycaster();
	container.addEventListener( 'mousemove', onDocumentMouseMove, false );
	
	// RENDERER
	renderer = new THREE.WebGLRenderer({ alpha: true });
    renderer.setClearColor( 0x000000, 0 );
    renderer.shadowMapEnabled = true;
	renderer.setSize(CONTAINER_WIDTH, CONTAINER_HEIGHT);
	container.appendChild( renderer.domElement );
	
	// LIGHT
    var ambientLight = new THREE.AmbientLight(0x888888);
    scene.add(ambientLight);
    var sunLight = new THREE.SpotLight(0xbbbb88);

    sunLight.position.set(500, -2000, 2000);
    scene.add(sunLight);
	
	render();

	setInterval(function () {render();}, 0.01);//wait for the textures to load, then render
}

var render = function() 
{
	camera.updateMatrixWorld();
	raycaster.setFromCamera( mouse, camera );
	var intersects = raycaster.intersectObjects( scene.children );

	if ( intersects.length > 1 )
	{
		// intersects listed in array in order of first intersected on line from mouse to infinity
		// so the plane will be last, and one of the cylinders making up the stand will be first 
		var intersectedObject = intersects[0].object;
		
		if (INTERSECTED == null || (intersectedObject.name != INTERSECTED.name))
		{
			$('#standName').text(intersectedObject.snapshot.description.name)
			$('#standStats').text(intersectedObject.snapshot.currentSpaces + " spaces, " + intersectedObject.snapshot.currentBikes + " bikes");
			$('#standDetail').fadeIn(250);
			//$('#standDetail').css('visibility', 'visible');
			console.log('hovered: '+intersectedObject.name);
			INTERSECTED = intersectedObject;
		}

	} else if(INTERSECTED != null){
		//$('#standDetail').css('visibility', 'hidden');
		$('#standDetail').fadeOut(250);
		console.log('un-hovered');

		INTERSECTED = null;

	}
	
	renderer.render( scene, camera );
};

function onDocumentMouseMove( event ) {

	event.preventDefault();

	mouse.x = ( event.clientX / CONTAINER_WIDTH ) * 2 - 1;
	mouse.y = - ( event.clientY / CONTAINER_HEIGHT ) * 2 + 1;
}


/* STUFF FROM TUTORIAL 

var container, stats;
var camera, scene, raycaster, renderer;

var mouse = new THREE.Vector2(), INTERSECTED;
var radius = 100, theta = 0;

init();
animate();

function init() {

	container = document.createElement( 'div' );
	document.body.appendChild( container );

	var info = document.createElement( 'div' );
	info.style.position = 'absolute';
	info.style.top = '10px';
	info.style.width = '100%';
	info.style.textAlign = 'center';
	info.innerHTML = '<a href="http://threejs.org" target="_blank">three.js</a> webgl - interactive cubes';
	container.appendChild( info );

	camera = new THREE.PerspectiveCamera( 70, window.innerWidth / window.innerHeight, 1, 10000 );

	scene = new THREE.Scene();

	var light = new THREE.DirectionalLight( 0xffffff, 1 );
	light.position.set( 1, 1, 1 ).normalize();
	scene.add( light );

	var geometry = new THREE.BoxGeometry( 20, 20, 20 );

	for ( var i = 0; i < 2000; i ++ ) {

		var object = new THREE.Mesh( geometry, new THREE.MeshLambertMaterial( { color: Math.random() * 0xffffff } ) );

		object.position.x = Math.random() * 800 - 400;
		object.position.y = Math.random() * 800 - 400;
		object.position.z = Math.random() * 800 - 400;

		object.rotation.x = Math.random() * 2 * Math.PI;
		object.rotation.y = Math.random() * 2 * Math.PI;
		object.rotation.z = Math.random() * 2 * Math.PI;

		object.scale.x = Math.random() + 0.5;
		object.scale.y = Math.random() + 0.5;
		object.scale.z = Math.random() + 0.5;

		scene.add( object );

	}

	raycaster = new THREE.Raycaster();

	renderer = new THREE.WebGLRenderer();
	renderer.setClearColor( 0xf0f0f0 );
	renderer.setPixelRatio( window.devicePixelRatio );
	renderer.setSize( window.innerWidth, window.innerHeight );
	renderer.sortObjects = false;
	container.appendChild(renderer.domElement);

	stats = new Stats();
	stats.domElement.style.position = 'absolute';
	stats.domElement.style.top = '0px';
	container.appendChild( stats.domElement );

	document.addEventListener( 'mousemove', onDocumentMouseMove, false );

	//

	window.addEventListener( 'resize', onWindowResize, false );

}

function onWindowResize() {

	camera.aspect = window.innerWidth / window.innerHeight;
	camera.updateProjectionMatrix();

	renderer.setSize( window.innerWidth, window.innerHeight );

}

function onDocumentMouseMove( event ) {

	event.preventDefault();

	mouse.x = ( event.clientX / window.innerWidth ) * 2 - 1;
	mouse.y = - ( event.clientY / window.innerHeight ) * 2 + 1;

}

//

function animate() {

	requestAnimationFrame( animate );

	render();
	stats.update();

}

function render() {

	theta += 0.1;

	camera.position.x = radius * Math.sin( THREE.Math.degToRad( theta ) );
	camera.position.y = radius * Math.sin( THREE.Math.degToRad( theta ) );
	camera.position.z = radius * Math.cos( THREE.Math.degToRad( theta ) );
	camera.lookAt( scene.position );

	camera.updateMatrixWorld();

	// find intersections

	raycaster.setFromCamera( mouse, camera );

	var intersects = raycaster.intersectObjects( scene.children );

	if ( intersects.length > 0 ) {

		if ( INTERSECTED != intersects[ 0 ].object ) {

			if ( INTERSECTED ) INTERSECTED.material.emissive.setHex( INTERSECTED.currentHex );

			INTERSECTED = intersects[ 0 ].object;
			INTERSECTED.currentHex = INTERSECTED.material.emissive.getHex();
			INTERSECTED.material.emissive.setHex( 0xff0000 );

		}

	} else {

		if ( INTERSECTED ) INTERSECTED.material.emissive.setHex( INTERSECTED.currentHex );

		INTERSECTED = null;

	}

	renderer.render( scene, camera );

}

*/