# Rick and Morty Character Browser 👽🚀

**Native Android App built with Kotlin and Jetpack Compose**

---

## Overview

The **Rick and Morty Character Browser** is an **online-first** Android application utilizing **The Rick and Morty API** to display and manage characters from the popular animated series.

The project is engineered for **performance and maintainability**, strictly following **Google's recommended Clean Architecture** and the modern **MVI (Model-View-Intent)** architectural pattern. **Test coverage**, including **UI tests** and detailed **unit tests** for the business logic, ensures code reliability and stability.

## Key Features

* **Browse Characters:** Users can scroll through a paginated list of all characters who have appeared in the show.
* **Offline Favorites:** Users can **save their favorite characters** by tapping the heart icon, ensuring full access to their details even **without an internet connection**.
* **Modern UI:** Built entirely with **Jetpack Compose** for a reactive and declarative user experience.
* **Scalable Architecture:** The codebase is cleanly separated into **Presentation**, **Domain**, and **Data** layers.

---

## Technical Stack and Architecture

This application showcases proficiency in a range of modern Android development tools and best practices.

### Architecture

* **MVI** (Model-View-Intent) Architecture Pattern
* **Clean Architecture** separation:
    * **Presentation** Layer
    * **Domain** Layer
    * **Data** Layer

### Core Technologies

* **Retrofit API** to handle HTTP requests.
* **Paging-Compose** library for efficient data paging from the server.
* **Room Database** to save data locally.
* **Hilt** for dependency injection.
* **Flow and Coroutines** for asynchronous operations.
* **Jetpack Compose** to display UI.
* **Compose Navigation** library for navigation.
* **Compose UI Tests** for instrumental testing.
* **Mockito and JUnit** for unit testing business logic.

---

## Demo

[GIF/Video Demo](https://github.com/user-attachments/assets/f727b1ba-900b-46c8-a095-595b3ef9c165)

---

## Links

* **Download APK:** If you want to try this app on your Android phone, you can download the APK file [here.](https://drive.google.com/file/d/1kR3WjyxZGbBlO3wi_rT1PCbHAR_XhwpP/view?usp=sharing)
* **API:** All data is provided by the official **[Rick and Morty API.](https://rickandmortyapi.com/)**
