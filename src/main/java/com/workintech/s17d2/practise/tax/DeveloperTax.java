package com.workintech.s17d2.practise.tax;

import org.springframework.stereotype.Component;

@Component
public class DeveloperTax implements Taxable{
    @Override
    public double getSimpleTaxRate() {
        return 0.25;
    }

    @Override
    public double getMiddleTaxRate() {
        return 0.4;
    }

    @Override
    public double getUpperTaxRate() {
        return 0.5;
    }
}
