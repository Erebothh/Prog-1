# Recorrido del código, clase por clase

Esta guía explica qué hace cada parte del proyecto y por qué está escrita así. La idea es que puedas abrir un archivo, ubicarte y explicar qué pasa sin memorizar.

El orden recomendado para leer es:

1. `TP0` y `TP0-extras`: clases, objetos, arreglos y métodos.
2. `TP1`: encapsulamiento, herencia, clases abstractas y polimorfismo.
3. `Proyecto/Entregable1`: el sistema CityLink con arreglos.
4. `Proyecto/Entregable2`: el mismo sistema con colecciones y algoritmos.

---

## 1. TP0: clase `Estudiante`

Archivos:

- `TP0/src/Estudiante.java`
- `TP0/src/Main.java`

### `Estudiante`

Es una clase simple con cinco atributos públicos:

```java
public String nombre;
public String apellido;
public int edad;
public String carrera;
public double promedio;
```

En este práctico los atributos son públicos porque todavía no se vio encapsulamiento. En el TP1 se pasan a `private` y se usan getters y setters.

Tiene dos constructores:

```java
public Estudiante() {
    this.nombre = "";
    this.apellido = "";
    this.edad = 0;
    this.carrera = "";
    this.promedio = 0.0;
}
```

El constructor vacío inicializa todo en valores neutros. Java lo agregaría solo si no hubiera ningún constructor, pero lo escribimos igual para practicar.

```java
public Estudiante(String nombre, String apellido, int edad, String carrera, double promedio) {
    this.nombre = nombre;
    this.apellido = apellido;
    this.edad = edad;
    this.carrera = carrera;
    this.promedio = promedio;
}
```

El constructor con parámetros permite crear el objeto ya cargado. `this.nombre` es el atributo; `nombre` es el parámetro.

`mostrarDatos()` imprime los datos con `System.out.println`.

### `Main`

1. Crea `e1` y `e2` con el constructor vacío y después asigna los datos con el punto.
2. Crea `e3` y `e4` con el constructor completo.
3. Guarda los cuatro en un arreglo `Estudiante[]`.
4. Recorre el arreglo con un `for` y muestra nombre y promedio.
5. Recorre otra vez y llama a `mostrarDatos()`.

Conceptos que aparecen:

- Clase y objeto.
- Constructor.
- Atributos.
- Operador punto.
- Arreglo de objetos.
- `for` con índice.
- `System.out.println`.

---

## 2. TP0-extras: los 7 ejercicios

Archivos:

- `TP0-extras/src/Estudiante.java`
- `TP0-extras/src/Main.java`

### `Estudiante`

Es parecido al del TP0, pero agrega métodos:

```java
public String obtenerNombreCompleto() {
    return nombre + " " + apellido;
}

public boolean estaAprobado(double notaCorte) {
    return promedio >= notaCorte;
}
```

`estaAprobado` devuelve un `boolean`. Es la forma de encapsular una condición: en vez de repetir `promedio >= notaCorte` en el `Main`, se pregunta al objeto.

### `Main`

Crea un arreglo de cinco estudiantes y resuelve los ejercicios en orden:

1. **Información completa**: recorre y llama a `mostrarInformacion()`.
2. **Promedio general**: suma todos los promedios y divide por `estudiantes.length`.
3. **Mejor promedio**: arranca con el primero y compara con los demás.
4. **Aprobados**: pide una nota de corte y cuenta los que llegan.
5. **Buscar por nombre**: recorre con `equalsIgnoreCase` y corta con `break`.
6. **Clasificación**: usa `if / else if / else` según el promedio.
7. **Método con condición**: llama a `estaAprobado(6.0)`.

El método `leerDouble` intenta convertir el texto a `double`; si falla, usa un valor por defecto.

Conceptos que aparecen:

- Métodos con retorno.
- `boolean`.
- `if / else if / else`.
- Búsqueda lineal.
- `break`.
- `equalsIgnoreCase`.
- `Double.parseDouble`.
- `try/catch`.

---

## 3. TP1: encapsulamiento, herencia y polimorfismo

### Ejercicio 1: `Estudiante`, `Materia`, `Carrera`, `Universidad`

Archivos:

- `TP1/src/Estudiante.java`
- `TP1/src/Materia.java`
- `TP1/src/Carrera.java`
- `TP1/src/Universidad.java`
- `TP1/src/MainEjercicio1.java`

#### `Estudiante`

Ahora los atributos son `private`:

```java
private String nombre;
private String apellido;
private int edad;
private String carrera;
private double promedio;
private ArrayList<Materia> materias;
```

Se accede con getters y setters. Los setters validan:

- nombre y apellido no vacíos,
- edad mayor a 16,
- promedio entre 0 y 10.

`agregarMateria` agrega a la lista. `calcularPromedio` recorre las materias y promedia las calificaciones. Si no hay materias, devuelve `0.0` para no dividir por cero.

`mostrarDatos` muestra los datos y las materias. `toString` devuelve solo nombre y apellido, para que al imprimir el objeto se vea lindo.

#### `Materia`

Tiene nombre, código, créditos y calificación. Valida que el nombre y el código no estén vacíos, que los créditos sean positivos y que la calificación esté entre 0 y 10. `toString` arma una línea con todos los datos.

#### `Carrera`

Guarda un nombre y un `ArrayList<Estudiante>`. `agregarEstudiante` valida que no sea `null`. `listarEstudiantes` recorre la lista. `buscarEstudiante` hace una búsqueda lineal por nombre con `equalsIgnoreCase` y devuelve `null` si no encuentra.

#### `Universidad`

Es parecida a `Carrera`, pero con nombre y dirección. Guarda estudiantes y los lista. Sirve para mostrar que varias clases se pueden relacionar con `Estudiante`.

#### `MainEjercicio1`

1. Crea una carrera.
2. Crea materias.
3. Crea estudiantes y les agrega materias.
4. Calcula el promedio con las materias y lo guarda con `setPromedio`.
5. Agrega los estudiantes a la carrera.
6. Muestra promedios, lista y busca a Ana.
7. Prueba también `Universidad`.

Conceptos:

- Encapsulamiento.
- Relaciones entre clases.
- `ArrayList`.
- Composición: una carrera tiene estudiantes; un estudiante tiene materias.
- Búsqueda lineal.
- `toString`.

### Ejercicio 2: `Persona` y `Alumno`

Archivos:

- `TP1/src/Persona.java`
- `TP1/src/Alumno.java`
- `TP1/src/MainEjercicio2.java`

`Persona` tiene nombre y DNI, con validaciones.

`Alumno extends Persona` agrega legajo y promedio.

```java
public Alumno(String nombre, String dni, int legajo, double promedio) {
    super(nombre, dni); // inicializa los atributos heredados
    setLegajo(legajo);
    setPromedio(promedio);
}
```

`super(nombre, dni)` llama al constructor de `Persona`. Sin esa línea, Java intentaría llamar al constructor vacío de `Persona`, que no existe, y el código no compilaría.

`Alumno` sobrescribe `mostrarDatos`:

```java
@Override
public void mostrarDatos() {
    super.mostrarDatos();
    System.out.println("Legajo: " + legajo);
    System.out.println("Promedio: " + promedio);
}
```

Primero muestra lo de `Persona` y después agrega lo propio.

En `MainEjercicio2` se crea un `Alumno` y se guarda en una variable `Persona`:

```java
Persona persona = alumno1;
persona.mostrarDatos();
```

Aunque la variable sea `Persona`, el objeto real es `Alumno`, así que se ejecuta la versión sobrescrita. Eso es polimorfismo.

### Ejercicio 3: `Empleado`, `EmpleadoPlanta`, `EmpleadoContratado`

Archivos:

- `TP1/src/Empleado.java`
- `TP1/src/EmpleadoPlanta.java`
- `TP1/src/EmpleadoContratado.java`
- `TP1/src/MainEjercicio3.java`

`Empleado` es abstracta:

```java
public abstract class Empleado {
    private String nombre;
    private int legajo;

    public abstract double calcularSueldo();

    public void mostrarRecibo() {
        System.out.println("Legajo: " + legajo);
        System.out.println("Empleado: " + nombre);
        System.out.println("Sueldo: $" + calcularSueldo());
    }
}
```

No existe un “empleado genérico” con una fórmula única. Por eso `calcularSueldo` es abstracto.

`EmpleadoPlanta` calcula:

```java
sueldoBase + (sueldoBase * 0.02 * antiguedadAnios)
```

`EmpleadoContratado` calcula:

```java
horasTrabajadas * valorHora
```

`mostrarRecibo` está en la clase base y llama a `calcularSueldo()`. Java ejecuta la versión de la subclase. Eso es polimorfismo dentro de un método heredado.

### Ejercicio 4: arreglo de `Empleado`

`MainEjercicio4` crea un `Empleado[]` con dos de planta y dos contratados. Recorre el arreglo y llama a `calcularSueldo()` sin preguntar el tipo:

```java
for (int i = 0; i < empleados.length; i++) {
    System.out.println(empleados[i].getNombre()
            + " -> $" + empleados[i].calcularSueldo());
}
```

Java mira el objeto real y ejecuta el método correspondiente. No hace falta `instanceof` ni `switch`.

### Ejercicio 5: alquiler de vehículos

Archivos:

- `TP1/src/Vehiculo.java`
- `TP1/src/Auto.java`
- `TP1/src/AutoElectrico.java`
- `TP1/src/Motocicleta.java`
- `TP1/src/Camioneta.java`
- `TP1/src/MainEjercicio5.java`

`Vehiculo` es abstracta y guarda patente, marca y modelo. Declara:

```java
public abstract double calcularAlquiler(int dias);
```

Cada subclase calcula distinto:

- `Auto`: `dias * precioPorDia`.
- `AutoElectrico`: `dias * precioPorDia + cargoCarga`.
- `Motocicleta`: `dias * precioPorDia + cargoCasco`.
- `Camioneta`: `dias * precioPorDia + recargoPorTamano`.

`MainEjercicio5` pide los días, recorre un `Vehiculo[]` y muestra el alquiler. No pregunta el tipo. Si mañana se agrega otro vehículo, solo se crea la subclase y se agrega al arreglo; el `for` no cambia.

---

## 4. Entregable 1: CityLink con arreglos

Ruta: `Proyecto/Entregable1/`

### 4.1 Paquetes

```
src/
├── app/
│   ├── CityLink.java   -> gestor: datos y operaciones
│   └── Main.java       -> menú: lee y muestra
├── excepciones/        -> errores propios
└── modelo/             -> clases del negocio
```

La separación es importante:

- `modelo` no sabe nada del menú.
- `Main` no guarda datos; solo lee, llama al gestor y muestra.
- `CityLink` coordina todo.
- `excepciones` contiene los errores del dominio.

### 4.2 `EstadoPedido`

```java
public enum EstadoPedido {
    PENDIENTE,
    EN_CAMINO,
    ENTREGADO,
    CANCELADO
}
```

Es un enum: solo puede tomar uno de esos cuatro valores. Evita usar textos sueltos como `"pendiente"` y permite comparar con `==`.

### 4.3 Interfaces

```java
public interface Rastreable {
    String obtenerEstadoActual();
}
```

```java
public interface Facturable {
    double calcularImporte();
}
```

- `Rastreable`: lo implementan `Pedido` y `Repartidor`.
- `Facturable`: lo implementan `Pedido` y `Repartidor`.

Una clase puede implementar varias interfaces. Eso permite tratar un `Pedido` como `Rastreable` o como `Facturable` según lo que necesite el método.

### 4.4 `Cliente`

Atributos privados: código, nombre, teléfono y zona.

- Constructor vacío: deja todo en valores neutros.
- Constructor completo: usa los setters para validar.
- Setters: validan que el código sea positivo y que los textos no estén vacíos. Usan `trim()` para sacar espacios de los extremos.
- `toString`: muestra los datos en una línea.

No tiene un método `equals`, así que dos clientes con los mismos datos son objetos distintos. En este proyecto se buscan por código, no por igualdad de objeto.

### 4.5 `Vehiculo` y sus subclases

`Vehiculo` es abstracta. Atributos:

```java
private String patente;
private double capacidadKg;
private double velocidadKmH;
private double costoPorKm;
```

El constructor llama a los setters para validar. Todos los setters controlan que los valores sean positivos.

`puedeTransportar` es la regla general:

```java
public boolean puedeTransportar(Pedido pedido) {
    if (pedido == null) {
        return false;
    }
    return pedido.getPesoKg() <= capacidadKg;
}
```

`calcularTiempoEntrega` y `getTipoVehiculo` son abstractos.

Subclases:

#### `Bicicleta`

```java
@Override
public double calcularTiempoEntrega(double km) {
    return (km / getVelocidadKmH()) * 60;
}
```

No suma tiempo extra. Además, sobrescribe `puedeTransportar`:

```java
@Override
public boolean puedeTransportar(Pedido pedido) {
    if (pedido instanceof PedidoFragil) {
        return false;
    }
    return super.puedeTransportar(pedido);
}
```

Una bicicleta no puede llevar pedidos frágiles. Después aplica la regla general de capacidad con `super`.

#### `Moto`

```java
return ((km / getVelocidadKmH()) * 60) + 5;
```

Suma 5 minutos por estacionamiento.

#### `Camioneta`

```java
return ((km / getVelocidadKmH()) * 60) + 10;
```

Suma 10 minutos por carga y descarga.

### 4.6 `Pedido` y sus subclases

`Pedido` es abstracta e implementa `Rastreable` y `Facturable`.

Atributos:

```java
private int codigo;
private Cliente cliente;
private String destino;
private double pesoKg;
private double distanciaKm;
private LocalDateTime fechaHora;
private EstadoPedido estado;
private Vehiculo vehiculoAsignado;
```

#### Constructores

El constructor de cinco parámetros usa `this(...)` para llamar al constructor completo con la fecha actual:

```java
public Pedido(int codigo, Cliente cliente, String destino,
              double pesoKg, double distanciaKm) throws PedidoInvalidoException {
    this(codigo, cliente, destino, pesoKg, distanciaKm, LocalDateTime.now());
}
```

El constructor completo valida:

- código mayor que cero,
- cliente no nulo,
- fecha no nula,
- destino no vacío (en `setDestino`),
- peso mayor que cero (en `setPesoKg`),
- distancia mayor que cero (en `setDistanciaKm`).

Después asigna `estado = PENDIENTE` y `vehiculoAsignado = null`.

#### Setters

`setDestino`, `setPesoKg` y `setDistanciaKm` lanzan `PedidoInvalidoException` si el dato no sirve. Son checked, por eso el constructor y los métodos que los llaman declaran `throws`.

No hay `setCodigo` ni `setEstado` públicos. El código no cambia y el estado solo cambia con `cambiarEstado`, que valida las transiciones.

#### `cambiarEstado`

Permite:

- `PENDIENTE -> EN_CAMINO`
- `PENDIENTE -> CANCELADO`
- `EN_CAMINO -> ENTREGADO`

Cualquier otra transición lanza `EstadoInvalidoException`. Esto evita, por ejemplo, pasar de `ENTREGADO` a `PENDIENTE` sin control.

#### `calcularCosto`

Es abstracto. Cada subclase define la fórmula:

- `PedidoNormal`: `1000 + distancia * costoPorKm`.
- `PedidoUrgente`: `(1000 + distancia * costoPorKm) * 1.5`.
- `PedidoFragil`: `1000 + distancia * costoPorKm + 800`.

#### `calcularImporte`

Implementa `Facturable`:

```java
@Override
public double calcularImporte() {
    if (vehiculoAsignado == null) {
        throw new IllegalStateException(
                "El pedido " + codigo + " todavía no tiene un vehículo asignado.");
    }
    return calcularCosto(vehiculoAsignado);
}
```

Si no tiene vehículo, no se puede calcular el costo. Es `IllegalStateException` porque el objeto no está en condiciones de responder.

#### `obtenerEstadoActual`

Devuelve `estado.name()`, por ejemplo `"EN_CAMINO"`.

#### `toString`

Usa `DateTimeFormatter` para mostrar la fecha como `dd/MM/yyyy HH:mm`. Si no hay vehículo, muestra `"sin asignar"`.

### 4.7 `Repartidor`

Atributos:

```java
private int legajo;
private String nombre;
private Vehiculo vehiculo;
private Pedido[] pedidos;
private int cantidadPedidos;
```

`CAPACIDAD_PEDIDOS` es una constante de clase:

```java
public static final int CAPACIDAD_PEDIDOS = 20;
```

El arreglo se crea con ese tamaño. `cantidadPedidos` indica cuántos espacios están ocupados.

#### `getPedidos`

Devuelve una copia sin los espacios vacíos:

```java
public Pedido[] getPedidos() {
    Pedido[] copia = new Pedido[cantidadPedidos];
    for (int i = 0; i < cantidadPedidos; i++) {
        copia[i] = pedidos[i];
    }
    return copia;
}
```

Así el que llama no puede modificar el arreglo interno.

#### `asignarPedido` (sobrecarga)

Hay dos versiones:

```java
public void asignarPedido(Pedido pedido)
        throws AlmacenamientoLlenoException, CapacidadExcedidaException {
    asignarPedido(pedido, vehiculo);
}

public void asignarPedido(Pedido pedido, Vehiculo vehiculo)
        throws AlmacenamientoLlenoException, CapacidadExcedidaException {
    ...
}
```

La primera usa el vehículo propio del repartidor. La segunda permite indicar otro.

La versión completa:

1. Valida que pedido y vehículo no sean `null`.
2. Si el arreglo está lleno, lanza `AlmacenamientoLlenoException`.
3. Recorre los pedidos para ver si el código ya está asignado. Si está, lanza `IllegalArgumentException`.
4. Pregunta al vehículo si puede transportarlo. Si no, lanza `CapacidadExcedidaException`. Si es bicicleta y el pedido es frágil, da un mensaje específico.
5. Guarda el pedido, incrementa el contador y llama a `pedido.asignarVehiculo(vehiculo)`.

#### `registrarEntrega`

Busca el pedido en el arreglo. Si no está, lanza `IllegalArgumentException`. Si está, llama a `cambiarEstado(ENTREGADO)`. La validación de que esté `EN_CAMINO` la hace `Pedido.cambiarEstado`.

#### `buscarPedido`

Búsqueda lineal por código. Devuelve el pedido o `null`.

#### `calcularImporte`

Suma el importe de los pedidos entregados y devuelve el 10%:

```java
return totalEntregado * 0.10;
```

Solo cuenta los `ENTREGADO`, no los pendientes ni los que están en camino.

#### `obtenerEstadoActual`

Recorre los pedidos y decide:

- si hay alguno `EN_CAMINO`: `"OCUPADO (tiene pedidos en camino)"`;
- si hay alguno `PENDIENTE`: `"CON PEDIDOS ASIGNADOS"`;
- si no: `"DISPONIBLE"`.

#### `toString`

Muestra legajo, nombre, tipo y patente del vehículo, y cantidad de pedidos.

### 4.8 Excepciones del E1

| Archivo | Hereda de | Tipo | Uso |
| --- | --- | --- | --- |
| `PedidoInvalidoException` | `Exception` | checked | datos inválidos o código repetido |
| `ClienteNoEncontradoException` | `Exception` | checked | búsqueda de cliente sin resultado |
| `AlmacenamientoLlenoException` | `Exception` | checked | arreglo sin lugar |
| `CapacidadExcedidaException` | `Exception` | checked | vehículo que no puede llevar el pedido |
| `EstadoInvalidoException` | `RuntimeException` | unchecked | transición de estado inválida |

Todas tienen un constructor que recibe un mensaje y llama a `super(mensaje)`.

### 4.9 `CityLink`

Es el gestor. Guarda cuatro arreglos y cuatro contadores:

```java
private Cliente[] clientes;
private int cantidadClientes;

private Pedido[] pedidos;
private int cantidadPedidos;

private Repartidor[] repartidores;
private int cantidadRepartidores;

private Vehiculo[] vehiculos;
private int cantidadVehiculos;
```

Capacidades:

```java
public static final int CAPACIDAD_CLIENTES = 50;
public static final int CAPACIDAD_PEDIDOS = 100;
public static final int CAPACIDAD_REPARTIDORES = 10;
public static final int CAPACIDAD_VEHICULOS = 30;
```

El constructor crea los arreglos, pone los contadores en cero y llama a `cargarDatosDePrueba()`.

#### `registrarCliente`

Valida que no sea `null`, que haya lugar y que el código no esté repetido. Después guarda y avanza el contador.

#### `buscarCliente(int)` y `buscarCliente(String)`

Dos versiones sobrecargadas:

- por código: recorre y compara con `==` porque el código es `int`;
- por nombre: recorre y compara con `equalsIgnoreCase`.

Si no encuentran, lanzan `ClienteNoEncontradoException`. No devuelven `null`; eso obliga a quien llama a manejar el error.

#### `eliminarCliente`

1. Busca la posición del cliente.
2. Si no existe, lanza `ClienteNoEncontradoException`.
3. Recorre los pedidos: si algún pedido es de ese cliente, no permite eliminarlo.
4. Desplaza los clientes siguientes una posición a la izquierda.
5. Pone `null` en el último lugar y decrementa el contador.

El desplazamiento es la forma de eliminar en un arreglo sin dejar un hueco.

#### `registrarRepartidor`

Valida que no sea `null`, que haya lugar y que el legajo no esté repetido. Guarda el repartidor y avanza el contador.

Después busca el vehículo del repartidor. Si no está en el arreglo de vehículos, lo agrega. Así el arreglo `vehiculos` sirve para mostrar el polimorfismo de tiempos.

#### `registrarPedido`

Valida que no sea `null`, que haya lugar y que el código no esté repetido. Después llama a `buscarCliente` con el código del cliente; si no existe, la excepción sube. Finalmente guarda el pedido.

#### `asignarPedido`

Busca el pedido y el repartidor. Si el repartidor no existe, lanza `IllegalArgumentException`. Si el pedido no está `PENDIENTE`, también. Después llama a `repartidor.asignarPedido(pedido)`, que hace las validaciones de capacidad y duplicado.

#### `cambiarEstadoPedido`

Si el nuevo estado es `ENTREGADO`, busca el repartidor que tiene el pedido y llama a `registrarEntrega`. Así el pedido queda entregado y el repartidor lo cuenta para la comisión. Si no, llama directamente a `pedido.cambiarEstado(nuevoEstado)`.

#### `listarPedidosConCostos`

Recorre los pedidos y muestra `pedido.toString()`. Si tiene vehículo, calcula costo y tiempo:

```java
Facturable facturable = pedido;
double costo = facturable.calcularImporte();
double tiempo = pedido.getVehiculoAsignado()
        .calcularTiempoEntrega(pedido.getDistanciaKm());
```

La variable `Facturable` demuestra que se puede tratar el pedido por su interfaz. El costo se calcula con polimorfismo: `PedidoNormal`, `PedidoUrgente` y `PedidoFragil` responden distinto.

Al final muestra el total y llama a `listarTiemposDeEntrega(10)`.

#### `calcularCostoTotal`

Suma solo los pedidos que tienen vehículo asignado. Usa `Facturable` para no depender del tipo concreto.

#### `listarTiemposDeEntrega`

Recorre `Vehiculo[]` y llama a `calcularTiempoEntrega(km)`. Cada vehículo responde con su fórmula. No hay `if` por tipo.

#### `consultarEstadoPedido` y `consultarEstadoRepartidor`

Guardan el objeto en una variable `Rastreable` y llaman a `obtenerEstadoActual()`. Es otra demostración de interfaz.

#### `listarImportesYComisiones`

Muestra el total de pedidos y recorre los repartidores como `Facturable` para mostrar la comisión de cada uno.

#### `cargarDatosDePrueba`

Carga:

- 5 clientes,
- 3 vehículos (bicicleta, moto, camioneta),
- 3 repartidores,
- 8 pedidos.

Asigna los 8 pedidos y deja:

- pedido 1: `ENTREGADO` (bicicleta),
- pedido 2: `ENTREGADO` (moto),
- pedido 3: `EN_CAMINO` (moto),
- el resto: `PENDIENTE`.

Todo está dentro de un `try/catch` para que, si algo falla, el sistema avise y no se corte.

### 4.10 Cuentas de los datos precargados

Costos:

| Pedido | Tipo | Vehículo | Cálculo | Costo |
| --- | --- | --- | --- | --- |
| 1 | Normal | Bicicleta | 1000 + 5 × 50 | $1250 |
| 2 | Urgente | Moto | (1000 + 8 × 120) × 1.5 | $2940 |
| 3 | Frágil | Moto | 1000 + 3 × 120 + 800 | $2160 |
| 4 | Normal | Camioneta | 1000 + 12 × 300 | $4600 |
| 5 | Urgente | Moto | (1000 + 6 × 120) × 1.5 | $2580 |
| 6 | Frágil | Camioneta | 1000 + 7 × 300 + 800 | $3900 |
| 7 | Normal | Camioneta | 1000 + 10 × 300 | $4000 |
| 8 | Urgente | Bicicleta | (1000 + 4 × 50) × 1.5 | $1800 |

Total: **$23230.00**.

Entregados: pedido 1 ($1250) y pedido 2 ($2940). Total entregado: $4190.

Comisiones (10%):

- Luis (bicicleta, pedido 1): **$125**.
- María (moto, pedidos 2 y 3; solo el 2 está entregado): **$294**.
- Jorge (camioneta, sin entregas): **$0**.

### 4.11 `Main`

El `main`:

1. Crea `CityLink sistema = new CityLink()`.
2. Tiene una variable `salir`.
3. Muestra el menú dentro de un `while (!salir)`.
4. Lee la opción con `leerEntero`.
5. Ejecuta un `switch`.
6. Captura cualquier `Exception` con un `try/catch` interno para no cerrar el programa.
7. Al salir, el `finally` muestra el mensaje y cierra el `Scanner`.

El `catch (Exception e)` es amplio: atrapa checked y unchecked. Muestra `e.getMessage()` y sigue.

Los métodos auxiliares:

- `registrarCliente`: pide datos y crea `Cliente`.
- `registrarRepartidor`: pide tipo de vehículo y crea `Bicicleta`, `Moto` o `Camioneta`.
- `registrarPedido`: pide datos, busca el cliente y crea el pedido según el tipo.
- `asignarPedido`: pide código y legajo.
- `cambiarEstado`: pide el nuevo estado y llama al gestor.
- `buscarCliente`: elige búsqueda por código o nombre.
- `consultarEstado`: elige pedido o repartidor.

`leerEntero` y `leerDouble` usan un `while (true)` con `try/catch`:

```java
while (true) {
    System.out.print(mensaje);
    String linea = teclado.nextLine();
    try {
        return Integer.parseInt(linea.trim());
    } catch (NumberFormatException e) {
        System.out.println("Eso no es un número entero. Probá de nuevo.");
    }
}
```

Si el usuario escribe mal, el `catch` avisa y el `while` vuelve a pedir. `leerDouble` acepta coma o punto:

```java
Double.parseDouble(linea.trim().replace(',', '.'))
```

### 4.12 Decisiones de diseño del E1

- **Arreglos y contadores**: porque el enunciado pedía arreglos. El contador evita recorrer lugares vacíos.
- **Validar en los setters**: así cualquier creación o modificación pasa por las mismas reglas.
- **Excepciones propias**: para que el error tenga nombre y el menú muestre un mensaje claro.
- **`Main` separado de `CityLink`**: el menú no tiene lógica de negocio; el gestor no lee por teclado.
- **Polimorfismo para costos y tiempos**: evita `if` por tipo en los listados.
- **`instanceof` solo para la regla de bicicleta y frágil**: es una excepción a la regla general de capacidad.

---

## 5. Entregable 2: CityLink con colecciones y algoritmos

Ruta: `Proyecto/Entregable2/`

### 5.1 Qué cambia

Se mantiene el modelo de objetos y las interfaces. Cambian las estructuras de datos:

| E1 | E2 |
| --- | --- |
| `Cliente[]` + contador | `TreeMap<Integer, Cliente>` |
| `Pedido[]` + contador | `ArrayList<Pedido>` |
| `Repartidor[]` + contador | `ArrayList<Repartidor>` |
| `Vehiculo[]` + contador | `ArrayList<Vehiculo>` |
| Sin colas | `ArrayDeque<Pedido>` para urgentes y normales |
| Sin pila | `ArrayDeque<Operacion>` para deshacer |
| Sin turnos | `ArrayList<Pedido>` para mañana y tarde |

También se agrega el paquete `algoritmos` y la clase `Operacion`.

### 5.2 `Pedido` en E2

Es casi igual al E1, pero agrega:

```java
public void restaurarEstado(EstadoPedido estado) {
    if (estado == null) {
    throw new IllegalArgumentException("El estado no puede ser nulo.");
    }
    this.estado = estado;
}
```

`restaurarEstado` no valida transiciones. Se usa solamente en “Deshacer”, porque ahí queremos volver al estado anterior aunque la transición normal no esté permitida.

### 5.3 `Repartidor` en E2

Cambia el arreglo por listas:

```java
private List<Pedido> pedidos;
private List<Pedido> historial;
```

- `pedidos`: pedidos a cargo.
- `historial`: pedidos entregados.

`getPedidos` y `getHistorial` devuelven copias:

```java
return new ArrayList<Pedido>(pedidos);
```

`asignarPedido` agrega con `pedidos.add(pedido)` y valida duplicados con `buscarPedido`.

`registrarEntrega`:

1. Busca el pedido.
2. Llama a `cambiarEstado(ENTREGADO)`.
3. Si no estaba en el historial, lo agrega con `historial.add`.

`quitarDelHistorial` y `quitarPedido` los usa “Deshacer”.

`calcularImporte` recorre el historial y suma los importes; devuelve el 10%.

`obtenerEstadoActual` recorre `pedidos` y decide igual que en E1.

`toString` muestra cantidad de pedidos y de entregas.

### 5.4 `Operacion`

Guarda los datos de una operación para poder deshacerla.

```java
public enum TipoOperacion {
    ALTA_CLIENTE,
    BAJA_CLIENTE,
    ALTA_REPARTIDOR,
    ALTA_PEDIDO,
    ASIGNAR_PEDIDO,
    ENTREGAR_PEDIDO,
    CAMBIO_ESTADO
}
```

Atributos:

```java
private TipoOperacion tipo;
private Cliente cliente;
private Repartidor repartidor;
private Pedido pedido;
private EstadoPedido estadoAnterior;
```

El constructor es privado. Se crean operaciones con métodos `static`:

```java
public static Operacion altaCliente(Cliente cliente) {
    return new Operacion(TipoOperacion.ALTA_CLIENTE, cliente, null, null, null);
}
```

Así no se puede crear una operación con datos incoherentes desde afuera.

### 5.5 `CityLink` en E2

#### Atributos

```java
private TreeMap<Integer, Cliente> clientes;
private List<Repartidor> repartidores;
private List<Pedido> pedidos;
private List<Vehiculo> vehiculos;

private Deque<Pedido> colaUrgentes;
private Deque<Pedido> colaNormales;

private Deque<Operacion> pilaOperaciones;

private List<Pedido> turnoManana;
private List<Pedido> turnoTarde;
```

#### Clientes con `TreeMap`

- `registrarCliente`: usa `containsKey` para evitar códigos repetidos y `put` para guardar.
- `bajaCliente`: busca, elimina con `remove` y guarda la operación para deshacer.
- `buscarCliente(int)`: usa `get`.
- `buscarCliente(String)`: arma una `ArrayList` con `clientes.values()` y usa `Busqueda.busquedaLinealClientePorNombre`.
- `listarClientesOrdenados`: recorre `clientes.values()` con for-each. El `TreeMap` ya está ordenado por código.

#### Repartidores con `List`

- `registrarRepartidor`: valida duplicado con `buscarRepartidor`, agrega con `add` y agrega el vehículo si no existe.
- `buscarRepartidor`: recorre con `get(i)`.
- `existeVehiculo`: compara patentes con `equalsIgnoreCase`.
- `listarRepartidores`: recorre y muestra.

#### Pedidos y colas

`registrarPedido(pedido, turno)`:

1. Valida que el pedido no sea nulo.
2. Valida que el turno sea 1 o 2.
3. Recorre `pedidos` para evitar códigos repetidos.
4. Verifica que el cliente exista.
5. Agrega a `pedidos`.
6. Llama a `encolarPedido`.
7. Agrega a `turnoManana` o `turnoTarde`.
8. Guarda la operación para deshacer.

`encolarPedido`:

```java
if (pedido.getTipo().equals("Urgente")) {
    colaUrgentes.offerLast(pedido);
} else {
    colaNormales.offerLast(pedido);
}
```

Los urgentes van a una cola y los normales a otra. `getTipo()` devuelve `"Urgente"`, `"Normal"` o `"Frágil"`.

`quitarDeCola` usa `remove` en ambas colas.

#### `atenderSiguientePedido`

1. Si las dos colas están vacías, lanza `SinPedidosPendientesException`.
2. Elige la cola de urgentes si no está vacía; si no, la de normales.
3. Mira el primero con `peekFirst`.
4. Busca un repartidor que pueda transportarlo y que tenga menos pedidos.
5. Si no hay ninguno, lanza `CapacidadExcedidaException`.
6. Saca el pedido de la cola con `pollFirst`.
7. Lo asigna al repartidor.
8. Lo pasa a `EN_CAMINO`.
9. Guarda la operación para deshacer.

La comparación de carga usa `getPedidos().size()`. Como `getPedidos` devuelve una copia, no se modifica la lista original.

#### `registrarEntrega`

Busca el pedido y el repartidor. Si el pedido no está asignado, lanza `IllegalArgumentException`. Después llama a `repartidor.registrarEntrega(pedido)`, que valida la transición y agrega al historial. Guarda la operación para deshacer.

#### Búsquedas

- `buscarPedido(codigo)`: usa `Busqueda.busquedaLinealPedido`.
- `buscarPedidoLineal(codigo)`: igual, pero lanza `PedidoInvalidoException` si no lo encuentra.
- `buscarPedidoBinario(codigo)`: arma una copia, la ordena por código con quicksort y usa búsqueda binaria. No cambia el orden original.

#### Ordenamiento

`ordenarYListarPedidos(criterio, algoritmo)`:

1. Obtiene un `Comparator<Pedido>` según el criterio.
2. Ejecuta el algoritmo elegido:

```java
if (algoritmo == 1) {
    Ordenamiento.burbuja(pedidos, comparador);
} else if (algoritmo == 2) {
    Ordenamiento.insercion(pedidos, comparador);
} else if (algoritmo == 3) {
    Ordenamiento.quicksort(pedidos, comparador);
} else if (algoritmo == 4) {
    Ordenamiento.mergesort(pedidos, comparador);
}
```

3. Muestra los pedidos ordenados.

`obtenerComparador` devuelve comparadores anónimos:

- criterio 1: costo (`costoSeguro`);
- criterio 2: distancia;
- criterio 3: fecha (`compareTo`).

`costoSeguro` devuelve 0 si el pedido no tiene vehículo, para no lanzar excepción al ordenar.

#### Intercalación

`intercalarTurnos(criterio)`:

1. Obtiene el comparador.
2. Copia `turnoManana` y `turnoTarde`.
3. Ordena cada copia con quicksort.
4. Llama a `Intercalacion.intercalar`.
5. Muestra el resultado.

#### Deshacer

`deshacer()` saca la última operación de la pila con `pollFirst`. Si no hay, lanza `IllegalStateException`.

Después usa un `switch` sobre `operacion.getTipo()`:

- `ALTA_CLIENTE`: `clientes.remove(codigo)`.
- `BAJA_CLIENTE`: `clientes.put(codigo, cliente)`.
- `ALTA_REPARTIDOR`: `repartidores.remove(repartidor)`.
- `ALTA_PEDIDO`: saca el pedido de `pedidos`, de las colas y de los turnos. Si tenía vehículo, lo saca del repartidor y del historial.
- `ASIGNAR_PEDIDO`: saca el pedido del repartidor, quita el vehículo, vuelve a `PENDIENTE` y lo encola de nuevo.
- `ENTREGAR_PEDIDO`: saca del historial y vuelve a `EN_CAMINO`.
- `CAMBIO_ESTADO`: restaura el estado anterior.

En el menú actual, la operación `CAMBIO_ESTADO` no se genera (no hay una opción separada de cambiar estado), pero el caso está implementado por completitud.

#### Listados y totales

`listarPedidosConCostos`, `mostrarPedido`, `calcularCostoTotal`, `listarTiemposDeEntrega`, `consultarEstadoPedido`, `consultarEstadoRepartidor`, `listarImportesYComisiones` y `listarPedidosDeRepartidor` son las versiones con listas de los métodos del E1.

`mostrarPedido` usa `Facturable` para calcular el costo y el vehículo para el tiempo. Si no hay vehículo, muestra “sin calcular”.

`listarPedidosDeRepartidor` muestra los pedidos a cargo y el historial.

### 5.6 Algoritmos

#### Búsqueda lineal

```java
public static Pedido busquedaLinealPedido(List<Pedido> pedidos, int codigo) {
    for (int i = 0; i < pedidos.size(); i++) {
        if (pedidos.get(i).getCodigo() == codigo) {
            return pedidos.get(i);
        }
    }
    return null;
}
```

Recorre toda la lista hasta encontrar. En el peor caso mira todos los elementos: **O(n)**.

#### Búsqueda binaria

```java
int inicio = 0;
int fin = pedidosOrdenados.size() - 1;

while (inicio <= fin) {
    int medio = (inicio + fin) / 2;
    int codigoMedio = pedidosOrdenados.get(medio).getCodigo();

    if (codigoMedio == codigo) {
        return pedidosOrdenados.get(medio);
    } else if (codigoMedio < codigo) {
        inicio = medio + 1;
    } else {
        fin = medio - 1;
    }
}
return null;
```

La lista tiene que estar ordenada por código. En cada vuelta descarta la mitad: **O(log n)**.

La versión recursiva hace lo mismo, pero se llama a sí misma con el rango que queda:

```java
if (codigoMedio < codigo) {
    return buscarRecursivo(pedidos, codigo, medio + 1, fin);
} else {
    return buscarRecursivo(pedidos, codigo, inicio, medio - 1);
}
```

#### Burbuja

```java
for (int i = 0; i < lista.size() - 1; i++) {
    for (int j = 0; j < lista.size() - 1 - i; j++) {
        if (comparador.compare(lista.get(j), lista.get(j + 1)) > 0) {
            intercambiar(lista, j, j + 1);
        }
    }
}
```

Compara pares vecinos y los intercambia si están desordenados. En cada vuelta, el mayor “burbujea” al final. **O(n²)**.

#### Inserción

```java
for (int i = 1; i < lista.size(); i++) {
    Pedido actual = lista.get(i);
    int j = i - 1;

    while (j >= 0 && comparador.compare(lista.get(j), actual) > 0) {
        lista.set(j + 1, lista.get(j));
        j--;
    }
    lista.set(j + 1, actual);
}
```

Toma cada elemento y lo inserta en el lugar que le corresponde dentro de la parte ya ordenada. **O(n²)** en el peor caso, pero anda bien si la lista ya está casi ordenada.

#### Quicksort

```java
private static int particionar(List<Pedido> lista, int izquierda, int derecha,
                               Comparator<Pedido> comparador) {
    Pedido pivote = lista.get(derecha);
    int i = izquierda - 1;

    for (int j = izquierda; j < derecha; j++) {
        if (comparador.compare(lista.get(j), pivote) <= 0) {
            i++;
            intercambiar(lista, i, j);
        }
    }

    intercambiar(lista, i + 1, derecha);
    return i + 1;
}
```

Elige el último como pivote, pone a la izquierda los menores o iguales y a la derecha los mayores, y después ordena cada lado recursivamente. Promedio **O(n log n)**, peor caso **O(n²)**.

#### Mergesort

```java
if (lista.size() <= 1) {
    return;
}

int medio = lista.size() / 2;
List<Pedido> izquierda = new ArrayList<Pedido>();
List<Pedido> derecha = new ArrayList<Pedido>();
...
mergesort(izquierda, comparador);
mergesort(derecha, comparador);
mezclar(lista, izquierda, derecha, comparador);
```

Divide la lista en dos, ordena cada mitad y las mezcla. Siempre **O(n log n)**, pero usa listas auxiliares.

#### Intercalación

```java
while (i < listaA.size() && j < listaB.size()) {
    if (comparador.compare(listaA.get(i), listaB.get(j)) <= 0) {
        resultado.add(listaA.get(i));
        i++;
    } else {
        resultado.add(listaB.get(j));
        j++;
    }
}
```

Recibe dos listas ya ordenadas y las combina en una sola. Recorre cada una una vez: **O(n + m)**.

### 5.7 Datos precargados del E2

- 5 clientes.
- 3 repartidores.
- 8 pedidos.
- Pedidos 1 y 2: `ENTREGADO`.
- Pedido 3: `EN_CAMINO`.
- Pedidos 4 a 8: `PENDIENTE`.
- El pedido urgente 5 es el próximo en la cola de urgentes (después de la precarga, la cola urgente tiene los pedidos 5 y 8).

Comisiones precargadas:

- Luis: pedido 1 ($1250) → **$125**.
- María: pedido 2 ($2940) → **$294**.
- Jorge: sin entregas → **$0**.

El total de pedidos asignados es $1250 + $2940 + $2160 = **$6350** (incluye el pedido 3, que está en camino).

### 5.8 Pruebas sugeridas

1. **Alta de cliente**: código nuevo, nombre, teléfono y zona. Debe aparecer en el listado ordenado.
2. **Código repetido**: intentar dar de alta un cliente con código 1. Debe mostrar error y seguir.
3. **Baja de cliente**: dar de baja un cliente y después deshacer. Debe volver.
4. **Alta de pedido**: con cliente existente. Debe encolarse.
5. **Cliente inexistente**: registrar pedido con código 99. Debe mostrar error.
6. **Atender siguiente**: primero debe salir un urgente; si no hay, uno normal.
7. **Entrega**: registrar entrega de un pedido `EN_CAMINO`; después ver comisiones.
8. **Entrega inválida**: entregar un pedido `PENDIENTE`; debe fallar.
9. **Deshacer**: probar cada operación y verificar que vuelve al estado anterior.
10. **Ordenar**: probar los tres criterios y los cuatro algoritmos.
11. **Buscar**: comparar búsqueda lineal y binaria con un código existente y uno inexistente.
12. **Intercalar**: cargar pedidos en los dos turnos y ver el resultado ordenado.
13. **Colas vacías**: atender cuando no hay pedidos; debe mostrar `SinPedidosPendientesException`.
14. **Entrada inválida**: escribir letras donde se pide un número; debe volver a pedir.

### 5.9 Decisiones de diseño del E2

- **`TreeMap` para clientes**: porque el código es la clave y queremos listarlos ordenados sin ordenar a mano.
- **`ArrayList` para pedidos y repartidores**: porque la cantidad no se sabe de antemano.
- **Dos colas**: para priorizar urgentes sin recorrer toda la lista.
- **`ArrayDeque` para pila y cola**: es eficiente y ya viene en Java.
- **`Operacion` con factory methods**: para guardar exactamente los datos que necesita cada deshacer.
- **Algoritmos a mano**: porque el enunciado prohíbe `Collections.sort` y `Arrays.binarySearch`.
- **`Comparator`**: para no escribir cuatro versiones de cada algoritmo.

---

## 6. Cómo se conecta todo

Un caso de uso completo, “registrar un pedido”:

```
Main.registrarPedido()
  -> pide datos por teclado
  -> busca el cliente: sistema.buscarCliente(codigoCliente)
  -> crea PedidoNormal / PedidoUrgente / PedidoFragil
  -> llama a sistema.registrarPedido(pedido, turno)
       -> valida código repetido
       -> valida cliente
       -> pedidos.add(pedido)
       -> encolarPedido(pedido)
       -> turnoManana / turnoTarde
       -> pilaOperaciones.push(Operacion.altaPedido(pedido))
```

Otro caso, “atender el siguiente pedido”:

```
Main
  -> sistema.atenderSiguientePedido()
       -> elige colaUrgentes o colaNormales
       -> peekFirst()
       -> busca repartidor con puedeTransportar y menos carga
       -> pollFirst()
       -> repartidor.asignarPedido(pedido)
       -> pedido.cambiarEstado(EN_CAMINO)
       -> pilaOperaciones.push(Operacion.asignarPedido(...))
```

Así se ve la arquitectura:

```
Main  ->  CityLink  ->  modelo (Cliente, Pedido, Vehiculo, Repartidor)
                  \->  excepciones
                  \->  algoritmos (E2)
```

`Main` no conoce los arreglos ni las colas. `CityLink` no conoce `Scanner`. `modelo` no conoce ni el menú ni el gestor. Cada capa tiene una responsabilidad.

---

## 7. Qué se podría mejorar (para la pregunta “¿qué cambiarías?”)

Estas son mejoras reales, no errores graves. Sirven para responder si en la exposición preguntan por el diseño:

- En E1, si se llena el arreglo de vehículos después de agregar el repartidor, el repartidor queda agregado y el vehículo no. Se podría validar antes.
- En E2, `bajaCliente` no controla si el cliente tiene pedidos. Se podría agregar la misma validación que en E1.
- `CAMBIO_ESTADO` está implementado en “Deshacer” pero el menú no genera esa operación. Se podría agregar una opción de cambiar estado o quitar el caso.
- Se podrían agregar pruebas automáticas con JUnit.
- Se podría separar la precarga en una clase aparte (`DatosDePrueba`) para que `CityLink` no tenga tantas líneas.
- Se podría usar `LocalDate`/`LocalDateTime` con zona horaria si el sistema creciera.
- Se podría persistir en archivos o base de datos, pero el enunciado no lo pide.

Nada de esto es obligatorio. Si preguntan, lo importante es mostrar que entendés las limitaciones y que sabés por qué están.

---

## 8. Cómo estudiar cada clase

Para cada archivo, hacé esta ficha:

1. **Nombre y paquete**.
2. **Qué representa**.
3. **Atributos** (tipo y para qué).
4. **Constructores** (qué reciben y qué validan).
5. **Métodos** (qué reciben, qué devuelven, qué modifican).
6. **Herencia / interfaces**.
7. **Excepciones**.
8. **Dónde se usa**.

Si podés completar esa ficha sin mirar, estás listo para explicar esa clase.
