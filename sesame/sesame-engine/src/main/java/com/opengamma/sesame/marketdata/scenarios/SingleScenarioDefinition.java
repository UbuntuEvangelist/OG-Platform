/**
 * Copyright (C) 2015 - present by OpenGamma Inc. and the OpenGamma group of companies
 * <p/>
 * Please see distribution for license.
 */
package com.opengamma.sesame.marketdata.scenarios;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import org.joda.beans.Bean;
import org.joda.beans.BeanDefinition;
import org.joda.beans.ImmutableBean;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.PropertyDefinition;
import org.joda.beans.impl.direct.DirectFieldsBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaBean;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

import com.google.common.collect.ImmutableList;

/**
 * Defines the scenario for a single set of calculations.
 * <p>
 * A scenario is made up of one or more {@link SinglePerturbationMapping}. Each mapping contains a filter
 * that chooses items of market data and a perturbation that applies a shock to the market data.
 */
@BeanDefinition
public final class SingleScenarioDefinition implements ImmutableBean {

  /** The name of the scenario. */
  @PropertyDefinition(validate = "notEmpty")
  private final String scenarioName;

  /** The filters and perturbations that make up the scenario. */
  @PropertyDefinition(validate = "notNull")
  private final List<SinglePerturbationMapping> mappings;

  /**
   * Returns a definition of the scenario for a single set of calculations.
   *
   * @return a definition of the scenario for a single set of calculations
   */
  public static SingleScenarioDefinition of(String scenarioName, List<SinglePerturbationMapping> mappings) {
    return new SingleScenarioDefinition(scenarioName, mappings);
  }

  /**
   * Returns a definition of the scenario for a single set of calculations.
   *
   * @return a definition of the scenario for a single set of calculations
   */
  public static SingleScenarioDefinition of(String scenarioName, SinglePerturbationMapping... mappings) {
    return new SingleScenarioDefinition(scenarioName, ImmutableList.copyOf(mappings));
  }

  /**
   * Returns a definition for a scenario with no perturbations applied to the market data.
   *
   * @return a definition for a scenario with no perturbations applied to the market data
   */
  public static SingleScenarioDefinition base() {
    return new SingleScenarioDefinition("Base", ImmutableList.<SinglePerturbationMapping>of());
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code SingleScenarioDefinition}.
   * @return the meta-bean, not null
   */
  public static SingleScenarioDefinition.Meta meta() {
    return SingleScenarioDefinition.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(SingleScenarioDefinition.Meta.INSTANCE);
  }

  /**
   * Returns a builder used to create an instance of the bean.
   * @return the builder, not null
   */
  public static SingleScenarioDefinition.Builder builder() {
    return new SingleScenarioDefinition.Builder();
  }

  private SingleScenarioDefinition(
      String scenarioName,
      List<SinglePerturbationMapping> mappings) {
    JodaBeanUtils.notEmpty(scenarioName, "scenarioName");
    JodaBeanUtils.notNull(mappings, "mappings");
    this.scenarioName = scenarioName;
    this.mappings = ImmutableList.copyOf(mappings);
  }

  @Override
  public SingleScenarioDefinition.Meta metaBean() {
    return SingleScenarioDefinition.Meta.INSTANCE;
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
   * Gets the name of the scenario.
   * @return the value of the property, not empty
   */
  public String getScenarioName() {
    return scenarioName;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the filters and perturbations that make up the scenario.
   * @return the value of the property, not null
   */
  public List<SinglePerturbationMapping> getMappings() {
    return mappings;
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
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      SingleScenarioDefinition other = (SingleScenarioDefinition) obj;
      return JodaBeanUtils.equal(getScenarioName(), other.getScenarioName()) &&
          JodaBeanUtils.equal(getMappings(), other.getMappings());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash = hash * 31 + JodaBeanUtils.hashCode(getScenarioName());
    hash = hash * 31 + JodaBeanUtils.hashCode(getMappings());
    return hash;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(96);
    buf.append("SingleScenarioDefinition{");
    buf.append("scenarioName").append('=').append(getScenarioName()).append(',').append(' ');
    buf.append("mappings").append('=').append(JodaBeanUtils.toString(getMappings()));
    buf.append('}');
    return buf.toString();
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code SingleScenarioDefinition}.
   */
  public static final class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code scenarioName} property.
     */
    private final MetaProperty<String> _scenarioName = DirectMetaProperty.ofImmutable(
        this, "scenarioName", SingleScenarioDefinition.class, String.class);
    /**
     * The meta-property for the {@code mappings} property.
     */
    @SuppressWarnings({"unchecked", "rawtypes" })
    private final MetaProperty<List<SinglePerturbationMapping>> _mappings = DirectMetaProperty.ofImmutable(
        this, "mappings", SingleScenarioDefinition.class, (Class) List.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> _metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null,
        "scenarioName",
        "mappings");

    /**
     * Restricted constructor.
     */
    private Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case -1008330181:  // scenarioName
          return _scenarioName;
        case 194445669:  // mappings
          return _mappings;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public SingleScenarioDefinition.Builder builder() {
      return new SingleScenarioDefinition.Builder();
    }

    @Override
    public Class<? extends SingleScenarioDefinition> beanType() {
      return SingleScenarioDefinition.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return _metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code scenarioName} property.
     * @return the meta-property, not null
     */
    public MetaProperty<String> scenarioName() {
      return _scenarioName;
    }

    /**
     * The meta-property for the {@code mappings} property.
     * @return the meta-property, not null
     */
    public MetaProperty<List<SinglePerturbationMapping>> mappings() {
      return _mappings;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case -1008330181:  // scenarioName
          return ((SingleScenarioDefinition) bean).getScenarioName();
        case 194445669:  // mappings
          return ((SingleScenarioDefinition) bean).getMappings();
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
   * The bean-builder for {@code SingleScenarioDefinition}.
   */
  public static final class Builder extends DirectFieldsBeanBuilder<SingleScenarioDefinition> {

    private String scenarioName;
    private List<SinglePerturbationMapping> mappings = new ArrayList<SinglePerturbationMapping>();

    /**
     * Restricted constructor.
     */
    private Builder() {
    }

    /**
     * Restricted copy constructor.
     * @param beanToCopy  the bean to copy from, not null
     */
    private Builder(SingleScenarioDefinition beanToCopy) {
      this.scenarioName = beanToCopy.getScenarioName();
      this.mappings = new ArrayList<SinglePerturbationMapping>(beanToCopy.getMappings());
    }

    //-----------------------------------------------------------------------
    @Override
    public Object get(String propertyName) {
      switch (propertyName.hashCode()) {
        case -1008330181:  // scenarioName
          return scenarioName;
        case 194445669:  // mappings
          return mappings;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Builder set(String propertyName, Object newValue) {
      switch (propertyName.hashCode()) {
        case -1008330181:  // scenarioName
          this.scenarioName = (String) newValue;
          break;
        case 194445669:  // mappings
          this.mappings = (List<SinglePerturbationMapping>) newValue;
          break;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
      return this;
    }

    @Override
    public Builder set(MetaProperty<?> property, Object value) {
      super.set(property, value);
      return this;
    }

    @Override
    public Builder setString(String propertyName, String value) {
      setString(meta().metaProperty(propertyName), value);
      return this;
    }

    @Override
    public Builder setString(MetaProperty<?> property, String value) {
      super.setString(property, value);
      return this;
    }

    @Override
    public Builder setAll(Map<String, ? extends Object> propertyValueMap) {
      super.setAll(propertyValueMap);
      return this;
    }

    @Override
    public SingleScenarioDefinition build() {
      return new SingleScenarioDefinition(
          scenarioName,
          mappings);
    }

    //-----------------------------------------------------------------------
    /**
     * Sets the {@code scenarioName} property in the builder.
     * @param scenarioName  the new value, not empty
     * @return this, for chaining, not null
     */
    public Builder scenarioName(String scenarioName) {
      JodaBeanUtils.notEmpty(scenarioName, "scenarioName");
      this.scenarioName = scenarioName;
      return this;
    }

    /**
     * Sets the {@code mappings} property in the builder.
     * @param mappings  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder mappings(List<SinglePerturbationMapping> mappings) {
      JodaBeanUtils.notNull(mappings, "mappings");
      this.mappings = mappings;
      return this;
    }

    /**
     * Sets the {@code mappings} property in the builder
     * from an array of objects.
     * @param mappings  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder mappings(SinglePerturbationMapping... mappings) {
      return mappings(Arrays.asList(mappings));
    }

    //-----------------------------------------------------------------------
    @Override
    public String toString() {
      StringBuilder buf = new StringBuilder(96);
      buf.append("SingleScenarioDefinition.Builder{");
      buf.append("scenarioName").append('=').append(JodaBeanUtils.toString(scenarioName)).append(',').append(' ');
      buf.append("mappings").append('=').append(JodaBeanUtils.toString(mappings));
      buf.append('}');
      return buf.toString();
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
