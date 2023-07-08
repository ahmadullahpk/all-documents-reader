package com.ahmadullahpk.alldocumentreader.dataType

import java.util.*

class LangDataType(
    var name: String,
    var localLanguageName: String,
    var code: String,
    var isSelected: Boolean,
    var flag: String
) {

    companion object {
        val langDataTypes: ArrayList<LangDataType>
            get() {
                val appLanguages = ArrayList<LangDataType>()
                appLanguages.add(LangDataType("English", "Default", "en", false, "uk.png"))
                appLanguages.add(LangDataType("Arabic", "العربية", "ar", false, "uae.png"))
                appLanguages.add(LangDataType("Spanish", "Español", "es", false, "spain.png"))
                appLanguages.add(LangDataType("Russian", "Русский", "ru", false, "russia.png"))
                appLanguages.add(LangDataType("Chinese", "中文", "zh", false, "china.png"))
                appLanguages.add(LangDataType("India", "हिंदी", "hi", false, "india.png"))
                appLanguages.add(LangDataType("French", "français", "fr", false, "abc.png"))
                appLanguages.add(LangDataType("Bengali", "বাংলা", "bn", false, "bangladesh.png"))
                appLanguages.add(
                    LangDataType(
                        "Indonesian",
                        "Indonesia",
                        "id",
                        false,
                        "indonesia.png"
                    )
                )
                appLanguages.add(LangDataType("Japanese", "日本語", "ja", false, "japan.png"))
                appLanguages.add(LangDataType("Malay", "Malay", "ms", false, "malaysia.png"))
                appLanguages.add(
                    LangDataType(
                        "Portuguese",
                        "português",
                        "pt",
                        false,
                        "portugal.png"
                    )
                )
                appLanguages.add(LangDataType("Turkish", "Türkçe ", "tr", false, "turkey.png"))
                appLanguages.add(LangDataType("Urdu", "اُردُو ", "ur", false, "pakistan.png"))
                appLanguages.add(LangDataType("German", "German", "de", false, "germany.png"))
                appLanguages.add(LangDataType("Korean", "韓國語 ", "ko", false, "korean.png"))
                return appLanguages
            }
    }
}