# VGSShow-android

## Table of contents
- [Overview](#overview)
- [Getting started](#getting-started)
- [Parameters](#parameters)
- [Tooling](#tooling)
- [API Reference](#api-reference)
- [Packages](#third-party-libraries)


## Overview
Using VGSShow sdk we can show sensitive info for card

## Getting started
Configure VGSShow Sample :
- Clone repository
```groovy
git clone git@github.com:solidfi/vgs-show-android.git
OR
git clone https://github.com/solidfi/vgs-show-android.git
```
- Open it in [Android Studio](https://developer.android.com/studio)
- Run the project and test it out.

## Parameters

In order to start the project we need below parameters. All fields are mandatory

- VGSVaultID : Talk to our solutions team (solutions@solidfi.com) to get the ID via a secured method. (It will different base on sandbox and live)
- CardID : Id of your card
- ShowToken : You will get it from the "show_token" api from the backend
- Environment :  Select live or sandbox environment

Notes: ShowToken can be used only once to view the card details. You need to call "show_token" api to generate new show_token

## Tooling
- Android 5.0 +
- Android Studio Bumblebee
- Kotlin 1.6.0

## API Reference
- VGS Show Integration : https://www.verygoodsecurity.com/docs/vgs-show/android-sdk/integration
- [Solid Dev Center](https://www.solidfi.com/docs/introduction)

## Packages
- VGSShowSDK
