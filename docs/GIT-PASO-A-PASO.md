# Git paso a paso

Esta guía es para que cada integrante pueda clonar el repositorio, hacer un cambio real, subirlo y abrir un Pull Request sin romper nada.

Si nunca usaste Git, no empieces por los comandos: leé primero la parte de conceptos y después seguí los pasos en orden.

---

## 1. Qué es Git y qué es GitHub

- **Git** es el programa que guarda el historial de cambios de un proyecto.
- **GitHub** es el sitio donde se sube ese historial para compartirlo.
- **Repositorio** es la carpeta del proyecto con su historial.
- **Commit** es una foto de los cambios, con un mensaje y un autor.
- **Rama** es una línea de trabajo separada. Sirve para probar cambios sin tocar `main`.
- **`main`** es la rama principal. Es la que se entrega.
- **Pull Request (PR)** es un pedido para mezclar una rama con `main`.
- **Remote** es el repositorio en GitHub. Normalmente se llama `origin`.
- **Clonar** es descargar una copia del repositorio con su historial.

El repositorio del grupo es:

```text
https://github.com/Erebothh/Prog-1
```

---

## 2. Instalar Git

### Linux (Debian/Ubuntu)

```bash
sudo apt update
sudo apt install git
```

### Windows

Descargar desde <https://git-scm.com/download/win> e instalar con las opciones por defecto.

### macOS

```bash
git --version
```

Si no está instalado, el sistema va a ofrecer instalarlo. También se puede instalar con Homebrew:

```bash
brew install git
```

### Verificar

```bash
git --version
```

Tiene que mostrar algo como `git version 2.x.x`.

---

## 3. Configurar la identidad

Git necesita saber quién hizo cada commit. Se configura una sola vez por computadora:

```bash
git config --global user.name "Thiago Sturzenegger"
git config --global user.email "tu-email-de-github@ejemplo.com"
```

Reglas:

- Usar el mismo email que en la cuenta de GitHub, o uno verificado en la cuenta.
- El nombre puede ser el real.
- No usar el nombre de otra persona.
- Si ya estaba configurado mal, se vuelve a ejecutar el comando con el valor correcto.

Para ver la configuración actual:

```bash
git config --global --list
```

Para verificar solamente nombre y email:

```bash
git config --global user.name
git config --global user.email
```

Si querés configurarlo solo para este repositorio (no para toda la computadora), entrá a la carpeta y usá los mismos comandos sin `--global`.

---

## 4. Autenticación con GitHub

Hay dos formas comunes:

### Opción A: GitHub CLI (más fácil)

Instalar `gh` y después:

```bash
gh auth login
```

Elegir:

1. `GitHub.com`.
2. `HTTPS`.
3. `Login with a web browser`.
4. Copiar el código que muestra y pegarlo en el navegador.
5. Autorizar.

Verificar:

```bash
gh auth status
```

### Opción B: token personal

GitHub ya no acepta la contraseña normal por HTTPS. Hay que crear un **Personal Access Token**:

1. GitHub → foto de perfil → `Settings`.
2. `Developer settings`.
3. `Personal access tokens` → `Tokens (classic)`.
4. `Generate new token`.
5. Elegir un nombre, por ejemplo `Prog-1`.
6. Marcar el permiso `repo`.
7. Generar y copiar el token.

Después, cuando Git pida contraseña, pegar el token. No la contraseña de GitHub.

**Importante:** el token es como una contraseña. No se sube al repositorio, no se pega en el código y no se comparte.

### Opción C: SSH

Si ya tenés una clave SSH cargada en GitHub, podés usar la URL SSH:

```bash
git clone git@github.com:Erebothh/Prog-1.git
```

Si no sabés cómo hacerlo, usá HTTPS con `gh` o con token.

---

## 5. Clonar el repositorio

Elegir una carpeta de trabajo y ejecutar:

```bash
git clone https://github.com/Erebothh/Prog-1.git
cd Prog-1
```

Queda una carpeta `Prog-1` con todo el proyecto y la carpeta oculta `.git` con el historial.

Para ver el estado:

```bash
git status
```

Para ver las ramas:

```bash
git branch
```

Para ver el remote:

```bash
git remote -v
```

Tiene que aparecer `origin` apuntando a GitHub.

### Si el repositorio ya estaba clonado

No hace falta clonar de nuevo. Entrá a la carpeta y actualizá:

```bash
git checkout main
git pull origin main
```

---

## 6. Crear una rama propia

Nunca trabajar directamente sobre `main`. Cada integrante crea su rama:

```bash
git checkout -b aporte-thiago
```

Otros ejemplos:

```bash
git checkout -b aporte-angel
git checkout -b aporte-walter
git checkout -b aporte-alvaro
```

`-b` crea la rama y se mueve a ella.

Para ver en qué rama estás:

```bash
git branch
```

La rama actual tiene un `*` al lado.

Para volver a `main`:

```bash
git checkout main
```

Para volver a tu rama:

```bash
git checkout aporte-thiago
```

---

## 7. Hacer un cambio real

Un cambio real es un aporte que se entiende y se puede explicar. No alcanza con agregar un espacio o cambiar una mayúscula.

Opciones válidas:

- Agregar una validación que falte.
- Agregar un método chico y usarlo.
- Mejorar un comentario para que explique mejor una decisión.
- Agregar un archivo en `docs/aportes/` con pruebas y conclusiones.
- Corregir un mensaje de error confuso.
- Agregar un caso de prueba documentado.

Antes de empezar, actualizá tu rama con lo último de `main`:

```bash
git checkout main
git pull origin main
git checkout aporte-thiago
git merge main
```

Si no hay conflictos, sigue. Si hay conflictos, están explicados más abajo.

### Ver qué cambiaste

```bash
git status
```

- **Untracked**: archivos nuevos que Git todavía no sigue.
- **Modified**: archivos que ya existían y cambiaron.
- **Staged**: archivos agregados al próximo commit.

Para ver el detalle:

```bash
git diff
```

Para ver solo los nombres:

```bash
git diff --name-only
```

Para ver un archivo específico:

```bash
git diff Proyecto/Entregable1/src/modelo/Cliente.java
```

---

## 8. Compilar antes de commitear

Nunca se commitea algo que no compila. Desde la carpeta del práctico:

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

En Windows con PowerShell, `$(find ...)` no funciona. Se puede compilar así:

```powershell
javac -encoding UTF-8 -d out (Get-ChildItem -Recurse -Filter *.java src | ForEach-Object { $_.FullName })
java -cp out app.Main
```

Si algo falla:

1. Leer el error completo.
2. Revisar la línea que indica.
3. Corregir.
4. Volver a compilar.
5. Recién ahí commitear.

---

## 9. Agregar archivos y commitear

Para agregar todo lo que cambió:

```bash
git add .
```

Para agregar solo un archivo:

```bash
git add Proyecto/Entregable1/src/modelo/Cliente.java
```

Para ver qué quedó en el área de preparación:

```bash
git status
```

Para confirmar el commit:

```bash
git commit -m "Agrego validación de teléfono en Cliente"
```

El mensaje tiene que ser corto, en presente y explicar qué se hizo. Ejemplos:

- `Agrego validación de patente en Vehiculo`
- `Corrijo el cálculo de la comisión`
- `Agrego pruebas de las colas en docs/aportes/angel.md`
- `Mejoro los comentarios de Pedido`

Malos mensajes:

- `cambios`
- `asd`
- `update`
- `lo que hice`

Para ver el historial:

```bash
git log --oneline
```

Para ver el último commit con detalle:

```bash
git show
```

---

## 10. Subir la rama

La primera vez:

```bash
git push -u origin aporte-thiago
```

`-u` deja configurada la rama remota. Las próximas veces alcanza con:

```bash
git push
```

Si Git pide usuario y contraseña, usar el token, no la contraseña de GitHub.

---

## 11. Abrir un Pull Request

1. Entrar a <https://github.com/Erebothh/Prog-1>.
2. GitHub va a mostrar un cartel con la rama recién subida y un botón `Compare & pull request`.
3. Si no aparece, ir a `Pull requests` → `New pull request`.
4. Elegir:
   - `base`: `main`.
   - `compare`: `aporte-thiago`.
5. Poner un título claro.
6. En la descripción explicar:
   - qué cambió,
   - por qué,
   - cómo se probó.
7. Crear el PR.
8. Avisarle a Erebothh para que lo revise.

No hacer `Merge` sin que otro lo revise. La idea es que el PR tenga aunque sea una revisión.

### Qué revisa Erebothh

- Que el cambio tenga sentido.
- Que no se suban `out/`, `.class`, archivos basura ni tokens.
- Que compile.
- Que no rompa `main`.
- Que el mensaje del commit sea claro.
- Que el integrante pueda explicar lo que hizo.

---

## 12. Actualizar `main` después del merge

Cuando el PR se mezcla, `main` cambia. Para traer esos cambios a tu computadora:

```bash
git checkout main
git pull origin main
```

Si vas a seguir trabajando, creá una rama nueva o actualizá la tuya:

```bash
git checkout aporte-thiago
git merge main
```

---

## 13. Conflictos

Un conflicto pasa cuando dos personas cambiaron la misma parte del mismo archivo. Git no adivina cuál versión vale.

Git avisa:

```text
CONFLICT (content): Merge conflict in Proyecto/Entregable1/src/modelo/Cliente.java
```

Abrir el archivo. Vas a ver algo así:

```java
<<<<<<< HEAD
this.nombre = nombre.trim();
=======
this.nombre = nombre;
>>>>>>> main
```

Significa:

- `HEAD`: tu versión.
- `main`: la versión que trajiste.

Hay que decidir qué queda. Se borran las marcas `<<<<<<<`, `=======`, `>>>>>>>` y se deja el código correcto. Después:

```bash
git add Proyecto/Entregable1/src/modelo/Cliente.java
git commit -m "Resuelvo conflicto en Cliente"
```

Recomendaciones:

- No resolver un conflicto sin entender el código.
- Si no sabés, preguntar antes de commitear.
- Después de resolver, compilar y probar.
- Si el conflicto es grande, mejor hablarlo con el grupo.

Para cancelar un merge que se complicó:

```bash
git merge --abort
```

---

## 14. Comandos de emergencia

### Ver el estado

```bash
git status
```

Es el comando que más se usa. No modifica nada.

### Ver diferencias

```bash
git diff
```

### Descartar cambios de un archivo (cuidado)

```bash
git checkout -- Proyecto/Entregable1/src/modelo/Cliente.java
```

Borra los cambios locales de ese archivo. No se puede recuperar.

### Guardar cambios sin commitear

```bash
git stash
```

Para recuperarlos:

```bash
git stash pop
```

### Ver quién cambió cada línea

```bash
git blame Proyecto/Entregable1/src/modelo/Cliente.java
```

### Ver commits de todos

```bash
git log --format="%h %an %s" --all
```

### Ver solo los autores

```bash
git log --format="%an" | sort | uniq -c
```

### Deshacer el último commit pero conservar los cambios

```bash
git reset --soft HEAD~1
```

Los cambios vuelven al área de preparación. No borra el código.

### Revertir un commit ya subido

```bash
git revert <hash>
git push origin main
```

`git revert` crea un commit nuevo que deshace el anterior. Es la forma segura cuando el commit ya está en GitHub.

**Nunca usar `git push --force` sobre `main`.** Puede borrar el trabajo de otros.

---

## 15. Reglas del repositorio

- No subir `out/` ni `*.class`.
- No subir tokens, contraseñas ni datos personales.
- No commitear directo sobre `main`.
- Un PR por aporte.
- Compilar antes de cada commit.
- Mensajes de commit claros.
- Revisar el PR de otro antes de mezclarlo.
- Si algo no compila, no se sube.
- Si no se entiende un cambio, no se sube.
- Cada integrante tiene que poder explicar su cambio y el flujo general.

---

## 16. Checklist antes de abrir el PR

- [ ] Estoy en mi rama, no en `main`.
- [ ] Actualicé mi rama con `main`.
- [ ] El cambio es real y se puede explicar.
- [ ] Compila.
- [ ] Probé lo que cambié.
- [ ] No subí `out/` ni `.class`.
- [ ] No subí tokens ni datos personales.
- [ ] El mensaje de commit es claro.
- [ ] El PR tiene título y descripción.
- [ ] Avisé a Erebothh.

---

## 17. Si algo sale mal

### `fatal: not a git repository`

Estás en la carpeta equivocada. Entrá a la carpeta del repositorio:

```bash
cd Prog-1
```

### `Permission denied (publickey)`

Estás usando SSH sin clave configurada. Cambiar a HTTPS:

```bash
git remote set-url origin https://github.com/Erebothh/Prog-1.git
```

### `Authentication failed`

Usar un token personal, no la contraseña de GitHub. O iniciar sesión con:

```bash
gh auth login
```

### `Updates were rejected because the remote contains work...`

Falta traer los cambios remotos:

```bash
git pull origin main
```

Si estás en una rama:

```bash
git pull origin aporte-thiago
```

### `Your local changes would be overwritten`

Hay cambios sin commitear. Opciones:

```bash
git stash
git pull
git stash pop
```

O commitear antes de actualizar.

### `src refspec ... does not match any`

El nombre de la rama está mal escrito o no existe. Verificar con:

```bash
git branch
```

### `error: pathspec ... did not match any file(s) known to git`

El archivo no existe en esa ruta o no está agregado. Verificar con:

```bash
git status
```

### `bad object HEAD`

El repositorio quedó en un estado raro. No hacer cosas al azar. Guardar una copia de la carpeta y pedir ayuda.

---

## 18. Alternativa sin consola: GitHub Desktop

Si los comandos resultan incómodos, se puede usar **GitHub Desktop**:

1. Descargar desde <https://desktop.github.com/>.
2. Iniciar sesión con la cuenta de GitHub.
3. `File` → `Clone repository` → elegir `Erebothh/Prog-1`.
4. Crear una rama desde el botón `Current branch`.
5. Editar los archivos con el editor que prefieras.
6. Ver los cambios en la pestaña `Changes`.
7. Escribir el mensaje y hacer `Commit`.
8. `Push origin`.
9. Abrir el PR desde GitHub.

Igual conviene entender los comandos, porque en la exposición pueden preguntar cómo se subió el trabajo.

---

## 19. Cómo se ve que todos contribuyeron

En GitHub:

1. Entrar al repositorio.
2. Ir a `Insights`.
3. Entrar a `Contributors`.

También se puede ver desde la consola:

```bash
git log --format="%an" | sort | uniq -c
```

Tiene que aparecer el nombre de los cuatro integrantes. Si falta alguien, esa persona todavía no hizo un commit con su identidad configurada.

---

## 20. Resumen de comandos

```bash
# Clonar
git clone https://github.com/Erebothh/Prog-1.git
cd Prog-1

# Configurar identidad
git config --global user.name "Nombre Apellido"
git config --global user.email "email@ejemplo.com"

# Actualizar main
git checkout main
git pull origin main

# Crear rama
git checkout -b aporte-nombre

# Ver cambios
git status
git diff

# Compilar y probar
javac -encoding UTF-8 -d out $(find src -name "*.java")
java -cp out app.Main

# Commitear
git add .
git commit -m "Mensaje claro"

# Subir
git push -u origin aporte-nombre

# Abrir PR en GitHub y avisar a Erebothh
```
