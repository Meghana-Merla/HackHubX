# 🚀 HackHubX

> A role-based Android application that enables students to discover and apply for hackathons while allowing organizers to create, manage, and track hackathon events in real time.

<p align="center">

![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)
![Firebase](https://img.shields.io/badge/Firebase-FFCA28?style=for-the-badge&logo=firebase&logoColor=black)
![Android Studio](https://img.shields.io/badge/Android%20Studio-3DDC84?style=for-the-badge&logo=androidstudio&logoColor=white)
![Firestore](https://img.shields.io/badge/Cloud%20Firestore-FFA000?style=for-the-badge)

</p>

---

# 📱 Overview

HackHubX is a modern Android application built using **Kotlin** and **Firebase** that bridges the gap between students and hackathon organizers.

Students can browse hackathons, apply for events, manage their developer profiles, and track applications. Organizers can create, edit, delete, and manage hackathons while monitoring applicants through a dedicated dashboard.

The application follows a **role-based architecture** with secure authentication and real-time synchronization using Firebase Firestore.

---

# ✨ Key Highlights

- 🔐 Firebase Authentication
- 👥 Role-based Login (Student & Organizer)
- 🏆 Hackathon Discovery
- 📝 One-click Registration
- 📂 Application Tracking
- 📊 Organizer Dashboard
- 👤 Editable Developer Profiles
- ☁️ Real-time Firestore Database
- 🎨 Modern Material-inspired UI

---

# 📋 Features

## 👨‍🎓 Student Module

- User Registration & Login
- Browse Available Hackathons
- Search Hackathons
- View Hackathon Details
- Apply for Hackathons
- Track Applied Hackathons
- Edit Developer Profile
- Add Bio
- Add Skills
- Add GitHub
- Add LinkedIn

---

## 🧑‍💼 Organizer Module

- Organizer Authentication
- Create Hackathons
- Edit Existing Hackathons
- Delete Hackathons
- View Registered Applicants
- Manage Dashboard

---

# 🎨 UI Features

- Modern Purple Theme
- Clean Material Design
- Responsive XML Layouts
- Empty State Screens
- Rounded Components
- Profile Avatar Support
- Real-time UI Updates

---

# 🛠 Tech Stack

| Technology | Purpose |
|------------|----------|
| Kotlin | Android Development |
| XML | User Interface |
| Firebase Authentication | Secure Login |
| Cloud Firestore | Database |
| RecyclerView | Dynamic Lists |
| Glide | Image Loading |
| Android Studio | Development |

---

# 🏗 Architecture

```
Presentation Layer
       │
Activities / RecyclerViews
       │
Firebase Authentication
       │
Cloud Firestore
```

---

# 🔥 Firestore Database Schema

```text
users
│
├── uid (String)
├── name (String)
├── email (String)
├── role (candidate / organizer)
├── college (String)
├── branch (String)
├── year (String)
├── bio (String)
├── skills (String)
├── github (String)
├── linkedin (String)
└── profileImageUrl (String)

hackathons
│
├── documentId (String)
├── title (String)
├── description (String)
├── prize (String)
├── teamSize (String)
├── deadline (String)
├── imageUrl (String)
├── organizerId (String)
└── applicantCount (Number)

applications
│
├── userId (String)
├── hackathonTitle (String)
└── timestamp (Timestamp)
```

---

# 📸 Screenshots

## Authentication

| Splash | Login | Register |
|--------|-------|----------|
| <img src="screenshots/splash.jpeg" width="220"/> | <img src="screenshots/login.jpeg" width="220"/> | <img src="screenshots/register.jpeg" width="220"/> |

---

## Student Module

| Dashboard | Applications | Profile |
|-----------|--------------|----------|
| <img src="screenshots/student_dashboard.jpeg" width="220"/> | <img src="screenshots/applications.jpeg" width="220"/> | <img src="screenshots/profile.jpeg" width="220"/> |

---

## Organizer Module

| Dashboard | Create | Applicants |
|-----------|--------|------------|
| <img src="screenshots/organizer_dashboard.jpeg" width="220"/> | <img src="screenshots/create_hackathon.jpeg" width="220"/> | <img src="screenshots/applicants.jpeg" width="220"/> |

---

## Other Screens

| Hackathon Details | Edit Profile |
|-------------------|--------------|
| <img src="screenshots/hackathon_details.jpeg" width="220"/> | <img src="screenshots/edit_profile.jpeg" width="220"/> |

---

# ⚙️ Installation

```bash
git clone https://github.com/your-username/HackHubX.git
```

Open the project using **Android Studio**.

Configure Firebase:

- Add `google-services.json`
- Enable Firebase Authentication
- Enable Cloud Firestore

Sync Gradle and run the application.

---

# 📂 Project Structure

```text
HackHubX
│
├── app
│   └── src
│       └── main
│           ├── java
│           │   └── com.meghana.hackhubx
│           │       ├── adapter          # RecyclerView Adapters
│           │       ├── data             # Firebase Data Sources
│           │       ├── model            # Data Models
│           │       │   ├── Applicant
│           │       │   ├── Application
│           │       │   ├── Hackathon
│           │       │   └── User
│           │       ├── repository       # Data Repository Layer
│           │       ├── ui
│           │       │   ├── auth
│           │       │   ├── dashboard
│           │       │   └── splash
│           │       ├── utils            # Utility Classes
│           │       ├── viewmodel        # MVVM ViewModels
│           │       └── MainActivity.kt
│           │
│           ├── res                      # Layouts, Drawables, Values
│           └── AndroidManifest.xml
│
├── screenshots
└── README.md
```

---

# 🚀 Future Scope

- Push Notifications
- Bookmark Hackathons
- Advanced Search Filters
- Dark Mode
- Event Recommendations
- Calendar Integration

---

# 📖 Learning Outcomes

- Android App Development with Kotlin
- Firebase Authentication
- Cloud Firestore Integration
- RecyclerView & Custom Adapters
- Role-based Access Control
- CRUD Operations
- Mobile UI/UX Design
- Git & GitHub Workflow

---

# 👨‍💻 Author

**Meghana Merla**

GitHub: https://github.com/Meghana-Merla

---

## ⭐ If you like this project

Give this repository a ⭐ on GitHub!
