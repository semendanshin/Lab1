package com.example.messenger.ui.settings

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.example.messenger.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var sharedPreferences: SharedPreferences

    companion object {
        private const val TAG = "SettingsFragment"
        private const val PREFS_NAME = "AppSettings"
        private const val KEY_DARK_THEME = "dark_theme"
        private const val KEY_NOTIFICATIONS = "notifications"
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG, "onAttach: Fragment прикреплён к активности")
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: Fragment создан")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "onCreateView: Создание View для Fragment")
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated: View создан")
        
        setupSettings()
    }

    private fun setupSettings() {
        // Загрузка сохранённых настроек
        val isDarkTheme = sharedPreferences.getBoolean(KEY_DARK_THEME, false)
        val isNotificationsEnabled = sharedPreferences.getBoolean(KEY_NOTIFICATIONS, true)
        
        binding.switchTheme.isChecked = isDarkTheme
        binding.switchNotifications.isChecked = isNotificationsEnabled
        
        // Обработчик переключателя темы
        binding.switchTheme.setOnCheckedChangeListener { _, isChecked ->
            Log.d(TAG, "Тема изменена: ${if (isChecked) "Тёмная" else "Светлая"}")
            sharedPreferences.edit().putBoolean(KEY_DARK_THEME, isChecked).apply()
            
            // Применение темы
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
        
        // Обработчик переключателя уведомлений
        binding.switchNotifications.setOnCheckedChangeListener { _, isChecked ->
            Log.d(TAG, "Уведомления: ${if (isChecked) "Включены" else "Выключены"}")
            sharedPreferences.edit().putBoolean(KEY_NOTIFICATIONS, isChecked).apply()
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: Fragment запущен")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: Fragment возобновлён")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: Fragment приостановлен")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: Fragment остановлен")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView: View уничтожен")
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: Fragment уничтожен")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(TAG, "onDetach: Fragment откреплён от активности")
    }
}
