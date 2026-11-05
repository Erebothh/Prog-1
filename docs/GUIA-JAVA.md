# Java desde cero (usando el código de CityLink)

Esta guía es para estudiar y para la exposición oral. No hace falta memorizarla: la idea es que puedas mirar cualquier clase del proyecto y explicar qué hace, por qué está escrita así y qué conceptos de Java aparecen.

La guía está pensada para alguien que recién empieza. Si algo ya lo sabés, podés saltearlo y volver cuando lo necesites.

---

## 1. Qué es Java y cómo se ejecuta

Java es un lenguaje de programación orientado a objetos. Un programa Java se escribe en archivos de texto con extensión `.java`. Después se compila y se ejecuta.

El recorrido es este:

```
Main.java  --javac-->  Main.class  --java-->  JVM
(código)               (bytecode)             (máquina virtual)
```

- **JDK**: es el kit para desarrollar. Incluye el compilador `javac` y la máquina virtual.
- **JRE**: es lo necesario para ejecutar un programa Java.
- **JVM**: es la máquina virtual que ejecuta el bytecode. Gracias a esto, el mismo `.class` puede correr en Windows, Linux o Mac.
- **`javac`**: compila el código fuente y genera archivos `.class`.
- **`java`**: arranca la JVM y ejecuta una clase que tenga el método `main`.

En el proyecto compilamos así:

```bash
javac -encoding UTF-8 -d out $(find src -name "*.java")
java -cp out app.Main
```

- `-encoding UTF-8` le dice a `javac` que los archivos tienen tildes y ñ.
- `-d out` guarda los `.class` en la carpeta `out`.
- `-cp out` le dice a `java` dónde buscar las clases compiladas.
- `app.Main` es el nombre completo de la clase: paquete `app`, clase `Main`.

El método que arranca todo es siempre:

```java
public static void main(String[] args) {
    ...
}
```

- `public`: se puede llamar desde afuera.
- `static`: pertenece a la clase, no a un objeto.
- `void`: no devuelve nada.
- `main`: nombre especial que busca la JVM.
- `String[] args`: arreglo de textos con los argumentos de la línea de comandos. En este proyecto no lo usamos, pero tiene que estar.

---

## 2. La estructura de un archivo Java

Un archivo del proyecto se ve así:

```java
package modelo;

import excepciones.PedidoInvalidoException;

public class Cliente {

    private int codigo;
    private String nombre;

    public Cliente(int codigo, String nombre) {
        setCodigo(codigo);
        setNombre(nombre);
    }

    public int getCodigo() {
        return codigo;
    }
}
```

### `package`

Indica a qué paquete pertenece la clase. El paquete es como una carpeta lógica. En el proyecto:

- `modelo`: las clases del negocio (Cliente, Pedido, Vehículo, etc.).
- `excepciones`: los errores propios del sistema.
- `app`: el gestor y el menú.
- `algoritmos`: búsquedas, ordenamientos e intercalación.

La carpeta física tiene que coincidir con el paquete. Por eso `package modelo;` está en `src/modelo/`.

### `import`

Sirve para usar clases que están en otro paquete o en la biblioteca de Java. Por ejemplo:

```java
import java.util.ArrayList;
import java.util.Scanner;
import modelo.Cliente;
```

No es obligatorio importar las clases que están en el mismo paquete.

### `public class`

Declara una clase. El nombre del archivo tiene que ser igual al nombre de la clase pública. `Cliente` va en `Cliente.java`.

### Llaves `{ }`

Delimitan bloques: el cuerpo de una clase, de un método, de un `if`, de un `for`, etc.

### Punto y coma `;`

Separa instrucciones. Casi todas las instrucciones terminan con `;`.

### Comentarios

```java
// Comentario de una línea

/*
 * Comentario de varias líneas.
 */
```

Los comentarios no se ejecutan. Sirven para explicar el código.

---

## 3. Variables y tipos de datos

Una variable es un espacio con nombre donde guardamos un valor.

```java
int edad = 20;
double promedio = 8.5;
boolean aprobado = true;
String nombre = "Ana";
```

### Tipos primitivos

| Tipo | Guarda | Ejemplo |
| --- | --- | --- |
| `int` | números enteros | `int codigo = 1;` |
| `double` | números con decimales | `double peso = 2.5;` |
| `boolean` | `true` o `false` | `boolean activo = true;` |
| `char` | un carácter | `char letra = 'A';` |
| `long` | enteros grandes | `long numero = 123456789L;` |

En el proyecto usamos mucho `int` (códigos, legajos, cantidades) y `double` (peso, distancia, costo, velocidad).

### Tipos de referencia

No guardan el valor directamente, sino una referencia a un objeto:

```java
String nombre = "Ana";
Cliente cliente = new Cliente(1, "Ana", "555-1111", "Centro");
Pedido[] pedidos = new Pedido[100];
```

- `String` es una clase, no un tipo primitivo.
- `Cliente` es una clase del proyecto.
- `Pedido[]` es un arreglo de pedidos.

Una variable de tipo referencia puede valer `null`, que significa “no apunta a ningún objeto”.

```java
Cliente cliente = null;
```

Si intentás usar un método sobre algo que es `null`, Java lanza `NullPointerException`.

### Declaración, asignación e inicialización

```java
int cantidad;          // declaración
cantidad = 0;          // asignación
int total = 0;         // declaración + inicialización
```

### `final`

Indica que el valor no se puede cambiar después de asignarlo.

```java
public static final int CAPACIDAD_CLIENTES = 50;
```

- `public`: se puede ver desde cualquier lado.
- `static`: pertenece a la clase, no a cada objeto.
- `final`: es una constante.
- `int`: tipo.
- `CAPACIDAD_CLIENTES`: nombre. Por convención, las constantes van en mayúsculas.

### `static`

Un atributo o método `static` pertenece a la clase, no a un objeto concreto. Se llama con el nombre de la clase:

```java
CityLink.CAPACIDAD_CLIENTES
Integer.parseInt("25")
```

En cambio, un método normal necesita un objeto:

```java
cliente.getNombre()
```

### Convenciones de nombres

- Clases: `MayusculaInicial` (`Cliente`, `PedidoUrgente`).
- Métodos y variables: `minusculaInicial` (`calcularCosto`, `cantidadPedidos`).
- Constantes: `MAYUSCULAS_CON_GUIONES` (`CAPACIDAD_PEDIDOS`).
- Paquetes: todo en minúscula (`modelo`, `algoritmos`).

---

## 4. Operadores

### Aritméticos

```java
int suma = 2 + 3;          // 5
int resta = 5 - 2;         // 3
int producto = 4 * 3;      // 12
double division = 10.0 / 4; // 2.5
int resto = 10 % 3;        // 1
```

Cuidado: `10 / 4` con dos `int` da `2`, porque Java descarta la parte decimal. Para obtener `2.5` hay que usar `10.0 / 4`.

### Asignación

```java
total = total + 5;
total += 5;       // equivalente
```

### Comparación

```java
a == b     // igual
a != b     // distinto
a > b      // mayor
a < b      // menor
a >= b     // mayor o igual
a <= b     // menor o igual
```

### Lógicos

```java
&&   // y
||   // o
!    // no
```

Ejemplo del proyecto:

```java
if (estado == EstadoPedido.PENDIENTE
        && (nuevoEstado == EstadoPedido.EN_CAMINO
        || nuevoEstado == EstadoPedido.CANCELADO)) {
    ...
}
```

### Incremento y decremento

```java
i++;   // i = i + 1
i--;   // i = i - 1
```

### Concatenación de texto

El operador `+` también une textos:

```java
System.out.println("Pedido " + codigo + " - " + destino);
```

Si mezclás texto y números, Java convierte los números a texto.

### Operador ternario

```java
String resultado = (edad >= 18) ? "mayor" : "menor";
```

Es un `if` corto. En el proyecto no lo usamos mucho, pero conviene reconocerlo.

### `==` versus `.equals`

- `==` compara referencias en los objetos. En tipos primitivos compara valores.
- `.equals()` compara el contenido de un `String`.

```java
String a = "Ana";
String b = "Ana";
a == b          // puede dar true o false según cómo se creó
a.equals(b)     // true, compara el contenido
```

En el proyecto usamos:

```java
nombre.equalsIgnoreCase(nombreBuscado)
```

`equalsIgnoreCase` compara sin distinguir mayúsculas y minúsculas.

---

## 5. Condicionales y bucles

### `if`, `else if`, `else`

```java
if (promedio >= 9) {
    System.out.println("Excelente");
} else if (promedio >= 6) {
    System.out.println("Aprobado");
} else {
    System.out.println("Libre");
}
```

Java evalúa de arriba hacia abajo. Cuando una condición es verdadera, entra a ese bloque y saltea el resto.

### `switch`

Sirve para elegir entre varios casos según un valor.

```java
switch (opcion) {
    case 1:
        registrarCliente(sistema);
        break;
    case 2:
        registrarRepartidor(sistema);
        break;
    default:
        System.out.println("Opción incorrecta");
}
```

- `case`: cada opción.
- `break`: corta el `switch` y sale. Si falta, sigue ejecutando los casos siguientes (esto se llama “caída” y casi nunca es lo que querés).
- `default`: se ejecuta si ningún caso coincide.

En Java moderno también existe el `switch` con flechas (`case 1 -> ...`), pero en el proyecto usamos el clásico para que se entienda.

### `while`

Repite mientras la condición sea verdadera.

```java
while (j >= 0 && comparador.compare(lista.get(j), actual) > 0) {
    lista.set(j + 1, lista.get(j));
    j--;
}
```

### `do-while`

Se ejecuta al menos una vez y después pregunta. En el proyecto no lo usamos, pero existe:

```java
do {
    ...
} while (condicion);
```

### `for`

Se usa cuando sabemos cuántas veces repetir.

```java
for (int i = 0; i < estudiantes.length; i++) {
    System.out.println(estudiantes[i]);
}
```

Partes:

1. `int i = 0`: inicialización.
2. `i < estudiantes.length`: condición.
3. `i++`: se ejecuta al final de cada vuelta.

### `for-each`

Recorre todos los elementos de una colección o arreglo sin usar índices.

```java
for (Cliente cliente : clientes.values()) {
    System.out.println(cliente);
}
```

Se lee: “para cada `cliente` dentro de `clientes.values()`”. Es más corto, pero no sirve si necesitás el índice o modificar la colección.

### `break` y `continue`

- `break`: corta el bucle o el `switch`.
- `continue`: salta a la siguiente vuelta del bucle.

En el proyecto usamos `break` en los `switch` y en la búsqueda por nombre:

```java
if (estudiantes[i].nombre.equalsIgnoreCase(nombreBuscado)) {
    estudiantes[i].mostrarInformacion();
    encontrado = true;
    break;
}
```

Cuando lo encuentra, no sigue buscando.

### `return`

Devuelve un valor y termina el método.

```java
public int getCodigo() {
    return codigo;
}
```

En un método `void`, `return;` solamente termina el método.

---

## 6. Métodos

Un método es un bloque de código con nombre que hace una tarea.

```java
public double calcularCosto(Vehiculo vehiculo) {
    return 1000 + (getDistanciaKm() * vehiculo.getCostoPorKm());
}
```

Partes:

- `public`: modificador de acceso.
- `double`: tipo de retorno.
- `calcularCosto`: nombre.
- `Vehiculo vehiculo`: parámetro.
- `return ...`: valor que devuelve.
- `{ ... }`: cuerpo.

### `void`

Si no devuelve nada, se usa `void`:

```java
public void mostrarDatos() {
    System.out.println(nombre);
}
```

### Parámetros y argumentos

- **Parámetro**: lo que el método recibe en su definición (`double km`).
- **Argumento**: el valor que le pasás al llamarlo (`calcularTiempoEntrega(10)`).

### Sobrecarga

Dos métodos con el mismo nombre pero distintos parámetros:

```java
public void asignarPedido(Pedido pedido) { ... }

public void asignarPedido(Pedido pedido, Vehiculo vehiculo) { ... }
```

Java elige cuál ejecutar según la cantidad y el tipo de argumentos. Esto es **sobrecarga** y es una forma de polimorfismo en tiempo de compilación.

### `static` versus instancia

```java
public static int sumar(int a, int b) {
    return a + b;
}
```

Se llama `Clase.sumar(2, 3)`, sin crear un objeto.

Un método normal necesita un objeto:

```java
cliente.getNombre();
```

### Recursividad

Un método recursivo se llama a sí mismo con un caso más chico, hasta llegar a un caso base.

```java
private static Pedido buscarRecursivo(List<Pedido> pedidos, int codigo, int inicio, int fin) {
    if (inicio > fin) {
        return null;
    }
    int medio = (inicio + fin) / 2;
    ...
    return buscarRecursivo(pedidos, codigo, medio + 1, fin);
}
```

- **Caso base**: `inicio > fin`, devuelve `null`.
- **Caso recursivo**: vuelve a llamar con un rango más chico.

Sin caso base, la recursión nunca termina y se produce `StackOverflowError`.

---

## 7. Clases y objetos

### Clase

Una clase es un molde. Define qué datos y qué comportamientos va a tener un tipo de objeto.

```java
public class Cliente {
    private int codigo;
    private String nombre;
}
```

### Objeto

Un objeto es una instancia concreta de una clase. Se crea con `new`:

```java
Cliente cliente = new Cliente(1, "Ana", "555-1111", "Centro");
```

- `new`: reserva memoria y llama al constructor.
- `Cliente(...)`: constructor.
- `cliente`: variable que apunta al objeto.

### Atributos o campos

Son las variables dentro de una clase:

```java
private int codigo;
private String nombre;
```

`private` significa que solo la propia clase puede acceder directamente. Desde afuera se usan los getters y setters.

### Constructor

Es un método especial que se ejecuta al crear el objeto. Tiene el mismo nombre que la clase y no tiene tipo de retorno.

```java
public Cliente(int codigo, String nombre, String telefono, String zona) {
    setCodigo(codigo);
    setNombre(nombre);
    setTelefono(telefono);
    setZona(zona);
}
```

Puede haber varios constructores:

```java
public Cliente() {
    this.codigo = 0;
    this.nombre = "";
}

public Cliente(int codigo, String nombre) {
    setCodigo(codigo);
    setNombre(nombre);
}
```

### `this`

`this` hace referencia al objeto actual.

```java
public void setNombre(String nombre) {
    this.nombre = nombre;
}
```

Acá hay dos variables llamadas `nombre`: el parámetro y el atributo. `this.nombre` es el atributo del objeto; `nombre` es el parámetro.

También se usa para llamar a otro constructor:

```java
public Pedido(int codigo, Cliente cliente, String destino,
              double pesoKg, double distanciaKm) throws PedidoInvalidoException {
    this(codigo, cliente, destino, pesoKg, distanciaKm, LocalDateTime.now());
}
```

`this(...)` llama al constructor completo. Tiene que ser la primera instrucción del constructor.

### Encapsulamiento

Consiste en esconder los datos y controlar cómo se modifican.

```java
private double pesoKg;

public double getPesoKg() {
    return pesoKg;
}

public void setPesoKg(double pesoKg) throws PedidoInvalidoException {
    if (pesoKg <= 0) {
        throw new PedidoInvalidoException("El peso tiene que ser mayor que cero.");
    }
    this.pesoKg = pesoKg;
}
```

Ventajas:

- Nadie puede poner un peso negativo directamente.
- Si cambia la regla, se cambia en un solo lugar.
- El objeto mantiene datos coherentes.

### Getters y setters

- `getX()`: devuelve el valor.
- `setX(...)`: lo modifica, generalmente con validación.

No todos los atributos necesitan setter. Por ejemplo, `Pedido` no tiene `setCodigo` porque el código no debería cambiar después de crear el pedido.

### `toString()`

Devuelve una representación en texto del objeto. Se llama automáticamente cuando hacés:

```java
System.out.println(cliente);
```

Si no está definido, Java muestra algo como `modelo.Cliente@1b6d3586`. En el proyecto lo definimos para mostrar datos legibles.

```java
@Override
public String toString() {
    return "Cliente " + codigo + " - " + nombre;
}
```

### `equals()`

Compara objetos por contenido. Si no lo definís, se comporta como `==` (compara referencias). En el proyecto no lo definimos, pero `String` sí lo tiene.

---

## 8. Herencia, abstracción, interfaces y polimorfismo

### Herencia

Una clase puede heredar de otra con `extends`.

```java
public class Moto extends Vehiculo {
    ...
}
```

- `Vehiculo` es la clase base (o superclase).
- `Moto` es la subclase.
- `Moto` hereda los atributos y métodos públicos/protegidos de `Vehiculo`.

### `super`

Se usa para llamar al constructor o a un método de la clase base.

```java
public Moto(String patente, double capacidadKg,
            double velocidadKmH, double costoPorKm) {
    super(patente, capacidadKg, velocidadKmH, costoPorKm);
}
```

`super(...)` tiene que ser la primera instrucción del constructor.

También se usa para llamar a un método heredado:

```java
@Override
public boolean puedeTransportar(Pedido pedido) {
    if (pedido instanceof PedidoFragil) {
        return false;
    }
    return super.puedeTransportar(pedido);
}
```

### `@Override`

Es una anotación. Le dice al compilador: “este método sobrescribe uno de la clase base”. Si te equivocás en el nombre o en los parámetros, el compilador te avisa.

No es obligatorio, pero es muy recomendable.

### Sobrescritura (override)

La subclase redefine un método de la clase base.

```java
public class Moto extends Vehiculo {
    @Override
    public double calcularTiempoEntrega(double km) {
        return ((km / getVelocidadKmH()) * 60) + 5;
    }
}
```

### Clase abstracta

Una clase abstracta no se puede instanciar. Sirve como base común.

```java
public abstract class Vehiculo {
    ...
}
```

No podés hacer:

```java
Vehiculo v = new Vehiculo(...); // error
```

Sí podés hacer:

```java
Vehiculo v = new Moto(...); // OK
```

### Método abstracto

No tiene cuerpo; obliga a las subclases a implementarlo.

```java
public abstract double calcularTiempoEntrega(double km);
```

Cada subclase define su fórmula.

### ¿Por qué `Pedido` y `Vehiculo` son abstractas?

Porque no existe un “pedido genérico” ni un “vehículo genérico” con una fórmula única:

- Un pedido normal no tiene recargo.
- Uno urgente tiene 50% más.
- Uno frágil tiene $800 más.

- Una bicicleta no tiene tiempo extra.
- Una moto suma 5 minutos.
- Una camioneta suma 10.

Si `Pedido` no fuera abstracta, habría que poner un `if` por tipo dentro de `calcularCosto`. Con herencia, cada subclase sabe su propia fórmula.

### Interfaz

Una interfaz define un contrato: qué métodos tiene que tener una clase. No dice cómo implementarlos.

```java
public interface Facturable {
    double calcularImporte();
}
```

Una clase la implementa con `implements`:

```java
public class Pedido implements Rastreable, Facturable {
    @Override
    public double calcularImporte() {
        ...
    }

    @Override
    public String obtenerEstadoActual() {
        ...
    }
}
```

Java permite implementar varias interfaces, aunque solo heredar de una clase. Eso es la “herencia múltiple” de interfaces.

### Polimorfismo

Polimorfismo significa “muchas formas”. Un mismo método puede comportarse distinto según el objeto real.

```java
Vehiculo[] vehiculos = new Vehiculo[3];
vehiculos[0] = new Bicicleta(...);
vehiculos[1] = new Moto(...);
vehiculos[2] = new Camioneta(...);

for (int i = 0; i < vehiculos.length; i++) {
    System.out.println(vehiculos[i].calcularTiempoEntrega(10));
}
```

La variable es de tipo `Vehiculo`, pero Java mira el objeto real y ejecuta el método de la subclase correspondiente. Esto se llama **despacho dinámico**.

En el proyecto se ve en:

- `listarTiemposDeEntrega`: recorre `Vehiculo[]` y llama a `calcularTiempoEntrega`.
- `listarPedidosConCostos`: recorre `Pedido[]` y llama a `calcularImporte`.
- `MainEjercicio4`: recorre `Empleado[]` y llama a `calcularSueldo`.

### `instanceof`

Sirve para preguntar si un objeto es de un tipo.

```java
if (vehiculo instanceof Bicicleta && pedido instanceof PedidoFragil) {
    throw new CapacidadExcedidaException("Una bicicleta no puede transportar pedidos frágiles.");
}
```

Se usa solamente cuando hay una regla especial que no se puede resolver con polimorfismo. En el proyecto aparece en:

- `Bicicleta.puedeTransportar`: para rechazar pedidos frágiles.
- `Repartidor.asignarPedido`: para dar un mensaje más claro.

### Casting

Convertir una referencia de un tipo a otro.

```java
Vehiculo v = new Moto(...);
Moto m = (Moto) v; // casting explícito
```

Si el objeto no es realmente del tipo al que hacés casting, se lanza `ClassCastException`. En el proyecto no abusamos del casting.

---

## 9. Excepciones

Una excepción es un error que ocurre mientras el programa corre. Java permite manejarlo para que el programa no se corte.

### `try` / `catch`

```java
try {
    int numero = Integer.parseInt("hola");
} catch (NumberFormatException e) {
    System.out.println("Eso no es un número.");
}
```

- `try`: bloque que puede fallar.
- `catch`: qué hacer si falla.
- `e`: variable que guarda la excepción.
- `e.getMessage()`: mensaje de error.

### `finally`

Se ejecuta siempre, haya error o no.

```java
try {
    while (!salir) {
        ...
    }
} finally {
    System.out.println("Cerrando CityLink.");
    teclado.close();
}
```

En el proyecto se usa para cerrar el `Scanner` y mostrar el mensaje de despedida.

### `throw`

Lanza una excepción en el momento.

```java
if (pesoKg <= 0) {
    throw new PedidoInvalidoException("El peso tiene que ser mayor que cero.");
}
```

### `throws`

Declara que un método puede lanzar una excepción checked. Quien lo llama tiene que manejarla o volver a declararla.

```java
public void setPesoKg(double pesoKg) throws PedidoInvalidoException {
    ...
}
```

### Excepciones checked y unchecked

- **Checked**: el compilador obliga a manejarlas o declararlas. Heredan de `Exception` pero no de `RuntimeException`. Ejemplo: `PedidoInvalidoException`, `ClienteNoEncontradoException`.
- **Unchecked**: no es obligatorio manejarlas. Heredan de `RuntimeException`. Ejemplo: `EstadoInvalidoException`, `IllegalArgumentException`, `NullPointerException`.

En el proyecto:

| Excepción | Tipo | Cuándo se lanza |
| --- | --- | --- |
| `PedidoInvalidoException` | checked | peso/distancia inválidos, código repetido |
| `ClienteNoEncontradoException` | checked | no existe el cliente |
| `AlmacenamientoLlenoException` | checked | no hay lugar en el arreglo |
| `CapacidadExcedidaException` | checked | el vehículo no puede llevar el pedido |
| `SinPedidosPendientesException` | checked | no hay pedidos en las colas (E2) |
| `EstadoInvalidoException` | unchecked | transición de estado no permitida |
| `IllegalArgumentException` | unchecked | argumento inválido |
| `IllegalStateException` | unchecked | el objeto no está en condiciones |

### ¿Por qué hacer excepciones propias?

Porque el nombre del error ya explica el problema. `PedidoInvalidoException` dice mucho más que un `Exception` genérico. Además, el menú puede mostrar un mensaje claro y seguir funcionando.

### Propagar o manejar

Un método puede:

1. Manejar la excepción con `try/catch`.
2. Declararla con `throws` y dejarla subir.
3. No hacer nada si es unchecked.

En el proyecto, `Main` captura las excepciones del menú:

```java
try {
    switch (opcion) {
        ...
    }
} catch (Exception e) {
    System.out.println("Error: " + e.getMessage());
    System.out.println("El sistema sigue funcionando.");
}
```

Así, si el usuario ingresa un dato inválido, el programa no se cierra.

---

## 10. Arreglos y colecciones

### Arreglos

Un arreglo tiene tamaño fijo.

```java
Cliente[] clientes = new Cliente[50];
clientes[0] = new Cliente(...);
```

- `new Cliente[50]`: crea 50 espacios, todos en `null`.
- `clientes.length`: cantidad de espacios (50).
- El primer índice es `0`.
- El último es `length - 1`.

Para saber cuántos están cargados, el proyecto usa un contador aparte:

```java
private int cantidadClientes;
```

Al agregar:

```java
clientes[cantidadClientes] = cliente;
cantidadClientes++;
```

Al eliminar, hay que desplazar los siguientes para no dejar un hueco:

```java
for (int i = posicion; i < cantidadClientes - 1; i++) {
    clientes[i] = clientes[i + 1];
}
clientes[cantidadClientes - 1] = null;
cantidadClientes--;
```

### `ArrayList`

Es un arreglo dinámico: crece y se achica solo.

```java
ArrayList<Pedido> pedidos = new ArrayList<Pedido>();
pedidos.add(pedido);
pedidos.get(0);
pedidos.size();
pedidos.remove(pedido);
pedidos.contains(pedido);
```

- `<Pedido>` es un **genérico**: indica qué tipo de objetos guarda.
- `add`: agrega al final.
- `get(i)`: devuelve el elemento en la posición `i`.
- `size()`: cantidad de elementos.
- `remove(objeto)`: elimina la primera ocurrencia.
- `contains(objeto)`: pregunta si está.

### `List`

`List` es una interfaz; `ArrayList` es una implementación.

```java
List<Pedido> pedidos = new ArrayList<Pedido>();
```

Es buena práctica declarar la variable como `List` y crear un `ArrayList`. Así, si después querés cambiar la implementación, solo cambiás el `new`.

### `TreeMap`

Es un mapa ordenado por clave.

```java
TreeMap<Integer, Cliente> clientes = new TreeMap<Integer, Cliente>();
clientes.put(1, cliente);
Cliente c = clientes.get(1);
clientes.containsKey(1);
clientes.remove(1);
```

- La clave es `Integer` (el código del cliente).
- El valor es `Cliente`.
- `put`: agrega o reemplaza.
- `get`: busca por clave.
- `containsKey`: pregunta si existe la clave.
- `remove`: elimina.
- `values()`: devuelve todos los valores.
- Las claves quedan ordenadas de menor a mayor automáticamente.

En E2 se usa para clientes. En E1 se usaba un arreglo con búsqueda lineal.

### `Deque` y `ArrayDeque`

`Deque` es una cola doble (se puede usar como cola o como pila). `ArrayDeque` es la implementación.

Como cola (FIFO: primero en entrar, primero en salir):

```java
Deque<Pedido> cola = new ArrayDeque<Pedido>();
cola.offerLast(pedido);   // agrega al final
Pedido primero = cola.peekFirst(); // mira el primero sin sacarlo
Pedido sacado = cola.pollFirst();  // saca el primero
```

Como pila (LIFO: último en entrar, primero en salir):

```java
Deque<Operacion> pila = new ArrayDeque<Operacion>();
pila.push(operacion);      // agrega arriba
Operacion ultima = pila.pollFirst(); // saca la última
```

`ArrayDeque` es más eficiente que `Stack` y que `LinkedList` para estos usos.

### `Comparator`

Sirve para definir un criterio de ordenamiento.

```java
Comparator<Pedido> porDistancia = new Comparator<Pedido>() {
    @Override
    public int compare(Pedido a, Pedido b) {
        return Double.compare(a.getDistanciaKm(), b.getDistanciaKm());
    }
};
```

`compare` devuelve:

- negativo si `a` va antes que `b`,
- cero si son iguales,
- positivo si `a` va después que `b`.

`Double.compare` y `Integer.compare` ya hacen esa comparación sin errores de redondeo.

### Arreglo versus colección

| | Arreglo | `ArrayList` |
| --- | --- | --- |
| Tamaño | fijo | dinámico |
| Agregar | hay que controlar el índice | `add` |
| Eliminar | hay que desplazar | `remove` |
| Acceso por índice | `arreglo[i]` | `lista.get(i)` |
| Cantidad | `arreglo.length` | `lista.size()` |

En E1 usamos arreglos porque el enunciado lo pedía. En E2 usamos colecciones para mostrar la evolución.

---

## 11. `enum`

Un `enum` es un tipo con un conjunto fijo de valores.

```java
public enum EstadoPedido {
    PENDIENTE,
    EN_CAMINO,
    ENTREGADO,
    CANCELADO
}
```

Se usa así:

```java
EstadoPedido estado = EstadoPedido.PENDIENTE;

if (estado == EstadoPedido.ENTREGADO) {
    ...
}
```

Métodos útiles:

- `estado.name()`: devuelve el nombre (`"PENDIENTE"`).
- `EstadoPedido.values()`: devuelve todos los valores.
- `EstadoPedido.valueOf("ENTREGADO")`: convierte un texto en enum.

En E2 también hay un enum dentro de `Operacion`:

```java
public enum TipoOperacion {
    ALTA_CLIENTE,
    BAJA_CLIENTE,
    ...
}
```

Así la pila sabe qué operación tiene que deshacer.

---

## 12. Palabras reservadas de Java

Las palabras reservadas son palabras que el lenguaje ya usa para algo. No se pueden usar como nombre de variable, clase o método.

En el proyecto aparecen estas:

| Palabra | Para qué sirve | Ejemplo en el proyecto |
| --- | --- | --- |
| `abstract` | clase o método sin implementación completa | `public abstract class Pedido` |
| `boolean` | verdadero/falso | `boolean salir = false;` |
| `break` | corta un `switch` o un bucle | `break;` |
| `case` | caso de un `switch` | `case 1:` |
| `catch` | maneja una excepción | `catch (Exception e)` |
| `class` | declara una clase | `public class Cliente` |
| `default` | caso por defecto del `switch` | `default:` |
| `double` | número decimal | `double pesoKg;` |
| `else` | alternativa del `if` | `else { ... }` |
| `enum` | tipo enumerado | `public enum EstadoPedido` |
| `extends` | herencia de clase | `class Moto extends Vehiculo` |
| `false` | literal falso | `boolean activo = false;` |
| `final` | constante / no modificable | `public static final int CAPACIDAD_PEDIDOS = 100;` |
| `finally` | bloque que se ejecuta siempre | `finally { ... }` |
| `for` | bucle | `for (int i = 0; ...)` |
| `if` | condicional | `if (pesoKg <= 0)` |
| `implements` | implementa una interfaz | `class Pedido implements Rastreable` |
| `import` | importa una clase | `import java.util.Scanner;` |
| `instanceof` | pregunta el tipo | `pedido instanceof PedidoFragil` |
| `int` | entero | `int codigo;` |
| `interface` | declara una interfaz | `public interface Facturable` |
| `new` | crea un objeto | `new Cliente(...)` |
| `null` | referencia vacía | `vehiculoAsignado = null;` |
| `package` | paquete de la clase | `package modelo;` |
| `private` | solo accesible dentro de la clase | `private int codigo;` |
| `public` | accesible desde cualquier lado | `public void mostrarDatos()` |
| `return` | devuelve un valor | `return total;` |
| `static` | pertenece a la clase | `public static void main` |
| `super` | llama a la clase base | `super(patente, ...)` |
| `switch` | selección múltiple | `switch (opcion)` |
| `this` | objeto actual | `this.nombre = nombre;` |
| `throw` | lanza una excepción | `throw new PedidoInvalidoException(...)` |
| `throws` | declara excepciones | `throws PedidoInvalidoException` |
| `true` | literal verdadero | `boolean activo = true;` |
| `try` | bloque que puede fallar | `try { ... }` |
| `void` | sin retorno | `public void mostrarDatos()` |
| `while` | bucle | `while (i < lista.size())` |

Otras palabras reservadas de Java que **no** usamos en este proyecto, pero conviene conocer:

| Palabra | Para qué sirve |
| --- | --- |
| `assert` | verificación de una condición en pruebas |
| `byte` | entero de 8 bits |
| `char` | un carácter |
| `const` | reservada, no se usa en Java |
| `continue` | salta a la siguiente vuelta |
| `do` | bucle que se ejecuta al menos una vez |
| `float` | decimal de menor precisión |
| `goto` | reservada, no se usa en Java |
| `long` | entero grande |
| `native` | método implementado en otro lenguaje |
| `protected` | acceso dentro del paquete y subclases |
| `short` | entero corto |
| `strictfp` | precisión estricta de decimales |
| `synchronized` | control de concurrencia |
| `transient` | no serializar un campo |
| `volatile` | visibilidad entre hilos |

También son especiales:

- `true`, `false`, `null`: literales, no se pueden usar como identificadores.
- `@Override`: anotación, no palabra reservada.
- `String`, `System`, `Math`, `Integer`, `Double`: clases de Java, no palabras reservadas.

---

## 13. Las instrucciones que aparecen en el código

Esta es una lista de las formas de instrucción más usadas, con un ejemplo real.

| Instrucción | Qué hace | Ejemplo |
| --- | --- | --- |
| `package ...;` | declara el paquete | `package modelo;` |
| `import ...;` | importa una clase | `import java.util.List;` |
| `public class ...` | declara una clase | `public class Cliente {` |
| `private int x;` | declara un atributo | `private int codigo;` |
| `public Cliente(...)` | constructor | `public Cliente(int codigo, ...)` |
| `public int getX()` | método que devuelve | `public int getCodigo()` |
| `public void setX(...)` | método que no devuelve | `public void setNombre(...)` |
| `new Clase(...)` | crea un objeto | `new ArrayList<Pedido>()` |
| `objeto.metodo()` | llama a un método | `sistema.listarClientes()` |
| `variable = valor;` | asigna | `total = 0;` |
| `if (condicion)` | condicional | `if (pedido == null)` |
| `if / else if / else` | alternativas | `if (...) { } else { }` |
| `for (...)` | repetición con índice | `for (int i = 0; i < n; i++)` |
| `for (Tipo x : coleccion)` | repetición por elemento | `for (Cliente c : clientes.values())` |
| `while (...)` | repetición con condición | `while (inicio <= fin)` |
| `switch (...)` | selección múltiple | `switch (opcion)` |
| `case ...:` | caso | `case 1:` |
| `break;` | corta | `break;` |
| `return valor;` | devuelve | `return total;` |
| `throw new ...` | lanza excepción | `throw new PedidoInvalidoException(...)` |
| `try { } catch { }` | maneja errores | `try { ... } catch (Exception e) { ... }` |
| `finally { }` | cierre | `finally { teclado.close(); }` |
| `this(...)` | llama a otro constructor | `this(codigo, ..., LocalDateTime.now());` |
| `super(...)` | llama al constructor base | `super(patente, ...);` |
| `@Override` | sobrescribe | `@Override public String toString()` |

---

## 14. Cómo leer un error de Java

### Error de compilación

Aparece antes de ejecutar. Por ejemplo:

```
error: ';' expected
```

Significa que falta un punto y coma. El compilador indica el archivo y la línea.

### Error de ejecución

Aparece mientras corre el programa. Java muestra una **stack trace**:

```
Exception in thread "main" java.lang.NullPointerException
    at modelo.Pedido.toString(Pedido.java:181)
    at app.Main.main(Main.java:56)
```

Se lee de abajo hacia arriba:

1. `Main.main` llamó a algo.
2. Ese algo llegó a `Pedido.toString`.
3. En la línea 181 se produjo un `NullPointerException`.

Los errores más comunes en este proyecto:

| Error | Causa | Solución |
| --- | --- | --- |
| `NullPointerException` | usaste un objeto `null` | verificar con `if (obj != null)` |
| `ArrayIndexOutOfBoundsException` | índice fuera del arreglo | revisar `i < cantidad` |
| `NumberFormatException` | texto que no es número | usar `try/catch` y validar |
| `ClassCastException` | casting a un tipo incorrecto | usar `instanceof` antes |
| `IllegalArgumentException` | dato inválido | validar antes de llamar |
| `StackOverflowError` | recursión sin caso base | agregar caso base |

### Depurar con `System.out.println`

Una técnica simple: imprimir valores intermedios.

```java
System.out.println("entré al for, i = " + i);
```

Después se borra o se comenta. No es elegante, pero sirve para entender qué está pasando.

---

## 15. Ejercicio para practicar: leer un método línea por línea

Tomemos `Pedido.cambiarEstado`:

```java
public void cambiarEstado(EstadoPedido nuevoEstado) {
    if (nuevoEstado == null) {
        throw new EstadoInvalidoException("El estado nuevo no puede ser nulo.");
    }

    boolean transicionValida =
            (estado == EstadoPedido.PENDIENTE
                    && (nuevoEstado == EstadoPedido.EN_CAMINO
                    || nuevoEstado == EstadoPedido.CANCELADO))
            || (estado == EstadoPedido.EN_CAMINO
                    && nuevoEstado == EstadoPedido.ENTREGADO);

    if (!transicionValida) {
        throw new EstadoInvalidoException(
                "No se puede pasar de " + estado + " a " + nuevoEstado + ".");
    }

    this.estado = nuevoEstado;
}
```

Explicación:

1. Recibe el estado nuevo.
2. Si es `null`, lanza una excepción.
3. Calcula `transicionValida`:
   - desde `PENDIENTE` se puede ir a `EN_CAMINO` o `CANCELADO`;
   - desde `EN_CAMINO` se puede ir a `ENTREGADO`.
4. Si la transición no es válida, lanza una excepción con el estado actual y el nuevo.
5. Si es válida, asigna el nuevo estado.

¿Por qué no se permite `ENTREGADO -> PENDIENTE`? Porque un pedido entregado ya terminó. Para deshacer eso está la opción “Deshacer” del E2, que usa `restaurarEstado` y es una operación controlada.

Si podés explicar este método, ya tenés la base para explicar casi cualquier otro: mirás qué recibe, qué valida, qué calcula, qué devuelve y qué excepciones lanza.

---

## 16. Lectura línea por línea de un programa completo

Este es el `Main` del TP0. Es corto, pero tiene casi todo lo básico: clase, método `main`, objetos, constructores, atributos, operador punto, arreglo, `for`, `System.out.println` y llamada a métodos.

```java
/**
 * Actividad del TP0: crear la clase Estudiante, instanciar objetos
 * de las dos formas y recorrer un arreglo.
 */
public class Main {

    public static void main(String[] args) {

        Estudiante e1 = new Estudiante();
        e1.nombre = "Ana";
        e1.apellido = "García";
        e1.edad = 20;
        e1.carrera = "Licenciatura en Sistemas";
        e1.promedio = 8.5;

        Estudiante e2 = new Estudiante();
        e2.nombre = "Bruno";
        e2.apellido = "Pérez";
        e2.edad = 22;
        e2.carrera = "Contador Público";
        e2.promedio = 7.25;

        Estudiante e3 = new Estudiante("Carla", "López", 19, "Ingeniería Civil", 9.1);
        Estudiante e4 = new Estudiante("Diego", "Martínez", 21, "Licenciatura en Sistemas", 6.75);

        Estudiante[] estudiantes = new Estudiante[4];
        estudiantes[0] = e1;
        estudiantes[1] = e2;
        estudiantes[2] = e3;
        estudiantes[3] = e4;

        System.out.println("=== LISTADO DE ESTUDIANTES ===");

        for (int i = 0; i < estudiantes.length; i++) {
            Estudiante actual = estudiantes[i];
            System.out.println(actual.nombre + " " + actual.apellido
                    + " - Promedio: " + actual.promedio);
        }

        System.out.println();
        System.out.println("=== DATOS COMPLETOS ===");
        for (int i = 0; i < estudiantes.length; i++) {
            estudiantes[i].mostrarDatos();
            System.out.println("-------------------------");
        }
    }
}
```

### Línea por línea

```java
/**
 * Actividad del TP0...
 */
```

Es un comentario de bloque. No se ejecuta. Sirve para explicar qué hace el archivo.

```java
public class Main {
```

Declara una clase llamada `Main`.

- `public`: se puede usar desde cualquier paquete.
- `class`: palabra reservada que indica que empieza una clase.
- `Main`: nombre de la clase. Tiene que coincidir con el archivo `Main.java`.
- `{`: abre el cuerpo de la clase.

```java
public static void main(String[] args) {
```

Declara el método que arranca el programa.

- `public`: la JVM lo puede llamar.
- `static`: pertenece a la clase, no hace falta crear un objeto `Main`.
- `void`: no devuelve ningún valor.
- `main`: nombre especial que busca la JVM.
- `String[] args`: recibe un arreglo de textos con argumentos de la línea de comandos. Aunque no lo usemos, tiene que estar.
- `{`: abre el cuerpo del método.

```java
Estudiante e1 = new Estudiante();
```

Crea un objeto de la clase `Estudiante` y lo guarda en la variable `e1`.

- `Estudiante`: tipo de la variable.
- `e1`: nombre de la variable.
- `=`: asigna.
- `new`: reserva memoria para un objeto nuevo.
- `Estudiante()`: llama al constructor sin parámetros.
- `;`: termina la instrucción.

```java
e1.nombre = "Ana";
```

Asigna el texto `"Ana"` al atributo `nombre` del objeto `e1`. El punto significa “entrar al objeto y acceder a ese atributo”.

```java
e1.apellido = "García";
e1.edad = 20;
e1.carrera = "Licenciatura en Sistemas";
e1.promedio = 8.5;
```

Lo mismo para los demás atributos. Como en el TP0 los atributos son `public`, se puede acceder directamente. En el TP1 se vuelven `private` y se usan setters.

```java
Estudiante e2 = new Estudiante();
...
```

Crea un segundo estudiante con el constructor vacío y le carga los datos.

```java
Estudiante e3 = new Estudiante("Carla", "López", 19, "Ingeniería Civil", 9.1);
```

Crea un objeto y le pasa todos los datos al constructor. Los argumentos tienen que coincidir en cantidad, orden y tipo con los parámetros del constructor:

```java
public Estudiante(String nombre, String apellido, int edad,
                  String carrera, double promedio)
```

- `"Carla"` va a `nombre`.
- `"López"` va a `apellido`.
- `19` va a `edad`.
- `"Ingeniería Civil"` va a `carrera`.
- `9.1` va a `promedio`.

```java
Estudiante e4 = new Estudiante("Diego", "Martínez", 21, "Licenciatura en Sistemas", 6.75);
```

Igual que la anterior, con otros datos.

```java
Estudiante[] estudiantes = new Estudiante[4];
```

Crea un arreglo de 4 posiciones para guardar referencias a objetos `Estudiante`.

- `Estudiante[]`: tipo “arreglo de Estudiante”.
- `new Estudiante[4]`: crea el arreglo con 4 espacios.
- Al principio las cuatro posiciones valen `null`.
- Los índices van de `0` a `3`.
- `estudiantes.length` vale `4`.

```java
estudiantes[0] = e1;
estudiantes[1] = e2;
estudiantes[2] = e3;
estudiantes[3] = e4;
```

Guarda cada objeto en una posición. No se copia el objeto: se copia la referencia. `estudiantes[0]` y `e1` apuntan al mismo objeto.

```java
System.out.println("=== LISTADO DE ESTUDIANTES ===");
```

Imprime un texto y baja un renglón.

- `System`: clase de Java.
- `out`: flujo de salida.
- `println`: imprime y agrega salto de línea.
- `"..."`: texto literal.

```java
for (int i = 0; i < estudiantes.length; i++) {
```

Repite el bloque mientras `i` sea menor que la cantidad de elementos.

- `int i = 0`: crea el contador en 0.
- `i < estudiantes.length`: condición. Se evalúa antes de cada vuelta.
- `i++`: suma 1 al final de cada vuelta.
- `{`: abre el bloque que se repite.

Cuando `i` llega a `4`, la condición `4 < 4` es falsa y el `for` termina.

```java
Estudiante actual = estudiantes[i];
```

Guarda en `actual` el objeto de la posición `i`. En la primera vuelta es `e1`, en la segunda `e2`, etc. La variable `actual` es de tipo `Estudiante`.

```java
System.out.println(actual.nombre + " " + actual.apellido
        + " - Promedio: " + actual.promedio);
```

Arma un texto con el nombre, un espacio, el apellido, el texto `" - Promedio: "` y el promedio. El operador `+` concatena textos. Como `promedio` es `double`, Java lo convierte a texto automáticamente.

```java
System.out.println();
```

Imprime una línea vacía. Sirve para separar secciones.

```java
System.out.println("=== DATOS COMPLETOS ===");
```

Imprime el segundo título.

```java
for (int i = 0; i < estudiantes.length; i++) {
    estudiantes[i].mostrarDatos();
    System.out.println("-------------------------");
}
```

Recorre otra vez el arreglo. En cada vuelta llama al método `mostrarDatos()` del objeto y después imprime una línea separadora.

```java
estudiantes[i].mostrarDatos();
```

Llama al método sobre el objeto de la posición `i`. El método no recibe parámetros y no devuelve nada (`void`), pero imprime los datos por pantalla.

```java
    }
}
```

La primera llave cierra el `for`. La segunda cierra el método `main`. La que sigue (no se ve en el fragmento) cierra la clase. Siempre tienen que estar balanceadas.

### Lo que hay que poder explicar de este programa

1. Qué diferencia hay entre `new Estudiante()` y `new Estudiante("Carla", ...)`.
2. Qué es una variable de tipo referencia.
3. Qué hace el operador punto.
4. Por qué el arreglo empieza en 0.
5. Qué hace `estudiantes.length`.
6. Qué hace cada parte del `for`.
7. Qué diferencia hay entre `System.out.print` y `System.out.println`.
8. Por qué `main` es `public static void`.
9. Qué significa que `mostrarDatos()` sea `void`.
10. Qué pasaría si el arreglo tuviera 4 posiciones y se intentara acceder a `estudiantes[4]`.

La respuesta a la 10 es: se lanza `ArrayIndexOutOfBoundsException`, porque el último índice válido es `3`.

---

## 17. Símbolos que conviene reconocer

| Símbolo | Nombre | Para qué se usa |
| --- | --- | --- |
| `;` | punto y coma | termina una instrucción |
| `{ }` | llaves | delimitan bloques |
| `( )` | paréntesis | agrupan condiciones y argumentos |
| `[ ]` | corchetes | arreglos y acceso por índice |
| `.` | punto | accede a atributos y métodos |
| `,` | coma | separa parámetros o elementos |
| `=` | asignación | guarda un valor |
| `==` | igualdad | compara valores o referencias |
| `!=` | distinto | compara diferencia |
| `+` | suma o concatenación | suma números o une textos |
| `-` | resta | resta números |
| `*` | multiplicación | multiplica |
| `/` | división | divide |
| `%` | módulo | devuelve el resto |
| `&&` | AND lógico | verdadero si ambos son verdaderos |
| `\|\|` | OR lógico | verdadero si al menos uno es verdadero |
| `!` | NOT lógico | invierte verdadero/falso |
| `++` | incremento | suma 1 |
| `--` | decremento | resta 1 |
| `//` | comentario | comenta hasta el final de la línea |
| `/* */` | comentario de bloque | comenta varias líneas |
| `@` | anotación | marca especial, por ejemplo `@Override` |
| `" "` | comillas dobles | delimitan un `String` |
| `' '` | comillas simples | delimitan un `char` |

---

## 18. Preguntas típicas de la exposición

Estas preguntas sirven para practicar. No están para memorizar la respuesta, sino para entender el porqué.

### Sobre el lenguaje

1. ¿Java es compilado o interpretado?
2. ¿Qué diferencia hay entre JDK, JRE y JVM?
3. ¿Qué es el bytecode?
4. ¿Por qué el archivo `.java` tiene que llamarse igual que la clase pública?
5. ¿Qué es un paquete?
6. ¿Para qué sirve `import`?
7. ¿Qué diferencia hay entre un tipo primitivo y un tipo de referencia?
8. ¿Qué es `null`?
9. ¿Qué diferencia hay entre `==` y `.equals`?
10. ¿Qué pasa si dividís dos `int`?

### Sobre clases y objetos

11. ¿Qué es una clase?
12. ¿Qué es un objeto?
13. ¿Qué hace `new`?
14. ¿Qué es un constructor?
15. ¿Puede haber más de un constructor?
16. ¿Qué hace `this`?
17. ¿Qué hace `this(...)`?
18. ¿Qué es el encapsulamiento?
19. ¿Para qué sirven los getters y setters?
20. ¿Qué hace `toString`?

### Sobre herencia y polimorfismo

21. ¿Qué hace `extends`?
22. ¿Qué hace `super`?
23. ¿Qué hace `super(...)`?
24. ¿Qué es una clase abstracta?
25. ¿Qué es un método abstracto?
26. ¿Se puede hacer `new Vehiculo(...)`?
27. ¿Qué es una interfaz?
28. ¿Qué diferencia hay entre `extends` e `implements`?
29. ¿Qué es el polimorfismo?
30. ¿Qué es el despacho dinámico?
31. ¿Qué hace `instanceof`?
32. ¿Qué es un casting?

### Sobre excepciones

33. ¿Qué es una excepción?
34. ¿Qué hace `try`?
35. ¿Qué hace `catch`?
36. ¿Qué hace `finally`?
37. ¿Qué diferencia hay entre `throw` y `throws`?
38. ¿Qué es una excepción checked?
39. ¿Qué es una excepción unchecked?
40. ¿Por qué crear excepciones propias?

### Sobre estructuras de datos

41. ¿Qué diferencia hay entre un arreglo y un `ArrayList`?
42. ¿Qué hace `ArrayList.add`?
43. ¿Qué hace `ArrayList.remove`?
44. ¿Qué es un `TreeMap`?
45. ¿Qué diferencia hay entre una `List` y un `Map`?
46. ¿Qué es una cola FIFO?
47. ¿Qué es una pila LIFO?
48. ¿Qué hace `offerLast`?
49. ¿Qué hace `pollFirst`?
50. ¿Qué hace `push`?
51. ¿Qué es un `Comparator`?
52. ¿Qué devuelve `compare`?

### Sobre algoritmos

53. ¿Qué complejidad tiene la búsqueda lineal?
54. ¿Qué complejidad tiene la búsqueda binaria?
55. ¿Qué condición necesita la búsqueda binaria?
56. ¿Qué complejidad tiene burbuja?
57. ¿Qué complejidad tiene inserción?
58. ¿Qué complejidad tiene quicksort en el promedio?
59. ¿Qué complejidad tiene mergesort?
60. ¿Qué hace la intercalación?
61. ¿Por qué la búsqueda binaria del proyecto ordena una copia?
62. ¿Qué hace `costoSeguro`?

### Sobre el proyecto

63. ¿Por qué `Pedido` es abstracta?
64. ¿Por qué `Vehiculo` es abstracta?
65. ¿Cómo se calcula el costo de un pedido urgente?
66. ¿Cómo se calcula el costo de un pedido frágil?
67. ¿Cómo se calcula el tiempo de una moto?
68. ¿Por qué una bicicleta no puede llevar pedidos frágiles?
69. ¿Cómo se calcula la comisión de un repartidor?
70. ¿Por qué `Main` está separado de `CityLink`?
71. ¿Qué hace el bloque `finally` del menú?
72. ¿Qué pasa si el usuario escribe letras en una opción numérica?
73. ¿Por qué E1 usa arreglos y E2 usa colecciones?
74. ¿Qué se gana con `TreeMap` para los clientes?
75. ¿Por qué hay dos colas en E2?
76. ¿Para qué sirve la pila de operaciones?
77. ¿Qué operación no se genera desde el menú de E2?
78. ¿Qué se podría mejorar del proyecto?

---

## Resumen final

Para entender cualquier clase del proyecto, preguntate:

1. ¿Qué representa?
2. ¿Qué atributos guarda?
3. ¿Cómo se construye?
4. ¿Qué métodos tiene y qué devuelven?
5. ¿De quién hereda o qué interfaz implementa?
6. ¿Qué valida?
7. ¿Qué excepciones lanza?
8. ¿Dónde se usa dentro del sistema?

Si respondés esas ocho preguntas, podés defender cualquier parte del código.
