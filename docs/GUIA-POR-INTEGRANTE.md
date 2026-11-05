# Guía por integrante

Esta guía tiene dos partes para cada persona:

1. **Qué hacer en Git** para que quede un commit propio (salvo Erebothh, que ya es el dueño del repo).
2. **Qué tiene que saber explicar** de su parte del código.

La idea no es que cada uno memorize solo un pedazo. En la exposición pueden preguntar cualquier clase, así que todos tienen que leer también `docs/GUIA-JAVA.md` y `docs/RECORRIDO-DEL-CODIGO.md`.

---

## Antes de empezar: reglas comunes

- No subir la carpeta `out/` ni archivos `.class`.
- No subir contraseñas, tokens ni datos personales.
- Cada commit tiene que tener un mensaje claro.
- Antes de commitear, compilar y probar.
- Si algo no compila, no se commitea.
- Si no entienden un cambio, no lo suben.
- Cada integrante tiene que poder explicar cualquier línea, no solo la que escribió.

Comandos de compilación:

```bash
# TP0
cd TP0
javac -encoding UTF-8 -d out src/*.java
java -cp out Main

# TP0-extras
cd TP0-extras
javac -encoding UTF-8 -d out src/*.java
java -cp out Main

# TP1
cd TP1
javac -encoding UTF-8 -d out src/*.java
java -cp out MainEjercicio1

# Entregable 1
cd Proyecto/Entregable1
javac -encoding UTF-8 -d out $(find src -name "*.java")
java -cp out app.Main

# Entregable 2
cd Proyecto/Entregable2
javac -encoding UTF-8 -d out $(find src -name "*.java")
java -cp out app.Main
```

Los pasos detallados de Git están en [`GIT-PASO-A-PASO.md`](GIT-PASO-A-PASO.md).

---

# Thiago Sturzenegger

## 1. Qué tenés que hacer en Git

1. Clonar el repositorio:

```bash
git clone https://github.com/Erebothh/Prog-1.git
cd Prog-1
```

2. Configurar tu identidad:

```bash
git config user.name "Thiago Sturzenegger"
git config user.email "tu-email-de-github@ejemplo.com"
```

3. Crear una rama:

```bash
git checkout -b aporte-thiago
```

4. Hacer un cambio real y chico en tu parte. Algunas opciones:

   - Agregar un comentario explicativo en `Cliente`, `Pedido` o alguna subclase.
   - Agregar una validación que falte (por ejemplo, que el teléfono tenga solo números).
   - Agregar un método `getNombreCompleto()` en `Cliente` y usarlo en `toString`.
   - Agregar un archivo `docs/aportes/thiago.md` con tus pruebas y conclusiones.

   Si tocás código, compilá y probá antes de commitear.

5. Ver qué cambiaste:

```bash
git status
git diff
```

6. Agregar y commitear:

```bash
git add .
git commit -m "Agrego validación y comentarios en Cliente y Pedido"
```

7. Subir la rama:

```bash
git push -u origin aporte-thiago
```

8. Entrar a GitHub y abrir un **Pull Request** desde `aporte-thiago` hacia `main`. Erebothh lo revisa y lo mergea.

## 2. Archivos que tenés que dominar

- `Proyecto/Entregable1/src/modelo/Cliente.java`
- `Proyecto/Entregable1/src/modelo/Pedido.java`
- `Proyecto/Entregable1/src/modelo/PedidoNormal.java`
- `Proyecto/Entregable1/src/modelo/PedidoUrgente.java`
- `Proyecto/Entregable1/src/modelo/PedidoFragil.java`
- `Proyecto/Entregable1/src/modelo/EstadoPedido.java`
- `Proyecto/Entregable1/src/modelo/Rastreable.java`
- `Proyecto/Entregable1/src/modelo/Facturable.java`
- `Proyecto/Entregable2/src/modelo/Cliente.java`
- `Proyecto/Entregable2/src/modelo/Pedido.java`
- `Proyecto/Entregable2/src/app/CityLink.java` (solamente la parte de clientes con `TreeMap`)
- `Proyecto/Entregable2/src/algoritmos/Busqueda.java` (búsqueda de cliente por nombre)

## 3. Explicación de tu parte

### `Cliente`

`Cliente` es una clase de modelo. Representa a una persona que hace pedidos.

Tiene cuatro atributos privados:

```java
private int codigo;
private String nombre;
private String telefono;
private String zona;
```

¿Por qué privados? Porque nadie desde afuera tiene que poder poner `cliente.codigo = -5`. Toda modificación pasa por un setter que valida.

#### Constructores

Hay dos:

```java
public Cliente() {
    this.codigo = 0;
    this.nombre = "";
    this.telefono = "";
    this.zona = "";
}
```

El constructor vacío deja el objeto en un estado neutro. Se usa, por ejemplo, si primero creás el objeto y después le cargás los datos.

```java
public Cliente(int codigo, String nombre, String telefono, String zona) {
    setCodigo(codigo);
    setNombre(nombre);
    setTelefono(telefono);
    setZona(zona);
}
```

El constructor completo no asigna directamente a los atributos: llama a los setters. Así las validaciones se hacen una sola vez y valen para cualquier forma de crear el objeto.

#### Setters

```java
public void setCodigo(int codigo) {
    if (codigo <= 0) {
        throw new IllegalArgumentException("El código del cliente debe ser mayor que cero.");
    }
    this.codigo = codigo;
}
```

- Valida que el código sea positivo.
- `IllegalArgumentException` es una excepción unchecked: no obliga a poner `throws` en la firma.
- `this.codigo` es el atributo; `codigo` es el parámetro.

Los setters de texto validan dos cosas:

```java
if (nombre == null || nombre.trim().isEmpty()) {
    throw new IllegalArgumentException("El nombre del cliente no puede estar vacío.");
}
this.nombre = nombre.trim();
```

- `nombre == null`: evita `NullPointerException`.
- `nombre.trim().isEmpty()`: saca espacios y controla que no quede vacío.
- `trim()` también se guarda, para que no queden espacios al principio o al final.

#### `toString`

```java
@Override
public String toString() {
    return "Cliente " + codigo + " - " + nombre
            + " - Tel: " + telefono + " - Zona: " + zona;
}
```

`toString` se llama automáticamente cuando hacés `System.out.println(cliente)`. Sin este método, se vería algo como `modelo.Cliente@1b6d3586`.

### `Pedido`

`Pedido` es una clase **abstracta**:

```java
public abstract class Pedido implements Rastreable, Facturable {
```

¿Por qué abstracta? Porque no existe un pedido “genérico”. El costo depende del tipo:

- normal: sin recargo;
- urgente: 50% más;
- frágil: $800 más.

Si `Pedido` fuera concreta, habría que poner un `if` por tipo dentro de `calcularCosto`. Con abstracción y herencia, cada subclase define su fórmula.

`implements Rastreable, Facturable` significa que `Pedido` se compromete a implementar:

```java
String obtenerEstadoActual();
double calcularImporte();
```

#### Atributos

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

- `codigo`: identificador único.
- `cliente`: objeto `Cliente` asociado.
- `destino`: texto.
- `pesoKg` y `distanciaKm`: datos para calcular el costo.
- `fechaHora`: cuándo se ingresó.
- `estado`: uno de los valores de `EstadoPedido`.
- `vehiculoAsignado`: se completa cuando se asigna a un repartidor.

#### Constructor con `this(...)`

```java
public Pedido(int codigo, Cliente cliente, String destino,
              double pesoKg, double distanciaKm) throws PedidoInvalidoException {
    this(codigo, cliente, destino, pesoKg, distanciaKm, LocalDateTime.now());
}
```

Este constructor tiene cinco parámetros y usa `this(...)` para llamar al constructor completo con la fecha actual. `this(...)` tiene que ser la primera instrucción.

El constructor completo valida:

```java
if (codigo <= 0) {
    throw new PedidoInvalidoException("El código del pedido debe ser mayor que cero.");
}
if (cliente == null) {
    throw new PedidoInvalidoException("El pedido tiene que tener un cliente.");
}
if (fechaHora == null) {
    throw new PedidoInvalidoException("La fecha y hora del pedido no puede ser nula.");
}
```

Después llama a los setters de destino, peso y distancia, que validan por su cuenta. Al final:

```java
this.estado = EstadoPedido.PENDIENTE;
this.vehiculoAsignado = null;
```

Todo pedido nace pendiente y sin vehículo.

#### Setters que lanzan excepciones checked

```java
public void setPesoKg(double pesoKg) throws PedidoInvalidoException {
    if (pesoKg <= 0) {
        throw new PedidoInvalidoException("El peso tiene que ser mayor que cero.");
    }
    this.pesoKg = pesoKg;
}
```

`PedidoInvalidoException` hereda de `Exception`, no de `RuntimeException`. Por eso es **checked**: el método tiene que declarar `throws` y quien lo llama tiene que manejarla o declararla.

#### `cambiarEstado`

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

Transiciones permitidas:

```
PENDIENTE -> EN_CAMINO
PENDIENTE -> CANCELADO
EN_CAMINO -> ENTREGADO
```

No se permite `ENTREGADO -> PENDIENTE`, por ejemplo. Para eso está “Deshacer” en el E2, que usa `restaurarEstado`.

#### `calcularCosto` y `calcularImporte`

```java
public abstract double calcularCosto(Vehiculo vehiculo);
```

Es abstracto: cada subclase lo implementa.

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

`calcularImporte` es la implementación de `Facturable`. Usa el vehículo asignado y delega en `calcularCosto`. Si no hay vehículo, lanza `IllegalStateException` porque el objeto no está en condiciones de calcular.

#### `obtenerEstadoActual`

```java
@Override
public String obtenerEstadoActual() {
    return estado.name();
}
```

`name()` es un método de los enum que devuelve el nombre del valor, por ejemplo `"EN_CAMINO"`.

### Subclases de `Pedido`

#### `PedidoNormal`

```java
@Override
public double calcularCosto(Vehiculo vehiculo) {
    return 1000 + (getDistanciaKm() * vehiculo.getCostoPorKm());
}

@Override
public String getTipo() {
    return "Normal";
}
```

Costo base $1000 más distancia por costo por km. No tiene recargo.

#### `PedidoUrgente`

```java
@Override
public double calcularCosto(Vehiculo vehiculo) {
    double costoBase = 1000 + (getDistanciaKm() * vehiculo.getCostoPorKm());
    return costoBase * 1.5;
}
```

Calcula igual que el normal y multiplica por 1.5 (50% más).

#### `PedidoFragil`

```java
@Override
public double calcularCosto(Vehiculo vehiculo) {
    double costoBase = 1000 + (getDistanciaKm() * vehiculo.getCostoPorKm());
    return costoBase + 800;
}
```

Suma $800 fijos. Además, una bicicleta no puede transportarlo; esa regla está en `Bicicleta.puedeTransportar`.

#### Constructores de las subclases

Cada subclase tiene dos constructores que llaman a `super(...)`:

```java
public PedidoNormal(int codigo, Cliente cliente, String destino,
                    double pesoKg, double distanciaKm) throws PedidoInvalidoException {
    super(codigo, cliente, destino, pesoKg, distanciaKm);
}
```

`super(...)` llama al constructor de `Pedido`. La subclase no repite las validaciones; las hereda.

### `EstadoPedido`

```java
public enum EstadoPedido {
    PENDIENTE,
    EN_CAMINO,
    ENTREGADO,
    CANCELADO
}
```

Es un enum. Solo puede valer uno de esos cuatro. Se compara con `==` porque son constantes.

### E2: clientes con `TreeMap`

En el Entregable 2, `CityLink` guarda los clientes así:

```java
private TreeMap<Integer, Cliente> clientes;
```

- La clave es el código (`Integer`).
- El valor es el `Cliente`.
- `TreeMap` mantiene las claves ordenadas de menor a mayor.

Métodos:

```java
clientes.put(codigo, cliente);       // agrega o reemplaza
clientes.get(codigo);                // busca
clientes.containsKey(codigo);        // pregunta si existe
clientes.remove(codigo);             // elimina
clientes.values();                   // todos los clientes
clientes.isEmpty();                  // ¿está vacío?
```

`registrarCliente` usa `containsKey` para no repetir códigos:

```java
if (clientes.containsKey(cliente.getCodigo())) {
    throw new IllegalArgumentException(
            "Ya existe un cliente con el código " + cliente.getCodigo() + ".");
}
clientes.put(cliente.getCodigo(), cliente);
```

`buscarCliente(int)` usa `get`:

```java
Cliente cliente = clientes.get(codigo);
if (cliente == null) {
    throw new ClienteNoEncontradoException(...);
}
return cliente;
```

`listarClientesOrdenados` recorre `values()`:

```java
for (Cliente cliente : clientes.values()) {
    System.out.println(cliente);
}
```

Como `TreeMap` ya está ordenado por clave, no hace falta ordenar la lista.

### Búsqueda de cliente por nombre

`buscarCliente(String)` arma una lista con los valores del mapa y usa el algoritmo lineal:

```java
List<Cliente> lista = new ArrayList<Cliente>(clientes.values());
Cliente cliente = Busqueda.busquedaLinealClientePorNombre(lista, nombre);
```

`busquedaLinealClientePorNombre` recorre y compara con `equalsIgnoreCase`:

```java
if (clientes.get(i).getNombre().equalsIgnoreCase(nombre.trim())) {
    return clientes.get(i);
}
```

Es una búsqueda lineal: en el peor caso mira todos los clientes. Como hay pocos, no hay problema.

## 4. Preguntas que te pueden hacer

1. **¿Qué es encapsulamiento?** Esconder los atributos con `private` y acceder solo por getters y setters que validan.
2. **¿Por qué `Cliente` tiene dos constructores?** Uno para crear vacío y otro para crear con todos los datos.
3. **¿Por qué el constructor completo llama a los setters?** Para no repetir validaciones y asegurar que se cumplan siempre.
4. **¿Qué diferencia hay entre `==` y `.equals`?** `==` compara referencias (o valores en primitivos); `.equals` compara contenido.
5. **¿Por qué `Pedido` es abstracta?** Porque el costo depende del tipo y no existe un pedido genérico.
6. **¿Qué diferencia hay entre `calcularCosto` y `calcularImporte`?** `calcularCosto` es abstracto y recibe el vehículo; `calcularImporte` es de la interfaz `Facturable` y usa el vehículo asignado.
7. **¿Qué es una interfaz?** Un contrato con métodos que una clase se compromete a implementar.
8. **¿Por qué `Pedido` implementa dos interfaces?** Porque puede ser rastreado (`Rastreable`) y facturado (`Facturable`).
9. **¿Qué es una excepción checked?** La que el compilador obliga a manejar o declarar con `throws`.
10. **¿Por qué `PedidoInvalidoException` es checked y `EstadoInvalidoException` no?** Porque la primera es un dato inválido que el menú debe manejar; la segunda es un error de uso del programa.
11. **¿Qué hace `this(...)`?** Llama a otro constructor de la misma clase.
12. **¿Qué hace `super(...)`?** Llama al constructor de la clase base.
13. **¿Qué es un enum?** Un tipo con un conjunto fijo de valores.
14. **¿Por qué no hay `setCodigo` en `Pedido`?** Porque el código no debería cambiar después de crear el pedido.
15. **¿Qué hace `trim()`?** Saca espacios al principio y al final.
16. **¿Qué es `LocalDateTime.now()`?** Devuelve la fecha y hora actual.
17. **¿Cómo se calcula el costo de un pedido urgente?** `(1000 + distancia * costoPorKm) * 1.5`.
18. **¿Cómo se calcula el de un frágil?** `1000 + distancia * costoPorKm + 800`.
19. **¿Dónde se usa `TreeMap`?** En E2, para guardar clientes por código y mantenerlos ordenados.
20. **¿Qué ventaja tiene `TreeMap` sobre un arreglo?** No hay que manejar capacidad ni ordenar a mano; las claves quedan ordenadas.

## 5. Ejercicio de práctica

Agregá un método `getNombreCompleto()` en `Cliente` que devuelva `nombre + " (" + zona + ")"`, usalo en `toString` y compilá. Después explicá:

- ¿Qué modificaste?
- ¿Qué tipo devuelve?
- ¿Dónde se usa?
- ¿Por qué no hace falta cambiar el constructor?

---

# Ángel Ferri

## 1. Qué tenés que hacer en Git

1. Clonar y configurar tu identidad (igual que Thiago).
2. Crear tu rama:

```bash
git checkout -b aporte-angel
```

3. Hacer un cambio real en tu parte. Opciones:

   - Agregar un comentario explicativo en `Vehiculo` o `Repartidor`.
   - Agregar una validación (por ejemplo, que la patente tenga un formato mínimo).
   - Agregar un método `getCapacidadDisponible()` en `Vehiculo`.
   - Agregar `docs/aportes/angel.md` con pruebas de las colas y la pila.

4. Compilar y probar.
5. `git add .`, `git commit -m "..."`, `git push -u origin aporte-angel`.
6. Abrir Pull Request.

## 2. Archivos que tenés que dominar

- `Proyecto/Entregable1/src/modelo/Vehiculo.java`
- `Proyecto/Entregable1/src/modelo/Bicicleta.java`
- `Proyecto/Entregable1/src/modelo/Moto.java`
- `Proyecto/Entregable1/src/modelo/Camioneta.java`
- `Proyecto/Entregable1/src/modelo/Repartidor.java`
- `Proyecto/Entregable2/src/modelo/Vehiculo.java`
- `Proyecto/Entregable2/src/modelo/Bicicleta.java`
- `Proyecto/Entregable2/src/modelo/Moto.java`
- `Proyecto/Entregable2/src/modelo/Camioneta.java`
- `Proyecto/Entregable2/src/modelo/Repartidor.java`
- `Proyecto/Entregable2/src/app/Operacion.java`
- `Proyecto/Entregable2/src/app/CityLink.java` (colas, pila y deshacer)

## 3. Explicación de tu parte

### `Vehiculo`

Es una clase abstracta:

```java
public abstract class Vehiculo {
    private String patente;
    private double capacidadKg;
    private double velocidadKmH;
    private double costoPorKm;
```

¿Por qué abstracta? Porque no existe un vehículo genérico con una fórmula única de tiempo. Cada tipo tiene su propia regla.

#### Constructor

```java
public Vehiculo(String patente, double capacidadKg,
                double velocidadKmH, double costoPorKm) {
    setPatente(patente);
    setCapacidadKg(capacidadKg);
    setVelocidadKmH(velocidadKmH);
    setCostoPorKm(costoPorKm);
}
```

Llama a los setters para validar. Todos los valores tienen que ser positivos, y la patente no puede estar vacía.

#### `puedeTransportar`

```java
public boolean puedeTransportar(Pedido pedido) {
    if (pedido == null) {
        return false;
    }
    return pedido.getPesoKg() <= capacidadKg;
}
```

Regla general: el pedido entra si su peso no supera la capacidad. Si el pedido es `null`, devuelve `false` para no fallar.

#### Métodos abstractos

```java
public abstract double calcularTiempoEntrega(double km);
public abstract String getTipoVehiculo();
```

Cada subclase está obligada a implementarlos.

### `Bicicleta`

```java
@Override
public double calcularTiempoEntrega(double km) {
    return (km / getVelocidadKmH()) * 60;
}
```

El tiempo es distancia dividida velocidad, multiplicado por 60 para pasar de horas a minutos. No suma tiempo extra.

Sobrescribe `puedeTransportar`:

```java
@Override
public boolean puedeTransportar(Pedido pedido) {
    if (pedido instanceof PedidoFragil) {
        return false;
    }
    return super.puedeTransportar(pedido);
}
```

- `instanceof` pregunta si el pedido es frágil.
- Si es frágil, devuelve `false`.
- Si no, usa la regla general de capacidad con `super.puedeTransportar(pedido)`.

`super` es importante: no repite la lógica de capacidad, la reutiliza.

### `Moto`

```java
@Override
public double calcularTiempoEntrega(double km) {
    return ((km / getVelocidadKmH()) * 60) + 5;
}
```

Suma 5 minutos por estacionamiento.

### `Camioneta`

```java
@Override
public double calcularTiempoEntrega(double km) {
    return ((km / getVelocidadKmH()) * 60) + 10;
}
```

Suma 10 minutos por carga y descarga.

### Polimorfismo en los vehículos

En `CityLink.listarTiemposDeEntrega`:

```java
for (int i = 0; i < cantidadVehiculos; i++) {
    Vehiculo vehiculo = vehiculos[i];
    System.out.printf("%s (%s): %.1f minutos%n",
            vehiculo.getTipoVehiculo(), vehiculo.getPatente(),
            vehiculo.calcularTiempoEntrega(km));
}
```

La variable es `Vehiculo`, pero Java ejecuta el método de la subclase real. No hay `if` por tipo.

### `Repartidor` en E1

Atributos:

```java
private int legajo;
private String nombre;
private Vehiculo vehiculo;
private Pedido[] pedidos;
private int cantidadPedidos;
```

`pedidos` es un arreglo fijo de 20 y `cantidadPedidos` dice cuántos están ocupados. No se recorre todo el arreglo, solo hasta `cantidadPedidos`.

#### `CAPACIDAD_PEDIDOS`

```java
public static final int CAPACIDAD_PEDIDOS = 20;
```

- `static`: es de la clase, no de cada repartidor.
- `final`: no cambia.
- Se usa para crear el arreglo.

#### `getPedidos`

```java
public Pedido[] getPedidos() {
    Pedido[] copia = new Pedido[cantidadPedidos];
    for (int i = 0; i < cantidadPedidos; i++) {
        copia[i] = pedidos[i];
    }
    return copia;
}
```

Devuelve una copia para que desde afuera no puedan modificar el arreglo interno. Es una forma de encapsulamiento.

#### `asignarPedido` sobrecargado

```java
public void asignarPedido(Pedido pedido)
        throws AlmacenamientoLlenoException, CapacidadExcedidaException {
    asignarPedido(pedido, vehiculo);
}
```

Esta versión usa el vehículo del repartidor. La otra permite pasar un vehículo:

```java
public void asignarPedido(Pedido pedido, Vehiculo vehiculo)
        throws AlmacenamientoLlenoException, CapacidadExcedidaException {
```

Validaciones:

1. `pedido == null || vehiculo == null`: `IllegalArgumentException`.
2. `cantidadPedidos >= pedidos.length`: `AlmacenamientoLlenoException`.
3. Recorre los pedidos y si encuentra el mismo código: `IllegalArgumentException`.
4. `!vehiculo.puedeTransportar(pedido)`: `CapacidadExcedidaException`.
5. Si es bicicleta y el pedido es frágil, da un mensaje específico.
6. Guarda, incrementa el contador y asigna el vehículo al pedido.

#### `registrarEntrega`

Busca el pedido en el arreglo. Si no está, lanza `IllegalArgumentException`. Si está, llama a `pedido.cambiarEstado(ENTREGADO)`. La validación de que esté `EN_CAMINO` la hace `Pedido.cambiarEstado`.

#### Comisión

```java
@Override
public double calcularImporte() {
    double totalEntregado = 0;

    for (int i = 0; i < cantidadPedidos; i++) {
        if (pedidos[i].getEstado() == EstadoPedido.ENTREGADO) {
            totalEntregado = totalEntregado + pedidos[i].calcularImporte();
        }
    }

    return totalEntregado * 0.10;
}
```

Solo suma los `ENTREGADO`. Los pendientes y los que están en camino no cuentan.

#### Estado del repartidor

```java
@Override
public String obtenerEstadoActual() {
    boolean tieneEnCamino = false;
    boolean tienePendiente = false;

    for (int i = 0; i < cantidadPedidos; i++) {
        if (pedidos[i].getEstado() == EstadoPedido.EN_CAMINO) {
            tieneEnCamino = true;
        }
        if (pedidos[i].getEstado() == EstadoPedido.PENDIENTE) {
            tienePendiente = true;
        }
    }

    if (tieneEnCamino) {
        return "OCUPADO (tiene pedidos en camino)";
    }
    if (tienePendiente) {
        return "CON PEDIDOS ASIGNADOS";
    }
    return "DISPONIBLE";
}
```

Prioriza `EN_CAMINO` sobre `PENDIENTE`.

### `Repartidor` en E2

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

`asignarPedido`:

```java
pedidos.add(pedido);
pedido.asignarVehiculo(vehiculo);
```

`registrarEntrega`:

```java
encontrado.cambiarEstado(EstadoPedido.ENTREGADO);

if (!historial.contains(encontrado)) {
    historial.add(encontrado);
}
```

`contains` evita duplicados en el historial.

`quitarDelHistorial` y `quitarPedido` usan `remove` y los usa “Deshacer”.

### Colas en E2

```java
private Deque<Pedido> colaUrgentes;
private Deque<Pedido> colaNormales;
```

Se crean así:

```java
colaUrgentes = new ArrayDeque<Pedido>();
colaNormales = new ArrayDeque<Pedido>();
```

`ArrayDeque` implementa `Deque`. Se usa como cola FIFO:

```java
cola.offerLast(pedido);   // agrega al final
cola.peekFirst();         // mira el primero
cola.pollFirst();         // saca el primero
```

`encolarPedido` decide:

```java
if (pedido.getTipo().equals("Urgente")) {
    colaUrgentes.offerLast(pedido);
} else {
    colaNormales.offerLast(pedido);
}
```

`atenderSiguientePedido`:

1. Si las dos colas están vacías, lanza `SinPedidosPendientesException`.
2. Elige urgentes si hay; si no, normales.
3. Mira el primero con `peekFirst`.
4. Busca el repartidor que pueda transportarlo y tenga menos carga.
5. Saca con `pollFirst`.
6. Asigna y pasa a `EN_CAMINO`.

¿Por qué dos colas? Para atender primero los urgentes sin recorrer toda la lista. Es una prioridad simple.

### Pila de operaciones y “Deshacer”

```java
private Deque<Operacion> pilaOperaciones;
```

Se usa como pila LIFO:

```java
pilaOperaciones.push(operacion);       // agrega arriba
pilaOperaciones.pollFirst();           // saca la última
```

`push` agrega al principio; `pollFirst` saca el primero. Por eso el último que entró es el primero que sale.

`Operacion` guarda:

- el tipo de operación,
- el cliente, repartidor o pedido involucrado,
- el estado anterior si hace falta.

Los métodos `static` crean operaciones:

```java
Operacion.altaCliente(cliente);
Operacion.bajaCliente(cliente);
Operacion.altaRepartidor(repartidor);
Operacion.altaPedido(pedido);
Operacion.asignarPedido(pedido, repartidor);
Operacion.entregarPedido(pedido, repartidor);
Operacion.cambioEstado(pedido, estadoAnterior);
```

`deshacer()` saca la última y hace la operación inversa:

| Operación | Para deshacer |
| --- | --- |
| `ALTA_CLIENTE` | `clientes.remove(codigo)` |
| `BAJA_CLIENTE` | `clientes.put(codigo, cliente)` |
| `ALTA_REPARTIDOR` | `repartidores.remove(repartidor)` |
| `ALTA_PEDIDO` | saca de pedidos, colas y turnos |
| `ASIGNAR_PEDIDO` | saca del repartidor, quita vehículo, vuelve a `PENDIENTE`, encola |
| `ENTREGAR_PEDIDO` | saca del historial, vuelve a `EN_CAMINO` |
| `CAMBIO_ESTADO` | restaura el estado anterior |

¿Por qué una pila? Porque “Deshacer” siempre deshace lo último que se hizo. Es el comportamiento clásico de un editor.

## 4. Preguntas que te pueden hacer

1. **¿Por qué `Vehiculo` es abstracta?** Porque cada tipo calcula el tiempo distinto.
2. **¿Qué hace `super.puedeTransportar(pedido)` en `Bicicleta`?** Reutiliza la validación de capacidad de la clase base.
3. **¿Por qué una bicicleta no puede llevar pedidos frágiles?** Es una regla del negocio, implementada con `instanceof`.
4. **¿Qué es el polimorfismo?** Que un mismo método se comporte distinto según el objeto real.
5. **¿Cómo se calcula el tiempo de una moto?** `(km / velocidad) * 60 + 5`.
6. **¿Cómo se calcula el de una camioneta?** `(km / velocidad) * 60 + 10`.
7. **¿Por qué `Repartidor` usa un contador en E1?** Porque el arreglo tiene espacios vacíos y hay que saber hasta dónde está cargado.
8. **¿Qué pasa si se llena el arreglo de pedidos?** Lanza `AlmacenamientoLlenoException`.
9. **¿Qué pasa si el vehículo no soporta el peso?** Lanza `CapacidadExcedidaException`.
10. **¿Cómo se calcula la comisión?** 10% de los pedidos entregados.
11. **¿Por qué `getPedidos` devuelve una copia?** Para no exponer el arreglo interno.
12. **¿Qué diferencia hay entre `ArrayList` y arreglo?** La lista crece sola; el arreglo tiene tamaño fijo.
13. **¿Qué es una cola FIFO?** El primero que entra es el primero que sale.
14. **¿Qué es una pila LIFO?** El último que entra es el primero que sale.
15. **¿Por qué hay dos colas?** Para priorizar urgentes.
16. **¿Qué hace `offerLast`?** Agrega al final de la cola.
17. **¿Qué hace `pollFirst`?** Saca y devuelve el primero.
18. **¿Qué hace `push`?** Agrega al principio (arriba de la pila).
19. **¿Cómo se deshace un alta de pedido?** Se saca de la lista, de las colas y de los turnos; si tenía vehículo, se saca del repartidor.
20. **¿Qué operación no se genera desde el menú?** `CAMBIO_ESTADO`; está implementada por completitud.

## 5. Ejercicio de práctica

Agregá un método `getCapacidadDisponible(Pedido pedido)` en `Vehiculo` que devuelva `capacidadKg - pedido.getPesoKg()`. Usalo en un `System.out.println` de prueba y explicá:

- ¿Qué pasa si el pedido pesa más que la capacidad?
- ¿Qué tipo devuelve?
- ¿Por qué no hace falta modificar las subclases?

---

# Walter Rodríguez

## 1. Qué tenés que hacer en Git

1. Clonar y configurar tu identidad.
2. Crear tu rama:

```bash
git checkout -b aporte-walter
```

3. Hacer un cambio real. Opciones:

   - Agregar un comentario explicativo en `CityLink` o `Main`.
   - Agregar una validación en el menú.
   - Agregar `docs/aportes/walter.md` con una prueba de los algoritmos.
   - Agregar un método `main` de prueba en `algoritmos` (por ejemplo, `PruebaOrdenamiento.java`).

4. Compilar y probar.
5. `git add .`, `git commit -m "..."`, `git push -u origin aporte-walter`.
6. Abrir Pull Request.

## 2. Archivos que tenés que dominar

- `Proyecto/Entregable1/src/app/CityLink.java`
- `Proyecto/Entregable1/src/app/Main.java`
- `Proyecto/Entregable2/src/app/CityLink.java`
- `Proyecto/Entregable2/src/app/Main.java`
- `Proyecto/Entregable2/src/algoritmos/Busqueda.java`
- `Proyecto/Entregable2/src/algoritmos/Ordenamiento.java`
- `Proyecto/Entregable2/src/algoritmos/Intercalacion.java`

## 3. Explicación de tu parte

### Separación entre `Main` y `CityLink`

`Main` hace tres cosas:

1. Lee por teclado.
2. Llama al gestor.
3. Muestra resultados.

`CityLink` hace la lógica:

- guarda los datos,
- busca,
- valida,
- calcula,
- lanza excepciones.

Esta separación se llama **separación de responsabilidades**. Si mañana se agrega una interfaz gráfica, se puede reutilizar `CityLink` sin cambiar la lógica.

### `CityLink` en E1

#### Arreglos y contadores

```java
private Cliente[] clientes;
private int cantidadClientes;
```

El arreglo tiene tamaño fijo. El contador indica cuántos espacios están ocupados. Los métodos recorren hasta `cantidadClientes`, no hasta `clientes.length`.

¿Por qué? Porque los espacios vacíos contienen `null`. Si se recorre todo, se intenta llamar a métodos sobre `null` y se produce `NullPointerException`.

#### Constantes de capacidad

```java
public static final int CAPACIDAD_CLIENTES = 50;
public static final int CAPACIDAD_PEDIDOS = 100;
public static final int CAPACIDAD_REPARTIDORES = 10;
public static final int CAPACIDAD_VEHICULOS = 30;
```

Son `static final`: constantes de la clase. Se usan para crear los arreglos y para los mensajes de error.

#### Altas

`registrarCliente`, `registrarRepartidor` y `registrarPedido` siguen el mismo patrón:

1. Validar que no sea `null`.
2. Validar que haya lugar.
3. Validar que el código/legajo no esté repetido.
4. Guardar en el arreglo.
5. Incrementar el contador.

En `registrarPedido` además se valida que el cliente exista con `buscarCliente`.

#### Búsquedas

`buscarCliente(int)` y `buscarPedido(int)` recorren y comparan con `==` porque son enteros. `buscarCliente(String)` compara con `equalsIgnoreCase`.

`buscarRepartidor` devuelve `null` si no encuentra; `buscarCliente` y `buscarPedido` lanzan excepción. ¿Por qué la diferencia? Porque en el menú, cuando no se encuentra un repartidor, se muestra un `IllegalArgumentException`; en los otros casos se quiso usar una excepción propia.

#### Eliminación con desplazamiento

```java
for (int i = posicion; i < cantidadClientes - 1; i++) {
    clientes[i] = clientes[i + 1];
}
clientes[cantidadClientes - 1] = null;
cantidadClientes--;
```

Si se elimina el cliente en la posición 2, todos los siguientes se mueven una posición a la izquierda. El último se pone en `null` y se decrementa el contador. Así no quedan huecos.

#### Asignación de pedidos

```java
Pedido pedido = buscarPedido(codigoPedido);
Repartidor repartidor = buscarRepartidor(legajoRepartidor);

if (repartidor == null) {
    throw new IllegalArgumentException(...);
}
if (pedido.getEstado() != EstadoPedido.PENDIENTE) {
    throw new IllegalArgumentException(...);
}

repartidor.asignarPedido(pedido);
```

La validación de capacidad y duplicado queda en `Repartidor`, que es quien conoce su vehículo y sus pedidos.

#### Cambio de estado

```java
if (nuevoEstado == EstadoPedido.ENTREGADO) {
    Repartidor repartidor = buscarRepartidorDePedido(codigoPedido);
    if (repartidor != null) {
        repartidor.registrarEntrega(pedido);
        return;
    }
}
pedido.cambiarEstado(nuevoEstado);
```

Si se marca como entregado, se busca el repartidor para que registre la entrega y cuente la comisión. Si no, se cambia el estado directamente.

#### Listados con polimorfismo

```java
Facturable facturable = pedido;
double costo = facturable.calcularImporte();
```

`Facturable` es una interfaz. La variable puede apuntar a cualquier objeto que la implemente. En este caso, a un `Pedido`.

`listarTiemposDeEntrega` recorre `Vehiculo[]` y llama a `calcularTiempoEntrega`. Cada subclase responde distinto.

#### Totales y comisiones

`calcularCostoTotal` suma los importes de los pedidos asignados. `listarImportesYComisiones` recorre los repartidores como `Facturable` y muestra la comisión.

### `Main` en E1

#### `main`

```java
CityLink sistema = new CityLink();
boolean salir = false;

try {
    while (!salir) {
        mostrarMenu();
        int opcion = leerEntero("Elija una opción: ");

        try {
            switch (opcion) {
                ...
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println("El sistema sigue funcionando.");
        }
    }
} finally {
    System.out.println("Cerrando CityLink. ¡Hasta luego!");
    teclado.close();
}
```

- El `while (!salir)` mantiene el menú hasta que el usuario elige 10.
- El `try/catch` interno evita que un error cierre el programa.
- El `finally` se ejecuta siempre y cierra el `Scanner`.
- `switch` elige la opción.
- `break` sale del `switch`, no del `while`.

#### Lectura por teclado

```java
private static int leerEntero(String mensaje) {
    while (true) {
        System.out.print(mensaje);
        String linea = teclado.nextLine();
        try {
            return Integer.parseInt(linea.trim());
        } catch (NumberFormatException e) {
            System.out.println("Eso no es un número entero. Probá de nuevo.");
        }
    }
}
```

- `Scanner.nextLine()` lee una línea completa.
- `Integer.parseInt` convierte texto a entero.
- Si el texto no es número, `NumberFormatException` y el `while` vuelve a pedir.
- `leerDouble` hace lo mismo con `Double.parseDouble` y acepta coma o punto.

### `CityLink` en E2

#### Estructuras

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

Ya no hay contadores: las listas saben su tamaño con `size()`, los mapas con `size()` y las colas con `isEmpty()`.

#### Registrar pedido y encolar

```java
pedidos.add(pedido);
encolarPedido(pedido);

if (turno == 1) {
    turnoManana.add(pedido);
} else {
    turnoTarde.add(pedido);
}
```

`encolarPedido` separa urgentes de normales.

#### Atender siguiente

El gestor elige la cola, busca el repartidor con menos carga y asigna. Es la operación que conecta colas, repartidores y pedidos.

#### Deshacer

La pila guarda `Operacion`. Cada operación sabe cómo revertirse. Es el patrón “deshacer” clásico.

### Algoritmos

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

Ventaja: funciona con cualquier lista, ordenada o no. Desventaja: en el peor caso recorre todo. Complejidad **O(n)**.

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

Requiere lista ordenada. En cada vuelta descarta la mitad. Complejidad **O(log n)**.

La versión recursiva:

```java
private static Pedido buscarRecursivo(List<Pedido> pedidos, int codigo, int inicio, int fin) {
    if (inicio > fin) {
        return null;
    }

    int medio = (inicio + fin) / 2;
    int codigoMedio = pedidos.get(medio).getCodigo();

    if (codigoMedio == codigo) {
        return pedidos.get(medio);
    } else if (codigoMedio < codigo) {
        return buscarRecursivo(pedidos, codigo, medio + 1, fin);
    } else {
        return buscarRecursivo(pedidos, codigo, inicio, medio - 1);
    }
}
```

El caso base es `inicio > fin`: si el rango se cruza, no está.

#### Burbuja

Compara pares vecinos y los intercambia. En cada pasada, el mayor queda al final. **O(n²)**.

#### Inserción

Toma cada elemento y lo inserta en la parte ya ordenada. **O(n²)**, pero eficiente si la lista está casi ordenada.

#### Quicksort

Elige un pivote, particiona y ordena cada lado. Promedio **O(n log n)**, peor caso **O(n²)**.

#### Mergesort

Divide, ordena y mezcla. Siempre **O(n log n)**, pero usa memoria extra.

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

Recibe dos listas ya ordenadas y las combina. **O(n + m)**.

### `Comparator`

```java
Comparator<Pedido> porDistancia = new Comparator<Pedido>() {
    @Override
    public int compare(Pedido a, Pedido b) {
        return Double.compare(a.getDistanciaKm(), b.getDistanciaKm());
    }
};
```

`compare` devuelve negativo, cero o positivo. `Double.compare` evita problemas de redondeo.

En `CityLink.obtenerComparador` hay tres comparadores:

- costo,
- distancia,
- fecha.

El de costo usa `costoSeguro`, que devuelve 0 si el pedido no tiene vehículo.

## 4. Preguntas que te pueden hacer

1. **¿Por qué `Main` no tiene lógica de negocio?** Para separar la entrada/salida de las reglas del sistema.
2. **¿Por qué se usan contadores en E1?** Porque los arreglos tienen tamaño fijo y espacios vacíos.
3. **¿Qué pasa si se recorre todo el arreglo en vez de hasta `cantidad`?** Se encuentran `null` y se produce `NullPointerException`.
4. **¿Cómo se elimina un elemento de un arreglo?** Se desplazan los siguientes y se decrementa el contador.
5. **¿Qué hace `try/catch` en el menú?** Evita que un error cierre el programa.
6. **¿Qué hace `finally`?** Se ejecuta siempre; acá cierra el `Scanner`.
7. **¿Qué diferencia hay entre `break` y `return`?** `break` corta el `switch` o bucle; `return` termina el método.
8. **¿Cómo se lee un entero controlando errores?** `Integer.parseInt` dentro de `try/catch`.
9. **¿Qué es una excepción checked?** La que el compilador obliga a manejar o declarar.
10. **¿Por qué E2 usa `TreeMap`?** Para clientes ordenados por código y búsqueda directa.
11. **¿Qué diferencia hay entre `ArrayList` y `TreeMap`?** La lista guarda elementos por posición; el mapa guarda pares clave-valor.
12. **¿Qué hace `Comparator`?** Define el criterio de ordenamiento.
13. **¿Qué complejidad tiene la búsqueda lineal?** O(n).
14. **¿Y la binaria?** O(log n), pero requiere orden.
15. **¿Qué complejidad tiene burbuja?** O(n²).
16. **¿Y mergesort?** O(n log n).
17. **¿Cuándo conviene inserción?** Cuando la lista está casi ordenada.
18. **¿Qué hace la intercalación?** Combina dos listas ordenadas en una sola.
19. **¿Por qué la búsqueda binaria arma una copia?** Para no cambiar el orden original de los pedidos.
20. **¿Qué hace `costoSeguro`?** Devuelve 0 si el pedido no tiene vehículo, para poder ordenar sin excepción.

## 5. Ejercicio de práctica

Agregá un criterio de ordenamiento por peso en `obtenerComparador` y probalo con los cuatro algoritmos. Explicá:

- ¿Qué comparador agregaste?
- ¿Qué pasa con los pedidos que tienen el mismo peso?
- ¿Por qué no hace falta cambiar los algoritmos?

---

# Álvaro Chirino

## 1. Qué tenés que hacer en Git

1. Clonar y configurar tu identidad.
2. Crear tu rama:

```bash
git checkout -b aporte-alvaro
```

3. Hacer un cambio real. Opciones:

   - Mejorar un README con resultados de pruebas.
   - Agregar `docs/CHECKLIST-ENTREGA.md`.
   - Actualizar el diagrama de clases.
   - Agregar `docs/aportes/alvaro.md` con la matriz de pruebas.

4. Compilar y probar.
5. `git add .`, `git commit -m "..."`, `git push -u origin aporte-alvaro`.
6. Abrir Pull Request.

## 2. Archivos que tenés que dominar

- `README.md` de la raíz.
- `Proyecto/Entregable1/README.md`.
- `Proyecto/Entregable2/README.md`.
- `Proyecto/Entregable1/docs/diagrama-clases.svg` y `.png`.
- `Proyecto/Entregable2/docs/diagrama-clases.svg` y `.png`.
- `Proyecto/Entregable2/src/app/Main.java` (menús).
- Los datos precargados de `CityLink` en E1 y E2.

## 3. Explicación de tu parte

### Documentación

El README no es decoración: es la puerta de entrada del proyecto. Tiene que explicar:

- quiénes son los integrantes,
- qué hace el sistema,
- cómo se compila y se ejecuta,
- qué estructuras se usan,
- qué algoritmos se implementaron,
- qué excepciones hay,
- cómo se prueba,
- qué uso se le dio a la IA.

Si un docente entra al repo y no puede ejecutar el proyecto en cinco minutos, el README falló.

### Diagrama de clases

El diagrama muestra:

- clases,
- atributos y métodos principales,
- relaciones de herencia (`extends`),
- interfaces (`implements`),
- relaciones de composición (una clase tiene objetos de otra).

Cómo leerlo:

- Una flecha con triángulo hueco indica herencia.
- Una línea con “implements” indica interfaz.
- Una relación `1..*` indica que una clase tiene muchos objetos de otra.

En CityLink:

- `Pedido` es abstracta y tiene subclases `PedidoNormal`, `PedidoUrgente` y `PedidoFragil`.
- `Vehiculo` es abstracta y tiene subclases `Bicicleta`, `Moto` y `Camioneta`.
- `Repartidor` tiene un `Vehiculo` y muchos `Pedido`.
- `CityLink` tiene clientes, pedidos, repartidores y vehículos.

### Pruebas

Una prueba no es solo “ejecutar y ver que no explota”. Hay que probar:

1. **Camino feliz**: datos válidos, todo funciona.
2. **Bordes**: listas vacías, capacidad llena, primer y último elemento.
3. **Errores**: datos inválidos, códigos repetidos, cliente inexistente.
4. **Deshacer**: cada operación vuelve al estado anterior.
5. **Algoritmos**: listas vacías, un elemento, ya ordenadas, desordenadas.

### Matriz de pruebas sugerida

| Caso | Entrada | Resultado esperado |
| --- | --- | --- |
| Alta de cliente | código 6, datos válidos | aparece en el listado |
| Cliente repetido | código 1 | error, no se agrega |
| Baja de cliente | código 5 | se elimina |
| Deshacer baja | opción 6 | vuelve el cliente 5 |
| Pedido con cliente inexistente | código 99 | error |
| Atender sin pedidos | colas vacías | `SinPedidosPendientesException` |
| Entrega de pedido pendiente | pedido 4 | error de estado |
| Entrega de pedido en camino | pedido 3 | pasa a entregado |
| Comisión | pedidos 1 y 2 entregados | Luis $125, María $294, Jorge $0 |
| Búsqueda lineal | código 8 | encuentra el pedido |
| Búsqueda binaria | código 8 | encuentra el pedido |
| Búsqueda inexistente | código 99 | error |
| Ordenar por costo | cualquiera | orden ascendente |
| Intercalar turnos | pedidos en ambos turnos | lista combinada ordenada |

### Datos precargados

E1 y E2 cargan lo mismo:

- 5 clientes,
- 3 repartidores,
- 8 pedidos.

En E1:

- pedidos 1 y 2 entregados,
- pedido 3 en camino,
- resto pendientes.

En E2:

- pedidos 1 y 2 entregados,
- pedido 3 en camino,
- pedidos 4 a 8 pendientes,
- pedidos 5 y 8 son urgentes y están en la cola de urgentes.

Las cuentas están en `docs/RECORRIDO-DEL-CODIGO.md`. Lo importante es que puedas explicar de dónde salen $23230, $125, $294 y $0.

### Menús

El menú de E1 tiene 10 opciones. El de E2 también, pero cambia:

- E1: registrar cliente/repartidor/pedido, asignar, cambiar estado, buscar, listar, consultar, comisiones.
- E2: gestión de clientes, gestión de repartidores, registrar pedido, atender siguiente, entregar, deshacer, ordenar, buscar, intercalar.

Los submenús de E2 (clientes y repartidores) usan `while (!volver)` y una opción `0` para volver. Eso evita salir del programa sin querer.

### Integración

El flujo general es:

```
Main -> CityLink -> modelo
                 -> excepciones
                 -> algoritmos
```

`Main` lee y muestra. `CityLink` coordina. `modelo` representa los datos. `excepciones` describe los errores. `algoritmos` resuelve búsquedas y ordenamientos.

## 4. Preguntas que te pueden hacer

1. **¿Cómo se compila el proyecto?** `javac -encoding UTF-8 -d out $(find src -name "*.java")`.
2. **¿Cómo se ejecuta?** `java -cp out app.Main`.
3. **¿Qué significa `-d out`?** Guarda los `.class` en `out`.
4. **¿Qué significa `-cp out`?** Le dice a Java dónde buscar las clases.
5. **¿Qué muestra el diagrama?** Clases, atributos, métodos, herencia, interfaces y relaciones.
6. **¿Qué relación hay entre `Repartidor` y `Vehiculo`?** Un repartidor tiene un vehículo.
7. **¿Qué relación hay entre `Pedido` y `Cliente`?** Un pedido tiene un cliente.
8. **¿Qué pasa si se llena la capacidad?** Se lanza `AlmacenamientoLlenoException`.
9. **¿Qué pasa si se intenta entregar un pedido pendiente?** `EstadoInvalidoException`.
10. **¿Cómo se prueba “Deshacer”?** Se hace una operación, se elige 6 y se verifica que volvió.
11. **¿Qué datos se precargan?** 5 clientes, 3 repartidores y 8 pedidos.
12. **¿De dónde sale el total $23230?** De la suma de los costos de los 8 pedidos del E1.
13. **¿Por qué Luis gana $125 y María $294?** Porque solo se cuenta el 10% de los pedidos entregados.
14. **¿Por qué Jorge gana $0?** Porque no tiene entregas.
15. **¿Qué diferencia hay entre el README y el diagrama?** El README explica el proyecto; el diagrama muestra la estructura de clases.
16. **¿Qué es una prueba de borde?** Una prueba con el límite: lista vacía, un elemento, capacidad llena.
17. **¿Qué es una prueba de error?** Una prueba con datos inválidos para ver si el sistema responde bien.
18. **¿Qué se debería mejorar en la documentación?** Agregar capturas, resultados de pruebas y una guía de solución de problemas.
19. **¿Por qué el README tiene la declaración de IA?** Porque el enunciado lo exige: qué herramienta, para qué y cómo se verificó.
20. **¿Qué tiene que ver el diagrama con el código?** Tiene que reflejar las clases y relaciones reales; si el código cambia, el diagrama se actualiza.

## 5. Ejercicio de práctica

Tomá la clase `Pedido` y dibujá en papel:

- sus atributos,
- sus métodos,
- su relación con `Cliente` y `Vehiculo`,
- sus subclases.

Después compará con el diagrama del repo y corregí lo que falte.

---

# Erebothh

Erebothh es el dueño del repositorio. No necesita un commit de “aporte” porque ya hizo la subida inicial. Su parte es la integración, la revisión y la defensa general.

## 1. Tareas de administración del repo

### Agregar colaboradores

Desde GitHub:

1. Entrar al repositorio.
2. `Settings` → `Collaborators`.
3. `Add people`.
4. Buscar por usuario o email.
5. Elegir el permiso `Write` o `Maintain`.

También se puede hacer con `gh`:

```bash
gh api repos/Erebothh/Prog-1/collaborators/thiago-sturzenegger -X PUT -f permission=push
```

(Reemplazar por el usuario real de cada uno.)

### Revisar un Pull Request

1. Entrar a `Pull requests`.
2. Abrir el PR.
3. Leer `Files changed`.
4. Verificar que no se suban `out/`, `.class` ni archivos basura.
5. Bajar la rama y compilar:

```bash
git fetch origin
git checkout aporte-thiago
javac -encoding UTF-8 -d out $(find src -name "*.java")
```

6. Probar el cambio.
7. Si está bien, `Merge pull request`.
8. Borrar la rama remota si ya no se usa.

### Mantener `main` actualizado

```bash
git checkout main
git pull origin main
```

### Ver commits de todos

```bash
git log --format="%h %an %s" --all
```

O en GitHub: `Insights` → `Contributors`.

### Si alguien rompe `main`

Se puede revertir un commit:

```bash
git revert <hash>
git push origin main
```

`git revert` crea un commit nuevo que deshace los cambios. Es más seguro que `git reset` cuando el commit ya está subido.

## 2. Explicación de la arquitectura general

### Capas

```
Main
  ↓
CityLink
  ↓
modelo (Cliente, Pedido, Vehiculo, Repartidor)
  ↓
excepciones

algoritmos (E2) ← CityLink
```

- **Main**: entrada y salida. No guarda datos.
- **CityLink**: gestor. Guarda las estructuras y coordina las operaciones.
- **modelo**: clases del negocio. No saben nada del menú.
- **excepciones**: errores propios.
- **algoritmos**: búsquedas, ordenamientos e intercalación.

### Por qué está separado así

Si todo estuviera en `Main`, el código sería un bloque gigante difícil de probar y explicar. Separando:

- se puede probar `CityLink` sin usar el teclado;
- se puede cambiar el menú sin tocar la lógica;
- se puede reutilizar el modelo en otro programa;
- cada clase tiene una responsabilidad clara.

### E1 versus E2

E1:

- arreglos,
- contadores,
- búsqueda lineal,
- sin colas,
- sin deshacer.

E2:

- `TreeMap` para clientes,
- `ArrayList` para pedidos y repartidores,
- `ArrayDeque` para colas y pila,
- algoritmos de búsqueda y ordenamiento,
- intercalación,
- deshacer.

¿Por qué el cambio? Porque el E2 pide estructuras dinámicas y algoritmos. Además, las colas y la pila permiten operaciones que con arreglos serían más incómodas.

### Flujo de una operación

Ejemplo: “atender el siguiente pedido”.

```
Main
  -> CityLink.atenderSiguientePedido()
       -> elige colaUrgentes o colaNormales
       -> peekFirst()
       -> busca repartidor con puedeTransportar y menos carga
       -> pollFirst()
       -> repartidor.asignarPedido(pedido)
       -> pedido.cambiarEstado(EN_CAMINO)
       -> pilaOperaciones.push(Operacion.asignarPedido(...))
```

Ejemplo: “deshacer”.

```
Main
  -> CityLink.deshacer()
       -> pilaOperaciones.pollFirst()
       -> switch (tipo)
            ALTA_CLIENTE -> clientes.remove(...)
            BAJA_CLIENTE -> clientes.put(...)
            ...
```

### Cómo explicar cualquier parte si te preguntan

Usá siempre este orden:

1. **Qué es**: “Esta clase representa...”
2. **Qué guarda**: atributos principales.
3. **Cómo se construye**: constructor y validaciones.
4. **Qué hace**: métodos importantes.
5. **Con quién se relaciona**: herencia, interfaces, composición.
6. **Qué errores maneja**: excepciones.
7. **Dónde se usa**: menú, gestor, listados.

Si no sabés una línea exacta, no inventes. Decí: “Esa parte la implementó el grupo para tal cosa; la puedo ubicar en el flujo general”. Y explicá el contexto.

## 3. Preguntas que te pueden hacer

1. **¿Por qué el repo tiene esta estructura?** Para separar TP0, TP1 y los dos entregables.
2. **¿Por qué hay dos entregables?** Porque el proyecto pide una evolución: primero arreglos, después estructuras dinámicas.
3. **¿Qué se reutiliza entre E1 y E2?** El modelo, las interfaces, las excepciones y la separación Main/CityLink.
4. **¿Qué cambia?** Las estructuras de datos, las colas, la pila, los algoritmos y el menú.
5. **¿Por qué `Main` no tiene la lógica?** Para separar entrada/salida de negocio.
6. **¿Por qué `CityLink` es el centro?** Porque coordina clientes, pedidos, repartidores y vehículos.
7. **¿Cómo se compila todo?** Con `javac` y `find`, como está en el README.
8. **¿Cómo se verifica que anda?** Compilando y corriendo los menús; además están las pruebas documentadas.
9. **¿Qué hacés si un PR rompe `main`?** `git revert` del commit y push.
10. **¿Cómo se agrega un colaborador?** Settings → Collaborators, o `gh api`.
11. **¿Cómo se ve que todos contribuyeron?** Con `git log --format="%an"` o `Insights → Contributors`.
12. **¿Qué pasa si alguien no puede explicar su parte?** Es un riesgo para la nota; por eso están las guías y las pruebas.
13. **¿Qué mejorarías del repo?** Pruebas automáticas, separar la precarga, agregar CI, capturas.
14. **¿Por qué el README declara la IA?** Porque el enunciado lo exige y porque es honesto.
15. **¿Qué herramienta se usó?** OpenCode con el modelo Big Pickle.
16. **¿Para qué se usó?** Para ordenar ideas, revisar compilación, proponer comentarios y contrastar pruebas.
17. **¿Cómo se verificó?** Compilando, ejecutando los menús y revisando los resultados.
18. **¿Qué es lo más importante para la exposición?** Poder seguir un caso de uso de punta a punta y explicar por qué cada clase está ahí.

## 4. Checklist antes de la entrega

- [ ] Todos los integrantes están en el README.
- [ ] El repo es público o el docente tiene acceso.
- [ ] Hay commits de los cuatro integrantes.
- [ ] `main` compila.
- [ ] Los dos entregables corren.
- [ ] Los READMEs tienen instrucciones claras.
- [ ] La declaración de IA está completa.
- [ ] Los diagramas están actualizados.
- [ ] No hay `out/`, `.class` ni archivos basura.
- [ ] Cada integrante puede explicar su parte y el flujo general.
- [ ] Las pruebas del README se corrieron.
- [ ] Hay un plan B si falla la demo (capturas o video).

---

# Plan de estudio sugerido

## Día 1: Java y TP0/TP1

- Leer `docs/GUIA-JAVA.md` completo.
- Correr TP0 y TP0-extras.
- Leer `Persona`/`Alumno`, `Empleado`/subclases y `Vehiculo`/subclases.
- Explicar en voz alta: encapsulamiento, herencia, abstracto, polimorfismo.

## Día 2: Entregable 1

- Leer `RECORRIDO-DEL-CODIGO.md`, sección E1.
- Correr el menú y probar las 10 opciones.
- Calcular a mano los costos y comisiones.
- Explicar el flujo “registrar pedido → asignar → cambiar estado → listar”.

## Día 3: Entregable 2

- Leer la sección E2.
- Probar colas, pila, deshacer, ordenamientos, búsquedas e intercalación.
- Explicar las diferencias con E1.
- Repasar complejidades.

## Día 4: Repaso y exposición

- Cada uno explica su parte en 5 minutos.
- Otro le hace preguntas.
- Se corrige lo que no se sepa.
- Se prueba la demo completa.

---

# Consejos para la exposición

- Empezá con el diagrama general: “CityLink tiene un gestor, un modelo, excepciones y algoritmos”.
- Después seguí un caso de uso concreto.
- No leas el código línea por línea; explicá qué hace y por qué.
- Si te preguntan por una palabra reservada, decí qué significa y mostrá un ejemplo del proyecto.
- Si no sabés algo, no inventes: ubicá la clase y el flujo, y ofrecé buscarlo.
- Tené el proyecto compilado y probado antes de la demo.
- Llevá capturas de pantalla por si falla la máquina.
- Cada uno tiene que poder explicar cualquier parte, no solo la suya.
