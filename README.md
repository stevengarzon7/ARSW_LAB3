
  
  
### Escuela Colombiana de Ingeniería
### Arquitecturas de Software – ARSW


## Laboratorio – Programación concurrente, condiciones de carrera y sincronización de hilos - Caso Inmortales

### Descripción
Este laboratorio tiene como fin que el estudiante conozca y aplique conceptos propios de la programación concurrente, además de estrategias que eviten condiciones de carrera.
### Dependencias:

* [Ejercicio Introducción al paralelismo - Hilos - BlackList Search](https://github.com/ARSW-ECI-beta/PARALLELISM-JAVA_THREADS-INTRODUCTION_BLACKLISTSEARCH)
#### Parte I – Antes de terminar la clase.

Control de hilos con wait/notify. Productor/consumidor.

##### 1. Revise el funcionamiento del programa y ejecútelo. Mientras esto ocurren, ejecute jVisualVM y revise el consumo de CPU del proceso correspondiente. A qué se debe este consumo?, cual es la clase responsable?
El consumo de cpu es alto,  se debe por que la clase consumidor  
no tiene ninguna instrucción de espera a la clase productor en consumir,
es decir que una vez el productor agrega algo a la lista, el
consumidor antes de que el productor pueda agregar algo más ya lo habrá consumido.

![consumoAlto](https://github.com/stevengarzon7/ARSW_Lab3/blob/master/img/imagen1.png?raw=true)

##### 2. Haga los ajustes necesarios para que la solución use más eficientemente la CPU, teniendo en cuenta que -por ahora- la producción es lenta y el consumo es rápido. Verifique con JVisualVM que el consumo de CPU se reduzca.
Se añadió un Thread sleep tal cual como esta en producer a consumer, bajando considerablemente la carga de CPU

![consumoBajo](https://github.com/stevengarzon7/ARSW_Lab3/blob/master/img/imagen2.png?raw=true)
![consumoBajo](https://github.com/stevengarzon7/ARSW_Lab3/blob/master/img/imagen3.png?raw=true)
![consumoBajo](https://github.com/stevengarzon7/ARSW_Lab3/blob/master/img/imagen4.png?raw=true)

##### 3. Haga que ahora el productor produzca muy rápido, y el consumidor consuma lento. Teniendo en cuenta que el productor conoce un límite de Stock (cuantos elementos debería tener, a lo sumo en la cola), haga que dicho límite se respete. Revise el API de la colección usada como cola para ver cómo garantizar que dicho límite no se supere. Verifique que, al poner un límite pequeño para el 'stock', no haya consumo alto de CPU ni errores.

El productor produce mas rapido de lo que el consumidor consume

![consumoMinimo](https://github.com/stevengarzon7/ARSW_Lab3/blob/master/img/imagen5.png?raw=true)

La diferencia entre el primer punto y este es considerable  
en cuanto al uso del CPU durante la ejecución del programa
a continuación se ve el consumo del CPU y la propuesta  
para llegar a hacer mejor uso del CPU en este programa:

![consumoBajo](https://github.com/stevengarzon7/ARSW_Lab3/blob/master/img/imagen6.png?raw=true)
![consumoBajo](https://github.com/stevengarzon7/ARSW_Lab3/blob/master/img/imagen7.png?raw=true)
![consumoBajo](https://github.com/stevengarzon7/ARSW_Lab3/blob/master/img/imagen8.png?raw=true)

 
#### Parte II. – Antes de terminar la clase.

Teniendo en cuenta los conceptos vistos de condición de carrera y sincronización, haga una nueva versión -más eficiente- del ejercicio anterior (el buscador de listas negras). En la versión actual, cada hilo se encarga de revisar el host en la totalidad del subconjunto de servidores que le corresponde, de manera que en conjunto se están explorando la totalidad de servidores. Teniendo esto en cuenta, haga que:

- La búsqueda distribuida se detenga (deje de buscar en las listas negras restantes) y retorne la respuesta apenas, en su conjunto, los hilos hayan detectado el número de ocurrencias requerido que determina si un host es confiable o no (_BLACK_LIST_ALARM_COUNT_).
- Lo anterior, garantizando que no se den condiciones de carrera.

Se creo la clase blackListControlador con el objetivo de controlar el atributo ocurrencesCount de tal manera 
que cuando fuera mayor a 5 es decir que la ip se encontrara en 5 de las listas, cada uno de los threads retornara las ocurrencias sin necesidad de 
terminar de recorrer todo el subconjunto

![Arreglo](https://github.com/stevengarzon7/ARSW_Lab3/blob/master/img/imagen9.png?raw=true)





#### Parte II. – Avance para la siguiente clase

Sincronización y Dead-Locks.

![](http://files.explosm.net/comics/Matt/Bummed-forever.png)

1. Revise el programa “highlander-simulator”, dispuesto en el paquete edu.eci.arsw.highlandersim. Este es un juego en el que:

	* Se tienen N jugadores inmortales.
	* Cada jugador conoce a los N-1 jugador restantes.
	* Cada jugador, permanentemente, ataca a algún otro inmortal. El que primero ataca le resta M puntos de vida a su contrincante, y aumenta en esta misma cantidad sus propios puntos de vida.
	* El juego podría nunca tener un único ganador. Lo más probable es que al final sólo queden dos, peleando indefinidamente quitando y sumando puntos de vida.

2. Revise el código e identifique cómo se implemento la funcionalidad antes indicada. Dada la intención del juego, un invariante debería ser que la sumatoria de los puntos de vida de todos los jugadores siempre sea el mismo(claro está, en un instante de tiempo en el que no esté en proceso una operación de incremento/reducción de tiempo). Para este caso, para N jugadores, cual debería ser este valor?.

N x 100 es el invariante dado que el valor por defecto de los puntos de vida para todos los inmortales es de 100  
es decir que la sumatoria durante todo el juego debe ser siempre de N * 100.

3. Ejecute la aplicación y verifique cómo funcionan las opción ‘pause and check’. Se cumple el invariante?.


![Invariante](https://github.com/stevengarzon7/ARSW_Lab3/blob/master/img/imagen10.png?raw=true)
![Invariante](https://github.com/stevengarzon7/ARSW_Lab3/blob/master/img/imagen11.png?raw=true)

Está en cambio constante y nunca cumple que la sumatoria sea igual a N * 100 
siendo N el número de inmortales. Por lo tanto, no se cumple el invariante.

4. Una primera hipótesis para que se presente la condición de carrera para dicha función (pause and check), es que el programa consulta la lista cuyos valores va a imprimir, a la vez que otros hilos modifican sus valores. Para corregir esto, haga lo que sea necesario para que efectivamente, antes de imprimir los resultados actuales, se pausen todos los demás hilos. Adicionalmente, implemente la opción ‘resume’.

Se implementaron las funciones 

![Invariante](https://github.com/stevengarzon7/ARSW_Lab3/blob/master/img/imagen12.png?raw=true)


5. Verifique nuevamente el funcionamiento (haga clic muchas veces en el botón). Se cumple o no el invariante?.

En todos los caso fue exitoso el resultado, es decir el invariante  
se cumple como se puede apreciar en la imagen pauseAndCheckTest.

![Invariante](https://github.com/stevengarzon7/ARSW_Lab3/blob/master/img/imagen13.png?raw=true)

6. Identifique posibles regiones críticas en lo que respecta a la pelea de los inmortales. Implemente una estrategia de bloqueo que evite las condiciones de carrera. Recuerde que si usted requiere usar dos o más ‘locks’ simultáneamente, puede usar bloques sincronizados anidados:

	```java
	synchronized(locka){
		synchronized(lockb){
			…
		}
	}
	```

![Estrategia](https://github.com/stevengarzon7/ARSW_Lab3/blob/master/img/imagen14.png?raw=true)

La estrategia se llevó a cabo en el metodo "figt()" de la clase "Inmortal".

7. Tras implementar su estrategia, ponga a correr su programa, y ponga atención a si éste se llega a detener. Si es así, use los programas jps y jstack para identificar por qué el programa se detuvo.

Al usar JPS y JSTACK se pudo ver lo que estaba pasando, el programa se encontraba en un deadlock, lo que quiere  
decir que por alguna razón se quedaron esperandose mutuamente los hilos (Immortal)

8. Plantee una estrategia para corregir el problema antes identificado (puede revisar de nuevo las páginas 206 y 207 de _Java Concurrency in Practice_).

9. Una vez corregido el problema, rectifique que el programa siga funcionando de manera consistente cuando se ejecutan 100, 1000 o 10000 inmortales. Si en estos casos grandes se empieza a incumplir de nuevo el invariante, debe analizar lo realizado en el paso 4.

![Estrategia](https://github.com/stevengarzon7/ARSW_Lab3/blob/master/img/imagen15.png?raw=true)

Con 100

![Estrategia](https://github.com/stevengarzon7/ARSW_Lab3/blob/master/img/imagen16.png?raw=true)

Con 1000

![Estrategia](https://github.com/stevengarzon7/ARSW_Lab3/blob/master/img/imagen17.png?raw=true)

Con 1000

10. Un elemento molesto para la simulación es que en cierto punto de la misma hay pocos 'inmortales' vivos realizando peleas fallidas con 'inmortales' ya muertos. Es necesario ir suprimiendo los inmortales muertos de la simulación a medida que van muriendo. Para esto:
	* Analizando el esquema de funcionamiento de la simulación, esto podría crear una condición de carrera? Implemente la funcionalidad, ejecute la simulación y observe qué problema se presenta cuando hay muchos 'inmortales' en la misma. Escriba sus conclusiones al respecto en el archivo RESPUESTAS.txt.
	* Corrija el problema anterior __SIN hacer uso de sincronización__, pues volver secuencial el acceso a la lista compartida de inmortales haría extremadamente lenta la simulación.

11. Para finalizar, implemente la opción STOP.

![Stop](https://github.com/stevengarzon7/ARSW_Lab3/blob/master/img/imagen18.png?raw=true)

Se implementa la opción Stop

## Control de versiones

por: 
+ [Santiago Buitrago](https://github.com/DonSantiagoS) 
+ [Steven Garzon](https://github.com/stevengarzon7) 

Version: 1.0
Fecha: 26 de febrero 2021

### Autores

* **Santiago Buitrago** - *Laboratorio N°3* - [DonSantiagoS](https://github.com/DonSantiagoS)
* **Steven Garzon** - *Laboratorio N°3* - [stevengarzon7](https://github.com/stevengarzon7)



<a rel="license" href="http://creativecommons.org/licenses/by-nc/4.0/"><img alt="Creative Commons License" style="border-width:0" src="https://i.creativecommons.org/l/by-nc/4.0/88x31.png" /></a><br />Este contenido hace parte del curso Arquitecturas de Software del programa de Ingeniería de Sistemas de la Escuela Colombiana de Ingeniería, y está licenciado como <a rel="license" href="http://creativecommons.org/licenses/by-nc/4.0/">Creative Commons Attribution-NonCommercial 4.0 International License</a>.
