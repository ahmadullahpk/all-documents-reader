package com.ahmadullahpk.alldocumentreader.manageui

class HomeCardText(
    val sectionTitle: String,
    val cardTitle: String,
    val cardSubtitle: String,
    val cardSubPrimaryText: String
) {
    override fun toString(): String {
        return "HomeCardText{" +
                "sectionTitle='" + sectionTitle + '\'' +
                ", cardTitle='" + cardTitle + '\'' +
                ", cardSubtitle='" + cardSubtitle + '\'' +
                ", cardSubPrimaryText='" + cardSubPrimaryText + '\'' +
                '}'
    }

    override fun hashCode(): Int {
        val sectionTitle = sectionTitle
        val i: Int
        val hashCode = (sectionTitle.hashCode() ?: 0) * 31
        val cardTitle = cardTitle
        val hashCode2 = (hashCode + (cardTitle.hashCode() ?: 0)) * 31
        val cardSubtitle = cardSubtitle
        val hashCode3 = (hashCode2 + (cardSubtitle.hashCode() ?: 0)) * 31
        val cardSubPrimaryText = cardSubPrimaryText
        i = cardSubPrimaryText.hashCode()
        return hashCode3 + i
    }
}