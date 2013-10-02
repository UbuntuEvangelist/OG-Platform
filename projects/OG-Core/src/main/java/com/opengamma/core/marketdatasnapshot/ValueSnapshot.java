/**
 * Copyright (C) 2011 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.core.marketdatasnapshot;

import java.io.Serializable;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import org.fudgemsg.FudgeMsg;
import org.fudgemsg.MutableFudgeMsg;
import org.fudgemsg.mapping.FudgeDeserializer;
import org.fudgemsg.mapping.FudgeSerializer;
import org.joda.beans.Bean;
import org.joda.beans.BeanDefinition;
import org.joda.beans.ImmutableBean;
import org.joda.beans.ImmutableConstructor;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.PropertyDefinition;
import org.joda.beans.impl.BasicImmutableBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaBean;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

import com.opengamma.util.PublicSPI;

/**
 * A snapshot of a market data point taken at a particular instant, potentially altered by hand.
 * <p>
 * This class is mutable and not thread-safe.
 */
@PublicSPI
@BeanDefinition
public final class ValueSnapshot implements ImmutableBean, Serializable {

  /** Serialization version. */
  private static final long serialVersionUID = 1L;

  /**
   * The value sampled from the market.
   */
  @PropertyDefinition
  private final Object _marketValue;
  /**
   * The value entered by the user, null if not overridden.
   */
  @PropertyDefinition
  private final Object _overrideValue;

  /**
   * Creates an instance with the real value and optional override.
   * 
   * @param marketValue  the real market value
   * @param overrideValue  the override, null if no override
   * @return the snapshot, not null
   */
  public static ValueSnapshot of(Object marketValue, Object overrideValue) {
    return new ValueSnapshot(marketValue, overrideValue);
  }

  /**
   * Creates an instance with the real value and no override.
   * 
   * @param marketValue  the real market value
   * @return the snapshot, not null
   */
  public static ValueSnapshot of(Object marketValue) {
    return new ValueSnapshot(marketValue, null);
  }

  /**
   * Creates an instance with the real value and no override.
   * 
   * @param marketValue  the real market value
   * @return the snapshot, not null
   */
  public static ValueSnapshot of(double marketValue) {
    return new ValueSnapshot(marketValue, null);
  }

  //-------------------------------------------------------------------------
  /**
   * Creates an instance with the real value and optional override.
   * 
   * @param marketValue  the real market value
   * @param overrideValue  the override, null if no override
   */
  @ImmutableConstructor
  private ValueSnapshot(Object marketValue, Object overrideValue) {
    super();
    _marketValue = marketValue;
    _overrideValue = overrideValue;
  }

  //-------------------------------------------------------------------------
  // TODO: externalize the Fudge representation to a builder
  /**
   * Creates a Fudge representation of the snapshot value:
   * <pre>
   *   message {
   *     optional double marketValue;
   *     optional double overrideValue;
   *   }
   * </pre>
   * 
   * @param serializer the Fudge serialization context, not null
   * @return the message representation
   */
  public MutableFudgeMsg toFudgeMsg(final FudgeSerializer serializer) {
    final MutableFudgeMsg msg = serializer.newMessage();
    if (getMarketValue() != null) {
      msg.add("marketValue", getMarketValue());
    }
    if (getOverrideValue() != null) {
      msg.add("overrideValue", getOverrideValue());
    }
    return msg;
  }

  /**
   * Creates a snapshot value object from a Fudge message representation. See {@link #toFudgeMsg}
   * for the message format.
   * 
   * @param deserializer the Fudge deserialization context, not null
   * @param msg message containing the value representation, not null
   * @return a snapshot object
   */
  public static ValueSnapshot fromFudgeMsg(final FudgeDeserializer deserializer, final FudgeMsg msg) {
    Object marketValue = msg.getValue("marketValue");
    Object overrideValue = msg.getValue("overrideValue");
    return new ValueSnapshot(marketValue, overrideValue);
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code ValueSnapshot}.
   * @return the meta-bean, not null
   */
  public static ValueSnapshot.Meta meta() {
    return ValueSnapshot.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(ValueSnapshot.Meta.INSTANCE);
  }

  /**
   * Returns a builder used to create an instance of the bean.
   *
   * @return the builder, not null
   */
  public static ValueSnapshot.Builder builder() {
    return new ValueSnapshot.Builder();
  }

  @Override
  public ValueSnapshot.Meta metaBean() {
    return ValueSnapshot.Meta.INSTANCE;
  }

  @Override
  public <R> Property<R> property(String propertyName) {
    return metaBean().<R>metaProperty(propertyName).createProperty(this);
  }

  @Override
  public Set<String> propertyNames() {
    return metaBean().metaPropertyMap().keySet();
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the value sampled from the market.
   * @return the value of the property
   */
  public Object getMarketValue() {
    return _marketValue;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the value entered by the user, null if not overridden.
   * @return the value of the property
   */
  public Object getOverrideValue() {
    return _overrideValue;
  }

  //-----------------------------------------------------------------------
  /**
   * Returns a builder that allows this bean to be mutated.
   * @return the mutable builder, not null
   */
  public Builder toBuilder() {
    return new Builder(this);
  }

  @Override
  public ValueSnapshot clone() {
    return this;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      ValueSnapshot other = (ValueSnapshot) obj;
      return JodaBeanUtils.equal(getMarketValue(), other.getMarketValue()) &&
          JodaBeanUtils.equal(getOverrideValue(), other.getOverrideValue());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash += hash * 31 + JodaBeanUtils.hashCode(getMarketValue());
    hash += hash * 31 + JodaBeanUtils.hashCode(getOverrideValue());
    return hash;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(96);
    buf.append("ValueSnapshot{");
    buf.append("marketValue").append('=').append(getMarketValue()).append(',').append(' ');
    buf.append("overrideValue").append('=').append(getOverrideValue());
    buf.append('}');
    return buf.toString();
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code ValueSnapshot}.
   */
  public static final class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code marketValue} property.
     */
    private final MetaProperty<Object> _marketValue = DirectMetaProperty.ofImmutable(
        this, "marketValue", ValueSnapshot.class, Object.class);
    /**
     * The meta-property for the {@code overrideValue} property.
     */
    private final MetaProperty<Object> _overrideValue = DirectMetaProperty.ofImmutable(
        this, "overrideValue", ValueSnapshot.class, Object.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> _metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null,
        "marketValue",
        "overrideValue");

    /**
     * Restricted constructor.
     */
    protected Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case 276583061:  // marketValue
          return _marketValue;
        case 2057831685:  // overrideValue
          return _overrideValue;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public ValueSnapshot.Builder builder() {
      return new ValueSnapshot.Builder();
    }

    @Override
    public Class<? extends ValueSnapshot> beanType() {
      return ValueSnapshot.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return _metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code marketValue} property.
     * @return the meta-property, not null
     */
    public MetaProperty<Object> marketValue() {
      return _marketValue;
    }

    /**
     * The meta-property for the {@code overrideValue} property.
     * @return the meta-property, not null
     */
    public MetaProperty<Object> overrideValue() {
      return _overrideValue;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case 276583061:  // marketValue
          return ((ValueSnapshot) bean).getMarketValue();
        case 2057831685:  // overrideValue
          return ((ValueSnapshot) bean).getOverrideValue();
      }
      return super.propertyGet(bean, propertyName, quiet);
    }

    @Override
    protected void propertySet(Bean bean, String propertyName, Object newValue, boolean quiet) {
      metaProperty(propertyName);
      if (quiet) {
        return;
      }
      throw new UnsupportedOperationException("Property cannot be written: " + propertyName);
    }

  }

  //-----------------------------------------------------------------------
  /**
   * The bean-builder for {@code ValueSnapshot}.
   */
  public static final class Builder extends BasicImmutableBeanBuilder<ValueSnapshot> {

    private Object _marketValue;
    private Object _overrideValue;

    /**
     * Restricted constructor.
     */
    private Builder() {
      super(ValueSnapshot.Meta.INSTANCE);
    }

    /**
     * Restricted copy constructor.
     * @param beanToCopy  the bean to copy from, not null
     */
    private Builder(ValueSnapshot beanToCopy) {
      super(ValueSnapshot.Meta.INSTANCE);
      this._marketValue = beanToCopy.getMarketValue();
      this._overrideValue = beanToCopy.getOverrideValue();
    }

    //-----------------------------------------------------------------------
    @Override
    public Builder set(String propertyName, Object newValue) {
      switch (propertyName.hashCode()) {
        case 276583061:  // marketValue
          this._marketValue = (Object) newValue;
          break;
        case 2057831685:  // overrideValue
          this._overrideValue = (Object) newValue;
          break;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
      return this;
    }

    @Override
    public ValueSnapshot build() {
      return new ValueSnapshot(
          _marketValue,
          _overrideValue);
    }

    //-----------------------------------------------------------------------
    /**
     * Sets the {@code marketValue} property in the builder.
     * @param marketValue  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder marketValue(Object marketValue) {
      this._marketValue = marketValue;
      return this;
    }

    /**
     * Sets the {@code overrideValue} property in the builder.
     * @param overrideValue  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder overrideValue(Object overrideValue) {
      this._overrideValue = overrideValue;
      return this;
    }

    //-----------------------------------------------------------------------
    @Override
    public String toString() {
      StringBuilder buf = new StringBuilder(96);
      buf.append("ValueSnapshot.Builder{");
      buf.append("marketValue").append('=').append(_marketValue).append(',').append(' ');
      buf.append("overrideValue").append('=').append(_overrideValue);
      buf.append('}');
      return buf.toString();
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
