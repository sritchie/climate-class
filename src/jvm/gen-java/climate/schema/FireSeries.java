/**
 * Autogenerated by Thrift
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 */
package climate.schema;

import org.apache.commons.lang.builder.HashCodeBuilder;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FireSeries implements org.apache.thrift.TBase<FireSeries, FireSeries._Fields>, java.io.Serializable, Cloneable {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("FireSeries");

  private static final org.apache.thrift.protocol.TField START_IDX_FIELD_DESC = new org.apache.thrift.protocol.TField("startIdx", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField END_IDX_FIELD_DESC = new org.apache.thrift.protocol.TField("endIdx", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField VALUES_FIELD_DESC = new org.apache.thrift.protocol.TField("values", org.apache.thrift.protocol.TType.LIST, (short)3);

  public int startIdx;
  public int endIdx;
  public List<FireTuple> values;

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    START_IDX((short)1, "startIdx"),
    END_IDX((short)2, "endIdx"),
    VALUES((short)3, "values");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // START_IDX
          return START_IDX;
        case 2: // END_IDX
          return END_IDX;
        case 3: // VALUES
          return VALUES;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __STARTIDX_ISSET_ID = 0;
  private static final int __ENDIDX_ISSET_ID = 1;
  private BitSet __isset_bit_vector = new BitSet(2);

  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.START_IDX, new org.apache.thrift.meta_data.FieldMetaData("startIdx", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.END_IDX, new org.apache.thrift.meta_data.FieldMetaData("endIdx", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.VALUES, new org.apache.thrift.meta_data.FieldMetaData("values", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, FireTuple.class))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(FireSeries.class, metaDataMap);
  }

  public FireSeries() {
  }

  public FireSeries(
    int startIdx,
    int endIdx,
    List<FireTuple> values)
  {
    this();
    this.startIdx = startIdx;
    setStartIdxIsSet(true);
    this.endIdx = endIdx;
    setEndIdxIsSet(true);
    this.values = values;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public FireSeries(FireSeries other) {
    __isset_bit_vector.clear();
    __isset_bit_vector.or(other.__isset_bit_vector);
    this.startIdx = other.startIdx;
    this.endIdx = other.endIdx;
    if (other.isSetValues()) {
      List<FireTuple> __this__values = new ArrayList<FireTuple>();
      for (FireTuple other_element : other.values) {
        __this__values.add(new FireTuple(other_element));
      }
      this.values = __this__values;
    }
  }

  public FireSeries deepCopy() {
    return new FireSeries(this);
  }

  @Override
  public void clear() {
    setStartIdxIsSet(false);
    this.startIdx = 0;
    setEndIdxIsSet(false);
    this.endIdx = 0;
    this.values = null;
  }

  public int getStartIdx() {
    return this.startIdx;
  }

  public FireSeries setStartIdx(int startIdx) {
    this.startIdx = startIdx;
    setStartIdxIsSet(true);
    return this;
  }

  public void unsetStartIdx() {
    __isset_bit_vector.clear(__STARTIDX_ISSET_ID);
  }

  /** Returns true if field startIdx is set (has been assigned a value) and false otherwise */
  public boolean isSetStartIdx() {
    return __isset_bit_vector.get(__STARTIDX_ISSET_ID);
  }

  public void setStartIdxIsSet(boolean value) {
    __isset_bit_vector.set(__STARTIDX_ISSET_ID, value);
  }

  public int getEndIdx() {
    return this.endIdx;
  }

  public FireSeries setEndIdx(int endIdx) {
    this.endIdx = endIdx;
    setEndIdxIsSet(true);
    return this;
  }

  public void unsetEndIdx() {
    __isset_bit_vector.clear(__ENDIDX_ISSET_ID);
  }

  /** Returns true if field endIdx is set (has been assigned a value) and false otherwise */
  public boolean isSetEndIdx() {
    return __isset_bit_vector.get(__ENDIDX_ISSET_ID);
  }

  public void setEndIdxIsSet(boolean value) {
    __isset_bit_vector.set(__ENDIDX_ISSET_ID, value);
  }

  public int getValuesSize() {
    return (this.values == null) ? 0 : this.values.size();
  }

  public java.util.Iterator<FireTuple> getValuesIterator() {
    return (this.values == null) ? null : this.values.iterator();
  }

  public void addToValues(FireTuple elem) {
    if (this.values == null) {
      this.values = new ArrayList<FireTuple>();
    }
    this.values.add(elem);
  }

  public List<FireTuple> getValues() {
    return this.values;
  }

  public FireSeries setValues(List<FireTuple> values) {
    this.values = values;
    return this;
  }

  public void unsetValues() {
    this.values = null;
  }

  /** Returns true if field values is set (has been assigned a value) and false otherwise */
  public boolean isSetValues() {
    return this.values != null;
  }

  public void setValuesIsSet(boolean value) {
    if (!value) {
      this.values = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case START_IDX:
      if (value == null) {
        unsetStartIdx();
      } else {
        setStartIdx((Integer)value);
      }
      break;

    case END_IDX:
      if (value == null) {
        unsetEndIdx();
      } else {
        setEndIdx((Integer)value);
      }
      break;

    case VALUES:
      if (value == null) {
        unsetValues();
      } else {
        setValues((List<FireTuple>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case START_IDX:
      return new Integer(getStartIdx());

    case END_IDX:
      return new Integer(getEndIdx());

    case VALUES:
      return getValues();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case START_IDX:
      return isSetStartIdx();
    case END_IDX:
      return isSetEndIdx();
    case VALUES:
      return isSetValues();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof FireSeries)
      return this.equals((FireSeries)that);
    return false;
  }

  public boolean equals(FireSeries that) {
    if (that == null)
      return false;

    boolean this_present_startIdx = true;
    boolean that_present_startIdx = true;
    if (this_present_startIdx || that_present_startIdx) {
      if (!(this_present_startIdx && that_present_startIdx))
        return false;
      if (this.startIdx != that.startIdx)
        return false;
    }

    boolean this_present_endIdx = true;
    boolean that_present_endIdx = true;
    if (this_present_endIdx || that_present_endIdx) {
      if (!(this_present_endIdx && that_present_endIdx))
        return false;
      if (this.endIdx != that.endIdx)
        return false;
    }

    boolean this_present_values = true && this.isSetValues();
    boolean that_present_values = true && that.isSetValues();
    if (this_present_values || that_present_values) {
      if (!(this_present_values && that_present_values))
        return false;
      if (!this.values.equals(that.values))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    HashCodeBuilder builder = new HashCodeBuilder();

    boolean present_startIdx = true;
    builder.append(present_startIdx);
    if (present_startIdx)
      builder.append(startIdx);

    boolean present_endIdx = true;
    builder.append(present_endIdx);
    if (present_endIdx)
      builder.append(endIdx);

    boolean present_values = true && (isSetValues());
    builder.append(present_values);
    if (present_values)
      builder.append(values);

    return builder.toHashCode();
  }

  public int compareTo(FireSeries other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    FireSeries typedOther = (FireSeries)other;

    lastComparison = Boolean.valueOf(isSetStartIdx()).compareTo(typedOther.isSetStartIdx());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetStartIdx()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.startIdx, typedOther.startIdx);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetEndIdx()).compareTo(typedOther.isSetEndIdx());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetEndIdx()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.endIdx, typedOther.endIdx);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetValues()).compareTo(typedOther.isSetValues());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetValues()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.values, typedOther.values);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    org.apache.thrift.protocol.TField field;
    iprot.readStructBegin();
    while (true)
    {
      field = iprot.readFieldBegin();
      if (field.type == org.apache.thrift.protocol.TType.STOP) { 
        break;
      }
      switch (field.id) {
        case 1: // START_IDX
          if (field.type == org.apache.thrift.protocol.TType.I32) {
            this.startIdx = iprot.readI32();
            setStartIdxIsSet(true);
          } else { 
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 2: // END_IDX
          if (field.type == org.apache.thrift.protocol.TType.I32) {
            this.endIdx = iprot.readI32();
            setEndIdxIsSet(true);
          } else { 
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 3: // VALUES
          if (field.type == org.apache.thrift.protocol.TType.LIST) {
            {
              org.apache.thrift.protocol.TList _list8 = iprot.readListBegin();
              this.values = new ArrayList<FireTuple>(_list8.size);
              for (int _i9 = 0; _i9 < _list8.size; ++_i9)
              {
                FireTuple _elem10;
                _elem10 = new FireTuple();
                _elem10.read(iprot);
                this.values.add(_elem10);
              }
              iprot.readListEnd();
            }
          } else { 
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
          }
          break;
        default:
          org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
      }
      iprot.readFieldEnd();
    }
    iprot.readStructEnd();

    // check for required fields of primitive type, which can't be checked in the validate method
    validate();
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    validate();

    oprot.writeStructBegin(STRUCT_DESC);
    oprot.writeFieldBegin(START_IDX_FIELD_DESC);
    oprot.writeI32(this.startIdx);
    oprot.writeFieldEnd();
    oprot.writeFieldBegin(END_IDX_FIELD_DESC);
    oprot.writeI32(this.endIdx);
    oprot.writeFieldEnd();
    if (this.values != null) {
      oprot.writeFieldBegin(VALUES_FIELD_DESC);
      {
        oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, this.values.size()));
        for (FireTuple _iter11 : this.values)
        {
          _iter11.write(oprot);
        }
        oprot.writeListEnd();
      }
      oprot.writeFieldEnd();
    }
    oprot.writeFieldStop();
    oprot.writeStructEnd();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("FireSeries(");
    boolean first = true;

    sb.append("startIdx:");
    sb.append(this.startIdx);
    first = false;
    if (!first) sb.append(", ");
    sb.append("endIdx:");
    sb.append(this.endIdx);
    first = false;
    if (!first) sb.append(", ");
    sb.append("values:");
    if (this.values == null) {
      sb.append("null");
    } else {
      sb.append(this.values);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bit_vector = new BitSet(1);
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

}

