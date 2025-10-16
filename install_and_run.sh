#!/bin/bash

# Скрипт для установки приложения на подключенное устройство

echo "📱 Установка приложения на устройство..."
echo ""

# Проверка наличия adb
if ! command -v adb >/dev/null 2>&1; then
    echo "❌ Команда 'adb' не найдена. Установите Android Platform Tools."
    if [[ "$(uname)" == "Darwin" ]]; then
        echo "👉 macOS: brew install android-platform-tools"
    fi
    exit 1
fi

# Проверка наличия APK
APK_PATH="app/build/outputs/apk/debug/app-debug.apk"

if [ ! -f "$APK_PATH" ]; then
    echo "❌ APK файл не найден!"
    echo "Сначала выполните сборку: ./build_debug.sh"
    exit 1
fi

# Проверка подключенных устройств
echo "🔍 Проверка подключенных устройств..."
devices=$(adb devices | grep -v "List" | grep "device" | wc -l)

if [ $devices -eq 0 ]; then
    echo "❌ Нет подключенных устройств!"
    echo ""
    echo "Подключите Android устройство или запустите эмулятор."
    exit 1
fi

echo "✅ Найдено устройств: $devices"
echo ""

# Установка APK
echo "📲 Установка APK..."
adb install -r "$APK_PATH"

if [ $? -eq 0 ]; then
    echo ""
    echo "✅ Приложение успешно установлено!"
    echo ""
    echo "🚀 Запуск приложения..."
    adb shell am start -n com.example.messenger/.MainActivity
    
    echo ""
    echo "📋 Для просмотра логов используйте:"
    echo "   adb logcat -s MainActivity NewsFeedFragment ProfileFragment SettingsFragment"
else
    echo ""
    echo "❌ Ошибка при установке!"
    exit 1
fi
