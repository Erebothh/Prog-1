# TP0 - Ejercicios extras aplicados

## Qué hice

Usé la clase `Estudiante` del TP0 y le agregué dos métodos para no repetir código:

- `mostrarInformacion()`: muestra todos los datos del estudiante.
- `estaAprobado(double notaCorte)`: devuelve `true` o `false` según el promedio.

Después resolví los siete ejercicios en un mismo `Main`. Separé cada uno con un comentario y un título, así se puede probar todo de corrido.

## Ejercicio 1 - Mostrar información completa

Recorrí el arreglo de estudiantes y llamé a `mostrarInformacion()` con cada uno.

## Ejercicio 2 - Promedio general del curso

Usé un acumulador:

```java
double sumaPromedios = 0;
for (int i = 0; i < estudiantes.length; i++) {
    sumaPromedios = sumaPromedios + estudiantes[i].promedio;
}
double promedioGeneral = sumaPromedios / estudiantes.length;
```

## Ejercicio 3 - Mejor promedio

Guardé el mejor promedio en una variable auxiliar y la posición del estudiante que lo tenía. Empecé con el primero del arreglo y comparé con los demás.

## Ejercicio 4 - Estudiantes aprobados

Pedí la nota de corte por teclado y recorrí el arreglo con un `for`. Dentro usé un `if` para mostrar solamente los que llegaban a esa nota. También conté cuántos aprobaron con un contador.

## Ejercicio 5 - Buscar por nombre

Pedí un nombre y lo comparé con `equalsIgnoreCase`, para que no importe si está en mayúscula o minúscula. Si lo encontraba, mostraba toda la información; si no, avisaba que no existía.

## Ejercicio 6 - Clasificación según promedio

Usé `if / else if / else` con estos rangos:

| Promedio | Clasificación |
| --- | --- |
| 9 a 10 | Excelente |
| 8 a 8.99 | Muy bueno |
| 6 a 7.99 | Aprobado |
| 4 a 5.99 | En recuperación |
| Menos de 4 | Libre |

## Ejercicio 7 - Método con condición

Agregué el método `estaAprobado(double notaCorte)` a la clase `Estudiante` y lo usé dentro de un `if` para mostrar los estudiantes que podían rendir el final.

```java
public boolean estaAprobado(double notaCorte) {
    return promedio >= notaCorte;
}
```

## Datos de prueba

Cargué cinco estudiantes:

| Nombre | Promedio |
| --- | --- |
| Ana García | 8.5 |
| Bruno Pérez | 7.25 |
| Carla López | 9.1 |
| Diego Martínez | 5.75 |
| Elena Rodríguez | 6.0 |

Si se ejecuta con una nota de corte de 6, aprueban Ana, Bruno, Carla y Elena.

## Salida esperada

```text
=== EJERCICIO 2: PROMEDIO GENERAL ===
El promedio general del grupo es: 7,32

=== EJERCICIO 3: MEJOR PROMEDIO ===
Mejor promedio: Carla López con 9.1

=== EJERCICIO 4: APROBADOS ===
...
Cantidad de aprobados: 4

=== EJERCICIO 6: CLASIFICACIÓN ===
Ana García: Muy bueno (8.5)
...
```

## Para ejecutar

```bash
cd TP0-extras
javac -d out src/*.java
java -cp out Main
```

El programa pide la nota de corte y después un nombre para buscar. Por ejemplo, se puede probar con `6` y `Ana`.
