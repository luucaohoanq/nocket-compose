# 📸 Nocket App

[![Kotlin](https://img.shields.io/badge/Kotlin-1.9-blue?logo=kotlin)](https://kotlinlang.org/)
[![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-Material%203-4285F4?logo=jetpackcompose)](https://developer.android.com/jetpack/compose)
[![Hilt](https://img.shields.io/badge/Hilt-DI-FF6F00?logo=dagger)](https://dagger.dev/hilt/)
[![Appwrite](https://img.shields.io/badge/Appwrite-Cloud-F02E65?logo=appwrite)](https://appwrite.io/)
[![License](https://img.shields.io/badge/License-MIT-green)](LICENSE)

> **A modern Android prototype** of a social camera app built with Kotlin and Jetpack Compose.  
> Features a clean Material 3 UI, smooth navigation, previewable components, and an Appwrite-backed data layer.

<div style="
    display: flex;
    align-items: center;
    flex-wrap: wrap;
    gap: 12px;
    font-family: 'Segoe UI', Roboto, sans-serif;
">
  
  <a href="#-highlights" style="
      flex: 1 1 calc(50% - 12px);
      background: #ffffff;
      border: 1px solid #e0e0e0;
      border-radius: 10px;
      padding: 12px;
      text-decoration: none;
      color: #2b6cb0;
      box-shadow: 0 2px 5px rgba(0,0,0,0.05);
      transition: all 0.2s ease;
      display: flex;
      align-items: center;
      font-weight: 500;
  " onmouseover="this.style.background='#f7fafc'" onmouseout="this.style.background='#ffffff'">
      ✨ Highlights
  </a>

<a href="#-screens" style="
      flex: 1 1 calc(50% - 12px);
      background: #ffffff;
      border: 1px solid #e0e0e0;
      border-radius: 10px;
      padding: 12px;
      text-decoration: none;
      color: #2b6cb0;
      box-shadow: 0 2px 5px rgba(0,0,0,0.05);
      transition: all 0.2s ease;
      display: flex;
      align-items: center;
      font-weight: 500;
  " onmouseover="this.style.background='#f7fafc'" onmouseout="this.style.background='#ffffff'">
🖼 Screens
</a>

<a href="#-project-structure" style="
      flex: 1 1 calc(50% - 12px);
      background: #ffffff;
      border: 1px solid #e0e0e0;
      border-radius: 10px;
      padding: 12px;
      text-decoration: none;
      color: #2b6cb0;
      box-shadow: 0 2px 5px rgba(0,0,0,0.05);
      transition: all 0.2s ease;
      display: flex;
      align-items: center;
      font-weight: 500;
  " onmouseover="this.style.background='#f7fafc'" onmouseout="this.style.background='#ffffff'">
📂 Project Structure
</a>

<a href="#-tech-stack" style="
      flex: 1 1 calc(50% - 12px);
      background: #ffffff;
      border: 1px solid #e0e0e0;
      border-radius: 10px;
      padding: 12px;
      text-decoration: none;
      color: #2b6cb0;
      box-shadow: 0 2px 5px rgba(0,0,0,0.05);
      transition: all 0.2s ease;
      display: flex;
      align-items: center;
      font-weight: 500;
  " onmouseover="this.style.background='#f7fafc'" onmouseout="this.style.background='#ffffff'">
🛠 Tech Stack
</a>

<a href="#-appwrite-setup" style="
      flex: 1 1 calc(50% - 12px);
      background: #ffffff;
      border: 1px solid #e0e0e0;
      border-radius: 10px;
      padding: 12px;
      text-decoration: none;
      color: #2b6cb0;
      box-shadow: 0 2px 5px rgba(0,0,0,0.05);
      transition: all 0.2s ease;
      display: flex;
      align-items: center;
      font-weight: 500;
  " onmouseover="this.style.background='#f7fafc'" onmouseout="this.style.background='#ffffff'">
⚙ Appwrite Setup
</a>

<a href="#-roadmap-ideas" style="
      flex: 1 1 calc(50% - 12px);
      background: #ffffff;
      border: 1px solid #e0e0e0;
      border-radius: 10px;
      padding: 12px;
      text-decoration: none;
      color: #2b6cb0;
      box-shadow: 0 2px 5px rgba(0,0,0,0.05);
      transition: all 0.2s ease;
      display: flex;
      align-items: center;
      font-weight: 500;
  " onmouseover="this.style.background='#f7fafc'" onmouseout="this.style.background='#ffffff'">
🚀 Roadmap Ideas
</a>

</div>

## ✨ Highlights

- **UI:** Jetpack Compose + Material 3, dark/light themes, and previews
- **Navigation:** Navigation Compose + top bars
- **Media:** Image loading with Coil
- **Architecture:** MVVM architecture, Hilt for DI, Retrofit (stub) + OkHttp
- **Backend:** Appwrite SDK integration via type-safe `BuildConfig` fields

## 🖼 Screens

<div align="center">
  <table>
    <tr><th colspan="3">📱 Screen Previews</th></tr>
    <tr>
      <td align="center"><img src="Sources/assets/posts_screen.png" alt="Posts" width="240"/><br/>Posts</td>
      <td align="center"><img src="Sources/assets/post_screen_list_friend_component.png" alt="Friend List Component" width="240"/><br/>Friend List</td>
      <td align="center"><img src="Sources/assets/take_photo_screen.png" alt="Camera" width="240"/><br/>Camera</td>
    </tr>
    <tr>
      <td align="center"><img src="Sources/assets/submit_photo_screen.png" alt="Submit Photo" width="240"/><br/>Submit Photo</td>
      <td align="center"><img src="Sources/assets/messages_screen.png" alt="Messages" width="240"/><br/>Messages</td>
      <td align="center"><img src="Sources/assets/my_profile_screen.png" alt="Profile" width="240"/><br/>Profile</td>
    </tr>
    <tr>
      <td align="center"><img src="Sources/assets/settings_screen.png" alt="Settings" width="240"/><br/>Settings</td>
      <td align="center"><img src="Sources/assets/logo.png" alt="Logo" width="240"/><br/>Logo</td>
      <td align="center"><img src="Sources/assets/splash_screen.png" alt="Splash Screen" width="240"/><br/>Splash Screen</td>
    </tr>
  </table>
</div>

> 📌 **Reference:** See navigation routes in  
> `app/src/main/java/com/example/nocket/Navigation.kt`

## 📂 Project Structure

```
components/     → Reusable UI: top bars, pills, lists, grids, etc.
ui/screen/      → Feature screens (post, message, profile, camera, submit photo)
data/           → SampleData for previews and demo
di/             → Hilt modules (e.g., AppwriteModule.kt)
repositories/   → Data sources (e.g., AppwriteRepository.kt)
viewmodels/     → State holders (e.g., AppwriteViewModel.kt)
constants/      → AppwriteConfig.kt bridges BuildConfig to the app
```

## 🛠 Tech Stack

| Layer          | Technology                   |
| -------------- | ---------------------------- |
| **Language**   | Kotlin                       |
| **UI**         | Jetpack Compose + Material 3 |
| **Navigation** | Navigation Compose           |
| **DI**         | Hilt                         |
| **Networking** | Retrofit, OkHttp, Gson       |
| **Media**      | Coil                         |
| **Backend**    | Appwrite Android SDK         |

---

## ⚙ Appwrite Setup

This project reads Appwrite settings from `local.properties` and exposes them as `BuildConfig` fields.

**Required Keys:**

```properties
appwrite.version=1.6.0
appwrite.project.id=YOUR_PROJECT_ID
appwrite.project.name=YOUR_PROJECT_NAME
appwrite.endpoint=https://<your-endpoint>/v1
```

**How it works:**

1. `app/build.gradle.kts` loads these values and generates `BuildConfig` constants.
2. `AppwriteConfig.kt` exposes them as `APPWRITE_*` constants.
3. `di/AppwriteModule.kt` configures the Appwrite `Client`, `Account`, and `Databases`.

---

## 🚀 Roadmap Ideas

- Replace `SampleData` with real Appwrite repositories
- Add authentication flow & real-time updates
- Expand test coverage + UI tests
