# Rebage

<p align="center">
  <img width="240" src="https://github.com/Rebage-Bangkit2022/Rebage-Android/blob/main/images/Sampel%20Demo%20Rebage.gif">
</p>

## Installation 🔨
Clone this repository and import into **Android Studio**
```bash
git clone https://github.com/Rebage-Bangkit2022/Rebage-Android.git
```

## Configuration ⚙️
### Keystores:
Create `local.properties` with the following info:
```properties
sdk.dir=<sdk_location>
backend.base.url=<base_url>
auth.client.id=<gcp_auth_client_id>
google.maps.web.key=<google_maps_web_key>
google.maps.android.key=<google_maps_android_key>
store.file=<keystore_location>
store.password=<keystore_password>
store.key.alias=<key_alias>
store.key.password=<key_password>
```

## Build variants 🏗️
Use the Android Studio *Build Variants* button to choose between debug and release build types


## Generating signed APK 📱
From Android Studio:
1. ***Build*** menu
2. ***Generate Signed APK...***
3. Fill in the keystore information *(you only need to do this once manually and then let Android Studio remember it)*

## Maintainers 🧑‍🤝‍🧑
This project is mantained by:
* [Tubagus Saifulloh](https://github.com/bagus2x)
* [Hafizh Daffa](https://github.com/HafizhDaffa)
