package com.example.sharedpreferences

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat

// PreferenceFragmentCompat() 추상 클래스를 상속
class SettingFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        // 리소스에 접근하는 형식은 R.리소스디렉터리명.파일명입니다.
        addPreferencesFromResource(R.xml.preferences)
    }
}