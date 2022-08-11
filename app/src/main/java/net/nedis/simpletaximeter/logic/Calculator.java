package net.nedis.simpletaximeter.logic;

import static net.nedis.simpletaximeter.Constants.CURRENCY_SCALE;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Calculator {

    private final BigDecimal kmInCity;
    private final BigDecimal kmOutCity;

    private final BigDecimal minCost;
    private final BigDecimal minCostDistance;
    private final BigDecimal costInCity;
    private final BigDecimal costOutCity;

    private Calculator(BigDecimal kmInCity, BigDecimal kmOutCity,
                       BigDecimal minCost, BigDecimal minCostDistance,
                       BigDecimal costInCity, BigDecimal costOutCity) {
        this.kmInCity = kmInCity;
        this.kmOutCity = kmOutCity;
        this.minCost = minCost;
        this.minCostDistance = minCostDistance;
        this.costInCity = costInCity;
        this.costOutCity = costOutCity;
    }

    public BigDecimal calculate() {
        BigDecimal result = BigDecimal.ZERO;
        if (kmInCity.compareTo(minCostDistance) >= 0) {
            result = kmInCity.subtract(minCostDistance);
        }
        result = result.multiply(costInCity);

        result = result.add(kmOutCity.multiply(costOutCity));

        result = result.add(minCost);

        return result.setScale(CURRENCY_SCALE, RoundingMode.HALF_UP);
    }

    public static final class Builder {

        private BigDecimal kmInCity;

        private BigDecimal kmOutCity;

        private BigDecimal minCost;

        private BigDecimal minCostDistance;

        private BigDecimal costInCity;

        private BigDecimal costOutCity;

        public Builder setKmInCity(BigDecimal kmInCity) {
            this.kmInCity = kmInCity;
            return this;
        }

        public Builder setKmOutCity(BigDecimal kmOutCity) {
            this.kmOutCity = kmOutCity;
            return this;
        }

        public Builder setMinCost(BigDecimal minCost) {
            this.minCost = minCost;
            return this;
        }

        public Builder setMinCostDistance(BigDecimal minCostDistance) {
            this.minCostDistance = minCostDistance;
            return this;
        }

        public Builder setCostInCity(BigDecimal costInCity) {
            this.costInCity = costInCity;
            return this;
        }

        public Builder setCostOutCity(BigDecimal costOutCity) {
            this.costOutCity = costOutCity;
            return this;
        }

        public Calculator build() {
            return new Calculator(
                    kmInCity, kmOutCity, minCost, minCostDistance, costInCity, costOutCity
            );
        }
    }
}
