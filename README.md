# SGHLU - Sistema de Gestión de Horas Libres UNAB (Versión Extendida)

Aplicación Java con interfaz gráfica para la gestión de eventos y acumulación de horas libres para estudiantes de la Universidad Autónoma de Bucaramanga (UNAB).

## ✨ Funcionalidades principales

- Registro de estudiante (nombre y código)
- Listado de eventos cargados desde archivo `eventos.csv`
- Inscripción a eventos con suma de horas
- Registro automático de inscripciones
- Persistencia en archivos CSV (`resources/`)
- GUI construida con Java Swing

---

## 🧱 Estructura del proyecto

```
SGHLU_Extendido/
├── src/                   # Código fuente Java
│   ├── SGHLU.java
│   ├── gui/
│   ├── modelo/
│   └── persistencia/
├── resources/             # Archivos de datos
│   ├── eventos.csv
│   └── estudiantes.csv
├── bin/                   # (Se genera al compilar)
├── SGHLU.jar              # (Se genera manualmente)
```

---

## 🛠️ Requisitos

- Java JDK 17 o superior
- Terminal o VS Code con entorno Java configurado

---

## 🚀 Instrucciones para compilar y ejecutar

1. Abre terminal y navega al directorio raíz:

```bash
cd RUTA/SGHLU_Extendido
```

2. Compila el proyecto:

```bash
mkdir bin
javac -d bin src\SGHLU.java src\gui\*.java src\modelo\*.java src\persistencia\*.java
```

3. Crea el archivo `.jar`:

```bash
jar cfe SGHLU.jar SGHLU -C bin .
```

4. Ejecuta la aplicación:

```bash
java -jar SGHLU.jar
```

---

## 📁 Formato de eventos.csv

```
id,nombre,tipo,fecha,horas
1,Taller de programación,Tecnología,2025-05-10,2
2,Seminario de liderazgo,Desarrollo Personal,2025-05-12,3
3,Conferencia de IA,Tecnología,2025-05-15,1
```

---

## 📋 Créditos

Desarrollado por estudiantes de Ingeniería UNAB para el curso de Estructuras de Datos y Análisis de Algoritmos.