# Greyscale Scheduler

Android app that automatically toggles grayscale mode on your Motorola Stylus based on a schedule.

## Schedule
- **Sunday - Thursday at 9:00 PM (phone local time)**: Grayscale ON
- **Monday - Friday at 5:00 AM (phone local time)**: Grayscale OFF
- **Friday and Saturday nights**: No scheduled grayscale

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

## Device verification (2026-10-14)

- Built `assembleDebug` with Homebrew OpenJDK 17.0.20 and installed the APK on the connected moto g stylus (2025). Installed APK SHA-256 matched the build (`1213d7fbd8c42ce12931e1bcd00e7d7e184c40d9c545cd2b644a5f611b31c228`).
- In the phone's `America/Los_Angeles` time zone, `dumpsys alarm` showed the next ENABLE alarm at Wednesday 9:00 PM and DISABLE at Thursday 5:00 AM. The receiver's day guards remain Sunday–Thursday for ENABLE and Monday–Friday for DISABLE.
- Sent both receiver actions as the app UID (`run-as`), since the receiver is not exported. ENABLE set `accessibility_display_daltonizer_enabled` to `1` with mode `0`; DISABLE restored enabled to its prior value `0`, with mode still `0`. No Digital Wellbeing settings were changed.
- After unlocking the phone, launched the app and verified its visible schedule: `Sun - Thu / 9:00 PM / Greyscale ON` and `Mon - Fri / 5:00 AM / Greyscale OFF`.