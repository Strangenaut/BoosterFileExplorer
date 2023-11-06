# Booster File Explorer

It is an Android application that serves as a file manager and allows users to browse, sort, open and share files on their smartphones. The project utilizes technologies such as Kotlin, Jetpack Compose, Dagger-Hilt, Room, Coroutines, MVVM and Clean Architecture.

## Features

- **File Viewer**: The app provides a user-friendly interface to browse and explore files on the user's smartphone. Users can navigate through directories, view file details and organize files with various sorting options.
- **Open Files**: Users can open different types of files directly in the app. The application handles common file formats such as documents, images, audio files, and videos using appropriate system intents or built-in viewers.
- **Share File**: The app allows users to share files with others through various methods such as email, messengers or social platforms. Users can select one or multiple files and choose the desired transfer option.
- **Background saving of file hash codes**: The application performs a background calculation of hash codes of file contents and saves them in the database each time the application is launched. This allows you to detect file changes between application sessions. Files that have been modified since the previous run are marked for easy identification.
- **Search and Filtering**: Users can search for specific files by name or apply filters based on name, file types, change date sizes, extension, in ascending and descending order. This helps users quickly find and access the files they need.
- **Available storage space information**: The app displays information about the available storage space on the device, including total storage space, used space and available space. This helps users effectively manage the storage space on the device.
- **Permission Management**: The application efficiently handles permissions at runtime, ensuring that the necessary permissions are requested from the user to access files and directories on the device.
- **Dark Theme Support**: The app offers a dark theme option, allowing users to customize the appearance according to their preferences and providing a comfortable viewing experience in low-light conditions.

## Architecture and design patterns

The project follows Clean Architecture principles and utilizes the MVVM architectural pattern, providing a clear separation of responsibilities and facilitating testing and maintenance of the project. Dagger-Hilt is used to implement dependencies, providing a modular and scalable approach to dependency management. Room is used as a local database to store file metadata, and Coroutines are used to handle asynchronous operations and provide a responsive user interface.

## Screenshots
### Main screen
![Root directory](https://user-images.githubusercontent.com/98609700/236897616-638ff776-8ac5-44bd-9aa8-e4457d14fb1e.jpg)
![Downloads](https://user-images.githubusercontent.com/98609700/236897632-29ab1519-080e-459a-9637-bec77bdab8ec.jpg)

### Sorting
![By name and type](https://user-images.githubusercontent.com/98609700/236898093-f7b3dfdc-5ece-4e10-b116-ab7b1311a6f4.jpg)
![By name and type in descending order](https://user-images.githubusercontent.com/98609700/236898137-702c9d7c-54ff-41db-ad9c-e27d878afcc3.jpg)
![By name](https://user-images.githubusercontent.com/98609700/236898184-7bb25be4-0120-440b-b288-22b65c4b2138.jpg)
![By size](https://user-images.githubusercontent.com/98609700/236898195-8833e3fb-f7fd-4f5e-bf89-caeed977d5dd.jpg)
![By date of creation](https://user-images.githubusercontent.com/98609700/236898251-b72f9634-083b-41aa-b461-8dfbe9ec9e41.jpg)
![By type](https://user-images.githubusercontent.com/98609700/236898286-d55b6e94-393d-47d5-bdeb-4d289820f329.jpg)

### Displaying the modified file
In the example above, the example.log file was modified between two runs of the application.

![Before changes](https://user-images.githubusercontent.com/98609700/236898360-32ac05dc-4bdf-42c7-890e-76761d1493f2.jpg)
![After changes](https://user-images.githubusercontent.com/98609700/236898364-1248d340-00a1-495e-8720-a4945a1bfb1a.jpg)

### Dark theme
![Dark theme](https://user-images.githubusercontent.com/98609700/236898405-876e7d13-61dc-4c7b-989f-733027cc9e3d.jpg)

### File opening
![File opening](https://user-images.githubusercontent.com/98609700/236898436-e1d8de1e-2347-4a65-8cae-e110e6a90574.jpg)

### File sending
![File sending](https://user-images.githubusercontent.com/98609700/236898516-dd91f146-9597-4c48-944f-b0718a415881.jpg)
