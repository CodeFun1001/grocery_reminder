# Grocery Reminder App 

## Smart Grocery Geofence Reminder

A simple Android application built using **Kotlin + Jetpack Compose** that allows users to save grocery reminders tied to a specific store location.

When the user physically enters the saved store area (within 100 meters), the app automatically triggers a personalized notification reminding them to buy the item.

The application works offline and synchronizes completed reminders to a mock server once internet connectivity becomes available.

---

## Features

✅ Add Grocery Reminder

* Enter Item Name
* Enter Shop Latitude
* Enter Shop Longitude

Example:

```text
Item Name: Milk

Latitude: 19.0330

Longitude: 73.0297
```

---

✅ Store Reminder Locally

Using Room Database

Stored fields:

```text
id
itemName
latitude
longitude
status
synced
```

---

✅ Geofencing

When user enters:

```text
100 meter radius
```

around saved coordinates

Android Geofencing API automatically triggers.

---

✅ Personalized Notification

Example:

```text
Hey! Looks like you're at the store.

Don't forget to buy Milk 🛒
```

Works even when:

* App is closed
* App is in background
* Phone is locked

---

✅ Lock Screen Notification

Notification appears directly on lock screen.

User can immediately see reminder without opening app.

---

✅ Offline Support

Entire flow works without internet.

Reminder creation:

```text
No internet required
```

Geofence triggering:

```text
No internet required
```

Notification:

```text
No internet required
```

---

✅ Sync Completed Reminders

When internet becomes available:

```text
Completed Reminder
        ↓
WorkManager
        ↓
Retrofit API
        ↓
Mock Server
```

Reminder is automatically marked synced.

---

## Architecture

```text
UI (Jetpack Compose)
        │
ViewModel
        │
Repository
        │
Room Database
        │
Reminder Entity
        │
Geofence Manager
        │
GeofencingClient
        │
BroadcastReceiver
        │
Notification
        │
WorkManager
        │
Retrofit
        │
Mock Server
```

---

## Project Structure

```text
com.img.groceryreminderapp

├── ui
│   └── ReminderScreen.kt

├── viewmodel
│   └── ReminderViewModel.kt

├── data
│   ├── ReminderEntity.kt
│   ├── ReminderDao.kt
│   ├── ReminderDatabase.kt
│   └── ReminderRepository.kt

├── geofence
│   ├── GeofenceManager.kt
│   └── GeofenceBroadcastReceiver.kt

├── notification
│   └── NotificationHelper.kt

├── worker
│   └── ReminderSyncWorker.kt

├── network
│   ├── ReminderApi.kt
│   ├── SyncRequest.kt
│   └── RetrofitModule.kt

└── MainActivity.kt
```

---

## How To Use

### Step 1

Open app.

### Step 2

Enter:

```text
Item Name
Shop Latitude
Shop Longitude
```

Example:

```text
Milk

19.0330

73.0297
```

### Step 3

Click:

```text
Save Reminder
```

### Step 4

Geofence gets registered automatically.

### Step 5

Visit the shop location later.

### Step 6

When you enter within:

```text
100 meters
```

of saved coordinates

notification appears:

```text
Hey! Looks like you're at the store.

Don't forget to buy Milk 🛒
```

### Step 7

Reminder marked completed.

### Step 8

When internet returns:

```text
WorkManager
→ Sync API
→ Mock Server
→ Mark Synced
```

---

## Tech Stack

* Kotlin
* Jetpack Compose
* Room Database
* Geofencing API
* FusedLocationProviderClient
* BroadcastReceiver
* NotificationManager
* WorkManager
* Retrofit
* Coroutines
* StateFlow
* MVVM Architecture

---

## Future Improvements

* Google Maps Picker
* Favorite Grocery Stores
* Multiple Reminders Per Store
* Reminder History
* Cloud Backup
* User Authentication
* Real Backend API

---

### Flow

```text
Create Reminder
        ↓
Store in Room
        ↓
Register Geofence
        ↓
User enters store area
        ↓
Notification Triggered
        ↓
Reminder Completed
        ↓
WorkManager Sync
        ↓
Mock Server
```
