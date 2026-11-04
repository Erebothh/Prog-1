# TP1 - Encapsulamiento, herencia, clases abstractas y polimorfismo

## Qué contiene

Cada ejercicio tiene su propio `main` para poder probarlo por separado:

| Archivo | Ejercicio |
| --- | --- |
| `MainEjercicio1.java` | Encapsulamiento y relaciones entre clases |
| `MainEjercicio2.java` | Herencia y constructores |
| `MainEjercicio3.java` | Clases abstractas y sobrescritura |
| `MainEjercicio4.java` | Polimorfismo |
| `MainEjercicio5.java` | Integración de conceptos (alquiler de vehículos) |

## Ejercicio 1 - Encapsulamiento y relaciones

### Estudiante

Pasé todos los atributos a `private` e hice getters y setters. Las validaciones quedaron así:

- La edad tiene que ser mayor a 16.
- El promedio tiene que estar entre 0 y 10.
- El nombre y el apellido no pueden estar vacíos.

```java
public void setEdad(int edad) {
    if (edad <= 16) {
        throw new IllegalArgumentException("La edad debe ser mayor a 16 años.");
    }
    this.edad = edad;
}
```

También agregué una lista de materias:

```java
private ArrayList<Materia> materias;
```

Y dos métodos:

- `agregarMateria(Materia materia)`
- `calcularPromedio()`, que suma las calificaciones de las materias y las divide por la cantidad.

### Materia

Tiene los atributos `nombre`, `codigo`, `creditos` y `calificacion`, todos privados. El setter de la calificación valida que esté entre 0 y 10.

### Carrera y Universidad

`Carrera` tiene un nombre y una lista de estudiantes. Tiene estos métodos:

- `agregarEstudiante(Estudiante estudiante)`
- `listarEstudiantes()`
- `buscarEstudiante(String nombre)`

`Universidad` tiene nombre, dirección y una lista de estudiantes. La usé en el `main` para mostrar la relación.

### Main

Creé una carrera, tres estudiantes y varias materias. Después mostré el promedio de cada estudiante, listé todos los inscriptos y busqué a Ana por nombre.

## Ejercicio 2 - Herencia y constructores

- `Persona` tiene `nombre` y `dni` privados.
- `Alumno` hereda de `Persona` y agrega `legajo` y `promedio`.
- El constructor de `Alumno` llama a `super(nombre, dni)`.
- `Alumno` sobrescribe `mostrarDatos()` y usa `super.mostrarDatos()` para no repetir el código de la clase base.

```java
public Alumno(String nombre, String dni, int legajo, double promedio) {
    super(nombre, dni);
    setLegajo(legajo);
    setPromedio(promedio);
}
```

## Ejercicio 3 - Clases abstractas

`Empleado` es abstracta y tiene `nombre`, `legajo` y el método abstracto:

```java
public abstract double calcularSueldo();
```

Las subclases calculan distinto:

- **EmpleadoPlanta:** `sueldoBase + (sueldoBase * 0.02 * antiguedadAnios)`
- **EmpleadoContratado:** `horasTrabajadas * valorHora`

Los dos usan `@Override`.

## Ejercicio 4 - Polimorfismo

Armé un arreglo de `Empleado` con objetos `EmpleadoPlanta` y `EmpleadoContratado`. Lo recorrí y llamé a `calcularSueldo()` sin preguntar el tipo:

```java
for (int i = 0; i < empleados.length; i++) {
    System.out.println(empleados[i].getNombre()
            + " -> $" + empleados[i].calcularSueldo());
}
```

No usé `instanceof` ni `switch`.

### Qué pasa cuando se ejecuta

Java mira el objeto real, no el tipo de la variable. Si la variable es de tipo `Empleado` pero el objeto es un `EmpleadoPlanta`, se ejecuta el `calcularSueldo()` de `EmpleadoPlanta`. Eso es el **despacho dinámico** o polimorfismo.

## Ejercicio 5 - Alquiler de vehículos

`Vehiculo` es abstracta y tiene patente, marca, modelo y el método:

```java
public abstract double calcularAlquiler(int dias);
```

Las clases concretas son:

- `Auto`: precio por día.
- `Motocicleta`: precio por día más un cargo por el casco.
- `AutoElectrico`: precio por día más un cargo por la carga.
- `Camioneta`: precio por día más un recargo por tamaño (es la ampliación).

En el `main` hay un arreglo de `Vehiculo`. Se pide la cantidad de días y se recorre el arreglo llamando a `calcularAlquiler(dias)`. Tampoco hay `if` ni `switch` para decidir el cálculo.

## Algunos errores que me aparecieron

- Al principio me olvidé de llamar a `super(...)` en `Alumno` y Java no compilaba porque `Persona` no tenía constructor vacío.
- En el Ejercicio 3 puse `calcularSueldo` sin `@Override`; funcionaba, pero lo agregué para que quede claro que estoy sobrescribiendo.
- En el Ejercicio 5, si no validaba los días, se podía pedir un alquiler de 0 o negativo. Agregué un `while` para pedir de nuevo.
- Un error lógico que tuve fue dividir por la cantidad de materias cuando la lista estaba vacía. Lo solucioné devolviendo 0 si no hay materias.

## Para ejecutar

```bash
cd TP1
javac -d out src/*.java

java -cp out MainEjercicio1
java -cp out MainEjercicio2
java -cp out MainEjercicio3
java -cp out MainEjercicio4
java -cp out MainEjercicio5
```

El Ejercicio 5 pide la cantidad de días. Por ejemplo, se puede probar con `3`.
