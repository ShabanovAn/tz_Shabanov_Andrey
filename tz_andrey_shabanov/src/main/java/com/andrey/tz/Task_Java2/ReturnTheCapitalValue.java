package com.andrey.tz.Task_Java2;

import com.ibm.icu.text.RuleBasedNumberFormat;

import java.math.BigDecimal;
import java.util.Locale;

public class ReturnTheCapitalValue {
    public static void main(String[] args) {

        BigDecimal cost = new BigDecimal("99.99");
        RuleBasedNumberFormat nf = new RuleBasedNumberFormat(Locale.forLanguageTag("ru"), RuleBasedNumberFormat.SPELLOUT);
        System.out.println(nf.format(cost));
    }
}
