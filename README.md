# 🚌 Los Chaskis – Sistema de Gestión de Transporte

Aplicación web para la gestión de una empresa de transporte interprovincial de buses (**Transportes Los Chaskis S.A.C.**). Incluye un panel administrativo para gestionar la flota y los viajes, y un portal público para clientes.

Proyecto del curso *Marcos de Desarrollo Web* (Grupo 5).

## Tecnologías

- Java 21
- Spring Boot 4.1.1 (Spring MVC)
- Thymeleaf (plantillas HTML)
- Maven (wrapper incluido: `mvnw`)
- CSS propio (`static/css/styles.css`)

## Estructura del repositorio

```
practicaParaELi/
├── demo/                     # Aplicación Spring Boot
│   ├── pom.xml
│   └── src/main/
│       ├── java/com/example/demo/
│       │   ├── controller/   # Controladores MVC (CrudController = CRUD común)
│       │   ├── service/      # Lógica de negocio (CrudEnMemoria = CRUD en memoria común)
│       │   └── model/        # Entidades y enumeraciones
│       └── resources/
│           ├── templates/    # Vistas Thymeleaf (fragments/ = layout y componentes reutilizables)
│           ├── static/css/   # Estilos
│           └── application.properties
├── message.txt               # Script SQL (MySQL) del modelo de base de datos
├── INFORME - APF1 MDDW - GRUPO 5.pdf
├── RUBRICA DEL PROYECTO AVANCE2.pdf
└── MARCOSDEDESARROLLOWEB_undefined (1).pdf
```

## Requisitos previos

- JDK 21 o superior
- No necesitas instalar Maven: se usa `mvnw`
- (Opcional) MySQL 8+ si quieres crear la base de datos del script SQL

## Cómo ejecutar

```bash
cd demo
./mvnw spring-boot:run
```

En Windows: `mvnw.cmd spring-boot:run`

Luego abre <http://localhost:8080>.

Para generar el JAR ejecutable:

```bash
./mvnw clean package
java -jar target/demo-0.0.1-SNAPSHOT.jar
```

Para ejecutar las pruebas: `./mvnw test`

## Roles y acceso

El inicio de sesión usa usuarios fijos en memoria y una sesión HTTP simple (sin Spring Security ni base de datos). Un interceptor protege las rutas por rol; si entras con el rol equivocado, te devuelve a tu propia vista.

| Rol | Correo | Contraseña | Entra a |
|-----|--------|------------|---------|
| Admin | admin@loschaskis.com | 12345678 | `/admin/**` |
| Supervisor | jperez@loschaskis.com | 12345678 | `/supervisor/**` |

> Al integrar MySQL solo hay que cambiar `AuthService` para que consulte las tablas `usuarios` y `roles`.

## Rutas principales

### Portal público (sin login)

| Ruta | Descripción |
|------|-------------|
| `/`, `/inicio`, `/portal` | Página de inicio del portal |
| `/portal/buscar` | Búsqueda de viajes |
| `/portal/pago` | Pantalla de pago |
| `/portal/registro` | Crear cuenta de cliente |
| `/login`, `/logout` | Inicio / cierre de sesión |

### Admin (`ROLE` Admin)

| Ruta | Descripción |
|------|-------------|
| `/admin/dashboard` | Panel principal con KPIs y gráficos |
| `/admin/buses`, `/admin/choferes`, `/admin/terminales`, `/admin/rutas`, `/admin/viajes` | CRUD con buscador (`?q=`) |
| `/admin/reportes/grafico1`, `/admin/reportes/grafico2` | Reportes gráficos (barras, lineal, circular) |

Cada módulo CRUD sigue el mismo patrón:

- `GET /admin/{modulo}` – listado, formulario y búsqueda (`?q=texto`)
- `POST /admin/{modulo}/guardar` – crear o actualizar
- `GET /admin/{modulo}/editar/{id}` – cargar registro para editar
- `GET /admin/{modulo}/eliminar/{id}` – eliminar

### Supervisor (`ROLE` Supervisor)

| Ruta | Descripción |
|------|-------------|
| `/supervisor/embarque` | Buscador de viaje, ficha del bus, ocupación y manifiesto con Abordó / Ausente |
| `/supervisor/plano-asientos` | Plano interactivo; al hacer clic en un asiento vendido se marca Abordó / Ausente sin salir de la pantalla |

## Datos y persistencia

⚠️ **Actualmente la aplicación no usa base de datos.** Los servicios (`BusService`, `ChoferService`, `RutaService`, `TerminalService`, `ViajeService`) guardan los datos en listas en memoria y los precargan al iniciar (por ejemplo, 8 buses `CHK-201` … `CHK-208`). Los cambios **se pierden al reiniciar** la aplicación.

### Modelo de base de datos (planificado)

El archivo [message.txt](message.txt) contiene el script MySQL del modelo previsto (`los_chaskis_db`), con tablas como `usuarios`, `roles`, `buses`, `asientos`, `rutas`, `terminales`, `viajes`, `pasajeros`, `ventas`, `detalle_venta` y `comprobantes`, además de datos iniciales (roles, tipos de documento, servicios Económico/Confort/Imperial, métodos de pago Yape/Plin/Tarjeta y la empresa emisora).

Para crearla:

```bash
mysql -u root -p < message.txt
```

> Para conectarla a la aplicación habría que agregar `spring-boot-starter-data-jpa` y el driver de MySQL en el `pom.xml`, y configurar `spring.datasource.*` en `application.properties`.

## Configuración

`demo/src/main/resources/application.properties`:

```properties
spring.application.name=Los Chaskis - Sistema de Gestion
server.port=8080
spring.thymeleaf.cache=false
```

Para cambiar el puerto, modifica `server.port`.

## Próximos pasos sugeridos

- Conectar los servicios a MySQL con Spring Data JPA
- Autenticación con Spring Security y usuarios en MySQL (`ROLE_ADMIN`, `ROLE_SUPERVISOR`, `ROLE_CLIENTE`)
- Selección de asientos, ventas y emisión de comprobantes (boleta/factura)
- Cambiar las operaciones de eliminar a `POST`/`DELETE` en lugar de `GET`

## Equipo

Grupo 5 – Marcos de Desarrollo Web.
