namespace java climate.schema

# Compound Objects

struct FireTuple {
  1: i32 temp330;
  2: i32 conf50;
  3: i32 bothPreds;
  4: i32 count;
}

# Collection Wrappers

struct DoubleArray {
  1: list<double> doubles
}

struct LongArray {
  1: list<i64> longs
}

struct FireSeries {
  1: i32 startIdx
  2: i32 endIdx;
  3: list<FireTuple> values
}

union ArrayValue {
  1: LongArray longs;
  2: DoubleArray doubles;
}

struct TimeSeries {
  1: i32 startIdx
  2: i32 endIdx;
  3: ArrayValue series;
}

union DataValue {
  1: i64 longVal;
  2: LongArray longs;
  3: double doubleVal;
  4: DoubleArray doubles;
  5: FireTuple fireVal;
  6: TimeSeries timeSeries;
  7: FireSeries fireSeries;
}

struct ModisPixelLocation {
  1: string resolution;
  2: i32 tileH;
  3: i32 tileV;
  4: i32 sample;
  5: i32 line;
}

struct DataChunk {
  1: string dataset;
  2: ModisPixelLocation pixelLocation;
  3: DataValue chunkValue;
  4: string temporalRes;
  5: optional i32 period;
}
