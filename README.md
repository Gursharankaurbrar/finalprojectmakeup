# Makeup Final Project 
## Mobile App Development ADEV-3007(261249)

# Author
## Gursharan Kaur Brar

# Description
Glam Guide is a modern makeup guide app dedicated to empowering young females to embrace their unique styles. It utilizes the Makeup API to display makeup
products from various brands. The app will categorize items into sections such as lipsticks, nail polishes, and foundations, to
ensure easy navigation. Users will be able to filter products based on brand, type, and other attributes. The makeup industry is vast, with so many products
available online, which makes it overwhelming and hard for users to make decisions. This leads to time-consuming searches.
Glam Guide offers solution to such problems by combining different product information in one place , which can help consumers make informed purchasing decisions quickly. By
categorizing products and providing brand-based filtering, users can explore and compare cosmetics easily and efficiently.

# Setup and Installation
## About the Makeup API :
- Here is the link for the API: http://makeup-api.herokuapp.com/   

- Protocol: The transport is HTTP. This API conforms to REST principals.  

- API Endpoints: The endpoint for the current API version is: http://makeup-api.herokuapp.com/api/v1/products.json  

- There is currently one API call: Search makeup products  

- Type: GET  

- Response Format: json

## Prerequisites
- Android Studio (latest stable version). 
- Firebase project set up with Authentication enabled. 
- Android emulator or physical device for testing.

## Steps to set up locally
- Clone the repository.
- In Android Studio, click "Open" and select the project folder you just cloned.
- Go to the Firebase Console, create a new project, and add an Android app. 
- Download the google-services.json file from Firebase and place it in the app directory of your project. 
- Enable Firebase Authentication in the Firebase console.
- Connect an Android device or start an Android emulator. 
- Click "Run" in Android Studio to build and run the app.

# Technologies Used
## Jetpack Compose: 
For building the UI in a declarative manner.  

## Firebase Authentication:
For user authentication (sign up, login, sign out).  

### Google Services Plugin
     - alias(libs.plugins.google.gms.google.services) apply false  

## Room Database (AppDatabase): 
For local storage and data management of makeup products.
## Dependencies
    - implementation(libs.androidx.room.runtime)
    - implementation(libs.androidx.room.ktx) 
    - implementation(libs.androidx.room.common)
    - annotationProcessor(libs.androidx.room.room.compiler)
    - ksp(libs.androidx.room.room.compiler)  

## Navigation Component:
For handling navigation between different screens in the app.  

## ViewModel: 
For managing UI-related data and for data persistence.  

## Kotlin: 
Programming language used for app development.  

## Material3: 
For UI components such as buttons, text fields, and app bars.  

## Moshi: 
JSON library for Android and Java, used to convert JSON into Kotlin data classes and vice versa.
## Dependencies
    - implementation(libs.moshi.kotlin)
    - implementation(libs.converter.moshi)

# How to Use the App
## Start Screen:
The app will either show the Login screen if the user is not logged in, or directly show the Makeup screen if the user is authenticated.

## Authentication:
- Users can either sign up or log in with an email and password.
- An anonymous sign-in option is available for quick access without creating an account.

## Browse Makeup Products:
- After logging in, the user can navigate to the Makeup Screen, where they can browse various makeup products.
- Users can click on a product to view more details.

## Search Makeup Products:
The app includes a search feature where users can search for makeup products.

## Edit and Delete Makeup Products:
- The app's data is saved in a local database. There is functionality to edit the price and category of a makeup product.
- Functionality to delete any makeup product is also available.

## Adding Makeup Products to Favorites list:
The app includes a feature to add the makeup products that you like to a favorites collection.

## Sign Out:
Users can sign out at any time, which will return them to the Login screen.

## How to Contribute
Contributions are welcome! Here’s how you can contribute to the development of this app:
- Fork the Repository: Fork this repository to your own GitHub account.
- Create a Branch: Create a feature branch for the change you want to make.
- Make Changes: Implement your changes or add new features in the code.
- Commit Your Changes: Commit your changes with a clear and concise message describing what you’ve done. Example: git commit -m "Add new search functionality".
- Push to Your Fork: Push your changes to your forked repository. Example: git push origin feature/new-feature.
- Create a Pull Request: Submit a pull request to the main repository with a description of your changes.