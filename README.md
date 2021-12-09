# Hey!! Cómo estás??  te presento TalanAPP
## Recursos principales
Aplicación desarrollada utilizando el MockServer de https://gitlab.talana.com/publico/mobile-test-mock-api. 
En ésta aplicación se siguió el patrón de diseño **MVVM**. 
Para la persistencia de datos local se utilizó  [SharedPreferences](https://developer.android.com/reference/android/content/SharedPreferences) y  [ROOM](https://developer.android.com/training/volley?hl=fa).
[Volley](https://developer.android.com/reference/androidx/room/package-summary?hl=en) para gestionar las peticiones **HTTP** al servidor.
Las imagenes fueron gestionadas mediante [Glide](https://github.com/bumptech/glide) y las animaciones por [Lottie](https://lottiefiles.com/).


## Configuraciones de la red

La configuración del servidor la encontramos en el **gradle**. 
Para utilizar la aplicación se debe cambiar ésta configuración o levantar un servidor en la red LAN cuya url sea "http://192.168.100.7:3100/" 

	buildTypes { 
	debug {
	  //        Config for localhost / emulator in same PC 
	  //        buildConfigField "String", 'SERVER_URL', 
	 //        Config for LAN conection ATENTION: replace IP and PORT for data of Server'"http://10.0.2.2:3100/"'
		 buildConfigField "String", 'SERVER_URL', '"http://192.168.100.7:3100/"'
	 }
	 release {  
	 buildConfigField "String", 'SERVER_URL', '"http://192.168.100.7:3100/"'
	 ...
	 }
   
