# Crypto Tracker

Una moderna aplicación para Android desarrollada con Jetpack Compose que permite monitorear datos de criptomonedas en tiempo real, proporcionando información detallada y tendencias de precios de las principales criptomonedas.

## Características

- Seguimiento de precios de criptomonedas en tiempo real.
- Vista detallada para cada criptomoneda que incluye:
  - Precio actual.
  - Capitalización de mercado.
  - Variación de precio en las últimas 24 horas.
  - Gráficos de tendencia en intervalos de 5 horas.
- Implementación con Material Design 3.
- Interfaz limpia e intuitiva.
- Compatibilidad con temas claro y oscuro.

## Capturas de Pantalla

| Inicio | Vista Detallada con Gráfico |
|---------|---------------------------|
| ![Pantalla de inicio](/screenshots/home.jpeg) | ![Vista detallada](/screenshots/detail.jpeg) |

## Tecnologías Utilizadas

- **Framework de UI**: Jetpack Compose con Material 3.
- **Lenguaje de Programación**: Kotlin.
- **Arquitectura**: Clean Architecture.
  - Capa de Dominio.
  - Capa de Datos.
  - Capa de Presentación.
- **Inyección de Dependencias**: Koin.
- **Networking**: Ktor.
- **Otras Bibliotecas**:
  - Compose Navigation.
  - Kotlinx Serialization.
  - Compose Charts.

## Instalación

1. Clona este repositorio.
2. Abre el proyecto en Android Studio.
3. Compila y ejecuta la aplicación en un emulador o dispositivo físico.

## Requisitos

- Android Studio Koala o una versión más reciente.
- SDK mínimo: 26.
- SDK objetivo: 35.
- Kotlin 2.0 o superior.
