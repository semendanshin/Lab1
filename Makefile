SHELL := /bin/bash

# Detect macOS and set JAVA_HOME to JDK 17 automatically if available
ifeq ($(shell uname),Darwin)
  export JAVA_HOME := $(shell /usr/libexec/java_home -v 17 2>/dev/null)
endif

GRADLEW := ./gradlew
APP_ID := com.example.messenger
MAIN_ACTIVITY := $(APP_ID)/.MainActivity
APK_DEBUG := app/build/outputs/apk/debug/app-debug.apk

.PHONY: help setup build clean install run logs rebuild open-apk copy-release

help:
	@echo "Available targets:"
	@echo "  setup        - Make gradlew executable"
	@echo "  build        - Assemble Debug APK"
	@echo "  clean        - Clean project"
	@echo "  rebuild      - Clean + Build"
	@echo "  install      - Install Debug APK to a connected device"
	@echo "  run          - Launch the app MainActivity on device"
	@echo "  logs         - Tail logcat with app tags"
	@echo "  open-apk     - Open the built APK in Finder (macOS)"
	@echo "  copy-release - Copy APK to releases/ folder"

setup:
	@chmod +x $(GRADLEW)

build: setup
	@echo "Building Debug APK..."
	@$(GRADLEW) :app:assembleDebug
	@echo "APK: $(APK_DEBUG)"

clean:
	@$(GRADLEW) clean

rebuild: clean build

install: build
	@if ! command -v adb >/dev/null 2>&1; then \
		echo "adb not found. On macOS: brew install android-platform-tools"; \
		exit 1; \
	fi
	@if [ ! -f "$(APK_DEBUG)" ]; then \
		echo "APK not found, run 'make build'"; \
		exit 1; \
	fi
	@adb install -r "$(APK_DEBUG)"

run:
	@if ! command -v adb >/dev/null 2>&1; then \
		echo "adb not found. On macOS: brew install android-platform-tools"; \
		exit 1; \
	fi
	@adb shell am start -n $(MAIN_ACTIVITY)

logs:
	@if ! command -v adb >/dev/null 2>&1; then \
		echo "adb not found. On macOS: brew install android-platform-tools"; \
		exit 1; \
	fi
	@adb logcat -s MainActivity NewsFeedFragment ProfileFragment SettingsFragment

open-apk:
	@if [ -f "$(APK_DEBUG)" ]; then \
		open "$(APK_DEBUG)"; \
	else \
		echo "APK not found. Run 'make build' first."; \
	fi

copy-release: build
	@mkdir -p releases
	@cp "$(APK_DEBUG)" releases/messenger-v1.0-debug.apk
	@echo "Copied to releases/messenger-v1.0-debug.apk"
