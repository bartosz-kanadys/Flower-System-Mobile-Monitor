# Flower-System-Mobile-Monitor – Aplikacja Android z wykresami, kontrolą zbiornika i integracją z ESP32

Aplikacja mobilna stworzona w Android Studio z użyciem **Kotlin** i **Jetpack Compose**, służąca do wizualizacji pomiarów z czujników oraz zdalnego zarządzania podlewaniem roślin przy użyciu modułu **ESP32**.

## Funkcjonalności

-  **Wybór sensora** z listy (np. temperatura powietrza, wilgotność itp.)
-  **Wybór daty**, dla której mają zostać pobrane i wyświetlone dane
-  **Wykres danych** z czujnika zrealizowany z użyciem biblioteki **Vico**
-  **Wizualizacja poziomu wody** w zbiorniku (np. 425/1000 ml)
-  **Możliwość zmiany pojemności zbiornika**, przesyłana do systemu ESP32
-  **Przycisk "Fill tank"** informujący ESP32, że zbiornik został uzupełniony i może kontynuować pracę
-  **Switch "Water at the next measurement"** – decyduje, czy przy kolejnym wybudzeniu należy podlać roślinę
-  **Przycisk odświeżania danych**

##  Technologie

- **Android Studio**
- **Kotlin**
- **Jetpack Compose**
- **Firebase Firestore** – do przechowywania i pobierania danych pomiarowych
- **Vico** – do rysowania wykresów
- **Koin** – do wstrzykiwania zależności
- **ESP32** – współpraca z systemem mikrokontrolera

##  Integracja z ESP32

Aplikacja komunikuje się z systemem opartym o **ESP32**, który odpowiada za:

- Gromadzenie danych z czujników (np. temperatury, wilgotności)
- Obsługę zbiornika na wodę i procesu podlewania
- Przesyłanie danych do Firestore
- Reagowanie na komendy z aplikacji mobilnej (zmiana pojemności zbiornika, napełnienie zbiornika, podlewanie)

##  Przedstawienie prezentacji
[![1000000739.jpg](https://i.postimg.cc/DzfZn00q/1000000739.jpg)](https://postimg.cc/VJT1FYj6)
