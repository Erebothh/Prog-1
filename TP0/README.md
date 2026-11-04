# TP0 - Primer proyecto Java y clase Estudiante

## Qué hice

Seguí el tutorial para crear el proyecto en VS Code, con el JDK y la extensión **Extension Pack for Java**, y después resolví la actividad de la clase `Estudiante`.

La clase quedó en `src/Estudiante.java` y el programa principal en `src/Main.java`.

## La clase Estudiante

La clase tiene estos atributos:

- `nombre`
- `apellido`
- `edad`
- `carrera`
- `promedio`

En este TP todavía no usamos encapsulamiento, así que los atributos son públicos. La idea era practicar la creación de clases, objetos y constructores. En el TP1 ya los pasamos a privados.

Escribí dos constructores:

```java
public Estudiante() {
    // constructor sin parámetros
}

public Estudiante(String nombre, String apellido, int edad,
                  String carrera, double promedio) {
    this.nombre = nombre;
    this.apellido = apellido;
    this.edad = edad;
    this.carrera = carrera;
    this.promedio = promedio;
}
```

El `this` sirve para diferenciar el atributo del objeto del parámetro que llega al constructor. Por ejemplo, `this.nombre` es el atributo y `nombre` es el parámetro.

## El main

En el `main` creé cuatro estudiantes:

- Dos con el constructor sin parámetros, asignando los datos después con el operador punto.
- Dos con el constructor con parámetros.

Después los guardé en un arreglo de `Estudiante`, lo recorrí con un `for` y mostré el nombre y el promedio de cada uno.

```java
Estudiante[] estudiantes = new Estudiante[4];
estudiantes[0] = e1;
estudiantes[1] = e2;
estudiantes[2] = e3;
estudiantes[3] = e4;

for (int i = 0; i < estudiantes.length; i++) {
    System.out.println(estudiantes[i].nombre + " "
            + estudiantes[i].apellido + " - Promedio: "
            + estudiantes[i].promedio);
}
```

## Salida esperada

```text
=== LISTADO DE ESTUDIANTES ===
Ana García - Promedio: 8.5
Bruno Pérez - Promedio: 7.25
Carla López - Promedio: 9.1
Diego Martínez - Promedio: 6.75

=== DATOS COMPLETOS ===
...
```

## Conceptos que repasé

**POO:** es una forma de programar usando objetos. Un objeto junta datos (atributos) y acciones (métodos).

**Clase:** es el molde o la plantilla. Por ejemplo, `Estudiante` es la clase.

**Objeto:** es una cosa concreta creada a partir de la clase. Por ejemplo, `ana` es un objeto de tipo `Estudiante`.

**Instanciar:** es crear el objeto con `new`.

**Constructor:** es el método especial que se ejecuta al crear el objeto. Sirve para dejarlo con datos válidos desde el principio.

**Operador punto:** se usa para acceder a los atributos y métodos de un objeto, por ejemplo `ana.promedio` o `ana.mostrarDatos()`.

## Para ejecutar

```bash
cd TP0
javac -d out src/*.java
java -cp out Main
```
