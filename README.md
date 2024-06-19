# ESP32 Flasher

En este repositorio vamos a ver todo el código y la documentación generada para esta aplicación en Java, capaz de flashear programas previamente compilados a un controlador ESP32. (Próximamente distintos modelos)

## Contenido

Dentro del repositorio encontraremos tanto documentación para usuario como documentación técnica. También encontraremos una presentación con una pequeña introducción al mundo de los microcontroladores y los pasos mas importantes a tener en cuenta.
También encontraremos código en C tanto compilado como sin compilar, los cuales usaremos para programas los microcontroladores.

Por último, encontramos el código del programa creado para programar las placas de desarrollo.

## Componentes necesarios

Para la utilización de este código será necesario disponer de:
### Software

* Java
* Python


### Hardware
* Microcontrolador ESP32
* Cable MicroUSB

## Estructura de directorios

```

TFG-ESP32/
├── .idea                              // Componentes del IDE     
├── src/main                           // Núcleo del proyecto.
│   ├── java/org/proyecto              // Aqui encontramos el código fuente de la aplicación java.
|       ├── Main.java                  //Programa principal, solo llama Ventana_Final.java
|       └── Vista
|           ├── HelpWindow.java         // Ventana de ayuda.
|           ├── MencionesWindow.java    // Ventana de menciones especiales.
|           └── VentanaFinal.java       // Ventana principal y lógica de funcionamiento.
│   ├── resources                       // Aqui encontramos los archivos compilados de cada programa.
|       ├── Access_Point
|       ├── Blink_Fade
|       ├── Bluethoot_Chat
|       └── Web_Server
│   ├── Docs                       // Toda documentación refente al proyecto.
|       ├── Presentación.pxpt
|       ├── Documentación_presentación.pdf
|       └── Documentación Técnica.pdf
│     
├── pom.xml    // Dependencias del proyecto.
├── .gitignore
└── README.md

```

## Acerca de

Este proyecto ha sido realizado por **Salvador López Criado**, alumno de 2º de DAM del Celia Viñas.
Me gustaría hacer mención a los profesores Evaristo Romero, Alfonso Martínez y Diego Gay, los cuales me han enseñado un nivel de algoritmia, manejo de procesos y diseño de interfaces necesario
para el desarrollo de esta aplicación.

Tambien mencionar a la empresa **[IKOSTECH S.L.](https://www.ikostech.es/cue/)**, empresa encargada de mi FCT, la cual me ha brindado un trato increible y me siento muy agradecido de haber vivido esta experiencia.

Este trabajo lo he hecho como favor a IKOSTECH, de cara a ayudar a proximos desarrolladores de firmware a pasar por el camino por el que he tenido que pasar yo para aprender acerca de este tema.

