# MovieCatalogApp

MovieCatalogApp is an Android application that allows users to browse popular movies and view detailed information about each movie. The app is built using modern Android development practices and libraries.

![MovieCatalogeApp](https://github.com/user-attachments/assets/f4f59987-d849-47e6-975d-2ca1897eadc7)

## Features

- View a list of popular movies
- See detailed information about each movie
- Offline support with local caching
- Automatically refreshes content when an internet connection becomes available
- Constantly listens for network changes to ensure up-to-date content
- Pull-to-refresh functionality
- Clean and intuitive user interface


## Tech Stack

- **Language**: Kotlin
- **UI**: Jetpack Compose
- **Architecture**: Clean Architecture with MVI pattern
- **Dependency Injection**: Hilt
- **Networking**: Retrofit
- **Local Database**: Room
- **Image Loading**: Glide
- **Asynchronous Programming**: Kotlin Coroutines and Flow
- **Navigation**: Jetpack Navigation Compose

## Project Structure

The project is organized into several modules:

- `app`: Main application module
- `data`: Handles data operations and contains repository implementations
- `domain`: Contains business logic and use cases
- `presentation`: Manages UI-related code including ViewModels and Compose UI
- `di`: Handles dependency injection setup
- `utils`: Contains utility classes and functions

## Key Components

### API

- `TmdbApi`: Interface for The Movie Database API calls

### Database

- `MovieDatabase`: Room database setup
- `MovieDao`: Data Access Object for local movie data

### Repository

- `MovieRepository`: Manages data operations between local database and remote API

### Use Cases

- `GetPopularMoviesUseCase`: Retrieves the list of popular movies
- `GetMovieDetailsUseCase`: Fetches detailed information about a specific movie

### ViewModels

- `MovieListViewModel`: Manages state for the list of movies
- `MovieDetailsViewModel`: Manages state for individual movie details

### UI

- `MovieListScreen`: Displays the list of popular movies
- `MovieDetailsScreen`: Shows detailed information about a selected movie
- `Navigation`: Handles navigation between screens

## Getting Started

To get started with this project:

1. Clone the repository
2. Open the project in Android Studio
3. Sync the project with Gradle files
4. Run the app on an emulator or physical device

## API Key

This project uses The Movie Database (TMDb) API. You need to provide your own API key:

1. Get an API key from [TMDb](https://www.themoviedb.org/documentation/api)
2. Add your API key to the `local.properties` file:
   ```
   api_key="YOUR_API_KEY_HERE"
   ```

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.
