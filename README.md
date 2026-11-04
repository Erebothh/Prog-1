# Resoluciones Prog-1

Acá dejé todo lo que había en la carpeta **Prog-1** resuelto y separado por práctico. La idea fue hacerlo con las herramientas que vimos en la materia: clases, objetos, encapsulamiento, herencia, polimorfismo, excepciones, arreglos y después las estructuras de `java.util`.

No está hecho con cosas raras ni con atajos: está escrito con `for`, `if`, `while`, `ArrayList`, `TreeMap`, etc., como lo resolvería alguien que recién empieza. Igual traté de que compile y funcione todo.

## Integrantes

- Thiago Sturzenegger
- Ángel Ferri
- Walter Rodríguez
- Álvaro Chirino

**Repositorio:** https://github.com/Erebothh/Prog-1

## Organización

```
Resoluciones/
├── TP0/                  -> actividad de la clase Estudiante
├── TP0-extras/           -> los 7 ejercicios extras
├── TP1/                  -> los 5 ejercicios del TP1
└── Proyecto/
    ├── Entregable1/      -> CityLink con arreglos
    └── Entregable2/      -> CityLink con estructuras dinámicas y algoritmos
```

Cada carpeta tiene su propio `README.md` con la explicación de lo que hice y cómo ejecutarlo.

## Cómo compilar

Necesitás un JDK instalado (con Java 17 o superior alcanza). Desde la carpeta de cada práctico:

### TP0 y TP0-extras

```bash
cd TP0
javac -d out src/*.java
java -cp out Main
```

```bash
cd TP0-extras
javac -d out src/*.java
java -cp out Main
```

### TP1

```bash
cd TP1
javac -d out src/*.java
java -cp out MainEjercicio1
```

También están `MainEjercicio2`, `MainEjercicio3`, `MainEjercicio4` y `MainEjercicio5`.

### Proyecto CityLink

```bash
cd Proyecto/Entregable1
javac -d out $(find src -name "*.java")
java -cp out app.Main
```

```bash
cd Proyecto/Entregable2
javac -d out $(find src -name "*.java")
java -cp out app.Main
```

## Sobre el repositorio y los commits

El proyecto integrador pide un repositorio de GitHub con commits de todos los integrantes. El repositorio del grupo es **https://github.com/Erebothh/Prog-1**.

Para cumplir con ese punto, cada integrante tiene que hacer al menos un commit con su nombre configurado en Git. Una forma simple es repartir el trabajo como se sugiere en los README de cada entregable y subir los cambios por separado.

También conviene leer el código antes de entregarlo. En la exposición oral pueden preguntar cualquier línea, así que lo mejor es entenderlo y, si algo no cierra, cambiarlo por una versión propia.
