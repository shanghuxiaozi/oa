<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ include file="/static/common/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<meta charset="UTF-8" />
	<title>义和信达</title>
	<link rel="stylesheet" type="text/css" href="${basePath}/static/css/login.css"/>
	<link rel="stylesheet" type="text/css" href="${basePath}/static/css/verify.css">
	<script src="${basePath }/static/js/jquery.md5.js"></script>
	 <link rel=stylesheet href="${basePath }/static/Three.js/css/base.css"/>
</head>
<body>


<script src="${basePath }/static/Three.js/js/Three.js"></script>
<script src="${basePath }/static/Three.js/js/Detector.js"></script>
<script src="${basePath }/static/Three.js/js/Stats.js"></script>
<script src="${basePath }/static/Three.js/js/OrbitControls.js"></script>
<script src="${basePath }/static/Three.js/js/KeyboardState.js"></script>
<script src="${basePath }/static/Three.js/js/THREEx.FullScreen.js"></script>
<script src="${basePath }/static/Three.js/js/THREEx.WindowResize.js"></script>

<!-- script src="js/ShaderParticles.js"></script -->
<script src="${basePath }/static/Three.js/js/ShaderParticleGroup.js"></script>
<script src="${basePath }/static/Three.js/js/ShaderParticleEmitter.js"></script>

<!-- Code to display an information button and box when clicked. 
<script src="${basePath }/static/Three.js/js/jquery-1.9.1.js"></script>-->
<script src="${basePath }/static/Three.js/js/jquery-ui.js"></script>
<link rel=stylesheet href="${basePath }/static/Three.js/css/jquery-ui.css" />
<link rel=stylesheet href="${basePath }/static/Three.js/css/info.css"/>
<script src="${basePath }/static/Three.js/js/info.js"></script>
<div id="infoButton"></div>
<div id="infoBox" title="小义温馨提示">
这是我们花巨资打造的顶级OA
<a href="http://www.yihexinda.com">义和信达官网</a>
</div>
<!-- ------------------------------------------------------------ -->

<div id="ThreeJS" style="position: absolute; left:0px; top:0px"></div>
<script>
/*
	Three.js "tutorials by example"
	Author: Lee Stemkoski
	Date: September 2013 (three.js v60)
 */

// MAIN

// standard global variables
var container, scene, camera, renderer, controls, stats;
var keyboard = new KeyboardState();
var clock = new THREE.Clock();

// custom global variables
var mesh;

init();
animate();

// FUNCTIONS 		
function init() 
{
	// SCENE
	scene = new THREE.Scene();
	// CAMERA
	var SCREEN_WIDTH = window.innerWidth, SCREEN_HEIGHT = window.innerHeight;
	var VIEW_ANGLE = 45, ASPECT = SCREEN_WIDTH / SCREEN_HEIGHT, NEAR = 0.1, FAR = 20000;
	camera = new THREE.PerspectiveCamera( VIEW_ANGLE, ASPECT, NEAR, FAR);
	scene.add(camera);
	camera.position.set(0,-50,400);
	camera.lookAt(scene.position);	
	// RENDERER
	if ( Detector.webgl )
		renderer = new THREE.WebGLRenderer( {antialias:true} );
	else
		renderer = new THREE.CanvasRenderer(); 
	renderer.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
	container = document.getElementById( 'ThreeJS' );
	container.appendChild( renderer.domElement );
	// EVENTS
	THREEx.WindowResize(renderer, camera);
	THREEx.FullScreen.bindKey({ charCode : 'm'.charCodeAt(0) });
	// CONTROLS
	controls = new THREE.OrbitControls( camera, renderer.domElement );
	// STATS
	stats = new Stats();
	stats.domElement.style.position = 'absolute';
	stats.domElement.style.bottom = '0px';
	stats.domElement.style.zIndex = 100;
	container.appendChild( stats.domElement );
	// LIGHT
	var light = new THREE.PointLight(0xffffff);
	light.position.set(100,250,100);
	scene.add(light);
	// FLOOR
	var floorTexture = new THREE.ImageUtils.loadTexture( '${basePath }/static/Three.js/images/checkerboard.jpg' );
	floorTexture.wrapS = floorTexture.wrapT = THREE.RepeatWrapping; 
	floorTexture.repeat.set( 10, 10 );
	var floorMaterial = new THREE.MeshBasicMaterial( { map: floorTexture, side: THREE.DoubleSide } );
	var floorGeometry = new THREE.PlaneGeometry(1000, 1000, 10, 10);
	var floor = new THREE.Mesh(floorGeometry, floorMaterial);
	floor.position.y = -0.5;
	floor.rotation.x = Math.PI / 2;
	// scene.add(floor);
	// SKYBOX
	var imagePrefix = "${basePath }/static/Three.js/images/DarkSea-";
	var directions  = ["xpos", "xneg", "ypos", "yneg", "zpos", "zneg"];
	var imageSuffix = ".jpg";
	var skyGeometry = new THREE.CubeGeometry( 10000, 10000, 10000 );	
	var materialArray = [];
	for (var i = 0; i < 6; i++)
		materialArray.push( new THREE.MeshBasicMaterial({
			map: THREE.ImageUtils.loadTexture( imagePrefix + directions[i] + imageSuffix ),
			side: THREE.BackSide
		}));
	var skyMaterial = new THREE.MeshFaceMaterial( materialArray );
	var skyBox = new THREE.Mesh( skyGeometry, skyMaterial );
	scene.add( skyBox );
	
	////////////
	// CUSTOM //
	////////////
	
	emitterSettings = {
                type: 'sphere',
                positionSpread: new THREE.Vector3(10, 10, 10),
				acceleration: new THREE.Vector3(0, -20, 0),
                radius: 10,
                speed: 40,
				speedSpread: 20,
                sizeStart: 30,
                // sizeStartSpread: 30,
                sizeEnd: 10,
                opacityStart: 1,
				opacityMiddle: 1,
                opacityEnd: 0,
                colorStart: new THREE.Color('white'),
				colorStartSpread: new THREE.Vector3(0.5,0.5,0.5),
                colorMiddle: new THREE.Color('red'),
                colorEnd: new THREE.Color('red'),
                particlesPerSecond: 2000,
                alive: 0, // initially disabled, will be triggered later
                emitterDuration: 0.1
            };
			
	// Create a particle group to add the emitter
	this.particleGroup = new ShaderParticleGroup(
	{
		texture: THREE.ImageUtils.loadTexture('${basePath }/static/Three.js/images/spark.png'),
		maxAge: 2,
		colorize: 1,
		blending: THREE.AdditiveBlending,
	});
	
	var colors = ["red", "orange", "yellow", "green", "blue", "violet", "pink", "magenta", "cyan", "steelblue", "seagreen"];
	for (var i = 0; i < colors.length; i++)
	{
		emitterSettings.colorMiddle = new THREE.Color( colors[i] );
		emitterSettings.colorEnd = new THREE.Color( colors[i] );
		particleGroup.addPool( 1, emitterSettings, false );
	}
	
	// Add the particle group to the scene so it can be drawn.
	scene.add( particleGroup.mesh );

}

function animate() 
{
    requestAnimationFrame( animate );
	render();		
	update();
}

function randomVector3(xMin, xMax, yMin, yMax, zMin, zMax)
{
	return new THREE.Vector3( xMin + (xMax - xMin) * Math.random(),
		yMin + (yMax - yMin) * Math.random(), zMin + (zMax - zMin) * Math.random() );
}

function update()
{
	keyboard.update();

	if ( keyboard.down("1") )
		particleGroup.triggerPoolEmitter( 1, randomVector3(-200,200, 50,200, -200,-100) );
	
	var delta = clock.getDelta();
	particleGroup.tick( delta );

	if ( Math.random() < delta )
		particleGroup.triggerPoolEmitter( 1, randomVector3(-200,200, 50,200, -200,-100) );
	
	controls.update();
	stats.update();
}

function render() 
{
	renderer.render( scene, camera );
}

</script>



<font color="red" id="msg" style="display: none;"></font>

<form class="layui-form" action=""  method="post" id="formId">
	<div class="main">
		<div class="box">
			<div class="logo">
				<img src="${basePath}/static/image/logo_04.png"/>
			</div>
			<div class="login-box">
				<div class="top">
					<span id="helder">
						用户登录
					</span>
				</div>
				<div class="lg-main">
					<p class="p1">账号</p>
					<p><input type="text" name="username" required style="padding-left: 6px;" placeholder="用户名" autocomplete="off" id="userName" /></p>
					<p class="p1">密码</p>
					<p>
					<input type="password" id="password" required style="padding-left: 6px;" placeholder="密码" autocomplete="off" class="layui-input" />
						<input type="hidden" name="password" id="psw" />
					</p>
					<p class="p1">验证码</p>

					<div id="mpanel3" class="clearfix">
					</div>

				</div>
				<div class="foot" id="foot1">
					<button type="button" id="check-btn2" class="verify-btn">确定</button>
				</div>

				<div class="foot" id="foot2" style="display: none">
					<input type="submit" id="Btn" value="登录" />
				</div>
				<div class="bot clearfix">
					<p class="bot-p1"><a href="${basePath }/">进入后台</a></p>
					<p class="bot-p2"><a href="${basePath }/logout">注销</a></p>
				</div>
				<div class="bot" style="margin-top: 8px;"><font color="red" size="2">${msg}</font></div>
			</div>

			<div class="footer">
				<div class="con_left">深圳义和信达科技有限公司技术支持 粤ICP备 11016239号-7<p><span>
			<img src="${basePath }/static/image/images/gonghui20160706_84.jpg" /></span><span><img src="${basePath }/static/image/images/gonghui20160706_86.jpg" /></span></p></div></div>

			</div>

		</div>
	</form>

<script type="text/javascript" src="${basePath }/static/js/verify.js" ></script>
<script type="text/javascript">

		$("#Btn").click(function(){
			var password = $.md5($('#password').val());
			$('#psw').val($.md5(password+"_Random_Key"));
//			$('#psw').val($.md5($('#password').val()));
		});

		$('#mpanel3').codeVerify({
			type : 2,
			figure : 100,		//位数，仅在type=2时生效
			arith : 0,			//算法，支持加减乘，不填为随机，仅在type=2时生效
			width : '100px',
			height : '30px',
			fontSize : '16px',
			btnId : 'check-btn2',
			ready : function() {
			},
			success : function() {
				$("#foot1").hide();
                $("#foot2").show();
			},
			error : function() {

			}
		});

</script>
</body>
</html>