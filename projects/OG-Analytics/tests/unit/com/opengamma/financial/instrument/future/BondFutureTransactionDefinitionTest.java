/**
 * Copyright (C) 2011 - present by OpenGamma Inc. and the OpenGamma group of companies
 * 
 * Please see distribution for license.
 */
package com.opengamma.financial.instrument.future;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;

import javax.time.calendar.Period;
import javax.time.calendar.ZonedDateTime;

import org.testng.annotations.Test;

import com.opengamma.financial.convention.businessday.BusinessDayConvention;
import com.opengamma.financial.convention.businessday.BusinessDayConventionFactory;
import com.opengamma.financial.convention.calendar.Calendar;
import com.opengamma.financial.convention.calendar.MondayToFridayCalendar;
import com.opengamma.financial.convention.daycount.DayCount;
import com.opengamma.financial.convention.daycount.DayCountFactory;
import com.opengamma.financial.convention.yield.YieldConvention;
import com.opengamma.financial.convention.yield.YieldConventionFactory;
import com.opengamma.financial.instrument.bond.BondFixedSecurityDefinition;
import com.opengamma.financial.interestrate.future.definition.BondFutureSecurity;
import com.opengamma.financial.interestrate.future.definition.BondFutureTransaction;
import com.opengamma.util.money.Currency;
import com.opengamma.util.time.DateUtil;

/**
 * Tests related to bond futures transaction Definition construction.
 */
public class BondFutureTransactionDefinitionTest {
  // 5-Year U.S. Treasury Note Futures: FVU1
  private static final Currency CUR = Currency.USD;
  private static final Period PAYMENT_TENOR = Period.ofMonths(6);
  private static final Calendar CALENDAR = new MondayToFridayCalendar("A");
  private static final DayCount DAY_COUNT = DayCountFactory.INSTANCE.getDayCount("Actual/Actual ISDA");
  private static final BusinessDayConvention BUSINESS_DAY = BusinessDayConventionFactory.INSTANCE.getBusinessDayConvention("Following");
  private static final boolean IS_EOM = false;
  private static final int SETTLEMENT_DAYS = 1;
  private static final YieldConvention YIELD_CONVENTION = YieldConventionFactory.INSTANCE.getYieldConvention("STREET CONVENTION");
  private static final int NB_BOND = 7;
  private static final Period[] BOND_TENOR = new Period[] {Period.ofYears(5), Period.ofYears(5), Period.ofYears(5), Period.ofYears(8), Period.ofYears(5), Period.ofYears(5), Period.ofYears(5)};
  private static final ZonedDateTime[] START_ACCRUAL_DATE = new ZonedDateTime[] {DateUtil.getUTCDate(2010, 11, 30), DateUtil.getUTCDate(2010, 12, 31), DateUtil.getUTCDate(2011, 1, 31),
      DateUtil.getUTCDate(2008, 2, 29), DateUtil.getUTCDate(2011, 3, 31), DateUtil.getUTCDate(2011, 4, 30), DateUtil.getUTCDate(2011, 5, 31)};
  private static final double[] RATE = new double[] {0.01375, 0.02125, 0.0200, 0.02125, 0.0225, 0.0200, 0.0175};
  private static final double[] CONVERSION_FACTOR = new double[] {.8317, .8565, .8493, .8516, .8540, .8417, .8292};
  private static final ZonedDateTime[] MATURITY_DATE = new ZonedDateTime[NB_BOND];
  private static final BondFixedSecurityDefinition[] BASKET_DEFINITION = new BondFixedSecurityDefinition[NB_BOND];
  static {
    for (int loopbasket = 0; loopbasket < NB_BOND; loopbasket++) {
      MATURITY_DATE[loopbasket] = START_ACCRUAL_DATE[loopbasket].plus(BOND_TENOR[loopbasket]);
      BASKET_DEFINITION[loopbasket] = BondFixedSecurityDefinition.from(CUR, MATURITY_DATE[loopbasket], START_ACCRUAL_DATE[loopbasket], PAYMENT_TENOR, RATE[loopbasket], SETTLEMENT_DAYS, CALENDAR,
          DAY_COUNT, BUSINESS_DAY, YIELD_CONVENTION, IS_EOM);
    }
  }
  private static final ZonedDateTime LAST_TRADING_DATE = DateUtil.getUTCDate(2011, 9, 21);
  private static final ZonedDateTime FIRST_NOTICE_DATE = DateUtil.getUTCDate(2011, 8, 31);
  private static final ZonedDateTime LAST_NOTICE_DATE = DateUtil.getUTCDate(2011, 9, 29);
  private static final double NOTIONAL = 100000;
  private static final BondFutureSecurityDefinition FUTURE_DEFINITION = new BondFutureSecurityDefinition(LAST_TRADING_DATE, FIRST_NOTICE_DATE, LAST_NOTICE_DATE, NOTIONAL, BASKET_DEFINITION,
      CONVERSION_FACTOR);
  // Transaction
  private static final int QUANTITY = 4321;
  private static final double TRADE_PRICE = 1.0987;
  private static final ZonedDateTime TRADE_DATE = DateUtil.getUTCDate(2011, 6, 21);
  private static final BondFutureTransactionDefinition FUTURE_TRANSACTION_DEFINITION = new BondFutureTransactionDefinition(FUTURE_DEFINITION, QUANTITY, TRADE_DATE, TRADE_PRICE);

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void testNullFuture() {
    new BondFutureTransactionDefinition(null, QUANTITY, TRADE_DATE, TRADE_PRICE);
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void testNullTradeDate() {
    new BondFutureTransactionDefinition(FUTURE_DEFINITION, QUANTITY, null, TRADE_PRICE);
  }

  @Test
  /**
   * Tests the getter methods.
   */
  public void getter() {
    assertEquals("Bond future transaction definition: underlying", FUTURE_DEFINITION, FUTURE_TRANSACTION_DEFINITION.getUnderlyingFuture());
    assertEquals("Bond future transaction definition: quantity", QUANTITY, FUTURE_TRANSACTION_DEFINITION.getQuantity());
    assertEquals("Bond future transaction definition: trade date", TRADE_DATE, FUTURE_TRANSACTION_DEFINITION.getTradeDate());
    assertEquals("Bond future transaction definition: trade price", TRADE_PRICE, FUTURE_TRANSACTION_DEFINITION.getTradePrice());
  }

  @Test
  /**
   * Tests the equal and hash code methods.
   */
  public void equalHash() {
    BondFutureTransactionDefinition other = new BondFutureTransactionDefinition(FUTURE_DEFINITION, QUANTITY, TRADE_DATE, TRADE_PRICE);
    assertTrue(FUTURE_TRANSACTION_DEFINITION.equals(other));
    assertTrue(FUTURE_TRANSACTION_DEFINITION.hashCode() == other.hashCode());
    BondFutureTransactionDefinition modifiedFuture;
    modifiedFuture = new BondFutureTransactionDefinition(FUTURE_DEFINITION, QUANTITY + 1, TRADE_DATE, TRADE_PRICE);
    assertFalse(FUTURE_TRANSACTION_DEFINITION.equals(modifiedFuture));
    modifiedFuture = new BondFutureTransactionDefinition(FUTURE_DEFINITION, QUANTITY, LAST_TRADING_DATE, TRADE_PRICE);
    assertFalse(FUTURE_TRANSACTION_DEFINITION.equals(modifiedFuture));
    modifiedFuture = new BondFutureTransactionDefinition(FUTURE_DEFINITION, QUANTITY, TRADE_DATE, TRADE_PRICE + 0.001);
    assertFalse(FUTURE_TRANSACTION_DEFINITION.equals(modifiedFuture));
  }

  @Test
  /**
   * Tests the toDerivative method.
   */
  public void toDerivativeOnTradeDate() {
    final ZonedDateTime referenceDate = DateUtil.getUTCDate(2011, 6, 21);
    final String creditCruveName = "Credit";
    final String repoCurveName = "Repo";
    final String[] curvesName = {creditCruveName, repoCurveName};
    final double lastMarginPrice = 1.0234;
    BondFutureTransaction futureConverted = FUTURE_TRANSACTION_DEFINITION.toDerivative(referenceDate, lastMarginPrice, curvesName);
    BondFutureSecurity security = FUTURE_DEFINITION.toDerivative(referenceDate, curvesName);
    BondFutureTransaction futureConstructed = new BondFutureTransaction(security, QUANTITY, TRADE_PRICE);
    assertEquals("Bond future transaction definition: to derivative", futureConstructed, futureConverted);
  }

  @Test
  /**
   * Tests the toDerivative method.
   */
  public void toDerivativeAfterTradeDate() {
    final ZonedDateTime referenceDate = DateUtil.getUTCDate(2011, 6, 22);
    final String creditCruveName = "Credit";
    final String repoCurveName = "Repo";
    final String[] curvesName = {creditCruveName, repoCurveName};
    final double lastMarginPrice = 1.0234;
    BondFutureTransaction futureConverted = FUTURE_TRANSACTION_DEFINITION.toDerivative(referenceDate, lastMarginPrice, curvesName);
    BondFutureSecurity security = FUTURE_DEFINITION.toDerivative(referenceDate, curvesName);
    BondFutureTransaction futureConstructed = new BondFutureTransaction(security, QUANTITY, lastMarginPrice);
    assertEquals("Bond future transaction definition: to derivative", futureConstructed, futureConverted);
  }

}
