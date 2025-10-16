Messenger — учебное Android-приложение (Kotlin)

Минимальный мессенджер-шаблон с одной Activity, тремя вкладками (Новости, Профиль, Настройки) и навигацией через Navigation Component. Разметки и стили приведены к Material 3, добавлено логирование жизненного цикла экранов.

Скриншоты/видео по желанию можно добавить в раздел ниже.

## Возможности
- Single-Activity + Navigation Component
- Bottom Navigation (3 вкладки)
- Material 3 UI: Toolbar, карточки, типографика
- Экран настроек с переключателем темы (светлая/тёмная)
- Логирование жизненного цикла Activity/Fragment

## Требования
- JDK 17 (Temurin/Oracle)
- Android SDK (compileSdk 36, targetSdk 36)
- Gradle Wrapper 8.4 (поставляется в репозитории)

## Сборка
Быстрый старт через Makefile (macOS/Linux):

```bash
make build      # собрать Debug APK
make install    # установить на устройство (проверяет наличие adb)
make run        # запустить MainActivity
make logs       # логи по тегам приложения
```

Или скриптом:

```bash
./build_debug.sh
```

Или вручную:

```bash
chmod +x gradlew
export JAVA_HOME=$(/usr/libexec/java_home -v 17)
./gradlew clean :app:assembleDebug
```

APK будет здесь: `app/build/outputs/apk/debug/app-debug.apk`.

## Установка на устройство/эмулятор
Скриптом:

```bash
./install_and_run.sh
```

Скрипт проверяет наличие `adb` и подсказывает установку на macOS: `brew install android-platform-tools`.

Вручную (опционально):

```bash
adb install -r app/build/outputs/apk/debug/app-debug.apk
adb shell am start -n com.example.messenger/.MainActivity
```

## Структура проекта
- `app/src/main/java/com/example/messenger/`
	- `MainActivity.kt` — хост Activity, Bottom Navigation + Toolbar
	- `ui/news/NewsFeedFragment.kt` — новости (плейсхолдер)
	- `ui/profile/ProfileFragment.kt` — профиль
	- `ui/settings/SettingsFragment.kt` — настройки (переключатель темы, уведомления)
- `app/src/main/res/layout/` — разметки экранов (Material 3)
- `app/src/main/res/navigation/nav_graph.xml` — граф навигации
- `app/src/main/res/menu/bottom_nav_menu.xml` — пункты нижней навигации
- `build_debug.sh`, `install_and_run.sh` — вспомогательные скрипты
 - `Makefile` — упрощённые команды (build/install/run/logs/open-apk/copy-release)

## Полезно знать
- Тема: `Theme.Material3.DayNight.NoActionBar`
- Переключение темы: `AppCompatDelegate.setDefaultNightMode(...)`
- Журналы lifecycle: теги `MainActivity`, `NewsFeedFragment`, `ProfileFragment`, `SettingsFragment`

## Троблшутинг
- Kotlin/Gradle ругается на Java 25/18
	- Используйте JDK 17: `export JAVA_HOME=$(/usr/libexec/java_home -v 17)`
- Не найден Android SDK
	- Убедитесь, что `local.properties` указывает корректный путь: `sdk.dir=/Users/<you>/Library/Android/sdk`
- Нет платформы compileSdk
	- Установите соответствующую платформу в SDK Manager (api 36) и пробуйте снова.

---

Если нужна публикация APK в репозитории (releases/), скажите — добавлю копирование результата сборки в отдельную папку и git-коммит.
