#!/bin/bash

# Скрипт для сборки debug версии APK

echo "🚀 Начинаем сборку Debug APK..."
echo ""

# Проверка наличия gradlew
if [ ! -f "./gradlew" ]; then
    echo "❌ Ошибка: gradlew не найден!"
    echo "Убедитесь, что вы находитесь в корневой директории проекта."
    exit 1
fi

# Делаем gradlew исполняемым
chmod +x ./gradlew

# Очистка предыдущей сборки
echo "🧹 Очистка предыдущей сборки..."
./gradlew clean

# Сборка Debug APK
echo ""
echo "🔨 Сборка Debug APK..."
./gradlew assembleDebug

# Проверка успешности сборки
if [ $? -eq 0 ]; then
    echo ""
    echo "✅ Сборка успешно завершена!"
    echo ""
    echo "📦 APK файл находится здесь:"
    echo "   app/build/outputs/apk/debug/app-debug.apk"
    echo ""
    
    # Проверяем размер файла
    if [ -f "app/build/outputs/apk/debug/app-debug.apk" ]; then
        size=$(du -h "app/build/outputs/apk/debug/app-debug.apk" | cut -f1)
        echo "📊 Размер APK: $size"
    fi
else
    echo ""
    echo "❌ Ошибка при сборке!"
    echo "Проверьте логи выше для получения подробностей."
    exit 1
fi
