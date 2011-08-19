/**
 * Copyright (C) 2011 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */

package com.opengamma.language.convert;

import static com.opengamma.language.convert.TypeMap.ZERO_LOSS;

import java.util.Map;

import org.fudgemsg.FudgeContext;
import org.fudgemsg.FudgeFieldType;
import org.fudgemsg.FudgeMsg;
import org.fudgemsg.MutableFudgeMsg;
import org.fudgemsg.mapping.FudgeDeserializer;
import org.fudgemsg.mapping.FudgeSerializer;
import org.fudgemsg.types.SecondaryFieldType;
import org.fudgemsg.wire.types.FudgeWireType;

import com.opengamma.language.definition.JavaTypeInfo;
import com.opengamma.language.invoke.TypeConverter;

/**
 * Conversions using the Fudge type and object dictionaries. Conversions will use secondary types if applicable, falling back to
 * the message/object builders otherwise.  
 */
public final class FudgeTypeConverter implements TypeConverter {

  // TODO: handle nulls

  private static final JavaTypeInfo<Object> OBJECT = JavaTypeInfo.builder(Object.class).get();
  private static final JavaTypeInfo<FudgeMsg> FUDGE_MSG = JavaTypeInfo.builder(FudgeMsg.class).get();
  private static final Map<JavaTypeInfo<?>, Integer> FROM_OBJECT = TypeMap.of(ZERO_LOSS, OBJECT);
  private static final Map<JavaTypeInfo<?>, Integer> FROM_FUDGE_MSG = TypeMap.of(ZERO_LOSS, FUDGE_MSG);

  private final FudgeContext _fudgeContext;

  public FudgeTypeConverter(final FudgeContext fudgeContext) {
    _fudgeContext = fudgeContext;
  }

  public FudgeContext getFudgeContext() {
    return _fudgeContext;
  }

  @Override
  public synchronized boolean canConvertTo(final JavaTypeInfo<?> targetType) {
    // Always have a go
    return true;
  }

  @SuppressWarnings("unchecked")
  @Override
  public void convertValue(final ValueConversionContext conversionContext, final Object value, final JavaTypeInfo<?> type) {
    final FudgeFieldType fieldType = getFudgeContext().getTypeDictionary().getByJavaType(type.getRawClass());
    try {
      if (fieldType == null) {
        // Try conversion from Fudge message
        final FudgeDeserializer deserializer = new FudgeDeserializer(getFudgeContext());
        conversionContext.setResult(deserializer.fudgeMsgToObject(type.getRawClass(), (FudgeMsg) value));
      } else if (fieldType instanceof SecondaryFieldType<?, ?>) {
        // Try conversion from primary type
        conversionContext.setResult(((SecondaryFieldType<Object, Object>) fieldType).primaryToSecondary(value));
      } else {
        final FudgeFieldType valueType = getFudgeContext().getTypeDictionary().getByJavaType(value.getClass());
        if ((valueType == null) && (fieldType.getTypeId() == FudgeWireType.SUB_MESSAGE_TYPE_ID)) {
          // Serialization to a message
          final Class<?> valueClass = value.getClass();
          if (getFudgeContext().getObjectDictionary().isDefaultObject(valueClass)) {
            // Don't convert default objects to messages; they should be expressed using Data/Value constructs
            conversionContext.setFail();
          } else {
            final FudgeSerializer serializer = new FudgeSerializer(getFudgeContext());
            final MutableFudgeMsg msg = serializer.objectToFudgeMsg(value);
            if (msg.getByOrdinal(FudgeSerializer.TYPES_HEADER_ORDINAL) == null) {
              FudgeSerializer.addClassHeader(msg, valueClass);
            }
            conversionContext.setResult(msg);
          }
        } else {
          // Target is a primary type; the source value might be a secondary type that can convert to it
          if (valueType instanceof SecondaryFieldType<?, ?>) {
            final SecondaryFieldType<Object, Object> secondaryValueType = (SecondaryFieldType<Object, Object>) valueType;
            if (type.getRawClass().isAssignableFrom(secondaryValueType.getPrimaryType().getJavaType())) {
              conversionContext.setResult(secondaryValueType.secondaryToPrimary(value));
            } else {
              // The primary type doesn't match the target type 
              conversionContext.setFail();
            }
          } else {
            // Unknown type to convert from
            conversionContext.setFail();
          }
        }
      }
    } catch (Throwable t) {
      // Could be anything from an unsupported operation to an invalid argument that prevents the conversion
      conversionContext.setFail();
    }
  }

  @Override
  public Map<JavaTypeInfo<?>, Integer> getConversionsTo(final JavaTypeInfo<?> targetType) {
    final FudgeFieldType fieldType = getFudgeContext().getTypeDictionary().getByJavaType(targetType.getRawClass());
    if (fieldType == null) {
      // Arbitrary object type found; conversion may be possible from a Fudge message
      return FROM_FUDGE_MSG;
    } else if (fieldType instanceof SecondaryFieldType<?, ?>) {
      // Secondary type found; conversion is from the primary type
      return TypeMap.of(TypeMap.ZERO_LOSS, JavaTypeInfo.builder(((SecondaryFieldType<?, ?>) fieldType).getPrimaryType().getJavaType()).get());
    } else {
      // Arbitrary wire type found; dictionary conversion may be possible
      return FROM_OBJECT;
    }
  }

}
