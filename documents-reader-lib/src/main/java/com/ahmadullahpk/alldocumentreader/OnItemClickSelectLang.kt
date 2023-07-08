package com.ahmadullahpk.alldocumentreader

import com.ahmadullahpk.alldocumentreader.dataType.LangDataType

interface OnItemClickSelectLang {

    fun onItemClick(langDataType: LangDataType?, i: Int)
}