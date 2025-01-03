# Crypto Tracker

A modern Android application built with Jetpack Compose that tracks real-time cryptocurrency data, providing detailed information and price trends for major cryptocurrencies.

## Features

- Real-time cryptocurrency price tracking
- Detailed view for each cryptocurrency including:
  - Current price
  - Market capitalization
  - 24-hour price change
  - 5-hour interval trend charts
- Material Design 3 implementation
- Clean and intuitive user interface
- Dark/Light theme support

## Screenshots

| Home Screen | Detail View with Chart |
|------------|-------------|
|![Home Screen](/screenshots/home.jpeg)|![Detail View](/screenshots/detail.jpeg)|

## Technology Stack

- **UI Framework**: Jetpack Compose with Material 3
- **Programming Language**: Kotlin
- **Architecture**: Clean Architecture
  - Domain Layer
  - Data Layer
  - Presentation Layer
- **Dependency Injection**: Koin
- **Networking**: Ktor
- **Other Libraries**:
  - Compose Navigation
  - Kotlinx Serialization
  - Compose Charts

## Installation

1. Clone this repository
2. Open project in Android Studio
3. Build and run on an emulator or physical device

## Requirements

- Android Studio Koala or newer
- Minimum SDK: 26
- Target SDK: 35
- Kotlin 2.0 or newer
