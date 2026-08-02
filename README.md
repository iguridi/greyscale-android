# Greyscale Scheduler

Android app that automatically toggles grayscale mode on your Motorola Stylus based on a schedule.

## Schedule
- **Sunday - Thursday at 8:00 PM**: Grayscale ON
- **Monday - Friday at 6:00 AM**: Grayscale OFF

## Setup

1. Install the app on your device
2. Grant the required permission via ADB:
   ```bash
   adb shell pm grant com.greyscale.scheduler android.permission.WRITE_SECURE_SETTINGS
   ```
3. Open the app once to activate the schedule
4. That's it! The app will automatically toggle grayscale according to the schedule

## How It Works

The app uses Android's accessibility daltonizer settings to enable grayscale mode:
- `accessibility_display_daltonizer_enabled` = 1 (enable)
- `accessibility_display_daltonizer` = 0 (grayscale mode)

Alarms are scheduled using `AlarmManager` and persist across app restarts. The `BootReceiver` ensures alarms are re-scheduled after device reboot.

## Tech Stack

- Kotlin
- Jetpack Compose (Material 3)
- AlarmManager for scheduling
- Settings.Secure for grayscale toggle

## Requirements

- Android 8.0 (API 26) or higher
- ADB access for initial permission grant

## Building

```bash
./gradlew assembleDebug
```

The APK will be in `app/build/outputs/apk/debug/`